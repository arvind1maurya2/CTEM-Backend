package com.ctem.endpoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.bonitasoft.engine.api.IdentityAPI;
import org.bonitasoft.engine.exception.AlreadyExistsException;
import org.bonitasoft.engine.exception.CreationException;
import org.bonitasoft.engine.exception.SearchException;
import org.bonitasoft.engine.exception.UpdateException;
import org.bonitasoft.engine.identity.ContactDataCreator;
import org.bonitasoft.engine.identity.ContactDataUpdater;
import org.bonitasoft.engine.identity.User;
import org.bonitasoft.engine.identity.UserCreator;
import org.bonitasoft.engine.identity.UserNotFoundException;
import org.bonitasoft.engine.identity.UserSearchDescriptor;
import org.bonitasoft.engine.identity.UserUpdater;
import org.bonitasoft.engine.identity.UserWithContactData;
import org.bonitasoft.engine.profile.Profile;
import org.bonitasoft.engine.profile.ProfileMemberCreator;
import org.bonitasoft.engine.profile.ProfileSearchDescriptor;
import org.bonitasoft.engine.search.SearchOptionsBuilder;
import org.bonitasoft.engine.search.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ctem.entity.BaseEntity;
import com.ctem.entity.Designation;
import com.ctem.entity.District;
import com.ctem.entity.Division;
import com.ctem.entity.Office;
import com.ctem.entity.Role;
import com.ctem.entity.State;
import com.ctem.entity.UserDetail;
import com.ctem.entity.UserEntity;
import com.ctem.payload.ApiResponse;
import com.ctem.payload.SignUpRequest;
import com.ctem.repository.DesignationRepository;
import com.ctem.repository.DistrictRepository;
import com.ctem.repository.DivisionRepository;
import com.ctem.repository.OfficeRepository;
import com.ctem.repository.RoleRepository;
import com.ctem.repository.StateRepository;
import com.ctem.repository.UserRepository;
import com.ctem.service.AuthenticationService;

/**
 * @author ARVIND MAURYA
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/api/auth/user")
public class IdentityController {

	@Autowired
	AuthenticationService authenticationService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	OfficeRepository officeRepository;
	
	@Autowired
	DesignationRepository designationRepository;
	@Autowired
	DistrictRepository districtRepository;
	@Autowired
	DivisionRepository divisionRepository;
	@Autowired
	StateRepository stateRepository;
	// @Autowired
	// RoleRepo roleRepo;

	@GetMapping("/create-user")
	public ResponseEntity<?> dashboard(HttpServletRequest request, HttpServletResponse response) {
		String msg = "Something wents wrong";
		try {
			Map m = new HashMap<>();
			m.put("designation", designationRepository.findAll());
			m.put("district", districtRepository.findAll());
			m.put("division", divisionRepository.findAll());
			m.put("office", officeRepository.findAll());
			m.put("state", stateRepository.findAll());
			m.put("role", roleRepository.findAll());
			return new ResponseEntity<>(m, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/create-user1")
	public ResponseEntity<?> createUser(HttpServletRequest request, HttpServletResponse response,
			@RequestBody(required = false) UserDetail u) {
		User user = null;
		String msg = "Something wents wrong";
		try {
			//APIClient apiClient = new APIClient();
			//apiClient.login(BaseEntity.currentuserName.get(),
			//		Base64.getEncoder().encodeToString(BaseEntity.currentuserName.get().getBytes()));

			IdentityAPI identityAPI = BaseEntity.apiClient.get().getIdentityAPI();
			UserCreator creator = new UserCreator(u.getUserName(),
					Base64.getEncoder().encodeToString(u.getUserName().getBytes()));
			
			creator.setFirstName(u.getFirstName()).setLastName(u.getLastName()).setEnabled(true);
			creator.setTitle(u.getOffice());// Office (DFSL/RFSL)
			creator.setJobTitle(u.getDesignation());// Designation
			ContactDataCreator proContactDataCreator = new ContactDataCreator().setEmail(u.getEmail())
					.setMobileNumber(u.getMobileNumber()).setAddress(u.getAddress()).setState(u.getState())
					.setRoom(u.getRole()).setFaxNumber(u.getDivision()).setWebsite(u.getGender())
					.setBuilding(u.getDistrict());
			creator.setProfessionalContactData(proContactDataCreator);
			user = identityAPI.createUser(creator);

			// The user must now be registered in a profile. Let's choose the existing
			// "User" profile:
			org.bonitasoft.engine.api.ProfileAPI orgProfileAPI = BaseEntity.apiClient.get().getProfileAPI();
			SearchOptionsBuilder searchOptionsBuilder = new SearchOptionsBuilder(0, 10);
			System.out.println(searchOptionsBuilder);
			searchOptionsBuilder.filter(ProfileSearchDescriptor.NAME, "User");// Administrator
			SearchResult<Profile> searchResultProfile = orgProfileAPI.searchProfiles(searchOptionsBuilder.done());

			// we should find one result now
			if (searchResultProfile.getResult().size() != 1) {
				msg = "User Registered but profile not found for mapping with user";
				return new ResponseEntity<>(msg, HttpStatus.OK);
			}

			// now register the user in the profile
			Profile profile = searchResultProfile.getResult().get(0);
			ProfileMemberCreator profileMemberCreator = new ProfileMemberCreator(profile.getId());
			profileMemberCreator.setUserId(user.getId());
			orgProfileAPI.createProfileMember(profileMemberCreator);
			System.out.println(user.getId());
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (CreationException | SearchException e) {
			e.printStackTrace();
			msg = e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/users-list")
	public ResponseEntity<?> searchUsers(HttpServletRequest request, HttpServletResponse response) {
		String msg = "Something wents wrong";
		try {
			//APIClient apiClient = new APIClient();
			//System.out.println(BaseEntity.currentuserName.get());
			//apiClient.login(BaseEntity.currentuserName.get(),
				//	Base64.getEncoder().encodeToString(BaseEntity.currentuserName.get().getBytes()));
			Map map = new HashMap<>();
			List<UserDetail> userDetails = new ArrayList<UserDetail>();
			final IdentityAPI identityAPI = BaseEntity.apiClient.get().getIdentityAPI();
			final SearchOptionsBuilder builder = new SearchOptionsBuilder(0, 100);
			//builder.filter(UserSearchDescriptor.ENABLED, true);
			final SearchResult<User> userResults = identityAPI.searchUsers(builder.done());
			map.put("count", userResults.getCount());
			for (User u : userResults.getResult()) {
				UserDetail userDetail = new UserDetail();
				userDetail.setUserId(u.getId());
				userDetail.setUserName(u.getUserName());
				userDetail.setFirstName(u.getFirstName());
				userDetail.setLastName(u.getLastName());
				//userDetail.setOffice(u.getTitle());
				userDetail.setEnable(u.isEnabled());
				//userDetail.setOfficeObj(officeRepository.findById(Long.parseLong(u.getTitle())).get());
				userDetail.setOfficeName(officeRepository.findById(Long.parseLong(u.getTitle())).get().getName());
				//if (u.getJobTitle().equalsIgnoreCase("1")) {
				//	userDetail.setDesignation("Manager Designation");
				//} else if (u.getJobTitle().equalsIgnoreCase("2")) {
				//	userDetail.setDesignation("senior officer Designation");
				//} else {
					//userDetail.setDesignation(u.getJobTitle());
				//}
				UserWithContactData proUser = identityAPI.getUserWithProfessionalDetails(u.getId());
				//userDetail.setDivisionObj(divisionRepository.findById(Long.parseLong(proUser.getContactData().getFaxNumber())).get());
				userDetail.setDivisionName(divisionRepository.findById(Long.parseLong(proUser.getContactData().getFaxNumber())).get().getName());
				
				//userDetail.setRoleObj(roleRepository.findById(Long.parseLong(proUser.getContactData().getRoom())).get());
				userDetail.setRoleName(roleRepository.findById(Long.parseLong(proUser.getContactData().getRoom())).get().getName());
				
				
				userDetail.setEmail(proUser.getContactData().getEmail());
				userDetail.setGender(proUser.getContactData().getWebsite());
				userDetail.setMobileNumber(proUser.getContactData().getMobileNumber());
				userDetail.setAddress(proUser.getContactData().getAddress());
				UserEntity userRepo=null;
				try {
					userRepo=userRepository.findByUsername(u.getUserName()).get();
					if(userRepo!=null) {
					//System.out.println(userRepo.getId());
					userDetail.setId(userRepo.getId());
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				userDetails.add(userDetail);
			}
			map.put("result", userDetails);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (SearchException e) {
			e.printStackTrace();
			msg = e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
		} 
		return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/get-user-by-id")
	public ResponseEntity<?> getUserByUserId(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "userId", required = true) Long userId) {
		String msg = "Something wents wrong";
		UserDetail userDetail = new UserDetail();
		try {
			UserEntity u=userRepository.getOne(userId);
			System.out.println(u.getUsername());
			//APIClient apiClient = new APIClient();
			//apiClient.login(BaseEntity.currentuserName.get(),
			//		Base64.getEncoder().encodeToString(BaseEntity.currentuserName.get().getBytes()));
			final IdentityAPI identityAPI = BaseEntity.apiClient.get().getIdentityAPI();
			UserWithContactData proUser = identityAPI.getUserWithProfessionalDetails(userId);
			userDetail.setUserId(proUser.getUser().getId());
			userDetail.setUserName(proUser.getUser().getUserName());
			userDetail.setFirstName(proUser.getUser().getFirstName());
			userDetail.setLastName(proUser.getUser().getLastName());
			userDetail.setGender(proUser.getContactData().getWebsite());
			userDetail.setDistrict(proUser.getContactData().getBuilding());
			userDetail.setState(proUser.getContactData().getState());
			userDetail.setOffice(proUser.getUser().getTitle());
			userDetail.setDesignation(proUser.getUser().getJobTitle());
			userDetail.setEmail(proUser.getContactData().getEmail());
			userDetail.setMobileNumber(proUser.getContactData().getMobileNumber());
			userDetail.setAddress(proUser.getContactData().getAddress());
			userDetail.setRole(proUser.getContactData().getRoom());
			userDetail.setDivision(proUser.getContactData().getFaxNumber());
			return new ResponseEntity<>(userDetail, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			msg = "Can't find the user";
			//e.printStackTrace();
		} catch (NumberFormatException e) {
			msg = e.getMessage();
			//e.printStackTrace();
		}
		return new ResponseEntity(new ApiResponse(false, msg), HttpStatus.BAD_REQUEST);
	}
	@GetMapping("/get-user-by-id/{id}")
	public ResponseEntity<?> getUserById(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "id", required = true) Long id) {
		String msg = "Something wents wrong";
		UserDetail userDetail = new UserDetail();
		try {
			
			UserEntity u = null;
			try {
				u = userRepository.getOne(id);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				msg="Record Not Found";
				return new ResponseEntity(new ApiResponse(false, msg), HttpStatus.BAD_REQUEST);
			}
			final IdentityAPI identityAPI = BaseEntity.apiClient.get().getIdentityAPI();
			User bonitaUser=identityAPI.getUserByUserName(u.getUsername());
			UserWithContactData proUser = identityAPI.getUserWithProfessionalDetails(bonitaUser.getId());
			userDetail.setId(id);
			userDetail.setUserId(proUser.getUser().getId());
			userDetail.setUserName(proUser.getUser().getUserName());
			userDetail.setFirstName(proUser.getUser().getFirstName());
			userDetail.setLastName(proUser.getUser().getLastName());
			userDetail.setGender(proUser.getContactData().getWebsite());
			userDetail.setDistrict(proUser.getContactData().getBuilding());
			userDetail.setState(proUser.getContactData().getState());
			userDetail.setOffice(proUser.getUser().getTitle());
			userDetail.setDesignation(proUser.getUser().getJobTitle());
			userDetail.setEmail(proUser.getContactData().getEmail());
			userDetail.setMobileNumber(proUser.getContactData().getMobileNumber());
			userDetail.setAddress(proUser.getContactData().getAddress());
			userDetail.setRole(proUser.getContactData().getRoom());
			userDetail.setDivision(proUser.getContactData().getFaxNumber());
			return new ResponseEntity<>(userDetail, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			msg = "Can't find the user";
			//e.printStackTrace();
		} catch (NumberFormatException e) {
			msg = e.getMessage();
			//e.printStackTrace();
		}
		return new ResponseEntity(new ApiResponse(false, msg), HttpStatus.BAD_REQUEST);
	}

	@PutMapping("/update-user-by-id")
	public ResponseEntity<?> updateUser(HttpServletRequest request, HttpServletResponse response,
			@RequestBody(required = false) UserDetail u) {
		User user = null;
		String msg = "Something wents wrong";
		try {
			//APIClient apiClient = new APIClient();
			//apiClient.login(BaseEntity.currentuserName.get(),
			//		Base64.getEncoder().encodeToString(BaseEntity.currentuserName.get().getBytes()));
			IdentityAPI identityAPI = BaseEntity.apiClient.get().getIdentityAPI();
			UserUpdater updateDescriptor = new UserUpdater();
			ContactDataUpdater proContactDataUpdater = new ContactDataUpdater();

			if (u.getFirstName() != null) {
				updateDescriptor.setFirstName(u.getFirstName());
			}
			if (u.getLastName() != null) {
				updateDescriptor.setLastName(u.getLastName());
			}
			if (u.getOffice() != null) {
				updateDescriptor.setTitle(u.getOffice());
			}
			if (u.getDesignation() != null) {
				updateDescriptor.setJobTitle(u.getDesignation());
			}
			if (u.getEmail() != null) {
				proContactDataUpdater.setEmail(u.getEmail());
			}
			if (u.getMobileNumber() != null) {
				proContactDataUpdater.setMobileNumber(u.getMobileNumber());
			}
			if (u.getAddress() != null) {
				proContactDataUpdater.setAddress(u.getAddress());
			}
			if (u.getRole() != null) {
				proContactDataUpdater.setRoom(u.getRole());
			}
			if (u.getDivision() != null) {
				proContactDataUpdater.setFaxNumber(u.getDivision());
			}
			if (u.getGender() != null) {
				proContactDataUpdater.setWebsite(u.getGender());
			}
			if (u.getEmail() != null || u.getMobileNumber() != null || u.getAddress() != null || u.getRole() != null
					|| u.getDivision() != null) {
				updateDescriptor.setProfessionalContactData(proContactDataUpdater);
			}
			user = identityAPI.updateUser(u.getUserId(), updateDescriptor);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (UserNotFoundException | NumberFormatException | UpdateException e) {
			e.printStackTrace();
			msg = e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/create-user")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) throws IOException {
		if(StringUtils.isNotBlank(signUpRequest.getUserName())) {
			if (userRepository.existsByUsername(signUpRequest.getUserName())) {
				return new ResponseEntity(new ApiResponse(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
			}
		}
		/* bonita user creation start */
		String msg = "Something wents wrong";
		User bonitaUser=null;
		String autoGeneratedPassword="4444";
		try {
			try {
				if(Long.parseLong(signUpRequest.getRole())<=0) {
					return new ResponseEntity(new ApiResponse(false, "please select role"), HttpStatus.BAD_REQUEST);
				}
			} catch (NumberFormatException e) {
				return new ResponseEntity(new ApiResponse(false, "Role id should be number"), HttpStatus.BAD_REQUEST);
			}
			//if(signUpRequest.getDivision().getClass().getName())
			
			IdentityAPI identityAPI = BaseEntity.apiClient.get().getIdentityAPI();
			UserCreator creator = new UserCreator(signUpRequest.getUserName(), autoGeneratedPassword);
			creator.setFirstName(signUpRequest.getFirstName()).setLastName(signUpRequest.getLastName()).setEnabled(true);
			creator.setTitle(signUpRequest.getOffice());// Office (DFSL/RFSL)
			creator.setJobTitle(signUpRequest.getDesignation());// Designation
			ContactDataCreator proContactDataCreator = new ContactDataCreator().setEmail(signUpRequest.getEmail())
					.setMobileNumber(signUpRequest.getMobileNumber()).setAddress(signUpRequest.getAddress()).setState(signUpRequest.getState())
					.setRoom(signUpRequest.getRole()).setFaxNumber(signUpRequest.getDivision()).setWebsite(signUpRequest.getGender())
					.setBuilding(signUpRequest.getDistrict());
			creator.setProfessionalContactData(proContactDataCreator);
			bonitaUser = identityAPI.createUser(creator);

			// The user must now be registered in a profile. Let's choose the existing
			// "User" profile:
			org.bonitasoft.engine.api.ProfileAPI orgProfileAPI = BaseEntity.apiClient.get().getProfileAPI();
			SearchOptionsBuilder searchOptionsBuilder = new SearchOptionsBuilder(0, 10);
			System.out.println(searchOptionsBuilder);
			searchOptionsBuilder.filter(ProfileSearchDescriptor.NAME, "User");// Administrator
			SearchResult<Profile> searchResultProfile = orgProfileAPI.searchProfiles(searchOptionsBuilder.done());

			// we should find one result now
			if (searchResultProfile.getResult().size() != 1) {
				msg="User Registered but profile not found for mapping with user";
				return new ResponseEntity(new ApiResponse(false, msg), HttpStatus.BAD_REQUEST);
			}

			// now register the user in the profile
			Profile profile = searchResultProfile.getResult().get(0);
			ProfileMemberCreator profileMemberCreator = new ProfileMemberCreator(profile.getId());
			profileMemberCreator.setUserId(bonitaUser.getId());
			orgProfileAPI.createProfileMember(profileMemberCreator);
			System.out.println(bonitaUser.getId());
		} catch (AlreadyExistsException e) {
			
			msg="Username is already taken.";
			//e.printStackTrace();
		} catch (CreationException e) {
			msg=e.getMessage();
			e.printStackTrace();
		} catch (SearchException e) {
			msg=e.getMessage();
			e.printStackTrace();
		}
		/* bonita user creation end */
		try {
			if(bonitaUser!=null && bonitaUser.getId()>0) {
			 
			UserEntity user = new UserEntity();
			user.setUsername(signUpRequest.getUserName());
			user.setPassword(passwordEncoder.encode(autoGeneratedPassword));
			user.setBonitaAccessToken(Base64.getEncoder().encodeToString(autoGeneratedPassword.getBytes()));
			user.setEnabled(true);
			user.setArchived(false);
			Role role = roleRepository.findById(Long.parseLong(signUpRequest.getRole())).get();
			user.setRole(role);
			Office office = officeRepository.findById(Long.parseLong(signUpRequest.getOffice())).get();
			user.setOffice(office);
			Designation designation = designationRepository.findById(Long.parseLong(signUpRequest.getDesignation())).get();
			user.setDesignation(designation);
			Division division = divisionRepository.findById(Long.parseLong(signUpRequest.getRole())).get();
			user.setDivision(division);
			District district = districtRepository.findById(Long.parseLong(signUpRequest.getRole())).get();
			user.setDistrict(district);
			State state = stateRepository.findById(Long.parseLong(signUpRequest.getRole())).get();
			user.setState(state);
			
			UserEntity result = userRepository.save(user);
			result.setEmail(signUpRequest.getEmail());
			result.setFirstName(signUpRequest.getFirstName());
			result.setLastName(signUpRequest.getLastName());
			result.setGender(signUpRequest.getGender());
			result.setMobileNumber(signUpRequest.getMobileNumber());
			result.setAddress(signUpRequest.getAddress());
			return new ResponseEntity<>(result, HttpStatus.OK);
			}else {
				return new ResponseEntity(new ApiResponse(false, msg), HttpStatus.BAD_REQUEST);
			}
		} catch (NumberFormatException e) {
			msg=e.getMessage();
			e.printStackTrace();
		}
		return new ResponseEntity(new ApiResponse(false, msg), HttpStatus.BAD_REQUEST);
		//URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/{username}")
		//		.buildAndExpand(result.getUsername()).toUri();
		//return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
	}
	
	
	
	
	@PostMapping("/disable-user-by-id/{id}")
	public ResponseEntity<?> disableUser(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "id", required = true) Long id) {
		User user = null;
		String msg = "Something wents wrong";
		try {
			IdentityAPI identityAPI = BaseEntity.apiClient.get().getIdentityAPI();
			UserUpdater updateDescriptor = new UserUpdater();
			updateDescriptor.setEnabled(false);
			user = identityAPI.updateUser(id, updateDescriptor);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (UserNotFoundException  e) {
			e.printStackTrace();
			msg = "User not found with given id";
		} catch (UpdateException e) {
			e.printStackTrace();
			msg = e.getMessage();
		} 
		return new ResponseEntity(new ApiResponse(false, msg), HttpStatus.BAD_REQUEST);
	}
	@PostMapping("/enable-user-by-id/{id}")
	public ResponseEntity<?> enableUser(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "id", required = true) Long id) {
		User user = null;
		String msg = "Something wents wrong";
		try {
			IdentityAPI identityAPI = BaseEntity.apiClient.get().getIdentityAPI();
			UserUpdater updateDescriptor = new UserUpdater();
			updateDescriptor.setEnabled(true);
			user = identityAPI.updateUser(id, updateDescriptor);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (UserNotFoundException  e) {
			e.printStackTrace();
			msg = "User not found with given id";
		} catch (UpdateException e) {
			e.printStackTrace();
			msg = e.getMessage();
		} 
		return new ResponseEntity(new ApiResponse(false, msg), HttpStatus.BAD_REQUEST);
	}
	
	
	
	
	
	
	
	
}