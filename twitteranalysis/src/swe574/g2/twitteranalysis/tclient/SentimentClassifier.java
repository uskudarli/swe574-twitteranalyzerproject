package swe574.g2.twitteranalysis.tclient;

import java.io.File;
import java.io.IOException;

import com.aliasi.classify.ConditionalClassification;
import com.aliasi.classify.LMClassifier;
import com.aliasi.util.AbstractExternalizable;

public class SentimentClassifier {
	
	private static SentimentClassifier instance = new SentimentClassifier();
	
	private String[] categories;
	private LMClassifier classifier;

	private SentimentClassifier() {
		try {
			classifier = (LMClassifier) AbstractExternalizable
					.readObject(new File(getClass().getResource("classifier.txt").getPath()));
			categories = classifier.categories();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static SentimentClassifier getInstance() {
		return instance;
	}

	public synchronized String classify(String text) {
		ConditionalClassification classification = classifier.classify(text);
		return classification.bestCategory();
	}
}