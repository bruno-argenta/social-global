package com.myklovr.rest.beans;

import java.util.Map;
import java.util.UUID;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;

import com.myklovr.api.datainfo.SessionDI;
import com.myklovr.api.datainfo.in.user.SectionIn;
import com.myklovr.api.datainfo.in.user.UserProfileIn;
import com.myklovr.api.datainfo.out.user.UserProfileOut;
import com.myklovr.api.user.LoginRegistrationAPI;
import com.myklovr.api.user.SessionAPI;
import com.myklovr.api.user.UserProfileAPI;

@Stateless
@LocalBean
public class UserProfileBean extends VerificationSessionBean {

	private static final String SECTION_KIND = "kind";	
	
	public UserProfileOut getUserProfile(SectionIn section) throws Exception {
		SessionDI session = verfySession(section);
		UUID userId = session.getUserId();
		String sectionName = section.getSectionName();
		UserProfileOut result = null;
		result = UserProfileAPI.getUserProfileSection(userId, sectionName);
		if (result != null){
			result.setUserId(null);
		}else{
			result = new UserProfileOut();
		}				
		return result;
	}

	public void setUserProfile(SectionIn section) throws Exception {
		SessionDI session = verfySession(section);
		UUID userId = session.getUserId();
		UserProfileIn user = new UserProfileIn();
		Map<String,String> values = section.getValues();
		String kind = values.get(SECTION_KIND);
		boolean changeKind = false;
		if (!StringUtils.isEmpty(kind)){
			values.remove(SECTION_KIND);
			user.setUserKind(kind);	
			changeKind = true;
		}else{
			user.setUserKind(session.getKind());
		}
		user.setSection(section);		
		user.setUserId(userId);
		UserProfileAPI.setUserProfileSection(user);
		LoginRegistrationAPI.updateNextPageUser(session.getUsername(),session.getProvider(), section.getNextPage(),user.getUserKind());
		if (changeKind){
			SessionAPI.updateKind(session.getSessionToken(), user.getUserKind());
		}
		
	}


}
