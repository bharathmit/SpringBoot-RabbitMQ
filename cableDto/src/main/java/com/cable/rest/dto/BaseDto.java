package com.cable.rest.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class BaseDto {
	
	@Getter	@Setter	
	private Date createdDate;
	
	@Getter	@Setter	
	private Long createdBy;

	@Getter	@Setter	
	private Date lastModifiedDate;
	
	@Getter	@Setter	
	private Long lastModifiedBy;
	
	
}
