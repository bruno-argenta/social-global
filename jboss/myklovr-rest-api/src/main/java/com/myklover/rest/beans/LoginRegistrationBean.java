package com.myklover.rest.beans;

import java.util.List;
import java.util.UUID;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;

import com.myklover.api.datainfo.user.in.LoginRegistrationIn;
import com.myklover.api.datainfo.user.out.LoginRegistrationOut;
import com.myklover.api.user.LoginRegistrationAPI;
import com.myklover.api.user.SessionAPI;
import com.myklover.helpers.CryptoHelper;
import com.myklover.helpers.PropertiesHelper;
import com.myklover.helpers.constants.MessagesConstants;
import com.myklover.helpers.constants.PropertiesConstants;
import com.myklover.helpers.exception.BussinesException;

@Stateless
@LocalBean
public class LoginRegistrationBean {

	private static final String MYKLOVER_PROVIDER = "myklover";
	private static final String FACEBOOK_PROVIDER = "facebook";
	private static final String TWITTER_PROVIDER = "twitter";
	private static final String GPLUS_PROVIDER = "gplus";

	public String registerUserMyKlovr(LoginRegistrationIn registrationInfo) throws Exception {
		registrationInfo.setProvider(MYKLOVER_PROVIDER);
		return registerUser(registrationInfo);

	}

	public String registerFacebookUser(LoginRegistrationIn registrationInfo) throws Exception {

		registrationInfo.setProvider(FACEBOOK_PROVIDER);
		// TODO verify integration with facebook for other info user
		return registerUser(registrationInfo);

	}

	public String registerTwitterUser(LoginRegistrationIn registrationInfo) throws Exception {

		registrationInfo.setProvider(TWITTER_PROVIDER);
		// TODO verify integration with twitter for other info user
		return registerUser(registrationInfo);

	}

	public String registerGplusUser(LoginRegistrationIn registrationInfo) throws Exception {

		registrationInfo.setProvider(GPLUS_PROVIDER);
		// TODO verify integration with gplus for other info user
		return registerUser(registrationInfo);

	}
	
	
	

	public String loginUser(LoginRegistrationIn registrationInfo) throws BussinesException {
		List<LoginRegistrationOut> result = LoginRegistrationAPI
				.getUserByUserNameProvider(registrationInfo.getUsername(), registrationInfo.getProvider());
		if (!result.isEmpty()) {
			LoginRegistrationOut loginUser = result.get(0);
			String hashedPassword = CryptoHelper.hashString(registrationInfo.getPassword());
			if (StringUtils.equalsIgnoreCase(hashedPassword, loginUser.getPassword()) && !loginUser.getAccountBlocked()){
				return createSessionUser(loginUser.getUserId());
			}else{
				
				loginUser.setWrongPassCounter(loginUser.getWrongPassCounter() +1);
				String errorMessage;
				if (loginUser.getWrongPassCounter() < PropertiesHelper.getIntConfigProperty(PropertiesConstants.CONFIG_USER_WRONG_PASSWORD_COUNTER)){
					errorMessage = PropertiesHelper.getStringMessageProperty(MessagesConstants.ERROR_LOGIN_WRONG_PASSWORD);
					//TODO update login register
				}else{
					errorMessage = PropertiesHelper.getStringMessageProperty(MessagesConstants.ERROR_LOGIN_ACCOUNT_BLOCKED);
					if (!loginUser.getAccountBlocked()){
						loginUser.setAccountBlocked(true);
						//TODO update login register
					}				
				};			
				throw new BussinesException(errorMessage);
			}
		}else{
			throw new BussinesException(PropertiesHelper.getStringMessageProperty(MessagesConstants.ERROR_LOGIN_WRONG_PASSWORD));
		}
		
	}
	
	private String createSessionUser(UUID userId) throws BussinesException{
		String sessionToken = UUID.randomUUID().toString();
		String hashedSession = CryptoHelper.hashString(sessionToken);
		SessionAPI.insertSession(hashedSession, userId);
		return sessionToken;
	}
	
	private String registerUser(LoginRegistrationIn registrationInfo) throws Exception{
		String hashedPassword = CryptoHelper.hashString(registrationInfo.getPassword());
		registrationInfo.setPassword(hashedPassword);
		LoginRegistrationAPI.registerUser(registrationInfo);
		List<LoginRegistrationOut> result = LoginRegistrationAPI.getUserByUserNameProvider(registrationInfo.getUsername(), registrationInfo.getProvider());
		LoginRegistrationOut user = result.get(0);
		return createSessionUser(user.getUserId());
	}

}
