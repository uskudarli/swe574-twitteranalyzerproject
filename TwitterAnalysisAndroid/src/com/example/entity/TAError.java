package com.example.entity;

import org.json.JSONObject;

public class TAError extends SimpleEntity {  
  private static final String HAS_ERROR = "error";
  private static final String MESSAGE = "message";
  
  public TAError(){
    super();
  }
  
  public TAError(JSONObject pObject){
    super(pObject);
  }
  
  public TAError(String pMessage){
    super();
    
    if(pMessage == null || pMessage.isEmpty()){
      return;
    }
    
    put(HAS_ERROR, true);
    put(MESSAGE, pMessage);
  }
  
  public boolean hasError(){
    return getBoolean(HAS_ERROR);
  }
  
  public String getMessage(){
    return getString(MESSAGE);
  }
}
