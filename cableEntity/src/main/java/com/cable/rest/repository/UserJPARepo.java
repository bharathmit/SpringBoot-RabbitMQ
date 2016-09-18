package com.cable.rest.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cable.rest.model.User;




public interface UserJPARepo extends JpaRepository< User, Long>{
	
	public User findByLoginId(String loginId);
	
	public User findByMobile(String mobile);
	
	@Modifying(clearAutomatically = true)
	@Query("update User a SET a.lastLoginDate=:lastLoginDate  where a.userId = :userId")
    public int loginUpdate(@Param("userId") Long userId,@Param("lastLoginDate") Date lastLoginDate);
	
	@Modifying(clearAutomatically = true)
	@Query("update User a SET a.password=:password  where a.userId = :userId")
    public int passwordUpdate(@Param("userId") Long userId,@Param("password") String password);


}
