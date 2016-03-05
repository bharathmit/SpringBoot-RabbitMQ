package com.cable.rest.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.cable.rest.constants.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ZipCodeDto extends BaseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private Long zipCode;

	@Getter
	@Setter
	private String locationName;

	@Getter
	@Setter
	private String pinCode;

	@Getter
	@Setter
	private String district;

	@Getter
	@Setter
	private String state;

	@Getter
	@Setter
	private String country;

	@Getter
	@Setter
	private Status status;

}
