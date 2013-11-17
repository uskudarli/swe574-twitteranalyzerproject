package swe574.g2.tiwa.analysis;

import swe574.g2.tiwa.MentionedUser;
import swe574.g2.tiwa.Tweet;
import swe574.g2.tiwa.TwitterUser;

public class UserSentimentAnalysis extends SentimentAnalysis {
	private int id;
	private int totalTweetCount;
	private int totalRetweetCount;
	private Tweet mostRetweetedTweet;
	private int totalMentionCount;
	private MentionedUser mostMentionedUser;
	private TwitterUser twitterUser;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public TwitterUser getTwitterUser() {
		return twitterUser;
	}
	public void setTwitterUser(TwitterUser twitterUser) {
		this.twitterUser = twitterUser;
	}
	public int getTotalTweetCount() {
		return totalTweetCount;
	}
	public void setTotalTweetCount(int totalTweetCount) {
		this.totalTweetCount = totalTweetCount;
	}
	public int getTotalRetweetCount() {
		return totalRetweetCount;
	}
	public void setTotalRetweetCount(int totalRetweetCount) {
		this.totalRetweetCount = totalRetweetCount;
	}
	public Tweet getMostRetweetedTweet() {
		return mostRetweetedTweet;
	}
	public void setMostRetweetedTweet(Tweet mostRetweetedTweet) {
		this.mostRetweetedTweet = mostRetweetedTweet;
	}
	public int getTotalMentionCount() {
		return totalMentionCount;
	}
	public void setTotalMentionCount(int totalMentionCount) {
		this.totalMentionCount = totalMentionCount;
	}
	public MentionedUser getMostMentionedUser() {
		return mostMentionedUser;
	}
	public void setMostMentionedUser(MentionedUser mostMentionedUser) {
		this.mostMentionedUser = mostMentionedUser;
	}
	
	public Tweet[] getRetweets(int sIndex, int count) {
		return null;
	}
	
	public Tweet getTweet(int id) {
		return null;
	}
	
	public MentionedUser[] getMentionedUsers(int sIndex, int count) {
		return null;
	}
	
	public MentionedUser getMentionedUser(int id) {
		return null;
	}
	
}
