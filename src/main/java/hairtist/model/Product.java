package hairtist.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Product {

	@Id
	private UUID id;

	private String name;

	private String description;

	private double price;

	private int duration;

	private boolean isVip;

	private boolean byPhone;

	public Product(){

	}

	public Product(String name, String description, double price, int duration, boolean isVip, boolean byPhone) {
		id = UUID.randomUUID();
		this.name = name;
		this.description = description;
		this.price = price;
		this.duration = duration;
		this.isVip = isVip;
		this.byPhone = byPhone;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public boolean isByPhone() {
		return byPhone;
	}

	public void setByPhone(boolean byPhone) {
		this.byPhone = byPhone;
	}

	public boolean isVip() {
		return isVip;
	}

	public void setVip(boolean vip) {
		isVip = vip;
	}
}
