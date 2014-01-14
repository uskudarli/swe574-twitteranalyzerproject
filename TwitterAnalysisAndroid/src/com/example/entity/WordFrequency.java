package com.example.entity;

import org.json.JSONObject;

public class WordFrequency extends SimpleEntity {
  
  private static final String WORD = "w";
  private static final String FREQUENCY = "f";
  
  public WordFrequency(JSONObject object){
    super(object);
  }
  
  public String getWord(){
    return getString(WORD);
  }
  
  public int getFrequency(){
    return getInt(FREQUENCY);
  }
}
