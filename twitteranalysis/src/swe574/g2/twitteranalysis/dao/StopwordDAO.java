package swe574.g2.twitteranalysis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import swe574.g2.twitteranalysis.ApplicationUser;
import swe574.g2.twitteranalysis.Stopword;
import swe574.g2.twitteranalysis.database.DataAccessObject;
import swe574.g2.twitteranalysis.database.DatabaseConnector;

public class StopwordDAO implements DataAccessObject<Stopword>{

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
	public boolean save(Stopword dataObject) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean save(Connection connection, Stopword dataObject)
			throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Stopword dataObject) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Connection connection, Stopword dataObject)
			throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Stopword[] get(Stopword dataObject) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stopword[] get(Connection connection, Stopword dataObject)
			throws SQLException {

		return null;
	}
	
	public String[] getStopwords(Connection connection) throws SQLException{
		String query = "select * from t_stopword where is_enabled = 'E'";

		Connection availableConnection = connection;
		
		if (availableConnection == null) {
			availableConnection = DatabaseConnector.getInstance().getConnection();
		}
		
		PreparedStatement s = availableConnection.prepareStatement(query);
				
		ResultSet rs = s.executeQuery();
		int i=0;
		List<String> stopwords = new ArrayList(1000);
		
		while (rs.next()) {
			String stopword = rs.getString("word");
			stopwords.add(stopword);
		}

		// do not close connection that isn't opened by yourself
		if (connection == null) {
			DatabaseConnector.getInstance().closeConnection(availableConnection);
		}
		
		String[] stopWordArray = new String[stopwords.size()];
		
		return stopwords.toArray(stopWordArray);
	}

	@Override
	public Stopword[] find(String query) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stopword[] find(Connection connection, String query)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
