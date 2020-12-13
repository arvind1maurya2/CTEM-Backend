package com.ctem.service;
import java.util.List;

import com.ctem.entity.Role;
import com.ctem.entity.UserType;
import com.ctem.payload.ApiResponse;
/**
 * 
 * @author Arvind Maurya
 *
 */
public interface RoleService {
	public ApiResponse createRole(Role role);
	public ApiResponse updateRole(Role role);
	public Role getRole(Long roleId);
	public List<Role> getActiveRoles();
	public List<Role> getAllRoles();
	public ApiResponse deleteRole(Long roleId);
	public String createUserType(UserType userType);
	public List<UserType> getActiveUserTypes();
	public ApiResponse updateUserType(UserType userType);
	public UserType getUserTypeById(Long userTypeId);
	public ApiResponse deleteUserType(Long userTypeId);
}
