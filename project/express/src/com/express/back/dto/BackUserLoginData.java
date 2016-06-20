package com.express.back.dto;

public class BackUserLoginData {

	private String sessionId;
	private String username;
	private String password;
	private String checkcode;
	private String ErrorInfo;
	private int RememberMe;
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCheckcode() {
		return checkcode;
	}
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	public String getErrorInfo() {
		return ErrorInfo;
	}
	public void setErrorInfo(String errorInfo) {
		ErrorInfo = errorInfo;
	}
	public int getRememberMe() {
		return RememberMe;
	}
	public void setRememberMe(int rememberMe) {
		RememberMe = rememberMe;
	}
}
