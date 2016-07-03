
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

import com.cable.rest.dto.OrganizationDto;
import com.cable.rest.dto.ProjectDto;
import com.cable.rest.exception.RestException;
import com.cable.rest.model.Organization;
import com.cable.rest.model.Project;
import com.cable.rest.repository.OrganizationJPARepo;
import com.cable.rest.repository.ProjectJPARepo;
import com.cable.rest.response.ErrorCodeDescription;
import com.cable.rest.response.ResponseResource;
import com.cable.rest.search.ProjectSearch;
import com.cable.rest.utils.ModelEntityMapper;

@Service
@Log4j
public class ProjectService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    OrganizationJPARepo organizationJPARepo;

    @Autowired
    ProjectJPARepo projectJPARepo;

    @Transactional
    public OrganizationDto saveOrganization(OrganizationDto reqObject) {
        try {
            Organization orgEntity = (Organization) ModelEntityMapper.converObjectToPoJo(reqObject, Organization.class);

            organizationJPARepo.saveAndFlush(orgEntity);

            reqObject.setOrgId(orgEntity.getOrgId());

        } catch (Exception e) {
            log.error("saveOrganization", e);

        }

        return reqObject;

    }

    @Transactional
    public List<OrganizationDto> getOrganization(ProjectSearch search) {
        try {

            List<OrganizationDto> orgDetails = new ArrayList<OrganizationDto>();
            Session session = entityManager.unwrap(Session.class);

            Criteria criteria = session.createCriteria(Organization.class);

            if (search.getOrgId() != null && search.getOrgId() > 0l) {
                criteria.add(Restrictions.eq("orgId", search.getOrgId()));
            }

            if (!StringUtils.isEmpty(search.getOrgName())) {
                criteria.add(Restrictions.eq("orgName", search.getOrgName()));
            }

            if (!StringUtils.isEmpty(search.getPinCode())) {
                criteria.add(Restrictions.eq("pinCode", search.getPinCode()));
            }
            List<Organization> list = criteria.list();
            //List<OrganizationDto> list=criteria.setResultTransformer(Transformers.aliasToBean(OrganizationDto.class)).list();

            for (Organization orgObject : list) {
                OrganizationDto orgDto = (OrganizationDto) ModelEntityMapper.converObjectToPoJo(orgObject, OrganizationDto.class);
                orgDetails.add(orgDto);
            }

            return orgDetails;

        } catch (Exception e) {
            throw new RestException(ErrorCodeDescription.DATA_ACCESS);
        }

    }

    @Transactional
    public ResponseResource deleteOrganization(ProjectSearch search) {
        try {
            organizationJPARepo.delete(search.getOrgId());
            return new ResponseResource(ErrorCodeDescription.TRANSACTION_SUCCESS);
        } catch (Exception e) {
            return null;
        }

    }

    @Transactional
    public ProjectDto saveProject(ProjectDto Object) {
        try {
            Project projectEntity = (Project) ModelEntityMapper.converObjectToPoJo(Object, Project.class);
            projectJPARepo.saveAndFlush(projectEntity);
            Object.setProjectId(projectEntity.getProjectId());
        } catch (Exception e) {
            log.error("saveProject", e);

        }
        return Object;
    }

    @Transactional
    public List<ProjectDto> getProject(ProjectSearch search) {
        try {

            List<ProjectDto> projectDetails = new ArrayList<ProjectDto>();
            Session session = entityManager.unwrap(Session.class);

            Criteria criteria = session.createCriteria(Project.class);

            if (search.getProjectId() != null && search.getProjectId() > 0l) {
                criteria.add(Restrictions.eq("projectId", search.getProjectId()));
            }

            if (!org.apache.commons.lang.StringUtils.isEmpty(search.getProjectCityVillage())) {
                criteria.add(Restrictions.eq("projectCityVillage", search.getProjectCityVillage()));
            }

            List<Project> list = criteria.list();

            for (Project projectObject : list) {
                ProjectDto projectDto = (ProjectDto) ModelEntityMapper.converObjectToPoJo(projectObject, ProjectDto.class);
                projectDetails.add(projectDto);
            }

            return projectDetails;
        } catch (Exception e) {
            throw new RestException(ErrorCodeDescription.DATA_ACCESS);
        }

    }

    @Transactional
    public ResponseResource deleteProject(ProjectSearch search) {
        try {

            projectJPARepo.delete(search.getProjectId());
            return new ResponseResource(ErrorCodeDescription.TRANSACTION_SUCCESS);
        } catch (Exception e) {
            throw new RestException(ErrorCodeDescription.TRANSACTION_FAILED);
        }

    }

}
