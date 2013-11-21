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
		
		String query = "insert into t_query (campaign_id) values (?) ";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setInt(1, dataObject.getCampaignId());
		ps.executeUpdate(query);
		ps.close();
		
		int queryId = -1;
		query = "select max(id) from t_query ";
		Statement s = connection.createStatement();
		ResultSet rs = s.executeQuery(query);
		rs.next();
		queryId = rs.getInt(1);
		rs.close();
		s.close();

		List<String> includingKeywords = dataObject.getIncludingKeywords();
		List<String> excludingKeywords = dataObject.getExcludingKeywords();
		

		query = "insert into t_querykeyword (name, type, query_id) values (?,?,?) ";
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
	public Query[] get(Query dataObject) throws SQLException {
		String query = "select * from t_query where ";
		Object[] bindVariables = new Object[3];
		int bindVariableCount = 0;
		if (dataObject.getId() > 0) {
			query += "name = ? and ";
			bindVariables[bindVariableCount++] = dataObject.getId();
		}
		if (dataObject.getCampaignId() > 0) {
			query += "campaign_id = ? and ";
			bindVariables[bindVariableCount++] = dataObject.getCampaignId();
		}
		
		query = query.substring(0, query.length() - 4);
						
		Connection connection = DatabaseConnector.getInstance().getConnection(); 
		PreparedStatement s = connection.prepareStatement(query);
		
		if (bindVariableCount == 0) {
			return null;
		}
		
		for (int i=0; i<bindVariableCount; ++i) {
			if (bindVariables[i] instanceof String) {
				s.setString(i+1, (String)bindVariables[i]);
			}
			else {
				s.setInt(i+1, (Integer)bindVariables[i]);
			}
		}
		
		ResultSet rs = s.executeQuery();
		int count = 0;
		Query[] qCache = new Query[100];
		while (rs.next()) {
			
			Query q = new Query();
			q.setId( rs.getInt("id") );
			q.setCampaignId( rs.getInt("campaign_id") );
			
			qCache[count++] = q;
			
			List<String> includingKeywords = new ArrayList<String>();
			List<String> excludingKeywords = new ArrayList<String>();
			
			String kQuery = "select * from t_querykeyword where query_id = ? ";
			PreparedStatement ps2 = connection.prepareStatement(kQuery);
			ps2.setInt(1, q.getId());
			ResultSet rs2 = ps2.executeQuery();
			
			while (rs.next()) {
				if ("including".equals( rs.getString("type") )) {
					includingKeywords.add( rs.getString("name") );
				}
				else if ("excluding".equals( rs.getString("type") )) {
					excludingKeywords.add( rs.getString("name") );
				}
			}
			q.setIncludingKeywords(includingKeywords);
			q.setExcludingKeywords(excludingKeywords);
			
			rs2.close();
			ps2.close();
			
		}
		
		rs.close();
		s.close();
		connection.close();
		
		Query[] queries = new Query[count];
		for (int k=0;k<count;++k) {
			queries[k] = qCache[k];
		}
		return queries;
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

		query = "create table IF NOT EXISTS t_query (id int(10) NOT NULL AUTO_INCREMENT, campaign_id int(10), PRIMARY KEY (id)) ";
		s = connection.createStatement();
		s.executeUpdate(query);
		s.close();
		
		connection.close();
		
		return true;
	}
	
	public void addKeyword(int queryId, String keyword, String type) throws SQLException {
		Connection connection = DatabaseConnector.getInstance().getConnection(); 

		String query = "insert into t_querykeyword (name, type, query_id) values (?,?,?) ";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, keyword);
		ps.setString(2, type);
		ps.setInt(3, queryId);
		ps.executeUpdate();
		ps.close();

		connection.close();
	}
	
	public void removeKeyword(int queryId, String keyword, String type) throws SQLException {
		Connection connection = DatabaseConnector.getInstance().getConnection(); 

		String query = "delete from t_querykeyword where name = ? and type = ? and query_id = ? ";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, keyword);
		ps.setString(2, type);
		ps.setInt(3, queryId);
		ps.executeUpdate();
		ps.close();
		
		connection.close();
	}

}
