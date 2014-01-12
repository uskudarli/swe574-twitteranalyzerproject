package com.example.fragment;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.twitteranalysisandroid.R;

public class ViewReportFragment extends Fragment {
  private static final String CURRENT_SERIES = "current_series";
  private static final String CURRENT_RENDERER = "current_renderer";
  
  private LinearLayout pieChartPlaceholder;
  
  private CategorySeries mSeries = new CategorySeries("");
  private DefaultRenderer mRenderer = new DefaultRenderer();
  private GraphicalView mChartView;

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putSerializable(CURRENT_SERIES, mSeries);
    outState.putSerializable(CURRENT_RENDERER, mRenderer);
  }
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if(savedInstanceState != null){
      mSeries = (CategorySeries) savedInstanceState.getSerializable(CURRENT_SERIES);
      mRenderer = (DefaultRenderer) savedInstanceState.getSerializable(CURRENT_RENDERER);
    }
    
    if(mRenderer == null){
      mRenderer = new DefaultRenderer();
      mRenderer.setDisplayValues(true);
      mRenderer.setStartAngle(270);
      mRenderer.setLabelsColor(Color.BLACK);
      mRenderer.setLabelsTextSize(18);
      mRenderer.setLegendTextSize(18);
    }
    
    if(mSeries == null){
      mSeries = new CategorySeries("");
    }
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    // inflate the layout
    View view = inflater.inflate(R.layout.fragment_view_report, null);
    
    // get references to some view objects
    pieChartPlaceholder = (LinearLayout) view.findViewById(R.id.pie_chart);
    
    showReport();
    
    return view;
  }
  
  @Override
  public void onResume() {
    super.onResume();
    
    if (mChartView == null) {
      mChartView = ChartFactory.getPieChartView(getActivity(), mSeries, mRenderer);
      pieChartPlaceholder.addView(mChartView, new LayoutParams(LayoutParams.MATCH_PARENT,
          LayoutParams.MATCH_PARENT));
    } else {
      mChartView.repaint();
    }
    
    mRenderer.setClickEnabled(true);
    mChartView.setOnClickListener(new PieChartClickListener());
  }
  
  @Override
  public void onPause(){
    super.onPause();
    
    mRenderer.setClickEnabled(false);
    mChartView.setOnClickListener(null);
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
      Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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
