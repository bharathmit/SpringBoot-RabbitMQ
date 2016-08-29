package com.cable.rest.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import com.cable.rest.constants.PaymentStatus;
import com.cable.rest.constants.PaymentType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentDetailDto extends BaseDto implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Getter	@Setter	
	private Long paymentId;
	
	@Getter	@Setter	
	private GeneratePaymentDto generatepayment;
	
	@Getter	@Setter	
	private PaymentType paymentType;
	
	@Getter	@Setter	
	private PaymentStatus paymentStatus;
	
	@Getter	@Setter	
	private Double paymentAmount;
	
	@Getter	@Setter	
	private Long paymentUser;
	
	@Getter	@Setter	
	private ConnectionAccountDto connection;
	
	
	
	
	

}
