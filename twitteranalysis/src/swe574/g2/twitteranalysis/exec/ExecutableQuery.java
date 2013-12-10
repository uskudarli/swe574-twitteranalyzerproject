package swe574.g2.twitteranalysis.exec;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import swe574.g2.twitteranalysis.Query;
import swe574.g2.twitteranalysis.Tweet;
import swe574.g2.twitteranalysis.dao.TweetDAO;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class ExecutableQuery extends Query implements Executable {

	private Query[] processedQueries;
	
	public ExecutableQuery(Query query, Processor processor) {
		this.setId(query.getId());
		this.setCampaignId(query.getCampaignId());
		this.setIncludingKeywords(query.getIncludingKeywords());
		this.setExcludingKeywords(query.getExcludingKeywords());
		
		this.processedQueries = processor.process(query);
	}
	
	@Override
	public void execute(Connection connection) {
		System.out.println("Query[" + this.getId() + "] starts running...");
		
        try {
        	Twitter twitter = new TwitterFactory().getInstance();
        	for (Query pq : this.processedQueries) {
        		twitter4j.Query query = new twitter4j.Query( pq.getQueryString() );
	            QueryResult result;
	            TweetDAO dao = new TweetDAO();
	            do {
	                result = twitter.search(query);
	                List<Status> tweets = result.getTweets();
	                for (Status fetchedTweet : tweets) {
	                	try {
	                		System.out.println("Save: " + fetchedTweet.getText());
							dao.save(connection, new Tweet( fetchedTweet ));
						} 
	                	catch (SQLException e) {
							// ignore exceptions
	                		e.printStackTrace();
						}
	                }
	            } while ((query = result.nextQuery()) != null);
        	}
        } catch (TwitterException te) {
        	// TODO: log exceptions
        }

		
	}

}
