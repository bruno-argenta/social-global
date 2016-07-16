package com.myklover.rest.beans;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.myklover.api.datainfo.user.in.LoginRegistrationIn;
import com.myklover.api.datainfo.user.out.LoginRegistrationOut;
import com.myklover.api.user.LoginRegistrationAPI;

@Stateless
@LocalBean
public class LoginRegistrationBean {

	private static final String MYKLOVER_PROVIDER = "myklover";
	private static final String FACEBOOK_PROVIDER = "facebook";
	private static final String TWITTER_PROVIDER = "twitter";
	private static final String GPLUS_PROVIDER = "gplus";

	public void registerUser(LoginRegistrationIn registrationInfo) throws Exception {

		registrationInfo.setProvider(MYKLOVER_PROVIDER);
		LoginRegistrationAPI.registerUser(registrationInfo);

	}

	public void registerFacebookUser(LoginRegistrationIn registrationInfo) throws Exception {

		registrationInfo.setProvider(FACEBOOK_PROVIDER);
		// TODO verify integration with facebook for other info user
		LoginRegistrationAPI.registerUser(registrationInfo);

	}

	public void registerTwitterUser(LoginRegistrationIn registrationInfo) throws Exception {

		registrationInfo.setProvider(TWITTER_PROVIDER);
		// TODO verify integration with twitter for other info user
		LoginRegistrationAPI.registerUser(registrationInfo);

	}

	public void registerGplusUser(LoginRegistrationIn registrationInfo) throws Exception {

		registrationInfo.setProvider(GPLUS_PROVIDER);
		// TODO verify integration with gplus for other info user
		LoginRegistrationAPI.registerUser(registrationInfo);

	}

	public void loginUser(LoginRegistrationIn registrationInfo) {
		List<LoginRegistrationOut> result = LoginRegistrationAPI
				.getUserByUserNameProvider(registrationInfo.getUsername(), registrationInfo.getProvider());
		if (!result.isEmpty()) {
			LoginRegistrationOut loginUser = result.get(0);

		}
	}

}
