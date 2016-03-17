package com.cable.rest.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cable.rest.dto.PaymentDetailDto;
import com.cable.rest.exception.RestException;
import com.cable.rest.model.PaymentDetails;
import com.cable.rest.model.ZipCode;
import com.cable.rest.repository.PaymentDetailJPARepo;
import com.cable.rest.response.ErrorCodeDescription;
import com.cable.rest.utils.ModelEntityMapper;

@Service
@Log4j
public class PaymentService {

	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	PaymentDetailJPARepo paymentDetailJPARepo;
	
	public PaymentDetailDto savePayment(PaymentDetailDto Object){
		try{
			
			PaymentDetails paymentEntity=(PaymentDetails) ModelEntityMapper.converObjectToPoJo(Object, PaymentDetailDto.class);
			paymentDetailJPARepo.saveAndFlush(paymentEntity);
		}
		catch(Exception e){
			log.error("savePayment", e);
			throw new RestException(ErrorCodeDescription.TRANSACTION_FAILED);
		}
		return Object;
	}
	
}
