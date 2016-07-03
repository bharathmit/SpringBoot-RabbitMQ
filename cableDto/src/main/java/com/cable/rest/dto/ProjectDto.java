package com.cable.rest.dto;

import java.io.Serializable;

import com.cable.rest.constants.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDto extends BaseDto implements Serializable  {
	
	private static final long serialVersionUID = 1L;

	@Getter @Setter
	private Long projectId;
	@Getter @Setter
	private String projectCityVillage;
	@Getter @Setter
	private OrganizationDto organization;
	@Getter @Setter
	private String address;
	@Getter @Setter
	private ZipCodeDto zipCode=new ZipCodeDto();
	@Getter @Setter
	private Status status;
	@Getter @Setter
	private String email;
	@Getter @Setter
	private String mobile;
	@Getter @Setter
	private Double advanceAmount;
	@Getter @Setter
	private Status onlinePaymentFlag;
	@Getter @Setter
	private int paymentGenerateDate;
	@Getter @Setter
	private int paymentDueDate;

}
