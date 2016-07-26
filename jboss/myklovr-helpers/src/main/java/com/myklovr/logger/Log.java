package com.myklovr.logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log {

	public Log() {
		// TODO Auto-generated constructor stub
	}

	private static Logger logInfo = null;
	private static Logger logError = null;
	private static Logger logDebug = null;
	
	private static void getInfoLogger() {		
		if (logInfo == null){
			Properties p = new Properties();
			InputStream in = Log.class.getResourceAsStream("/log4j.properties");
			try {
				p.load(in);
				in.close();
				PropertyConfigurator.configure(p);
				logInfo = Logger.getLogger("myklovrLoggerInfo");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
	
	private static void getErrorLogger() {		
		if (logError == null){
			Properties p = new Properties();
			InputStream in = Log.class.getResourceAsStream("/log4j.properties");
			try {
				p.load(in);
				in.close();
				PropertyConfigurator.configure(p);
				logError = Logger.getLogger("myklovrLoggerError");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
	
	private static void getDebugLogger() {		
		if (logDebug == null){
			Properties p = new Properties();
			InputStream in = Log.class.getResourceAsStream("/log4j.properties");
			try {
				p.load(in);
				in.close();
				PropertyConfigurator.configure(p);
				logDebug = Logger.getLogger("myklovrLoggerDebug");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}

	public static void error(String m) {
		getErrorLogger();
		logError.error(m);
	}

	public static void info(String m) {
		getInfoLogger();
		logInfo.info(m);
	}

	public static void warn(String m) {
		getErrorLogger();
		logError.warn(m);
	}

	public static void debug(String m) {
		getDebugLogger();
		logDebug.debug(m);
	}

	public static void error(String m, Throwable t) {
		getErrorLogger();
		logError.error(m, t);
	}

	public static void info(String m, Throwable t) {
		getInfoLogger();
		logInfo.info(m, t);
	}

	public static void warn(String m, Throwable t) {
		getErrorLogger();
		logError.warn(m, t);
	}

}
