package com.myklovr.rest.facade.privates;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.myklovr.api.datainfo.in.user.SectionIn;
import com.myklovr.api.datainfo.out.user.UserProfileOut;
import com.myklovr.helpers.constants.AppConstants;
import com.myklovr.helpers.exception.BussinesException;
import com.myklovr.helpers.exception.ForbiddenException;
import com.myklovr.logger.Log;
import com.myklovr.rest.beans.UserProfileBean;
import com.myklovr.rest.dto.GenericDTO;


@Path("/userProfile")
@Stateless
public class UserProfileRest {

	@EJB
	UserProfileBean userProfileBean;
	
	
	@POST
	@Path("/getSection")
	@Consumes("application/json")
	public Response getSection(SectionIn sectionIn) {
		GenericDTO result;
		Gson gson = new Gson();
		try {			
			UserProfileOut user = userProfileBean.getUserProfile(sectionIn);
			result = new GenericDTO("", AppConstants.OK_CODE, user);
		}catch (ForbiddenException e) {
			Log.error(e.getMessage(),e);
			return Response.status(403).build();
		}
		catch (BussinesException e) {
			result = new GenericDTO(e.getMessage(), AppConstants.ERROR_INTERNAL, "");
			Log.error(e.getMessage(),e);
		}		
		catch (Exception e) {
			result = new GenericDTO(e.getMessage(), AppConstants.ERROR_INTERNAL, "");
			Log.error(e.getMessage(),e);
		}
		String resultString = gson.toJson(result); 
		
		return Response.status(200).entity(resultString).build();		
	}
	
	
	@POST
	@Path("/setSection")
	@Consumes("application/json")
	public Response setSection(SectionIn sectionIn) {
		GenericDTO result;
		Gson gson = new Gson();
		try {
			userProfileBean.setUserProfile(sectionIn);
			result = new GenericDTO("", AppConstants.OK_CODE, "");
		}catch (ForbiddenException e) {
			Log.error(e.getMessage(),e);
			return Response.status(403).build();
		}
		catch (BussinesException e) {
			result = new GenericDTO(e.getMessage(), AppConstants.ERROR_INTERNAL, "");
			Log.error(e.getMessage(),e);
		}		
		catch (Exception e) {
			result = new GenericDTO(e.getMessage(), AppConstants.ERROR_INTERNAL, "");
			Log.error(e.getMessage(),e);
		}
		String resultString = gson.toJson(result);		
		return Response.status(200).entity(resultString).build();		
	}		
	
}
