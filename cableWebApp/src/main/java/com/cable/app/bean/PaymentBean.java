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
	
	@Setter @Getter
	String action;
	
	@Getter @Setter
	int numberOfRecords;
	
	
	public String showPaymentList(){
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
		return "/pages/payment/payment.xhtml";
		
	}
	
	public void clearSearch(){
		paymentSearch = new PaymentSearch();
	}
	
	

}
