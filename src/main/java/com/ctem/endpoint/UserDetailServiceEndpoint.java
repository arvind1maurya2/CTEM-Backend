package com.ctem.endpoint;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ctem.entity.BaseEntity;
import com.ctem.entity.UserEntity;
import com.ctem.payload.ApiResponse;
import com.ctem.payload.LoginRequest;
import com.ctem.repository.UserRepository;
import com.ctem.service.UserDetailService;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserDetailServiceEndpoint {
	@Autowired
	UserRepository userRepository;

	@Autowired
	UserDetailService userDetailService;

	@GetMapping("/currentUser")
	public UserEntity getCurrentUser() throws IOException {		
		UserEntity user = userRepository.getOne(BaseEntity.currentUserId.get());
		return user;
	}

	@PostMapping("/changePasswordAdmin/{userId}")
	public ApiResponse changePasswordAuthenticateByAdmin(@PathVariable(value = "userId", required = true) Long userId,
			@RequestBody String password) throws IOException {
		return userDetailService.changePassword(userId, password);
	}

	@PostMapping("/changeUserPassword")
	public ApiResponse changeUserPassword(@Valid @RequestBody LoginRequest loginRequest) throws IOException {
		return userDetailService.changeUserPassword(loginRequest);
	}

	@PostMapping("/createUser")
	public ApiResponse createUser(@Valid @RequestBody UserEntity user) throws IOException {
		return userDetailService.createUser(user);
	}

	@PutMapping("/updateUser")
	public ApiResponse updateUser(@RequestBody UserEntity user) throws IOException {
		return userDetailService.updateUser(user);
	}

	@GetMapping("/getUserById/{userId}")
	public ResponseEntity<?> getUserById(@PathVariable(value = "userId", required = true) Long userId)
			throws IOException {
		return ResponseEntity.ok(userDetailService.getUserById(userId));
	}

	@GetMapping("/getAllUsers")
	public ResponseEntity<?> getAllUsers() throws IOException {
		return ResponseEntity.ok(userDetailService.getAllUsers());
	}

	@DeleteMapping("/deleteUser/{userId}")
	public ApiResponse deleteUser(@PathVariable(value = "userId", required = true) Long userId) throws IOException {
		return userDetailService.deleteUser(userId);
	}

	@GetMapping("/getAllUsersByRole/{userTypeId}")
	public ResponseEntity<?> getAllUsersByRole(@PathVariable(value = "userTypeId", required = true) Long userTypeId)
			throws IOException {
		return ResponseEntity.ok(userDetailService.getAllUsersByRole(userTypeId));
	}

}
