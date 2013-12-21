package com.example.twitteranalysisandroid;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class QueryActivity extends Activity implements OnClickListener {  
  private Spinner campaignNameSpinner;
  private EditText queryTitleEdit, includeKeywordEdit, excludeKeywordEdit;
  private Button submitButton, addIncludeKeywordButton, addExcludeKeywordButton;
  private LinearLayout includeKeywordsLayout, excludeKeywordsLayout;
  private OnClickListener removeFromIncludedListListener, removeFromExcludedListListener;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_query);
    
    // if no logged in user found, then finish immediately
    if(User.getCurrentUser() == null){
      finish();
    }
    
    campaignNameSpinner = (Spinner) findViewById(R.id.spinner_campaign_name);
    queryTitleEdit = (EditText) findViewById(R.id.et_query_title);
    includeKeywordEdit = (EditText) findViewById(R.id.et_include_keyword);
    excludeKeywordEdit = (EditText) findViewById(R.id.et_exclude_keyword);
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
    
    setupCampaignNames();
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu items for use in the action bar
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu, menu);
    return super.onCreateOptionsMenu(menu);
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle presses on the action bar items
    switch (item.getItemId()) {
    case R.id.menu_query:
      // this is the current activity so return
      return true;
    case R.id.menu_reports:
      // start reports activity
      Intent reportsIntent = new Intent(this, ReportsActivity.class);
      startActivity(reportsIntent);
      finish();
      return true;
    case R.id.menu_settings:
      // start settings activity
      Intent settingsIntent = new Intent(this, SettingsActivity.class);
      startActivity(settingsIntent);
      finish();
      return true;
    default:
      return super.onOptionsItemSelected(item);
    }
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
      validateAndSubmit();
      break;
    }
  }
  
  private void clearForm(){
    campaignNameSpinner.setSelection(0);
    queryTitleEdit.setText("");
    includeKeywordEdit.setText("");
    excludeKeywordEdit.setText("");
    includeKeywordsLayout.removeAllViews();
    excludeKeywordsLayout.removeAllViews();
  }
  
  private void setupCampaignNames(){
    ProgressDialog loadingDialog = new LoadingDialog(this);
    loadingDialog.show();
    
    // TODO: fill the spinner adapter with real values as an async process
    String[] campaignNames = {"", "Campaign 1", "Campaign 2"}; // remove this line
    SpinnerAdapter campaignNameAdapter = new ArrayAdapter<String>(this, 
        android.R.layout.simple_spinner_item,
        campaignNames);
    campaignNameSpinner.setAdapter(campaignNameAdapter);
    
    loadingDialog.hide();
  }
  
  private void addToIncludedKeywords(){
    String keyword = includeKeywordEdit.getText().toString().trim();
    includeKeywordEdit.setText("");
    
    if(keyword.length()<1){
      Toast.makeText(this, getString(R.string.err_empty_keyword), Toast.LENGTH_SHORT).show();
      return;
    }
    
    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
    LinearLayout listItem = (LinearLayout) inflater.inflate(R.layout.list_item, null);
    TextView listItemText = (TextView) listItem.findViewById(R.id.txt_keyword);
    Button listItemDeleteButton = (Button) listItem.findViewById(R.id.button_delete);
    
    listItemText.setText(keyword);
    listItemDeleteButton.setOnClickListener(removeFromIncludedListListener);
    
    includeKeywordsLayout.addView(listItem);
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
    
    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
    LinearLayout listItem = (LinearLayout) inflater.inflate(R.layout.list_item, null);
    TextView listItemText = (TextView) listItem.findViewById(R.id.txt_keyword);
    Button listItemDeleteButton = (Button) listItem.findViewById(R.id.button_delete);
    
    listItemText.setText(keyword);
    listItemDeleteButton.setOnClickListener(removeFromExcludedListListener);
    
    excludeKeywordsLayout.addView(listItem);
  }

  private void removeFromExcludedKeywords(LinearLayout pView){
    excludeKeywordsLayout.removeView(pView);
  }
  
  private void validateAndSubmit(){
    if(isFormValid()){
      submit();
    }
  }
  
  private boolean isFormValid(){
    // every form needs a campaign to be selected
    if(campaignNameSpinner.getSelectedItemPosition()<1){
      Toast.makeText(this, getString(R.string.err_select_campaign), Toast.LENGTH_SHORT).show();
      return false;
    }
    
    // every form needs a query title to be set
    if(queryTitleEdit.getText().toString().trim().length()<1){
      Toast.makeText(this, getString(R.string.err_query_title), Toast.LENGTH_SHORT).show();
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
      EditText keywordEdit = (EditText) includeKeywordsLayout.getChildAt(i).findViewById(R.id.txt_keyword);
      result.set(i, keywordEdit.getText().toString().trim());
    }
    
    return result;
  }
  
  private List<String> getExcludeKeywords(){
    int numItems = excludeKeywordsLayout.getChildCount();
    List<String> result = new ArrayList<String>(numItems);
    
    for(int i=0; i<numItems; ++i){
      EditText keywordEdit = (EditText) excludeKeywordsLayout.getChildAt(i).findViewById(R.id.txt_keyword);
      result.set(i, keywordEdit.getText().toString().trim());
    }
    
    return result;
  }
  
  private void submit(){      
    Query newQuery = new Query();
    newQuery.setCampaignName(campaignNameSpinner.getSelectedItem().toString());
    newQuery.setQueryTitle(queryTitleEdit.getText().toString().trim());
    newQuery.setIncludeKeywords(getIncludeKeywords());
    newQuery.setExcludeKeywords(getExcludeKeywords());
    
    final ProgressDialog loadingDialog = new LoadingDialog(this);
    loadingDialog.show();
    
    newQuery.submit(new Callback<Query>(){
      @Override
      public void onSuccess(Query pQuery) {
        clearForm();
        
        new WarningDialogBuilder(QueryActivity.this)
        .setTitle(getString(R.string.txt_query_submitted))
        .setMessage(getString(R.string.txt_query_submit_info))
        .show();
        
        loadingDialog.dismiss();
        
        // remove the toasts
        Toast.makeText(QueryActivity.this, "Campaign: " + pQuery.getCampaignName(), Toast.LENGTH_SHORT).show();
        Toast.makeText(QueryActivity.this, "Title: " + pQuery.getQueryTitle(), Toast.LENGTH_SHORT).show();
        for(String item : pQuery.getIncludeKeywords()){
          Toast.makeText(QueryActivity.this, "include: " + item, Toast.LENGTH_SHORT).show();
        }
        for(String item : pQuery.getExcludeKeywords()){
          Toast.makeText(QueryActivity.this, "exclude: " + item, Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onError(Exception pEx) {
        new WarningDialogBuilder(QueryActivity.this)
        .setTitle(getString(R.string.txt_error))
        .setMessage(getString(R.string.txt_query_submit_error))
        .show();
        
        loadingDialog.dismiss();
      }
    });  
  }
}
