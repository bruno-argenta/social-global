package com.myklovr.api.datainfo.in.user;

import java.util.UUID;

public class UserProfileIn {

	private UUID userId;
	private String userKind;
	private SectionIn section;
	
	public UUID getUserId() {
		return userId;
	}
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
	public String getUserKind() {
		return userKind;
	}
	public void setUserKind(String userKind) {
		this.userKind = userKind;
	}
	public SectionIn getSection() {
		return section;
	}
	public void setSection(SectionIn section) {
		this.section = section;
	}
	
}
