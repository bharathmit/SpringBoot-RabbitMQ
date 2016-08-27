
package com.cable.rest.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.extern.log4j.Log4j;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cable.rest.dto.ConnectionAccountDto;
import com.cable.rest.exception.RestException;
import com.cable.rest.model.ConnectionAccount;
import com.cable.rest.repository.ConnectionAccountJPARepo;
import com.cable.rest.response.ErrorCodeDescription;
import com.cable.rest.response.ResponseResource;
import com.cable.rest.search.AccountSearch;
import com.cable.rest.utils.ModelEntityMapper;
import com.cable.rest.utils.RandomUtil;

@Service
@Log4j
public class ConnectionAccountService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    ConnectionAccountJPARepo accountRepo;
    
    @Autowired
    RandomUtil randomUtil;

    @Transactional
    public ConnectionAccountDto saveAccount(ConnectionAccountDto Object) {
        try {
        	
        	if(StringUtils.isEmpty(Object.getAccountToken())){
        		Object.setAccountToken(randomUtil.getTrackId());
        	}
        	
            ConnectionAccount accountEntity = (ConnectionAccount) ModelEntityMapper.converObjectToPoJo(Object, ConnectionAccount.class);
            accountRepo.saveAndFlush(accountEntity);
            Object.setAccountId(accountEntity.getAccountId());
        } catch (Exception e) {
            log.error("saveAccount", e);
            throw new RestException(ErrorCodeDescription.TRANSACTION_FAILED);
        }
        return Object;
    }

    @Transactional
    public List<ConnectionAccountDto> getAccount(AccountSearch search) {
        try {

            List<ConnectionAccountDto> accountList = new ArrayList<ConnectionAccountDto>();
            Session session = entityManager.unwrap(Session.class);

            Criteria criteria = session.createCriteria(ConnectionAccount.class);

            List<ConnectionAccount> list = criteria.list();

            accountList = (List<ConnectionAccountDto>) ModelEntityMapper.convertListToCollection(list);

            return accountList;
        } catch (Exception e) {
            throw new RestException(ErrorCodeDescription.DATA_ACCESS);
        }
    }

    @Transactional
    public ResponseResource deleteZipCode(AccountSearch search) {
        try {

            //accountRepo.delete(search.);
            return new ResponseResource(ErrorCodeDescription.TRANSACTION_SUCCESS);
        } catch (DataIntegrityViolationException e) {
            throw new RestException(ErrorCodeDescription.TRANSACTION_FAILED);
        }
    }

}
