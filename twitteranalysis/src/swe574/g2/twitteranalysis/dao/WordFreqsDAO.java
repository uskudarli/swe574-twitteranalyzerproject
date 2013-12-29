package swe574.g2.twitteranalysis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import swe574.g2.twitteranalysis.ApplicationUser;
import swe574.g2.twitteranalysis.WordFreqs;
import swe574.g2.twitteranalysis.database.DataAccessObject;
import swe574.g2.twitteranalysis.database.DatabaseConnector;

public class WordFreqsDAO implements DataAccessObject<WordFreqs>{

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
	public boolean save(WordFreqs dataObject) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean save(Connection connection, WordFreqs dataObject)
			throws SQLException {
		String query = "insert into t_wordfreqs ( ";
		Object[] bindVariables = new Object[5];
		int bindVariableCount = 0;
		
		if (dataObject.getCampaignId() != 0) {
			query += "campaign_id,";
			bindVariables[bindVariableCount++] = dataObject.getCampaignId();
		}
		if (dataObject.getQueryId() != 0) {
			query += "query_id,";
			bindVariables[bindVariableCount++] = dataObject.getQueryId();
		}
		if (dataObject.getSentiment() != null) {
			query += "sentiment,";
			bindVariables[bindVariableCount++] = dataObject.getSentiment();
		}
		if (dataObject.getSentiment() != null) {
			query += "word,";
			bindVariables[bindVariableCount++] = dataObject.getWord();
		}
		if (dataObject.getSentiment() != null) {
			query += "frequency,";
			bindVariables[bindVariableCount++] = dataObject.getFrequency();
		}
		
		if (bindVariableCount == 0) {
			return false;
		}
		
		query = query.substring(0, query.length() - 1) + ") values (";
		for (int i=0; i<bindVariableCount; ++i) {
			query += "?,";
		}
		query = query.substring(0, query.length() - 1) + ") ";
		

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
	public boolean remove(WordFreqs dataObject) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Connection connection, WordFreqs dataObject)
			throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public WordFreqs[] get(WordFreqs dataObject) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WordFreqs[] get(Connection connection, WordFreqs dataObject)
			throws SQLException {
		String query = "select * from t_wordfreqs where ";
		Object[] bindVariables = new Object[4];
		int bindVariableCount = 0;
		
		if (dataObject.getCampaignId() != 0) {
			query += "campaign_id = ? and ";
			bindVariables[bindVariableCount++] = dataObject.getCampaignId();
		}
		if (dataObject.getQueryId() != 0) {
			query += "query_id = ? and ";
			bindVariables[bindVariableCount++] = dataObject.getQueryId();
		}
		if (dataObject.getSentiment() != null) {
			query += "sentiment = ? and ";
			bindVariables[bindVariableCount++] = dataObject.getSentiment();
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
		List<WordFreqs> wordFreqs = new ArrayList<WordFreqs>();
		
		while (rs.next()) {
			WordFreqs wordFreq = new WordFreqs();
			wordFreq.setId(rs.getInt("id") );
			wordFreq.setCampaignId(rs.getInt("campaign_id"));
			wordFreq.setQueryId(rs.getInt("query_id"));
			wordFreq.setWord(rs.getString("word"));
			wordFreq.setFrequency(rs.getInt("frequency"));
			wordFreq.setSentiment(rs.getString("sentiment"));
			
			wordFreqs.add(wordFreq);
			
			
		}

		WordFreqs[] wordFreqArray = new WordFreqs[wordFreqs.size()];
		
		// do not close connection that isn't opened by yourself
		if (connection == null) {
			DatabaseConnector.getInstance().closeConnection(availableConnection);
		}
		
		return wordFreqs.toArray(wordFreqArray);
	}

	@Override
	public WordFreqs[] find(String query) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WordFreqs[] find(Connection connection, String query)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
