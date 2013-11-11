package edu.boun.cmpe.swe574.twanalyzer.twitter;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

import java.util.List;



public class TwitterClient {

	
	public int fetchFirstLevelTweets(String q) {
		int count = 0;
		Twitter twitter = new TwitterFactory().getInstance();
        try {
            Query query = new Query(q);
            QueryResult result;
            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) {
                	//save tweets
                }
            } while ((query = result.nextQuery()) != null);
        } catch (TwitterException te) {
        	;
        }

        return count;
	}

}
