package com.ctem.endpoint;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ctem.entity.AuthorizationRule;
import com.ctem.entity.City;
import com.ctem.entity.District;
import com.ctem.payload.ApiResponse;
import com.ctem.repository.CityRepository;
import com.ctem.repository.DistrictRepository;
import com.ctem.service.AuthenticationService;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationService authenticationService;
	@Autowired
	DistrictRepository districtRepository;
	@Autowired
	CityRepository cityRepository;
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/menus/{userId}")
	public ResponseEntity<?> getSidebarContent(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "userId", required = true) Long userId) {
		if (userId > 0) {
			return ResponseEntity.ok(authenticationService.getAllPermissionsByUserId(Long.parseLong("1")));
		} else {
			return new ResponseEntity(new ApiResponse(false, "userId Can't be blank"), HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/get-district-city-list")
	public ResponseEntity<?> getDistrictCityList(HttpServletRequest request, HttpServletResponse response) {

		List<District> districts = districtRepository.findAll();
		if (districts != null) {
			for (District district : districts) {
				district.setCities(entityManager.createNamedQuery("City.findDistrictId")
						.setParameter("districtId", district.getId()).getResultList());
			}
		}
		return new ResponseEntity<>(districts, HttpStatus.OK);
	}

}