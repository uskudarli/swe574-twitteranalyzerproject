package com.example.webservice;

import com.example.entity.TAError;
import com.example.entity.User;
import com.example.helper.AuthenticationKeeper;
import com.example.parameter.LoginParameter;
import com.example.parameter.SignupParameter;
import com.example.parameter.UserUpdateParameter;
import com.example.parser.ErrorParser;
import com.example.parser.UserParser;

public class UserService extends WebServiceCaller {
  
  static final String TAG = "UserService";
  
  // user service has 3 different actions
  private static final String ACTION_LOGIN = "login/";
  private static final String ACTION_SIGNUP = "signup/";
  private static final String ACTION_UPDATE = "user/update/";
  
  public UserService(){
    super();
  }
  
  /***
   * Logs in in background thread then notifies callback. On successful login updates, and
   * on unsuccessful login invalidates the AuthenticationKeeper instance.
   * @param pUsername
   * @param pPassword
   * @param pCallback
   */
  public void login(final String pUsername, final String pPassword, final LoginCallback pCallback){
    // prepare the parameters
    LoginParameter param = new LoginParameter();
    param.setUsername(pUsername);
    param.setPassword(pPassword);
    
    // prepare the fields required by super
    setServiceURL(ACTION_LOGIN);
    setInputParams(param);
    
    // call in background
    callAsync(new WebServiceCallback() {
      @Override
      public void onSuccess(String response) {
        // check to see if the response is an error response
        TAError error = new ErrorParser(response).parse();
        if(error != null && error.hasError()){
          // it is an error so invalidate and callback error
          AuthenticationKeeper.getInstance().invalidate();
          pCallback.onError(error);
          return;
        }
        
        // check if a user object is returned
        User user = new UserParser(response).parse();
        if(user == null || user.getId() < 1){
          // no user is returned so invalidate and callback error
          AuthenticationKeeper.getInstance().invalidate();
          pCallback.onError(new TAError("Invalid Login"));
          return;
        }
        
        // successful login so update and callback success
        AuthenticationKeeper.getInstance().set(pUsername, pPassword);
        pCallback.onLogin(user);
      }
      
      @Override
      public void onError(Exception e) {        
        // service encountered error, just callback error but don't invalidate
        pCallback.onError(new TAError(e.getMessage()));
      }
    });
  }
  
  /***
   * Signs up in background thread then notifies callback. On successful signup updates, and
   * on unsuccessful signup invalidates the AuthenticationKeeper instance.
   * @param pUsername
   * @param pPassword
   * @param pCallback
   */
  public void signup(final String pUsername, final String pPassword, final SignupCallback pCallback){
    // prepare the parameters
    SignupParameter param = new SignupParameter();
    param.setUsername(pUsername);
    param.setPassword(pPassword);
    
    // prepare the fields required by super
    setServiceURL(ACTION_SIGNUP);
    setInputParams(param);
    
    // call in background
    callAsync(new WebServiceCallback() {
      @Override
      public void onSuccess(String response) {
        // check to see if the response is an error response
        TAError error = new ErrorParser(response).parse();
        if(error != null && error.hasError()){
          // it is an error so invalidate and callback error
          AuthenticationKeeper.getInstance().invalidate();
          pCallback.onError(error);
          return;
        }
        
        // check if a user object is returned
        User user = new UserParser(response).parse();
        if(user == null || user.getId() < 1){
          // no user is returned so invalidate and callback error
          AuthenticationKeeper.getInstance().invalidate();
          pCallback.onError(new TAError("Something went wrong"));
          return;
        }
        
        // successful signup so update and callback success
        AuthenticationKeeper.getInstance().set(pUsername, pPassword);
        pCallback.onSignup(user);
      }
      
      @Override
      public void onError(Exception e) {
        // service encountered error, just callback error but don't invalidate
        pCallback.onError(new TAError(e.getMessage()));
      }
    });
  }

  /***
   * Updates user profile in background thread then notifies callback.
   * @param pUser
   * up to date user object
   * @param pNewPassword
   * can be null if password is not changed
   * @param pCallback
   */
  public void update(User pUser, final String pNewPassword, final SaveCallback<User> pCallback){
    // prepare the parameters
    UserUpdateParameter param = new UserUpdateParameter();
    param.setUsername(AuthenticationKeeper.getInstance().getUsername());
    param.setPassword(AuthenticationKeeper.getInstance().getPassword());
    param.setName(pUser.getName());
    if(pNewPassword != null){
      param.setNewPassword(pNewPassword);
    }
    
    // prepare the fields required by super
    setServiceURL(ACTION_UPDATE);
    setInputParams(param);
    
    // call in background
    callAsync(new WebServiceCallback() {
      @Override
      public void onSuccess(String response) {
        // check to see if the response is an error response
        TAError error = new ErrorParser(response).parse();
        if(error != null && error.hasError()){
          // it is an error so callback error
          pCallback.onError(error);
          return;
        }
        
        // check if the updated user object is returned
        User user = new UserParser(response).parse();
        if(user == null || user.getId() < 1){
          // no user is returned so callback error
          pCallback.onError(new TAError("Something went wrong"));
          return;
        }
        
        // successfully updated so callback success
        if(pNewPassword != null){
          // if a new password was set then update saved credentials
          AuthenticationKeeper.getInstance().updatePassword(pNewPassword);
        }
        pCallback.onSave(user);
      }
      
      @Override
      public void onError(Exception e) {
        // service encountered error, callback error
        pCallback.onError(new TAError(e.getMessage()));
      }
    });
  }
}
