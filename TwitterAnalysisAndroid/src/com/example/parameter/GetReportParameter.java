package com.example.parameter;

public class GetReportParameter extends AuthorizationParameter {
  private static final String CAMPAIGN_ID = "campaignId";
  private static final String QUERY_ID = "queryId";

  public GetReportParameter(){
    super();
  }
  
  public void setCampaignId(String pCampaignId){
    put(CAMPAIGN_ID, pCampaignId);
  }
  
  public void setQueryId(String queryId){
    put(QUERY_ID, queryId);
  }
}
