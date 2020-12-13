package com.ctem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctem.entity.UserEntity;
import com.ctem.repository.UserRepository;
import com.ctem.service.UserDetailService;

/**
 * @author Shashank
 *
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserDetailService userDetailService;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		// Let people login with either username or email
		UserEntity user = userDetailService.findByUsernameOrEmail(usernameOrEmail);
		if(user == null) {
			throw new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail);
		}
		return (UserDetails) UserPrincipal.create(user);
	}

	// This method is used by JWTAuthenticationFilter
	@Transactional
	public UserDetails loadUserById(Long id) {
		UserEntity user = userRepository.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id));

		return (UserDetails) UserPrincipal.create(user);
	}
	@Transactional
	public UserDetails userById(Long id) {
		UserEntity user = userRepository.getOne(id);
		return (UserDetails) UserPrincipal.create(user);
	}
	
}