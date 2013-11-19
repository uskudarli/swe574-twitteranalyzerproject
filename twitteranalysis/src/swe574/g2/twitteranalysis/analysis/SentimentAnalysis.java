package swe574.g2.twitteranalysis.analysis;

public class SentimentAnalysis implements Analysis {
	private int id;
	private QueryAnalysis queryAnalysis;
	private KeywordAnalysis keywordAnalysis;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public QueryAnalysis getQueryAnalysis() {
		return queryAnalysis;
	}
	public void setQueryAnalysis(QueryAnalysis queryAnalysis) {
		this.queryAnalysis = queryAnalysis;
	}
	public KeywordAnalysis getKeywordAnalysis() {
		return keywordAnalysis;
	}
	public void setKeywordAnalysis(KeywordAnalysis keywordAnalysis) {
		this.keywordAnalysis = keywordAnalysis;
	}
	
	
}
