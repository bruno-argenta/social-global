package com.myklover.api.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.myklover.api.GenericAPI;
import com.myklover.api.datainfo.user.out.SessionOut;
import com.myklover.helpers.PropertiesHelper;
import com.myklover.helpers.constants.MessagesConstants;
import com.myklover.helpers.exception.BussinesException;
import com.myklover.logger.Log;;

public class SessionAPI extends GenericAPI{

	
	
	public static List<SessionOut> getSessionByToken(String sessionToken){
		StringBuffer statement = new StringBuffer();
		statement.append("SELECT userid,sessiontoken,expirationtimestamp FROM session WHERE sessiontoken= ? ");
		List<Object> args = new ArrayList<Object>();
		args.add(sessionToken);
		ResultSet result = executeStatement(statement.toString(), args);
		List<SessionOut> resultList = new ArrayList<SessionOut>();
		for (Row row : result) {
			resultList.add(getElement(row));
		}
		return resultList;	
	}
	
	
	public static void updateExpirationTimestamp(String sessionToken) throws BussinesException{		
		StringBuffer statement = new  StringBuffer();
		Calendar expirationTime = Calendar.getInstance();
		expirationTime.add(Calendar.DATE, 3);
		statement.append("UPDATE session set expirationtimestamp = ? WHERE sessiontoken = ? ");		
		statement.append(" IF NOT EXISTS");
		List<Object> args = new ArrayList<Object>();
		args.add(expirationTime.getTime());
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
	
	
	
	public static void insertSession(String sessionToken, UUID userId) throws BussinesException{	
		StringBuffer statement = new  StringBuffer();
		statement.append("INSERT INTO session (sessiontoken,userid,expirationtimestamp) ");		
		statement.append("VALUES (?,?,toTimestamp(now())) IF NOT EXISTS");
		List<Object> args = new ArrayList<Object>();
		args.add(sessionToken);
		args.add(userId);				
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
	
	private static SessionOut getElement(Row row) {
		SessionOut result = new SessionOut();				
		result.setUserId(row.getUUID(0));
		result.setSessionToken(row.getString(1));
		result.setExpirationTimestamp(row.getTimestamp(2));
		return result;		
	}
	
	
}
