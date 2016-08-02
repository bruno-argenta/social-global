package com.myklovr.rest.beans;

import java.util.UUID;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;

import com.myklovr.api.datainfo.LoginRegistrationDI;
import com.myklovr.api.datainfo.in.user.LoginRegistrationIn;
import com.myklovr.api.datainfo.out.user.LoginOut;
import com.myklovr.api.user.LoginRegistrationAPI;
import com.myklovr.api.user.SessionAPI;
import com.myklovr.helpers.CryptoHelper;
import com.myklovr.helpers.PropertiesHelper;
import com.myklovr.helpers.constants.AppConstants;
import com.myklovr.helpers.constants.MessagesConstants;
import com.myklovr.helpers.constants.PropertiesConstants;
import com.myklovr.helpers.exception.BussinesException;
import com.myklovr.rest.socialnetwork.facebook.FBAccessToken;
import com.myklovr.rest.socialnetwork.facebook.FBBasicProfile;
import com.myklovr.rest.socialnetwork.facebook.FBConnection;
import com.myklovr.rest.socialnetwork.google.GPBasicProfile;
import com.myklovr.rest.socialnetwork.google.GPConnection;

@Stateless
@LocalBean
public class LoginRegistrationBean {

	

	public LoginOut registerUserMyKlovr(LoginRegistrationIn registrationInfo) throws Exception {
		registrationInfo.setProvider(AppConstants.MYKLOVR_PROVIDER);
		return registerUser(registrationInfo);

	}


	public LoginOut loginUser(LoginRegistrationIn registrationInfo) throws Exception {
		LoginRegistrationDI loginUser = LoginRegistrationAPI.getUserByUserNameProvider(registrationInfo.getUsername(),
				registrationInfo.getProvider());
		if (loginUser != null) {
			String hashedPassword = CryptoHelper.hashString(registrationInfo.getPassword());
			if (StringUtils.equalsIgnoreCase(hashedPassword, loginUser.getPassword())
					&& !loginUser.getAccountBlocked()) {
				LoginRegistrationAPI.updatePasswordCounterUser(loginUser.getUserName(), loginUser.getProvider(), false,0);
				String sessionToken = createSessionUser(loginUser.getUserId(),loginUser.getUserName(),loginUser.getProvider());
				LoginOut user = new LoginOut(loginUser);
				user.setSessionToken(sessionToken);
				return user;
			} else {

				loginUser.setWrongPassCounter(loginUser.getWrongPassCounter() + 1);
				String errorMessage;
				if (loginUser.getWrongPassCounter() < PropertiesHelper
						.getIntConfigProperty(PropertiesConstants.CONFIG_USER_WRONG_PASSWORD_COUNTER)) {
					errorMessage = PropertiesHelper
							.getStringMessageProperty(MessagesConstants.ERROR_LOGIN_WRONG_PASSWORD);
				} else {
					errorMessage = PropertiesHelper
							.getStringMessageProperty(MessagesConstants.ERROR_LOGIN_ACCOUNT_BLOCKED);
					if (!loginUser.getAccountBlocked()) {
						loginUser.setAccountBlocked(true);						
					}
				}
				;
				LoginRegistrationAPI.updatePasswordCounterUser(loginUser.getUserName(), loginUser.getProvider(),
						loginUser.getAccountBlocked(), loginUser.getWrongPassCounter());
				throw new BussinesException(errorMessage);
			}
		} else {
			throw new BussinesException(
					PropertiesHelper.getStringMessageProperty(MessagesConstants.ERROR_LOGIN_WRONG_PASSWORD));
		}

	}

	public LoginOut loginExternalProvider(LoginRegistrationIn registrationInfo) throws Exception {
		LoginOut result;
		switch (registrationInfo.getProvider()) {
		case AppConstants.FACEBOOK_PROVIDER:
			result = loginFacebook(registrationInfo);
			break;
		case AppConstants.TWITTER_PROVIDER:
			result = loginTwitter(registrationInfo);
			break;
		case AppConstants.GPLUS_PROVIDER:
			result = loginGoogle(registrationInfo);
			break;
		default:
			throw new BussinesException(
					PropertiesHelper.getStringMessageProperty(MessagesConstants.ERROR_LOGIN_WRONG_PASSWORD));

		}
		return result;
	}

	private String createSessionUser(UUID userId,String username,String provider) throws BussinesException {
		String sessionToken = UUID.randomUUID().toString();
		String hashedSession = CryptoHelper.hashString(sessionToken);
		SessionAPI.insertSession(hashedSession, userId,username,provider);
		return sessionToken;
	}

	private LoginOut registerUser(LoginRegistrationIn registrationInfo) throws Exception {
		String hashedPassword = CryptoHelper.hashString(registrationInfo.getPassword());
		registrationInfo.setPassword(hashedPassword);
		LoginRegistrationAPI.registerUser(registrationInfo);
		LoginRegistrationDI userLogin = LoginRegistrationAPI.getUserByUserNameProvider(registrationInfo.getUsername(),
				registrationInfo.getProvider());		
		String sessionToken = createSessionUser(userLogin.getUserId(),userLogin.getUserName(),userLogin.getProvider());
		LoginOut user = new LoginOut(userLogin);
		user.setSessionToken(sessionToken);
		return user;
	}

	private LoginOut loginFacebook(LoginRegistrationIn registrationInfo) throws Exception {
		FBAccessToken accessToken = FBConnection.getAccessToken(registrationInfo.getUsername());
		FBBasicProfile profile = FBConnection.getFBGraph(accessToken.getAccess_token());
		LoginRegistrationDI loginUser = LoginRegistrationAPI.getUserByUserNameProvider(profile.getId(),
				registrationInfo.getProvider());
		if (loginUser != null){
			String sessionToken = createSessionUser(loginUser.getUserId(),loginUser.getUserName(),loginUser.getProvider());
			LoginOut user = new LoginOut(loginUser);
			user.setSessionToken(sessionToken);
			return user;
		}else{
			registrationInfo.setUsername(profile.getId());
			return registerUser(registrationInfo);
		}
	}

	private LoginOut loginTwitter(LoginRegistrationIn registrationInfo) throws Exception {
		GPBasicProfile profile = GPConnection.getUserInfoJson(registrationInfo.getUsername());
		LoginRegistrationDI loginUser = LoginRegistrationAPI.getUserByUserNameProvider(profile.getId(),
				registrationInfo.getProvider());
		if (loginUser != null){
			String sessionToken = createSessionUser(loginUser.getUserId(),loginUser.getUserName(),loginUser.getProvider());
			LoginOut user = new LoginOut(loginUser);
			user.setSessionToken(sessionToken);
			return user;
		}else{
			registrationInfo.setUsername(profile.getId());
			return registerUser(registrationInfo);
		}
	}

	private LoginOut loginGoogle(LoginRegistrationIn registrationInfo) throws Exception {
		GPBasicProfile profile = GPConnection.getUserInfoJson(registrationInfo.getUsername());
		LoginRegistrationDI loginUser = LoginRegistrationAPI.getUserByUserNameProvider(profile.getId(),
				registrationInfo.getProvider());
		if (loginUser != null){
			String sessionToken = createSessionUser(loginUser.getUserId(),loginUser.getUserName(),loginUser.getProvider());
			LoginOut user = new LoginOut(loginUser);
			user.setSessionToken(sessionToken);
			return user;
		}else{
			registrationInfo.setUsername(profile.getId());
			return registerUser(registrationInfo);
		}
	}

}
