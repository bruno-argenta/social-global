package com.myklovr.api.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.myklovr.api.GenericAPI;
import com.myklovr.api.datainfo.in.user.UserProfileIn;
import com.myklovr.api.datainfo.out.user.SectionOut;
import com.myklovr.api.datainfo.out.user.UserProfileOut;;

public class UserProfileAPI extends GenericAPI{

	
	
	public static UserProfileOut getUserProfile(UUID userId){
		StringBuffer statement = new StringBuffer();
		statement.append("SELECT userid,user_kind,section,value FROM user_profile WHERE userid = ?");
		List<Object> args = new ArrayList<Object>();
		args.add(userId);
		ResultSet result = executeStatement(statement.toString(), args);
		UserProfileOut user = null;
		for (Row row : result) {
			user = getElement(row,user);
		}
		return user;	
	}
	
	public static UserProfileOut getUserProfileSection(UUID userId, String section){
		StringBuffer statement = new StringBuffer();
		statement.append("SELECT userid,user_kind,section,value FROM user_profile WHERE userid = ? and section = ?");
		List<Object> args = new ArrayList<Object>();
		args.add(userId);
		args.add(section);
		ResultSet result = executeStatement(statement.toString(), args);
		UserProfileOut user = null;
		for (Row row : result) {
			user = getElement(row,user);
		}
		return user;	
	}	
	
	public static void setUserProfileSection(UserProfileIn user){
		StringBuffer statement = new StringBuffer();
		StringBuffer sb = new StringBuffer("UPDATE user_profile SET value=? ");
		List<Object> args = new ArrayList<Object>();
		args.add(user.getSection().getValues());
		if (!StringUtils.isEmpty(user.getUserKind())){
			args.add(user.getUserKind());
			sb.append(sb.append("user_kind=? "));			
		}
		sb.append("UPDATE user_profile SET value=? WHERE userid = ? and section = ?");		
		statement.append(sb.toString());		
		args.add(user.getUserId());
		args.add(user.getSection().getSectionName());
		ResultSet result = executeStatement(statement.toString(), args);
		//TODO check result
	}	
	
	private static UserProfileOut getElement(Row row,UserProfileOut user) {
		if (user != null){
			user = new UserProfileOut();
			user.setUserId(row.getUUID(0));
			user.setUserKind(row.getString(1));
		}
		
		SectionOut section = new SectionOut();
		section.setSectionName(row.getString(2));
		section.setValues(row.getMap(3, String.class, String.class));
		user.getSections().add(section);
		return user;		
	}
	
	
}
