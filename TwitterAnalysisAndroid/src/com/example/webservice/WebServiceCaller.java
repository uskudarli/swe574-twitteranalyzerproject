package com.example.webservice;

import static com.example.helper.Constants.DEBUG;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

import com.example.parameter.SimpleParameter;

public abstract class WebServiceCaller {
  
  static final String TAG = "WebServiceCaller";
  
  // base url of the web service
  private static final String baseUrl = "http://swe.cmpe.boun.edu.tr:8180/twitteranalysis/v1.0/api/";
  
  // parameters for calling different services
  private String serviceURL;
  private SimpleParameter inputParams;
  
  public WebServiceCaller(){
  }
  
  /* Getters and Setters for subclasses */
  protected String getUrl(){
    return serviceURL;
  }
  
  protected SimpleParameter getInputParams(){
    return inputParams;
  }
  
  protected void setServiceURL(String pServiceURL){
    serviceURL = pServiceURL;
  }
  
  protected void setInputParams(SimpleParameter pInputParams){
    inputParams = pInputParams;
  }
  
  /***
   * Calls the given web service in a background thread
   * @param pCallback
   */
  protected void callAsync(WebServiceCallback pCallback){
    AsyncWebCall call = new AsyncWebCall();
    call.setCallback(pCallback);
    call.execute();
  }
  
  /***
   * Calls the given web service in the current thread
   * @return
   * @throws MalformedURLException
   * @throws IOException
   */
  protected String call() throws MalformedURLException, IOException {
    URL connectionURL;
    
    // write the parameters into the url
    connectionURL = new URL(baseUrl + serviceURL + "?" + inputParams.toUrlEncodedString());
    if(DEBUG){
      Log.d(TAG, baseUrl+serviceURL);
      Log.d(TAG, inputParams.toUrlEncodedString());
    }
    // get the connection
    HttpURLConnection connection =  (HttpURLConnection) connectionURL.openConnection();
    
    // set the request method
    connection.setRequestMethod("POST");
    // this is a json web service
    connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
    
//    // enable writing the stream
//    connection.setDoOutput(true);
//    // get a utf-8 writer
//    BufferedWriter outputWriter = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "utf-8"));
//    // write the parameters json string
//    outputWriter.write(inputParams.toUrlEncodedString());
//    // make sure everything is written
//    outputWriter.flush();
//    // release the resource
//    outputWriter.close();
    
    // read the output from the server
    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
 
    // read all the lines into a single string
    StringBuilder builder = new StringBuilder();
    String line;
    while((line = reader.readLine()) != null){
      builder.append(line.trim());
    }
    
    // if debugging log all service call details
    if(DEBUG){
      Log.d(TAG, builder.toString());
    }
    
    // return the response string from the server
    return builder.toString();
  }
  
  /***
   * A class to asynchronously call a web service with a callback
   * @author Yildiz
   *
   */
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
        // this exception should never happen
        if(DEBUG){
          e.printStackTrace();
        }
        error = e;
      } catch(IOException e){
        // this exception seems to happen randomly
        if(DEBUG){
          Log.d(TAG, e.getMessage());
        }
        return doInBackground(params);
      }
      
      return result;
    }
    
    @Override
    protected void onPostExecute(String result) {
      // if we have a callback set then notify appropriate callback method
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
