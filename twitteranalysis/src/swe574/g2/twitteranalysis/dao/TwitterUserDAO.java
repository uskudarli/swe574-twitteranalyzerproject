package swe574.g2.twitteranalysis.dao;

import java.sql.Connection;
import java.sql.SQLException;

import swe574.g2.twitteranalysis.TwitterUser;
import swe574.g2.twitteranalysis.database.DataAccessObject;

public class TwitterUserDAO implements DataAccessObject<TwitterUser> {

	@Override
	public boolean save(TwitterUser dataObject) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(TwitterUser dataObject) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TwitterUser[] get(TwitterUser dataObject) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TwitterUser[] find(String query) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean init() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean init(Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean save(Connection connection, TwitterUser dataObject)
			throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Connection connection, TwitterUser dataObject)
			throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TwitterUser[] get(Connection connection, TwitterUser dataObject)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TwitterUser[] find(Connection connection, String query)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
