package com.myklovr.rest.beans;

import com.myklovr.api.datainfo.SessionDI;
import com.myklovr.api.datainfo.in.PrivateDataInfoIn;
import com.myklovr.api.user.SessionAPI;
import com.myklovr.helpers.CryptoHelper;
import com.myklovr.helpers.exception.BussinesException;
import com.myklovr.helpers.exception.ForbiddenException;


public class VerificationSessionBean {

	
	protected SessionDI verfySession(PrivateDataInfoIn dataInfo) throws ForbiddenException, BussinesException{
		String hashedSession = CryptoHelper.hashString(dataInfo.getSessionToken());
		SessionDI result = SessionAPI.getValidSessionByToken(hashedSession);
		if (result == null){
			throw new ForbiddenException();
		}else{
			SessionAPI.updateExpirationTimestamp(hashedSession);
			return result;
		}
	}

}
