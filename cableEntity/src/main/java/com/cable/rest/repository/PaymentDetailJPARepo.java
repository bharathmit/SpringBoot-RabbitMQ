package com.cable.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cable.rest.model.PaymentDetails;

public interface PaymentDetailJPARepo extends JpaRepository<PaymentDetails , Long>{

}
