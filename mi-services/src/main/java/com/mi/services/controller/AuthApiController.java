package com.mi.services.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mi.services.dto.AuthRequest;
import com.mi.services.dto.AuthResp;
import com.mi.services.util.JwtTokenUtil;

@RestController
@RequestMapping(path = "api/public")
public class AuthApiController {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenUtil jwtTokenUtil;

	public AuthApiController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil;

	}

	@PostMapping("login")
	public ResponseEntity<AuthResp> login(@RequestBody AuthRequest request) {
		try {
			Authentication authenticate = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

			UserDetails user = (UserDetails) authenticate.getCredentials();

			String token = jwtTokenUtil.generateToken(user);

			return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token)
					.body(new AuthResp(user.getUsername(), token));
		} catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

}