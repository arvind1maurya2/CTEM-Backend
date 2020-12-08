package com.ctem.service;
import java.util.List;

import javax.validation.Valid;

import com.ctem.entity.UserEntity;
import com.ctem.payload.ApiResponse;
import com.ctem.payload.LoginRequest;

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
}
