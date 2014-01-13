package com.example.parser;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.entity.Query;

public class QueryListParser extends JSONArrayParser<Query> {

  private static final String QUERIES = "queries";
  
  public QueryListParser(String pJSONString) {
    super(pJSONString);
  }

  @Override
  public List<Query> parse() {
    List<Query> result = new LinkedList<Query>();
    
    JSONObject queryObject = parseObject();
    
    if(queryObject == null){
      return null;
    }
    
    JSONArray queryList = null;
    try {
      queryList = queryObject.getJSONArray(QUERIES);
    } catch(JSONException e){
      return null;
    }
    
    if(queryList == null){
      return null;
    }
    
    for(int i=0; i<queryList.length(); ++i){
      try{
        Query query = new Query(queryList.getJSONObject(i));
        if(query.getId() < 1){
          return null;
        }
        result.add(query);
      } catch(JSONException e){
        return null;
      }
    }
    
    return result;
  }

}
