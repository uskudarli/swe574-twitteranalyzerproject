package swe574.g2.twitteranalysis.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;

import swe574.g2.twitteranalysis.Tweet;
import swe574.g2.twitteranalysis.analysis.SentimentAnalysis;
import swe574.g2.twitteranalysis.database.DataAccessObject;
import swe574.g2.twitteranalysis.database.DatabaseConnector;

public class TweetDAO implements DataAccessObject<Tweet> {

	@Override
	public boolean save(Tweet dataObject) throws SQLException {
		return save(null, dataObject);
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
		return init(null);
	}

	@Override
	public boolean init(Connection connection) throws SQLException {
		Connection availableConnection = connection;
		
		if (availableConnection == null) {
			availableConnection = DatabaseConnector.getInstance().getConnection();
		}
		
		String query = "create table IF NOT EXISTS t_tweet (id int(10) NOT NULL AUTO_INCREMENT, content varchar(256), tweet_time timestamp, sentiment varchar(256), retweet_count int(10), favorite_count int(10), ext_tweet_id bigint, username varchar(512), campaign_id int(10), query_id int(10), PRIMARY KEY (id)) ";

		Statement s = availableConnection.createStatement();
		s.executeUpdate(query);
		s.close();

		// do not close connection that isn't opened by yourself
		if (connection == null) {
			DatabaseConnector.getInstance().closeConnection(availableConnection);
		}
		
		return true;
	}

	@Override
	public boolean save(Connection connection, Tweet dataObject)
			throws SQLException {
		Connection availableConnection = connection;
		
		if (availableConnection == null) {
			availableConnection = DatabaseConnector.getInstance().getConnection();
		}
		
		String query = "insert into t_tweet (content, tweet_time, sentiment, retweet_count, favorite_count, ext_tweet_id, username, campaign_id, query_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?) ";
		PreparedStatement ps = availableConnection.prepareStatement(query);

		ps = availableConnection.prepareStatement(query);

		ps.setString(1, dataObject.getContent());
		ps.setTimestamp(2, dataObject.getTweetTime());
		ps.setString(3, dataObject.getSentiment());
		ps.setInt(4, dataObject.getRetweetCount());
		ps.setInt(5, dataObject.getFavoriteCount());
		ps.setLong(6, dataObject.getExtTweetId());
		ps.setString(7, dataObject.getTweetOwner().getUsername());
		ps.setNull(8, java.sql.Types.INTEGER);//dataObject.getCampaign().getId());
		ps.setNull(9, java.sql.Types.INTEGER);//dataObject.getQuery().getId());
		ps.executeUpdate();
		ps.close();

		// do not close connection that isn't opened by yourself
		if (connection == null) {
			DatabaseConnector.getInstance().closeConnection(availableConnection);
		}
		
		return true;
	}
	
	public SentimentAnalysis getSentimentAnalysis(String queryId, String campaignId) throws SQLException {
		String query = "select sentiment, count(sentiment) cnt from t_tweet where ";
		Object[] bindVariables = new Object[2];
		int bindVariableCount = 0;
		if (queryId != null) {
			query += "query_id = ? and ";
			bindVariables[bindVariableCount++] = queryId;
		}
		if (campaignId != null) {
			query += "campaign_id = ? and ";
			bindVariables[bindVariableCount++] = campaignId;
		}
		
		/*if (bindVariableCount == 0) {
			return null;
		}*/
		
		query = query.substring(0, query.length() - 4);

		query = query + " group by sentiment";
		
		Connection availableConnection = null;
		
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

		SentimentAnalysis sentiment = new SentimentAnalysis();
		while (rs.next()) {
			if("pos".equals(rs.getString("sentiment"))){
				sentiment.setPositiveSentimentCount(rs.getInt("cnt"));
			}
			if("neg".equals(rs.getString("sentiment"))){
				sentiment.setNegativeSentimentCount(rs.getInt("cnt"));
			}
			if("neu".equals(rs.getString("sentiment"))){
				sentiment.setNeutralSentimentCount(rs.getInt("cnt"));
			}
		}
		
		DatabaseConnector.getInstance().closeConnection(availableConnection);
		
		return sentiment;
	}
	
	public IndexWriter getLuceneDocument(String queryId, String campaignId, Directory dir) throws SQLException, CorruptIndexException, LockObtainFailedException, IOException {
		String query = "select content, tweet_time, sentiment, retweet_count, favorite_count, ext_tweet_id, username, campaign_id, query_id from t_tweet where ";
		Object[] bindVariables = new Object[2];
		int bindVariableCount = 0;
		if (queryId != null) {
			query += "query_id = ? and ";
			bindVariables[bindVariableCount++] = queryId;
		}
		if (campaignId != null) {
			query += "campaign_id = ? and ";
			bindVariables[bindVariableCount++] = campaignId;
		}
		
		/*if (bindVariableCount == 0) {
			return null;
		}*/
		
		query = query.substring(0, query.length() - 4);

		Connection availableConnection = null;
		
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
		
		IndexWriter writer = new IndexWriter(dir, new IndexWriterConfig(Version.LUCENE_36, new WhitespaceAnalyzer(Version.LUCENE_36)));
		Document doc = new Document();
		StringBuilder builder = new StringBuilder();
		while (rs.next()) {
	        builder.append(rs.getString("content")).append(" ");
		}
		doc.add(new Field("content", builder.toString(), Field.Store.NO, Field.Index.ANALYZED, Field.TermVector.YES));
		
		writer.addDocument(doc);
		writer.close();
		
		DatabaseConnector.getInstance().closeConnection(availableConnection);
		
		return writer;
	}

	@Override
	public boolean remove(Connection connection, Tweet dataObject)
			throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Tweet[] get(Connection connection, Tweet dataObject)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tweet[] find(Connection connection, String query)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
