package com.example.twitteranalysisandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class SettingsActivity extends Activity implements OnClickListener {
  public static final String USER_ID = "USER_ID";
  
  private long userId;
  private EditText firstName, lastName, emailAddress;
  private CheckBox emailNotification, phoneNotification;
  private Button updateButton;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);
    
    // get the user id of the logged in user from the intent
    if(getIntent().getExtras() != null){
      userId = getIntent().getExtras().getLong(USER_ID, -1);
    }
    
    // if no logged in user found, then finish immediately
    if(userId<=0){
      finish();
    }
    
    firstName = (EditText) findViewById(R.id.et_first_name);
    lastName = (EditText) findViewById(R.id.et_last_name);
    emailAddress = (EditText) findViewById(R.id.et_email);
    emailNotification = (CheckBox) findViewById(R.id.chk_email_notification);
    phoneNotification = (CheckBox) findViewById(R.id.chk_phone_notification);
    updateButton = (Button) findViewById(R.id.button_update);
    
    updateButton.setOnClickListener(this);
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
      // start query activity
      Intent queryIntent = new Intent(this, QueryActivity.class);
      queryIntent.putExtra(QueryActivity.USER_ID, userId);
      startActivity(queryIntent);
      finish();
      return true;
    case R.id.menu_reports:
      // start reports activity
      Intent reportsIntent = new Intent(this, ReportsActivity.class);
      reportsIntent.putExtra(ReportsActivity.USER_ID, userId);
      startActivity(reportsIntent);
      finish();
      return true;
    case R.id.menu_settings:
      // this is the current activity so return
      return true;
    default:
      return super.onOptionsItemSelected(item);
    }
  }
  
  @Override
  protected void onResume(){
    super.onResume();
    
    // TODO: query the user settings and update the ui
  }

  @Override
  public void onClick(View pView) {
    switch(pView.getId()){
    case R.id.button_update:
      validateAndUpdate();
      break;
    }
  }
  
  private void validateAndUpdate(){
    if(isFormValid()){
      update();
    }
  }
  
  private boolean isFormValid(){
    // TODO: add form validation code and mark errors
    return false;
  }
  
  private void update(){
    // TODO: read all the data and update to database
  }
}
