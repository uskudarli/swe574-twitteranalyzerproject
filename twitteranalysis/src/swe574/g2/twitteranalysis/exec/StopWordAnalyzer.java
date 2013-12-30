package swe574.g2.twitteranalysis.exec;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.PorterStemFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.util.Version;

import swe574.g2.twitteranalysis.dao.StopwordDAO;

public class StopWordAnalyzer extends Analyzer{
	
	private Set<Object> stopset;
	private Connection conn;
	
	  
	  public StopWordAnalyzer(Connection conn, List<String> excluding, List<String> including) throws IOException, SQLException {
	    this.conn = conn;  
		  
	    StopwordDAO stopwordDao = new StopwordDAO();
	    String[] stopwords = stopwordDao.getStopwords(conn);
	    List<String> stopwordsList = new ArrayList<String>(Arrays.asList(stopwords));
	    
	    if(excluding != null && excluding.size() > 0){
	    	stopwordsList.addAll(excluding);
	    }
	    if(including != null && including.size() > 0){
	    	stopwordsList.addAll(including);
	    }
	    
	    stopwords = new String[stopwordsList.size()];
    	stopwordsList.toArray(stopwords);
    	
	    
	    this.stopset = StopFilter.makeStopSet(Version.LUCENE_36, stopwords, true);
	  }
	  
	  @Override
	  public TokenStream tokenStream(String fieldName, Reader reader) {
	    return new PorterStemFilter(
	      new StopFilter(Version.LUCENE_36,
	        new LowerCaseFilter(Version.LUCENE_36,
	          new StandardFilter(Version.LUCENE_36,
	            new StandardTokenizer(Version.LUCENE_36, reader))), stopset));
	  }
}
