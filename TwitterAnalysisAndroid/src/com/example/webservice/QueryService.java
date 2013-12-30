package com.example.webservice;

import java.util.List;

import com.example.entity.Campaign;
import com.example.entity.Query;
import com.example.entity.TAError;
import com.example.helper.AuthenticationKeeper;
import com.example.parameter.AddQueryParameter;
import com.example.parameter.GetQueryParameter;
import com.example.parser.ErrorParser;
import com.example.parser.QueryListParser;
import com.example.webservicecallback.FindCallback;
import com.example.webservicecallback.SaveCallback;

public class QueryService extends WebServiceCaller {
  private static final String ACTION_GET = "query/";
  private static final String ACTION_ADD = "query/add";
  
  public QueryService(){
    super();
  }
  
  public void get(String pCampaignId, final FindCallback<Query> pCallback){
    GetQueryParameter param = new GetQueryParameter();
    param.setUsername(AuthenticationKeeper.getInstance().getUsername());
    param.setPassword(AuthenticationKeeper.getInstance().getPassword());
    param.setCampaignId(pCampaignId);
    
    setServiceURL(ACTION_GET);
    setInputParams(param.createQueryString());
    
    callAsync(new WebServiceCallback() {
      @Override
      public void onSuccess(String response) {
        TAError error = new ErrorParser(response).parse();
        if(error != null && error.hasError()){
          pCallback.onError(error);
          return;
        }
        
        List<Query> queries = new QueryListParser(response).parse();
        if(queries == null){
          pCallback.onError(new TAError("Something went wrong"));
          return;
        }
        
        pCallback.onFind(queries);
      }
      
      @Override
      public void onError(Exception e) {
        pCallback.onError(new TAError(e.getMessage()));
      }
    });
  }
  
  public void add(final Campaign pCampaign, final Query pQuery, final SaveCallback<Query> pCallback){
    AddQueryParameter param = new AddQueryParameter();
    param.setUsername(AuthenticationKeeper.getInstance().getUsername());
    param.setPassword(AuthenticationKeeper.getInstance().getPassword());
    param.setIncluding(pQuery.getIncluding());
    param.setExcluding(pQuery.getExcluding());
    
    setServiceURL(ACTION_ADD);
    setInputParams(param.createQueryString());
    
    callAsync(new WebServiceCallback() {
      @Override
      public void onSuccess(String response) {
        TAError error = new ErrorParser(response).parse();
        if(error != null && error.hasError()){
          pCallback.onError(error);
          return;
        }
        
        List<Query> queries = new QueryListParser(response).parse();
        if(queries == null){
          pCallback.onError(new TAError("Something went wrong"));
          return;
        }
        
        Query savedQuery = null;
        long maxId = 0;
        for(Query query : queries){
          if(query.getId()>maxId){
            maxId = query.getId();
            savedQuery = query;
          }
        }
        
        if(savedQuery == null){
          pCallback.onError(new TAError("Something went wrong"));
          return;
        }
        
        pCallback.onSave(savedQuery);
      }
      
      @Override
      public void onError(Exception e) {
        pCallback.onError(new TAError(e.getMessage()));
      }
    });
  }
}
