package com.example.entity;

import java.util.List;

import org.json.JSONObject;

import com.example.webservice.CampaignService;
import com.example.webservice.FindCallback;
import com.example.webservice.QueryService;
import com.example.webservice.SaveCallback;

public class Campaign extends SimpleEntity {
  private static final String ID = "campaignId";
  private static final String NAME = "name";
  private static final String DESCRIPTION = "description";
  
  private List<Query> queries;
  
  public Campaign(){
    super();
  }
  
  public Campaign(JSONObject pObject){
    super(pObject);
  }

  public long getId() {
    return getLong(ID);
  }
  
  private void setId(long pId){
    put(ID, pId);
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
  
  public void saveChanges(final SaveCallback<Campaign> callback){
    if(!isDirty()){
      callback.onSave(this);
      return;
    }
    
    if(getId()<1){
      // if there is no id then it's a new object
      new CampaignService().add(this, new SaveCallback<Campaign>(){
        @Override
        public void onError(TAError pError) {
          callback.onError(pError);
        }

        @Override
        public void onSave(Campaign pObject) {
          Campaign.this.setId(pObject.getId());
          Campaign.this.setDirty(false);
          callback.onSave(Campaign.this);
        }
        
      });
    }
    // TODO: otherwise update the campaign (currently not supported)
  }
  
  public void addQuery(Query query){
    // if queries are not loaded then don't add anything
    // it will be loaded on the next call to getQueries
    if(queries == null){
      return;
    }
    
    queries.add(query);
  }
  
  public void getQueries(final FindCallback<Query> callback){
    // if already loaded then return immediately
    if(queries != null){
      callback.onFind(queries);
      return;
    }
    
    // lazy load the queries for this campaign
    new QueryService().get(Long.toString(getId()), new FindCallback<Query>(){
      @Override
      public void onError(TAError pError) {
        callback.onError(pError);
      }

      @Override
      public void onFind(List<Query> pObjects) {
        queries = pObjects;
        callback.onFind(queries);
      }
    });
  }
}
