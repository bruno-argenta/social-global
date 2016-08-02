package com.myklovr.api.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.myklovr.api.GenericAPI;
import com.myklovr.api.datainfo.SessionDI;
import com.myklovr.helpers.PropertiesHelper;
import com.myklovr.helpers.constants.MessagesConstants;
import com.myklovr.helpers.exception.BussinesException;
import com.myklovr.logger.Log;;

public class SessionAPI extends GenericAPI{

	
	
	public static List<SessionDI> getValidSessionByToken(String sessionToken){
		StringBuffer statement = new StringBuffer();
		statement.append("SELECT userid,sessiontoken,expirationtimestamp,username,provider FROM session WHERE sessiontoken= ? ");
		List<Object> args = new ArrayList<Object>();
		args.add(sessionToken);
		ResultSet result = executeStatement(statement.toString(), args);
		List<SessionDI> resultList = new ArrayList<SessionDI>();
		for (Row row : result) {
			resultList.add(getElement(row));
		}
		return resultList;	
	}
	
	
	public static void updateExpirationTimestamp(String sessionToken) throws BussinesException{		
		StringBuffer statement = new  StringBuffer();
		Calendar expirationTime = Calendar.getInstance();
		expirationTime.add(Calendar.DATE, 3);
		statement.append("UPDATE session set expirationtimestamp = ? WHERE sessiontoken = ?");		
		statement.append(" IF EXISTS");
		List<Object> args = new ArrayList<Object>();		
		args.add(expirationTime.getTime());
		args.add(sessionToken);
		ResultSet result = executeStatement(statement.toString(), args);
		List<Row> rows = result.all();
		if (!rows.isEmpty()){
			Row row  = rows.get(0);
			Boolean updated = row.get(0,Boolean.class);
			if (!updated){
				Log.warn(PropertiesHelper.getStringMessageProperty(MessagesConstants.ERROR_MESSAGE_SESSION_EXPIRED));
				throw new BussinesException(PropertiesHelper.getStringMessageProperty(MessagesConstants.ERROR_MESSAGE_SESSION_EXPIRED));
			}
		}	
	}
	
	
	
	public static void insertSession(String sessionToken, UUID userId, String username, String provider) throws BussinesException{	
		StringBuffer statement = new  StringBuffer();
		statement.append("INSERT INTO session (sessiontoken,userid,expirationtimestamp,username,provider) ");		
		statement.append("VALUES (?,?,toTimestamp(now()),?,?) IF NOT EXISTS");
		List<Object> args = new ArrayList<Object>();
		args.add(sessionToken);
		args.add(userId);	
		args.add(username);
		args.add(provider);
		ResultSet result = executeStatement(statement.toString(), args);
		List<Row> rows = result.all();
		if (!rows.isEmpty()){
			Row row  = rows.get(0);
			Boolean inserted = row.get(0,Boolean.class);
			if (!inserted){
				Log.warn(PropertiesHelper.getStringMessageProperty(MessagesConstants.ERROR_MESSAGE_SESSION_NOT_CREATED));
				throw new BussinesException(PropertiesHelper.getStringMessageProperty(MessagesConstants.ERROR_MESSAGE_SESSION_NOT_CREATED));
			}
		}				
	}
	
	private static SessionDI getElement(Row row) {
		Calendar today = Calendar.getInstance();
		Calendar sessionCalendar = Calendar.getInstance();
		Date session = row.getTimestamp(2);
		sessionCalendar.setTime(session);
		SessionDI result = null;
		if (sessionCalendar.after(today)){
			result = new SessionDI();
			result.setUserId(row.getUUID(0));
			result.setSessionToken(row.getString(1));
			result.setExpirationTimestamp(session);
			result.setUsername(row.getString(3));
			result.setProvider(row.getString(4));
		}		
		return result;		
	}
	
	
}
