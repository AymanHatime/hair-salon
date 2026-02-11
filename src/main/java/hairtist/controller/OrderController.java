package hairtist.controller;

import hairtist.dto.*;
import hairtist.model.Extra;
import hairtist.model.TheOrder;
import hairtist.model.OrderLine;
import hairtist.model.Product;
import hairtist.repository.ExtraRepository;
import hairtist.repository.OrderLineRepository;
import hairtist.repository.OrderRepository;
import hairtist.repository.ProductRepository;
import hairtist.service.GoogleCalendarService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ExtraRepository extraRepository;

	@Autowired
	private OrderLineRepository orderLineRepository;

	private final GoogleCalendarService calendarService;

	public OrderController(GoogleCalendarService calendarService) {
		this.calendarService = calendarService;
	}

	@GetMapping("/ses")
	public ResponseEntity<OrderDTO> listOrderLines(HttpSession session){
		TheOrder order = (TheOrder) session.getAttribute("order");
		if(order==null) return ResponseEntity.notFound().build();
		return ResponseEntity.ok().body(new OrderDTO(order));
	}

	@PostMapping("/{productId}")
	public ResponseEntity<OrderLineDTO> addOrderLine (HttpSession session, @PathVariable UUID productId){
		Optional<Product> product = productRepository.findById(productId);
		if(!product.isPresent()) return ResponseEntity.badRequest().build();
		if(session.getAttribute("order") == null) {
			TheOrder order = new TheOrder();
			order.setId(UUID.randomUUID());
			session.setAttribute("order", order);
		}
		TheOrder order = (TheOrder) session.getAttribute("order");
		OrderLine orderLine = new OrderLine(product.get());
		order.getOrderLines().add(orderLine);
		return ResponseEntity.ok().body(new OrderLineDTO(orderLine, productId));
	}

	@PutMapping("/removeOrderLine/{orderLineId}")
	public ResponseEntity<TheOrder> removeOrderLine(HttpSession session, @PathVariable UUID orderLineId){
		TheOrder order = (TheOrder) session.getAttribute("order");
		if(order == null) return ResponseEntity.notFound().build();
		for(OrderLine orderLine : order.getOrderLines()){
			if(orderLineId.equals(orderLine.getId())){
				order.getOrderLines().remove(orderLine);
				if(order.getOrderLines().isEmpty()) session.removeAttribute("order");
				return ResponseEntity.ok().body(order);
			}
		}
		return ResponseEntity.badRequest().build();
	}

	@GetMapping("/reservedSlots")
	public ResponseEntity<List<OrderTimeDTO>> getReservedSlots(){
		List<OrderTimeDTO> reservedSlots = new ArrayList<>();
		for(TheOrder order : orderRepository.findAll()){
			int nbMinutes = 0;
			for(OrderLine orderLine : order.getOrderLines()){
				nbMinutes += orderLine.getDuration();
			}
			if(nbMinutes == 0) nbMinutes = 15;
			LocalDateTime startTime = order.getOrderTime().atZone(ZoneId.of("Europe/Paris")).toLocalDateTime();
			LocalDateTime endTime = startTime.plusMinutes(nbMinutes);
			reservedSlots.add(new OrderTimeDTO(startTime, endTime));
		}
		return ResponseEntity.ok().body(reservedSlots);
	}

	@PutMapping("/addExtra")
	public ResponseEntity<List<ExtraDTO>> addExtra(HttpSession session, @RequestBody TwoUuidDTO ids){
		TheOrder order = (TheOrder) session.getAttribute("order");
		if(order == null) return ResponseEntity.notFound().build();
		for(OrderLine orderLine : order.getOrderLines()){
			if(orderLine.getId().equals(ids.getId1())){
				Optional<Extra> extra = extraRepository.findById(ids.getId2());
				if(!extra.isPresent()) return ResponseEntity.notFound().build();
				orderLine.addExtra(extra.get());
				session.setAttribute("order", order);
				List<ExtraDTO> extraDTOs = new ArrayList<>();
				return ResponseEntity.ok().build();
			}
		}
		return ResponseEntity.badRequest().build();
	}

	@PostMapping("/comfirmorder")
	public ResponseEntity<OrderDTO> confirmOrder(HttpSession session, @RequestBody TwoStringOneMoment lastData){
		TheOrder order = (TheOrder) session.getAttribute("order");
		order.setEmail(lastData.getEmail());
		order.setUserName(lastData.getUserName());
		order.setOrderTime(lastData.getOrderTime().atZone(ZoneOffset.UTC).toInstant());

		for(OrderLine orderLine : order.getOrderLines()){
			orderLineRepository.save(orderLine);
		}

		// Adding the order to the database.
		orderRepository.save(order);

		// Adding the order to the enterprise calendar.
		try {
			LocalDateTime orderTimeFormated = order.getOrderTime().atZone(ZoneId.of("Europe/Paris")).toLocalDateTime();
			String descriptionFormated = "Réservation au nom de "+order.getUserName()+" ("+order.getEmail()+") pour : ";
			int minutesToAdd = 0;

			for(OrderLine orderLine : order.getOrderLines()){
				descriptionFormated += orderLine.getProductName()+", ";
				minutesToAdd += orderLine.getDuration();
			}

			if(minutesToAdd == 0) {
				minutesToAdd = 15;
			}

			String timeZone = "Europe/Paris";
			String startDateTime = orderTimeFormated.atZone(ZoneId.of(timeZone))
							.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
			String endDateTime = orderTimeFormated.plusMinutes(minutesToAdd).atZone(ZoneId.of(timeZone))
							.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

			calendarService.addEvent(
							"aymanhtm09@gmail.com",
							"Réservation : " + order.getUserName(),
							descriptionFormated,
							startDateTime,
							endDateTime,
							timeZone
			);
			session.removeAttribute("order");
			return ResponseEntity.ok().body(new OrderDTO(order));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping("deleteorder/{id}")
	public ResponseEntity<OrderDTO> deleteOrder(@PathVariable UUID id){
		Optional<TheOrder> order = orderRepository.findById(id);
		List<OrderLine> orderLinesList = orderLineRepository.findAll();
		if(!order.isPresent()) return ResponseEntity.notFound().build();
		orderRepository.delete(order.get());
		for(OrderLine ol : order.get().getOrderLines()){
			Optional<OrderLine> theol = orderLineRepository.findById(ol.getId());
			if(theol.isPresent()) orderLineRepository.delete(theol.get());
		}
		return ResponseEntity.ok().build();
	}
}
