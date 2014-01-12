package com.example.parameter;

import static com.example.helper.Constants.DEBUG;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

public class SimpleParameter extends JSONObject {
  private static final char PARAMETER_DELIMITER = '&';
  private static final char PARAMETER_EQUALS_CHAR = '=';
  
  protected void put(String pKey, String pValue){
    try {
      super.put(pKey, pValue);
    } catch(JSONException e){
    }
  }

  public String toUrlEncodedString() {
    StringBuilder parametersAsQueryString = new StringBuilder();
    
    Iterator<?> key = keys();
    boolean firstParameter = true;
    while(key.hasNext()) {
      String keyString = (String) key.next();
      
      if (!firstParameter) {
        parametersAsQueryString.append(PARAMETER_DELIMITER);
      }

      parametersAsQueryString.append(urlEncode(keyString));
      parametersAsQueryString.append(PARAMETER_EQUALS_CHAR);
      parametersAsQueryString.append(urlEncode(opt(keyString).toString()));

      firstParameter = false;
    }
    return parametersAsQueryString.toString();
  }
  
  private static String urlEncode(String pValue){
    String result = "";
    
    try{
      result = URLEncoder.encode(pValue, "UTF-8");
    } catch(UnsupportedEncodingException e){
      if(DEBUG){
        e.printStackTrace();
      }
    }
    
    return result;
  }
}
