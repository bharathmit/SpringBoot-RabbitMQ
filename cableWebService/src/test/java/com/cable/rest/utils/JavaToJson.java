package com.cable.rest.utils;


import com.cable.rest.search.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JavaToJson {
	
public static void main(String[] args) {
		
		try {
			ProjectSearch object=new ProjectSearch();
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			
			//java variables
			//String json = ow.writeValueAsString(object).replaceAll(":", "").replaceAll("null", "").replaceAll(",","").replaceAll("\"", "");
			
			//Json Objject
			String json = ow.writeValueAsString(object).replaceAll("null", "\"\"");
			
			System.out.println(json);
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
