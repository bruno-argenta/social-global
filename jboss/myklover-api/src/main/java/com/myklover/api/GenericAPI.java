package com.myklover.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.myklover.api.cassandra.CassandraDriver;

public abstract class GenericAPI {

	
	protected static String createStatement(String statement,HashMap<String,String> args){		
		for (String key : args.keySet()) {
		    String value = args.get(key);
		    StringUtils.replace(statement, key, value);
		}
		return statement;
	}
	
	protected static List<Object> executeStatement(String stringStatement,List<Object> args){
		ResultSet result =CassandraDriver.executeStatement(stringStatement, args);
		return getResult(result);
	}
	
	private static List<Object> getResult(ResultSet result){  	
		List<Object> resultList = new ArrayList<Object>();
		for (Row row : result) {
			resultList.add(getElement(row));
		}
		return resultList;
	}
	
	
	protected static Object getElement(Row row) {
		return null;
	}
	
	
}
