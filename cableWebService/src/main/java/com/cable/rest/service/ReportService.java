package com.cable.rest.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.extern.log4j.Log4j;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.cable.rest.constants.ReportType;
import com.cable.rest.dto.ConnectionAccountDto;
import com.cable.rest.dto.OrganizationDto;
import com.cable.rest.dto.ReportDto;
import com.cable.rest.dto.UserDto;
import com.cable.rest.exception.RestException;
import com.cable.rest.model.Organization;
import com.cable.rest.model.Report;
import com.cable.rest.model.User;
import com.cable.rest.repository.ReportJPARepo;
import com.cable.rest.response.ErrorCodeDescription;
import com.cable.rest.search.ProjectSearch;
import com.cable.rest.utils.ModelEntityMapper;

@Service
@Log4j
public class ReportService {

	@PersistenceContext
    private EntityManager entityManager;
	
	@Autowired
	ReportJPARepo reportJPARepo;
	
	
	
    public List<ReportDto> getReports(ReportType reportType) {
        try {

            List<ReportDto> reportLists = new ArrayList<ReportDto>();
            Session session = entityManager.unwrap(Session.class);

            Criteria criteria = session.createCriteria(Report.class);

            criteria.add(Restrictions.eq("reportType", reportType));
            
            List<Report> list = criteria.list();
            
            for (Report entityObject : list) {
            	ReportDto reportDto = (ReportDto) ModelEntityMapper.converObjectToPoJo(entityObject, ReportDto.class);
                reportLists.add(reportDto);
            }

            return reportLists;

        } catch (Exception e) {
            throw new RestException(ErrorCodeDescription.DATA_ACCESS);
        }

    }
	
}
