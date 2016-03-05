package com.cable.app.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.cable.app.exception.FacesUtil;
import com.cable.app.exception.RestUtil;
import com.cable.app.utils.RestClient;
import com.cable.rest.dto.LoginDto;
import com.cable.rest.dto.LoginResponseDto;
import com.cable.rest.response.ErrorResource;
import com.fasterxml.jackson.databind.ObjectMapper;


@ManagedBean
@SessionScoped
@Log4j
public class LoginBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{restTemplate}")
	@Getter @Setter
	RestTemplate restTemplate;
	
	@ManagedProperty(value="#{restClient}")
	@Getter @Setter
	RestClient restClient;
	
	@ManagedProperty(value="#{objectMapper}")
	@Getter @Setter
	ObjectMapper objectMapper;
	
	
	@Getter @Setter
	LoginDto loginDto=new LoginDto();
	
	@Getter @Setter
	private LoginResponseDto loginResponse;

	public static String  AUTH_KEY="name";	
	ExternalContext extCon;
	HttpSession session;
	HttpServletRequest req;
	
	@Getter @Setter
	public static HttpEntity requestEntity;
	@Getter @Setter
	public static MultiValueMap<String,String> header;
	
	/** layout.xhtml side bar main menu set active */
	@Getter @Setter
	private String activeMainMenu;
	/** layout.xhtml side bar sub menu set active */
	@Getter @Setter
	private String activeSubMenu;
	
	
	
	public void submit() throws IOException{
		try{
			extCon = FacesContext.getCurrentInstance().getExternalContext();
			log.info("Login Method Call");
			
	    	if ( StringUtils.isEmpty(loginDto.getLoginId()) && StringUtils.isEmpty(loginDto.getPassword())) 
			{			
	    		FacesUtil.warn("Please Enter Your Email Id and Password");
				return ;
			} 
			else if (StringUtils.isEmpty(loginDto.getLoginId())) 
			{			
				FacesUtil.warn("Please Enter Your Email Id");
				return ;
			} 
			else if (StringUtils.isEmpty(loginDto.getPassword())) 
			{			
				FacesUtil.warn("Please Enter Your Password");
				return ;
			}	
	    	
	    	LoginResponseDto loginResponseDto=null;
	    	try{
	    		
	    		ResponseEntity<String> response = restTemplate.postForEntity(restClient.createUrl("login/validateuser"),loginDto, String.class);
	    		
	    		String responseBody = response.getBody();
	    		
	    		if (RestUtil.isError(response.getStatusCode())) {
	    			ErrorResource error = objectMapper.readValue(responseBody, ErrorResource.class);
	    			
	    			FacesUtil.warn(error.getFieldErrors().get(0).getMessage());
	    			return ;
	               
	            } else {
	            	loginResponseDto = objectMapper.readValue(responseBody, LoginResponseDto.class);
	                
	            }
	    		
	    		
	    		
	    		if(org.springframework.util.StringUtils.isEmpty(loginResponseDto.getUser())){
	    			FacesUtil.warn("The username or password you entered is incorrect.");
	    			return ;
	    		}
	    		
	    		
	    		
	    	}catch(Exception e){
	    		FacesUtil.warn("Connection Refused");
	    		log.error("Connection Refused",e);
	    		return ;
	    	}
	    	
	    	if(loginResponseDto.isAuthenticationStatus()){
	    		System.out.println("SESSIONid :"+loginResponseDto.getSessionid());
	    		
	    		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
	    		headers.add("Cookie", "JSESSIONID=" + loginResponseDto.getSessionid());
	    		headers.add("Content-Type", "application/json");
	    		
	    		requestEntity = new HttpEntity(headers);    
	    		header=headers;
	    		
	    		extCon = FacesContext.getCurrentInstance().getExternalContext();
				session = (HttpSession) extCon.getSession(true);
				req =(HttpServletRequest) extCon.getRequest();	
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(AUTH_KEY, loginDto.getLoginId());
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("UserObject", loginResponseDto);
				loginResponse=loginResponseDto;
				
				extCon.redirect(extCon.getRequestContextPath()+"/pages/index.xhtml?faces-redirect=true");
				
	    		
	    	}else{
	    		FacesUtil.warn("The username or password you entered is incorrect.");
	    	}
			
		}
		catch(Exception e){
			FacesUtil.error("unexpected condition occurs");
			log.error("unexpected condition occurs");
			return ;
		}
		}
	
	
	public void logout() throws IOException {
		extCon = FacesContext.getCurrentInstance().getExternalContext();
		extCon.invalidateSession();
		restTemplate.exchange(restClient.createUrl("login/logmeout"), HttpMethod.GET,
				LoginBean.requestEntity, String.class);
		extCon.redirect(extCon.getRequestContextPath()+"/login.xhtml");
		
	}

}
