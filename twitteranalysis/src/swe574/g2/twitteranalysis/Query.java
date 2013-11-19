package swe574.g2.twitteranalysis;

import java.util.List;

public class Query {
	private List<String> includingKeywords;
	private List<String> excludingKeywords;
	
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
	
	public Tweet[] getTweets(int sIndex, int count) {
		return null;
	}
	
	public Tweet getTweet(int id) {
		return null;
	}
}
