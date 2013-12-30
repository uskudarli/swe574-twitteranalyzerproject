package com.example.parser;

import org.json.JSONObject;

import com.example.entity.TAError;

public class ErrorParser extends JSONObjectParser<TAError> {

  public ErrorParser(String pJSONString) {
    super(pJSONString);
  }

  @Override
  public TAError parse() {
    JSONObject object = parseObject();
    if(object == null){
      return null;
    }
    return new TAError(object);
  }

}
