package com.example.webservice;

import java.util.List;

import com.example.entity.Campaign;
import com.example.entity.TAError;
import com.example.helper.AuthenticationKeeper;
import com.example.parameter.AddCampaignParameter;
import com.example.parameter.AuthorizationParameter;
import com.example.parser.CampaignListParser;
import com.example.parser.ErrorParser;

public class CampaignService extends WebServiceCaller {
  private static final String ACTION_GET = "campaign/";
  private static final String ACTION_ADD = "campaign/add/";
  
  List<Campaign> cachedCampaigns;

  public CampaignService(){
    super();
  }
  
  public void get(final FindCallback<Campaign> pCallback){
    // if we have previously retrieved the list then just return that
    if(cachedCampaigns != null){
      pCallback.onFind(cachedCampaigns);
      return;
    }
    
    // lazy load the campaigns
    AuthorizationParameter param = new AuthorizationParameter();
    param.setUsername(AuthenticationKeeper.getInstance().getUsername());
    param.setPassword(AuthenticationKeeper.getInstance().getPassword());
    
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
        
        List<Campaign> campaigns = new CampaignListParser(response).parse();
        if(campaigns == null){
          pCallback.onError(new TAError("Something went wrong"));
          return;
        }
        
        // cache the campaigns
        cachedCampaigns = campaigns;
        pCallback.onFind(cachedCampaigns);
      }
      
      @Override
      public void onError(Exception e) {
        pCallback.onError(new TAError(e.getMessage()));
      }
    });
  }
  
  public void add(final Campaign pCampaign, final SaveCallback<Campaign> pCallback){
    AddCampaignParameter param = new AddCampaignParameter();
    param.setUsername(AuthenticationKeeper.getInstance().getUsername());
    param.setPassword(AuthenticationKeeper.getInstance().getPassword());
    param.setName(pCampaign.getName());
    param.setDescription(pCampaign.getDescription());
    
    setServiceURL(ACTION_ADD);
    setInputParams(param);
    
    callAsync(new WebServiceCallback() {
      @Override
      public void onSuccess(String response) {
        TAError error = new ErrorParser(response).parse();
        if(error != null && error.hasError()){
          pCallback.onError(error);
          return;
        }
        
        List<Campaign> campaigns = new CampaignListParser(response).parse();
        if(campaigns == null){
          pCallback.onError(new TAError("Something went wrong"));
          return;
        }
        
        Campaign savedCampaign = null;
        for(Campaign campaign : campaigns){
          if(campaign.getName().equals(pCampaign.getName())){
            savedCampaign = campaign;
            break;
          }
        }
        
        if(savedCampaign == null){
          pCallback.onError(new TAError("Something went wrong"));
          return;
        }
        
        // if we have cache loaded then add this campaign to it
        if(cachedCampaigns != null){
          cachedCampaigns.add(savedCampaign);
        }
        
        pCallback.onSave(savedCampaign);
      }
      
      @Override
      public void onError(Exception e) {
        pCallback.onError(new TAError(e.getMessage()));
      }
    });
  }
}
