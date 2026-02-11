package hairtist.dto;

import java.util.UUID;

public class TwoUuidDTO {
	private UUID id1;
	private UUID id2;

	public TwoUuidDTO() {

	}

	public TwoUuidDTO(UUID id1, UUID id2) {
		this.id1 = id1;
		this.id2 = id2;
	}

	public UUID getId1() {
		return id1;
	}

	public void setId1(UUID id1) {
		this.id1 = id1;
	}

	public UUID getId2() {
		return id2;
	}

	public void setId2(UUID id2) {
		this.id2 = id2;
	}

}
