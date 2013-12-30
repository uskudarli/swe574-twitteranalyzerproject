package com.example.webservicecallback;

import com.example.entity.TAError;

public interface ErrorCallback {
  public void onError(TAError pError);
}
