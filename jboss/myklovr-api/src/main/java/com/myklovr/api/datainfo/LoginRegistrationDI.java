package com.myklovr.api.datainfo;

import java.util.Date;
import java.util.UUID;

public class LoginRegistrationDI {

	private UUID userId;
	private String userName;
	private String password;
	private String provider;
	private int wrongPassCounter;
	private Date userCrationTimestamp;
	private Boolean accountBlocked;
	private String name;
	private String urlImageProfile;
	private String nextPage;
	private String kind;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrlImageProfile() {
		return urlImageProfile;
	}
	public void setUrlImageProfile(String urlImageProfile) {
		this.urlImageProfile = urlImageProfile;
	}
	public String getNextPage() {
		return nextPage;
	}
	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}	
}
