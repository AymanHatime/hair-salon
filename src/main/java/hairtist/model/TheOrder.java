package hairtist.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class TheOrder {

	@Id
	private UUID id;

	private String userName;

	private String email;

	private Instant orderTime;

	@ManyToMany
	private List<OrderLine> orderLines = new ArrayList<>();

	public TheOrder() {

	}

	public TheOrder(String userName, String email, Instant orderTime) {
		id = UUID.randomUUID();
		this.userName = userName;
		this.email = email;
		this.orderTime = orderTime;
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

}
