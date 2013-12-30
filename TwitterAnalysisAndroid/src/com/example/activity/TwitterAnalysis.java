package com.example.activity;

import android.app.Application;

import com.example.helper.AuthenticationKeeper;

public class TwitterAnalysis extends Application {
  @Override
  public void onCreate()
  {
    super.onCreate();
    AuthenticationKeeper.initialize(getApplicationContext());
  }
}
