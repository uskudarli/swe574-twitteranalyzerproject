package edu.boun.cmpe.swe574.twanalyzer;

import java.util.List;

public class Tweet {
	private int id;
	private TwitterUser user;
	private List<Mention> mentions;
	private List<Hashtag> hashtags;
	private String content;
	private Campaign campaign;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public TwitterUser getUser() {
		return user;
	}
	public void setUser(TwitterUser user) {
		this.user = user;
	}
	public List<Mention> getMentions() {
		return mentions;
	}
	public void setMentions(List<Mention> mentions) {
		this.mentions = mentions;
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
	
	
}
