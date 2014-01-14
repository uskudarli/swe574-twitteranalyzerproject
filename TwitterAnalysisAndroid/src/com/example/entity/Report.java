package com.example.entity;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Report extends SimpleEntity {

  private static final String POSITIVE_FREQS = "positive_freqs";
  private static final String NEGATIVE_FREQS = "negative_freqs";
  private static final String NEUTRAL_FREQS = "neutral_freqs";
  private static final String ALL_FREQS = "all_freqs";

  public Report(JSONObject object) {
    super(object);
  }
  
  public List<WordFrequency> getPositive() {
    return getList(POSITIVE_FREQS);
  }
  
  public List<WordFrequency> getNegative() {
    return getList(NEGATIVE_FREQS);
  }
  
  public List<WordFrequency> getNeutral() {
    return getList(NEUTRAL_FREQS);
  }

  
  public List<WordFrequency> getAll() {
    return getList(ALL_FREQS);
  }
  
  private List<WordFrequency> getList(String key){
    JSONArray wordFrequencies = getArray(key);
    if (wordFrequencies == null) {
      return null;
    }

    List<WordFrequency> result = new LinkedList<WordFrequency>();
    for (int i = 0; i < wordFrequencies.length(); ++i) {
      try {
        result.add(new WordFrequency(wordFrequencies.getJSONObject(i)));
      } catch (JSONException e) {
        return null;
      }
    }

    return result;
  }

}
