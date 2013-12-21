package com.example.twitteranalysisandroid;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.Toast;

public class SettingsActivity extends Activity implements OnClickListener {
  
  private EditText firstName, lastName, emailAddress;
  private CheckBox emailNotification, phoneNotification;
  private Button updateButton;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);
    
    // if no logged in user found, then finish immediately
    if(User.getCurrentUser() == null){
      finish();
    }
    
    firstName = (EditText) findViewById(R.id.et_first_name);
    lastName = (EditText) findViewById(R.id.et_last_name);
    emailAddress = (EditText) findViewById(R.id.et_email);
    emailNotification = (CheckBox) findViewById(R.id.chk_email_notification);
    phoneNotification = (CheckBox) findViewById(R.id.chk_phone_notification);
    updateButton = (Button) findViewById(R.id.button_update);
    
    User user = User.getCurrentUser();
    firstName.setText(user.getFirstName());
    lastName.setText(user.getLastName());
    emailAddress.setText(user.getEmail());
    emailNotification.setChecked(user.isEmailNotificationsEnabled());
    phoneNotification.setChecked(user.isphoneNotificationsEnabled());
    
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
      startActivity(queryIntent);
      finish();
      return true;
    case R.id.menu_reports:
      // start reports activity
      Intent reportsIntent = new Intent(this, ReportsActivity.class);
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
    if(firstName.getText().toString().trim().length()<1){
      Toast.makeText(this, getString(R.string.err_first_name), Toast.LENGTH_SHORT).show();
      return false;
    }
    
    if(lastName.getText().toString().trim().length()<1){
      Toast.makeText(this, getString(R.string.err_last_name), Toast.LENGTH_SHORT).show();
      return false;
    }
    
    if(emailAddress.getText().toString().trim().length()<1){
      Toast.makeText(this, getString(R.string.err_email_address), Toast.LENGTH_SHORT).show();
      return false;
    }
    
    return true;
  }
  
  private void update(){
    final ProgressDialog loadingDialog = new LoadingDialog(this);
    loadingDialog.show();
    
    User user = User.getCurrentUser();
    user.setFirstName(firstName.getText().toString().trim());
    user.setLastName(lastName.getText().toString().trim());
    user.setEmail(emailAddress.getText().toString().trim());
    user.setEmailNotificationsEnabled(emailNotification.isChecked());
    user.setphoneNotificationsEnabled(phoneNotification.isChecked());
    
    user.saveChanges(new Callback<User>(){
      @Override
      public void onSuccess(User pObject) {
        new WarningDialogBuilder(SettingsActivity.this)
        .setTitle(getString(R.string.txt_changes_saved))
        .setMessage(getString(R.string.txt_user_settings_updated))
        .show();
      
        loadingDialog.dismiss();
      }

      @Override
      public void onError(Exception pEx) {
        new WarningDialogBuilder(SettingsActivity.this)
        .setTitle(getString(R.string.txt_error))
        .setMessage(getString(R.string.txt_settings_update_error))
        .show();
      
        loadingDialog.dismiss();
      }
    });
  }
}
