package com.cable.rest.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailDto  implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Getter	@Setter	
	private String to;
	
	@Getter	@Setter	
	private String bcc;
	
	@Getter	@Setter	
	private String subject;
	
	@Getter	@Setter	
	private String templateLocation;
	
	@Getter	@Setter
	private Map model = new HashMap();
	
	
	
}
