package com.myklover.api.cassandra;

import java.util.List;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.myklover.helpers.PropertiesHelper;
import com.myklover.helpers.constants.PropertiiesConstants;

public class CassandraDriver {

	private static final Cluster cluster = Cluster.builder()
	                  .addContactPoint(PropertiesHelper.getStringConfigProperty(PropertiiesConstants.CONFIG_DATABASE_HOST))
	                  .withPort(PropertiesHelper.getIntConfigProperty(PropertiiesConstants.CONFIG_DATABASE_PORT))
	                  .withCredentials("myklover", "myklover")
	                  .build();
	private static final Session session = cluster.connect("myklover");
		
	@SuppressWarnings("unchecked")
	public static ResultSet executeStatement(String stringStatement, List<Object> args){
		PreparedStatement statement = session.prepare(stringStatement);
		BoundStatement boundStatement = new BoundStatement(statement);
		boundStatement.bind();
		Integer i =0;
		for(Object obj : args){
			boundStatement.set(i, obj, (Class)obj.getClass());
			i++;
		}				
		return session.execute(boundStatement);
	}
	
	
}
