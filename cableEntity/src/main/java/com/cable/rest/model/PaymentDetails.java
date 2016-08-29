package com.cable.rest.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cable.rest.constants.PaymentStatus;
import com.cable.rest.constants.PaymentType;

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
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	@Getter	@Setter	
	private PaymentType paymentType;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	@Getter	@Setter	
	private PaymentStatus paymentStatus;
	
	@Column(nullable=false)
	@Getter	@Setter	
	private Double paymentAmount;
	
	@Column(nullable=false)
	@Getter	@Setter	
	private Long paymentUser;
	
	@ManyToOne
	@JoinColumn(name="connection", nullable=false)
	@Getter	@Setter	
	private ConnectionAccount connection;
	
	
}
