package com.example.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.twitteranalysisandroid.R;

public class ViewQueryFragment extends Fragment {

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    // inflate the layout
    View view = inflater.inflate(R.layout.fragment_view_query, null);
    
    return view;
  }
}
