package com.mi.services.dto;

import java.util.Date;

public class InvestorDTO {

	private Long id;

	private String name;

	private Date dob;

	private String mobile;

	private String email;

	private String address;

	public InvestorDTO() {

	}

	public InvestorDTO(Long id, String name, Date dob, String mobile, String email, String address) {
		super();
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.mobile = mobile;
		this.email = email;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
