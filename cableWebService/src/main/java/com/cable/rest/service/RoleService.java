
package com.cable.rest.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.extern.log4j.Log4j;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cable.rest.dto.RoleDto;
import com.cable.rest.exception.RestException;
import com.cable.rest.model.Role;
import com.cable.rest.repository.RoleJPARepo;
import com.cable.rest.response.ErrorCodeDescription;
import com.cable.rest.response.ResponseResource;
import com.cable.rest.search.UserSearch;
import com.cable.rest.utils.ModelEntityMapper;

@Service
@Log4j
public class RoleService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    RoleJPARepo roleRepo;

    @Transactional
    public RoleDto saveRole(RoleDto Object) {
        try {
            Role roleEntity = (Role) ModelEntityMapper.converObjectToPoJo(Object, Role.class);
            roleEntity = roleRepo.save(roleEntity);
            Object.setRoleId(roleEntity.getRoleId());
            return Object;
        } catch (Exception e) {
            log.info("saveRole Message : " + ErrorCodeDescription.TRANSACTION_SUCCESS.getErrorDescription());
            throw new RestException(ErrorCodeDescription.TRANSACTION_SUCCESS);
        }
    }

    @Transactional
    public List<RoleDto> getRole(UserSearch search) {
        try {
            List<RoleDto> roleList = new ArrayList<RoleDto>();
            Session session = entityManager.unwrap(Session.class);

            Criteria criteria = session.createCriteria(Role.class);

            if (search.getRoleId() != null && search.getRoleId() > 0l) {
                criteria.add(Restrictions.eq("roleId", search.getRoleId()));
            }

            List<Role> list = criteria.list();

            for (Role entityObject : list) {
                RoleDto roleDto = (RoleDto) ModelEntityMapper.converObjectToPoJo(entityObject, RoleDto.class);
                roleList.add(roleDto);
            }

            return roleList;
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public ResponseResource deleteRole(UserSearch search) {
        try {

            roleRepo.delete(search.getRoleId());
            return new ResponseResource(ErrorCodeDescription.TRANSACTION_SUCCESS);
        } catch (Exception e) {
            throw new RestException(ErrorCodeDescription.TRANSACTION_FAILED);
        }

    }

}
