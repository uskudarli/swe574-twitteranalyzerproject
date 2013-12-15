package swe574.g2.twitteranalysis.tclient;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import swe574.g2.twitteranalysis.Tweet;
import swe574.g2.twitteranalysis.dao.TweetDAO;
import swe574.g2.twitteranalysis.database.DatabaseConnector;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterManager {
	SentimentClassifier sentClassifier;
	int LIMIT = 500; // the number of retrieved tweets
	ConfigurationBuilder cb;
	Twitter twitter;

	public TwitterManager() {
		twitter = new TwitterFactory().getInstance();
		sentClassifier = SentimentClassifier.getInstance();
	}

	public void performQuery(String inQuery) throws InterruptedException,
			IOException {
		TweetDAO dao = new TweetDAO();
		Query query = new Query(inQuery);
		query.setCount(100);
		try {
			int count = 0;
			QueryResult r;
			Connection connection = DatabaseConnector.getInstance().getConnection();
			connection.setAutoCommit(false);
			do {
				r = twitter.search(query);
				ArrayList ts = (ArrayList) r.getTweets();
				for (int i = 0; i < ts.size() && count < LIMIT; i++) {
					count++;
					Status t = (Status)ts.get(i);
					String text = t.getText();
					System.out.println("Text: " + text);
					String name = t.getUser().getScreenName();
					System.out.println("User: " + name);
					String sent = sentClassifier.classify(t.getText());
					System.out.println("Sentiment: " + sent);
					try {
						dao.save(connection, new Tweet( t ));
					} catch (SQLException e) {
						System.err.println(e.getMessage());
					}
				}
			} while ((query = r.nextQuery()) != null && count < LIMIT);
			connection.commit();
			DatabaseConnector.getInstance().closeConnection(connection);
		} catch (TwitterException te) {
			System.out.println("Couldn't connect: " + te);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) throws InterruptedException, IOException{
		TwitterManager twitterManager = new TwitterManager();
		twitterManager.performQuery("mandela");
	}
}
