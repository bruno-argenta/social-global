package com.myklovr.rest.facade;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.myklovr.api.datainfo.user.in.ChangePasswordRecoveryIn;
import com.myklovr.api.datainfo.user.in.LoginRegistrationIn;
import com.myklovr.api.datainfo.user.in.PasswordRecoveryIn;
import com.myklovr.api.datainfo.user.in.VerifyCodeIn;
import com.myklovr.helpers.constants.AppConstants;
import com.myklovr.logger.Log;
import com.myklovr.rest.beans.LoginRegistrationBean;
import com.myklovr.rest.beans.RecoveryPasswordBean;
import com.myklovr.rest.dto.GenericDTO;


@Path("/user")
@Stateless
public class UserRest {

	@EJB
	LoginRegistrationBean loginRegistrationBean;
	@EJB
	RecoveryPasswordBean recoveryPasswordBean;
	
	
	@POST
	@Path("/register")
	@Consumes("application/json")
	public Response registerUser(LoginRegistrationIn loginRegistration) {
		GenericDTO result;
		Gson gson = new Gson();
		try {
			String sessionToken = loginRegistrationBean.registerUserMyKlovr(loginRegistration);
			result = new GenericDTO("", AppConstants.OK_CODE, sessionToken);
		} catch (Exception e) {
			result = new GenericDTO(e.getMessage(), AppConstants.ERROR_INTERNAL, "");
			e.printStackTrace();
		}
		String resultString = gson.toJson(result); 
		
		return Response.status(200).entity(resultString).build();		
	}
	
	
	@POST
	@Path("/login")
	@Consumes("application/json")
	public Response loginUser(LoginRegistrationIn loginRegistration) {
		GenericDTO result;
		Gson gson = new Gson();
		try {
			String sessionToken = loginRegistrationBean.loginUser(loginRegistration);
			result = new GenericDTO("", AppConstants.OK_CODE, sessionToken);
		} catch (Exception e) {
			result = new GenericDTO(e.getMessage(), AppConstants.ERROR_INTERNAL, "");
			Log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		String resultString = gson.toJson(result);		
		return Response.status(200).entity(resultString).build();		
	}
	
	@POST
	@Path("/loginExternalProvider")
	@Consumes("application/json")
	public Response loginExternalProvider(LoginRegistrationIn loginRegistration) {
		GenericDTO result;
		Gson gson = new Gson();
		try {
			String sessionToken = loginRegistrationBean.loginExternalProvider(loginRegistration);
			result = new GenericDTO("", AppConstants.OK_CODE, sessionToken);
		} catch (Exception e) {
			result = new GenericDTO(e.getMessage(), AppConstants.ERROR_INTERNAL, "");
			e.printStackTrace();
		}
		String resultString = gson.toJson(result);		
		return Response.status(200).entity(resultString).build();		
	}
	
	
	@POST
	@Path("/verifyCode")
	@Consumes("application/json")
	public Response verifyCode(VerifyCodeIn verifyCode) {
		GenericDTO result;
		Gson gson = new Gson();
		try {
			Boolean valid = recoveryPasswordBean.verifyCode(verifyCode);
			result = new GenericDTO("", AppConstants.OK_CODE,valid);
		} catch (Exception e) {
			result = new GenericDTO(e.getMessage(), AppConstants.ERROR_INTERNAL, "");
			e.printStackTrace();
		}
		String resultString = gson.toJson(result);		
		return Response.status(200).entity(resultString).build();		
	}
	
	
	@POST
	@Path("/recoveryPassword")
	@Consumes("application/json")
	public Response recoveryPassword(PasswordRecoveryIn recoveryPassword) {
		GenericDTO result;
		Gson gson = new Gson();
		try {
			recoveryPasswordBean.recoveryPassword(recoveryPassword);
			result = new GenericDTO("", AppConstants.OK_CODE);
		} catch (Exception e) {
			
			e.printStackTrace();
			Log.error(e.getMessage(), e);
			result = new GenericDTO(e.getMessage(), AppConstants.ERROR_INTERNAL, "");
		}
		String resultString = gson.toJson(result);		
		return Response.status(200).entity(resultString).build();		
	}
	
	
	@POST
	@Path("/changePasswordRecovery")
	@Consumes("application/json")
	public Response changePasswordRecovery(ChangePasswordRecoveryIn changePassword) {
		GenericDTO result;
		Gson gson = new Gson();
		try {
			String message = recoveryPasswordBean.changePasswordRecovery(changePassword);
			result = new GenericDTO(message, AppConstants.OK_CODE);
		} catch (Exception e) {
			result = new GenericDTO(e.getMessage(), AppConstants.ERROR_INTERNAL, "");
			e.printStackTrace();
		}
		String resultString = gson.toJson(result);		
		return Response.status(200).entity(resultString).build();		
	}
	
	
}
