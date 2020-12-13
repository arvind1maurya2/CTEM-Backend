/**
 * 
 */
package com.ctem.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bonitasoft.engine.api.ProcessAPI;
import org.bonitasoft.engine.bpm.contract.ContractViolationException;
import org.bonitasoft.engine.bpm.flownode.ArchivedActivityInstanceSearchDescriptor;
import org.bonitasoft.engine.bpm.process.ProcessActivationException;
import org.bonitasoft.engine.bpm.process.ProcessDefinitionNotFoundException;
import org.bonitasoft.engine.bpm.process.ProcessDeploymentInfo;
import org.bonitasoft.engine.bpm.process.ProcessDeploymentInfoSearchDescriptor;
import org.bonitasoft.engine.bpm.process.ProcessExecutionException;
import org.bonitasoft.engine.bpm.process.ProcessInstance;
import org.bonitasoft.engine.exception.BonitaRuntimeException;
import org.bonitasoft.engine.exception.SearchException;
import org.bonitasoft.engine.search.Order;
import org.bonitasoft.engine.search.SearchOptionsBuilder;
import org.bonitasoft.engine.search.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctem.constant.StatusMessage;
import com.ctem.entity.BaseEntity;
import com.ctem.entity.Office;
import com.ctem.payload.ApiResponse;
import com.ctem.payload.OfficeDetails;
import com.ctem.repository.CityRepository;
import com.ctem.repository.DistrictRepository;
import com.ctem.repository.OfficeRepository;
import com.ctem.service.OfficeService;

/**
 * @author Arvind Maurya
 *
 */
@Service
public class OfficeServiceImpl extends StatusMessage implements OfficeService {

	@Autowired
	OfficeRepository officeRepository;
	@Autowired
	DistrictRepository districtRepository;
	@Autowired
	CityRepository cityRepository;

	/**
	 * @author Arvind Maurya
	 * @since 2020-12-14
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map createOffice(OfficeDetails o) {
		Map map = new HashMap();
		try {
			if (StringUtils.isNotBlank(o.getName())) {
				if (officeRepository.existsByName(o.getName())) {
					map.put("error", new ApiResponse(false, "Office name is already taken!"));
					return map;
				}
			} else {
				map.put("error", new ApiResponse(false, "Office Name can't be blank!"));
				return map;
			}
			SearchOptionsBuilder searchBuilder = new SearchOptionsBuilder(0, 1000);
			searchBuilder.filter(ArchivedActivityInstanceSearchDescriptor.NAME, "office_create_pool");
			searchBuilder.filter(ProcessDeploymentInfoSearchDescriptor.ACTIVATION_STATE, "ENABLED");
			searchBuilder.sort(ProcessDeploymentInfoSearchDescriptor.VERSION, Order.DESC);
			SearchResult<ProcessDeploymentInfo> archActivitResult = BaseEntity.apiClient.get().getProcessAPI()
					.searchProcessDeploymentInfos(searchBuilder.done());
			if (archActivitResult.getResult().isEmpty()) {
				map.put("error", new ApiResponse(false, "bonita office creation form is not bind"));
				return map;
			}
			Map varMap = new HashMap();
			Map inputForm = new HashMap();
			inputForm.put("name", o.getName());
			inputForm.put("code", o.getCode());
			inputForm.put("type", o.getType());
			inputForm.put("mobile_number", o.getMobileNumber());
			inputForm.put("address", o.getAddress());
			inputForm.put("district", o.getDistrict());
			inputForm.put("city", o.getCity());
			varMap.put("officeBVInput", (Serializable) inputForm);
			ProcessAPI processAPI = BaseEntity.apiClient.get().getProcessAPI();
			ProcessInstance processInstance = null;
			processInstance = processAPI.startProcessWithInputs(archActivitResult.getResult().get(0).getProcessId(),
					varMap);
			map.put("office", new ApiResponse(true, "Office Created", processInstance.getId()));
		} catch (ProcessDefinitionNotFoundException e) {
			map.put("error", new ApiResponse(true, e.getMessage()));
		} catch (ProcessActivationException e) {
			map.put("error", new ApiResponse(true, e.getMessage()));
		} catch (ProcessExecutionException e) {
			map.put("error", new ApiResponse(true, e.getMessage()));
		} catch (NumberFormatException e) {
			map.put("error", new ApiResponse(true, e.getMessage()));
		} catch (ContractViolationException e) {
			map.put("error", new ApiResponse(true, e.getMessage()));
		} catch (SearchException e) {
			map.put("error", new ApiResponse(true, e.getMessage()));
		} catch (BonitaRuntimeException e) {
			map.put("error", new ApiResponse(true, e.getMessage()));
		} catch (Exception e) {
			map.put("error", new ApiResponse(true, e.getMessage()));
		}
		return map;
	}

	/**
	 * @author Arvind Maurya
	 * @since 2020-12-14
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map getOfficeById(Long id) {
		Map map = new HashMap();
		try {
			Office office;
			office = officeRepository.findById(id).get();
			map.put("office", office);
		} catch (Exception e) {
			map.put("error", new ApiResponse(true, e.getMessage()));
		}
		return map;
	}

	/**
	 * @author Arvind Maurya
	 * @since 2020-12-14
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map officesList() {
		Map map = new HashMap();
		try {
			List<Office> offices = officeRepository.findByArchived(false);
			for (Office office : offices) {
				office.setDistrictName(districtRepository.getOne(office.getDistrict()).getName());
				office.setCityName(cityRepository.getOne(office.getCity()).getName());
			}
			map.put("office", offices);
		} catch (BonitaRuntimeException e) {
			map.put("error", new ApiResponse(true, e.getMessage()));
		} catch (Exception e) {
			map.put("error", new ApiResponse(true, e.getMessage()));
		}
		return map;
	}

	/**
	 * @author Arvind Maurya
	 * @since 2020-12-14
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map updateOfficeById(OfficeDetails o) {
		Map map=new HashMap();
		try {
			if (o.getId() > 0) {
				Office office =officeRepository.findById(o.getId()).get();
				office.setCode(o.getCode());
				office.setName(o.getName());
				office.setType(o.getType());
				office.setMobileNumber(o.getMobileNumber());
				office.setAddress(o.getAddress());
				office.setDistrict(o.getDistrict());
				office.setCity(o.getCity());
				officeRepository.save(office);
				map.put("office", new ApiResponse(true, "Office Updated",o.getId()));
			} else {
				map.put("error", new ApiResponse(true, "Office id can't be blank!"));
			}
		} catch (BonitaRuntimeException e) {
			map.put("error", new ApiResponse(true, e.getMessage()));
		} catch (Exception e) {
			map.put("error", new ApiResponse(true, e.getMessage()));
		}
		return map;
	}

}
