package com.example.parser;

import static com.example.helper.Constants.DEBUG;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.entity.Campaign;

public class CampaignListParser extends JSONArrayParser<Campaign> {
  
  private static final String CAMPAIGNS = "campaigns";
  
  public CampaignListParser(String pJSONString) {
    super(pJSONString);
  }

  @Override
  public List<Campaign> parse() {
    List<Campaign> result = new LinkedList<Campaign>();
    
    JSONObject campaignsObject = parseObject();
    
    if(campaignsObject == null){
      return null;
    }
    
    JSONArray campaignList = null;
    try {
      campaignList = campaignsObject.getJSONArray(CAMPAIGNS);
    } catch(JSONException e){
      return null;
    }
    
    if(campaignList == null){
      return null;
    }
    
    for(int i=0; i<campaignList.length(); ++i){
      try{
        Campaign camp = new Campaign(campaignList.getJSONObject(i));
        if(camp.getId() < 1){
          return null;
        }
        result.add(camp);
      } catch(JSONException e){
        return null;
      }
    }
    
    return result;
  }
}
