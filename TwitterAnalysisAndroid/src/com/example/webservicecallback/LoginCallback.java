package com.example.webservicecallback;

import com.example.entity.User;

public interface LoginCallback extends ErrorCallback {
  public void onLogin(User pUser);
}
