package com.cable.rest.search;

import lombok.Getter;
import lombok.Setter;

public class AccountSearch {

	@Getter	@Setter	
	private Long areaId;
	
	@Getter	@Setter	
	private Long streetId;
	
	@Getter	@Setter	
	private String name;
	
	@Getter	@Setter	
	private String mobile;
	
	@Getter	@Setter	
	private String emailId;
}
