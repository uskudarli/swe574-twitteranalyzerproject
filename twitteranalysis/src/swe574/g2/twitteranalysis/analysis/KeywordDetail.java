package swe574.g2.twitteranalysis.analysis;

import swe574.g2.twitteranalysis.Tweet;

public class KeywordDetail {
	private int id;
	private String keyword;
	private int tweetCount;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getTweetCount() {
		return tweetCount;
	}
	public void setTweetCount(int tweetCount) {
		this.tweetCount = tweetCount;
	}
	
	public Tweet[] getTweets(int sIndex, int count) {
		return null;
	}
	
	public Tweet getTweet(int id) {
		return null;
	}
}
