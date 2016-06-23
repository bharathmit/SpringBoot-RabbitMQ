package com.cable.app.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.cable.app.exception.FacesUtil;
import com.cable.app.exception.RestUtil;
import com.cable.app.utils.RestClient;
import com.cable.rest.dto.OrganizationDto;
import com.cable.rest.dto.PaymentDetailDto;
import com.cable.rest.response.ErrorResource;
import com.cable.rest.search.PaymentSearch;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@ManagedBean
@SessionScoped
@Log4j
public class PaymentBean {
	
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
	PaymentSearch paymentSearch=new PaymentSearch();
	
	@Getter @Setter
	List<PaymentDetailDto> paymentList=new ArrayList<PaymentDetailDto>();
	
	@Getter @Setter
	PaymentDetailDto paymentSelected=new PaymentDetailDto();
	
	@Setter @Getter
	String action;
	
	@Getter @Setter
	int numberOfRecords;
	
	
	public String showPaymentList(){

		
		paymentSelected=new PaymentDetailDto();
		numberOfRecords = 0;
		
		try{
           
			HttpEntity<PaymentSearch> requestEntity = new HttpEntity<PaymentSearch>(paymentSearch, LoginBean.header);

			ResponseEntity<String> response = restTemplate.exchange(restClient.createUrl("payment/paymentlist"),HttpMethod.POST,requestEntity,String.class);

			String responseBody = response.getBody();

			if (RestUtil.isError(response.getStatusCode())) {
				ErrorResource error = objectMapper.readValue(responseBody, ErrorResource.class);

				FacesUtil.warn(error.getFieldErrors().get(0).getMessage());
				return "";

			} else {
				paymentList = objectMapper.readValue(responseBody, new TypeReference<List<PaymentDetailDto>>(){});
				if(paymentList != null){
					numberOfRecords = paymentList.size();
				}

			}

		}
		catch(Exception e){
			log.error("showPaymentList", e);
		}
		paymentSearch = new PaymentSearch();
		return "/pages/payment/paymentlist.xhtml";
		
	}
	
	public void clearSearch(){
		paymentSearch = new PaymentSearch();
	}
	
	public String showPaymentForm(){
		log.info("show Payment Form Called Action is "+action);
		log.info("show Project Form Value is "+paymentSelected);
		
		if(action.equals("Add")){
			paymentSelected = new PaymentDetailDto();
		}else if(action.equals("Edit")){
			if(paymentSelected == null){
				FacesUtil.warn("Please select any one Payment");
				return null;
			}
		}else if(action.equals("View")){
			if(paymentSelected == null){
				FacesUtil.warn("Please select any one Payment");
				return null;
			}
		}
		return "/pages/payment/paymentform.xhtml";
	}
	
	
	public void clearForm(){
		//paymentSelected.	  
	}
	
	public String cancelPaymentForm(){
		paymentSelected = new PaymentDetailDto();
		return "/pages/master/projectlist.xhtml";
		
	}
	
	
	public String savePayment(){
		try{
			
			//need to change UserContext
			OrganizationDto org=new OrganizationDto();
			org.setOrgId(1l);
			//paymentSelected.setOrganization(org);
			
			HttpEntity<PaymentDetailDto> requestEntity = new HttpEntity<PaymentDetailDto>(paymentSelected, LoginBean.header);

			ResponseEntity<String> response = restTemplate.exchange(restClient.createUrl("payment/savepayment"),HttpMethod.POST,requestEntity,String.class);

			String responseBody = response.getBody();

			if (RestUtil.isError(response.getStatusCode())) {
				ErrorResource error = objectMapper.readValue(responseBody, ErrorResource.class);

				FacesUtil.error(error.getFieldErrors().get(0).getMessage());
				return null;

			} else {
				PaymentDetailDto result = objectMapper.readValue(responseBody, PaymentDetailDto.class);
				FacesUtil.info("Payment has been saved.");

			}
		}
		catch(Exception e){
			log.error("saveProject", e);

		}
		showPaymentList();
		return "/pages/payment/paymentlist.xhtml";
	}
	

}
