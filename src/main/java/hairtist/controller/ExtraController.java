package hairtist.controller;

import hairtist.dto.ExtraDTO;
import hairtist.dto.ExtrasDTO;
import hairtist.model.Extra;
import hairtist.model.TheOrder;
import hairtist.model.OrderLine;
import hairtist.repository.ExtraRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * This class is used to manage the extras the users can add to their products.
 */
@RestController
@RequestMapping("/extra")
public class ExtraController {

	@Autowired
	private ExtraRepository extraRepository;

	@GetMapping("")
	public ResponseEntity<List<Extra>> listAllExtras(){
		return ResponseEntity.ok(extraRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Extra> getExtraById(@PathVariable UUID id){
		Optional<Extra> extra = extraRepository.findById(id);
		if(extra.isPresent()) return ResponseEntity.ok(extra.get());
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/byorderline/{orderLineId}")
	public ResponseEntity<List<ExtrasDTO>> getExtraByOrder(HttpSession session, @PathVariable UUID orderLineId){
		TheOrder order = (TheOrder) session.getAttribute("order");

		if(order!=null){
			List<ExtrasDTO> extrasDto = new ArrayList<>();
			List<Extra> extras = extraRepository.findAll();

			for(Extra extra : extras){
				extrasDto.add(new ExtrasDTO(extra));
			}

			for(OrderLine orderLine : order.getOrderLines()){
				if(orderLine.getId().equals(orderLineId)){
					for(Extra extraOL : orderLine.getExtras()){
						for(ExtrasDTO extraDB : extrasDto){
							if(extraOL.getId().equals(extraDB.getId())){
								extraDB.setActive(true);
							}
						}
					}
				}
			}

			return ResponseEntity.ok().body(extrasDto);
		}

		return ResponseEntity.badRequest().build();
	}

	@PostMapping
	public ResponseEntity<Extra> createExtra(@RequestBody ExtraDTO extra){
		Optional<Extra> alreadyExist = extraRepository.findByName(extra.getName());

		if(alreadyExist.isPresent()) {
			return ResponseEntity.badRequest().build();
		}

		Extra newExtra = new Extra(extra.getName(), extra.getDescription(), extra.getPrice());
		extraRepository.save(newExtra);
		return ResponseEntity.ok(newExtra);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Extra> deleteExtra(@PathVariable UUID id){
		Optional<Extra> extra = extraRepository.findById(id);

		if(!extra.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		extraRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
