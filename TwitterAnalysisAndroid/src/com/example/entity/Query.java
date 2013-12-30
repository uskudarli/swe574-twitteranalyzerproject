package com.example.entity;

import static com.example.helper.Constants.DEBUG;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Query extends SimpleEntity {

  private static final String ID = "queryId";
  private static final String INCLUDING = "including";
  private static final String EXCLUDING = "excluding";
  
  public Query(){
  }
  
  public Query(JSONObject pObject){
    super(pObject);
  }

  public long getId() {
    return getLong(ID);
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
        if(DEBUG){
          e.printStackTrace();
        }
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
        if(DEBUG){
          e.printStackTrace();
        }
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
}
