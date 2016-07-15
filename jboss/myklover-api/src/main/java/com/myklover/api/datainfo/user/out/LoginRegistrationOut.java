package com.myklover.api.datainfo.user.out;

import java.util.Date;
import java.util.UUID;

import com.datastax.driver.core.LocalDate;

public class LoginRegistrationOut {

	
	
	private UUID userId;
	private String userName;
	private String password;
	private String provider;
	private int wrongPassCounter;
	private Date userCrationTimestamp;
	private Boolean accountBlocked;
	public UUID getUserId() {
		return userId;
	}
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public int getWrongPassCounter() {
		return wrongPassCounter;
	}
	public void setWrongPassCounter(int wrongPassCounter) {
		this.wrongPassCounter = wrongPassCounter;
	}
	public Date getUserCrationTimestamp() {
		return userCrationTimestamp;
	}
	public void setUserCrationTimestamp(Date userCrationTimestamp) {
		this.userCrationTimestamp = userCrationTimestamp;
	}
	public Boolean getAccountBlocked() {
		return accountBlocked;
	}
	public void setAccountBlocked(Boolean accountBlocked) {
		this.accountBlocked = accountBlocked;
	}
	
	
	
	
}
