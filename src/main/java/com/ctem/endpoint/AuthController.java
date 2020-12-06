package com.ctem.endpoint;
import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ctem.constant.StatusMessage;
import com.ctem.entity.User;
import com.ctem.payload.JwtAuthenticationResponse;
import com.ctem.payload.LoginRequest;
import com.ctem.repository.RoleRepository;
import com.ctem.repository.UserRepository;
import com.ctem.security.JWTTokenProvider;
import com.ctem.service.UserDetailService;

/**
 * @author Shashank
 *
 */

@RestController
@CrossOrigin
@RequestMapping("/api/basic")
public class AuthController extends StatusMessage {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserDetailService userDetailService;

	@Autowired
	RoleRepository roleService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JWTTokenProvider tokenProvider;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws IOException {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserNameOrEmail(), loginRequest.getPassword()));
		User user = userDetailService.findByUsernameOrEmail(loginRequest.getUserNameOrEmail());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, user));
	}
}
