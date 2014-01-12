package com.example.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.fragment.LoginFragment;
import com.example.fragment.RegistrationFragment;
import com.example.twitteranalysisandroid.R;

public class LoginActivity extends MenuActivity {
  
  private static final int LOGIN_INDEX = 0;
  private static final int REGISTRATION_INDEX = 1;
  
  private View mMenuLoginButton;
  private View mMenuRegistrationButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_login);
    
    // setup the references required by the super class
    // menu view
    mMenu = findViewById(R.id.menu_layout);
    // content view and menu button on content view
    mContent = findViewById(R.id.main_layout);
    mToggleMenuButton = findViewById(R.id.toggle_menu_button);
    // view pager
    mViewPager = (ViewPager) findViewById(R.id.view_pager);
    
    // get references to the menu buttons
    mMenuLoginButton = findViewById(R.id.menu_login);
    mMenuRegistrationButton = findViewById(R.id.menu_registration); 
    
    // setup the view pager
    mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()){
      @Override
      public Fragment getItem(int arg0) {
        switch(arg0){
        case LOGIN_INDEX:
          // first page is the login page
          return new LoginFragment();
        case REGISTRATION_INDEX:
          // second page is the registration page
          return new RegistrationFragment();
        default:
          // make login the default page
          return new LoginFragment();
        } 
      }

      @Override
      public int getCount() {
        // only two pages in the pager
        // can't find a way to make this dynamic
        return 2;
      }
    });
    
    // if returning then open the correct page
    if (savedInstanceState != null) {
      mViewPager.setCurrentItem(savedInstanceState.getInt(PAGE, 0));
    }
  }
  
  @Override
  protected void onResume(){
    super.onResume();
    
    // setup menu button listeners
    mMenuLoginButton.setOnClickListener(new OnClickListener() { 
      @Override
      public void onClick(View v) {
        mViewPager.setCurrentItem(LOGIN_INDEX);
        closeMenu();
      }
    });
    mMenuRegistrationButton.setOnClickListener(new OnClickListener() { 
      @Override
      public void onClick(View v) {
        mViewPager.setCurrentItem(REGISTRATION_INDEX);
        closeMenu();
      }
    });
  }
  
  @Override
  protected void onPause(){
    super.onPause();
    
    // unregister all click listeners
    mMenuLoginButton.setOnClickListener(null);
    mMenuRegistrationButton.setOnClickListener(null);
  }
}