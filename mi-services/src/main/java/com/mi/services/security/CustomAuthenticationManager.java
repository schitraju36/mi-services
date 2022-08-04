package com.mi.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.mi.services.service.UserService;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

	@Autowired
	private UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		UserDetails userDetails = userService.getUserDetails(authentication.getName(),
				(String) authentication.getCredentials());

		return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails, userDetails.getAuthorities());
	}

}
