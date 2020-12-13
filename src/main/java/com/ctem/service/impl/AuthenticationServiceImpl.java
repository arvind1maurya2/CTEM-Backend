package com.ctem.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.bonitasoft.engine.api.APIClient;
import org.bonitasoft.engine.api.ApiAccessType;
import org.bonitasoft.engine.api.IdentityAPI;
import org.bonitasoft.engine.api.LoginAPI;
import org.bonitasoft.engine.api.TenantAPIAccessor;
import org.bonitasoft.engine.exception.BonitaHomeNotSetException;
import org.bonitasoft.engine.exception.ServerAPIException;
import org.bonitasoft.engine.exception.UnknownAPITypeException;
import org.bonitasoft.engine.identity.UserNotFoundException;
import org.bonitasoft.engine.identity.UserWithContactData;
import org.bonitasoft.engine.platform.LoginException;
import org.bonitasoft.engine.platform.UnknownUserException;
import org.bonitasoft.engine.session.APISession;
import org.bonitasoft.engine.util.APITypeManager;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ctem.entity.AuthorizationRole;
import com.ctem.entity.AuthorizationRule;
import com.ctem.entity.BaseEntity;
import com.ctem.entity.Designation;
import com.ctem.entity.UserEntity;
import com.ctem.model.AuthenticationModal;
import com.ctem.repository.AuthorizationRoleRepository;
import com.ctem.repository.AuthorizationRuleRepository;
import com.ctem.security.JWTTokenProvider;
import com.ctem.service.AuthenticationService;
import com.ctem.service.UserDetailService;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JWTTokenProvider tokenProvider;
	@Autowired
	UserDetailService userDetailService;
	@Autowired
	private AuthorizationRoleRepository authorizationRoleRepository;
	@Autowired
	private AuthorizationRuleRepository authorizationRuleRepository;
	@Value("${app.bonitaURL}")
	private String bonitaURL;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
	
	/**
	 * @author Arvind Maurya
	 * @since 2020-12-14
	 */
	public String bonitaAuthentication(String username, String password) {
		String message = null;
		try {
			Map<String, String> settings = new HashMap<String, String>();
			settings.put("server.url", bonitaURL);
			settings.put("application.name", "bonita");
			APITypeManager.setAPITypeAndParams(ApiAccessType.HTTP, settings);
			LoginAPI loginAPI = TenantAPIAccessor.getLoginAPI();

			APISession apiSession = loginAPI.login(username, password);

			APIClient apiClient = new APIClient();
			apiClient.login(username, password);

			BaseEntity.apiSession.set(apiSession);
			BaseEntity.apiClient.set(apiClient);

		} catch (UnknownUserException e) {
			message = e.getMessage();
		} catch (BonitaHomeNotSetException e) {
			message = e.getMessage();
		} catch (ServerAPIException e) {
			message = e.getMessage();
		} catch (UnknownAPITypeException e) {
			message = e.getMessage();
		} catch (LoginException e) {
			message = "Username or password is not valid";
		} catch (Exception e) {
			message = e.getMessage();
		}
		return message;
	}

	/**
	 * @author Arvind Maurya
	 * @since 2020-12-14
	 * 
	 */
	public AuthenticationModal signin(String username, String password) {

		String message = bonitaAuthentication(username, password);
		if (StringUtils.isBlank(message)) {
			try {
				IdentityAPI identityAPI = BaseEntity.apiClient.get().getIdentityAPI();
				UserWithContactData proUser = identityAPI
						.getUserWithProfessionalDetails(BaseEntity.apiSession.get().getUserId());
				if (BaseEntity.apiSession.get().getUserName() != null) {
					Authentication authentication = authenticationManager
							.authenticate(new UsernamePasswordAuthenticationToken(username, password));
					UserEntity user = userDetailService.findByUsernameOrEmail(username);
					SecurityContextHolder.getContext().setAuthentication(authentication);
					String jwt = tokenProvider.generateToken(authentication);
					user.setFirstName(proUser.getUser().getFirstName());
					user.setLastName(proUser.getUser().getLastName());
					user.setEmail(proUser.getContactData().getEmail());
					user.setGender(proUser.getContactData().getWebsite());
					user.setMobileNumber(proUser.getContactData().getMobileNumber());
					user.setAddress(proUser.getContactData().getAddress());
					return new AuthenticationModal(jwt, user, true, "Login success");
				}
			} catch (UserNotFoundException e) {
				message = "Username or password is not valid!";
				e.printStackTrace();
			}
		}
		return new AuthenticationModal(false, message);
	}
}
