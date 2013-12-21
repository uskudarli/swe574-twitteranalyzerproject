package com.example.twitteranalysisandroid;

public interface Callback<T> {
  public void onSuccess(T pObject);
  public void onError(Exception pEx);
}
