package com.cable.payUMoney;

import lombok.Getter;
import lombok.Setter;

public class PaymentBuilder {
	
	@Setter @Getter
	private String key;
	@Setter @Getter
	private String surl;
	@Setter @Getter
	private String furl;
	@Setter @Getter
	private String checksum;
	@Setter @Getter
	private String service_provider;
	
	@Setter @Getter
	private String txnid;
	@Setter @Getter
	private String amount;
	@Setter @Getter
	private String productinfo;
	@Setter @Getter
	private String firstname;
	@Setter @Getter
	private String email;
	@Setter @Getter
	private String phone;
	
	@Setter @Getter
	private String lastname;
	@Setter @Getter
	private String address1;
	@Setter @Getter
	private String address2;
	@Setter @Getter
	private String city;
	@Setter @Getter
	private String state;
	@Setter @Getter
	private String country;
	@Setter @Getter
	private String zipcode;
	
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
	

}
