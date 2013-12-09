package com.example.twitteranalysisandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginActivity extends Activity implements OnClickListener {
  
  Button loginButton;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    
    loginButton = (Button) findViewById(R.id.button_login);
    loginButton.setOnClickListener(this);
  }

  @Override
  public void onClick(View clickedView) {
    int clickedId = clickedView.getId();
    switch(clickedId){
    case R.id.button_login:
      long userId = -1;
      
      // TODO: do login check here
      userId = 1; // auto login, remove this line
      
      if(userId <= 0){
        new AlertDialog.Builder(this)
        .setTitle("Invalid Login")
        .setMessage("Check your email address and password")
        .show();
        
        return;
      }
      
      Intent intent = new Intent(this, QueryActivity.class);
      intent.putExtra(QueryActivity.USER_ID, userId);
      startActivity(intent);
      finish();     
    break;
    }
  }
}
