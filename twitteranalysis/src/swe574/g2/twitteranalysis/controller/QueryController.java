package swe574.g2.twitteranalysis.controller;

import java.util.List;

import com.vaadin.navigator.Navigator;

public class QueryController {
	
	private Navigator navigator = null;
	
	public void setNavigator(Navigator navigator) {
		this.navigator = navigator;
	}
	
	public void showQueryPage() {
		navigator.navigateTo("");
	}
	
	public void addQuery(List<String> includingKeywords, List<String> excludingKeywords) {
		
	}
	
	public void removeQuery(int queryId) {
		
	}
	
	public void addKeyword(int queryId, String keyword, String type) {
		
	}
	
	public void removeKeyword(int queryId, String keyword, String type) {
		
	}
	
	public void runQuery(int queryId) {
		
	}
}
