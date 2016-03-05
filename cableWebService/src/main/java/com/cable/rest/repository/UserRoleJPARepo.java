package com.cable.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cable.rest.model.UserRole;



public interface UserRoleJPARepo extends JpaRepository< UserRole, Long>{

}
