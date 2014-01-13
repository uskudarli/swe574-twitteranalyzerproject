package com.example.webservice;

import com.example.entity.SimpleEntity;

public interface GetCallback<T extends SimpleEntity> extends ErrorCallback {
  public void onGet(T pObject);
}
