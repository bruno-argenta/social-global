package com.myklovr.api.datainfo.user.out;

import java.util.Date;
import java.util.UUID;

public class SessionOut {

	private UUID userId;
	private String sessionToken;
	private Date expirationTimestamp;
	
	public UUID getUserId() {
		return userId;
	}
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
	public String getSessionToken() {
		return sessionToken;
	}
	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}
	public Date getExpirationTimestamp() {
		return expirationTimestamp;
	}
	public void setExpirationTimestamp(Date expirationTimestamp) {
		this.expirationTimestamp = expirationTimestamp;
	}
		
	
	
	
	
}
