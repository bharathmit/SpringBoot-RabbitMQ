package com.cable.rest.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.cable.rest.constants.ReportType;
import com.cable.rest.constants.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportDto extends BaseDto implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Getter	@Setter	
	private Long reportId;  
	@Getter	@Setter	  
	private String reportName;  
	@Getter	@Setter	  
	private String reportFileName;  
	@Getter	@Setter	 
	private ReportType reportType;  
	@Getter	@Setter	  
	private Status status;  
	@Getter	@Setter	  
	private List<ReportParameterDto> reportParameters;
	
	
}
