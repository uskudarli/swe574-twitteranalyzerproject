package com.example.webservicecallback;

import com.example.entity.User;

public interface SignupCallback extends ErrorCallback {
  public void onSignup(User pUser);
}
