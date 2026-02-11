package hairtist.dto;

import java.time.LocalDateTime;

public class RequestTime {
	private String name;
	private String desription;
	private LocalDateTime dateTimeStart;

	public RequestTime(){

	}

	public RequestTime(String name, String desription, LocalDateTime dateTimeStart) {
		this.name = name;
		this.desription = desription;
		this.dateTimeStart = dateTimeStart;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesription() {
		return desription;
	}

	public void setDesription(String desription) {
		this.desription = desription;
	}

	public LocalDateTime getDateTimeStart() {
		return dateTimeStart;
	}

	public void setDateTimeStart(LocalDateTime dateTimeStart) {
		this.dateTimeStart = dateTimeStart;
	}
}
