package swe574.g2.twitteranalysis.analysis;

public class KeywordAnalysis implements Analysis {
	
	private int id;
	private QueryAnalysis queryAnalysis;
	
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

	public KeywordDetail[] getKeywordDetails(int sIndex, int count) {
		return null;
	}
	
	public KeywordDetail getKeywordDetail(int id) {
		return null;
	}
	
}
