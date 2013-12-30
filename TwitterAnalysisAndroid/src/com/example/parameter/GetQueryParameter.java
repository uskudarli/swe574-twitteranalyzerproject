package com.example.parameter;

public class GetQueryParameter extends AuthorizationParameter {
  private static final String CAMPAIGN_ID = "campaignId";
  
  public GetQueryParameter(){
    super();
  }
  
  public void setCampaignId(String pCampaignId){
    put(CAMPAIGN_ID, pCampaignId);
  }
}
