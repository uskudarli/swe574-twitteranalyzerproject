package com.example.parameter;

public class SignupParameter extends AuthorizationParameter {
  private static final String PASSWORD_CONFIRM = "confirmPassword";
  
  public SignupParameter(){
    super();
  }
  
  public void setConfirmPassword(String pPassword){
    put(PASSWORD_CONFIRM, pPassword);
  }
}
