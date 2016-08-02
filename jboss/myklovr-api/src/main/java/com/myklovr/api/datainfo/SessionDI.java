package com.myklovr.api.datainfo;

import java.util.Date;
import java.util.UUID;

public class SessionDI {

	private UUID userId;
	private String sessionToken;
	private Date expirationTimestamp;
	private String username;
	private String provider;
	
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
}
