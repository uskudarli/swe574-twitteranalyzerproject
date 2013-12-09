package com.example.twitteranalysisandroid;

import android.app.Activity;
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

public class QueryActivity extends Activity implements OnClickListener {
  public static final String USER_ID = "USER_ID";
  
  private long userId;
  private Spinner campaignNameSpinner;
  private SpinnerAdapter campaignNameAdapter;
  private EditText queryTitleEdit, includeKeywordEdit, excludeKeywordEdit;
  private Button submitButton, addIncludeKeywordButton, addExcludeKeywordButton;
  private LinearLayout includeKeywordsLayout, excludeKeywordsLayout;
  private OnClickListener removeFromIncludedListListener, removeFromExcludedListListener;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_query);
    
    // get the user id of the logged in user from the intent
    if(getIntent().getExtras() != null){
      userId = getIntent().getExtras().getLong(USER_ID, -1);
    }
    
    // if no logged in user found, then finish immediately
    if(userId<=0){
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
    
    // TODO: fill the spinner adapter with real values
    String[] campaignNames = {"", "Campaign1", "Campaign2"}; // remove this line
    campaignNameAdapter = new ArrayAdapter<String>(this, 
        android.R.layout.simple_spinner_item,
        campaignNames);
    campaignNameSpinner.setAdapter(campaignNameAdapter);    
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
      reportsIntent.putExtra(ReportsActivity.USER_ID, userId);
      startActivity(reportsIntent);
      finish();
      return true;
    case R.id.menu_settings:
      // start settings activity
      Intent settingsIntent = new Intent(this, SettingsActivity.class);
      settingsIntent.putExtra(SettingsActivity.USER_ID, userId);
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
  
  private void addToIncludedKeywords(){
    String keyword = includeKeywordEdit.getText().toString().trim();
    includeKeywordEdit.setText("");
    
    // TODO: indicate to the user why the item is not added
    if(keyword.length()<1){
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
    
    // TODO: indicate to the user why the item is not added
    if(keyword.length()<1){
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
    // TODO: add form validation code and mark errors
    return false;
  }
  
  private void submit(){
    // TODO: read all the data and submit to database
    
    clearForm();
  }
}
