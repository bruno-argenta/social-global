package com.myklovr.api.datainfo.out.user;

import com.myklovr.api.datainfo.LoginRegistrationDI;

public class LoginOut {

	private String name;
	private String urlImageProfile;
	private String kind;
	private String sessionToken;
	private String nextPage;
	
	
	public LoginOut(LoginRegistrationDI user){
		name = user.getName();
		urlImageProfile = user.getUrlImageProfile();
		kind = user.getKind();
		nextPage = user.getNextPage();
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
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getSessionToken() {
		return sessionToken;
	}
	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}
	public String getNextPage() {
		return nextPage;
	}
	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}
		
}
