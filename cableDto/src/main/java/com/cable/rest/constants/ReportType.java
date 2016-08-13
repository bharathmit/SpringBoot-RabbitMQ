package com.cable.rest.constants;

public enum ReportType {
  
	PAYMENT(1,"Payment"),
	CONNECTION(2,"TV Connection"),
	MASTER(3,"Master"),
	USER(4,"User");
	
  private int code;
  private String value;

  ReportType(int code,String value) {
      this.code = code;
      this.value = value;
  }
  
  public int getCode() {
      return this.code;
  }
  
  public String getValue() {
      return this.value;
  }
  
  
}
