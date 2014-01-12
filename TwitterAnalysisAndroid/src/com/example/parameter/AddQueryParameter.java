package com.example.parameter;

import java.util.List;

public class AddQueryParameter extends AuthorizationParameter {

  private static final String CAMPAIGN_ID = "campaignId";
  private static final String INCLUDING = "including";
  private static final String EXCLUDING = "excluding";
  
  private List<String> including;
  private List<String> excluding;
  
  public void setCampaignId(String pCampaignId){
    put(CAMPAIGN_ID, pCampaignId);
  }
  
  public void setIncluding(List<String> pIncluding){
    including = pIncluding;
  }
  
  public void setExcluding(List<String> pExcluding){
    excluding = pExcluding;
  }
  
  @Override
  public String toUrlEncodedString() {
    String baseString = super.toUrlEncodedString();
    
    StringBuilder builder = new StringBuilder();
    builder.append(baseString);
    
    for(String include : including){
      builder.append(PARAMETER_DELIMITER);
      builder.append(INCLUDING);
      builder.append(PARAMETER_EQUALS_CHAR);
      builder.append(include);
    }
    
    for(String exclude : excluding){
      builder.append(PARAMETER_DELIMITER);
      builder.append(EXCLUDING);
      builder.append(PARAMETER_EQUALS_CHAR);
      builder.append(exclude);
    }
    
    return builder.toString();
  }
}
