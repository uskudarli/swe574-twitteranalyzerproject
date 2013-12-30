package com.example.activity;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.entity.User;
import com.example.twitteranalysisandroid.R;

public class ReportsActivity extends Activity {
  private static final String CURRENT_SERIES = "current_series";
  private static final String CURRENT_RENDERER = "current_renderer";
  
  private CategorySeries mSeries = new CategorySeries("");
  private DefaultRenderer mRenderer = new DefaultRenderer();
  private GraphicalView mChartView;
  
  @Override
  protected void onRestoreInstanceState(Bundle savedState) {
    super.onRestoreInstanceState(savedState);
    mSeries = (CategorySeries) savedState.getSerializable(CURRENT_SERIES);
    mRenderer = (DefaultRenderer) savedState.getSerializable(CURRENT_RENDERER);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putSerializable(CURRENT_SERIES, mSeries);
    outState.putSerializable(CURRENT_RENDERER, mRenderer);
  }
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_report);
    
    // if no logged in user found, then finish immediately
    if(User.getCurrentUser() == null){
      finish();
    }

    //mRenderer.setZoomButtonsVisible(true);
    mRenderer.setDisplayValues(true);
    mRenderer.setStartAngle(270);
    mRenderer.setLabelsColor(Color.BLACK);
    mRenderer.setLabelsTextSize(18);
    mRenderer.setLegendTextSize(18);
    
    showReport();
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu items for use in the action bar
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu, menu);
    return super.onCreateOptionsMenu(menu);
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle presses on the action bar items
    switch (item.getItemId()) {
    case R.id.menu_query:
      // start query activity
      Intent queryIntent = new Intent(this, QueryActivity.class);
      startActivity(queryIntent);
      finish();
      return true;
    case R.id.menu_reports:
     // this is the current activity so return
      return true;
    case R.id.menu_settings:
      // start settings activity
      Intent settingsIntent = new Intent(this, SettingsActivity.class);
      startActivity(settingsIntent);
      finish();
      return true;
    default:
      return super.onOptionsItemSelected(item);
    }
  }
  
  @Override
  protected void onResume() {
    super.onResume();
    
    if (mChartView == null) {
      LinearLayout layout = (LinearLayout) findViewById(R.id.pie_chart);
      mChartView = ChartFactory.getPieChartView(this, mSeries, mRenderer);
      mRenderer.setClickEnabled(true);
      mChartView.setOnClickListener(new PieChartClickListener());
      layout.addView(mChartView, new LayoutParams(LayoutParams.MATCH_PARENT,
          LayoutParams.MATCH_PARENT));
    } else {
      mChartView.repaint();
    }
  }
  
  private class PieChartClickListener implements OnClickListener {
    @Override
    public void onClick(View v) {
      SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
      if (seriesSelection == null) {
        return;
      }
      
      for (int i=0; i<mSeries.getItemCount(); i++) {
        mRenderer.getSeriesRendererAt(i).setHighlighted(i == seriesSelection.getPointIndex());
      }
      mChartView.repaint();
      
      String message = mSeries.getCategory(seriesSelection.getPointIndex()) + ": " + seriesSelection.getValue() + "%";
      Toast.makeText(ReportsActivity.this, message, Toast.LENGTH_SHORT).show();
    }
  }
  
  private void showReport(){
    int[] colors = new int[] { Color.rgb(190,190,0), Color.rgb(200,100,0), Color.rgb(0,120,0)};
    String[] series = new String[] {getString(R.string.txt_positive), getString(R.string.txt_negative), getString(R.string.txt_neutral)};
    double[] values = new double[] {29, 16, 55};
    
    mSeries.clear();
    mRenderer.removeAllRenderers();
    
    for(int i=0; i<series.length; ++i){
      mSeries.add(series[i], values[i]);
      SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
      renderer.setColor(colors[i]);
      mRenderer.addSeriesRenderer(renderer);
    }
    
    if(mChartView != null){
      mChartView.repaint();
    }
  }
}
