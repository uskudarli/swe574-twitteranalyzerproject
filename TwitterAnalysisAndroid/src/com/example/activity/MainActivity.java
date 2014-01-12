package com.example.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.entity.User;
import com.example.fragment.AddCampaignFragment;
import com.example.fragment.AddQueryFragment;
import com.example.fragment.ProfileFragment;
import com.example.fragment.ViewQueryFragment;
import com.example.fragment.ViewReportFragment;
import com.example.twitteranalysisandroid.R;

public class MainActivity extends MenuActivity {

	private static final int VIEW_REPORT_INDEX = 0;
	private static final int VIEW_QUERY_INDEX = 1;
	private static final int ADD_QUERY_INDEX = 2;
	private static final int ADD_CAMPAIGN_INDEX = 3;
	private static final int PROFILE_INDEX = 4;

	private View mMenuViewReport;
	private View mMenuViewQuery;
	private View mMenuAddQuery;
	private View mMenuAddCampaign;
	private View mMenuProfile;

	private AddCampaignFragment addCampaignFragment;
	private AddQueryFragment addQueryFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// a user must be logged in
		if (User.getCurrentUser() == null) {
			finish();
			return;
		}

		setContentView(R.layout.activity_main);

		// setup the references required by the super class
		// menu view
		mMenu = findViewById(R.id.menu_layout);
		// content view and menu button on content view
		mContent = findViewById(R.id.main_layout);
		mToggleMenuButton = findViewById(R.id.toggle_menu_button);
		// view pager
		mViewPager = (ViewPager) findViewById(R.id.view_pager);

		// get references to the menu buttons
		mMenuViewReport = findViewById(R.id.menu_view_report);
		mMenuViewQuery = findViewById(R.id.menu_view_query);
		mMenuAddQuery = findViewById(R.id.menu_add_query);
		mMenuAddCampaign = findViewById(R.id.menu_add_campaign);
		mMenuProfile = findViewById(R.id.menu_profile);

		// setup the view pager
		mViewPager.setAdapter(new FragmentPagerAdapter(
				getSupportFragmentManager()) {
			@Override
			public Fragment getItem(int arg0) {
				switch (arg0) {
				case VIEW_REPORT_INDEX:
					// first page is the login page
					return new ViewReportFragment();
				case VIEW_QUERY_INDEX:
					// second page is the registration page
					return new ViewQueryFragment();
				case ADD_QUERY_INDEX:
					// first page is the login page
					addQueryFragment = AddQueryFragment.getInstance();
					return addQueryFragment;
				case ADD_CAMPAIGN_INDEX:
					// second page is the registration page
					addCampaignFragment = AddCampaignFragment.getInstance();
					return addCampaignFragment;
				case PROFILE_INDEX:
					// second page is the registration page
					return new ProfileFragment();
				default:
					// make report the default page
					return new ViewReportFragment();
				}
			}

			@Override
			public int getCount() {
				// four pages in the pager
				// can't find a way to make this dynamic
				return 5;
			}
		});

		// if returning then open the correct page
		if (savedInstanceState != null) {
			mViewPager.setCurrentItem(savedInstanceState.getInt(PAGE, 0));
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		// setup menu button listeners
		mMenuViewReport.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mViewPager.setCurrentItem(VIEW_REPORT_INDEX);
        closeMenu();
			}
		});
		mMenuViewQuery.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mViewPager.setCurrentItem(VIEW_QUERY_INDEX);
        closeMenu();
			}
		});
		mMenuAddQuery.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mViewPager.setCurrentItem(ADD_QUERY_INDEX);
        closeMenu();
			}
		});
		mMenuAddCampaign.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mViewPager.setCurrentItem(ADD_CAMPAIGN_INDEX);
        closeMenu();
			}
		});
		mMenuProfile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mViewPager.setCurrentItem(PROFILE_INDEX);
        closeMenu();
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();

		// unregister all click listeners
		mMenuViewReport.setOnClickListener(null);
		mMenuViewQuery.setOnClickListener(null);
		mMenuAddQuery.setOnClickListener(null);
		mMenuAddCampaign.setOnClickListener(null);
		mMenuProfile.setOnClickListener(null);
	}

	public void addCampaign(View view) {
		addCampaignFragment.addCampaign(view);
	}
	public void addToIncludedKeywords(View view) {
		addQueryFragment.addToIncludedKeywords(view);
	}
	public void addToExcludedKeywords(View view) {
		addQueryFragment.addToExcludedKeywords(view);
	}
	public void addQuery(View view) {
		addQueryFragment.addQuery(view);
	}
}