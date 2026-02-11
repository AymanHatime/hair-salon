package hairtist.dto;

import hairtist.model.Extra;
import hairtist.model.TheOrder;
import hairtist.model.OrderLine;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class OrderDTO {

	private UUID id;
	private String userName;
	private String email;
	private Instant orderTime;
	private List<OrderLine> orderLines;
	private double totalPrice = 0;

	public OrderDTO() {

	}

	public OrderDTO(TheOrder order) {
		id = UUID.randomUUID();
		userName = order.getUserName();
		email = order.getEmail();
		orderTime = order.getOrderTime();
		orderLines = order.getOrderLines();
		for(OrderLine orderLine : order.getOrderLines()) {
			totalPrice+=orderLine.getProductPrice();
			for(Extra extra : orderLine.getExtras()) {
				totalPrice+=extra.getPrice();
			}
		}
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Instant getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Instant orderTime) {
		this.orderTime = orderTime;
	}

	public List<OrderLine> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(List<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
}
