/**
 * 
 */
package com.ctem.endpoint;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ctem.entity.BaseEntity;
import com.ctem.entity.Office;
import com.ctem.payload.ApiResponse;
import com.ctem.payload.OfficeDetails;
import com.ctem.repository.CityRepository;
import com.ctem.repository.DistrictRepository;
import com.ctem.repository.OfficeRepository;

/**
 * @author Arvind Maurya
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/api/auth/office")
public class OfficeController {

	@Autowired
	OfficeRepository officeRepository;
	@Autowired
	DistrictRepository districtRepository;
	@Autowired
	CityRepository cityRepository;
	
	@Autowired
	EntityManager entityManager;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/create-office")
	public ResponseEntity<?> createOffice(HttpServletRequest request, HttpServletResponse response,
			@RequestBody(required = false) OfficeDetails o) throws SQLException {
		String msg = "something wents wrong";
		try {
			if (StringUtils.isNotBlank(o.getName())) {
				if (officeRepository.existsByName(o.getName())) {
					return new ResponseEntity(new ApiResponse(false, "Office name is already taken!"),
							HttpStatus.BAD_REQUEST);
				}
			} else {
				return new ResponseEntity(new ApiResponse(false, "Office Name can't be blank!"),
						HttpStatus.BAD_REQUEST);
			}
			SearchOptionsBuilder searchBuilder = new SearchOptionsBuilder(0, 100);
			searchBuilder.filter(ArchivedActivityInstanceSearchDescriptor.NAME, "office_create_pool");
			searchBuilder.filter(ProcessDeploymentInfoSearchDescriptor.ACTIVATION_STATE, "ENABLED");
			searchBuilder.sort(ProcessDeploymentInfoSearchDescriptor.VERSION, Order.DESC);
			SearchResult<ProcessDeploymentInfo> archActivitResult = BaseEntity.apiClient.get().getProcessAPI()
					.searchProcessDeploymentInfos(searchBuilder.done());
			if (archActivitResult.getResult().isEmpty()) {
				msg = "bonita office creation form is not bind";
				return new ResponseEntity(new ApiResponse(false, msg), HttpStatus.BAD_REQUEST);
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
			//TenantAdministrationAPI tenantAdministrationAPI = org.bonitasoft.engine.api.TenantAPIAccessor.getTenantAdministrationAPI(BaseEntity.apiSession.get());
			//File file = new File("D://bdmClient.zip");
			//FileUtils.writeByteArrayToFile(file,  tenantAdministrationAPI.getClientBDMZip());			
			return new ResponseEntity(new ApiResponse(true, "Office Created", processInstance.getId()), HttpStatus.OK);
		} catch (ProcessDefinitionNotFoundException e) {
			msg = e.getMessage();
			e.printStackTrace();
		} catch (ProcessActivationException e) {
			msg = e.getMessage();
			e.printStackTrace();
		} catch (ProcessExecutionException e) {
			msg = e.getMessage();
			e.printStackTrace();
		} catch (NumberFormatException e) {
			msg = e.getMessage();
			e.printStackTrace();
		} catch (ContractViolationException e) {
			msg = e.getMessage();
			e.printStackTrace();
		} catch (SearchException e) {
			msg = e.getMessage();
			e.printStackTrace();
		} catch (BonitaRuntimeException e) {
			msg = e.getMessage();
			e.printStackTrace();
		} catch (Exception e) {
			msg = e.getMessage();
			e.printStackTrace();
		}
		return new ResponseEntity(new ApiResponse(false, msg), HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/offices-list")
	public ResponseEntity<?> officesList(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		String msg = "something wents wrong";
		try {
			// List<OfficeDTO> offices= entityManager.createNamedQuery("Office.getAllOffices").getResultList();
			List<Office> offices = officeRepository.findByArchived(false);
			for (Office office : offices) {
				office.setDistrictName(districtRepository.getOne(office.getDistrict()).getName());
				office.setCityName(cityRepository.getOne(office.getCity()).getName());
			}
			return new ResponseEntity<>(offices, HttpStatus.OK);
		} catch (BonitaRuntimeException e) {
			msg = e.getMessage();
			e.printStackTrace();
		} catch (Exception e) {
			msg = e.getMessage();
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ApiResponse(false, msg), HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/get-office-by-id/{id}")
	public ResponseEntity<?> getOfficeById(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "id", required = true) Long id) {
		Office office;
		office = officeRepository.findById(id).get();
		return new ResponseEntity<>(office, HttpStatus.OK);
	}

}
