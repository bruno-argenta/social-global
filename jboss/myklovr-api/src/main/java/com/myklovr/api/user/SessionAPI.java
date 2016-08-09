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

	
	
	public static SessionDI getValidSessionByToken(String sessionToken){
		StringBuffer statement = new StringBuffer();
		statement.append("SELECT userid,sessiontoken,expirationtimestamp,username,provider,kind FROM session WHERE sessiontoken= ? ");
		List<Object> args = new ArrayList<Object>();
		args.add(sessionToken);
		ResultSet result = executeStatement(statement.toString(), args);		
		for (Row row : result) {
			return getElement(row);	
		}
		return null;
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
	
	public static void updateKind(String sessionToken, String kind) throws BussinesException{		
		StringBuffer statement = new  StringBuffer();
		statement.append("UPDATE session set kind = ? WHERE sessiontoken = ?");		
		statement.append(" IF EXISTS");
		List<Object> args = new ArrayList<Object>();		
		args.add(kind);
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
	
	
	public static void insertSession(String sessionToken, UUID userId, String username, String provider, String kind) throws BussinesException{	
		StringBuffer statement = new  StringBuffer();
		statement.append("INSERT INTO session (sessiontoken,userid,expirationtimestamp,username,provider,kind) ");		
		statement.append("VALUES (?,?,?,?,?,?) IF NOT EXISTS");
		List<Object> args = new ArrayList<Object>();
		Calendar expirationTime = Calendar.getInstance();
		expirationTime.add(Calendar.DATE, 3);
		args.add(sessionToken);
		args.add(userId);	
		args.add(expirationTime.getTime());
		args.add(username);
		args.add(provider);
		args.add(kind);
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
			result.setKind(row.getString(5));
		}		
		return result;		
	}
	
	
}
