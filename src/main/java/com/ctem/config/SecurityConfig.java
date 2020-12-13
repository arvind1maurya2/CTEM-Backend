package com.ctem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ctem.security.CustomUserDetailsService;
import com.ctem.security.JWTAuthenticationEntryPoint;
import com.ctem.security.JWTAuthenticationFilter;

/**
 * 
 * @author Arvind Maurya
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JWTAuthenticationEntryPoint unauthorizedHandler;

	/**
	 * @return the customUserDetailsService
	 */
	public CustomUserDetailsService getCustomUserDetailsService() {
		return customUserDetailsService;
	}

	/**
	 * @param customUserDetailsService the customUserDetailsService to set
	 */
	public void setCustomUserDetailsService(CustomUserDetailsService customUserDetailsService) {
		this.customUserDetailsService = customUserDetailsService;
	}

	/**
	 * @return the unauthorizedHandler
	 */
	public JWTAuthenticationEntryPoint getUnauthorizedHandler() {
		return unauthorizedHandler;
	}

	/**
	 * @param unauthorizedHandler the unauthorizedHandler to set
	 */
	public void setUnauthorizedHandler(JWTAuthenticationEntryPoint unauthorizedHandler) {
		this.unauthorizedHandler = unauthorizedHandler;
	}

	@Bean
	public JWTAuthenticationFilter jwtAuthenticationFilter() {
		return new JWTAuthenticationFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/api/basic/**").permitAll()
				.antMatchers("/api/user1/checkUsernameAvailability", "/api/user1/checkEmailAvailability",
						"/api/user1/forgotPassword", "/api/user1/changeUserPassword")
				.permitAll().antMatchers(HttpMethod.GET, "/api/polls/**", "/api/users/**").permitAll().anyRequest()
				.authenticated();

		// Add our custom JWT security filter
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

	}
}