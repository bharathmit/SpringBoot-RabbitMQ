package com.cable.rest.search;

import lombok.Getter;
import lombok.Setter;

public class MasterSearch {
	
	@Getter	@Setter	
	private Long zipCode;
	
	@Getter @Setter
	private String locationName;
	
	
	@Getter @Setter
	private Long projectId;
	@Getter @Setter
	private String projectName;
	
	@Getter	@Setter	
	private Long areaId;
	@Getter	@Setter	
	private String areaName;
	
	@Getter	@Setter	
	private Long streetId;
	@Getter	@Setter	
	private String streetName;

}
