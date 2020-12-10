/**
 * 
 */
package com.ctem.endpoint;

import java.io.File;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.bonitasoft.engine.api.BusinessDataAPI;
import org.bonitasoft.engine.api.IdentityAPI;
import org.bonitasoft.engine.api.ProcessAPI;
import org.bonitasoft.engine.api.TenantAPIAccessor;
import org.bonitasoft.engine.api.TenantAdministrationAPI;
import org.bonitasoft.engine.bpm.contract.ContractViolationException;
import org.bonitasoft.engine.bpm.flownode.ArchivedActivityInstance;
import org.bonitasoft.engine.bpm.flownode.ArchivedActivityInstanceSearchDescriptor;
import org.bonitasoft.engine.bpm.process.ProcessActivationException;
import org.bonitasoft.engine.bpm.process.ProcessDefinitionNotFoundException;
import org.bonitasoft.engine.bpm.process.ProcessDeploymentInfo;
import org.bonitasoft.engine.bpm.process.ProcessDeploymentInfoSearchDescriptor;
import org.bonitasoft.engine.bpm.process.ProcessExecutionException;
import org.bonitasoft.engine.bpm.process.ProcessInstance;
import org.bonitasoft.engine.bpm.process.ProcessInstanceSearchDescriptor;
import org.bonitasoft.engine.business.data.SimpleBusinessDataReference;
import org.bonitasoft.engine.exception.BonitaRuntimeException;
import org.bonitasoft.engine.exception.SearchException;
import org.bonitasoft.engine.identity.User;
import org.bonitasoft.engine.search.Order;
import org.bonitasoft.engine.search.SearchOptions;
import org.bonitasoft.engine.search.SearchOptionsBuilder;
import org.bonitasoft.engine.search.SearchResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ctem.entity.BaseEntity;
import com.ctem.payload.ApiResponse;
import com.ctem.payload.OfficeDetails;

/**
 * @author Arvind Maurya
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/api/auth/office")
public class OfficeController {

	@SuppressWarnings({ "unchecked", "rawtypes"})
	@PostMapping("/create-office")
	public ResponseEntity<?> createOffice(HttpServletRequest request, HttpServletResponse response,
			@RequestBody(required = false) OfficeDetails o) throws SQLException {
		String msg = "something wents wrong";
		try {
			SearchOptionsBuilder searchBuilder = new SearchOptionsBuilder(0, 100);
			searchBuilder.filter(ArchivedActivityInstanceSearchDescriptor.NAME,"office_create_pool");
			searchBuilder.filter(ProcessDeploymentInfoSearchDescriptor.ACTIVATION_STATE, "ENABLED");
			searchBuilder.sort(ProcessDeploymentInfoSearchDescriptor.VERSION, Order.DESC);
			SearchResult<ProcessDeploymentInfo> archActivitResult = BaseEntity.apiClient.get().getProcessAPI().searchProcessDeploymentInfos(searchBuilder.done());
			if(archActivitResult.getResult().isEmpty()) {
				msg="bonita office creation form is not bind";
				return new ResponseEntity(new ApiResponse(false, msg), HttpStatus.BAD_REQUEST);
			}
			Map varMap = new HashMap();
            Map inputForm = new HashMap();
            inputForm.put("name", o.getName());
            inputForm.put("code", o.getCode());
            inputForm.put("type", o.getType());
            inputForm.put("mobile_number", o.getMobileNumber());
            inputForm.put("address", o.getAddress());
            inputForm.put("district",o.getDistrict());
            inputForm.put("city", o.getCity());
            varMap.put("officeBVInput", (Serializable) inputForm);
			ProcessAPI processAPI=BaseEntity.apiClient.get().getProcessAPI();
			ProcessInstance processInstance = null;
			processInstance=processAPI.startProcessWithInputs(archActivitResult.getResult().get(0).getProcessId(), varMap);
			return new ResponseEntity(new ApiResponse(true, "Office Created",processInstance.getId()), HttpStatus.OK);
			
		}catch (ProcessDefinitionNotFoundException e) {
			msg=e.getMessage();
			e.printStackTrace();
		} catch (ProcessActivationException e) {
			msg=e.getMessage();
			e.printStackTrace();
		} catch (ProcessExecutionException e) {
			msg=e.getMessage();
			e.printStackTrace();
		} catch (NumberFormatException e) {
			msg=e.getMessage();
			e.printStackTrace();
		} catch (ContractViolationException e) {
			msg=e.getMessage();
			e.printStackTrace();
		} catch (SearchException e) {
			msg=e.getMessage();
			e.printStackTrace();
		}catch (BonitaRuntimeException e) {
			msg=e.getMessage();
			e.printStackTrace();
		}catch (Exception e) {
			msg=e.getMessage();
			e.printStackTrace();
		}
		return new ResponseEntity(new ApiResponse(false, msg), HttpStatus.BAD_REQUEST);
	}
	@SuppressWarnings({ "unchecked", "rawtypes"})
	
	@GetMapping("/offices-list")
	public ResponseEntity<?> officesList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody(required = false) OfficeDetails o) throws SQLException {
		String msg = "something wents wrong";
		try {
			/*
			SearchOptionsBuilder searchBuilder = new SearchOptionsBuilder(0, 100);
			searchBuilder.filter(ArchivedActivityInstanceSearchDescriptor.NAME, "office_create_pool");
			searchBuilder.filter(ProcessDeploymentInfoSearchDescriptor.ACTIVATION_STATE, "ENABLED");
			searchBuilder.sort(ProcessDeploymentInfoSearchDescriptor.VERSION, Order.DESC);
			SearchResult<ProcessDeploymentInfo> archActivitResult = BaseEntity.apiClient.get().getProcessAPI()
					.searchProcessDeploymentInfos(searchBuilder.done());

			final ProcessAPI processAPI = TenantAPIAccessor.getProcessAPI(BaseEntity.apiSession.get());
			final SearchOptionsBuilder builder = new SearchOptionsBuilder(0, 100);
			builder.filter(ProcessInstanceSearchDescriptor.PROCESS_DEFINITION_ID,
					archActivitResult.getResult().get(0).getProcessId());
			final SearchResult<ProcessInstance> processInstanceResults = processAPI
					.searchOpenProcessInstances(builder.done());
			
			
			ProcessAPI a =BaseEntity.apiClient.get().getProcessAPI();
			BusinessDataAPI businessDataAPI = TenantAPIAccessor.getBusinessDataAPI(BaseEntity.apiSession.get());
			SimpleBusinessDataReference businessDataReference = (SimpleBusinessDataReference)businessDataAPI.getProcessBusinessDataReference("vacationAvailable", 2);
		 */
			TenantAdministrationAPI tenantAdministrationAPI = org.bonitasoft.engine.api.TenantAPIAccessor.getTenantAdministrationAPI(BaseEntity.apiSession.get());
            File file = new File("D:\bdmClient.zip");
            FileUtils.writeByteArrayToFile(file,  tenantAdministrationAPI.getClientBDMZip());
			
		} catch (BonitaRuntimeException e) {
			msg=e.getMessage();
			e.printStackTrace();
		}catch (Exception e) {
			msg=e.getMessage();
			e.printStackTrace();
		}
		return new ResponseEntity(new ApiResponse(false, msg), HttpStatus.BAD_REQUEST);
	}
/**
 * 
 // Then retrieve the Process API: OfficeCreate
		try {
			SearchOptionsBuilder searchBuilder = new SearchOptionsBuilder(0, 100);
			// implicit AND clause between the following two filters:
			searchBuilder.filter(ArchivedActivityInstanceSearchDescriptor.NAME,"office_create_pool");
			searchBuilder.filter(ProcessDeploymentInfoSearchDescriptor.ACTIVATION_STATE, "ENABLED");
			searchBuilder.sort(ProcessDeploymentInfoSearchDescriptor.VERSION, Order.DESC);
			SearchResult<ProcessDeploymentInfo> archActivitResult = BaseEntity.apiClient.get().getProcessAPI().searchProcessDeploymentInfos(searchBuilder.done());
			//System.out.println(archActivitResult.getResult().get(0).toString());
			if(archActivitResult.getResult().isEmpty()) {
				System.out.println("bonita office creation form is not bind");
			}
			//====================
			//Map<String, Serializable> processData = new HashMap<String, Serializable>();
			//processData.put("testBVInput", "var1"); // for data you want to send to the process
			//processData.put("data2", "var2");
			//processData.put("data3", "var3");
			
			
			Map varMap = new HashMap();
            Map inputForm = new HashMap();
            
            inputForm.put("name", "1");
            inputForm.put("code", "2");
            inputForm.put("type", "3");
            inputForm.put("mobile_number", "4");
            inputForm.put("address", "5");
            inputForm.put("district",6);
            inputForm.put("city", 7);

            varMap.put("officeBVInput", (Serializable) inputForm);
            
			ProcessAPI a=BaseEntity.apiClient.get().getProcessAPI();
			ProcessInstance g;
			g=a.startProcessWithInputs(archActivitResult.getResult().get(0).getProcessId(), varMap);
			
			
			//@SuppressWarnings({ "unused", "unchecked" })
			//ProcessInstance processInstance = a.startProcess(archActivitResult.getResult().get(0).getProcessId(),varMap); //start the process
			//sb.append(processInstance.getName()+"-"+processId+":"+processInstance.getId()+":"+usern+";"); //save to processes started list
			
 * 
 */
}
