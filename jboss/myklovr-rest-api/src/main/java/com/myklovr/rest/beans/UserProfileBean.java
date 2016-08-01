package com.myklovr.rest.beans;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.myklovr.api.datainfo.dictionaries.in.DictionaryIn;
import com.myklovr.api.datainfo.dictionaries.out.DictionaryOut;
import com.myklovr.api.datainfo.user.in.SectionIn;
import com.myklovr.api.datainfo.user.out.SectionOut;
import com.myklovr.api.datainfo.user.out.UserProfileOut;
import com.myklovr.api.dictionaries.DictionariesAPI;
import com.myklovr.api.user.UserProfileAPI;
import com.myklovr.helpers.PropertiesHelper;
import com.myklovr.helpers.constants.AppConstants;
import com.myklovr.helpers.constants.MessagesConstants;
import com.myklovr.helpers.exception.BussinesException;

@Stateless
@LocalBean
public class UserProfileBean {

	public UserProfileOut getUserProfile(UUID userId, String section) throws Exception {
		UserProfileOut result = null;
		switch (section) {
		case AppConstants.USER_PROFILE_SECTION_BASIC_INFO:
		case AppConstants.USER_PROFILE_SECTION_KIND:
		case AppConstants.USER_PROFILE_SECTION_PURPOSE_ACCADEMIC_GROWTH:
		case AppConstants.USER_PROFILE_SECTION_PURPOSE_CAREER_SERVICES:
		case AppConstants.USER_PROFILE_SECTION_PURPOSE_MARKET:
		case AppConstants.USER_PROFILE_SECTION_PURPOSE_PERSONAL_GROWTH:
		case AppConstants.USER_PROFILE_SECTION_PURPOSE_PROFESSIONAL_GROWTH:
		case AppConstants.USER_PROFILE_SECTION_PURPOSE_PROMOTING:
		case AppConstants.USER_PROFILE_SECTION_PURPOSE_RECRUIOTONG_GROWTH:
		case AppConstants.USER_PROFILE_SECTION_PURPOSE_RESEARCH_DEVELOPMENT:
			result = UserProfileAPI.getUserProfileSection(userId, section);
			result.setUserId(null);
			break;
		default:
			throw new BussinesException(
					PropertiesHelper.getStringMessageProperty(MessagesConstants.ERROR_DICTIONARY_NOT_FOUND));
		}

		return result;
	}

	public void setUserProfile(UUID userId, SectionIn section) throws Exception {
		UserProfileOut result = null;
		switch (section.getSectionName()) {
		case AppConstants.USER_PROFILE_SECTION_BASIC_INFO:
		case AppConstants.USER_PROFILE_SECTION_KIND:
		case AppConstants.USER_PROFILE_SECTION_PURPOSE_ACCADEMIC_GROWTH:
		case AppConstants.USER_PROFILE_SECTION_PURPOSE_CAREER_SERVICES:
		case AppConstants.USER_PROFILE_SECTION_PURPOSE_MARKET:
		case AppConstants.USER_PROFILE_SECTION_PURPOSE_PERSONAL_GROWTH:
		case AppConstants.USER_PROFILE_SECTION_PURPOSE_PROFESSIONAL_GROWTH:
		case AppConstants.USER_PROFILE_SECTION_PURPOSE_PROMOTING:
		case AppConstants.USER_PROFILE_SECTION_PURPOSE_RECRUIOTONG_GROWTH:
		case AppConstants.USER_PROFILE_SECTION_PURPOSE_RESEARCH_DEVELOPMENT:
			//result = UserProfileAPI.getUserProfileSection(userId, section);
			result.setUserId(null);
			break;
		default:
			throw new BussinesException(
					PropertiesHelper.getStringMessageProperty(MessagesConstants.ERROR_DICTIONARY_NOT_FOUND));
		}
	}

}
