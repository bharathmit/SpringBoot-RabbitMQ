package com.cable.rest.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.extern.log4j.Log4j;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.jasypt.digest.StringDigester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.cable.rest.dto.UserDto;
import com.cable.rest.dto.UserRoleDto;
import com.cable.rest.exception.RestException;
import com.cable.rest.model.User;
import com.cable.rest.model.UserRole;
import com.cable.rest.repository.UserJPARepo;
import com.cable.rest.repository.UserRoleJPARepo;
import com.cable.rest.response.ErrorCodeDescription;
import com.cable.rest.response.ResponseResource;
import com.cable.rest.search.UserSearch;
import com.cable.rest.utils.ModelEntityMapper;



@Service
@Log4j
public class UserService {
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	UserJPARepo userRepo;
	
	@Autowired
	UserRoleJPARepo userRoleRepo;
	
	@Autowired
	StringDigester stringDigester;
	
	public UserDto findLoginId(String loginId){
		UserDto resultObject=new UserDto();
		
		User entityObject= userRepo.findByLoginId(loginId.trim());
		
		if(StringUtils.isEmpty(entityObject)){
			log.info("Message" + ErrorCodeDescription.INVALID_USER.getErrorDescription());
			throw new RestException(ErrorCodeDescription.INVALID_USER);		
		}
		resultObject=(UserDto) ModelEntityMapper.converObjectToPoJo(entityObject, UserDto.class);
		return resultObject;
	}
	
	
	public boolean exitLoginId(String loginId){
		
		User entityObject= userRepo.findByLoginId(loginId.trim());
		
		if(!StringUtils.isEmpty(entityObject)){
			log.info("Message" + ErrorCodeDescription.USER_EXIT.getErrorDescription());
			throw new RestException(ErrorCodeDescription.USER_EXIT);		
		}
		
		return true;
	}
	
	/** This Method no Used in present */ 
	public boolean exitMobile(String mobileNumber){
		User entityObject= userRepo.findByMobile(mobileNumber.trim());
		
		if(!StringUtils.isEmpty(entityObject)){
			log.info("Message" + ErrorCodeDescription.USER_EXIT.getErrorDescription());
			throw new RestException(ErrorCodeDescription.USER_EXIT);
		}
		return true;	
	}
	
	
	
	@Transactional
	public UserDto saveUser(UserDto userObject) {
		
		if(!exitLoginId(userObject.getLoginId())){
			return null;
		}
		
		/** Password Encry */
		userObject.setPassword(stringDigester.digest(userObject.getPassword()));
		
		System.out.println(userObject.getPassword());
				
		User userEntity=(User) ModelEntityMapper.converObjectToPoJo(userObject, User.class);
		
		userEntity=userRepo.save(userEntity);
		
		userObject.setUserId(userEntity.getUserId());
		
		if(!deleteUserRole(userObject.getUserId())){
			return null;
		}
		
		for(UserRoleDto roleUser : userObject.getUserRoles()){
			
			UserRole userRoleEntity=(UserRole) ModelEntityMapper.converObjectToPoJo(roleUser, UserRole.class);
			
			userRoleEntity.setUser(userEntity);
			
			
			userRoleRepo.save(userRoleEntity);
		}
		return userObject;
	}
	
	
	@Transactional
	public boolean deleteUserRole(Long userId){
		Session session =(Session) entityManager.getDelegate();
		String hsql=" delete from UserRole where user_Id= "+userId+" ";
		
		int row=session.createQuery(hsql).executeUpdate();
		
		log.info("Number of User Role Delete from DB == "+row);
		return true;
	} 
	
	
	
	@Transactional
	@Rollback(false)
	public List<UserDto> getUser(UserSearch search){
		try{
			List<UserDto> userList=new ArrayList<UserDto>();
			Session session = entityManager.unwrap(Session.class);
			
			Criteria criteria= session.createCriteria(User.class);
			
			if(search.getUserId()!=null && search.getUserId() >0l){
				criteria.add(Restrictions.eq("userId", search.getUserId()));
			}
			
			List<User> list=criteria.list();
			
			for( User entityObject: list){
				UserDto userDto=(UserDto) ModelEntityMapper.converObjectToPoJo(entityObject, UserDto.class);
				userList.add(userDto);
			}
			
			return userList;
		}
		catch(Exception e){
			return null;
		}
	}
	
	@Transactional
	public ResponseResource deleteUser(UserSearch search) {
		try{
			
			userRepo.delete(search.getUserId());
			return new ResponseResource(ErrorCodeDescription.TRANSACTION_SUCCESS);
		}
		catch(Exception e){
			throw new RestException(ErrorCodeDescription.TRANSACTION_FAILED);
		}
	}
	

}
