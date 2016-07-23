package com.myklover.rest.socialnetwork.facebook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.google.gson.Gson;
import com.myklover.helpers.PropertiesHelper;
import com.myklover.helpers.constants.PropertiesConstants;
import com.myklover.helpers.exception.BussinesException;
import com.myklover.logger.Log;

public class FBConnection {

	public static final String FB_APP_ID = PropertiesHelper.getStringConfigProperty(PropertiesConstants.CONFIG_FB_APP_ID);
	public static final String FB_APP_SECRET = PropertiesHelper.getStringConfigProperty(PropertiesConstants.CONFIG_FB_APP_SECRET);;
	public static final String REDIRECT_URI = PropertiesHelper.getStringConfigProperty(PropertiesConstants.CONFIG_APACHE_URL);

	public static String getFBGraphUrl(String code) {
		String fbGraphUrl = "";
		try {
			fbGraphUrl = "https://graph.facebook.com/v2.3/oauth/access_token?"
					+ "client_id=" + FBConnection.FB_APP_ID + "&redirect_uri="
					+ URLEncoder.encode(FBConnection.REDIRECT_URI, "UTF-8")
					+ "&client_secret=" + FB_APP_SECRET + "&code=" + code;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return fbGraphUrl;
	}

	public static FBAccessToken getAccessToken(String code) throws BussinesException {
		
			URL fbGraphURL;
			try {
				fbGraphURL = new URL(getFBGraphUrl(code));
			} catch (MalformedURLException e) {
				e.printStackTrace();
				throw new RuntimeException("Invalid code received " + e);
			}
			URLConnection fbConnection;
			StringBuffer b = null;
			try {
				fbConnection = fbGraphURL.openConnection();
				BufferedReader in;
				in = new BufferedReader(new InputStreamReader(
						fbConnection.getInputStream()));
				String inputLine;
				b = new StringBuffer();
				while ((inputLine = in.readLine()) != null)
					b.append(inputLine + "\n");
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
				Log.error("Unable to connect with Facebook",e);
				throw new RuntimeException("Unable to connect with Facebook "
						+ e);
			}
			Gson gson = new Gson();		
			FBAccessToken accessToken = gson.fromJson(b.toString(), FBAccessToken.class); 
			
			
			if (accessToken.getAccess_token().startsWith("{")) {
				Log.error("Access Token Invalid: "
						+ accessToken.getAccess_token());
				throw new BussinesException("ERROR: Access Token Invalid");
			}
		return accessToken;
	}
	
	
	public static FBBasicProfile getFBGraph(String accessToken) throws BussinesException {
		String graph = null;
		FBBasicProfile profile;
		try {
			String g = "https://graph.facebook.com/me?access_token=" + accessToken;
			URL u = new URL(g);
			URLConnection c = u.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					c.getInputStream()));
			String inputLine;
			StringBuffer b = new StringBuffer();
			while ((inputLine = in.readLine()) != null)
				b.append(inputLine + "\n");
			in.close();
			graph = b.toString();
			Gson gson = new Gson();
			profile = gson.fromJson(graph, FBBasicProfile.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BussinesException("ERROR in getting FB graph data. " + e);
		}
		return profile;
	}
	
}
