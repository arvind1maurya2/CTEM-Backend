/**
 * 
 */
package com.ctem.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bonitasoft.engine.api.ProcessAPI;
import org.bonitasoft.engine.api.TenantAPIAccessor;
import org.bonitasoft.engine.expression.Expression;
import org.bonitasoft.engine.expression.ExpressionBuilder;
import org.bonitasoft.engine.expression.ExpressionType;
import org.bonitasoft.engine.expression.InvalidExpressionException;
import org.bonitasoft.engine.operation.LeftOperand;
import org.bonitasoft.engine.operation.LeftOperandBuilder;
import org.bonitasoft.engine.operation.Operation;
import org.bonitasoft.engine.operation.OperationBuilder;
import org.bonitasoft.engine.operation.OperatorType;
import org.bonitasoft.engine.session.APISession;

/**
 * @author Arvind Maurya
 *
 */
public class OfficeServiceImpl {

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

}
