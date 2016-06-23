package com.cable.rest.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.extern.log4j.Log4j;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.cable.rest.dto.PaymentDetailDto;
import com.cable.rest.exception.RestException;
import com.cable.rest.model.PaymentDetails;
import com.cable.rest.repository.PaymentDetailJPARepo;
import com.cable.rest.response.ErrorCodeDescription;
import com.cable.rest.response.ResponseResource;
import com.cable.rest.search.PaymentSearch;
import com.cable.rest.utils.ModelEntityMapper;

@Service
@Log4j
public class PaymentService {

	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	PaymentDetailJPARepo paymentDetailJPARepo;
	
	@Transactional
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
	
	@Transactional
	@Rollback(false)
	public List<PaymentDetailDto> getPaymentList(PaymentSearch search) {
		try{
			
			List<PaymentDetailDto> paymentList=new ArrayList<PaymentDetailDto>();
			Session session = entityManager.unwrap(Session.class);
			
			Criteria criteria= session.createCriteria(PaymentDetails.class);
			
			/*if(search.getOrgId() !=null && search.getOrgId() >0l){
				criteria.add(Restrictions.eq("orgId", search.getOrgId()));
			}
			
			if(!StringUtils.isEmpty(search.getOrgName())){
				criteria.add(Restrictions.eq("orgName", search.getOrgName()));
			}
			
			if(!StringUtils.isEmpty(search.getPinCode())){
				criteria.add(Restrictions.eq("pinCode", search.getPinCode()));
			}*/
			List<PaymentDetails> list=criteria.list();
			
			paymentList=criteria.setResultTransformer(Transformers.aliasToBean(PaymentDetailDto.class)).list();
			
			return paymentList;
			
		}
		catch(Exception e){
			throw new RestException(ErrorCodeDescription.DATA_ACCESS);
		}
		
	}
	
	@Transactional
	public ResponseResource deletePayment(PaymentSearch search) {
		try{
			//paymentDetailJPARepo.delete(search.getOrgId());
			return new ResponseResource(ErrorCodeDescription.TRANSACTION_SUCCESS);
		}
		catch(Exception e){
			throw new RestException(ErrorCodeDescription.DATA_ACCESS);
		}
		
	}
	
}
