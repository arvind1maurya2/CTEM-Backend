package com.ctem.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctem.entity.AuthorizationRole;
import com.ctem.entity.AuthorizationRule;
import com.ctem.entity.Designation;
import com.ctem.repository.AuthorizationRoleRepository;
import com.ctem.repository.AuthorizationRuleRepository;
import com.ctem.service.AuthenticationService;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private AuthorizationRoleRepository authorizationRoleRepository;

	@Autowired
	private AuthorizationRuleRepository authorizationRuleRepository;

	public ArrayList getAllPermissionsByUserId1(Long id) {
		List<AuthorizationRole> authorizationRoles = null;

		authorizationRoles = authorizationRoleRepository.findAll();
		authorizationRoles.stream().forEach(authorizationRole -> {
			System.out.println(authorizationRole.getName());

		});
		ArrayList arrayRules = new ArrayList();

		List<AuthorizationRule> authorizationRules = null;
		authorizationRules = authorizationRuleRepository.findAll();
		authorizationRules.stream().forEach(authorizationRule -> {
			JSONObject obj = new JSONObject();
			obj.put("name", authorizationRule.getName());
			obj.put("route", authorizationRule.getRoute());
			obj.put("menu", authorizationRule.isMenu());
			obj.put("parent", authorizationRule.isParent());
			arrayRules.add(obj);
		});
		System.out.println(arrayRules);

		return arrayRules;

	}

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<AuthorizationRule> getAllPermissionsByUserId(Long id) {
		List<AuthorizationRule> parentMenus = entityManager.createNamedQuery("AuthorizationRule.findAllParentMenus")
				.getResultList();
		if (parentMenus != null) {
			for (AuthorizationRule parentMenu : parentMenus) {
				parentMenu.setSubmenus(entityManager.createNamedQuery("AuthorizationRule.findAllChildMenus")
						.setParameter("parentId", parentMenu.getId()).getResultList());
			}
		}
		return parentMenus;
	}

	@Override
	public List<AuthorizationRole> getRoleListByUserId() {
		// TODO Auto-generated method stub
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Designation> getUserPayload() {
		
		List<Designation> designation = entityManager.createNamedQuery("Designation.findAll").getResultList();

	//List<AuthorizationRule> parentMenus = entityManager.createNamedQuery("AuthorizationRule.findAllParentMenus")
	//		.getResultList();
	//if (parentMenus != null) {
	//	for (AuthorizationRule parentMenu : parentMenus) {
	//		parentMenu.setSubmenus(entityManager.createNamedQuery("AuthorizationRule.findAllChildMenus")
	//				.setParameter("parentId", parentMenu.getId()).getResultList());
	//	}
	//}
		return designation;
	}
}
