
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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cable.rest.dto.AreaDto;
import com.cable.rest.dto.StreetDto;
import com.cable.rest.dto.ZipCodeDto;
import com.cable.rest.exception.RestException;
import com.cable.rest.model.Area;
import com.cable.rest.model.Street;
import com.cable.rest.model.ZipCode;
import com.cable.rest.repository.AreaJPARepo;
import com.cable.rest.repository.StreetJPARepo;
import com.cable.rest.repository.ZipCodeJPARepo;
import com.cable.rest.response.ErrorCodeDescription;
import com.cable.rest.response.ResponseResource;
import com.cable.rest.search.MasterSearch;
import com.cable.rest.utils.ModelEntityMapper;

@Service
@Log4j
public class MasterService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    ZipCodeJPARepo zipCodeRepo;

    @Autowired
    AreaJPARepo areaRepo;

    @Autowired
    StreetJPARepo streetRepo;

    @Transactional
    @CacheEvict(value = "zipcode", allEntries = true, beforeInvocation = true)
    public ZipCodeDto saveZipCode(ZipCodeDto Object) {
        try {

            ZipCode zipCodeEntity = (ZipCode) ModelEntityMapper.converObjectToPoJo(Object, ZipCode.class);
            zipCodeRepo.saveAndFlush(zipCodeEntity);
            Object.setZipCode(zipCodeEntity.getZipCode());
        } catch (Exception e) {
            log.error("saveZipCode", e);
            throw new RestException(ErrorCodeDescription.TRANSACTION_FAILED);
        }
        return Object;
    }

    @Transactional
    @Cacheable("zipcode")
    public List<ZipCodeDto> getZipCode(MasterSearch search) {
        try {

            List<ZipCodeDto> zipcodeList = new ArrayList<ZipCodeDto>();
            Session session = entityManager.unwrap(Session.class);

            Criteria criteria = session.createCriteria(ZipCode.class);

            if (search.getZipCode() != null && search.getZipCode() > 0l) {
                criteria.add(Restrictions.eq("zipCode", search.getZipCode()));
            }

            if (!StringUtils.isEmpty(search.getLocationName())) {
                criteria.add(Restrictions.eq("locationName", search.getLocationName()));
            }

            List<ZipCode> list = criteria.list();

            zipcodeList = (List<ZipCodeDto>) ModelEntityMapper.convertListToCollection(list);

            return zipcodeList;
        } catch (Exception e) {
            throw new RestException(ErrorCodeDescription.DATA_ACCESS);
        }
    }

    @Transactional
    public ResponseResource deleteZipCode(MasterSearch search) {
        try {

            zipCodeRepo.delete(search.getZipCode());
            return new ResponseResource(ErrorCodeDescription.TRANSACTION_SUCCESS);
        } catch (DataIntegrityViolationException e) {
            throw new RestException(ErrorCodeDescription.TRANSACTION_FAILED);
        }
    }

    @Transactional
    public AreaDto saveArea(AreaDto Object) {
        try {
            Area areaEntity = (Area) ModelEntityMapper.converObjectToPoJo(Object, Area.class);
            areaRepo.save(areaEntity);
            Object.setAreaId(areaEntity.getAreaId());
            return Object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Transactional
    public List<AreaDto> getArea(MasterSearch search) {
        try {

            List<AreaDto> areaList = new ArrayList<AreaDto>();
            Session session = entityManager.unwrap(Session.class);

            Criteria criteria = session.createCriteria(Area.class);

            if (search.getAreaId() != null && search.getAreaId() > 0l) {
                criteria.add(Restrictions.eq("areaId", search.getAreaId()));
            }

            List<Area> list = criteria.list();

            for (Area entityObject : list) {
                AreaDto areaDto = (AreaDto) ModelEntityMapper.converObjectToPoJo(entityObject, AreaDto.class);
                areaList.add(areaDto);
            }

            return areaList;
        } catch (Exception e) {
            throw new RestException(ErrorCodeDescription.DATA_ACCESS);
        }
    }

    @Transactional
    public ResponseResource deleteArea(MasterSearch search) {
        try {

            areaRepo.delete(search.getAreaId());
            return new ResponseResource(ErrorCodeDescription.TRANSACTION_SUCCESS);
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public StreetDto saveStreet(StreetDto Object) {
        try {
            Street streetEntity = (Street) ModelEntityMapper.converObjectToPoJo(Object, Street.class);
            streetRepo.save(streetEntity);
            Object.setStreetId(streetEntity.getStreetId());
            return Object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public List<StreetDto> getStreet(MasterSearch search) {
        try {

            List<StreetDto> streetList = new ArrayList<StreetDto>();
            Session session = entityManager.unwrap(Session.class);

            Criteria criteria = session.createCriteria(Street.class);

            if (search.getStreetId() != null && search.getStreetId() > 0l) {
                criteria.add(Restrictions.eq("streetId", search.getStreetId()));
            }

            List<Street> list = criteria.list();

            for (Street entityObject : list) {
                StreetDto streetDto = (StreetDto) ModelEntityMapper.converObjectToPoJo(entityObject, StreetDto.class);
                streetList.add(streetDto);
            }

            return streetList;
        } catch (Exception e) {
            throw new RestException(ErrorCodeDescription.DATA_ACCESS);
        }
    }

    @Transactional
    public ResponseResource deleteStreet(MasterSearch search) {
        try {

            streetRepo.delete(search.getStreetId());
            return new ResponseResource(ErrorCodeDescription.TRANSACTION_SUCCESS);
        } catch (Exception e) {
            throw new RestException(ErrorCodeDescription.TRANSACTION_FAILED);
        }
    }

}
