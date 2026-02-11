package hairtist.dto;

import hairtist.model.Extra;
import hairtist.model.OrderLine;

import java.util.List;
import java.util.UUID;

public class OrderLineDTO {
	private UUID id;
	private String productName;
	private String productDescription;
	private double productPrice;
	private int duration;
	private List<Extra> extras;
	private UUID productId;

	public OrderLineDTO() {

	}

	public OrderLineDTO(OrderLine orderLine, UUID productId) {
		id = orderLine.getId();
		productName = orderLine.getProductName();
		productDescription = orderLine.getProductDescription();
		productPrice = orderLine.getProductPrice();
		duration = orderLine.getDuration();
		extras = orderLine.getExtras();
		this.productId = productId;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public List<Extra> getExtras() {
		return extras;
	}

	public void setExtras(List<Extra> extras) {
		this.extras = extras;
	}

	public UUID getProductId() {
		return productId;
	}

	public void setProductId(UUID productId) {
		this.productId = productId;
	}
}
