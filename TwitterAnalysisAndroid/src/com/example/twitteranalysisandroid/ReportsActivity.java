package com.example.twitteranalysisandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ReportsActivity extends Activity {
  public static final String USER_ID = "USER_ID";
  
  private long userId;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_report);
    
    // get the user id of the logged in user from the intent
    if(getIntent().getExtras() != null){
      userId = getIntent().getExtras().getLong(USER_ID, -1);
    }
    
    // if no logged in user found, then finish immediately
    if(userId<=0){
      finish();
    }
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
     // this is the current activity so return
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
  protected void onResume(){
    super.onResume();
    
    // TODO: query the reports
  }
}
