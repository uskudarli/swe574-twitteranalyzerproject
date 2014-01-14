package com.example.webservice;

import com.example.entity.Report;
import com.example.entity.TAError;
import com.example.helper.AuthenticationKeeper;
import com.example.parameter.GetReportParameter;
import com.example.parser.ErrorParser;
import com.example.parser.ReportParser;

public class ReportService extends WebServiceCaller {

  private static final String ACTION_GET = "report/";
  
  public ReportService(){
    super();
  }
  
  public void get(String pCampaignId, String queryId, final GetCallback<Report> pCallback){
    GetReportParameter param = new GetReportParameter();
    param.setUsername(AuthenticationKeeper.getInstance().getUsername());
    param.setPassword(AuthenticationKeeper.getInstance().getPassword());
    param.setCampaignId(pCampaignId);
    param.setQueryId(queryId);
    
    setServiceURL(ACTION_GET);
    setInputParams(param);
    
    callAsync(new WebServiceCallback() {
      @Override
      public void onSuccess(String response) {
        TAError error = new ErrorParser(response).parse();
        if(error != null && error.hasError()){
          pCallback.onError(error);
          return;
        }
        
        Report report = new ReportParser(response).parse();
        if(report == null){
          pCallback.onError(new TAError("Something went wrong"));
          return;
        }
        
        pCallback.onGet(report);
      }
      
      @Override
      public void onError(Exception e) {
        pCallback.onError(new TAError(e.getMessage()));
      }
    });
  }
}
