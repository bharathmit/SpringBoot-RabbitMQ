package com.cable.rest.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.extern.log4j.Log4j;

import org.jasypt.digest.StringDigester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.cable.rest.constants.Status;
import com.cable.rest.constants.TokenType;
import com.cable.rest.dto.CustomerDto;
import com.cable.rest.dto.EmailDto;
import com.cable.rest.exception.RestException;
import com.cable.rest.model.Customer;
import com.cable.rest.model.VerificationToken;
import com.cable.rest.repository.CustomerJPARepo;
import com.cable.rest.repository.VerificationTokenRepo;
import com.cable.rest.response.ErrorCodeDescription;
import com.cable.rest.response.ResponseResource;
import com.cable.rest.search.UserSearch;
import com.cable.rest.utils.ModelEntityMapper;


@Service
@Log4j
public class CustomerService {
	
	
	@Autowired
	EmailNotificationService emailService;
	
	@Autowired
	CustomerJPARepo customerRepo;
	
	@Autowired
    VerificationTokenRepo tokenRepository;
	
	@Autowired
	StringDigester stringDigester;
	
	
	public CustomerDto findCustomerID(String emailId){
		CustomerDto resultObject=new CustomerDto();
		
		Customer entityObject= customerRepo.findByEmailId(emailId.trim());
		
		if(StringUtils.isEmpty(entityObject)){
			log.info("Message" + ErrorCodeDescription.INVALID_USER.getErrorDescription());
			throw new RestException(ErrorCodeDescription.INVALID_USER);		
		}
		resultObject=(CustomerDto) ModelEntityMapper.converObjectToPoJo(entityObject, CustomerDto.class);
		return resultObject;
	}
	
	public boolean exitCustomerID(String emailId){
		if(StringUtils.isEmpty(customerRepo.findByEmailId(emailId.trim()))){
			log.info("Message" + ErrorCodeDescription.INVALID_USER.getErrorDescription());
			throw new RestException(ErrorCodeDescription.INVALID_USER);		
		}
		return true;
	}
	
	/** This Method no Used in present */ 
	public boolean exitMobile(String mobileNumber){
		Customer entityObject= customerRepo.findByMobile(mobileNumber.trim());
		
		if(!StringUtils.isEmpty(entityObject)){
			log.info("Message" + ErrorCodeDescription.USER_EXIT.getErrorDescription());
			throw new RestException(ErrorCodeDescription.USER_EXIT);
		}
		return true;	
	}
	
	@Transactional
	public CustomerDto saveCustomer(CustomerDto customerObject) {
		
		
		if(!exitCustomerID(customerObject.getEmailId())){
			return null;
		}
		
		if(!exitMobile(customerObject.getMobile())){
			return null;
		}
		
		/** Password Encry */
		customerObject.setPassword(stringDigester.digest(customerObject.getPassword()));
		
		System.out.println(customerObject.getPassword());
		
		Customer customerEntity = (Customer) ModelEntityMapper.converObjectToPoJo(customerObject, Customer.class);
		customerRepo.save(customerEntity);
		
		emailRegistrationToken(customerEntity);
		
		return customerObject;

	}
	
	
	public boolean emailRegistrationToken(Customer customerEntity){
		String token = UUID.randomUUID().toString();
		createRegistrationTokenForCustomer(customerEntity,token);
		
		
		EmailDto emailRequest=new EmailDto();
		
		emailRequest.setTo(customerEntity.getEmailId());
		emailRequest.setSubject("Welcome Email");
		emailRequest.setTemplateLocation("templates/welcomeEmail.vm");
		
		Map model=new HashMap();
		String appUrl = "http://localhost:8095/customer/regitrationConfirm?token=" + token;;
		model.put("api", appUrl);
		
		emailRequest.setModel(model);
		
		
		
		return emailService.constructEmailMessage(emailRequest);
	}
	
	public CustomerDto resendRegistrationToken(CustomerDto customerObject){
		Customer customerEntity = (Customer) ModelEntityMapper.converObjectToPoJo(customerObject, Customer.class);
		emailRegistrationToken(customerEntity);
		
		return customerObject;
	}
	
	
	public void createRegistrationTokenForCustomer(final Customer customer, final String token) {
        final VerificationToken myToken = new VerificationToken(token, customer,TokenType.ConfirmRegistration);
        tokenRepository.save(myToken);
    }
	
	public VerificationToken getVerificationToken(String VerificationToken){
		return tokenRepository.findByToken(VerificationToken);
	}
	
	public ResponseResource confirmRegistration(String token){
		 final VerificationToken verificationToken = getVerificationToken(token);
	        if (verificationToken == null) {
	        	return new ResponseResource(ErrorCodeDescription.INVALID_TOKEN);
	        }

	        final Customer customer = verificationToken.getCustomer();
	        final Calendar cal = Calendar.getInstance();
	        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
	        	return new ResponseResource(ErrorCodeDescription.TIME_OUT);
	        }

	        customer.setStatus(Status.Active);
	        customerRepo.saveAndFlush(customer);
	        return new ResponseResource(ErrorCodeDescription.TRANSACTION_SUCCESS);
	}
	
	
	
	public List<CustomerDto> getCustomer(UserSearch search) {
		return null;
	}
	
	public boolean deleteCustomer() {
		return false;
	}
	
	
	public CustomerDto forgotPassword(CustomerDto customerObject){
		Customer customerEntity = (Customer) ModelEntityMapper.converObjectToPoJo(customerObject, Customer.class);
	
		emailForgotPasswordToken(customerEntity);
		
		return customerObject;
	}
	
	
	public CustomerDto confirmPassword(String token){
		 final VerificationToken verificationToken = getVerificationToken(token);
	        if (verificationToken == null) {
	        	throw new RestException(ErrorCodeDescription.INVALID_TOKEN);		
	        }

	        final Customer customer = verificationToken.getCustomer();
	        final Calendar cal = Calendar.getInstance();
	        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
	        	throw new RestException(ErrorCodeDescription.TIME_OUT);	
	        }
	        
	        return (CustomerDto) ModelEntityMapper.converObjectToPoJo(customer, CustomerDto.class);
	        
	}
	
	public CustomerDto changePassword(CustomerDto customerObject){
		
		//verify with old password
		stringDigester.matches(customerObject.getPassword(),
				customerObject.getPassword());
		
		Customer customerEntity = (Customer) ModelEntityMapper.converObjectToPoJo(customerObject, Customer.class);
		customerRepo.save(customerEntity);
		return customerObject;
	}
	
	
	public boolean emailForgotPasswordToken(Customer customerEntity){
		String token = UUID.randomUUID().toString();
		createForgotPasswordTokenForCustomer(customerEntity,token);
		
		
		EmailDto emailRequest=new EmailDto();
		
		emailRequest.setTo(customerEntity.getEmailId());
		emailRequest.setSubject("Welcome Email");
		emailRequest.setTemplateLocation("templates/welcomeEmail.vm");
		
		Map model=new HashMap();
		String appUrl = "http://localhost:8095/customer/passwordConfirm?token=" + token;;
		model.put("api", appUrl);
		
		emailRequest.setModel(model);
		
		
		
		return emailService.constructEmailMessage(emailRequest);
	}
	
	
	public void createForgotPasswordTokenForCustomer(final Customer customer, final String token) {
		final VerificationToken myToken = new VerificationToken(token, customer,TokenType.ForgotPassword);
        tokenRepository.save(myToken);
    }
	
	
	
	
	
	
	
	
	
}
