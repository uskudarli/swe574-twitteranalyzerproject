package com.example.twitteranalysisandroid;

import android.app.ProgressDialog;
import android.content.Context;

public class LoadingDialog extends ProgressDialog {

  public LoadingDialog(Context context){
    super(context);
    
    setProgressStyle(ProgressDialog.STYLE_SPINNER);
    setMessage(context.getResources().getString(R.string.txt_loading));
    setCancelable(false);
  }
}
