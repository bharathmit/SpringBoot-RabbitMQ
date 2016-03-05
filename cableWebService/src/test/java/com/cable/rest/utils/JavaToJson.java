package com.cable.rest.utils;

import com.cable.rest.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JavaToJson {
	
public static void main(String[] args) {
		
		try {
			ConnectionAccountDto object=new ConnectionAccountDto();
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = ow.writeValueAsString(object).replaceAll(":", "").replaceAll("null", "").replaceAll(",","");
			System.out.println(json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
