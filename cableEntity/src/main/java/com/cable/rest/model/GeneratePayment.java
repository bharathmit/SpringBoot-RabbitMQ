package com.cable.rest.model;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cable.rest.constants.PaymentStatus;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="generate_payment")
public class GeneratePayment extends Audit implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter	@Setter	
	private Long payGenId;
	
	@Temporal( TemporalType.DATE)
	@Column(nullable=false)
	@Getter	@Setter	
	private Date payGenDate;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	@Getter	@Setter	
	private PaymentStatus payGenStatus;
	
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(nullable=false)
	@Getter	@Setter	
	private Date billDate;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(nullable=false)
	@Getter	@Setter	
	private Date dueDate;
	
	@Column(nullable=false)
	@Getter	@Setter	
	private Double invoiceAmount;
	
	@Column(nullable=false)
	@Getter	@Setter	
	private Double discountAmount;
	
	@ManyToOne
	@JoinColumn(name="account_Id", nullable=false)
	@Getter	@Setter	
	private ConnectionAccount connectionAccount;
	
	
	
	
	

}
