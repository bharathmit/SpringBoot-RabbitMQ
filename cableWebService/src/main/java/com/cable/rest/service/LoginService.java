package com.cable.rest.service;

import lombok.extern.log4j.Log4j;

import org.jasypt.digest.StringDigester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cable.rest.dto.CustomerDto;
import com.cable.rest.dto.LoginDto;
import com.cable.rest.dto.LoginResponseDto;
import com.cable.rest.dto.UserDto;
import com.cable.rest.exception.RestException;
import com.cable.rest.response.ErrorCodeDescription;



@Service
@Log4j
public class LoginService {
	
	
	@Autowired
	UserService userService;
	
	@Autowired
	StringDigester stringDigester;
	
	
	public LoginResponseDto loginUser(LoginDto login){
		log.info("LoginService.loginUser method call");
		LoginResponseDto loginResponseDto = new LoginResponseDto();
		UserDto userDto = userService.findLoginId(login.getLoginId());

		if (StringUtils.isEmpty(userDto)) {
			return null;
		}

		log.info("Check the Password");
		boolean result = stringDigester.matches(login.getPassword(),
				userDto.getPassword());
		// Assign the user details to LoginResponseDto
		if (result == true) {
			log.info("Session Id Set");
			loginResponseDto.setAuthenticationStatus(true);
			loginResponseDto.setUser(userDto);
			
			log.info("LoginService.loginUser method end");
			return loginResponseDto;
		} else {
			log.info("Message" + ErrorCodeDescription.INVALID_PASSWORD.getErrorDescription());
			throw new RestException(ErrorCodeDescription.INVALID_PASSWORD);		
		}
	}
	
	
	public CustomerDto loginCustomer(){
		return null;
	}
	
	
	

}
