package com.cable.rest.interceptor;

import java.util.Date;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.sql.DataSource;

import lombok.extern.log4j.Log4j;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cable.rest.config.SpringApplicationContext;
import com.cable.rest.dto.UserDto;

@Service
@Log4j
public class LogListener {
	

	
	//@PostPersist
    private void logPersist(Object object) {
		log.info("LogListener.logPersist method call");
		if(object instanceof IAuditLog){
			IAuditLog entity = (IAuditLog) object ;
			
			DataSource dataSource = SpringApplicationContext.getBean(DataSource.class);
			
			UserDto userAccountModel = (UserDto) SecurityContextHolder
					.getContext().getAuthentication().getCredentials();
			
			String query = "insert into auditlog (entity_id, entity_name, action_name, detail, created_by, created_date) "
					+ " values (?,?,?,?,?,?)";
	         
	        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	         
	        Object[] args = new Object[] {entity.getEntityId(), entity.getClass().toString(), "INSERT" ,entity.getLogDeatil(),
	        		userAccountModel.getUserId(), new Date() };
	         
	        int out = jdbcTemplate.update(query, args);
	         
	        if(out !=0){
	        	log.info("LogListener.logPersist method save Successfully");
	        }
	        else 
	        	log.info("LogListener.logPersist method save failed");
			
		}
		
		
    }
	
	@PostUpdate
    private void logUpdate(Object object) {
		
		log.info("LogListener.logUpdate method call");
		
		if(object instanceof IAuditLog){
			IAuditLog entity = (IAuditLog) object ;
			
			DataSource dataSource = SpringApplicationContext.getBean(DataSource.class);
			
			UserDto userAccountModel = (UserDto) SecurityContextHolder
					.getContext().getAuthentication().getCredentials();
			
			String query = "insert into auditlog (entity_id, entity_name, action_name, detail, created_by, created_date) "
					+ " values (?,?,?,?,?,?)";
	         
	        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	         
	        Object[] args = new Object[] {entity.getEntityId(), entity.getClass().toString(), "UPDATE" ,entity.getLogDeatil(),
	        		userAccountModel.getUserId(), new Date() };
	         
	        int out = jdbcTemplate.update(query, args);
	         
	        if(out !=0){
	        	log.info("LogListener.logUpdate method save Successfully");
	        }
	        else 
	        	log.info("LogListener.logUpdate method save failed");
			
		}
    }

	
}
