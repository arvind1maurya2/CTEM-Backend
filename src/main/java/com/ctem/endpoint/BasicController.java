package com.ctem.endpoint;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ctem.constant.StatusMessage;
import com.ctem.model.AuthenticationModal;
import com.ctem.payload.LoginRequest;
import com.ctem.service.AuthenticationService;

/**
 * @author Arvind Maurya
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/api/basic")
public class BasicController extends StatusMessage {

	@Autowired
	AuthenticationService authenticationService;

	/**
	 * @author Arvind Maurya
	 * @since 2020-12-14
	 * @param loginRequest
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/signin")
	public ResponseEntity<?> signin(@Valid @RequestBody LoginRequest loginRequest) throws IOException {
		AuthenticationModal authenticationModal = authenticationService.signin(loginRequest.getUsername(),
				loginRequest.getPassword());
		if (authenticationModal.getSuccess()) {
			return new ResponseEntity<>(authenticationModal, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(authenticationModal, HttpStatus.UNAUTHORIZED);
		}
	}
/**
 * 
 public void createInstance(String processDefinitionName, String processVersion, Map<String, Object> variables,
			APISession apiSession) {
		ProcessAPI processAPI;
		try {
			processAPI = TenantAPIAccessor.getProcessAPI(apiSession);
			long processDefinitionId = processAPI.getProcessDefinitionId(processDefinitionName, processVersion);

			List<Operation> listOperations = new ArrayList<Operation>();
			for (String variableName : variables.keySet()) {
				if (variables.get(variableName) == null)
					continue;
				Operation operation = buildAssignOperation(variableName, variables.get(variableName).toString(),
						String.class.getName(), ExpressionType.TYPE_CONSTANT);
				listOperations.add(operation);
			}
			processAPI.startProcess(processDefinitionId, listOperations, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Operation buildAssignOperation(final String dataInstanceName, final String newConstantValue,
			final String className, final ExpressionType expressionType) throws InvalidExpressionException {
		final LeftOperand leftOperand = new LeftOperandBuilder().createNewInstance().setName(dataInstanceName).done();
		final Expression expression = new ExpressionBuilder().createNewInstance(dataInstanceName)
				.setContent(newConstantValue).setExpressionType(expressionType.name()).setReturnType(className).done();
		final Operation operation;
		operation = new OperationBuilder().createNewInstance().setOperator("=").setLeftOperand(leftOperand)
				.setType(OperatorType.ASSIGNMENT).setRightOperand(expression).done();
		return operation;
	}
	//ProcessAPI processAPI = BaseEntity.apiClient.get().getProcessAPI();
	//List<ArchivedProcessInstance> a=processAPI.searchArchivedProcessInstances(new SearchOptionsBuilder(0, 100).done()).getResult();
	//List<ArchivedDataInstance> aa= processAPI.getArchivedActivityDataInstances(2002, 0, 100);
	//long processInstance = processAPI.getNumberOfArchivedProcessInstances();//(Long.parseLong("4680666241133101661"),0,1000000000);

 */
}
