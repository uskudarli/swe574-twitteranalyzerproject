package swe574.g2.twitteranalysis.dao;

import java.sql.SQLException;

import swe574.g2.twitteranalysis.Hashtag;
import swe574.g2.twitteranalysis.database.DataAccessObject;

public class HashtagDAO implements DataAccessObject<Hashtag> {

	@Override
	public boolean save(Hashtag dataObject) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Hashtag dataObject) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Hashtag get(Hashtag dataObject) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hashtag[] find(String query) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean init() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}
