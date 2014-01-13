package com.example.parser;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.entity.User;
import static com.example.helper.Constants.DEBUG;

public class UserParser extends JSONObjectParser<User>{
  public static final String USER = "user";

  public UserParser(String pJSONString) {
    super(pJSONString);
  }

  @Override
  public User parse() {
    JSONObject object = parseObject();
    if(object == null){
      return null;
    }
    
    JSONObject userObject = null;
    try {
      userObject = object.getJSONObject(USER);
    } catch(JSONException e){
    }
    
    if(userObject == null){
      return null;
    }
    return new User(userObject);
  }
}
