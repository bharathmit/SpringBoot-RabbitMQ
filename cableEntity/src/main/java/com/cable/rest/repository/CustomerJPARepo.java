package com.cable.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cable.rest.model.Customer;
import com.cable.rest.model.User;



public interface CustomerJPARepo extends JpaRepository< Customer, Long>{
	
	public Customer findByEmailId(String emailId);
	
	public Customer findByMobile(String mobile);
	

}
