package com.myklovr.rest.beans;

import java.util.Map;
import java.util.UUID;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;

import com.myklovr.api.datainfo.in.user.SectionIn;
import com.myklovr.api.datainfo.in.user.UserProfileIn;
import com.myklovr.api.datainfo.out.user.UserProfileOut;
import com.myklovr.api.user.UserProfileAPI;

@Stateless
@LocalBean
public class UserProfileBean extends VerificationSessionBean {

	private static final String SECTION_KIND = "kind";
	
	public UserProfileOut getUserProfile(SectionIn section) throws Exception {
		UUID userId = verfySession(section); 
		String sectionName = section.getSectionName();
		UserProfileOut result = null;
		result = UserProfileAPI.getUserProfileSection(userId, sectionName);
		result.setUserId(null);		
		return result;
	}

	public void setUserProfile(SectionIn section) throws Exception {
		UUID userId = verfySession(section);
		UserProfileIn user = new UserProfileIn();
		Map<String,String> values = section.getValues();
		String kind = values.get(SECTION_KIND);		
		if (!StringUtils.isEmpty(kind)){
			values.remove(SECTION_KIND);
			user.setUserKind(kind);			
		}else{
			user.setUserKind(StringUtils.EMPTY);
		}
		user.setSection(section);		
		user.setUserId(userId);
		UserProfileAPI.setUserProfileSection(user);
	}


}
