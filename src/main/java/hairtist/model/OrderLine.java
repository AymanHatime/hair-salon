package hairtist.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class OrderLine {

	@Id
	private UUID id;

	private String productName;

	private String productDescription;

	private double productPrice;

	private int duration;

	@ManyToMany
	private List<Extra> extras;

	public OrderLine() {

	}

	public OrderLine(Product product) {
		id = UUID.randomUUID();
		productName = product.getName();
		productDescription = product.getDescription();
		productPrice = product.getPrice();
		duration = product.getDuration();
		this.extras = new ArrayList<>();
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

	public void addExtra(Extra extra) {
		extras.add(extra);
	}
}
