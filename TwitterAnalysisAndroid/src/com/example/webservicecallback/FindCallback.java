package com.example.webservicecallback;

import java.util.List;

import com.example.entity.SimpleEntity;

public interface FindCallback<T extends SimpleEntity> extends ErrorCallback {
  public void onFind(List<T> pObjects);
}
