package com.example.entity;

import org.json.JSONObject;

import com.example.webservice.LoginCallback;
import com.example.webservice.SaveCallback;
import com.example.webservice.SignupCallback;
import com.example.webservice.UserService;

public class User extends SimpleEntity {
  private static final String ID = "userId";

  private static final String USERNAME = "username";
  private static final String PASSWORD = "password";
  private static final String NAME = "name";
  
  private static User currentUser;
  
  public User(){
    super();
  }
  
  public User(String username, String password){
    setUserName(username);
    setPassword(password);
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
  
  public String getUserName(){
    return getString(USERNAME);
  }
  
  public void setUserName(String pUserName){
    put(USERNAME, pUserName);
  }
  
  private String getPassword(){
    return getString(PASSWORD);
  }
  
  private void setPassword(String pPassword){
    put(PASSWORD, pPassword);
  }

  public static User getCurrentUser(){
    return currentUser;
  }
  
  public void login(final LoginCallback pCallback){
    new UserService().login(getUserName(), getPassword(), new LoginCallback(){
      @Override
      public void onError(TAError pError) {
        currentUser = null;
        pCallback.onError(pError);
      }

      @Override
      public void onLogin(User pUser) {
        currentUser = pUser;
        pCallback.onLogin(currentUser);
      }
    });
  }
  
  public void signup(final SignupCallback pCallback){
    new UserService().signup(getUserName(), getPassword(), new SignupCallback(){
      @Override
      public void onError(TAError pError) {
        currentUser = null;
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
    new UserService().update(this, pNewPassword, new SaveCallback<User>(){
      @Override
      public void onError(TAError pError) {
        pCallback.onError(pError);
      }

      @Override
      public void onSave(User pObject) {
        User.this.setPassword(pNewPassword);
        User.this.setDirty(false);
        pCallback.onSave(User.this);
      }
    });
  }
  
  public void saveChanges(final SaveCallback<User> pCallback){
    if(!isDirty()){
      pCallback.onSave(this);
      return;
    }
    
    new UserService().update(this, null, new SaveCallback<User>(){
      @Override
      public void onError(TAError pError) {
        pCallback.onError(pError);
      }

      @Override
      public void onSave(User pObject) {
        User.this.setDirty(false);
        pCallback.onSave(User.this);
      }
    });
  }
}
