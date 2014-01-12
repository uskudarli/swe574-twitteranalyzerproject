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

import com.example.activity.QueryActivity;
import com.example.entity.TAError;
import com.example.entity.User;
import com.example.helper.LoadingDialog;
import com.example.helper.WarningDialogBuilder;
import com.example.twitteranalysisandroid.R;
import com.example.webservice.SaveCallback;

public class ProfileFragment extends Fragment {
  
  private EditText mFullNameEdit;
  private Button mUpdateButton;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    // inflate the layout
    View view = inflater.inflate(R.layout.fragment_profile, null);
    
    // get references to some view objects
    mUpdateButton = (Button) view.findViewById(R.id.button_update);
    mFullNameEdit = (EditText) view.findViewById(R.id.et_name);
    
    // set up email and password from disk
    mFullNameEdit.setText(User.getCurrentUser().getName());
    
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
    mUpdateButton.setOnClickListener(new OnClickListener() {
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
    mUpdateButton.setOnClickListener(null);
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
    
    // check that a name exists
    if(mFullNameEdit.getText().toString().trim().length()<1){
      mFullNameEdit.setError(getString(R.string.err_name));
      isValid = false;
    }
    
    return isValid;
  }
  
  /***
   * Clears all errors from the form
   */
  private void clearFromErrors(){
    mFullNameEdit.setError(null);
  }
  
  /***
   * Submits the form to update user profile
   * Displays loading dialog while processing request
   * Displays success or error message when done
   */
  private void submit(){
    // start a loading dialog
    final ProgressDialog loadingDialog = new LoadingDialog(getActivity());
    loadingDialog.show();
    
    User.getCurrentUser().setName(mFullNameEdit.getText().toString().trim());
    
    // call the login method with the parameters from the form
    User.getCurrentUser().saveChanges(
        new SaveCallback<User>(){
          @Override
          public void onSave(User pUser) {
            // stop loading
            loadingDialog.dismiss();
            
            // if logged in then finish the activity
            getActivity().finish();
            
            // start the main activity
            Intent intent = new Intent(getActivity(), QueryActivity.class);
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
                .setMessage(getString(R.string.err_could_not_save))
                .show();
            }
          }
    });
  }
}
