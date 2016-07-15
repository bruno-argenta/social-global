package com.myklover.rest.beans;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.myklover.api.datainfo.user.in.LoginRegistrationIn;
import com.myklover.api.datainfo.user.out.LoginRegistrationOut;
import com.myklover.api.user.LoginRegistrationAPI;

@Stateless(name = "LoginRegisterBean", mappedName = "ejb/LoginRegisterBean")
@LocalBean
public class LoginRegistrationBean {
	
	
	
	public void registerUser(LoginRegistrationIn registrationInfo){
		LoginRegistrationAPI.registerUser(registrationInfo);
	}
	
	
	public void loginUser(LoginRegistrationIn registrationInfo){
		List<LoginRegistrationOut> result = LoginRegistrationAPI.GetUserByUserNameProvider(registrationInfo.getUsername(), registrationInfo.getProvider());
		if (!result.isEmpty()){
			LoginRegistrationOut loginUser = result.get(0);
			
		}
	}

}
