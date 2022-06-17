package com.losilegales.oprterrestres.dto.AeroNaves;

import lombok.Data;

@Data
public class AeronaveDTO {

	private Integer id;
	private String model;
	private Integer fuelConsumption;
	private Integer lubricantConsumption;
	private Integer passengerCapacity;
	private Integer weightTolerance;
	private Integer maxDistance;
	private Integer crewMembers;

}
