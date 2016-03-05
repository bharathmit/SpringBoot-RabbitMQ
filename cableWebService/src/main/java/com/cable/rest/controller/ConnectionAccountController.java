package com.cable.rest.controller;

import javax.validation.Valid;

import lombok.extern.log4j.Log4j;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cable.rest.dto.ConnectionAccountDto;
import com.cable.rest.exception.InvalidRequestException;
import com.cable.rest.search.AccountSearch;

@RestController
@RequestMapping("/account")
@Log4j
public class ConnectionAccountController extends BaseController {
	
	@RequestMapping(value="/saveAccount",method=RequestMethod.POST)
	public Object  saveAccount(@RequestBody @Valid ConnectionAccountDto reqObject,BindingResult bindingResult){
		if (bindingResult.hasErrors()){
			throw new InvalidRequestException("Exception", bindingResult);
		}
		return (Object) sendtoMQ(reqObject, "saveAccount", "connectionAccountService");
	}
	
	@RequestMapping(value="/accountlist",method=RequestMethod.POST)
	public Object getAccount(@RequestBody AccountSearch searchObject){
		return sendtoMQ(searchObject, "getAccount", "connectionAccountService");
	}
	
	@RequestMapping(value="/deleteAccount",method=RequestMethod.POST)
	public Object deleteAccount(@RequestBody AccountSearch searchObject){
		return sendtoMQ(searchObject, "deleteAccount", "connectionAccountService");
	}

}
