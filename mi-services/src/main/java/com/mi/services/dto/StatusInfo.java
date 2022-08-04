package com.mi.services.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

public class StatusInfo {

	private String status;

	private String msg;
	
	public StatusInfo() {
		
	}

	public StatusInfo(String status) {

		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
