package com.example.entity;

public interface Callback<T> {
  public void onSuccess(T pObject);
  public void onError(Exception pEx);
}
