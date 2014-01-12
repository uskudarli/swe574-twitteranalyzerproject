package com.example.parameter;

public class SignupParameter extends AuthorizationParameter {
  private static final String PASSWORD_CONFIRM = "confirmPassword";
  
  public SignupParameter(){
    super();
  }
  
  @Override
  public void setPassword(String pPassword){
    super.setPassword(pPassword);
    setConfirmPassword(pPassword);
  }
  
  public void setConfirmPassword(String pPassword){
    put(PASSWORD_CONFIRM, pPassword);
  }
}
