package com.example.fragment;

import static com.example.helper.Constants.DEBUG;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.entity.Campaign;
import com.example.entity.Query;
import com.example.entity.TAError;
import com.example.helper.LoadingDialog;
import com.example.helper.WarningDialogBuilder;
import com.example.twitteranalysisandroid.R;
import com.example.webservice.CampaignService;
import com.example.webservice.FindCallback;

public class ViewQueryFragment extends Fragment {

  static final String TAG = "ViewQueryFragment";
  
  private Spinner mCampaignSpinner;
  private Spinner mQuerySpinner;
  private LinearLayout includeKeywordsLayout;
  private LinearLayout excludeKeywordsLayout;
  
  private List<Campaign> campaigns;
  private List<Query> queries;
  private Campaign currentCampaign;
  private Query currentQuery;
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    // inflate the layout
    View view = inflater.inflate(R.layout.fragment_view_query, null);
    
    // get some references
    mCampaignSpinner = (Spinner) view.findViewById(R.id.spinner_campaign_name);
    mQuerySpinner = (Spinner) view.findViewById(R.id.spinner_query_number);
    includeKeywordsLayout = (LinearLayout) view.findViewById(R.id.list_include_keywords);
    excludeKeywordsLayout = (LinearLayout) view.findViewById(R.id.list_exclude_keywords);
    
    return view;
  }
  
  @Override
  public void onResume(){
    super.onResume();
    
    if(campaigns == null){
      setupCampaignNames();
    }
    
    mCampaignSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> arg0, View arg1, int pIndex, long arg3) {
        currentCampaign = campaigns.get(pIndex);
        setupCampaign();
      }

      @Override
      public void onNothingSelected(AdapterView<?> arg0) {
      }
    });
    
    mQuerySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> arg0, View arg1, int pIndex, long arg3) {
        currentQuery = queries.get(pIndex);
        setupQuery();
      }

      @Override
      public void onNothingSelected(AdapterView<?> arg0) {
      }
    });
  }
  
  @Override
  public void onPause(){
    super.onPause();
    
    mCampaignSpinner.setOnItemSelectedListener(null);
    mQuerySpinner.setOnItemSelectedListener(null);
  }
  
  private void setupCampaignNames() {
    currentCampaign = null;

    final ProgressDialog loadingDialog = new LoadingDialog(getActivity());
    loadingDialog.show();
    
    new CampaignService().get(new FindCallback<Campaign>() {
      @Override
      public void onError(TAError pError) {
        loadingDialog.dismiss();
        
        if(DEBUG){
          new WarningDialogBuilder(getActivity().getApplicationContext())
            .setTitle(getString(R.string.txt_error))
            .setMessage(pError.getMessage())
            .show();
        } else {
          new WarningDialogBuilder(getActivity().getApplicationContext())
              .setTitle(getString(R.string.txt_error))
              .setMessage(getString(R.string.txt_error_get))
              .show();
        }
      }

      @Override
      public void onFind(List<Campaign> pObjects) {
        loadingDialog.dismiss();
        
        campaigns = pObjects;
        if(campaigns.size()>0){
          currentCampaign = campaigns.get(0);
        }

        String[] campaignNames = new String[pObjects.size()];
        int i = 0;
        for (Campaign campaign : pObjects) {
          campaignNames[i] = campaign.getName();
          ++i;
        }

        SpinnerAdapter campaignNameAdapter = new ArrayAdapter<String>(
            getActivity(),
            android.R.layout.simple_spinner_item, 
            campaignNames);

        mCampaignSpinner.setAdapter(campaignNameAdapter);
        
        setupCampaign();
      }
    });
  }
  
  private void clearCampaign(){
    mQuerySpinner.setAdapter(new ArrayAdapter<String>(
        getActivity(),
        android.R.layout.simple_spinner_item,
        new String[0]));
    currentQuery = null;
    setupQuery();
  }
  
  private void setupCampaign() {
    clearCampaign();
    
    if(currentCampaign == null){
      return;
    }
    
    final ProgressDialog loadingDialog = new LoadingDialog(getActivity());
    loadingDialog.show();

    currentCampaign.getQueries(
        new FindCallback<Query>() {
          @Override
          public void onError(TAError pError) {
            new WarningDialogBuilder(getActivity())
                .setTitle(getString(R.string.txt_error))
                .setMessage(pError.getMessage()).show();

            loadingDialog.dismiss();
          }

          @Override
          public void onFind(List<Query> pObjects) {
            loadingDialog.dismiss();
            
            queries = pObjects;
            if(queries.size()>0){
              currentQuery = queries.get(0);
            }
            
            String[] queryNumbers = new String[pObjects.size()];
            int i = 0;
            for (Query query : pObjects) {
              queryNumbers[i] = Long.toString(query.getId());
              ++i;
            }

            SpinnerAdapter queryNumberAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                queryNumbers);
            mQuerySpinner.setAdapter(queryNumberAdapter);
            
            setupQuery();
          }
        });
  }
  
  private void setupQuery() {
    clearQuery();

    if (currentQuery == null) {
      return;
    }

    for (String include : currentQuery.getIncluding()) {
      addIncludeKeyword(include);
    }

    for (String exclude : currentQuery.getExcluding()) {
      addExcludeKeyword(exclude);
    }
  }
  
  private void clearQuery() {
    includeKeywordsLayout.removeAllViews();
    excludeKeywordsLayout.removeAllViews();
  }
  
  private void addIncludeKeyword(String keyword) {
    includeKeywordsLayout.addView(getListItem(keyword));
  }

  private void addExcludeKeyword(String keyword) {
    excludeKeywordsLayout.addView(getListItem(keyword));
  }
  
  private View getListItem(String text){
    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    LinearLayout listItem = (LinearLayout) inflater.inflate(R.layout.list_item, null);
    TextView listItemText = (TextView) listItem.findViewById(R.id.txt_keyword);
    Button listItemDeleteButton = (Button) listItem.findViewById(R.id.button_delete);

    listItemText.setText(text);
    listItemDeleteButton.setVisibility(View.GONE);
    
    return listItem;
  }
}
