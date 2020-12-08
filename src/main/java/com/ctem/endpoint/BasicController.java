package com.ctem.endpoint;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.bonitasoft.engine.api.APIClient;
import org.bonitasoft.engine.api.ApiAccessType;
import org.bonitasoft.engine.api.IdentityAPI;
import org.bonitasoft.engine.api.LoginAPI;
import org.bonitasoft.engine.api.TenantAPIAccessor;
import org.bonitasoft.engine.exception.BonitaHomeNotSetException;
import org.bonitasoft.engine.exception.ServerAPIException;
import org.bonitasoft.engine.exception.UnknownAPITypeException;
import org.bonitasoft.engine.identity.User;
import org.bonitasoft.engine.identity.UserNotFoundException;
import org.bonitasoft.engine.identity.UserWithContactData;
import org.bonitasoft.engine.platform.LoginException;
import org.bonitasoft.engine.platform.UnknownUserException;
import org.bonitasoft.engine.session.APISession;
import org.bonitasoft.engine.util.APITypeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ctem.constant.StatusMessage;
import com.ctem.entity.BaseEntity;
import com.ctem.entity.UserEntity;
import com.ctem.payload.ApiResponse;
import com.ctem.payload.JwtAuthenticationResponse;
import com.ctem.payload.LoginRequest;
import com.ctem.repository.RoleRepository;
import com.ctem.repository.UserRepository;
import com.ctem.security.JWTTokenProvider;
import com.ctem.service.AuthenticationService;
import com.ctem.service.UserDetailService;

/**
 * @author Shashank
 *
 */

@RestController
@CrossOrigin
@RequestMapping("/api/basic")
public class BasicController extends StatusMessage {

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
	@Autowired
	AuthenticationService authenticationService;

	@SuppressWarnings("unchecked")
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws IOException {
		String msg = "something wents wrong";
		try {
			Map<String, String> settings = new HashMap<String, String>();
			settings.put("server.url", "http://3.6.207.6:8080");
			settings.put("application.name", "bonita");
			APITypeManager.setAPITypeAndParams(ApiAccessType.HTTP, settings);
			LoginAPI loginAPI = TenantAPIAccessor.getLoginAPI();
			// Base64.getEncoder().encodeToString(loginRequest.getUserNameOrEmail().getBytes());
			// encode without padding
			// Base64.getEncoder().withoutPadding().encodeToString(someByteArray);
			// byte [] barr = Base64.getDecoder().decode(encoded);
			APISession apiSession = loginAPI.login(loginRequest.getUsername(), loginRequest.getPassword());

			APIClient apiClient = new APIClient();
			apiClient.login(loginRequest.getUsername(), loginRequest.getPassword());
			IdentityAPI identityAPI = apiClient.getIdentityAPI();
			UserWithContactData proUser = identityAPI.getUserWithProfessionalDetails(apiSession.getUserId());
			// apiClient.getIdentityAPI().getUserByUserName(loginRequest.getUserNameOrEmail());
			if (apiSession.getUserName() != null) {
				Authentication authentication = authenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
								loginRequest.getPassword()));
				UserEntity user = userDetailService.findByUsernameOrEmail(loginRequest.getUsername());
				SecurityContextHolder.getContext().setAuthentication(authentication);
				String jwt = tokenProvider.generateToken(authentication);
				user.setFirstName(proUser.getUser().getFirstName());
				user.setLastName(proUser.getUser().getLastName());
				user.setEmail(proUser.getContactData().getEmail());
				user.setGender(proUser.getContactData().getWebsite());
				user.setMobileNumber(proUser.getContactData().getMobileNumber());
				user.setAddress(proUser.getContactData().getAddress());
				return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, user));
			}
		} catch (LoginException e) {
			msg = "User name or password is not valid!";
		} catch (UserNotFoundException e) {
			msg = e.getMessage();
			e.printStackTrace();
		} catch (BonitaHomeNotSetException e) {
			msg = e.getMessage();
			e.printStackTrace();
		} catch (ServerAPIException e) {
			msg = e.getMessage();
			e.printStackTrace();
		} catch (UnknownAPITypeException e) {
			msg = e.getMessage();
			e.printStackTrace();
		}
		return new ResponseEntity(new ApiResponse(false, msg), HttpStatus.UNAUTHORIZED);
	}
	@GetMapping("/menus")
	public ResponseEntity<?> dashboard(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) String userId) {
		String msg = "Something wents wrong";
		String userId_1="1";
		try {
			return ResponseEntity.ok(authenticationService.getAllPermissionsByUserId(Long.parseLong(userId_1)));
		} catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
	}
}
