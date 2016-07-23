package com.myklover.rest.filters;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;

import org.apache.http.ParseException;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;

import com.myklover.logger.Log;

@Provider
@ServerInterceptor
public class LoggingFilter implements PreProcessInterceptor{	  

		


		@Override
		public ServerResponse preProcess(HttpRequest req, ResourceMethod res)
				throws Failure, WebApplicationException {
			// TODO Auto-generated method stub			
			try {
				/*StringWriter body = new StringWriter();
				HttpRequest request = (HttpRequest) new HttpServletRequestWrapper((HttpServletRequest)req);
	            IOUtils.copy(request.getInputStream(), body, "UTF-8");*/
				Log.info("Request method: "+req.getHttpMethod()+ ", Request URI: "+req.getUri().getPath()+ " Parameters: " + req.getFormParameters() + " Body: "+req.getInputStream());
			} catch (ParseException e) {
				Log.error("Could not get body parameteres",e);
				e.printStackTrace();
			} 
			return null;
		}
	
}
