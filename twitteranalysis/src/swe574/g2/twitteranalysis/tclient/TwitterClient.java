package swe574.g2.twitteranalysis.tclient;

import swe574.g2.twitteranalysis.Query;

public class TwitterClient {
	public void runQuery(Query query) {
		System.out.println("Creating new thread for query...");
		Thread t = new Thread(new RunnableQuery(query));
		t.start();
		t = null;
	}
}
