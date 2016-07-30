package com.cable.rest.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import com.cable.rest.constants.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneratePaymentDto extends BaseDto implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Getter	@Setter	
	private Long payGenId;
	
	@Getter	@Setter	
	private Date payGenDate;
	
	@Getter	@Setter	
	private PaymentStatus payGenStatus;
	
	@Getter	@Setter	
	private Date billDate;
	
	@Getter	@Setter	
	private Date dueDate;
	
	@Getter	@Setter	
	private Double invoiceAmount;
	
	@Getter	@Setter	
	private Double discountAmount;
	
	@Getter	@Setter	
	private Double billAmount;
	
	@Getter	@Setter	
	private ConnectionAccountDto connectionAccount;

}
