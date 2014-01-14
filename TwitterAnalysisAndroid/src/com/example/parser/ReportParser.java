package com.example.parser;

import org.json.JSONObject;

import com.example.entity.Report;

public class ReportParser extends JSONObjectParser<Report> {
  
//  public static final String REPORT = "result";

  public ReportParser(String pJSONString) {
    super(pJSONString);
  }
  
  @Override
  public Report parse() {
    JSONObject object = parseObject();
    if(object == null){
      return null;
    }
//    
//    JSONObject reportObject = null;
//    try {
//      reportObject = object.getJSONObject(REPORT);
//    } catch(JSONException e){
//    }
//    
//    if(reportObject == null){
//      return null;
//    }
//    return new Report(reportObject);
    
    return new Report(object);
  }

}
