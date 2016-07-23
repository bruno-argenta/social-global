package com.myklover.api.user;

import java.util.ArrayList;
import java.util.List;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.myklover.api.GenericAPI;
import com.myklover.api.datainfo.user.in.LoginRegistrationIn;
import com.myklover.api.datainfo.user.out.LoginRegistrationOut;
import com.myklover.helpers.PropertiesHelper;
import com.myklover.helpers.constants.MessagesConstants;
import com.myklover.helpers.exception.BussinesException;
import com.myklover.logger.Log;;

public class LoginRegistrationAPI extends GenericAPI{

	
	
	public static LoginRegistrationOut getUserByUserNameProvider(String userName, String provider){
		StringBuffer statement = new StringBuffer();
		statement.append("SELECT userid,username,provider,accountblocked,password,usercreationtimestamp,wrongpasswordcounter FROM \"Login\" WHERE username = ? and provider=?");
		List<Object> args = new ArrayList<Object>();
		args.add(userName);
		args.add(provider);
		ResultSet result = executeStatement(statement.toString(), args);
		List<LoginRegistrationOut> resultList = new ArrayList<LoginRegistrationOut>();
		for (Row row : result) {
			resultList.add(getElement(row));
		}
		return resultList.isEmpty()? null : resultList.get(0);
	
	}
	
	
	public static void registerUser(LoginRegistrationIn registrationInfo) throws Exception{
		
		StringBuffer statement = new  StringBuffer();
		statement.append("INSERT INTO \"Login\" (userId,userName,provider,accountBlocked,password,userCreationTimestamp,wrongPasswordCounter) ");		
		statement.append("VALUES (uuid(),?,?,?,?,toTimestamp(now()),?) IF NOT EXISTS");
		List<Object> args = new ArrayList<Object>();
		args.add(registrationInfo.getUsername());
		args.add(registrationInfo.getProvider());
		args.add(Boolean.FALSE);
		args.add(registrationInfo.getPassword());
		args.add(0);		
		ResultSet result = executeStatement(statement.toString(), args);
		List<Row> rows = result.all();
		if (!rows.isEmpty()){
			Row row  = rows.get(0);
			Boolean inserted = row.get(0,Boolean.class);
			if (!inserted){
				Log.warn(PropertiesHelper.getStringMessageProperty(MessagesConstants.ERROR_MESSAGE_USER_ALREADY_REGISTERED));
				throw new BussinesException(PropertiesHelper.getStringMessageProperty(MessagesConstants.ERROR_MESSAGE_USER_ALREADY_REGISTERED));
			}
		}		
		
	}
	
	public static void updatePasswordCounterUser(String userName, String provider, boolean accountBlocked, int passwordCounter) throws Exception{
		
		StringBuffer statement = new  StringBuffer();
		statement.append("UPDATE \"Login\" SET wrongPasswordCounter = ? , accountBlocked = ? ");		
		statement.append("WHERE username= ? and provider = ? IF EXISTS");
		List<Object> args = new ArrayList<Object>();
		args.add(passwordCounter);
		args.add(accountBlocked);
		args.add(userName);
		args.add(provider);	
		ResultSet result = executeStatement(statement.toString(), args);
		List<Row> rows = result.all();
		if (!rows.isEmpty()){
			Row row  = rows.get(0);
			Boolean inserted = row.get(0,Boolean.class);
			if (!inserted){
				Log.warn(PropertiesHelper.getStringMessageProperty(MessagesConstants.ERROR_LOGIN_UPDATE_PASSWORDCOUNTER));
				throw new BussinesException(PropertiesHelper.getStringMessageProperty(MessagesConstants.ERROR_LOGIN_UPDATE_PASSWORDCOUNTER));
			}
		}		
		
	}

	public static void updatePasswordUser(String userName, String provider, String password) throws Exception{
		
		StringBuffer statement = new  StringBuffer();
		statement.append("UPDATE \"Login\" SET password = ? ,wrongPasswordCounter=0 ");		
		statement.append("WHERE username= ? and provider = ? IF EXISTS");
		List<Object> args = new ArrayList<Object>();
		args.add(password);
		args.add(userName);
		args.add(provider);	
		ResultSet result = executeStatement(statement.toString(), args);
		List<Row> rows = result.all();
		if (!rows.isEmpty()){
			Row row  = rows.get(0);
			Boolean inserted = row.get(0,Boolean.class);
			if (!inserted){
				Log.warn(PropertiesHelper.getStringMessageProperty(MessagesConstants.ERROR_PASSWORD_RECOVERY_UPDATE_PASSWORD));
				throw new BussinesException(PropertiesHelper.getStringMessageProperty(MessagesConstants.ERROR_PASSWORD_RECOVERY_UPDATE_PASSWORD));
			}
		}		
		
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
