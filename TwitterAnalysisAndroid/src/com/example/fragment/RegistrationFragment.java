package com.example.fragment;

import static com.example.helper.Constants.DEBUG;
import static com.example.helper.Constants.NOVALIDATION;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.activity.MainActivity;
import com.example.entity.TAError;
import com.example.entity.User;
import com.example.helper.LoadingDialog;
import com.example.helper.WarningDialogBuilder;
import com.example.twitteranalysisandroid.R;
import com.example.webservice.SignupCallback;

public class RegistrationFragment extends Fragment {
  
  static final String TAG = "RegistrationFragment";
  
  private EditText emailEdit, passwordEdit, confirmPasswordEdit;
  private Button registerButton;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    // inflate the layout
    View view = inflater.inflate(R.layout.fragment_registration, null);
    
    // get references to some view objects
    registerButton = (Button) view.findViewById(R.id.button_register);
    emailEdit = (EditText) view.findViewById(R.id.edit_email_registration);
    passwordEdit = (EditText) view.findViewById(R.id.edit_password_registration);
    confirmPasswordEdit = (EditText) view.findViewById(R.id.edit_confirm_password_registration);
    
    return view;
  }
  
  @Override
  public void onStart(){
    super.onStart();
  }
  
  @Override
  public void onResume(){
    super.onResume();
    
    // start listening to the login button
    registerButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        // validate and submit the login form
        validateAndSubmit();
      }
    });
  }
  
  @Override
  public void onPause(){
    super.onPause();
    
    // stop listening to the login button
    registerButton.setOnClickListener(null);
  }
  
  @Override
  public void onStop(){
    super.onStop();
  }
  
  /***
   * Validates and submits the Login form
   * Marks invalid form items if form is not valid
   */
  private void validateAndSubmit(){
    if(isFormValid()){
      submit();
    }
  }
  
  /***
   * Checks every field of the form and marks them if they
   * do not fit the form requirements
   * @return whether or not login form is valid
   */
  private boolean isFormValid(){
    // skip validation if needed
    if(NOVALIDATION){
      return true;
    }
    
    // remove any existing errors from form
    clearFromErrors();
    
    boolean isValid = true;
    
    // check that email exists
    // TODO: add email regex
    if(emailEdit.getText().toString().trim().length()<1){
      emailEdit.setError(getString(R.string.err_email));
      isValid = false;
    }
    
    // check that password exists
    // TODO: set minimum password length
    if(passwordEdit.getText().toString().trim().length()<1){
      passwordEdit.setError(getString(R.string.err_password));
      isValid = false;
    }
    
    // check that password match
    if(!passwordEdit.getText().toString().trim().equals(confirmPasswordEdit.getText().toString().trim())){
      confirmPasswordEdit.setError(getString(R.string.err_confirm_password));
      isValid = false;
    }
    
    return isValid;
  }
  
  /***
   * Clears all errors from the login form
   */
  private void clearFromErrors(){
    emailEdit.setError(null);
    passwordEdit.setError(null);
    confirmPasswordEdit.setError(null);
  }
  
  /***
   * Submits the login form to log the user in
   * Displays loading dialog while processing request
   * If successful, redirects to MainActivity
   * otherwise displays and error message
   */
  private void submit(){
    // start a loading dialog
    final ProgressDialog loadingDialog = new LoadingDialog(getActivity());
    loadingDialog.show();
    
    // create a new user with the parameters from the form
    User newUser = new User(emailEdit.getText().toString().trim(), passwordEdit.getText().toString().trim());
    
    // call the signup method on the new user
    newUser.signup(new SignupCallback(){
      @Override
      public void onSignup(User pUser) {
        // stop loading
        loadingDialog.dismiss();
        
        // if logged in then finish the activity
        getActivity().finish();
        
        // start the main activity
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
      }

      @Override
      public void onError(TAError pError) {
        // stop loading
        loadingDialog.dismiss();
        
        if(DEBUG){
          // if on debug then display the error message
          new WarningDialogBuilder(getActivity())
            .setTitle(getString(R.string.txt_error))
            .setMessage(pError.getMessage())
            .show();
        } else {
          // if production then display check credentials message
          new WarningDialogBuilder(getActivity())
            .setTitle(getString(R.string.txt_error))
            .setMessage(getString(R.string.err_dublicate_email))
            .show();
        }
      }
    });
  }
}
