package com.myklovr.api;

import java.util.List;

import com.datastax.driver.core.ResultSet;
import com.myklovr.api.cassandra.CassandraDriver;

public abstract class GenericAPI {

		
	protected static ResultSet executeStatement(String stringStatement,List<Object> args){
		ResultSet result = CassandraDriver.executeStatement(stringStatement, args);
		return result;
	}

	
	
}
