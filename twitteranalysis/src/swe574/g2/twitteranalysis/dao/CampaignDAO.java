package swe574.g2.twitteranalysis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import swe574.g2.twitteranalysis.Campaign;
import swe574.g2.twitteranalysis.database.DataAccessObject;
import swe574.g2.twitteranalysis.database.DatabaseConnector;

public class CampaignDAO implements DataAccessObject<Campaign> {
	
	@Override
	public boolean save(Connection connection, Campaign dataObject) throws SQLException {
		String query = "";
		Object[] bindVariables = new Object[4];
		int bindVariableCount = 0;
		
		if (dataObject.getId() > 0) {
		
		    query = "update t_campaign set ";
			if (dataObject.getName() != null) {
				query += "name = ?,";
				bindVariables[bindVariableCount++] = dataObject.getName();
			}
			if (dataObject.getDescription() != null) {
				query += "description = ?,";
				bindVariables[bindVariableCount++] = dataObject.getDescription();
			}
			if (dataObject.getOwnerUserId() > 0) {
				query += "applicationuser_id = ?,";
				bindVariables[bindVariableCount++] = dataObject.getOwnerUserId();
			}
			
			if (bindVariableCount == 0) {
				return false;
			}
			
			query = query.substring(0, query.length() - 1) + " where id = ? ";
			bindVariables[bindVariableCount++] = dataObject.getId();
		}
		else {
			query = "insert into t_campaign (";
			if (dataObject.getName() != null) {
				query += "name,";
				bindVariables[bindVariableCount++] = dataObject.getName();
			}
			if (dataObject.getDescription() != null) {
				query += "description,";
				bindVariables[bindVariableCount++] = dataObject.getDescription();
			}
			if (dataObject.getOwnerUserId() > 0) {
				query += "applicationuser_id,";
				bindVariables[bindVariableCount++] = dataObject.getOwnerUserId();
			}
			
			if (bindVariableCount == 0) {
				return false;
			}
			
			query = query.substring(0, query.length() - 1) + ") values (";
			for (int i=0; i<bindVariableCount; ++i) {
				query += "?,";
			}
			query = query.substring(0, query.length() - 1) + ") ";
		}
		System.out.println(query);
		
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
	public boolean remove(Connection connection, Campaign dataObject) throws SQLException {
		String query = "delete from t_campaign where ";
		Object[] bindVariables = new Object[4];
		int bindVariableCount = 0;
		if (dataObject.getName() != null) {
			query += "name = ? and ";
			bindVariables[bindVariableCount++] = dataObject.getName();
		}
		if (dataObject.getDescription() != null) {
			query += "description = ? and ";
			bindVariables[bindVariableCount++] = dataObject.getDescription();
		}
		if (dataObject.getOwnerUserId() > 0) {
			query += "applicationuser_id = ? and ";
			bindVariables[bindVariableCount++] = dataObject.getOwnerUserId();
		}
		if (dataObject.getId() > 0) {
			query += "id = ? and ";
			bindVariables[bindVariableCount++] = dataObject.getId();
		}
		
		if (bindVariableCount == 0) {
			return false;
		}
		
		query = query.substring(0, query.length() - 4);

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
	public Campaign[] get(Connection connection, Campaign dataObject) throws SQLException {
		String query = "select * from t_campaign where ";
		Object[] bindVariables = new Object[4];
		int bindVariableCount = 0;
		if (dataObject.getName() != null) {
			query += "name = ? and ";
			bindVariables[bindVariableCount++] = dataObject.getName();
		}
		if (dataObject.getDescription() != null) {
			query += "description = ? and ";
			bindVariables[bindVariableCount++] = dataObject.getDescription();
		}
		if (dataObject.getOwnerUserId() > 0) {
			query += "applicationuser_id = ? and ";
			bindVariables[bindVariableCount++] = dataObject.getOwnerUserId();
		}
		if (dataObject.getId() > 0) {
			query += "id = ? and ";
			bindVariables[bindVariableCount++] = dataObject.getId();
		}
		
		if (bindVariableCount == 0) {
			return null;
		}
		
		query = query.substring(0, query.length() - 4);

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
		
		ResultSet rs = s.executeQuery();
		Campaign[] campaignsCache = new Campaign[100];
		int i=0;
		while(rs.next()) {
			Campaign campaign = new Campaign();
			campaign.setId( rs.getInt("id") );
			campaign.setName( rs.getString("name") );
			campaign.setDescription( rs.getString("description") );
			campaign.setOwnerUserId( rs.getInt("applicationuser_id") );
			campaignsCache[i++] = campaign;
		}

		Campaign[] campaigns = new Campaign[i];
		for (int k=0; k<i; ++k) {
			campaigns[k] = campaignsCache[k];
		}
		

		// do not close connection that isn't opened by yourself
		if (connection == null) {
			DatabaseConnector.getInstance().closeConnection(availableConnection);
		}
		
		return campaigns;
	}

	@Override
	public Campaign[] find(String query) throws SQLException {
		return null;
	}

	@Override
	public boolean init(Connection connection) throws SQLException {
		Connection availableConnection = connection;
		
		if (availableConnection == null) {
			availableConnection = DatabaseConnector.getInstance().getConnection();
		}
		
		String query = "create table IF NOT EXISTS t_campaign (id int(10) NOT NULL AUTO_INCREMENT, name varchar(512), description text, applicationuser_id int(10), PRIMARY KEY (id)) ";
		Statement s = availableConnection.createStatement();
		s.executeUpdate(query);

		// do not close connection that isn't opened by yourself
		if (connection == null) {
			DatabaseConnector.getInstance().closeConnection(availableConnection);
		}
		
		return true;
	}

	@Override
	public boolean init() throws SQLException {
		return init(null);
	}

	@Override
	public boolean save(Campaign dataObject) throws SQLException {
		return save(null, dataObject);
	}

	@Override
	public boolean remove(Campaign dataObject) throws SQLException {
		return remove(null, dataObject);
	}

	@Override
	public Campaign[] get(Campaign dataObject) throws SQLException {
		return get(null, dataObject);
	}

	@Override
	public Campaign[] find(Connection connection, String query)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
