package com.example.entity;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.webservice.QueryService;
import com.example.webservice.SaveCallback;

public class Query extends SimpleEntity {

  private static final String ID = "queryId";
  private static final String INCLUDING = "including";
  private static final String EXCLUDING = "excluding";
  
  private Campaign campaign;
  
  public Query(){
  }
  
  public Query(JSONObject pObject){
    super(pObject);
  }

  public long getId() {
    return getLong(ID);
  }
  
  private void setId(long pId){
    put(ID, pId);
  }
  
  public List<String> getIncluding(){
    JSONArray includings = getArray(INCLUDING);
    if(includings == null){
      return null;
    }
    
    List<String> result = new LinkedList<String>();
    for(int i=0; i<includings.length(); ++i){
      try {
        result.add(includings.getString(i));
      } catch(JSONException e){
        return null;
      }
    }
    
    return result;
  }
  
  public void setIncluding(List<String> pList){
    JSONArray includings = new JSONArray();
    
    for(String include : pList){
      includings.put(include);
    }
    
    setArray(INCLUDING, includings);
  }
  
  public List<String> getExcluding(){
    JSONArray excludings = getArray(EXCLUDING);
    if(excludings == null){
      return null;
    }
    
    List<String> result = new LinkedList<String>();
    for(int i=0; i<excludings.length(); ++i){
      try {
        result.add(excludings.getString(i));
      } catch(JSONException e){
        return null;
      }
    }
    
    return result;
  }
  
  public void setExcluding(List<String> pList){
    JSONArray excludings = new JSONArray();
    
    for(String include : pList){
      excludings.put(include);
    }
    
    setArray(EXCLUDING, excludings);
    
  }
  
  public Campaign getCampaign(){
    return campaign;
  }
  
  public void setCampaign(Campaign campaign){
    this.campaign = campaign;
  }
  
  public void saveChanges(final SaveCallback<Query> callback){
    if(!isDirty()){
      callback.onSave(this);
      return;
    }
    
    if(getId()<1){
      // if there is no id then it's a new object
      new QueryService().add(getCampaign(), this, new SaveCallback<Query>(){
        @Override
        public void onError(TAError pError) {
          callback.onError(pError);
        }

        @Override
        public void onSave(Query pObject) {
          Query.this.setId(pObject.getId());
          Query.this.setDirty(false);
          callback.onSave(Query.this);
        }
        
      });
    }
    // TODO: otherwise update the campaign (currently not supported)
  }
}
