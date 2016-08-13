package com.cable.rest.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import com.cable.rest.constants.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StreetDto extends BaseDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Getter	@Setter	
	private Long streetId;
	
	@Getter	@Setter	
	private String streetName;
	
	@Getter	@Setter	
	private AreaDto area=new AreaDto();
	
	@Getter	@Setter	
	private Status status;

}
