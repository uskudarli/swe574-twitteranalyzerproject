package swe574.g2.twitteranalysis.dao;

import java.sql.SQLException;

import swe574.g2.twitteranalysis.Tweet;
import swe574.g2.twitteranalysis.database.DataAccessObject;

public class TweetDAO implements DataAccessObject<Tweet> {

	@Override
	public boolean save(Tweet dataObject) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Tweet dataObject) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Tweet[] get(Tweet dataObject) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tweet[] find(String query) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean init() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}
