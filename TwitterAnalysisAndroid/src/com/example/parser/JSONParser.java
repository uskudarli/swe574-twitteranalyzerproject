package com.example.parser;

import com.example.entity.SimpleEntity;

public abstract class JSONParser<T extends SimpleEntity> {
  
  private String jsonString;
  
  public JSONParser(String pJSONString){
    jsonString = pJSONString;
  }
  
  protected String getJSONString(){
    return jsonString;
  }
}
