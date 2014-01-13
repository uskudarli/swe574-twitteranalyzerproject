package com.example.fragment;

import static com.example.helper.Constants.NOVALIDATION;

import com.example.entity.Campaign;
import com.example.entity.TAError;
import com.example.helper.WarningDialogBuilder;
import com.example.twitteranalysisandroid.R;
import com.example.webservice.CampaignService;
import com.example.webservice.SaveCallback;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddCampaignFragment extends Fragment {

	private EditText campaignNameEditText, campaignDescriptionEditText;
	private Button campaignAddButton;

	private static AddCampaignFragment instance = null;

	public AddCampaignFragment() {
		super();
	}

	public static AddCampaignFragment getInstance() {
		if (instance == null)
			instance = new AddCampaignFragment();

		return instance;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// inflate the layout
		View view = inflater.inflate(R.layout.fragment_add_campaign, null);

		campaignNameEditText = (EditText) view
				.findViewById(R.id.et_campaign_name);
		campaignDescriptionEditText = (EditText) view
				.findViewById(R.id.et_campaign_description);

		campaignAddButton = (Button) view
				.findViewById(R.id.button_add_campaign);

		return view;
	}

	public void addCampaign(View view) {
		if (isFormValid()) {
			Campaign campaign = new Campaign();
			campaign.setName(campaignNameEditText.getText().toString());
			campaign.setDescription(campaignDescriptionEditText.getText().toString());
			
			new CampaignService().add(campaign, new SaveCallback<Campaign>() {
				
				@Override
				public void onError(TAError pError) {
					new WarningDialogBuilder(getActivity())
		            .setTitle(getString(R.string.txt_error))
		            .setMessage(pError.getMessage())
		            .show();					
				}
				
				@Override
				public void onSave(Campaign pObject) {
					Toast.makeText(getActivity(), getString(R.string.txt_campaign_submitted), Toast.LENGTH_SHORT).show();
				}
			});
		}
	}

	/***
	 * Checks every field of the form and marks them if they do not fit the form
	 * requirements
	 * 
	 * @return whether or not login form is valid
	 */
	private boolean isFormValid() {
		// skip validation if needed
		if (NOVALIDATION) {
			return true;
		}

		// remove any existing errors from form
		clearFromErrors();

		boolean isValid = true;

		// check that email exists
		// TODO: add email regex
		if (campaignNameEditText.getText().toString().trim().length() < 1) {
			campaignNameEditText
					.setError(getString(R.string.err_campaign_name));
			isValid = false;
		}

		// check that password exists
		// TODO: set minimum password length
		if (campaignDescriptionEditText.getText().toString().trim().length() < 1) {
			campaignDescriptionEditText
					.setError(getString(R.string.err_campaign_description));
			isValid = false;
		}

		return isValid;
	}

	/***
	 * Clears all errors from the login form
	 */
	private void clearFromErrors() {
		campaignDescriptionEditText.setError(null);
		campaignDescriptionEditText.setError(null);
	}
}
