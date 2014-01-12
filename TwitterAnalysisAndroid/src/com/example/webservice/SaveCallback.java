package com.example.webservice;

import com.example.entity.SimpleEntity;

public interface SaveCallback<T extends SimpleEntity> extends ErrorCallback {
  public void onSave(T pObject);
}
