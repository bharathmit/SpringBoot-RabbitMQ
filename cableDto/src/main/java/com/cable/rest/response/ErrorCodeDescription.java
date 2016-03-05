package com.cable.rest.response;

import lombok.Getter;


public enum ErrorCodeDescription {
	
	TRANSACTION_FAILED(9000,"your transaction is failed"),
	TRANSACTION_SUCCESS(9001,"your transaction is successful"),
	DATA_ACCESS(9002,"Data Access Exception"),
	 
	
	
	INVALID_USER(1001,"Incorrect UserName"),
	INVALID_PASSWORD(1002,"Incorrect Password"),
	USER_EXIT(1003,"User Name Already Exit"),
	MOBILE_EXIT(1004,"Mobile Number Already Exit"),
	INVALID_TOKEN(1005,"Incoorrect Registration confirmation Token"),
	TIME_OUT(1006,"Registration confirmation Token Expiryed"),
	
	;
	 
	@Getter
    private int errorCode; 
	@Getter
    private String errorDescription;

    private ErrorCodeDescription(int code, String description) {
    	
        this.errorCode = code;
        this.errorDescription = description;
    }
    
}
