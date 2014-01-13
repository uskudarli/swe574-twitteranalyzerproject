package swe574.g2.twitteranalysis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import swe574.g2.twitteranalysis.Query;
import swe574.g2.twitteranalysis.database.DataAccessObject;
import swe574.g2.twitteranalysis.database.DatabaseConnector;

public class QueryDAO implements DataAccessObject<Query> {

	@Override
	public boolean save(Connection connection, Query dataObject) throws SQLException {
		Connection availableConnection = connection;
		
		if (availableConnection == null) {
			availableConnection = DatabaseConnector.getInstance().getConnection();
		}
		
		String query = "insert into t_query (campaign_id, query_title, active) values (?,?,?) ";
		PreparedStatement ps = availableConnection.prepareStatement(query);
		int queryId = -1;

		Object[] bindVariables = new Object[4];
		int bindVariableCount = 0;
		
		if (dataObject != null && dataObject.getId() < 1) {
			query =  "insert into t_query (campaign_id, query_title, active) values (?,?,?) ";
			ps = availableConnection.prepareStatement(query);
			ps.setInt(1, dataObject.getCampaignId());
			ps.setString(2, dataObject.getQueryTitle());
			ps.setString(3, dataObject.getActive());
			ps.executeUpdate();
			ps.close();

			query = "select max(id) from t_query ";
			Statement s = availableConnection.createStatement();
			ResultSet rs = s.executeQuery(query);
			rs.next();
			queryId = rs.getInt(1);
			rs.close();
			s.close();
		}
		else {
			queryId = dataObject.getId();
			
			query = "update t_query set ";
			if (dataObject.getQueryTitle() != null) {
				query += "query_title = ?,";
				bindVariables[bindVariableCount++] = dataObject.getQueryTitle();
			}
			if (dataObject.getActive() != null) {
				query += "active = ?,";
				bindVariables[bindVariableCount++] = dataObject.getActive();
			}
			
			query = query.substring(0, query.length() - 1) + " where id = ? ";
			bindVariables[bindVariableCount++] = dataObject.getId();
			
			PreparedStatement s = availableConnection.prepareStatement(query);
			
			for (int i=0; i<bindVariableCount; ++i) {
				if (bindVariables[i] instanceof String) {
					s.setString(i+1, (String)bindVariables[i]);
				}
				else {
					s.setInt(i+1, (Integer)bindVariables[i]);
				}
			}
			
			s.executeUpdate();
		}
		
		List<String> includingKeywords = dataObject.getIncludingKeywords();
		List<String> excludingKeywords = dataObject.getExcludingKeywords();
		
		try {
			query = "delete from t_querykeyword where query_id = ? ";
			ps = availableConnection.prepareStatement(query);
			
			try {
				ps.setInt(1, queryId);
				ps.executeUpdate();
			} catch (Exception e) {
				// do nothing
			}
			finally {
				ps.close();
			}
		} catch (SQLException e1) {
			// do nothing
		}
		
		query = "insert into t_querykeyword (name, type, query_id) values (?,?,?) ";
		if (includingKeywords != null) {
			for (String k : includingKeywords) {
				try {
					ps = availableConnection.prepareStatement(query);
					ps.setString(1, k);
					ps.setString(2, "including");
					ps.setInt(3, queryId);
					ps.executeUpdate();
				} catch (MySQLIntegrityConstraintViolationException e) {
					// do nothing
				}
				finally {
					ps.close();
				}
				
			}
		}
		
		if (excludingKeywords != null) {
			for (String k : excludingKeywords) {
				try {
					ps = availableConnection.prepareStatement(query);
					ps.setString(1, k);
					ps.setString(2, "excluding");
					ps.setInt(3, queryId);
					ps.executeUpdate();
				} catch (MySQLIntegrityConstraintViolationException e) {
					// do nothing
				}
				finally {
					ps.close();
				}
			}
		}

		// do not close connection that isn't opened by yourself
		if (connection == null) {
			DatabaseConnector.getInstance().closeConnection(availableConnection);
		}
		
		return true;
	}
	
	public Query saveAndGet(Connection connection, Query dataObject) throws SQLException {
		Connection availableConnection = connection;
		
		if (availableConnection == null) {
			availableConnection = DatabaseConnector.getInstance().getConnection();
		}
		
		String query = "insert into t_query (campaign_id, query_title, active) values (?,?,?) ";
		PreparedStatement ps = availableConnection.prepareStatement(query);
		int queryId = -1;
		
		if (dataObject != null && dataObject.getId() < 1) {
			query = "insert into t_query (campaign_id, query_title, active) values (?,?,?) ";
			ps = availableConnection.prepareStatement(query);
			ps.setInt(1, dataObject.getCampaignId());
			ps.setString(2, dataObject.getQueryTitle());
			ps.setString(3, dataObject.getActive());
			//queryId = ps.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			queryId = rs.getInt(1);
			dataObject.setId(queryId);
			//ps.executeUpdate();
			ps.close();

			/*query = "select max(id) from t_query ";
			Statement s = availableConnection.createStatement();
			ResultSet rs = s.executeQuery(query);
			rs.next();
			queryId = rs.getInt(1);*/

			//rs.close();
			//s.close();
		}
		else {
			queryId = dataObject.getId();
		}
		
		
		List<String> includingKeywords = dataObject.getIncludingKeywords();
		List<String> excludingKeywords = dataObject.getExcludingKeywords();
		
		try {
			query = "delete from t_querykeyword where query_id = ? ";
			ps = availableConnection.prepareStatement(query);
			
			try {
				ps.setInt(1, queryId);
				ps.executeUpdate();
			} catch (Exception e) {
				// do nothing
			}
			finally {
				ps.close();
			}
		} catch (SQLException e1) {
			// do nothing
		}
		
		query = "insert into t_querykeyword (name, type, query_id) values (?,?,?) ";
		if (includingKeywords != null) {
			for (String k : includingKeywords) {
				try {
					ps = availableConnection.prepareStatement(query);
					ps.setString(1, k);
					ps.setString(2, "including");
					ps.setInt(3, queryId);
					ps.executeUpdate();
				} catch (MySQLIntegrityConstraintViolationException e) {
					// do nothing
				}
				finally {
					ps.close();
				}
				
			}
		}
		
		if (excludingKeywords != null) {
			for (String k : excludingKeywords) {
				try {
					ps = availableConnection.prepareStatement(query);
					ps.setString(1, k);
					ps.setString(2, "excluding");
					ps.setInt(3, queryId);
					ps.executeUpdate();
				} catch (MySQLIntegrityConstraintViolationException e) {
					// do nothing
				}
				finally {
					ps.close();
				}
			}
		}

		// do not close connection that isn't opened by yourself
		if (connection == null) {
			DatabaseConnector.getInstance().closeConnection(availableConnection);
		}
		
		return dataObject;
	}

	@Override
	public boolean remove(Connection connection, Query dataObject) throws SQLException {
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
				
		Connection availableConnection = connection;
		
		if (availableConnection == null) {
			availableConnection = DatabaseConnector.getInstance().getConnection();
		}
		
		PreparedStatement s = availableConnection.prepareStatement(query);
		
		for (int i=0; i<bindVariableCount; ++i) {
			if (bindVariables[i] instanceof String) {
				s.setString(i+1, (String)bindVariables[i]);
			}
			else {
				s.setInt(i+1, (Integer)bindVariables[i]);
			}
		}
		
		s.executeUpdate();
		
		// do not close connection that isn't opened by yourself
		if (connection == null) {
			DatabaseConnector.getInstance().closeConnection(availableConnection);
		}
		
		return true;
	}

	@Override
	public Query[] get(Connection connection, Query dataObject) throws SQLException {
		String query = "select * from t_query where ";
		Object[] bindVariables = new Object[3];
		int bindVariableCount = 0;
		if (dataObject.getId() > 0) {
			query += "id = ? and ";
			bindVariables[bindVariableCount++] = dataObject.getId();
		}
		if (dataObject.getCampaignId() > 0) {
			query += "campaign_id = ? and ";
			bindVariables[bindVariableCount++] = dataObject.getCampaignId();
		}
		if (dataObject.getActive() != null) {
			query += "active = ? and ";
			bindVariables[bindVariableCount++] = dataObject.getActive();
		}
		
		query = query.substring(0, query.length() - 4);
						
		Connection availableConnection = connection;
		
		if (availableConnection == null) {
			availableConnection = DatabaseConnector.getInstance().getConnection();
		}
		
		PreparedStatement s = availableConnection.prepareStatement(query);
		
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
			q.setQueryTitle(rs.getString("query_title"));
			q.setActive(rs.getString("active"));
			
			List<String> includingKeywords = new ArrayList<String>();
			List<String> excludingKeywords = new ArrayList<String>();
			
			String kQuery = "select * from t_querykeyword where query_id = ? ";
			PreparedStatement ps2 = availableConnection.prepareStatement(kQuery);
			ps2.setInt(1, q.getId());
			ResultSet rs2 = ps2.executeQuery();
			
			while (rs2.next()) {
				if ("including".equals( rs2.getString("type") )) {
					includingKeywords.add( rs2.getString("name") );
				}
				else if ("excluding".equals( rs2.getString("type") )) {
					excludingKeywords.add( rs2.getString("name") );
				}
			}
			q.setIncludingKeywords(includingKeywords);
			q.setExcludingKeywords(excludingKeywords);
			
			qCache[count++] = q;
			
			rs2.close();
			ps2.close();
			
		}
		
		rs.close();
		s.close();
		
		// do not close connection that isn't opened by yourself
		if (connection == null) {
			DatabaseConnector.getInstance().closeConnection(availableConnection);
		}
		
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
	public boolean init(Connection connection) throws SQLException {
		Connection availableConnection = connection;
		
		if (availableConnection == null) {
			availableConnection = DatabaseConnector.getInstance().getConnection();
		}
		
		String query = "create table IF NOT EXISTS t_querykeyword (id int(10) NOT NULL AUTO_INCREMENT, name varchar(512), type varchar(512), query_id int(10), PRIMARY KEY (id), UNIQUE(name(100),type(10),query_id)) ";

		Statement s = availableConnection.createStatement();
		s.executeUpdate(query);
		s.close();

		query = "create table IF NOT EXISTS t_query (id int(10) NOT NULL AUTO_INCREMENT, campaign_id int(10), query_title varchar(512), active varchar(4), PRIMARY KEY (id)) ";
		s = availableConnection.createStatement();
		s.executeUpdate(query);
		s.close();

		// do not close connection that isn't opened by yourself
		if (connection == null) {
			DatabaseConnector.getInstance().closeConnection(availableConnection);
		}
		
		return true;
	}
	
	public void addKeyword(Connection connection, int queryId, String keyword, String type) throws SQLException {
		Connection availableConnection = connection;
		
		if (availableConnection == null) {
			availableConnection = DatabaseConnector.getInstance().getConnection();
		}
		 

		String query = "insert into t_querykeyword (name, type, query_id) values (?,?,?) ";
		PreparedStatement ps = availableConnection.prepareStatement(query);
		ps.setString(1, keyword);
		ps.setString(2, type);
		ps.setInt(3, queryId);
		ps.executeUpdate();
		ps.close();

		// do not close connection that isn't opened by yourself
		if (connection == null) {
			DatabaseConnector.getInstance().closeConnection(availableConnection);
		}
	}
	
	public void removeKeyword(Connection connection, int queryId, String keyword, String type) throws SQLException {
		Connection availableConnection = connection;
		
		if (availableConnection == null) {
			availableConnection = DatabaseConnector.getInstance().getConnection();
		}
		 

		String query = "delete from t_querykeyword where name = ? and type = ? and query_id = ? ";
		PreparedStatement ps = availableConnection.prepareStatement(query);
		ps.setString(1, keyword);
		ps.setString(2, type);
		ps.setInt(3, queryId);
		ps.executeUpdate();
		ps.close();

		// do not close connection that isn't opened by yourself
		if (connection == null) {
			DatabaseConnector.getInstance().closeConnection(availableConnection);
		}
	}

	@Override
	public boolean init() throws SQLException {
		return init(null);
	}

	@Override
	public boolean save(Query dataObject) throws SQLException {
		return save(null, dataObject);
	}

	@Override
	public boolean remove(Query dataObject) throws SQLException {
		return remove(null, dataObject);
	}

	@Override
	public Query[] get(Query dataObject) throws SQLException {
		return get(null, dataObject);
	}

	@Override
	public Query[] find(Connection connection, String query)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
