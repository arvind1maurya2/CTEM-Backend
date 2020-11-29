package com.ctem.service;
import java.util.List;

import javax.validation.Valid;

import com.ctem.entity.User;
import com.ctem.payload.ApiResponse;
import com.ctem.payload.LoginRequest;

public interface UserDetailService {

	public ApiResponse changePassword(Long id,String password);

	//public ApiResponse changePassword(ChangePasswordModal changePasswordModal);
	
	public User findByUsernameOrEmail(String usernameOrEmail);

	public ApiResponse createUser(User user);

	public List<User> getAllUsers();

	public User getUserById(Long userId);

	public ApiResponse updateUser(User user);
	
	public ApiResponse deleteUser(Long userId);
	
	public ApiResponse forgotPassword(String userNameOrEmail);

	public List<User> getAllUsersByRole(Long userTypeId);

	public ApiResponse changeUserPassword(@Valid LoginRequest loginRequest);
}
