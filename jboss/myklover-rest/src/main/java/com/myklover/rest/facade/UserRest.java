package com.myklover.rest.facade;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.myklover.api.datainfo.user.in.LoginRegistrationIn;
import com.myklover.rest.beans.LoginRegistrationBean;

@Path("/user")
public class UserRest {

	@EJB
	LoginRegistrationBean loginRegistrationBean;
	
	
	@POST
	@Path("/register")
	@Consumes("application/json")
	public Response registerUser(LoginRegistrationIn loginRegistration) {
		String result = "Product created : ";
		return Response.status(200).entity(result).build();
		
	}
	
	
}
