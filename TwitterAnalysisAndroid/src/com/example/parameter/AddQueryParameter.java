package com.example.parameter;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

public class AddQueryParameter extends AuthorizationParameter {

  private static final String CAMPAIGN_ID = "campaignId";
  private static final String INCLUDING = "including";
  private static final String EXCLUDING = "excluding";
  
  public void setCampaignId(String pCampaignId){
    put(CAMPAIGN_ID, pCampaignId);
  }
  
  public void setIncluding(List<String> pIncluding){
    if(pIncluding == null || pIncluding.size()<1){
      return;
    }
    
    try {
      put(INCLUDING, new JSONArray(pIncluding));
    } catch (JSONException e) {
    }
  }
  
  public void setExcluding(List<String> pExcluding){
    if(pExcluding == null || pExcluding.size()<1){
      return;
    }
    try {
      put(EXCLUDING, new JSONArray(pExcluding));
    } catch (JSONException e) {
    }
  }
//  
//  private static String toJSArray(List<String> pList){
//    StringBuilder builder = new StringBuilder();
//    
//    builder.append("[");
//    boolean isFirst = true;
//    for(String word : pList){
//      if(isFirst){
//        builder.append(word);
//        isFirst = false;
//        continue;
//      }
//      
//      builder.append("," + word);
//    }
//    
//    builder.append("]");
//    
//    return builder.toString();
//  }
}
