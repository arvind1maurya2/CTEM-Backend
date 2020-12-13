/**
 * 
 */
package com.ctem.endpoint;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.ctem.payload.OfficeDetails;
import com.ctem.service.OfficeService;

/**
 * @author Arvind Maurya
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/api/auth/office")
public class OfficeController {

	@Autowired
	OfficeService officeService;

	/**
	 * @author Arvind Maurya
	 * @since 2020-12-14
	 * @param request
	 * @param response
	 * @param officeDetails
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@PostMapping("/create-office")
	public ResponseEntity<?> createOffice(HttpServletRequest request, HttpServletResponse response,
			@RequestBody(required = false) OfficeDetails officeDetails) {
		Map map = officeService.createOffice(officeDetails);
		if (map.get("error") == null) {
			return ResponseEntity.ok(map.get("office"));
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
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping("/offices-list")
	public ResponseEntity<?> officesList(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		Map map = officeService.officesList();
		if (map.get("error") == null) {
			return ResponseEntity.ok(map.get("office"));
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
	@GetMapping("/get-office-by-id/{id}")
	public ResponseEntity<?> getOfficeById(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "id", required = true) Long id) {
		Map map = officeService.getOfficeById(id);
		if (map.get("error") == null) {
			return ResponseEntity.ok(map.get("office"));
		} else {
			return new ResponseEntity<>(map.get("error"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author Arvind Maurya
	 * @since 2020-12-14
	 * @param request
	 * @param response
	 * @param officeDetails
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings({ "rawtypes" })
	@PutMapping("/update-office-by-id")
	public ResponseEntity<?> updateOfficeById(HttpServletRequest request, HttpServletResponse response,
			@RequestBody(required = false) OfficeDetails officeDetails) throws SQLException {
		Map map = officeService.updateOfficeById(officeDetails);
		if (map.get("error") == null) {
			return ResponseEntity.ok(map.get("office"));
		} else {
			return new ResponseEntity<>(map.get("error"), HttpStatus.BAD_REQUEST);
		}
	}

}
