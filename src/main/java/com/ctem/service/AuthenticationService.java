package com.ctem.service;
import java.util.List;

import com.ctem.entity.AuthorizationRole;
import com.ctem.entity.AuthorizationRule;
import com.ctem.entity.Designation;

/**
 * @author ARVIND MAURYA
 *
 */
public interface AuthenticationService {
	
	public List<AuthorizationRole> getRoleListByUserId();

	public List<AuthorizationRule> getAllPermissionsByUserId(Long id);
	
	public List<Designation> getUserPayload();
}
