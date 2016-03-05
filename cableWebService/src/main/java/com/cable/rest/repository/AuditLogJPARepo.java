package com.cable.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cable.rest.model.AuditLog;

public interface AuditLogJPARepo extends JpaRepository<AuditLog , Long> {

}
