package swe574.g2.twitteranalysis.tclient;

import swe574.g2.twitteranalysis.Query;

public class RunnableQuery extends Query implements Runnable {

	public RunnableQuery(Query query) {
		this.setId(query.getId());
		this.setCampaignId(query.getCampaignId());
		this.setIncludingKeywords(query.getIncludingKeywords());
		this.setExcludingKeywords(query.getExcludingKeywords());
	}
	
	@Override
	public void run() {
		System.out.println(this.toString() + " running...");
	}

}
