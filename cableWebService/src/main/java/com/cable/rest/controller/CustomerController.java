package com.cable.rest.controller;

import javax.validation.Valid;

import lombok.extern.log4j.Log4j;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cable.rest.dto.CustomerDto;
import com.cable.rest.exception.InvalidRequestException;

@RestController
@RequestMapping("/customer")
@Log4j
public class CustomerController extends BaseController {
	
	
	@RequestMapping(value="/savecustomer",method=RequestMethod.POST)
	public Object  saveCustomer(@RequestBody @Valid CustomerDto reqObject,BindingResult bindingResult){
		if (bindingResult.hasErrors()){
			throw new InvalidRequestException("Exception", bindingResult);
		}
		return sendtoMQ(reqObject, "saveCustomer", "customerService");
	}
	
	@RequestMapping(value = "/regitrationConfirm", method = RequestMethod.GET)
    public Object confirmRegistration(@RequestParam("token") final String token) {
		return sendtoMQ(token, "confirmRegistration", "customerService");
	}
	
	
	@RequestMapping(value="/resendRegistrationToken",method=RequestMethod.POST)
	public Object  resendRegistrationToken(@RequestBody @Valid CustomerDto reqObject,BindingResult bindingResult){
		if (bindingResult.hasErrors()){
			throw new InvalidRequestException("Exception", bindingResult);
		}
		return sendtoMQ(reqObject, "resendRegistrationToken", "customerService");
	}
	
	@RequestMapping(value="/forgotPassword",method=RequestMethod.POST)
	public Object  forgotPassword(@RequestBody @Valid CustomerDto reqObject,BindingResult bindingResult){
		if (bindingResult.hasErrors()){
			throw new InvalidRequestException("Exception", bindingResult);
		}
		return sendtoMQ(reqObject, "forgotPassword", "customerService");
	}
	
	@RequestMapping(value = "/passwordConfirm", method = RequestMethod.GET)
    public Object confirmPassword(@RequestParam("token") final String token) {
		return sendtoMQ(token, "confirmPassword", "customerService");
	}
	
	
	@RequestMapping(value="/changePassword",method=RequestMethod.POST)
	public Object  changePassword(@RequestBody @Valid CustomerDto reqObject,BindingResult bindingResult){
		if (bindingResult.hasErrors()){
			throw new InvalidRequestException("Exception", bindingResult);
		}
		return sendtoMQ(reqObject, "changePassword", "customerService");
	}
	
	
	
	
	

}
