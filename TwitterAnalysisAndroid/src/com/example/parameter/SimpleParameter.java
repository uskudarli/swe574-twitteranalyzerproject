package com.example.parameter;

import static com.example.helper.Constants.DEBUG;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class SimpleParameter {
  private static final char PARAMETER_DELIMITER = '&';
  private static final char PARAMETER_EQUALS_CHAR = '=';
  
  private Map<String, String> parameters;
  
  public SimpleParameter(){
    parameters = new HashMap<String, String>();
  }
  
  protected void put(String pKey, String pValue){
    parameters.put(pKey, pValue);
  }

  public String createQueryString() {
    StringBuilder parametersAsQueryString = new StringBuilder();
    if (parameters != null) {
      boolean firstParameter = true;

      for (String parameterName : parameters.keySet()) {
        if (!firstParameter) {
          parametersAsQueryString.append(PARAMETER_DELIMITER);
        }

        parametersAsQueryString.append(urlEncode(parameterName));
        parametersAsQueryString.append(PARAMETER_EQUALS_CHAR);
        parametersAsQueryString.append(urlEncode(parameters.get(parameterName)));

        firstParameter = false;
      }
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
