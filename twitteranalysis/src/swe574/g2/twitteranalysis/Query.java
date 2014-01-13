package swe574.g2.twitteranalysis;

import java.util.ArrayList;
import java.util.List;

public class Query{
	private int id;
	private String active;
	private List<String> includingKeywords;
	private List<String> excludingKeywords;
	private int campaignId;
	private String queryTitle;
	
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
	
	
	public String getQueryTitle() {
		return queryTitle;
	}
	public void setQueryTitle(String queryTitle) {
		this.queryTitle = queryTitle;
	}

	
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public Tweet[] getTweets(int sIndex, int count) {
		return null;
	}
	
	public Tweet getTweet(int id) {
		return null;
	}
	
	public void addKeyword(String name, String type) {
		if ("including".equals(type)) {
			if (this.includingKeywords !=null) {
				this.includingKeywords.add(name);
			}
			else {
				this.includingKeywords = new ArrayList<String>();
				this.includingKeywords.add(name);
			}
		}
		else if ("excluding".equals(type)) {
			if (this.excludingKeywords !=null) {
				this.excludingKeywords.add(name);
			}
			else {
				this.excludingKeywords = new ArrayList<String>();
				this.excludingKeywords.add(name);
			}
		}
	}
	
	public void removeKeyword(String name, String type) {
		
		if (name != null && "including".equals(type)) {
			if (this.includingKeywords !=null) {
				this.includingKeywords.remove(name);
			}
		}
		else if (name!=null && "excluding".equals(type)) {
			if (this.excludingKeywords !=null) {
				this.excludingKeywords.remove(name);
			}
		}
	}
	
	public String getQueryString() {
		String queryString = "";
		List<String> iKeywords = this.getIncludingKeywords();
		if (iKeywords != null) {
			for (String keyword : iKeywords) {
				queryString += keyword + " ";
			}
		}
		
		return queryString;
	}
	
	@Override
	public String toString() {
		return id + " :: " + super.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof Query && this.id == ((Query)obj).getId();
	}
	
	
}
