package com.example.entity;

import org.json.JSONObject;

public class Campaign extends SimpleEntity {
  private static final String ID = "campaignId";
  private static final String NAME = "name";
  private static final String DESCRIPTION = "description";
  
  public Campaign(){
    super();
  }
  
  public Campaign(JSONObject pObject){
    super(pObject);
  }

  public long getId() {
    return getLong(ID);
  }
  
  public String getName(){
    return getString(NAME);
  }
  
  public void setName(String pName){
    put(NAME, pName);
  }
  
  public String getDescription(){
    return getString(DESCRIPTION);
  }
  
  public void setDescription(String pDescription){
    put(DESCRIPTION, pDescription);
  }
}
