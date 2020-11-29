package com.ctem.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.ctem.constant.StatusMessage;
import com.ctem.entity.Role;
import com.ctem.entity.RoleAllotedPermission;
import com.ctem.entity.UserType;
import com.ctem.exception.BadRequestException;
import com.ctem.payload.ApiResponse;
import com.ctem.service.RoleService;

@Service
public class RoleServiceImpl extends StatusMessage implements RoleService{
	
	@PersistenceContext
    private EntityManager entityManager;

	@Override
	@Transactional
	public ApiResponse createRole(Role role){
		try {
			if(existRoleByRoleName(role.getName())) {
				throw new BadRequestException("Role name already in use!");
			}
			entityManager.persist(role);
			entityManager.flush();
			saveAllotedPermissions(role);
			return new ApiResponse(true, "Role " + CREATED_SUCCESSFULLY_MESSAGE);
		}
		catch(Exception e) {
			return new ApiResponse(false, e.getMessage());
		}
	}
	
	@Override
	@Transactional
	public ApiResponse updateRole(Role role) {
		try {
			entityManager.merge(role);
			entityManager.flush();
			entityManager.createNamedQuery("RoleAllotedPermission.deleteAllAllotedPermissionByRole").setParameter("roleId", role.getId()).executeUpdate();
			saveAllotedPermissions(role);
			entityManager.createNamedQuery("User.updateUserDeleteStatusByRole").setParameter("status", role.isArchived()).setParameter("roleId", role.getId()).executeUpdate();
			return new ApiResponse(true, "Role " + UPDATED_SUCCESSFULLY_MESSAGE);
		}
		catch(Exception e) {
			return new ApiResponse(false, UNABLE_TO_FULFIL_REQUEST + e.getMessage());
		}
	}
	
	@Transactional
	private void saveAllotedPermissions(Role role) {
		for(RoleAllotedPermission permission : role.getRoleAllotedPermission()) {
			permission.setRoleId(role.getId());
			entityManager.persist(permission);
			entityManager.flush();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Role getRole(Long roleId) {
		Role role = entityManager.find(Role.class, roleId);
		role.setRoleAllotedPermission(entityManager.createNamedQuery("RoleAllotedPermission.findAllAllotedPermissionByRole").setParameter("roleId", roleId).getResultList());
		return role;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getActiveRoles() {
		return entityManager.createNamedQuery("Role.getActiveRoles").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getAllRoles() {
		return entityManager.createNamedQuery("Role.getAllRoles").getResultList();
	}

	@Override
	@Transactional
	public ApiResponse deleteRole(Long roleId) {
		try {
			Role role = entityManager.find(Role.class, roleId);
			if(role.isArchived() == true) {
				role.setArchived(false);
			}else {
				role.setArchived(true);
			}
			entityManager.merge(role);
			entityManager.flush();
			entityManager.createNamedQuery("User.updateUserDeleteStatusByRole").setParameter("status", role.isArchived()).setParameter("roleId", role.getId()).executeUpdate();
			return new ApiResponse(true, "Role " + STATUS_CHANGED_SUCCESSFULLY_MESSAGE);
		}
		catch(Exception e) {
			return new ApiResponse(false, UNABLE_TO_FULFIL_REQUEST + e.getMessage());
		}
	}

	@Override
	@Transactional
	public String createUserType(UserType userType) {
		userType.setTypeName(StringUtils.upperCase(userType.getDisplayName().replaceAll(" ", "_")));
		entityManager.persist(userType);
		entityManager.flush();
		return "User Type"+ CREATED_SUCCESSFULLY_MESSAGE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserType> getActiveUserTypes() {
		return entityManager.createNamedQuery("UserType.getAllActiveUserTypes").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	private boolean existRoleByRoleName(String roleName) {
		List<Role> roles = entityManager.createNamedQuery("Role.existRoleByRoleName").setParameter("roleName", roleName).getResultList();
		return roles.size() > 0;
	}
	
	@Override
	public UserType getUserTypeById(Long usertypeId) {
		UserType userType = entityManager.find(UserType.class, usertypeId);
		return userType;
	}
	
	@Override
	@Transactional
	public ApiResponse updateUserType(UserType userType) {
		try {
			entityManager.merge(userType);
			entityManager.flush();
			return new ApiResponse(true, "UserType " + UPDATED_SUCCESSFULLY_MESSAGE);
		}
		catch(Exception e) {
			return new ApiResponse(false, UNABLE_TO_FULFIL_REQUEST + e.getMessage());
		}
	}
	
	@Override
	@Transactional
	public ApiResponse deleteUserType(Long userTypeId) {
		UserType userType = entityManager.find(UserType.class, userTypeId);
		if (userType.isArchived()) {
			return new ApiResponse(true, "UserType" + ALREADY_DELETED_MESSAGE);
		}
		userType.setArchived(true);
		entityManager.merge(userType);
		entityManager.flush();
		return new ApiResponse(true, "UserType" + DELETED_SUCCESSFULLY_MESSAGE);

	}
}