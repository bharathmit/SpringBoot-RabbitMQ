package com.cable.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cable.rest.model.ConnectionAccount;


public interface ConnectionAccountJPARepo extends JpaRepository<ConnectionAccount , Long>{
	
	@Modifying(clearAutomatically = true)
	@Query("update ConnectionAccount a SET a.payGenMonth=:payGenMonth  where a.accountId = :accountId")
    public int updatePayGenMonth(@Param("accountId") Long accountId,@Param("payGenMonth") Integer payGenMonth);

}
