package com.example.fragment;

import static com.example.helper.Constants.DEBUG;
import static com.example.helper.Constants.NOVALIDATION;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.entity.TAError;
import com.example.entity.User;
import com.example.helper.LoadingDialog;
import com.example.helper.WarningDialogBuilder;
import com.example.twitteranalysisandroid.R;
import com.example.webservice.SaveCallback;

public class ChangePasswordFragment extends Fragment {
  
  private EditText mCurrentPassword;
  private EditText mNewPassword;
  private EditText mNewConfirmPassword;
  private Button mChangePassButton;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    // inflate the layout
    View view = inflater.inflate(R.layout.fragment_change_password, null);
    
    // get references to some view objects
    mChangePassButton = (Button) view.findViewById(R.id.button_change_password);
    mCurrentPassword = (EditText) view.findViewById(R.id.edit_current_password);
    mNewPassword = (EditText) view.findViewById(R.id.edit_new_password);
    mNewConfirmPassword = (EditText) view.findViewById(R.id.edit_confirm_new_password);
    
    return view;
  }
  
  @Override
  public void onStart(){
    super.onStart();
  }
  
  @Override
  public void onResume(){
    super.onResume();
    
    // start listening to the update button
    mChangePassButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        // validate and submit the form
        validateAndSubmit();
      }
    });
  }
  
  @Override
  public void onPause(){
    super.onPause();
    
    // stop listening to the login button
    mChangePassButton.setOnClickListener(null);
  }
  
  @Override
  public void onStop(){
    super.onStop();
  }
  
  /***
   * Validates and submits the form
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
   * @return whether or not the form is valid
   */
  private boolean isFormValid(){
    // skip validation if needed
    if(NOVALIDATION){
      return true;
    }
    
    // remove any existing errors from form
    clearFromErrors();
    
    boolean isValid = true;
    
    // check that the password is correct
    if(!User.getCurrentUser().checkPassword(mCurrentPassword.getText().toString().trim())){
      mCurrentPassword.setError(getString(R.string.err_current_pass));
      isValid = false;
    }
    
    // check that new password is long enough
    if(mNewPassword.getText().toString().trim().length()<6){
      mNewPassword.setError(getString(R.string.err_password));
      isValid = false;
    }
    
    // check that the password is correct
    if(mNewPassword.getText().toString().trim().equals(mNewConfirmPassword.getText().toString().trim())){
      mCurrentPassword.setError(getString(R.string.err_confirm_password));
      isValid = false;
    }
    
    return isValid;
  }
  
  /***
   * Clears all errors from the form
   */
  private void clearFromErrors(){
    mCurrentPassword.setError(null);
  }
  
  /***
   * Submits the form to change the user password
   * Displays loading dialog while processing request
   * Displays success or error message when done
   */
  private void submit(){
    // start a loading dialog
    final ProgressDialog loadingDialog = new LoadingDialog(getActivity());
    loadingDialog.show();
    
    // call the change password method with the new password
    User.getCurrentUser().changePassword(mNewPassword.getText().toString().trim(), 
        new SaveCallback<User>(){
          @Override
          public void onSave(User pUser) {
            // stop loading
            loadingDialog.dismiss();
            
            // display success message
            new WarningDialogBuilder(getActivity())
            .setTitle(getString(R.string.txt_error))
            .setMessage(getString(R.string.password_changed))
            .show();
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
                .setMessage(getString(R.string.err_could_not_save))
                .show();
            }
          }
    });
  }
}
