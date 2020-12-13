package com.ctem.endpoint;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ctem.entity.UserDetail;
import com.ctem.payload.SignUpRequest;
import com.ctem.service.UserDetailService;

/**
 * @author ARVIND MAURYA
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/api/auth/user")
public class UserController {

	@Autowired
	UserDetailService userDetailService;

	/**
	 * @author Arvind Maurya
	 * @since 2020-12-14
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping("/create-user")
	public ResponseEntity<?> createUser(HttpServletRequest request, HttpServletResponse response) {
		Map map = userDetailService.getUserCreationDateFields();
		if (map.get("error") == null) {
			return ResponseEntity.ok(map);
		} else {
			return new ResponseEntity<>(map.get("error"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author Arvind Maurya
	 * @since 2020-12-14
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping("/users-list")
	public ResponseEntity<?> usersList(HttpServletRequest request, HttpServletResponse response) {
		Map map = userDetailService.usersList();
		if (map.get("error") == null) {
			return ResponseEntity.ok(map);
		} else {
			return new ResponseEntity<>(map.get("error"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author Arvind Maurya
	 * @since 2020-12-14
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping("/get-user-by-id/{id}")
	public ResponseEntity<?> getBonitaUserById(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "id", required = true) Long id) {
		Map map = userDetailService.getBonitaUserById(id);
		if (map.get("error") == null) {
			return ResponseEntity.ok(map.get("user"));
		} else {
			return new ResponseEntity<>(map.get("error"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author Arvind Maurya
	 * @since 2020-12-14
	 * @param request
	 * @param response
	 * @param userDetail
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@PutMapping("/update-user-by-id")
	public ResponseEntity<?> updateUserById(HttpServletRequest request, HttpServletResponse response,
			@RequestBody(required = false) UserDetail userDetail) {
		Map map = userDetailService.updateUserById(userDetail);
		if (map.get("error") == null) {
			return ResponseEntity.ok(map.get("user"));
		} else {
			return new ResponseEntity<>(map.get("error"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author Arvind Maurya
	 * @since 2020-12-14
	 * @param signUpRequest
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes" })
	@PostMapping("/create-user")
	public ResponseEntity<?> createUser(@Valid @RequestBody SignUpRequest signUpRequest) throws IOException {
		Map map = userDetailService.createUser(signUpRequest);
		if (map.get("error") == null) {
			return ResponseEntity.ok(map.get("user"));
		} else {
			return new ResponseEntity<>(map.get("error"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author Arvind Maurya
	 * @since 2020-12-14
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping("/disable-user-by-id/{id}")
	public ResponseEntity<?> disableUser(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "id", required = true) Long id) {
		Map map = userDetailService.disableUser(id);
		if (map.get("error") == null) {
			return ResponseEntity.ok(map.get("user"));
		} else {
			return new ResponseEntity<>(map.get("error"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author Arvind Maurya
	 * @since 2020-12-14
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping("/enable-user-by-id/{id}")
	public ResponseEntity<?> enableUser(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "id", required = true) Long id) {
		Map map = userDetailService.enableUser(id);
		if (map.get("error") == null) {
			return ResponseEntity.ok(map.get("user"));
		} else {
			return new ResponseEntity<>(map.get("error"), HttpStatus.BAD_REQUEST);
		}
	}

}