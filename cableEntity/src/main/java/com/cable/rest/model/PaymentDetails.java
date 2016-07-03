package com.cable.rest.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="payment_details")
public class PaymentDetails extends Audit implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter	@Setter	
	private Long paymentId;
	
	@ManyToOne
	@JoinColumn(name="pay_gen_id", nullable=false)
	@Getter	@Setter	
	private GeneratePayment generatepayment;
	
	//need to change enum. online or manual
	@Column(nullable=false)
	@Getter	@Setter	
	private int paymentType;
	
	@Column(nullable=false)
	@Getter	@Setter	
	private int paymentStatus;
	
	@Column(nullable=false)
	@Getter	@Setter	
	private Double paymentAmount;
	
	@Column(nullable=false)
	@Getter	@Setter	
	private Long paymentUser;
	
	@Column(nullable=false)
	@Getter	@Setter	
	private Long paymentCustomer;
	
	
}
