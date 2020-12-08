package com.ctem.endpoint;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ctem.payload.ApiResponse;
import com.ctem.service.AuthenticationService;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {
 
	@Autowired
	AuthenticationService authenticationService;

	@GetMapping("/menus/{userId}")
	public ResponseEntity<?> getSidebarContent(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "userId", required = true) Long userId) {
		if (userId > 0) {
			return ResponseEntity.ok(authenticationService.getAllPermissionsByUserId(Long.parseLong("1")));
		} else {
			return new ResponseEntity(new ApiResponse(false, "userId Can't be blank"), HttpStatus.BAD_REQUEST);
		}
	}

}