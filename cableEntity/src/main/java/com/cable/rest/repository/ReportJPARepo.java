package com.cable.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cable.rest.model.Report;

public interface ReportJPARepo extends JpaRepository< Report, Long>{

}
