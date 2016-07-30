package com.cable.rest.search;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import com.cable.rest.constants.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentSearch implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Getter	@Setter	
	private PaymentStatus payGenStatus;
	
}
