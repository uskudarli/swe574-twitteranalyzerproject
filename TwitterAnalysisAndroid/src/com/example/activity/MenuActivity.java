package com.example.activity;

import java.util.LinkedList;
import java.util.List;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;

public class MenuActivity extends FragmentActivity {
  
  protected static final String PAGE = "PAGE";
  
  protected ViewPager mViewPager;
  protected View mToggleMenuButton;
  protected View mMenu;
  protected View mContent;
  protected List<ObjectAnimator> menuAnimators;
  
  @Override
  protected void onResume(){
    super.onResume();
    
    // setup menu toggle listener
    mToggleMenuButton.setOnClickListener(new OnClickListener() { 
      @Override
      public void onClick(View v) {
        toggleMenu();
      }
    });
  }
  
  @Override
  protected void onPause(){
    super.onPause();
    
    // unregister all click listeners
    mToggleMenuButton.setOnClickListener(null);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    
    // save the current page number
    outState.putInt(PAGE, mViewPager.getCurrentItem());
  }
  
  @Override
  public void onBackPressed(){
    // if menu is open then close menu, otherwise end activity
    if(isMenuOpen()){
      closeMenu();
    } else {
      // will most likely end activity
      super.onBackPressed();
    }
  }
  
  /***
   * Checks if the menu is open
   * @return whether or not menu is open
   */
  protected boolean isMenuOpen(){
    // check if an open animator exists
    return menuAnimators != null;
  }
  
  /***
   * Toggles menu
   */
  protected void toggleMenu(){
    // if menu is open then close it, otherwise open it
    if(isMenuOpen()){
      closeMenu();
    } else {
      openMenu();
    }
  }
  
  /***
   * Animate the main content view to the right so that menu becomes visible
   */
  protected void openMenu(){
    // check if already open
    if(isMenuOpen()){
      return;
    }
    
    // get the width of the menu
    float translateX = mMenu.getWidth();
    // set an animation duration, 200ms seems to give the best experience
    long animationDuration = 200;
    
    // prepare animators
    menuAnimators = new LinkedList<ObjectAnimator>();
    // animate the content view to the right to reveal the menu
    menuAnimators.add(ObjectAnimator.ofFloat(mContent, "translationX", 0, translateX).setDuration(animationDuration));
    menuAnimators.add(ObjectAnimator.ofFloat(mMenu, "translationX", -translateX, 0).setDuration(animationDuration));

    // start the animations
    for(ObjectAnimator objectAnimator : menuAnimators){
      objectAnimator.start();
    }
  }
  
  /***
   * Moves the content view back to left to cover and hide the menu
   */
  protected void closeMenu(){
    // check if already closed
    if(!isMenuOpen()){
      return;
    }
    
    // if menu is open then an animator exists that opened the menu
    // reverse that animator to close the menu
    for(ObjectAnimator objectAnimator : menuAnimators){
      objectAnimator.reverse();
    }
    
    // make sure to set animators to null so that menu is indicated as closed
    menuAnimators = null;
  }
}
