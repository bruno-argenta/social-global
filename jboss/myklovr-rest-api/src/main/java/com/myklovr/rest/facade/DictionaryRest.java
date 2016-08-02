package com.myklovr.rest.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.myklovr.api.datainfo.in.dictionary.DictionaryIn;
import com.myklovr.api.datainfo.out.dictionary.DictionaryOut;
import com.myklovr.helpers.constants.AppConstants;
import com.myklovr.logger.Log;
import com.myklovr.rest.beans.DictionaryBean;
import com.myklovr.rest.dto.GenericDTO;


@Path("/dictionary")
@Stateless
public class DictionaryRest {

	@EJB
	DictionaryBean dictionaryBean;
	
	
	@POST
	@Path("/getDictionary")
	@Consumes("application/json")
	public Response getDictionary(DictionaryIn dictionary) {
		GenericDTO result;
		Gson gson = new Gson();
		try {
			List<DictionaryOut> resultList = dictionaryBean.getDictionary(dictionary);
			result = new GenericDTO("", AppConstants.OK_CODE, resultList);
		} catch (Exception e) {
			result = new GenericDTO(e.getMessage(), AppConstants.ERROR_INTERNAL, "");
			Log.error(e.getMessage(),e);
		}
		String resultString = gson.toJson(result); 		
		return Response.status(200).entity(resultString).build();		
	}
	
	
	
}
