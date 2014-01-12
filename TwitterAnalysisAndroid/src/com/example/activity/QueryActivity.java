package com.example.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entity.Campaign;
import com.example.entity.Query;
import com.example.entity.TAError;
import com.example.entity.User;
import com.example.helper.LoadingDialog;
import com.example.helper.WarningDialogBuilder;
import com.example.twitteranalysisandroid.R;
import com.example.webservice.CampaignService;
import com.example.webservice.FindCallback;
import com.example.webservice.QueryService;
import com.example.webservice.SaveCallback;

public class QueryActivity extends Activity implements OnClickListener {
  
  static final String TAG = "QueryActivity";
  
  private Spinner campaignNameSpinner, queryNumberSpinner;
  private TextView campaignNameTitle;
  private EditText campaignDescription, includeKeywordEdit, excludeKeywordEdit, campaignName;
  private Button submitButton, addIncludeKeywordButton, addExcludeKeywordButton;
  private LinearLayout includeKeywordsLayout, excludeKeywordsLayout;
  private OnClickListener removeFromIncludedListListener, removeFromExcludedListListener;
  
  private List<Campaign> campaigns;
  private List<Query> queries;
  private Campaign currentCampaign;
  private Query currentQuery;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_query);
    
    // if no logged in user found, then finish immediately
    if(User.getCurrentUser() == null){
      finish();
    }
    
    campaignNameSpinner = (Spinner) findViewById(R.id.spinner_campaign_name);
    queryNumberSpinner = (Spinner) findViewById(R.id.spinner_query_number);
    
    campaignDescription = (EditText) findViewById(R.id.et_campaign_description);
    includeKeywordEdit = (EditText) findViewById(R.id.et_include_keyword);
    excludeKeywordEdit = (EditText) findViewById(R.id.et_exclude_keyword);
    
    campaignNameTitle = (TextView) findViewById(R.id.campaign_name_title);
    campaignName = (EditText) findViewById(R.id.et_campaign_name);
    
    submitButton = (Button) findViewById(R.id.button_submit);
    
    addIncludeKeywordButton = (Button) findViewById(R.id.button_add_include);
    addExcludeKeywordButton = (Button) findViewById(R.id.button_add_exclude);
    includeKeywordsLayout = (LinearLayout) findViewById(R.id.list_include_keywords);
    excludeKeywordsLayout = (LinearLayout) findViewById(R.id.list_exclude_keywords);
    
    removeFromIncludedListListener = new OnClickListener(){
      @Override
      public void onClick(View pView) {
        removeFromIncludedKeywords((LinearLayout)pView.getParent());
      }
    };
    removeFromExcludedListListener = new OnClickListener(){
      @Override
      public void onClick(View pView) {
        removeFromExcludedKeywords((LinearLayout)pView.getParent());
      }
    };
    
    submitButton.setOnClickListener(this);
    addIncludeKeywordButton.setOnClickListener(this);
    addExcludeKeywordButton.setOnClickListener(this);
    

    campaignNameSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> arg0, View arg1, int pIndex, long arg3) {
        currentCampaign = getCampaignAtIndex(pIndex);
        if(currentQuery != null){
          clearQuery();
        }
        setupCampaign();
      }

      @Override
      public void onNothingSelected(AdapterView<?> arg0) {
      }
    });
    
    queryNumberSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> arg0, View arg1, int pIndex, long arg3) {
        currentQuery = getQueryAtIndex(pIndex);
        setupQuery();
      }

      @Override
      public void onNothingSelected(AdapterView<?> arg0) {
      }
    });
    
    setupCampaignNames();
  }

  @Override
  public void onClick(View pView) {
    switch(pView.getId()){
    case R.id.button_add_include:
      addToIncludedKeywords();
      break;
    case R.id.button_add_exclude:
      addToExcludedKeywords();
      break;
    case R.id.button_submit:
      if(currentCampaign == null){
        validateAndSubmitNewCampaign();
      } else if(currentQuery == null){
        validateAndSubmitNewQuery();
      }
      break;
    }
  }
  
  private Campaign getCampaignAtIndex(int pIndex){
    if(pIndex<1 || pIndex>campaigns.size()){
      return null;
    }
    
    return campaigns.get(pIndex-1);
  }
  
  private Query getQueryAtIndex(int pIndex){
    if(pIndex<1 || pIndex>queries.size()){
      return null;
    }
    
    return queries.get(pIndex-1);
  }
  
  private void clearQuery(){
    includeKeywordEdit.setText("");
    excludeKeywordEdit.setText("");
    includeKeywordsLayout.removeAllViews();
    excludeKeywordsLayout.removeAllViews();
  }
  
  private void clearCampaign(){
    campaignName.setText("");
    campaignDescription.setText("");
  }
  
  private void addCampaign(Campaign pCampaign){
    campaigns.add(pCampaign);
    currentCampaign = pCampaign;
    setupCampaign();
  }
  
  private void addQuery(Query pQuery){
    queries.add(pQuery);
    currentQuery = pQuery;
    setupQuery();
  }
  
  private void setupCampaignNames(){
    final ProgressDialog loadingDialog = new LoadingDialog(this);
    loadingDialog.show();
    
    new CampaignService().get(new FindCallback<Campaign>(){
      @Override
      public void onError(TAError pError) {
        new WarningDialogBuilder(QueryActivity.this)
        .setTitle(getString(R.string.txt_error))
        .setMessage(pError.getMessage())
        .show();
      
        loadingDialog.dismiss();
      }

      @Override
      public void onFind(List<Campaign> pObjects) {
        campaigns = pObjects;
        
        String[] campaignNames = new String[pObjects.size()+1];
        campaignNames[0] = getString(R.string.txt_new);
        int i=1;
        for(Campaign campaign : pObjects){
          campaignNames[i] = campaign.getName();
          ++i;
        }
        
        SpinnerAdapter campaignNameAdapter = new ArrayAdapter<String>(
            QueryActivity.this, 
            android.R.layout.simple_spinner_item,
            campaignNames);
        campaignNameSpinner.setAdapter(campaignNameAdapter);
        
        loadingDialog.dismiss();
      }
    });
  }
  
  private void disableQuery(){
    submitButton.setEnabled(false);
    addIncludeKeywordButton.setEnabled(false);
    addExcludeKeywordButton.setEnabled(false);
    includeKeywordEdit.setEnabled(false);
    excludeKeywordEdit.setEnabled(false);
  }
  
  private void enableQuery(){
    submitButton.setEnabled(true);
    addIncludeKeywordButton.setEnabled(true);
    addExcludeKeywordButton.setEnabled(true);   
    includeKeywordEdit.setEnabled(true);
    excludeKeywordEdit.setEnabled(true); 
  }
  
  private void setupQuery(){
    clearQuery();
    
    if(currentQuery == null){
      enableQuery();
      return;
    }
    
    disableQuery();
    
    for(String include : currentQuery.getIncluding()){
      addIncludeKeyword(include, false);
    }
    
    for(String exclude : currentQuery.getExcluding()){
      addExcludeKeyword(exclude, false);
    }
  }
  
  private void enableCampaign(){
    campaignDescription.setEnabled(true);
    campaignName.setVisibility(View.VISIBLE);
    campaignNameTitle.setVisibility(View.VISIBLE);
  }
  
  private void disableCampaign(){
    campaignDescription.setEnabled(false);
    campaignName.setVisibility(View.GONE);
    campaignNameTitle.setVisibility(View.GONE);
  }
  
  private void setupCampaign(){
    clearCampaign();
    
    if(currentCampaign == null){
      enableCampaign();
      return;
    }
    
    disableCampaign();
    
    campaignDescription.setText(currentCampaign.getDescription());
    
    final ProgressDialog loadingDialog = new LoadingDialog(this);
    loadingDialog.show();
    
    new QueryService().get(Long.toString(currentCampaign.getId()), new FindCallback<Query>(){
      @Override
      public void onError(TAError pError) {
        new WarningDialogBuilder(QueryActivity.this)
        .setTitle(getString(R.string.txt_error))
        .setMessage(pError.getMessage())
        .show();
      
        loadingDialog.dismiss();
      }

      @Override
      public void onFind(List<Query> pObjects) {
        queries = pObjects;
        
        String[] queryNumbers = new String[pObjects.size()+1];
        queryNumbers[0] = getString(R.string.txt_new);;
        int i=1;
        for(Query query : pObjects){
          queryNumbers[i] = Long.toString(query.getId());
          ++i;
        }
        
        SpinnerAdapter queryNumberAdapter = new ArrayAdapter<String>(
            QueryActivity.this, 
            android.R.layout.simple_spinner_item,
            queryNumbers);
        queryNumberSpinner.setAdapter(queryNumberAdapter);
        
        loadingDialog.dismiss();
      }
    });
  }
  
  private void addToIncludedKeywords(){
    String keyword = includeKeywordEdit.getText().toString().trim();
    includeKeywordEdit.setText("");
    
    if(keyword.length()<1){
      Toast.makeText(this, getString(R.string.err_empty_keyword), Toast.LENGTH_SHORT).show();
      return;
    }
    
    addIncludeKeyword(keyword, true);
  }
  
  private void addIncludeKeyword(String keyword, boolean removeable){
    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
    LinearLayout listItem = (LinearLayout) inflater.inflate(R.layout.list_item, null);
    TextView listItemText = (TextView) listItem.findViewById(R.id.txt_keyword);
    Button listItemDeleteButton = (Button) listItem.findViewById(R.id.button_delete);
    
    listItemText.setText(keyword);
    if(removeable){
      listItemDeleteButton.setOnClickListener(removeFromIncludedListListener);
    } else {
      listItemDeleteButton.setVisibility(View.GONE);
    }
    
    includeKeywordsLayout.addView(listItem);
  }
  
  private void addExcludeKeyword(String keyword, boolean removeable){
    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
    LinearLayout listItem = (LinearLayout) inflater.inflate(R.layout.list_item, null);
    TextView listItemText = (TextView) listItem.findViewById(R.id.txt_keyword);
    Button listItemDeleteButton = (Button) listItem.findViewById(R.id.button_delete);
    
    listItemText.setText(keyword);
    if(removeable){
      listItemDeleteButton.setOnClickListener(removeFromExcludedListListener);
    } else {
      listItemDeleteButton.setVisibility(View.GONE);
    }
    
    excludeKeywordsLayout.addView(listItem);
  }
  
  private void removeFromIncludedKeywords(LinearLayout pView){
    includeKeywordsLayout.removeView(pView);
  }
  
  private void addToExcludedKeywords(){
    String keyword = excludeKeywordEdit.getText().toString().trim();
    excludeKeywordEdit.setText("");
    
    if(keyword.length()<1){
      Toast.makeText(this, getString(R.string.err_empty_keyword), Toast.LENGTH_SHORT).show();
      return;
    }
    
    addExcludeKeyword(keyword, true);
  }

  private void removeFromExcludedKeywords(LinearLayout pView){
    excludeKeywordsLayout.removeView(pView);
  }
  
  private void validateAndSubmitNewQuery(){
    if(isQueryFormValid()){
      submitNewQuery();
    }
  }
  
  private void validateAndSubmitNewCampaign(){
    if(isCampaignFormValid()){
      submitNewCampaign();
    }
  }
  
  private boolean isCampaignFormValid(){
    // a campaign name has to be set
    if(campaignName.getText().toString() == null || campaignName.getText().toString().trim().length()<1){
      Toast.makeText(this, getString(R.string.err_campaign_name), Toast.LENGTH_SHORT).show();
      return false;
    }

    // a campaign title has to be set
    if(campaignDescription.getText().toString() == null || campaignDescription.getText().toString().trim().length()<1){
      Toast.makeText(this, getString(R.string.err_campaign_description), Toast.LENGTH_SHORT).show();
      return false;
    }
    
    return true;
  }
  
  private boolean isQueryFormValid(){
    // every form needs a campaign to be selected
    if(currentCampaign == null){
      Toast.makeText(this, getString(R.string.err_select_campaign), Toast.LENGTH_SHORT).show();
      return false;
    }
    
    // every form needs at least one include keyword to be set
    if(includeKeywordsLayout.getChildCount()<1){
      Toast.makeText(this, getString(R.string.err_include_keywords), Toast.LENGTH_SHORT).show();
      return false;
    }
    
    return true;
  }
  
  private List<String> getIncludeKeywords(){
    int numItems = includeKeywordsLayout.getChildCount();
    List<String> result = new ArrayList<String>(numItems);
    
    for(int i=0; i<numItems; ++i){
      TextView keyword = (TextView) includeKeywordsLayout.getChildAt(i).findViewById(R.id.txt_keyword);
      result.add(keyword.getText().toString().trim());
    }
    
    return result;
  }
  
  private List<String> getExcludeKeywords(){
    int numItems = excludeKeywordsLayout.getChildCount();
    List<String> result = new ArrayList<String>(numItems);
    
    for(int i=0; i<numItems; ++i){
      TextView keyword = (TextView) excludeKeywordsLayout.getChildAt(i).findViewById(R.id.txt_keyword);
      result.add(keyword.getText().toString().trim());
    }
    
    return result;
  }
  
  private void submitNewCampaign(){
    Campaign newCampaign = new Campaign();
    newCampaign.setName(campaignName.getText().toString().trim());
    newCampaign.setDescription(campaignDescription.getText().toString().trim());
    
    final ProgressDialog loadingDialog = new LoadingDialog(this);
    loadingDialog.show();
    
    new CampaignService().add(newCampaign, new SaveCallback<Campaign>(){
      @Override
      public void onError(TAError pError) {
        new WarningDialogBuilder(QueryActivity.this)
          .setTitle(getString(R.string.txt_error))
          .setMessage(getString(R.string.txt_query_submit_error))
          .show();
          
          loadingDialog.dismiss();
      }

      @Override
      public void onSave(Campaign pObject) {
        addCampaign(pObject);
        
        new WarningDialogBuilder(QueryActivity.this)
          .setTitle(getString(R.string.txt_campaign_submitted))
          .setMessage(getString(R.string.txt_campaign_submit_info))
          .show();
        
        loadingDialog.dismiss();
        
        validateAndSubmitNewQuery();
      }
    });
  }
  
  private void submitNewQuery(){      
    Query newQuery = new Query();
    newQuery.setIncluding(getIncludeKeywords());
    newQuery.setExcluding(getExcludeKeywords());
    
    final ProgressDialog loadingDialog = new LoadingDialog(this);
    loadingDialog.show();
    
    new QueryService().add(currentCampaign, newQuery, new SaveCallback<Query>(){
      @Override
      public void onError(TAError pError) {
        new WarningDialogBuilder(QueryActivity.this)
          .setTitle(getString(R.string.txt_error))
          .setMessage(getString(R.string.txt_query_submit_error))
          .show();
          
          loadingDialog.dismiss();
      }

      @Override
      public void onSave(Query pObject) {
        addQuery(pObject);
        
        new WarningDialogBuilder(QueryActivity.this)
          .setTitle(getString(R.string.txt_query_submitted))
          .setMessage(getString(R.string.txt_query_submit_info))
          .show();
        
        loadingDialog.dismiss();
        
        // remove the toasts
//        Toast.makeText(QueryActivity.this, "Campaign: " + pQuery.getCampaignName(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(QueryActivity.this, "Title: " + pQuery.getQueryTitle(), Toast.LENGTH_SHORT).show();
//        for(String item : pQuery.getIncludeKeywords()){
//          Toast.makeText(QueryActivity.this, "include: " + item, Toast.LENGTH_SHORT).show();
//        }
//        for(String item : pQuery.getExcludeKeywords()){
//          Toast.makeText(QueryActivity.this, "exclude: " + item, Toast.LENGTH_SHORT).show();
//        }
      }
    });
    
//    newQuery.submit(new Callback<Query>(){
//      @Override
//      public void onSuccess(Query pQuery) {
//        clearForm();
//        
//        new WarningDialogBuilder(QueryActivity.this)
//        .setTitle(getString(R.string.txt_query_submitted))
//        .setMessage(getString(R.string.txt_query_submit_info))
//        .show();
//        
//        loadingDialog.dismiss();
//        
//        // remove the toasts
//        Toast.makeText(QueryActivity.this, "Campaign: " + pQuery.getCampaignName(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(QueryActivity.this, "Title: " + pQuery.getQueryTitle(), Toast.LENGTH_SHORT).show();
//        for(String item : pQuery.getIncludeKeywords()){
//          Toast.makeText(QueryActivity.this, "include: " + item, Toast.LENGTH_SHORT).show();
//        }
//        for(String item : pQuery.getExcludeKeywords()){
//          Toast.makeText(QueryActivity.this, "exclude: " + item, Toast.LENGTH_SHORT).show();
//        }
//      }
//
//      @Override
//      public void onError(Exception pEx) {
//        new WarningDialogBuilder(QueryActivity.this)
//        .setTitle(getString(R.string.txt_error))
//        .setMessage(getString(R.string.txt_query_submit_error))
//        .show();
//        
//        loadingDialog.dismiss();
//      }
//    });  
  }
}
