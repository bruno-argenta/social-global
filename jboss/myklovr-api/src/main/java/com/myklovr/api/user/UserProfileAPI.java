package com.myklovr.api.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.myklovr.api.GenericAPI;
import com.myklovr.api.datainfo.user.out.SectionOut;
import com.myklovr.api.datainfo.user.out.UserProfileOut;;

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
	
	public static void setUserProfileSection(UUID userId, String section, Map<String,String> values){
		StringBuffer statement = new StringBuffer();
		statement.append("UPDATE user_profile SET value=? WHERE userid = ? and section = ?");
		List<Object> args = new ArrayList<Object>();
		args.add(values);
		args.add(userId);
		args.add(section);
		ResultSet result = executeStatement(statement.toString(), args);
		//TODO chack result
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
