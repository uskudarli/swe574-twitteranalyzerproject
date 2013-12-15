package com.example.twitteranalysisandroid;

import java.util.List;

public class Query {

  private String campaignName;
  private String queryTitle;
  private List<String> includeKeywords;
  private List<String> excludeKeywords;
  
  public Query(){
  }
  
  public String getCampaignName(){
    return campaignName;
  }
  
  public void setCampaignName(String pCampaignName){
    campaignName = pCampaignName;
  }
  
  public void setQueryTitle(String pQueryTitle){
    queryTitle = pQueryTitle;
  }
  
  public String getQueryTitle(){
    return queryTitle;
  }
  
  public void setIncludeKeywords(List<String> pIncludeKeywords){
    includeKeywords = pIncludeKeywords;
  }
  
  public List<String> getIncludeKeywords(){
    return includeKeywords;
  }
  
  public void setExcludeKeywords(List<String> pExcludeKeywords){
    excludeKeywords = pExcludeKeywords;
  }
  
  public List<String> getExcludeKeywords(){
    return excludeKeywords;
  }
  
  public void submit(final Callback<Query> pCallback){
    new Runnable(){
      @Override
      public void run() {
        // TODO submit the query to database
        // assume success
        if(pCallback != null){
          pCallback.onSuccess(Query.this);
        }
      }
    }.run();
  }
}
