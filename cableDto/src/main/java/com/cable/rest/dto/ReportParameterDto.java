package com.cable.rest.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import com.cable.rest.constants.ReportParamType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportParameterDto extends BaseDto implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Getter	@Setter	
	private Long reportParamId;
	
	@Getter	@Setter	
	private String reportParamName;
	
	@Getter	@Setter	
	private ReportParamType reportParamType;

}
