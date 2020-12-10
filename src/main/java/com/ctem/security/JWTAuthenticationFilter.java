package com.ctem.security;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bonitasoft.engine.api.APIClient;
import org.bonitasoft.engine.api.ApiAccessType;
import org.bonitasoft.engine.api.LoginAPI;
import org.bonitasoft.engine.api.TenantAPIAccessor;
import org.bonitasoft.engine.exception.BonitaHomeNotSetException;
import org.bonitasoft.engine.exception.ServerAPIException;
import org.bonitasoft.engine.exception.UnknownAPITypeException;
import org.bonitasoft.engine.platform.LoginException;
import org.bonitasoft.engine.platform.UnknownUserException;
import org.bonitasoft.engine.session.APISession;
import org.bonitasoft.engine.util.APITypeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ctem.entity.BaseEntity;
import com.ctem.entity.UserEntity;
import com.ctem.repository.UserRepository;

/**
 * @author Shashank
 *
 */
public class JWTAuthenticationFilter extends OncePerRequestFilter{
	
	@Value("${app.bonitaURL}")
	private String bonitaURL;
	@Autowired
    private JWTTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    @Autowired
    UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Long userId = tokenProvider.getUserIdFromJWT(jwt);

                UserDetails userDetails = customUserDetailsService.loadUserById(userId);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                BaseEntity.currentUserId.set(userId);
                UserEntity user=  userRepository.findById(userId).get();
                //System.out.println(Base64.getEncoder().encodeToString("4444".getBytes()));
                byte [] encodedPassword = Base64.getDecoder().decode(user.getBonitaAccessToken());
                Map<String, String> settings = new HashMap<String, String>();
    			settings.put("server.url", bonitaURL);
    			settings.put("application.name", "bonita");
    			APITypeManager.setAPITypeAndParams(ApiAccessType.HTTP, settings);
    			LoginAPI loginAPI = TenantAPIAccessor.getLoginAPI();
    			//System.out.println(new String(encodedPassword));
    			APISession apiSession = loginAPI.login(user.getUsername(), new String(encodedPassword));
    			APIClient apiClient = new APIClient();
    			apiClient.login(user.getUsername(), new String(encodedPassword));
    			BaseEntity.apiSession.set(apiSession);
    			BaseEntity.apiClient.set(apiClient);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (UnknownUserException e) {
        	e.getStackTrace();
		} catch (AuthenticationException e) {
			e.getStackTrace();
		} catch (BonitaHomeNotSetException e) {
			e.getStackTrace();
		} catch (ServerAPIException e) {
			e.getStackTrace();
		} catch (UnknownAPITypeException e) {
			e.getStackTrace();
		} catch (LoginException e) {
			e.getStackTrace();
		}
        catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Basic ")) {
            return bearerToken.substring(6, bearerToken.length());
        }
        return null;
    }

}
