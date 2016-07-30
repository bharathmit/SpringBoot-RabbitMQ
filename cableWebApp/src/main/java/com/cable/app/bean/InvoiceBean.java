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
import com.cable.rest.constants.PaymentStatus;
import com.cable.rest.constants.PaymentType;
import com.cable.rest.dto.GeneratePaymentDto;
import com.cable.rest.dto.OrganizationDto;
import com.cable.rest.dto.PaymentDetailDto;
import com.cable.rest.dto.ProjectDto;
import com.cable.rest.response.ErrorResource;
import com.cable.rest.search.PaymentSearch;
import com.cable.rest.search.ProjectSearch;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@ManagedBean
@SessionScoped
@Log4j
public class InvoiceBean {
	
	@ManagedProperty(value="#{restTemplate}")
	@Getter @Setter
	RestTemplate restTemplate;

	@ManagedProperty(value="#{restClient}")
	@Getter @Setter
	RestClient restClient;

	@ManagedProperty(value="#{objectMapper}")
	@Getter @Setter
	ObjectMapper objectMapper;
	
	@ManagedProperty(value="#{applicationBean}")
	@Getter @Setter
	ApplicationBean applicationBean;
	
	@Getter @Setter
	PaymentSearch paymentSearch=new PaymentSearch();
	
	@Getter @Setter
	List<GeneratePaymentDto> invoiceList=new ArrayList<GeneratePaymentDto>();
	
	@Getter @Setter
	GeneratePaymentDto invoicSelected=new GeneratePaymentDto();
	
	@Getter @Setter
	PaymentDetailDto paymentSelected=new PaymentDetailDto();
	
	@Setter @Getter
	String action;
	
	@Getter @Setter
	int numberOfRecords;
	
	public String showInvoiceList(){
		
		paymentSelected=new PaymentDetailDto();
		invoicSelected=new GeneratePaymentDto();
		numberOfRecords = 0;
		
		try{
           
			HttpEntity<PaymentSearch> requestEntity = new HttpEntity<PaymentSearch>(paymentSearch, LoginBean.header);

			ResponseEntity<String> response = restTemplate.exchange(restClient.createUrl("payment/invoicelist"),HttpMethod.POST,requestEntity,String.class);

			String responseBody = response.getBody();

			if (RestUtil.isError(response.getStatusCode())) {
				ErrorResource error = objectMapper.readValue(responseBody, ErrorResource.class);

				FacesUtil.warn(error.getFieldErrors().get(0).getMessage());
				return "";

			} else {
				invoiceList = objectMapper.readValue(responseBody, new TypeReference<List<GeneratePaymentDto>>(){});
				if(invoiceList != null){
					numberOfRecords = invoiceList.size();
				}

			}

		}
		catch(Exception e){
			log.error("showInvoiceList", e);
		}
		paymentSearch = new PaymentSearch();
		return "/pages/payment/invoice.xhtml";
		
	}
	
	public void clearSearch(){
		paymentSearch = new PaymentSearch();
	}
	
	public String savePayment(){
		try{
			
			paymentSelected.setGeneratepayment(invoicSelected);
			paymentSelected.setPaymentAmount(invoicSelected.getBillAmount());
			paymentSelected.setPaymentCustomer(invoicSelected.getConnectionAccount());
			paymentSelected.setPaymentStatus(PaymentStatus.PAYED);
			paymentSelected.setPaymentType(PaymentType.CASH);
			paymentSelected.setPaymentUser(applicationBean.getUserContext().getUserId());
			
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
			log.error("savePayment", e);

		}
		showInvoiceList();
		return "";
	}
	
	
	
	
	
	
	

}
