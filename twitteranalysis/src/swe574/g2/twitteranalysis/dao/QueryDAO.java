package swe574.g2.twitteranalysis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import swe574.g2.twitteranalysis.Query;
import swe574.g2.twitteranalysis.database.DataAccessObject;
import swe574.g2.twitteranalysis.database.DatabaseConnector;

public class QueryDAO implements DataAccessObject<Query> {

	@Override
	public boolean save(Query dataObject) throws SQLException {
		Connection connection = DatabaseConnector.getInstance().getConnection(); 
		
		String query = "insert into t_query () values () ";
		Statement s = connection.createStatement();
		s.executeUpdate(query);
		s.close();
		
		int queryId = -1;
		query = "select max(id) from t_query ";
		s = connection.createStatement();
		ResultSet rs = s.executeQuery(query);
		rs.next();
		queryId = rs.getInt(1);
		rs.close();
		s.close();

		List<String> includingKeywords = dataObject.getIncludingKeywords();
		List<String> excludingKeywords = dataObject.getExcludingKeywords();
		

		query = "insert into t_querykeyword (name, type, query_id) values (?,?,?) ";
		PreparedStatement ps;
		if (includingKeywords != null) {
			for (String k : includingKeywords) {
				ps = connection.prepareStatement(query);
				ps.setString(1, k);
				ps.setString(2, "including");
				ps.setInt(3, queryId);
				ps.executeUpdate();
				ps.close();
			}
		}
		
		if (excludingKeywords != null) {
			for (String k : excludingKeywords) {
				ps = connection.prepareStatement(query);
				ps.setString(1, k);
				ps.setString(2, "excluding");
				ps.setInt(3, queryId);
				ps.executeUpdate();
				ps.close();
			}
		}
		
		connection.close();
		
		return true;
	}

	@Override
	public boolean remove(Query dataObject) throws SQLException {
		String query = "delete from t_query where ";
		Object[] bindVariables = new Object[4];
		int bindVariableCount = 0;
		
		if (dataObject.getId() > 0) {
			query += "id = ? ";
			bindVariables[bindVariableCount++] = dataObject.getId();
		}
		
		if (bindVariableCount == 0) {
			return false;
		}
				
		Connection connection = DatabaseConnector.getInstance().getConnection(); 
		PreparedStatement s = connection.prepareStatement(query);
		
		for (int i=0; i<bindVariableCount; ++i) {
			if (bindVariables[i] instanceof String) {
				s.setString(i+1, (String)bindVariables[i]);
			}
			else {
				s.setInt(i+1, (Integer)bindVariables[i]);
			}
		}
		
		s.executeUpdate();
		connection.close();
		
		return true;
	}

	@Override
	public Query get(Query dataObject) throws SQLException {
		String query = "select * from t_querykeyword where id = ?";
						
		Connection connection = DatabaseConnector.getInstance().getConnection(); 
		PreparedStatement s = connection.prepareStatement(query);
		
		if (dataObject.getId() <= 0) {
			return null;
		}
		
		ResultSet rs = s.executeQuery();
		
		Query queryObject = new Query();
		queryObject.setId(dataObject.getId());
		List<String> includingKeywords = new ArrayList<String>();
		List<String> excludingKeywords = new ArrayList<String>();
		
		while (rs.next()) {
			if ("including".equals( rs.getString("type") )) {
				includingKeywords.add( rs.getString("name") );
			}
			else if ("excluding".equals( rs.getString("type") )) {
				excludingKeywords.add( rs.getString("name") );
			}
		}		
		queryObject.setIncludingKeywords(includingKeywords);
		queryObject.setExcludingKeywords(excludingKeywords);
		
		connection.close();
		
		return queryObject;
	}

	@Override
	public Query[] find(String query) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean init() throws SQLException {
		String query = "create table IF NOT EXISTS t_querykeyword (id int(10) NOT NULL AUTO_INCREMENT, name varchar(512), type varchar(512), query_id int(10), PRIMARY KEY (id)) ";
		Connection connection = DatabaseConnector.getInstance().getConnection(); 

		Statement s = connection.createStatement();
		s.executeUpdate(query);
		s.close();

		query = "create table IF NOT EXISTS t_query (id int(10) NOT NULL AUTO_INCREMENT, PRIMARY KEY (id)) ";
		s = connection.createStatement();
		s.executeUpdate(query);
		s.close();
		
		connection.close();
		
		return true;
	}

}
