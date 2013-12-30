package com.example.parser;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.entity.SimpleEntity;

public abstract class JSONArrayParser<T extends SimpleEntity> extends JSONParser<T> {

  public JSONArrayParser(String pJSONString){
    super(pJSONString);
  }
  
  protected JSONObject parseObject(){
    JSONObject result = null;
    try {
      result = new JSONObject(getJSONString());
    } catch(JSONException e){
      e.printStackTrace();
    }
    
    return result;
  }
  
  protected JSONArray parseArray(){
    JSONArray result = null;
    try {
      result = new JSONArray(getJSONString());
    } catch(JSONException e){
      e.printStackTrace();
    }
    
    return result;
  }
  
  public abstract List<T> parse();
}
