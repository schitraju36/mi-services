package com.mi.services.dto;

public class AuthResp {

	private String username;

	private String token;

	public AuthResp() {
	}

	public AuthResp(String username, String token) {
		this.username = username;
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
