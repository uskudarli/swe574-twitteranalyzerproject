package com.example.parameter;

public class AuthorizationParameter extends SimpleParameter {
  private static final String USERNAME = "username";
  private static final String PASSWORD = "password";
  
  public AuthorizationParameter(){
    super();
  }
  
  public void setUsername(String pUsername){
    put(USERNAME, pUsername);
  }
  
  public void setPassword(String pPassword){
    put(PASSWORD, pPassword);
  }
}
