package com.example.fragment;

import static com.example.helper.Constants.DEBUG;

import java.util.List;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.entity.Campaign;
import com.example.entity.Query;
import com.example.entity.Report;
import com.example.entity.TAError;
import com.example.entity.WordFrequency;
import com.example.helper.WarningDialogBuilder;
import com.example.twitteranalysisandroid.R;
import com.example.webservice.CampaignService;
import com.example.webservice.FindCallback;
import com.example.webservice.GetCallback;
import com.example.webservice.ReportService;

public class ViewReportFragment extends Fragment {
//  private static final String CURRENT_SERIES = "current_series";
//  private static final String CURRENT_RENDERER = "current_renderer";
  
//  private LinearLayout pieChartPlaceholder;
  
//  private CategorySeries mSeries = new CategorySeries("");
//  private DefaultRenderer mRenderer = new DefaultRenderer();
//  private GraphicalView mChartView;
  
  private Spinner mCampaignSpinner;
  private Spinner mQuerySpinner;
  
  private List<Campaign> campaigns;
  private List<Query> queries;
  private Campaign currentCampaign;
  private Query currentQuery;
  
  private TableLayout positivePlaceholder;
  private TableLayout negativePlaceholder;
  private TableLayout neutralPlaceholder;
  private TableLayout allPlaceholder;

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
//    outState.putSerializable(CURRENT_SERIES, mSeries);
//    outState.putSerializable(CURRENT_RENDERER, mRenderer);
  }
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

//    if(savedInstanceState != null){
//      mSeries = (CategorySeries) savedInstanceState.getSerializable(CURRENT_SERIES);
//      mRenderer = (DefaultRenderer) savedInstanceState.getSerializable(CURRENT_RENDERER);
//    }
    
//    if(mRenderer == null){
//      mRenderer = new DefaultRenderer();
//      mRenderer.setDisplayValues(true);
//      mRenderer.setStartAngle(270);
//      mRenderer.setLabelsColor(Color.BLACK);
//      mRenderer.setLabelsTextSize(18);
//      mRenderer.setLegendTextSize(18);
//    }
    
//    if(mSeries == null){
//      mSeries = new CategorySeries("");
//    }
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    // inflate the layout
    View view = inflater.inflate(R.layout.fragment_view_report, null);
    
    // get references to some view objects
    mCampaignSpinner = (Spinner) view.findViewById(R.id.spinner_campaign_name);
    mQuerySpinner = (Spinner) view.findViewById(R.id.spinner_query_number);
//    pieChartPlaceholder = (LinearLayout) view.findViewById(R.id.pie_chart);
    positivePlaceholder = (TableLayout) view.findViewById(R.id.positive_frequency);
    negativePlaceholder = (TableLayout) view.findViewById(R.id.negative_frequency);
    neutralPlaceholder = (TableLayout) view.findViewById(R.id.neutral_frequency);
    allPlaceholder = (TableLayout) view.findViewById(R.id.all_frequency);
    
    return view;
  }
  
  @Override
  public void onResume() {
    super.onResume();
    
//    if (mChartView == null) {
//      mChartView = ChartFactory.getPieChartView(getActivity(), mSeries, mRenderer);
//      pieChartPlaceholder.addView(mChartView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//    } else {
//      mChartView.repaint();
//    }
    
//    mRenderer.setClickEnabled(true);
//    mChartView.setOnClickListener(new PieChartClickListener());
    
    setupCampaignNames();
    
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
        showReport();
      }

      @Override
      public void onNothingSelected(AdapterView<?> arg0) {
      }
    });
  }
  
  @Override
  public void onPause(){
    super.onPause();
    
//    mRenderer.setClickEnabled(false);
//    mChartView.setOnClickListener(null);
    
    mCampaignSpinner.setOnItemSelectedListener(null);
    mQuerySpinner.setOnItemSelectedListener(null);
  }
  
//  private class PieChartClickListener implements OnClickListener {
//    @Override
//    public void onClick(View v) {
//      SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
//      if (seriesSelection == null) {
//        return;
//      }
//      
//      for (int i=0; i<mSeries.getItemCount(); i++) {
//        mRenderer.getSeriesRendererAt(i).setHighlighted(i == seriesSelection.getPointIndex());
//      }
//      mChartView.repaint();
//      
//      String message = mSeries.getCategory(seriesSelection.getPointIndex()) + ": " + seriesSelection.getValue() + "%";
//      Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//    }
//  }
  
  private void setupCampaignNames() {
    currentCampaign = null;

//    final ProgressDialog loadingDialog = new LoadingDialog(getActivity());
//    loadingDialog.show();
    
    new CampaignService().get(new FindCallback<Campaign>() {
      @Override
      public void onError(TAError pError) {
//        loadingDialog.dismiss();
        
        if(DEBUG){
          new WarningDialogBuilder(getActivity())
            .setTitle(getString(R.string.txt_error))
            .setMessage(pError.getMessage())
            .show();
        } else {
          new WarningDialogBuilder(getActivity())
              .setTitle(getString(R.string.txt_error))
              .setMessage(getString(R.string.txt_error_get))
              .show();
        }
      }

      @Override
      public void onFind(List<Campaign> pObjects) {
//        loadingDialog.dismiss();
        
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
    showReport();
  }
  
  private void setupCampaign() {
    clearCampaign();
    
    if(currentCampaign == null){
      return;
    }
    
//    final ProgressDialog loadingDialog = new LoadingDialog(getActivity());
//    loadingDialog.show();

    currentCampaign.getQueries(
        new FindCallback<Query>() {
          @Override
          public void onError(TAError pError) {
            new WarningDialogBuilder(getActivity())
                .setTitle(getString(R.string.txt_error))
                .setMessage(pError.getMessage()).show();

//            loadingDialog.dismiss();
          }

          @Override
          public void onFind(List<Query> pObjects) {
//            loadingDialog.dismiss();
            
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
            
//            showReport();
          }
        });
  }
  
  private void clearCharts(){
    positivePlaceholder.removeAllViews();
    negativePlaceholder.removeAllViews();
    neutralPlaceholder.removeAllViews();
    allPlaceholder.removeAllViews();
  }
  
  private void showReport(){
//    int[] colors = new int[] { Color.rgb(190,190,0), Color.rgb(200,100,0), Color.rgb(0,120,0)};
//    String[] series = new String[] {getString(R.string.txt_positive), getString(R.string.txt_negative), getString(R.string.txt_neutral)};
//    double[] values = new double[] {29, 16, 55};
//    
//    mSeries.clear();
//    mRenderer.removeAllRenderers();
//    
//    for(int i=0; i<series.length; ++i){
//      mSeries.add(series[i], values[i]);
//      SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
//      renderer.setColor(colors[i]);
//      mRenderer.addSeriesRenderer(renderer);
//    }
//    
//    if(mChartView != null){
//      mChartView.repaint();
//    }
    
    clearCharts();
    
    if(currentCampaign == null || currentQuery == null){
      return;
    }
    
//    final ProgressDialog loadingDialog = new LoadingDialog(getActivity());
//    loadingDialog.show();
    
    new ReportService().get(Long.toString(currentCampaign.getId()), Long.toString(currentQuery.getId()),
        new GetCallback<Report>(){
          @Override
          public void onError(TAError pError) {
//            loadingDialog.dismiss();
            
            if(DEBUG){
              new WarningDialogBuilder(getActivity())
                .setTitle(getString(R.string.txt_error))
                .setMessage(pError.getMessage())
                .show();
            } else {
              new WarningDialogBuilder(getActivity())
                .setTitle(getString(R.string.txt_error))
                .setMessage(getString(R.string.txt_error_get))
                .show();
            }
          }

          @Override
          public void onGet(Report pObject) {
//            loadingDialog.dismiss();
            
            displayReport(pObject);
          }
        });
  }
  
  private void displayReport(Report report){
    if(report == null){
      return;
    }
    
    if(report.getPositive().size()>0){
//      GraphicalView posChart = getBarChart(getString(R.string.txt_positive), report.getPositive());
//      positivePlaceholder.addView(posChart, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//      posChart.repaint();
      addToTable(positivePlaceholder, getString(R.string.txt_positive), report.getPositive());
    }
    if(report.getNegative().size()>0){
//      GraphicalView negChart = getBarChart(getString(R.string.txt_negative), report.getNegative());
//      negativePlaceholder.addView(negChart, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//      negChart.repaint();
      addToTable(negativePlaceholder, getString(R.string.txt_negative), report.getNegative());
    }
    if(report.getNeutral().size()>0){
//      GraphicalView neutChart = getBarChart(getString(R.string.txt_neutral), report.getNeutral());
//      neutralPlaceholder.addView(neutChart, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//      neutChart.repaint();
      addToTable(neutralPlaceholder, getString(R.string.txt_neutral), report.getNeutral());
    }
    if(report.getAll().size()>0){
//      GraphicalView allChart = getBarChart(getString(R.string.txt_all), report.getAll());
//      allPlaceholder.addView(allChart, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//      allChart.repaint();
      addToTable(allPlaceholder, getString(R.string.txt_all), report.getAll());
    }
  }
  
  private void addToTable(TableLayout table, String title, List<WordFrequency> frequencies){
    table.setStretchAllColumns(true);
    table.bringToFront();
    
    TableRow titleRow =  new TableRow(getActivity());
    TextView tableTitle = new TextView(getActivity());
    tableTitle.setTypeface(null, Typeface.BOLD);
    tableTitle.setText(title);
    titleRow.addView(tableTitle);
    table.addView(titleRow);
    
    TableRow headerRow =  new TableRow(getActivity());
    TextView wordTitle = new TextView(getActivity());
    wordTitle.setText(getString(R.string.txt_word));
    wordTitle.setTypeface(null, Typeface.BOLD_ITALIC);
    TextView freqTitle = new TextView(getActivity());
    freqTitle.setText(getString(R.string.txt_frequency));
    freqTitle.setTypeface(null, Typeface.BOLD_ITALIC);
    headerRow.addView(wordTitle);
    headerRow.addView(freqTitle);
    table.addView(headerRow);
    
    int count = 0;
    for(WordFrequency frequency : frequencies){
        TableRow tr =  new TableRow(getActivity());
        TextView wordCell = new TextView(getActivity());
        wordCell.setText(frequency.getWord());
        TextView freqCell = new TextView(getActivity());
        freqCell.setText(Integer.toString(frequency.getFrequency()));
        tr.addView(wordCell);
        tr.addView(freqCell);
        table.addView(tr);
        ++count;
        
        if(count>25){
          break;
        }
    }
  }
  
//  private XYMultipleSeriesRenderer buildBarRenderer(int[] colors) {
//    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
//    renderer.setAxisTitleTextSize(16);
//    renderer.setChartTitleTextSize(20);
//    renderer.setLabelsTextSize(15);
//    renderer.setLegendTextSize(15);
//    int length = colors.length;
//    for (int i = 0; i < length; i++) {
//      SimpleSeriesRenderer r = new SimpleSeriesRenderer();
//      r.setColor(colors[i]);
//      renderer.addSeriesRenderer(r);
//    }
//    return renderer;
//  }
//  
//  private GraphicalView getBarChart(String title, List<WordFrequency> list){    
//    int[] colors = new int[] { Color.BLUE };
//    XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
//    renderer.setOrientation(Orientation.VERTICAL);
//    renderer.setXLabels(1);
//    renderer.setYLabels(10);
//    
//    double[] values = new double[list.size()];
//    
//    int index = 0;
//    for(WordFrequency frequency : list){
//      values[index] = frequency.getFrequency();
//      renderer.addXTextLabel(index+1, frequency.getWord());
//      ++index;
//      if(index>=10){
//        break;
//      }
//    }
//    
//    int length = renderer.getSeriesRendererCount();
//    for (int i = 0; i < length; i++) {
//      SimpleSeriesRenderer seriesRenderer = renderer.getSeriesRendererAt(i);
//      seriesRenderer.setDisplayChartValues(true);
//    }
//    
//    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
//    XYSeries series = new XYSeries(title);
//    int seriesLength = values.length;
//    if(seriesLength>9){
//      seriesLength = 9;
//    }
//    for (int k = 0; k < seriesLength; k++) {
//      series.add(k, values[k]);
//    }
//    dataset.addSeries(series);
//    
//    GraphicalView chart = ChartFactory.getBarChartView(getActivity(), dataset, renderer, BarChart.Type.DEFAULT);
//    return chart;
//  }
}
