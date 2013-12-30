package com.example.webservice;

import static com.example.helper.Constants.DEBUG;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

public abstract class WebServiceCaller {
  
  private static final String TAG = "WebServiceCaller";
  private static final String baseUrl = "http://swe.cmpe.boun.edu.tr:8180/twitteranalysis/v1.0/api/";
  
  private String serviceURL;
  private String inputParams;
  
  public WebServiceCaller(){
  }
  
  protected String getUrl(){
    return serviceURL;
  }
  
  protected String getInputParams(){
    return inputParams;
  }
  
  protected void setServiceURL(String pServiceURL){
    serviceURL = pServiceURL;
  }
  
  protected void setInputParams(String pInputParams){
    inputParams = pInputParams;
  }
  
  protected void callAsync(WebServiceCallback pCallback){
    AsyncWebCall call = new AsyncWebCall();
    call.setCallback(pCallback);
    call.execute();
  }
  
  protected String call() throws MalformedURLException, IOException {
    URL connectionURL = new URL(baseUrl+serviceURL);
    HttpURLConnection connection =  (HttpURLConnection) connectionURL.openConnection();
    
    connection.setDoOutput(true);
    connection.setRequestMethod("POST");
    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    connection.setFixedLengthStreamingMode(inputParams.getBytes().length);
    
    PrintWriter out = new PrintWriter(connection.getOutputStream());
    out.print(inputParams);
    out.close();
    
    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
 
    StringBuilder builder = new StringBuilder();
    String line;
    while((line = reader.readLine()) != null){
      builder.append(line.trim());
    }
    
    if(DEBUG){
      Log.d(TAG, baseUrl+serviceURL);
      Log.d(TAG, inputParams);
      Log.d(TAG, builder.toString());
    }
    
    return builder.toString();
  }
  
  private class AsyncWebCall extends AsyncTask<Void, Void, String>{
    private Exception error;
    private WebServiceCallback callback;
    
    public void setCallback(WebServiceCallback pCallback){
      callback = pCallback;
    }
    
    @Override
    protected String doInBackground(Void... params) {
      String result = null;
      try{
        result = call();
      } catch(MalformedURLException e){
        if(DEBUG){
          e.printStackTrace();
        }
        error = e;
      } catch(IOException e){
        if(DEBUG){
          e.printStackTrace();
        }
        error = e;
      }
      
      return result;
    }
    
    @Override
    protected void onPostExecute(String result) {
      if(callback == null){
        return;
      }
      if(error != null){
        callback.onError(error);
        return;
      }
      callback.onSuccess(result);
    }
  }
}
