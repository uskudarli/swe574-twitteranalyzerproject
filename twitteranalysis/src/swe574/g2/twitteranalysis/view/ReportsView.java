package swe574.g2.twitteranalysis.view;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.index.TermEnum;
import org.apache.lucene.index.TermFreqVector;
import org.apache.lucene.misc.HighFreqTerms;
import org.apache.lucene.misc.TermStats;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import swe574.g2.twitteranalysis.analysis.SentimentAnalysis;
import swe574.g2.twitteranalysis.dao.TweetDAO;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.Cursor;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.HorizontalAlign;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.Legend;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.PlotOptionsColumn;
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.addon.charts.model.Title;
import com.vaadin.addon.charts.model.Tooltip;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.GradientColor;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.charts.model.style.Style;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class ReportsView extends VerticalLayout implements View {

	public void enter(ViewChangeEvent event) {
		VerticalLayout toolbar = new VerticalLayout();
		toolbar.setWidth("100%");
		toolbar.setHeight("100%");
		toolbar.setSpacing(true);
		toolbar.setMargin(true);
		toolbar.addStyleName("toolbar");
		addComponent(toolbar);

		Label header = new Label("Reports");
		header.addStyleName("h1");
		toolbar.addComponent(header);

		Component pieChart = getPieChart();
		toolbar.addComponent(pieChart);

		Component columnChart = null;
		try {
			columnChart = getMostlyUsedWordsChart();
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		toolbar.addComponent(columnChart);
	}

	protected Component getPieChart() {
		Chart chart = new Chart(ChartType.PIE);

		Configuration conf = chart.getConfiguration();

		conf.setTitle("Tweet Sentiments");

		PlotOptionsPie plotOptions = new PlotOptionsPie();
		plotOptions.setCursor(Cursor.POINTER);
		Labels dataLabels = new Labels();
		dataLabels.setEnabled(true);
		dataLabels.setColor(SolidColor.BLACK);
		dataLabels.setConnectorColor(SolidColor.BLACK);
		dataLabels
				.setFormatter("''+ this.point.name +': '+ this.percentage +' %'");
		plotOptions.setDataLabels(dataLabels);
		conf.setPlotOptions(plotOptions);

		conf.setSeries(getBrowserMarketShareSeries());

		chart.drawChart();
		return chart;
	}

	private DataSeries getBrowserMarketShareSeries() {
		TweetDAO tweetDao = new TweetDAO();
		SentimentAnalysis sentiment = null;
		try {
			sentiment = tweetDao.getSentimentAnalysis(null, null);// TODO: get
																	// it from
																	// the
																	// query.
		} catch (SQLException e) {
			e.printStackTrace();
		}

		double pos = 0.0, neg = 0.0, neu = 0.0;

		if (sentiment != null) {
			int total = sentiment.getNegativeSentimentCount()
					+ sentiment.getPositiveSentimentCount()
					+ sentiment.getNeutralSentimentCount();
			if (total != 0) {
				pos = (double) sentiment.getPositiveSentimentCount() / total;
				neg = (double) sentiment.getNegativeSentimentCount() / total;
			}
		}

		DecimalFormat decim = new DecimalFormat("0.00");
		pos = Double.parseDouble(decim.format(pos));
		neg = Double.parseDouble(decim.format(neg));
		neu = 1 - pos - neg;

		DataSeriesItem positive = new DataSeriesItem("Positive", pos);
		positive.setColor(createRadialGradient(new SolidColor(255, 255, 0),
				new SolidColor(128, 128, 0)));
		positive.setSelected(true);
		positive.setSliced(true);

		DataSeriesItem negative = new DataSeriesItem("Negative", neg);
		negative.setColor(createRadialGradient(new SolidColor(255, 128, 0),
				new SolidColor(128, 64, 0)));

		DataSeriesItem neutral = new DataSeriesItem("Neutral", neu);
		neutral.setColor(createRadialGradient(new SolidColor(0, 128, 0),
				new SolidColor(0, 64, 0)));

		return new DataSeries(positive, negative, neutral);
	}

	/**
	 * Creates a radial gradient with the specified start and end colors.
	 */
	private GradientColor createRadialGradient(SolidColor start, SolidColor end) {
		GradientColor color = GradientColor.createRadial(0.5, 0.3, 0.7);
		color.addColorStop(0, start);
		color.addColorStop(1, end);
		return color;
	}

	private Component getMostlyUsedWordsChart() throws CorruptIndexException,
			IOException {
		Chart chart = new Chart(ChartType.COLUMN);

		Configuration conf = chart.getConfiguration();
		conf.getChart().setMargin(50, 80, 100, 50);

		conf.setTitle(new Title("Mostly Used Words in Tweets"));

		TermStats[] stats = getTermStatsForGraph();
		int count = 40; // TODO change it
		String[] words = new String[count];
		Number[] freqs = new Number[count];
		for (int i = 0; i < count; i++) {
			if (i == stats.length)
				break;
			words[i] = stats[i].term.text();
			freqs[i] = (int) stats[i].totalTermFreq;
		}

		XAxis xAxis = new XAxis();
		xAxis.setCategories(words);
		Labels labels = new Labels();
		labels.setRotation(-45);
		labels.setAlign(HorizontalAlign.RIGHT);
		Style style = new Style();
		style.setFontSize("13px");
		style.setFontFamily("Verdana, sans-serif");
		labels.setStyle(style);
		xAxis.setLabels(labels);
		conf.addxAxis(xAxis);

		YAxis yAxis = new YAxis();
		yAxis.setMin(0);
		yAxis.setTitle(new Title("Word Frequency"));
		conf.addyAxis(yAxis);

		Legend legend = new Legend();
		legend.setEnabled(false);
		conf.setLegend(legend);

		Tooltip tooltip = new Tooltip();
		tooltip.setFormatter("''+ this.x +''+'Frequency: '"
				+ "+ Highcharts.numberFormat(this.y, 1)");
		conf.setTooltip(tooltip);

		ListSeries serie = new ListSeries("Frequency", freqs);
		Labels dataLabels = new Labels();
		dataLabels.setEnabled(true);
		dataLabels.setRotation(-90);
		dataLabels.setColor(new SolidColor(255, 255, 255));
		dataLabels.setAlign(HorizontalAlign.RIGHT);
		dataLabels.setX(4);
		dataLabels.setY(10);
		dataLabels.setFormatter("this.y");
		style = new Style();
		style.setFontSize("13px");
		style.setFontFamily("Verdana, sans-serif");
		dataLabels.setStyle(style);
		PlotOptionsColumn plotOptionsColumn = new PlotOptionsColumn();
		plotOptionsColumn.setDataLabels(dataLabels);
		serie.setPlotOptions(plotOptionsColumn);
		conf.addSeries(serie);

		chart.drawChart(conf);
		return chart;
	}

	private TermStats[] getTermStatsForGraph() throws IOException {
		TweetDAO tweetDao = new TweetDAO();
		IndexWriter indexWriter = null;
		Directory ramDir = new RAMDirectory();
		TermStats[] stats = null;

		try {
			indexWriter = tweetDao.getLuceneDocument(null, null, ramDir);
		} catch (SQLException e) {
			e.printStackTrace();
		} // TODO add query id and campaign id here
		if (indexWriter != null) {

			IndexReader indexReader = IndexReader.open(ramDir);
			TermDocs termDocs = indexReader.termDocs();
			TermEnum terms = indexReader.terms();

			System.out.println(termDocs.freq());

			for (int docNum = 0; docNum < indexReader.maxDoc(); docNum++) {
				if (indexReader.isDeleted(docNum)) {
					continue;
				}
				TermFreqVector tfv = indexReader.getTermFreqVector(docNum,
						"content");
				if (tfv != null) {
					try {
						int termCount = tfv.getTerms().length;
						stats = HighFreqTerms.sortByTotalTermFreq(indexReader,
								HighFreqTerms.getHighFreqTerms(indexReader,
										(int) termCount, "content"));

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		return stats;
	}
}
