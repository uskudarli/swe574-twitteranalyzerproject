package edu.boun.cmpe.swe574.twanalyzer.query;


import java.util.List;

public class Query {
	private int id;
	private List<String> keywords;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

}
