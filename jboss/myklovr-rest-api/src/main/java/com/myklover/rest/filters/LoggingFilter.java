package com.myklover.rest.filters;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

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
			Log.info("Request method: "+req.getHttpMethod()+ ", Request URI: "+res.getMethod());
			return null;
		}
	
}
