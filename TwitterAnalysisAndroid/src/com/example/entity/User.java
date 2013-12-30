package com.example.entity;

import org.json.JSONObject;

import com.example.webservice.UserService;
import com.example.webservicecallback.LoginCallback;
import com.example.webservicecallback.SaveCallback;
import com.example.webservicecallback.SignupCallback;

public class User extends SimpleEntity {
  private static final String ID = "userId";
  private static final String NAME = "name";
  
  private static User currentUser;
  
  public User(){
    super();
  }
  
  public User(JSONObject pObject){
    super(pObject);
  }

  public long getId() {
    return getLong(ID);
  }
  
  public String getName(){
    return getString(NAME);
  }
  
  public void setName(String pName){
    put(NAME, pName);
  }

  public static User getCurrentUser(){
    return currentUser;
  }
  
  public static void login(final String pUsername, final String pPassword, final LoginCallback pCallback){
    new UserService().login(pUsername, pPassword, new LoginCallback(){
      @Override
      public void onError(TAError pError) {
        pCallback.onError(pError);
      }

      @Override
      public void onLogin(User pUser) {
        currentUser = pUser;
        pCallback.onLogin(currentUser);
      }
    });
  }
  
  public static void signup(final String pUsername, final String pPassword, final SignupCallback pCallback){
    new UserService().signup(pUsername, pPassword, new SignupCallback(){
      @Override
      public void onError(TAError pError) {
        pCallback.onError(pError);
      }

      @Override
      public void onSignup(User pUser) {
        currentUser = pUser;
        pCallback.onSignup(currentUser);
      }
    });
  }
  
  public void changePassword(final String pNewPassword, final SaveCallback<User> pCallback){
    new UserService().update(this, pNewPassword, pCallback);
  }
  
  public void saveChanges(final SaveCallback<User> pCallback){
    if(!isDirty()){
      pCallback.onSave(this);
      return;
    }
    
    new UserService().update(this, null, pCallback);
  }
}
