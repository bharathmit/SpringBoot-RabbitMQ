package com.cable.payUMoney;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;


public class PayURestTemplate {
	
	private String hashSequence = "key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5|udf6|udf7|udf8|udf9|udf10";
	
	
	private String key;
	private String salt;
	private String surl;
	private String furl;
	private String api;
	private String provider;
	
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	ObjectMapper objectMapper;
	
	
	public PayURestTemplate(String merchantKey, String merchantKeySalt, Map<String, String> config) {
        this.key=merchantKey;
        this.salt=merchantKeySalt;
        this.api=config.get("api");
        this.provider=config.get("provider");
        this.surl=config.get("surl");
        this.furl=config.get("furl");
        
    }
	
	
	public String hashCal(String algorithmType, PaymentBuilder paymentBuilder){
		StringBuffer hexString = new StringBuffer();
		try {
			
			String hash="";
			String[] hashVarSeq = hashSequence.split("\\|");
			
			for (String part : hashVarSeq) {
				hash.concat("");
				hash.concat("|");
			}
			hash.concat(this.salt);
			
			
			byte[] hashseq = hash.getBytes();
			MessageDigest algorithm = MessageDigest.getInstance(algorithmType);
			algorithm.reset();
            algorithm.update(hashseq);
            
            byte messageDigest[] = algorithm.digest();
            for (int i = 0; i < messageDigest.length; i++) {
                String hex = Integer.toHexString(0xFF & messageDigest[i]);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
			
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hexString.toString();
	}
	
	
	
	public PaymentReturn createPayment(PaymentBuilder paymentBuilder){
		try{
			paymentBuilder.setKey(this.key);
			paymentBuilder.setSurl(this.surl);
			paymentBuilder.setFurl(this.furl);
			paymentBuilder.setService_provider(this.provider);
			paymentBuilder.setChecksum(hashCal("SHA-512",paymentBuilder));
			
			ResponseEntity<String> response = restTemplate.postForEntity(this.api+"/_payment",paymentBuilder, String.class);
			
			String responseBody = response.getBody();
			
			return objectMapper.readValue(responseBody, PaymentReturn.class);
		}
		catch(Exception e){
			
		}
		return null;
	}

}
