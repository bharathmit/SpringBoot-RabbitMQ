package com.cable.rest.dto;


import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import com.cable.rest.constants.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class AreaDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Getter	@Setter	
	private Long areaId;
	
	@Getter	@Setter	
	private String areaName;
	
	@Getter	@Setter	
	private ProjectDto project;
	
	@Getter	@Setter	
	private Status status;

}
