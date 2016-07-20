package com.myklover.rest.facade;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.myklover.api.datainfo.user.in.LoginRegistrationIn;
import com.myklover.helpers.constants.AppConstants;
import com.myklover.rest.beans.LoginRegistrationBean;
import com.myklover.rest.dto.GenericDTO;


@Path("/user")
@Stateless
public class UserRest {

	@EJB
	LoginRegistrationBean loginRegistrationBean;
	
	
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
		}
		String resultString = gson.toJson(result);		
		return Response.status(200).entity(resultString).build();		
	}
	
	
}
