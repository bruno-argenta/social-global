package com.myklover.helpers;

import java.util.ResourceBundle;

import com.myklover.helpers.constants.RoutesConstants;

public class PropertiesHelper {
	
	private static String getMessage(String ruta, String key){
		ResourceBundle rb = ResourceBundle.getBundle(ruta);
		return rb.getString(key);
    }    
        
    public static String getStringConfigProperty (String key){
    	return getMessage(RoutesConstants.CONFIG_PROPERTIES, key);
    }
    
    public static int getIntConfigProperty (String key){
    	String value = getMessage(RoutesConstants.CONFIG_PROPERTIES, key);
    	int intValue = Integer.parseInt(value);
    	return intValue;
    }
    
    
    public static String getStringMessageProperty (String key){
    	return getMessage(RoutesConstants.MESSAGE_PROPERTIES, key);
    }

}
