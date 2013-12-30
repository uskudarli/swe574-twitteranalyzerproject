package com.example.parameter;

public class AddCampaignParameter extends AuthorizationParameter {
  private static final String NAME = "campaignName";
  private static final String DESCRIPTION = "campaignDescription";
  
  public AddCampaignParameter(){
    super();
  }
  
  public void setName(String pName){
    put(NAME, pName);
  }
  
  public void setDescription(String pDescription){
    put(DESCRIPTION, pDescription);
  }
}
