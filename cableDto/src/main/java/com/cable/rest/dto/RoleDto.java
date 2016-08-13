package com.cable.rest.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import com.cable.rest.constants.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleDto extends BaseDto implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Getter	@Setter	
	Long roleId;
	
	@Getter	@Setter	
	String roleName;
	
	@Getter	@Setter	
	Status status;

}
