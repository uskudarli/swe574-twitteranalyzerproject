package com.example.webservice;

import com.example.entity.TAError;
import com.example.entity.User;
import com.example.helper.AuthenticationKeeper;
import com.example.parameter.LoginParameter;
import com.example.parameter.SignupParameter;
import com.example.parameter.UserUpdateParameter;
import com.example.parser.ErrorParser;
import com.example.parser.UserParser;
import com.example.webservicecallback.LoginCallback;
import com.example.webservicecallback.SaveCallback;
import com.example.webservicecallback.SignupCallback;

public class UserService extends WebServiceCaller {
  private static final String ACTION_LOGIN = "login/";
  private static final String ACTION_SIGNUP = "signup/";
  private static final String ACTION_UPDATE = "user/update/";
  
  public UserService(){
    super();
  }
  
  public void login(final String pUsername, final String pPassword, final LoginCallback pCallback){
    LoginParameter param = new LoginParameter();
    param.setUsername(pUsername);
    param.setPassword(pPassword);
    
    setServiceURL(ACTION_LOGIN);
    setInputParams(param.createQueryString());
    
    callAsync(new WebServiceCallback() {
      @Override
      public void onSuccess(String response) {
        TAError error = new ErrorParser(response).parse();
        if(error != null && error.hasError()){
          AuthenticationKeeper.getInstance().invalidate();
          pCallback.onError(error);
          return;
        }
        
        User user = new UserParser(response).parse();
        if(user == null || user.getId() < 1){
          AuthenticationKeeper.getInstance().invalidate();
          pCallback.onError(new TAError("Invalid Login"));
          return;
        }
        
        AuthenticationKeeper.getInstance().set(pUsername, pPassword);
        pCallback.onLogin(user);
      }
      
      @Override
      public void onError(Exception e) {
        AuthenticationKeeper.getInstance().invalidate();
        pCallback.onError(new TAError(e.getMessage()));
      }
    });
  }
  
  public void signup(final String pUsername, final String pPassword, final SignupCallback pCallback){
    SignupParameter param = new SignupParameter();
    param.setUsername(pUsername);
    param.setPassword(pPassword);
    
    setServiceURL(ACTION_SIGNUP);
    setInputParams(param.createQueryString());
    
    callAsync(new WebServiceCallback() {
      @Override
      public void onSuccess(String response) {
        TAError error = new ErrorParser(response).parse();
        if(error != null && error.hasError()){
          AuthenticationKeeper.getInstance().invalidate();
          pCallback.onError(error);
          return;
        }
        
        User user = new UserParser(response).parse();
        if(user == null || user.getId() < 1){
          AuthenticationKeeper.getInstance().invalidate();
          pCallback.onError(new TAError("Something went wrong"));
          return;
        }
        
        AuthenticationKeeper.getInstance().set(pUsername, pPassword);
        pCallback.onSignup(user);
      }
      
      @Override
      public void onError(Exception e) {
        AuthenticationKeeper.getInstance().invalidate();
        pCallback.onError(new TAError(e.getMessage()));
      }
    });
  }

  public void update(User pUser, final String pNewPassword, final SaveCallback<User> pCallback){
    UserUpdateParameter param = new UserUpdateParameter();
    param.setUsername(AuthenticationKeeper.getInstance().getUsername());
    param.setPassword(AuthenticationKeeper.getInstance().getPassword());
    param.setName(pUser.getName());
    if(pNewPassword != null){
      param.setNewPassword(pNewPassword);
    }
    
    setServiceURL(ACTION_UPDATE);
    setInputParams(param.createQueryString());
    
    callAsync(new WebServiceCallback() {
      @Override
      public void onSuccess(String response) {
        TAError error = new ErrorParser(response).parse();
        if(error != null && error.hasError()){
          pCallback.onError(error);
          return;
        }
        
        User user = new UserParser(response).parse();
        if(user == null || user.getId() < 1){
          pCallback.onError(new TAError("Something went wrong"));
          return;
        }
        
        if(pNewPassword != null){
          AuthenticationKeeper.getInstance().updatePassword(pNewPassword);
        }
        pCallback.onSave(user);
      }
      
      @Override
      public void onError(Exception e) {
        pCallback.onError(new TAError(e.getMessage()));
      }
    });
  }
}
