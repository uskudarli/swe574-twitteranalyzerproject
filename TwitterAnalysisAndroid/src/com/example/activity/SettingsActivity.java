package com.example.activity;

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
import android.widget.EditText;
import android.widget.Toast;

import com.example.entity.TAError;
import com.example.entity.User;
import com.example.helper.LoadingDialog;
import com.example.helper.WarningDialogBuilder;
import com.example.twitteranalysisandroid.R;
import com.example.webservicecallback.SaveCallback;

public class SettingsActivity extends Activity implements OnClickListener {
  
  private EditText name;
  private Button updateButton;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);
    
    // if no logged in user found, then finish immediately
    if(User.getCurrentUser() == null){
      finish();
    }
    
    name = (EditText) findViewById(R.id.et_first_name);
    
    User user = User.getCurrentUser();
    name.setText(user.getName());
    
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
    if(name.getText().toString().trim().length()<1){
      Toast.makeText(this, getString(R.string.err_first_name), Toast.LENGTH_SHORT).show();
      return false;
    }
    
    return true;
  }
  
  private void update(){
    final ProgressDialog loadingDialog = new LoadingDialog(this);
    loadingDialog.show();
    
    User user = User.getCurrentUser();
    user.setName(name.getText().toString().trim());
    
    user.saveChanges(new SaveCallback<User>(){
      @Override
      public void onSave(User pObject) {
        new WarningDialogBuilder(SettingsActivity.this)
        .setTitle(getString(R.string.txt_changes_saved))
        .setMessage(getString(R.string.txt_user_settings_updated))
        .show();
      
        loadingDialog.dismiss();
      }

      @Override
      public void onError(TAError pError) {
        new WarningDialogBuilder(SettingsActivity.this)
        .setTitle(getString(R.string.txt_error))
        .setMessage(pError.getMessage())
        .show();
      
        loadingDialog.dismiss();
      }
    });
  }
}
