package swe574.g2.twitteranalysis;

import java.util.List;

public class Tweet {
	private int id;
	private TweetOwner tweetOwner;
	private List<MentionedUser> mentionedUsers;
	private List<Hashtag> hashtags;
	private String content;
	private Campaign campaign;
	private Query query;
	
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
}
