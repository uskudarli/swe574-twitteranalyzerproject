package swe574.g2.twitteranalysis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import swe574.g2.twitteranalysis.ApplicationUser;
import swe574.g2.twitteranalysis.database.DataAccessObject;
import swe574.g2.twitteranalysis.database.DatabaseConnector;

public class ApplicationUserDAO implements DataAccessObject<ApplicationUser> {

	@Override
	public boolean init() throws SQLException {
		return init( null );
	}
	
	@Override
	public boolean init(Connection connection) throws SQLException {
		Connection availableConnection = connection;
		
		if (availableConnection == null) {
			availableConnection = DatabaseConnector.getInstance().getConnection();
		}
		
		String query = "create table IF NOT EXISTS t_applicationuser (id int(10) NOT NULL AUTO_INCREMENT, name varchar(512), email varchar(512), password varchar(512), PRIMARY KEY (id), UNIQUE(email(100))) ";
		Statement s = availableConnection.createStatement();
		s.executeUpdate(query);
		
		// do not close connection that isn't opened by yourself
		if (connection == null) {
			DatabaseConnector.getInstance().closeConnection(availableConnection);
		}
			
		
		return true;
	}
	
	@Override
	public boolean save(ApplicationUser dataObject) throws SQLException {
		return save(null, dataObject);
	}
	
	@Override
	public boolean save(Connection connection, ApplicationUser dataObject) throws SQLException {
		String query = "";
		Object[] bindVariables = new Object[4];
		int bindVariableCount = 0;
		
		if (dataObject.getId() > 0) {
		
		    query = "update t_applicationuser set ";
			if (dataObject.getName() != null) {
				query += "name = ?, ";
				bindVariables[bindVariableCount++] = dataObject.getName();
			}
			if (dataObject.getEmail() != null) {
				query += "email = ?, ";
				bindVariables[bindVariableCount++] = dataObject.getEmail();
			}
			if (dataObject.getHashedPassword() != null) {
				query += "password = ?, ";
				bindVariables[bindVariableCount++] = dataObject.getHashedPassword();
			}
			
			if (bindVariableCount == 0) {
				return false;
			}
			
			query = query.substring(0, query.length() - 2) + " where id = ? ";
			bindVariables[bindVariableCount++] = dataObject.getId();
		}
		else {
			query = "insert into t_applicationuser (";
			if (dataObject.getName() != null) {
				query += "name,";
				bindVariables[bindVariableCount++] = dataObject.getName();
			}
			if (dataObject.getEmail() != null) {
				query += "email,";
				bindVariables[bindVariableCount++] = dataObject.getEmail();
			}
			if (dataObject.getHashedPassword() != null) {
				query += "password,";
				bindVariables[bindVariableCount++] = dataObject.getHashedPassword();
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
	public boolean remove(ApplicationUser dataObject) throws SQLException {
		return remove(null, dataObject);
	}

	@Override
	public boolean remove(Connection connection, ApplicationUser dataObject) throws SQLException {
		String query = "delete from t_applicationuser where ";
		Object[] bindVariables = new Object[5];
		int bindVariableCount = 0;
		if (dataObject.getName() != null) {
			query += "name = ? and ";
			bindVariables[bindVariableCount++] = dataObject.getName();
		}
		if (dataObject.getEmail() != null) {
			query += "email = ? and ";
			bindVariables[bindVariableCount++] = dataObject.getEmail();
		}
		if (dataObject.getHashedPassword() != null) {
			query += "password = ? and ";
			bindVariables[bindVariableCount++] = dataObject.getHashedPassword();
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
	public ApplicationUser[] get(ApplicationUser dataObject)
			throws SQLException {
		return get(null, dataObject);
	}

	@Override
	public ApplicationUser[] get(Connection connection, ApplicationUser dataObject) throws SQLException {
		String query = "select * from t_applicationuser where ";
		Object[] bindVariables = new Object[4];
		int bindVariableCount = 0;
		if (dataObject.getName() != null) {
			query += "name = ? and ";
			bindVariables[bindVariableCount++] = dataObject.getName();
		}
		if (dataObject.getEmail() != null) {
			query += "email = ? and ";
			bindVariables[bindVariableCount++] = dataObject.getEmail();
		}
		if (dataObject.getHashedPassword() != null) {
			query += "password = ? and ";
			bindVariables[bindVariableCount++] = dataObject.getHashedPassword();
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
		int i=0;
		ApplicationUser[] appUsersCache = new ApplicationUser[100];
		
		while (rs.next()) {
			ApplicationUser applicationUser = new ApplicationUser();
			applicationUser.setId( rs.getInt("id") );
			applicationUser.setName( rs.getString("name") );
			applicationUser.setEmail( rs.getString("email") );
			applicationUser.setHashedPassword( rs.getString("password") );
			appUsersCache[i++] = applicationUser;
		}
		ApplicationUser[] appUsers = new ApplicationUser[i];
		for (int k=0; k<i; ++k) {
			appUsers[k] = appUsersCache[k];
		}

		// do not close connection that isn't opened by yourself
		if (connection == null) {
			DatabaseConnector.getInstance().closeConnection(availableConnection);
		}
		
		return appUsers;
	}
	
	@Override
	public ApplicationUser[] find(String query) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplicationUser[] find(Connection connection, String query) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
