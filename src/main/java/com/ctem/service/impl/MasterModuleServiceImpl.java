package com.ctem.service.impl;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.ctem.constant.StatusMessage;
import com.ctem.entity.MasterPermissionModules;
import com.ctem.service.MasterModuleService;

@Service
public class MasterModuleServiceImpl extends StatusMessage implements MasterModuleService{

	@PersistenceContext
    private EntityManager entityManager;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MasterPermissionModules> getAllMasterModules() {
		return entityManager.createNamedQuery("MasterPermissionModules.getAllActiveMasterPermissions").getResultList();
	}
	/*
	@SuppressWarnings("unchecked")
	@Override
	public List<MasterCountryList> getAllMasterCountryList() {
		return entityManager.createNamedQuery("MasterCountryList.getAllMasterCountryList").getResultList();
	}
	*/
}
