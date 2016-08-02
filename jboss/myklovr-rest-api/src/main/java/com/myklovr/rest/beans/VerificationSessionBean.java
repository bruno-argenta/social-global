package com.myklovr.rest.beans;

import java.util.List;
import java.util.UUID;

import com.myklovr.api.datainfo.SessionDI;
import com.myklovr.api.datainfo.in.PrivateDataInfoIn;
import com.myklovr.api.user.SessionAPI;
import com.myklovr.helpers.CryptoHelper;
import com.myklovr.helpers.exception.BussinesException;
import com.myklovr.helpers.exception.ForbiddenException;


public class VerificationSessionBean {

	
	protected UUID verfySession(PrivateDataInfoIn dataInfo) throws ForbiddenException, BussinesException{
		String hashedSession = CryptoHelper.hashString(dataInfo.getSessionToken());
		List<SessionDI> result = SessionAPI.getValidSessionByToken(hashedSession);
		if (result.isEmpty()){
			throw new ForbiddenException();
		}else{
			SessionAPI.updateExpirationTimestamp(hashedSession);
			return result.get(0).getUserId();
		}
	}

}
