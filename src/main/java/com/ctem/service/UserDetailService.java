package com.ctem.service;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.ctem.entity.UserDetail;
import com.ctem.entity.UserEntity;
import com.ctem.payload.ApiResponse;
import com.ctem.payload.LoginRequest;
import com.ctem.payload.SignUpRequest;
/**
 * 
 * @author Arvind Maurya
 *
 */

public interface UserDetailService {

	public ApiResponse changePassword(Long id,String password);

	//public ApiResponse changePassword(ChangePasswordModal changePasswordModal);
	
	public UserEntity findByUsernameOrEmail(String usernameOrEmail);

	public ApiResponse createUser(UserEntity user);

	public List<UserEntity> getAllUsers();

	public UserEntity getUserById(Long userId);

	public ApiResponse updateUser(UserEntity user);
	
	public ApiResponse deleteUser(Long userId);
	
	public ApiResponse forgotPassword(String userNameOrEmail);

	public List<UserEntity> getAllUsersByRole(Long userTypeId);

	public ApiResponse changeUserPassword(@Valid LoginRequest loginRequest);
	
	//Added by Arvind Maurya
	@SuppressWarnings("rawtypes")
	public Map getUserCreationDateFields();
	
	@SuppressWarnings("rawtypes")
	public Map usersList();
	
	@SuppressWarnings("rawtypes")
	public Map getBonitaUserById(Long id);
	
	@SuppressWarnings("rawtypes")
	public Map updateUserById(UserDetail userDetail);
	
	@SuppressWarnings("rawtypes")
	public Map createUser(SignUpRequest signUpRequest);
	
	@SuppressWarnings("rawtypes")
	public Map disableUser(Long id);
	
	@SuppressWarnings("rawtypes")
	public Map enableUser(Long id);
	
}
