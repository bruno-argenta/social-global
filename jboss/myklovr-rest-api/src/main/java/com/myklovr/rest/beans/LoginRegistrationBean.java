package com.myklovr.rest.beans;

import java.util.UUID;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;

import com.myklovr.api.datainfo.user.in.LoginRegistrationIn;
import com.myklovr.api.datainfo.user.out.LoginRegistrationOut;
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

	

	public String registerUserMyKlovr(LoginRegistrationIn registrationInfo) throws Exception {
		registrationInfo.setProvider(AppConstants.MYKLOVR_PROVIDER);
		return registerUser(registrationInfo);

	}

	public String registerFacebookUser(LoginRegistrationIn registrationInfo) throws Exception {

		registrationInfo.setProvider(AppConstants.FACEBOOK_PROVIDER);
		// TODO verify integration with facebook for other info user
		return registerUser(registrationInfo);

	}

	public String registerTwitterUser(LoginRegistrationIn registrationInfo) throws Exception {

		registrationInfo.setProvider(AppConstants.TWITTER_PROVIDER);
		// TODO verify integration with twitter for other info user
		return registerUser(registrationInfo);

	}

	public String registerGplusUser(LoginRegistrationIn registrationInfo) throws Exception {

		registrationInfo.setProvider(AppConstants.GPLUS_PROVIDER);
		// TODO verify integration with gplus for other info user
		return registerUser(registrationInfo);

	}

	public String loginUser(LoginRegistrationIn registrationInfo) throws Exception {
		LoginRegistrationOut loginUser = LoginRegistrationAPI.getUserByUserNameProvider(registrationInfo.getUsername(),
				registrationInfo.getProvider());
		if (loginUser != null) {
			String hashedPassword = CryptoHelper.hashString(registrationInfo.getPassword());
			if (StringUtils.equalsIgnoreCase(hashedPassword, loginUser.getPassword())
					&& !loginUser.getAccountBlocked()) {
				LoginRegistrationAPI.updatePasswordCounterUser(loginUser.getUserName(), loginUser.getProvider(), false,
						0);
				return createSessionUser(loginUser.getUserId());
			} else {

				loginUser.setWrongPassCounter(loginUser.getWrongPassCounter() + 1);
				String errorMessage;
				if (loginUser.getWrongPassCounter() < PropertiesHelper
						.getIntConfigProperty(PropertiesConstants.CONFIG_USER_WRONG_PASSWORD_COUNTER)) {
					errorMessage = PropertiesHelper
							.getStringMessageProperty(MessagesConstants.ERROR_LOGIN_WRONG_PASSWORD);
					// TODO update login register

				} else {
					errorMessage = PropertiesHelper
							.getStringMessageProperty(MessagesConstants.ERROR_LOGIN_ACCOUNT_BLOCKED);
					if (!loginUser.getAccountBlocked()) {
						loginUser.setAccountBlocked(true);
						// TODO update login register
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

	public String loginExternalProvider(LoginRegistrationIn registrationInfo) throws Exception {
		String result;
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

	private String createSessionUser(UUID userId) throws BussinesException {
		String sessionToken = UUID.randomUUID().toString();
		String hashedSession = CryptoHelper.hashString(sessionToken);
		SessionAPI.insertSession(hashedSession, userId);
		return sessionToken;
	}

	private String registerUser(LoginRegistrationIn registrationInfo) throws Exception {
		String hashedPassword = CryptoHelper.hashString(registrationInfo.getPassword());
		registrationInfo.setPassword(hashedPassword);
		LoginRegistrationAPI.registerUser(registrationInfo);
		LoginRegistrationOut user = LoginRegistrationAPI.getUserByUserNameProvider(registrationInfo.getUsername(),
				registrationInfo.getProvider());
		return createSessionUser(user.getUserId());
	}

	private String loginFacebook(LoginRegistrationIn registrationInfo) throws Exception {
		FBAccessToken accessToken = FBConnection.getAccessToken(registrationInfo.getUsername());
		FBBasicProfile profile = FBConnection.getFBGraph(accessToken.getAccess_token());
		LoginRegistrationOut loginUser = LoginRegistrationAPI.getUserByUserNameProvider(profile.getId(),
				registrationInfo.getProvider());
		if (loginUser != null){
			return createSessionUser(loginUser.getUserId());
		}else{
			registrationInfo.setUsername(profile.getId());
			return registerUser(registrationInfo);
		}
	}

	private String loginTwitter(LoginRegistrationIn registrationInfo) throws Exception {
		GPBasicProfile profile = GPConnection.getUserInfoJson(registrationInfo.getUsername());
		LoginRegistrationOut loginUser = LoginRegistrationAPI.getUserByUserNameProvider(profile.getId(),
				registrationInfo.getProvider());
		if (loginUser != null){
			return createSessionUser(loginUser.getUserId());
		}else{
			registrationInfo.setUsername(profile.getId());
			return registerUser(registrationInfo);
		}
	}

	private String loginGoogle(LoginRegistrationIn registrationInfo) throws Exception {
		GPBasicProfile profile = GPConnection.getUserInfoJson(registrationInfo.getUsername());
		LoginRegistrationOut loginUser = LoginRegistrationAPI.getUserByUserNameProvider(profile.getId(),
				registrationInfo.getProvider());
		if (loginUser != null){
			return createSessionUser(loginUser.getUserId());
		}else{
			registrationInfo.setUsername(profile.getId());
			return registerUser(registrationInfo);
		}
	}

}
