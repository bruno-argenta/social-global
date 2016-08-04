package com.myklovr.api.datainfo.out.user;

import java.util.UUID;

public class UserProfileOut {

	private UUID userId;
	private String userKind;
	private SectionOut section;

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
	public SectionOut getSection() {
		return section;
	}
	public void setSection(SectionOut section) {
		this.section = section;
	}
	
}
