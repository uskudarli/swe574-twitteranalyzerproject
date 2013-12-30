package com.example.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.entity.TAError;
import com.example.entity.User;
import com.example.helper.AuthenticationKeeper;
import com.example.helper.LoadingDialog;
import com.example.helper.WarningDialogBuilder;
import com.example.twitteranalysisandroid.R;
import com.example.webservicecallback.LoginCallback;

public class LoginActivity extends Activity implements OnClickListener {
  
  private static final String TAG = "LoginActivity";
  
  private EditText emailEdit, passwordEdit;
  private Button loginButton;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    
    loginButton = (Button) findViewById(R.id.button_login);
    emailEdit = (EditText) findViewById(R.id.edit_email);
    passwordEdit = (EditText) findViewById(R.id.edit_password);
    
    loginButton.setOnClickListener(this);
    
    if(AuthenticationKeeper.getInstance().getUsername() != null && 
        AuthenticationKeeper.getInstance().getPassword() != null){
      emailEdit.setText(AuthenticationKeeper.getInstance().getUsername());
      passwordEdit.setText(AuthenticationKeeper.getInstance().getPassword());
      validateAndSubmit();
    }
  }

  @Override
  public void onClick(View clickedView) {
    int clickedId = clickedView.getId();
    switch(clickedId){
    case R.id.button_login:
      validateAndSubmit();
    break;
    }
  }
  
  private void validateAndSubmit(){
    if(isFormValid()){
      submit();
    }
  }
  
  private boolean isFormValid(){
    if(emailEdit.getText().toString().trim().length()<1){
      Toast.makeText(this, getString(R.string.err_email), Toast.LENGTH_SHORT).show();
      return false;
    }
    
    if(passwordEdit.getText().toString().trim().length()<1){
      Toast.makeText(this, getString(R.string.err_password), Toast.LENGTH_SHORT).show();
      return false;
    }
    
    return true;
  }
  
  private void submit(){
    final ProgressDialog loadingDialog = new LoadingDialog(this);
    loadingDialog.show();
    
    User.login(emailEdit.getText().toString().trim(), passwordEdit.getText().toString().trim(),
        new LoginCallback(){
          @Override
          public void onLogin(User pUser) {
            Intent intent = new Intent(LoginActivity.this, QueryActivity.class);
            startActivity(intent);
            
            loadingDialog.dismiss();
            finish();
          }

          @Override
          public void onError(TAError pError) {
            new WarningDialogBuilder(LoginActivity.this)
              .setTitle(getString(R.string.txt_error))
              .setMessage(pError.getMessage())
              .show();
            
            loadingDialog.dismiss();
          }
    });
  }
}
