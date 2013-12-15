package swe574.g2.twitteranalysis.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import swe574.g2.twitteranalysis.Campaign;
import swe574.g2.twitteranalysis.Hashtag;
import swe574.g2.twitteranalysis.MentionedUser;
import swe574.g2.twitteranalysis.Query;
import swe574.g2.twitteranalysis.Tweet;
import swe574.g2.twitteranalysis.TwitterUser;

public class DatabaseController {

	public synchronized void saveTweet(Tweet tweet) throws SQLException{
		Connection connection = null;
		if (tweet != null) {
			connection = DatabaseConnector.getInstance().getConnection();
			try {
				PreparedStatement ps = connection.prepareStatement("insert into t_tweet (username, content, campaign_id) values (?,?,?)");
				//ps.setInt(1, tweet.getId());
				ps.setString(1, tweet.getTweetOwner().getUsername());
				ps.setString(2, tweet.getContent());
				ps.setInt(3, tweet.getCampaign().getId());
				
				ps.execute();
				ps.close();
				connection.commit();
				
				//get tweet id
				Statement s = connection.createStatement();
				ResultSet rs = s.executeQuery("select max(id) as max_id from t_tweet");
				rs.next();
				tweet.setId(rs.getInt("max_id"));
				rs.close();
				s.close();
				
				//now save mentions
				List<MentionedUser> mentions = tweet.getMentionedUsers();
				for (MentionedUser m : mentions) {
					this.saveMentionedUser(tweet, m);
				}
				
				//now save hastags
				List<Hashtag> hashtags = tweet.getHashtags();
				for (Hashtag h : hashtags) {
					this.saveHashtag(tweet, h);
				}
				connection.commit();
			}
			finally {
				connection.close();
			}
		}
	}
	
	public synchronized void saveMentionedUser(Tweet tweet, MentionedUser mention) throws SQLException {
		Connection connection = null;
		if (tweet != null && mention != null) {
			connection = DatabaseConnector.getInstance().getConnection();
			try {
				PreparedStatement ps = connection.prepareStatement("insert into t_mentions (username, tweet_id) values (?,?)");
				//ps.setInt(1, tweet.getId());
				ps.setString(1, mention.getUsername());
				ps.setLong(2, tweet.getId());
				
				ps.execute();
				ps.close();
			}
			finally {
				connection.close();
			}
		}
	}
	
	public synchronized void saveQuery(Query query) {
		
	}
	
	public synchronized void saveCampaign(Campaign campaign) throws SQLException  {
		Connection connection = null;
		if (campaign != null) {
			connection = DatabaseConnector.getInstance().getConnection();
			try {
				PreparedStatement ps = connection.prepareStatement("insert into t_campaign (name, description) values (?,?)");
				ps.setString(1, campaign.getName());
				ps.setString(2, campaign.getDescription());
				
				ps.execute();
				ps.close();
			}
			finally {
				connection.close();
			}
		}
	}
	
	public synchronized void saveHashtag(Tweet tweet, Hashtag hashtag) throws SQLException  {
		Connection connection = null;
		if (tweet != null && hashtag != null) {
			connection = DatabaseConnector.getInstance().getConnection();
			try {
				PreparedStatement ps = connection.prepareStatement("insert into t_hastag (value, tweet_id) values (?,?)");
				//ps.setInt(1, tweet.getId());
				ps.setString(1, hashtag.getValue());
				ps.setLong(2, tweet.getId());
				
				ps.execute();
				ps.close();
			}
			finally {
				connection.close();
			}
		}
	}
	
	public synchronized void saveTwitterUser(TwitterUser twitterUser) {
		
	}
	
}