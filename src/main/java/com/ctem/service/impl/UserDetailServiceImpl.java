package com.ctem.service.impl;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ctem.constant.StatusMessage;
import com.ctem.entity.BaseEntity;
import com.ctem.entity.Role;
import com.ctem.entity.UserEntity;
import com.ctem.exception.BadRequestException;
import com.ctem.exception.UserPrivilegeException;
import com.ctem.payload.ApiResponse;
import com.ctem.payload.LoginRequest;
import com.ctem.repository.RoleRepository;
import com.ctem.repository.UserRepository;
import com.ctem.service.UserDetailService;
import com.ctem.util.ConversionUtil;

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
}