package com.cable.payUMoney;

import lombok.Getter;
import lombok.Setter;


public class PaymentReturn {
	
	@Setter @Getter
	private String mode;
	@Setter @Getter
	private String Status;
	@Setter @Getter
	private String key;
	@Setter @Getter
	private String txnid;
	@Setter @Getter
	private String amount;
	@Setter @Getter
	private String productinfo;
	@Setter @Getter
	private String firstname;
	@Setter @Getter
	private String lastname;
	@Setter @Getter
	private String city;
	@Setter @Getter
	private String state;
	@Setter @Getter
	private String country;
	@Setter @Getter
	private String zipcode;
	@Setter @Getter
	private String email;
	@Setter @Getter
	private String phone;
	
	@Setter @Getter
	private String udf1;
	@Setter @Getter
	private String udf2;
	@Setter @Getter
	private String udf3;
	@Setter @Getter
	private String udf4;
	@Setter @Getter
	private String udf5;
	
	@Setter @Getter
	private String error;
	@Setter @Getter
	private String pg_type;
	@Setter @Getter
	private String bank_ref_num;
	@Setter @Getter
	private String payumoneyId;
	@Setter @Getter
	private String additionalCharges;
	

}
