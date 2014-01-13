package com.example.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SimpleEntity {
  private JSONObject object;
  private boolean isDirty = false;
  
  public SimpleEntity(){
    object = new JSONObject();
  }
  
  public SimpleEntity(JSONObject pObject){
    object = pObject;
  }
  
  protected String getString(String pKey){
    String result = null;
    
    try {
      result = object.getString(pKey);
    } catch(JSONException e){
    }
    
    return result;
  }
  
  protected int getInt(String pKey){
    int result = 0;
    
    try {
      result = object.getInt(pKey);
    } catch(JSONException e){
    }
    
    return result;
  }
  
  protected long getLong(String pKey){
    long result = 0;
    
    try {
      result = object.getLong(pKey);
    } catch(JSONException e){
    }
    
    return result;
  }
  
  protected double getDouble(String pKey){
    double result = 0;
    
    try {
      result = object.getDouble(pKey);
    } catch(JSONException e){
    }
    
    return result;
  }
  
  protected boolean getBoolean(String pKey){
    boolean result = false;
    
    try {
      result = object.getBoolean(pKey);
    } catch(JSONException e){
    }
    
    return result;
  }
  
  protected JSONObject getObject(String pKey){
    JSONObject result = null;
    
    try {
      result = object.getJSONObject(pKey);
    } catch(JSONException e){
    }
    
    return result;
  }
  
  protected JSONArray getArray(String pKey){
    JSONArray result = null;
    
    try {
      result = object.getJSONArray(pKey);
    } catch(JSONException e){
    }
    
    return result;
  }
  
  protected void put(String pKey, String pValue){
    String currentValue = getString(pKey);
    if((pValue != null && pValue.equals(currentValue)) ||
        (currentValue == null && pValue == null)){
     return; 
    }
    
    setDirty(true);
    
    try {
      object. put(pKey, pValue);
    } catch(JSONException e){
    }
  }
  
  protected void put(String pKey, int pValue){
    if(pValue == getInt(pKey)){
      return;
    }
    
    setDirty(true);
    
    try {
      object. put(pKey, pValue);
    } catch(JSONException e){
    }
  }
  
  protected void put(String pKey, long pValue){
    if(pValue == getLong(pKey)){
      return;
    }
    
    setDirty(true);
    
    try {
      object. put(pKey, pValue);
    } catch(JSONException e){
    }
  }
  
  protected void put(String pKey, double pValue){
    if(pValue == getDouble(pKey)){
      return;
    }
    
    setDirty(true);
    
    try {
      object. put(pKey, pValue);
    } catch(JSONException e){
    }
  }
  
  protected void put(String pKey, boolean pValue){
    if(pValue == getBoolean(pKey)){
      return;
    }
    
    setDirty(true);
    
    try {
      object. put(pKey, pValue);
    } catch(JSONException e){
    }
  }
  
  protected void setObject(String pKey, JSONObject pObject){
    try {
      object.put(pKey, pObject);
    } catch(JSONException e){
    }
  }
  
  protected void setArray(String pKey, JSONArray pArray){
    try {
      object.put(pKey, pArray);
    } catch(JSONException e){
    }
  }
  
  protected boolean isDirty(){
    return isDirty;
  }
  
  protected void setDirty(boolean pIsDirty){
    isDirty = pIsDirty;
  }
}
