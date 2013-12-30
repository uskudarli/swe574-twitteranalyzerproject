package swe574.g2.twitteranalysis;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import swe574.g2.twitteranalysis.tclient.SentimentClassifier;
import twitter4j.HashtagEntity;
import twitter4j.Status;
import twitter4j.UserMentionEntity;

public class Tweet {
	private int id;
	private TweetOwner tweetOwner;
	private List<MentionedUser> mentionedUsers = new ArrayList<MentionedUser>();
	private List<Hashtag> hashtags = new ArrayList<Hashtag>();
	private String content;
	private Campaign campaign;
	private Query query;
	
	private Date createdAt;
	private int favoriteCount;
	private TweetLocation tweetLocation;
	private long extTweetId;
	private TweetPlace tweetPlace;
	private int retweetCount;
	
	private String sentiment;
	private Timestamp tweetTime;
	
	private int campaignId;
	private int queryId;
	
	public Tweet() {
	}
	
	public Tweet(Status twitter4jTweet, int campaignId, int queryId) {
		this.createdAt = twitter4jTweet.getCreatedAt();
		this.favoriteCount = twitter4jTweet.getFavoriteCount();
		this.tweetLocation = new TweetLocation(twitter4jTweet.getGeoLocation());
		
		HashtagEntity[] hastagEntites = twitter4jTweet.getHashtagEntities();
		for (HashtagEntity h : hastagEntites) {
			Hashtag hashtag = new Hashtag(h);
			this.hashtags.add(hashtag);
		}
		
		this.extTweetId = twitter4jTweet.getId();
		if (twitter4jTweet.getPlace() != null) {
			this.tweetPlace = new TweetPlace(this.extTweetId, twitter4jTweet.getPlace());
		}
		this.retweetCount = twitter4jTweet.getRetweetCount();
		this.content = twitter4jTweet.getText();
		this.tweetOwner = new TweetOwner(twitter4jTweet.getUser());
		
		UserMentionEntity[] mentionEntites = twitter4jTweet.getUserMentionEntities();
		for (UserMentionEntity m : mentionEntites) {
			MentionedUser mentionedUser = new MentionedUser(m);
			this.mentionedUsers.add( mentionedUser );
		}
		
		// sentiment analysis of tweet content
		this.sentiment = SentimentClassifier.getInstance().classify(this.content);
		this.campaignId = campaignId;
		this.queryId = queryId;
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public TweetOwner getTweetOwner() {
		return tweetOwner;
	}
	public void setTweetOwner(TweetOwner tweetOwner) {
		this.tweetOwner = tweetOwner;
	}
	public List<MentionedUser> getMentionedUsers() {
		return mentionedUsers;
	}
	public void setMentionedUsers(List<MentionedUser> mentionedUsers) {
		this.mentionedUsers = mentionedUsers;
	}
	public List<Hashtag> getHashtags() {
		return hashtags;
	}
	public void setHashtags(List<Hashtag> hashtags) {
		this.hashtags = hashtags;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Campaign getCampaign() {
		return campaign;
	}
	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}
	public Query getQuery() {
		return query;
	}
	public void setQuery(Query query) {
		this.query = query;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public int getFavoriteCount() {
		return favoriteCount;
	}
	public void setFavoriteCount(int favoriteCount) {
		this.favoriteCount = favoriteCount;
	}
	public TweetLocation getTweetLocation() {
		return tweetLocation;
	}
	public void setTweetLocation(TweetLocation tweetLocation) {
		this.tweetLocation = tweetLocation;
	}
	public long getExtTweetId() {
		return extTweetId;
	}
	public void setExtTweetId(long extTweetId) {
		this.extTweetId = extTweetId;
	}
	public TweetPlace getTweetPlace() {
		return tweetPlace;
	}
	public void setTweetPlace(TweetPlace tweetPlace) {
		this.tweetPlace = tweetPlace;
	}
	public int getRetweetCount() {
		return retweetCount;
	}
	public void setRetweetCount(int retweetCount) {
		this.retweetCount = retweetCount;
	}
	public String getSentiment() {
		return sentiment;
	}
	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}
	public Timestamp getTweetTime() {
		return tweetTime;
	}
	public void setTweetTime(Timestamp tweetTime) {
		this.tweetTime = tweetTime;
	}

	public int getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(int campaignId) {
		this.campaignId = campaignId;
	}

	public int getQueryId() {
		return queryId;
	}

	public void setQueryId(int queryId) {
		this.queryId = queryId;
	}
	
	
}
