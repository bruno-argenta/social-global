package com.myklover.rest.socialnetwork.google;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gson.Gson;
import com.myklover.helpers.PropertiesHelper;
import com.myklover.helpers.constants.PropertiesConstants;

public class GPConnection {

	private static final String CLIENT_ID = PropertiesHelper.getStringConfigProperty(PropertiesConstants.CONFIG_GP_APP_ID);
	private static final String CLIENT_SECRET = PropertiesHelper.getStringConfigProperty(PropertiesConstants.CONFIG_GP_APP_SECRET);
	private static final String CALLBACK_URI = PropertiesHelper.getStringConfigProperty(PropertiesConstants.CONFIG_GP_APP_CALLBACK);
	
	// start google authentication constants
	private static final Iterable<String> SCOPE = Arrays.asList(PropertiesHelper.getStringConfigProperty(PropertiesConstants.CONFIG_GP_APP_SCOPE_URL).split(";"));
	private static final String USER_INFO_URL = PropertiesHelper.getStringConfigProperty(PropertiesConstants.CONFIG_GP_APP_USERINFO_URL);
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();	
	private static GoogleAuthorizationCodeFlow flow = null;


	/**
	 * Expects an Authentication Code, and makes an authenticated request for the user's profile information
	 * @return JSON formatted user profile information
	 * @param authCode authentication code provided by google
	 */
	public static GPBasicProfile getUserInfoJson(final String authCode) throws IOException {

		if (flow == null){
			flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT,
					JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, (Collection<String>) SCOPE).build();
		}
		
		final GoogleTokenResponse response = flow.newTokenRequest(authCode).setRedirectUri(CALLBACK_URI).execute();
		final Credential credential = flow.createAndStoreCredential(response, null);
		final HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(credential);
		// Make an authenticated request
		final GenericUrl url = new GenericUrl(USER_INFO_URL);
		final HttpRequest request = requestFactory.buildGetRequest(url);
		request.getHeaders().setContentType("application/json");
		final String jsonIdentity = request.execute().parseAsString();
		Gson gson = new Gson();
		GPBasicProfile profile = gson.fromJson(jsonIdentity, GPBasicProfile.class);
		return profile;

	}
	
	
}
