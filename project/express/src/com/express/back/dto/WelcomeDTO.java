package com.express.back.dto;

public class WelcomeDTO {
	
	private String memberInfo;
	private String accountInfo;
	
	public WelcomeDTO(){
		this.memberInfo="您好";
		this.accountInfo="您好";
	}

	public String getMemberInfo() {
		return memberInfo;
	}

	public void setMemberInfo(String memberInfo) {
		this.memberInfo = memberInfo;
	}

	public String getAccountInfo() {
		return accountInfo;
	}

	public void setAccountInfo(String accountInfo) {
		this.accountInfo = accountInfo;
	}
}
