package com.example.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.example.twitteranalysisandroid.R;

public class WarningDialogBuilder extends AlertDialog.Builder {

  public WarningDialogBuilder(Context context) {
    super(context);
 
    setPositiveButton(context.getString(R.string.txt_ok), new OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
      }
    });
  }

}
