package com.example.parser;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.entity.SimpleEntity;

public abstract class JSONObjectParser<T extends SimpleEntity> extends JSONParser<T> {
  
  public JSONObjectParser(String pJSONString) {
    super(pJSONString);
  }
  
  protected JSONObject parseObject(){
    JSONObject result = null;
    try {
      result = new JSONObject(getJSONString());
    } catch(JSONException e){
    }
    
    return result;
  }

  public abstract T parse();
}
