package com.example.fragment;

import static com.example.helper.Constants.DEBUG;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entity.Campaign;
import com.example.entity.Query;
import com.example.entity.TAError;
import com.example.helper.LoadingDialog;
import com.example.helper.WarningDialogBuilder;
import com.example.twitteranalysisandroid.R;
import com.example.webservice.CampaignService;
import com.example.webservice.FindCallback;
import com.example.webservice.QueryService;
import com.example.webservice.SaveCallback;

public class AddQueryFragment extends Fragment {

	// Items
	private Spinner campaignNameSpinner;

	private EditText includeKeywordEdit, excludeKeywordEdit;
	private LinearLayout includeKeywordsLayout, excludeKeywordsLayout;
	private OnClickListener removeFromIncludedListListener,
			removeFromExcludedListListener;

	private List<Campaign> campaigns;
	private Campaign currentCampaign;

	private static AddQueryFragment instance = null;

	public AddQueryFragment() {
		super();
	}

	public static AddQueryFragment getInstance() {
		if (instance == null)
			instance = new AddQueryFragment();

		return instance;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// inflate the layout
		View view = inflater.inflate(R.layout.fragment_add_query, null);

		campaignNameSpinner = (Spinner) view.findViewById(R.id.spinner_campaign_name);

		campaignNameSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int pIndex, long arg3) {
						currentCampaign = campaigns.get(pIndex);
						clearQuery();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});

		includeKeywordEdit = (EditText) view
				.findViewById(R.id.et_include_keyword);
		excludeKeywordEdit = (EditText) view
				.findViewById(R.id.et_exclude_keyword);

		removeFromIncludedListListener = new OnClickListener() {
			@Override
			public void onClick(View pView) {
				removeFromIncludedKeywords((LinearLayout) pView.getParent());
			}
		};
		removeFromExcludedListListener = new OnClickListener() {
			@Override
			public void onClick(View pView) {
				removeFromExcludedKeywords((LinearLayout) pView.getParent());
			}
		};

		includeKeywordsLayout = (LinearLayout) view
				.findViewById(R.id.list_include_keywords);
		excludeKeywordsLayout = (LinearLayout) view
				.findViewById(R.id.list_exclude_keywords);

		setupCampaignNames();

		return view;
	}

	private void setupCampaignNames() {
//		 final ProgressDialog loadingDialog = new LoadingDialog(getActivity());
//		 loadingDialog.show();

		new CampaignService().get(new FindCallback<Campaign>() {
			@Override
			public void onError(TAError pError) {
//        loadingDialog.dismiss();
        
        if(DEBUG){
  				new WarningDialogBuilder(getActivity())
  						.setTitle(getString(R.string.txt_error))
  						.setMessage(pError.getMessage()).show();
        } else {
          // if production then display check credentials message
          new WarningDialogBuilder(getActivity())
            .setTitle(getString(R.string.txt_error))
            .setMessage(getString(R.string.err_could_not_save))
            .show();
        }
			}

			@Override
			public void onFind(List<Campaign> pObjects) {
//        loadingDialog.dismiss();
        
				campaigns = pObjects;

				String[] campaignNames = new String[pObjects.size()];
				int i = 0;
				for (Campaign campaign : pObjects) {
					campaignNames[i] = campaign.getName();
					++i;
				}

				SpinnerAdapter campaignNameAdapter = new ArrayAdapter<String>(
						getActivity(),
						android.R.layout.simple_spinner_item, campaignNames);

				campaignNameSpinner.setAdapter(campaignNameAdapter);
			}
		});
	}

	private void clearQuery() {
		includeKeywordEdit.setText("");
		excludeKeywordEdit.setText("");
		includeKeywordsLayout.removeAllViews();
		excludeKeywordsLayout.removeAllViews();
	}

	public void addToIncludedKeywords(View view) {
		String keyword = includeKeywordEdit.getText().toString().trim();
		includeKeywordEdit.setText("");

		if (keyword.length() < 1) {
			Toast.makeText(getActivity(),
					getString(R.string.err_empty_keyword), Toast.LENGTH_SHORT)
					.show();
			return;
		}

		addIncludeKeyword(keyword, true);
	}

	private void addIncludeKeyword(String keyword, boolean removeable) {
		LayoutInflater inflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout listItem = (LinearLayout) inflater.inflate(
				R.layout.list_item, null);
		TextView listItemText = (TextView) listItem
				.findViewById(R.id.txt_keyword);
		Button listItemDeleteButton = (Button) listItem
				.findViewById(R.id.button_delete);

		listItemText.setText(keyword);
		if (removeable) {
			listItemDeleteButton
					.setOnClickListener(removeFromIncludedListListener);
		} else {
			listItemDeleteButton.setVisibility(View.GONE);
		}

		includeKeywordsLayout.addView(listItem);
	}

	private void addExcludeKeyword(String keyword, boolean removeable) {
		LayoutInflater inflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout listItem = (LinearLayout) inflater.inflate(
				R.layout.list_item, null);
		TextView listItemText = (TextView) listItem
				.findViewById(R.id.txt_keyword);
		Button listItemDeleteButton = (Button) listItem
				.findViewById(R.id.button_delete);

		listItemText.setText(keyword);
		if (removeable) {
			listItemDeleteButton
					.setOnClickListener(removeFromExcludedListListener);
		} else {
			listItemDeleteButton.setVisibility(View.GONE);
		}

		excludeKeywordsLayout.addView(listItem);
	}

	private void removeFromIncludedKeywords(LinearLayout pView) {
		includeKeywordsLayout.removeView(pView);
	}

	public void addToExcludedKeywords(View view) {
		String keyword = excludeKeywordEdit.getText().toString().trim();
		excludeKeywordEdit.setText("");

		if (keyword.length() < 1) {
			Toast.makeText(getActivity(),
					getString(R.string.err_empty_keyword), Toast.LENGTH_SHORT)
					.show();
			return;
		}

		addExcludeKeyword(keyword, true);
	}

	private void removeFromExcludedKeywords(LinearLayout pView) {

		excludeKeywordsLayout.removeView(pView);
	}

	public void addQuery(View view) {
		submitNewQuery();
	}

	private List<String> getIncludeKeywords() {
		int numItems = includeKeywordsLayout.getChildCount();
		List<String> result = new ArrayList<String>(numItems);

		for (int i = 0; i < numItems; ++i) {
			TextView keyword = (TextView) includeKeywordsLayout.getChildAt(i)
					.findViewById(R.id.txt_keyword);
			result.add(keyword.getText().toString().trim());
		}

		return result;
	}

	private List<String> getExcludeKeywords() {
		int numItems = excludeKeywordsLayout.getChildCount();
		List<String> result = new ArrayList<String>(numItems);

		for (int i = 0; i < numItems; ++i) {
			TextView keyword = (TextView) excludeKeywordsLayout.getChildAt(i)
					.findViewById(R.id.txt_keyword);
			result.add(keyword.getText().toString().trim());
		}

		return result;
	}

	private void submitNewQuery() {
		Query newQuery = new Query();
		newQuery.setIncluding(getIncludeKeywords());
		newQuery.setExcluding(getExcludeKeywords());

		final ProgressDialog loadingDialog = new LoadingDialog(getActivity());
		loadingDialog.show();

		new QueryService().add(currentCampaign, newQuery,
				new SaveCallback<Query>() {
					@Override
					public void onError(TAError pError) {
						new WarningDialogBuilder(getActivity())
								.setTitle(getString(R.string.txt_error))
								.setMessage(
										getString(R.string.txt_query_submit_error))
								.show();

						loadingDialog.dismiss();
					}

					@Override
					public void onSave(Query pObject) {
						new WarningDialogBuilder(getActivity())
								.setTitle(
										getString(R.string.txt_query_submitted))
								.setMessage(
										getString(R.string.txt_query_submit_info))
								.show();

						loadingDialog.dismiss();
					}
				});
	}
}
