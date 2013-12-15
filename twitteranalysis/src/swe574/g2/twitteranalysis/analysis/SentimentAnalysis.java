package swe574.g2.twitteranalysis.analysis;

public class SentimentAnalysis implements Analysis {
	private int id;
	private QueryAnalysis queryAnalysis;
	private KeywordAnalysis keywordAnalysis;
	private int positiveSentimentCount;
	private int negativeSentimentCount;
	private int neutralSentimentCount;
	
	
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
	public int getPositiveSentimentCount() {
		return positiveSentimentCount;
	}
	public void setPositiveSentimentCount(int positiveSentimentCount) {
		this.positiveSentimentCount = positiveSentimentCount;
	}
	public int getNegativeSentimentCount() {
		return negativeSentimentCount;
	}
	public void setNegativeSentimentCount(int negativeSentimentCount) {
		this.negativeSentimentCount = negativeSentimentCount;
	}
	public int getNeutralSentimentCount() {
		return neutralSentimentCount;
	}
	public void setNeutralSentimentCount(int neutralSentimentCount) {
		this.neutralSentimentCount = neutralSentimentCount;
	}
	
	
}
