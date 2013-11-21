package swe574.g2.twitteranalysis;

import java.util.List;

public class Query {
	private int id;
	private List<String> includingKeywords;
	private List<String> excludingKeywords;
	private int campaignId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<String> getIncludingKeywords() {
		return includingKeywords;
	}
	public void setIncludingKeywords(List<String> includingKeywords) {
		this.includingKeywords = includingKeywords;
	}
	public List<String> getExcludingKeywords() {
		return excludingKeywords;
	}
	public void setExcludingKeywords(List<String> excludingKeywords) {
		this.excludingKeywords = excludingKeywords;
	}
	public int getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(int campaignId) {
		this.campaignId = campaignId;
	}
	
	public Tweet[] getTweets(int sIndex, int count) {
		return null;
	}
	
	public Tweet getTweet(int id) {
		return null;
	}
	
	@Override
	public String toString() {
		return id + "  - " + campaignId;
	}
}
