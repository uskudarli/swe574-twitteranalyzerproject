package com.example.webservice;

import com.example.entity.User;

public interface SignupCallback extends ErrorCallback {
  public void onSignup(User pUser);
}
