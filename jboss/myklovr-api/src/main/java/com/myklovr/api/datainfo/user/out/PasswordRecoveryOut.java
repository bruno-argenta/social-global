package com.myklovr.api.datainfo.user.out;

import java.util.Date;

public class PasswordRecoveryOut {

	private String username;
	private String verificationToken;
	private Date expirationTimestamp;
	
	public Date getExpirationTimestamp() {
		return expirationTimestamp;
	}
	public void setExpirationTimestamp(Date expirationTimestamp) {
		this.expirationTimestamp = expirationTimestamp;
	}
	public String getVerificationToken() {
		return verificationToken;
	}
	public void setVerificationToken(String verificationToken) {
		this.verificationToken = verificationToken;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
		
	
	
	
	
}
