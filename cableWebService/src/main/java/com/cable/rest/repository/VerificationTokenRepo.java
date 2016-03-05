package com.cable.rest.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.cable.rest.model.Customer;
import com.cable.rest.model.VerificationToken;

public interface VerificationTokenRepo extends JpaRepository<VerificationToken, Long> {

    public VerificationToken findByToken(String token);

    public VerificationToken findByCustomer(Customer customer);
}
