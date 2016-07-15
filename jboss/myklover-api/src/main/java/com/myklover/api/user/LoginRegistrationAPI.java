package com.myklover.api.user;

import java.util.ArrayList;
import java.util.List;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.myklover.api.GenericAPI;
import com.myklover.api.datainfo.user.in.LoginRegistrationIn;
import com.myklover.api.datainfo.user.out.LoginRegistrationOut;;

public class LoginRegistrationAPI extends GenericAPI{

	
	
	public static List<LoginRegistrationOut> GetUserByUserNameProvider(String userName, String provider){
		StringBuffer statement = new StringBuffer();
		statement.append("SELECT * FROM \"Login\" WHERE username = ? and provider=? allow filtering;");
		List<Object> args = new ArrayList<Object>();
		args.add(userName);
		args.add(provider);
		ResultSet result = executeStatement(statement.toString(), args);
		List<LoginRegistrationOut> resultList = new ArrayList<LoginRegistrationOut>();
		for (Row row : result) {
			resultList.add(getElement(row));
		}
		return resultList;
	
	}
	
	
	public static void registerUser(LoginRegistrationIn registrationInfo){
		
		StringBuffer statement = new  StringBuffer();
		statement.append("INSERT INTO \"Login\" (userId,userName,provider,accountBlocked,password,userCreationTimestamp,wrongPasswordCounter) ");		
		statement.append("VALUES (uuid(),?,?,?,?,toTimestamp(now()),?)");
		List<Object> args = new ArrayList<Object>();
		args.add(registrationInfo.getUsername());
		args.add(registrationInfo.getProvider());
		args.add(Boolean.FALSE);
		args.add(registrationInfo.getPassword());
		args.add(0);		
		executeStatement(statement.toString(), args);
		
		
	}
	
	private static LoginRegistrationOut getElement(Row row) {
		LoginRegistrationOut result = new LoginRegistrationOut();
				
		result.setUserId(row.getUUID(0));
		result.setUserName(row.getString(1));
		result.setProvider(row.getString(2));
		result.setAccountBlocked(row.getBool(3));
		result.setPassword(row.getString(4));
		result.setUserCrationTimestamp(row.getTimestamp(5));
		result.setWrongPassCounter(row.getInt(6));		
		
		return result;		
	}
	
	
}
