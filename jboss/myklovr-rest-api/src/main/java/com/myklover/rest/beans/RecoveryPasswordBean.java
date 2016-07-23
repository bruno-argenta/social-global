package com.myklover.rest.beans;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;

import com.myklover.api.datainfo.user.in.ChangePasswordRecoveryIn;
import com.myklover.api.datainfo.user.in.PasswordRecoveryIn;
import com.myklover.api.datainfo.user.in.VerifyCodeIn;
import com.myklover.api.datainfo.user.out.PasswordRecoveryOut;
import com.myklover.api.user.LoginRegistrationAPI;
import com.myklover.api.user.PasswordRecoveryAPI;
import com.myklover.helpers.ComunicationHelper;
import com.myklover.helpers.CryptoHelper;
import com.myklover.helpers.PropertiesHelper;
import com.myklover.helpers.constants.MessagesConstants;
import com.myklover.helpers.exception.BussinesException;

@Stateless
@LocalBean
public class RecoveryPasswordBean {
	
	private static final String PHONE="PHONE";
	private static final String EMAIL="EMAIL";
	

	public String recoveryPassword(PasswordRecoveryIn recoveryPassword) throws BussinesException {
		String code = UUID.randomUUID().toString();
		String hashedCode = CryptoHelper.hashString(code);
		PasswordRecoveryAPI.insertPasswordRecovery(hashedCode, recoveryPassword.getUsername());
		String message;
		switch (recoveryPassword.getMethod()){
		case EMAIL:
			ComunicationHelper.sendEmail(recoveryPassword.getUsername(),
										PropertiesHelper.getStringMessageProperty(MessagesConstants.SUCCESS_PASSWORD_RECOVERY_SEND_EMAIL_SUBJECT),
										PropertiesHelper.getStringMessageProperty(MessagesConstants.SUCCESS_PASSWORD_RECOVERY_SEND_EMAIL_TEXT,code));
			message = PropertiesHelper.getStringMessageProperty(MessagesConstants.SUCCESS_PASSWORD_RECOVERY_SEND_EMAIL);
			break;
		case PHONE:
			ComunicationHelper.sendSMS(recoveryPassword.getUsername(),
					PropertiesHelper.getStringMessageProperty(MessagesConstants.SUCCESS_PASSWORD_RECOVERY_SEND_SMS_TEXT));
			message = PropertiesHelper.getStringMessageProperty(MessagesConstants.SUCCESS_PASSWORD_RECOVERY_SEND_SMS);
			break;
		default:
			message = PropertiesHelper.getStringMessageProperty(MessagesConstants.ERROR_PASSWORD_RECOVERY_INVALID_METHOD);
			break;
		}		
		return message;
	}
	
	
	public Boolean verifyCode(VerifyCodeIn verifyCode) throws BussinesException{
		String hashedCode = CryptoHelper.hashString(verifyCode.getCode());
		PasswordRecoveryOut result = PasswordRecoveryAPI.getPasswordRecoveryByToken(hashedCode);		
		if (result != null){
			if (StringUtils.equalsIgnoreCase(verifyCode.getUsername(),result.getUsername())){
				Date expirationTime = result.getExpirationTimestamp();
				Calendar today = Calendar.getInstance();
				Calendar calendarExpiration = Calendar.getInstance();
				calendarExpiration.setTime(expirationTime);				
				return today.before(calendarExpiration);
			}
		}
		return false;
	}
	
	public String changePasswordRecovery(ChangePasswordRecoveryIn changePassword) throws Exception{
		
		VerifyCodeIn verifyCode = new VerifyCodeIn();
		verifyCode.setCode(changePassword.getCode());
		verifyCode.setUsername(changePassword.getUsername());
		boolean validCode = verifyCode(verifyCode);
		String message;
		if (validCode){
			String hashedPassword = CryptoHelper.hashString(changePassword.getNewPassword());
			LoginRegistrationAPI.updatePasswordUser(changePassword.getUsername(), changePassword.getProvider(), hashedPassword);
			message = PropertiesHelper.getStringMessageProperty(MessagesConstants.SUCCESS_PASSWORD_RECOVERY_CHANGE_PASSWORD);
		}else{
			throw new BussinesException(PropertiesHelper.getStringMessageProperty(MessagesConstants.ERROR_PASSWORD_RECOVERY_INVALID_CODE));
		}
		return message;
	}

}
