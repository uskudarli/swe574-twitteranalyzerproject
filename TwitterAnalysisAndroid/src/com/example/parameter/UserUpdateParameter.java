package com.example.parameter;

public class UserUpdateParameter extends AuthorizationParameter {
  private static final String NAME = "name";
  private static final String NEW_PASSWORD = "changedPassword";
  private static final String NEW_PASSWORD_CONFIRM = "confirmPassword";

  public UserUpdateParameter(){
    super();
  }
  
  public void setName(String pName){
    put(NAME, pName);
  }
  
  public void setNewPassword(String pNewPassword){
    put(NEW_PASSWORD, pNewPassword);
    put(NEW_PASSWORD_CONFIRM, pNewPassword);
  }
}
