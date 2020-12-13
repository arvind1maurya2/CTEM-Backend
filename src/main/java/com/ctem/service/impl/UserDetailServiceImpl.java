package com.ctem.service.impl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
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
import org.bonitasoft.engine.identity.UserUpdater;
import org.bonitasoft.engine.identity.UserWithContactData;
import org.bonitasoft.engine.profile.Profile;
import org.bonitasoft.engine.profile.ProfileMemberCreator;
import org.bonitasoft.engine.profile.ProfileSearchDescriptor;
import org.bonitasoft.engine.search.SearchOptionsBuilder;
import org.bonitasoft.engine.search.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ctem.constant.StatusMessage;
import com.ctem.entity.BaseEntity;
import com.ctem.entity.Designation;
import com.ctem.entity.District;
import com.ctem.entity.Division;
import com.ctem.entity.Office;
import com.ctem.entity.Role;
import com.ctem.entity.State;
import com.ctem.entity.UserDetail;
import com.ctem.entity.UserEntity;
import com.ctem.exception.BadRequestException;
import com.ctem.exception.UserPrivilegeException;
import com.ctem.payload.ApiResponse;
import com.ctem.payload.LoginRequest;
import com.ctem.payload.SignUpRequest;
import com.ctem.repository.DesignationRepository;
import com.ctem.repository.DistrictRepository;
import com.ctem.repository.DivisionRepository;
import com.ctem.repository.OfficeRepository;
import com.ctem.repository.RoleRepository;
import com.ctem.repository.StateRepository;
import com.ctem.repository.UserRepository;
import com.ctem.service.UserDetailService;
import com.ctem.util.ConversionUtil;
/**
 * 
 * @author Arvind Maurya
 *
 */
@Service
public class UserDetailServiceImpl extends StatusMessage implements UserDetailService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	EmailServiceImpl emailService;
	//Added by Arvind Maurya
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

	@Value("${app.expirationTime}")
	private Long expirationTime;

	@Value("${app.storagePath}")
	private String storagePath;

	@Override
	@Transactional
	public ApiResponse changePassword(Long userId, String password) {
		try {
			UserEntity user = entityManager.find(UserEntity.class, userId);
			user.setPassword(passwordEncoder.encode(password));
			String content = HELLO + " " + StringUtils.capitalize(user.getFirstName()) + CHANGE_PASSWORD_BODY + "\n"
					+ EMAIL + user.getEmail() + "\n" + PASSWORD + password + MANUAL_GREET;
			boolean status = emailService.sendMail(user.getEmail(), CHANGE_PASSWORD_SUBJECT, content);
			entityManager.merge(user);
			entityManager.flush();
			if (status)
				return new ApiResponse(true, "Password" + UPDATED_SUCCESSFULLY_MESSAGE);
			else
				return new ApiResponse(false, "Network Error.");
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResponse(false, "Network Error.");
		}
	}

	/*
	 * @Override
	 * 
	 * @Transactional public ApiResponse changePassword(ChangePasswordModal
	 * changePasswordModal) { User user = entityManager.find(User.class,
	 * BaseEntity.currentUserId.get());
	 * if(StringUtils.isNotBlank(changePasswordModal.getCurrentPassword())) {
	 * if(passwordEncoder.matches(changePasswordModal.getCurrentPassword(),
	 * user.getPassword())) {
	 * user.setPassword(passwordEncoder.encode(changePasswordModal.getNewPassword())
	 * ); entityManager.merge(user); entityManager.flush(); return new
	 * ApiResponse(true, "Password" + UPDATED_SUCCESSFULLY_MESSAGE); }else { return
	 * new ApiResponse(false, INCORRECT_PASSWORD); } } return new ApiResponse(false,
	 * INCORRECT_PASSWORD); }
	 */
	@Override
	public UserEntity findByUsernameOrEmail(String usernameOrEmail) {
		UserEntity user = null;
		@SuppressWarnings("unchecked")
		List<UserEntity> users = entityManager.createNamedQuery("UserEntity.findByUsernameOrEmail")
				.setParameter("email", usernameOrEmail).getResultList();
		if (users != null) {
			user = users.get(0);
		}
		return user;
	}

	@SuppressWarnings("unused")
	@Override
	@Transactional
	public ApiResponse createUser(UserEntity user) throws UserPrivilegeException {
		try {
			// check user who perform this action has authority to create or not
			UserEntity currentUser = entityManager.find(UserEntity.class, BaseEntity.currentUserId.get());
			if (currentUser.getRole().getId() != 1) {
				throw new UserPrivilegeException("You are not authorised to perform this operation.");
			}
//			if (userRepository.existsByUsername(user.getUsername())) {
//				throw new BadRequestException("Username is already taken!");
//			}
			if (userRepository.existsByEmail(user.getEmail())) {
				throw new BadRequestException("Email Address already in use!");
			}
			UserEntity newuser = null;
			// Creating user's account
			//if (user.getMiddleName() == null) {
				newuser = new UserEntity(user.getEmail(), user.getFirstName(), user.getLastName(), user.getUsername(),
						user.getPassword());
			///} else {
			///	newuser = new UserEntity(user.getEmail(), user.getFirstName(), user.getMiddleName(), user.getLastName(),
			///			user.getUsername(), user.getEmail(), user.getPassword());
			///}
			newuser.setArchived(user.isArchived());
			//newuser.setFirstLogin(true);
			newuser.setPassword(passwordEncoder.encode(user.getPassword()));
			Role role = roleRepository.getOne(user.getRoleId());
			newuser.setRole(role);
			String content = HELLO + " " + StringUtils.capitalize(user.getFirstName()) + ACCOUNT_CREATION + "\n" + EMAIL
					+ user.getEmail() + "\n" + PASSWORD + user.getPassword() + MANUAL_GREET;
			boolean status = emailService.sendMail(user.getEmail(), ALMABANI_ACCOUNT_CREATION_SUBJECT, content);
			entityManager.persist(newuser);
			entityManager.flush();
			return new ApiResponse(true, "User" + CREATED_SUCCESSFULLY_MESSAGE);
		} catch (Exception e) {
			return new ApiResponse(false, e.getMessage());
		}
	}

	@Override
	@Transactional
	public ApiResponse updateUser(UserEntity user) {
		try {
			UserEntity olduser = entityManager.find(UserEntity.class, user.getId());
			if (StringUtils.isNotBlank(olduser.getPassword())) {
				user.setPassword(olduser.getPassword());
			}
			Role role = roleRepository.getOne(user.getRoleId());
			user.setRole(role);
			entityManager.merge(user);
			entityManager.flush();
			return new ApiResponse(true, "User" + UPDATED_SUCCESSFULLY_MESSAGE);
		} catch (Exception e) {
			return new ApiResponse(false, UNABLE_TO_FULFIL_REQUEST + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserEntity> getAllUsers() {
		return entityManager.createNamedQuery("UserEntity.getAllUsers").getResultList();
	}

	@Override
	public UserEntity getUserById(Long userId) {
		UserEntity user = entityManager.find(UserEntity.class, userId);
		user.setPassword(null);
		user.setRoleId(user.getRole().getId());
		return user;
	}

	@Override
	@Transactional
	public ApiResponse deleteUser(Long userId) {
		UserEntity user = entityManager.find(UserEntity.class, userId);
		if (user.isArchived()) {
			return new ApiResponse(true, "User" + ALREADY_DELETED_MESSAGE);
		}
		user.setArchived(true);
		entityManager.merge(user);
		entityManager.flush();
		return new ApiResponse(true, "User" + DELETED_SUCCESSFULLY_MESSAGE);

	}

	@SuppressWarnings("unused")
	@Transactional
	@Override
	public ApiResponse forgotPassword(String userNameOrEmail) throws UsernameNotFoundException {
		try {
			UserEntity user = findByUsernameOrEmail(userNameOrEmail);
			if (user == null) {
				throw new UsernameNotFoundException("User not found with username or email : " + userNameOrEmail);
			}
			String verificationCode = ConversionUtil.getAlhpaNumeric(System.currentTimeMillis()).toUpperCase();
			user.setVerificationCode(verificationCode);
			user.setVerified(false);
			Calendar expiryTime = Calendar.getInstance();
			expiryTime.setTimeInMillis(expirationTime);
			String content = HELLO + " " + StringUtils.capitalize(user.getFirstName()) + SEND_VERIFICATION_CODE_EMAIL
					+ "\n" + VERIFICATION_CODE + user.getVerificationCode() + MANUAL_GREET;
			boolean status = emailService.sendMail(user.getEmail(), FORGOT_PASSWORD_SUBJECT, content);
			entityManager.merge(user);
			entityManager.flush();
			return new ApiResponse(true, VERIFICATION_CODE_SEND_MESSAGE);
		} catch (Exception e) {
			return new ApiResponse(false, e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserEntity> getAllUsersByRole(Long userTypeId) {
		List<UserEntity> allusers = entityManager.createNamedQuery("UserEntity.getAllUsersByRole")
				.setParameter("userTypeId", userTypeId).getResultList();
		return allusers;
	}

	@Override
	@Transactional
	public ApiResponse changeUserPassword(@Valid LoginRequest loginRequest) {
		UserEntity user = findByUsernameOrEmail(loginRequest.getUsername());
		user.setPassword(passwordEncoder.encode(loginRequest.getPassword()));
		entityManager.merge(user);
		entityManager.flush();
		return new ApiResponse(true, "Password" + UPDATED_SUCCESSFULLY_MESSAGE);
	}

	/**
	 * @author Arvind Maurya
	 * @since 2020-12-14
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map getUserCreationDateFields() {
		Map map = new HashMap<>();
		try {
			map.put("designation", designationRepository.findAll());
			map.put("district", districtRepository.findAll());
			map.put("division", divisionRepository.findAll());
			map.put("office", officeRepository.findAll());
			map.put("state", stateRepository.findAll());
			map.put("role", roleRepository.findAll());
		} catch (Exception e) {
			map.put("error", new ApiResponse(false,e.getMessage()));
		}
		return map;
	}
	
	/**
	 * @author Arvind Maurya
	 * @since 2020-12-14
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map usersList() {

		Map map = new HashMap<>();
		try {
			List<UserDetail> userDetails = new ArrayList<UserDetail>();
			UserEntity userRepo = null;

			final IdentityAPI identityAPI = BaseEntity.apiClient.get().getIdentityAPI();
			final SearchOptionsBuilder builder = new SearchOptionsBuilder(0, 10000);
			final SearchResult<User> userResults = identityAPI.searchUsers(builder.done());

			for (User u : userResults.getResult()) {
				UserDetail userDetail = new UserDetail();
				userDetail.setUserId(u.getId());
				userDetail.setUserName(u.getUserName());
				userDetail.setFirstName(u.getFirstName());
				userDetail.setLastName(u.getLastName());
				userDetail.setOffice(u.getTitle());
				userDetail.setEnable(u.isEnabled());
				userDetail.setDesignation(u.getJobTitle());

				UserWithContactData proUser = identityAPI.getUserWithProfessionalDetails(u.getId());
				userDetail.setEmail(proUser.getContactData().getEmail());
				userDetail.setGender(proUser.getContactData().getWebsite());
				userDetail.setMobileNumber(proUser.getContactData().getMobileNumber());
				userDetail.setAddress(proUser.getContactData().getAddress());
				userDetail.setOfficeName(officeRepository.findById(Long.parseLong(u.getTitle())).get().getName());
				userDetail.setDivisionName(divisionRepository
						.findById(Long.parseLong(proUser.getContactData().getFaxNumber())).get().getName());
				userDetail.setRoleName(
						roleRepository.findById(Long.parseLong(proUser.getContactData().getRoom())).get().getName());

				try {
					userRepo = userRepository.findByUsername(u.getUserName()).get();
					if (userRepo != null) {
						userDetail.setId(userRepo.getId());
					}
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
				userDetails.add(userDetail);
			}
			map.put("count", userResults.getCount());
			map.put("result", userDetails);
		} catch (UserNotFoundException e) {
			map.put("error", new ApiResponse(false,"User not found"));
		} catch (NumberFormatException e) {
			map.put("error", new ApiResponse(false,e.getMessage()));
		} catch (SearchException e) {
			map.put("error", new ApiResponse(false,e.getMessage()));
		}
		return map;
	}

	/**
	 * @author Arvind Maurya
	 * @since 2020-12-11
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map getBonitaUserById(Long id) {

		Map map = new HashMap<>();
		UserDetail userDetail = new UserDetail();
		UserEntity userEntity = null;
		try {
			try {
				userEntity = userRepository.getOne(id);
			} catch (Exception e) {
				map.put("error", new ApiResponse(false, "Record Not Found with this user id"));
			}
			final IdentityAPI identityAPI = BaseEntity.apiClient.get().getIdentityAPI();
			User bonitaUser = identityAPI.getUserByUserName(userEntity.getUsername());
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
			map.put("user", userDetail);
		} catch (UserNotFoundException e) {
			map.put("error", new ApiResponse(false, e.getMessage()));
		} catch (NumberFormatException e) {
			map.put("error", new ApiResponse(false, e.getMessage()));
		} catch (Exception e) {
			map.put("error", new ApiResponse(false, e.getMessage()));
		}
		return map;
	}

	/**
	 * @author Arvind Maurya
	 * @since 2020-12-14
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map updateUserById(UserDetail u) {
		User user = null;
		Map map = new HashMap<>();
		try {
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
			
			map.put("user", user);
			
		} catch (UserNotFoundException e) {
			map.put("error", new ApiResponse(false, "User not found"));
		} catch (NumberFormatException e) {
			map.put("error", new ApiResponse(false, e.getMessage()));
		} catch (UpdateException e) {
			map.put("error", new ApiResponse(false, e.getMessage()));
		} catch (Exception e) {
			map.put("error", new ApiResponse(false, e.getMessage()));
		}
		return map;
	}

	/**
	 * @author Arvind Maurya
	 * @since 2020-12-14
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map createUser(SignUpRequest signUpRequest) {
		Map map = new HashMap();
		if (StringUtils.isNotBlank(signUpRequest.getUserName())) {
			if (userRepository.existsByUsername(signUpRequest.getUserName())) {
				map.put("error", new ApiResponse(false,"Username is already taken.!"));
				return map;
			}
		}
		/* bonita user creation start */
		User bonitaUser = null;
		String autoGeneratedPassword = "4444";// RandomStringUtils.randomAlphanumeric(15);
		try {
			try {
				if (Long.parseLong(signUpRequest.getRole()) <= 0) {
					map.put("error", new ApiResponse(false,"Please select role"));
					return map;
				}
			} catch (NumberFormatException e) {
				map.put("error", new ApiResponse(false,"Role id should be number"));
				return map;
			}
			IdentityAPI identityAPI = BaseEntity.apiClient.get().getIdentityAPI();
			UserCreator creator = new UserCreator(signUpRequest.getUserName(), autoGeneratedPassword);
			creator.setFirstName(signUpRequest.getFirstName()).setLastName(signUpRequest.getLastName())
					.setEnabled(true);
			creator.setTitle(signUpRequest.getOffice());// Office (DFSL/RFSL)
			creator.setJobTitle(signUpRequest.getDesignation());// Designation
			ContactDataCreator proContactDataCreator = new ContactDataCreator().setEmail(signUpRequest.getEmail())
					.setMobileNumber(signUpRequest.getMobileNumber()).setAddress(signUpRequest.getAddress())
					.setState(signUpRequest.getState()).setRoom(signUpRequest.getRole())
					.setFaxNumber(signUpRequest.getDivision()).setWebsite(signUpRequest.getGender())
					.setBuilding(signUpRequest.getDistrict());
			creator.setProfessionalContactData(proContactDataCreator);
			bonitaUser = identityAPI.createUser(creator);
			// The user must now be registered in a profile. Let's choose the existing
			// "User" profile:
			org.bonitasoft.engine.api.ProfileAPI orgProfileAPI = BaseEntity.apiClient.get().getProfileAPI();
			SearchOptionsBuilder searchOptionsBuilder = new SearchOptionsBuilder(0, 10);
			searchOptionsBuilder.filter(ProfileSearchDescriptor.NAME, "User");// Administrator
			SearchResult<Profile> searchResultProfile = orgProfileAPI.searchProfiles(searchOptionsBuilder.done());

			// we should find one result now
			if (searchResultProfile.getResult().size() != 1) {
				map.put("error", new ApiResponse(false,"User Registered but profile not found or more then one found for mapping with user"));
				return map;
			}

			// now register the user in the profile
			Profile profile = searchResultProfile.getResult().get(0);
			ProfileMemberCreator profileMemberCreator = new ProfileMemberCreator(profile.getId());
			profileMemberCreator.setUserId(bonitaUser.getId());
			orgProfileAPI.createProfileMember(profileMemberCreator);
		} catch (AlreadyExistsException e) {
			map.put("error", new ApiResponse(false,"Username Already Exist"));
		} catch (CreationException e) {
			map.put("error", new ApiResponse(false,e.getMessage()));
		} catch (SearchException e) {
			map.put("error", new ApiResponse(false,e.getMessage()));
		}
		/* bonita user creation end */
		try {
			if (bonitaUser != null && bonitaUser.getId() > 0) {

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
				Designation designation = designationRepository.findById(Long.parseLong(signUpRequest.getDesignation()))
						.get();
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
				map.put("user", result);
			}
		} catch (NumberFormatException e) {
			map.put("error", new ApiResponse(false,e.getMessage()));
		}
		return map;
	}

	/**
	 * @author Arvind Maurya
	 * @since 2020-12-14
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map disableUser(Long id) {
		Map map =new HashMap();
		User user = null;
		try {
			IdentityAPI identityAPI = BaseEntity.apiClient.get().getIdentityAPI();
			UserUpdater updateDescriptor = new UserUpdater();
			updateDescriptor.setEnabled(false);
			user = identityAPI.updateUser(id, updateDescriptor);
			map.put("user", user);
		} catch (UserNotFoundException  e) {
			map.put("error", new ApiResponse(false,"User not found with given id"));
		} catch (UpdateException e) {
			map.put("error", new ApiResponse(false,e.getMessage()));
		} 
		return map;
	}

	/**
	 * @author Arvind Maurya
	 * @since 2020-12-14
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map enableUser(Long id) {
		Map map =new HashMap();
		User user = null;
		try {
			IdentityAPI identityAPI = BaseEntity.apiClient.get().getIdentityAPI();
			UserUpdater updateDescriptor = new UserUpdater();
			updateDescriptor.setEnabled(true);
			user = identityAPI.updateUser(id, updateDescriptor);
			map.put("user", user);
		} catch (UserNotFoundException  e) {
			map.put("error", new ApiResponse(false,"User not found with given id"));
		} catch (UpdateException e) {
			map.put("error", new ApiResponse(false,e.getMessage()));
		} 
		return map;
	}
}