package com.example.webservice;

public interface WebServiceCallback {
  public void onSuccess(String response);
  public void onError(Exception e);
}
