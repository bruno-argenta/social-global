package com.myklovr.api.datainfo.out.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserProfileOut {

	private UUID userId;
	private String userKind;
	private List<SectionOut> sections;
	
	public UserProfileOut(){
		sections = new ArrayList<SectionOut>(); 
	}
	
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
	public List<SectionOut> getSections() {
		return sections;
	}
	public void setSections(List<SectionOut> sections) {
		this.sections = sections;
	}
	
}
