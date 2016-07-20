package com.myklover.api;

import java.util.ArrayList;
import java.util.List;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.myklover.api.cassandra.CassandraDriver;

public abstract class GenericAPI {

		
	protected static ResultSet executeStatement(String stringStatement,List<Object> args){
		ResultSet result = CassandraDriver.executeStatement(stringStatement, args);
		return result;
	}
	
	private static List<Object> getResult(ResultSet result){  	
		List<Object> resultList = new ArrayList<Object>();
		for (Row row : result) {
			resultList.add(getElement(row));
		}
		return resultList;
	}
	
	
	private static Object getElement(Row row) {
		return null;
	}
	
	
}
