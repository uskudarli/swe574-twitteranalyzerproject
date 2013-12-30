package com.example.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class AuthenticationKeeper {
  private static final String PREFERENCES = "TA_LOGIN";
  private static final String USERNAME = "username";
  private static final String PASSWORD = "password";
  
  private static SharedPreferences preferences;
  private static AuthenticationKeeper instance;
  
  private String username;
  private String password;
  
  private AuthenticationKeeper(){
    if(preferences == null){
      return;
    }
    username = preferences.getString(USERNAME, null);
    password = preferences.getString(PASSWORD, null);
  }
  
  public static void initialize(Context pContext){
    if(preferences != null || pContext == null){
      return;
    }
    preferences = pContext.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
  }
  
  public static AuthenticationKeeper getInstance(){
    if(instance == null){
      instance = new AuthenticationKeeper();
    }
    
    return instance;
  }
  
  public String getUsername(){
    return username;
  }
  
  public String getPassword(){
    return password;
  }
  
  public void set(String pUsername, String pPassword){
    if(preferences == null){
      return;
    }
    
    username = pUsername;
    password = pPassword;
    
    SharedPreferences.Editor editor = preferences.edit();
    editor.putString(USERNAME, username);              
    editor.putString(PASSWORD, password);   
    editor.commit();
  }
  
  public void updatePassword(String pPassword){
    if(preferences == null){
      return;
    }
    
    SharedPreferences.Editor editor = preferences.edit();            
    editor.putString(PASSWORD, pPassword);   
    editor.commit();
  }
  
  public void invalidate(){
    set(null, null);
  }
}
