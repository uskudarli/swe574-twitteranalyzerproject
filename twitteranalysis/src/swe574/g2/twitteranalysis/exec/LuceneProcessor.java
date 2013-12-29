package swe574.g2.twitteranalysis.exec;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.index.TermFreqVector;
import org.apache.lucene.misc.HighFreqTerms;
import org.apache.lucene.misc.TermStats;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import swe574.g2.twitteranalysis.Tweet;
import swe574.g2.twitteranalysis.WordFreqs;
import swe574.g2.twitteranalysis.dao.WordFreqsDAO;

public class LuceneProcessor {
	private static final int STAT_WORD_COUNT = 50;
	
	private Connection connection;

	private Directory ramdir;

	private Document doc;

	private IndexWriter writer;
	
	private int queryId;
	
	private int campaignId;

	public LuceneProcessor(Connection connection, int campaignId, int queryId, List<String> excluding, List<String> including) throws Exception {
		this.connection = connection;
		this.ramdir = new RAMDirectory();
		this.writer = new IndexWriter(ramdir, new IndexWriterConfig(
				Version.LUCENE_36, new StopWordAnalyzer(connection, excluding, including)));
		this.doc = new Document();
		
		this.queryId = queryId;
		
		this.campaignId = campaignId;
	}

	public void buildIndex(Tweet tweet) throws Exception {
		Field field;

		if ("pos".equals(tweet.getSentiment())) {
			field = new Field("pos_content", tweet.getContent(),
					Field.Store.NO, Field.Index.ANALYZED, Field.TermVector.YES);
		} else if ("neg".equals(tweet.getSentiment())) {
			field = new Field("neg_content", tweet.getContent(),
					Field.Store.NO, Field.Index.ANALYZED, Field.TermVector.YES);
		} else {
			field = new Field("neu_content", tweet.getContent(),
					Field.Store.NO, Field.Index.ANALYZED, Field.TermVector.YES);
		}

		doc.add(field);
		doc.add(new Field("all_content", tweet.getContent(), Field.Store.NO,
				Field.Index.ANALYZED, Field.TermVector.YES));

		writer.addDocument(doc);
	}

	public void finalizeProcess() throws IOException {
		writer.commit();
		writer.close();

		TermStats[] allStats, posStats, negStats, neuStats;

		IndexReader indexReader = IndexReader.open(ramdir);
		TermDocs termDocs = indexReader.termDocs();

		for (int docNum = 0; docNum < indexReader.maxDoc(); docNum++) {
			if (indexReader.isDeleted(docNum)) {
				continue;
			}
			TermFreqVector tfvPos = indexReader.getTermFreqVector(docNum,
					"pos_content");
			TermFreqVector tfvNeg = indexReader.getTermFreqVector(docNum,
					"neg_content");
			TermFreqVector tfvNeu = indexReader.getTermFreqVector(docNum,
					"neu_content");
			TermFreqVector tfvAll = indexReader.getTermFreqVector(docNum,
					"all_content");

			try {
				if(tfvPos != null){
					posStats = HighFreqTerms.sortByTotalTermFreq(indexReader,
						HighFreqTerms.getHighFreqTerms(indexReader,
								(int) tfvPos.getTerms().length, "pos_content"));
					saveStats(posStats, "pos");
				}
				
				if(tfvNeg != null){
					negStats = HighFreqTerms.sortByTotalTermFreq(indexReader,
						HighFreqTerms.getHighFreqTerms(indexReader,
								(int) tfvNeg.getTerms().length, "neg_content"));
					
					saveStats(negStats, "neg");
				}
				if(tfvNeu != null){
					neuStats = HighFreqTerms.sortByTotalTermFreq(indexReader,
						HighFreqTerms.getHighFreqTerms(indexReader,
								(int) tfvNeu.getTerms().length, "neu_content"));
					
					saveStats(neuStats, "neu");
				}
				if(tfvAll != null){
					allStats = HighFreqTerms.sortByTotalTermFreq(indexReader,
						HighFreqTerms.getHighFreqTerms(indexReader,
								(int) tfvAll.getTerms().length, "all_content"));
					
					saveStats(allStats, "all");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void saveStats(TermStats[] stats, String sentiment) throws SQLException{
		WordFreqsDAO wordFreqsDao = new WordFreqsDAO();
		int size = stats.length;
		int max = size < STAT_WORD_COUNT? size : STAT_WORD_COUNT;
		
		for(int i = 0; i < max; i++){
			WordFreqs wordFreqs = new WordFreqs();
			wordFreqs.setCampaignId(campaignId);
			wordFreqs.setQueryId(queryId);
			wordFreqs.setFrequency((int)stats[i].totalTermFreq);
			wordFreqs.setSentiment(sentiment);
			wordFreqs.setWord(stats[i].term.text());
			
			try {
				wordFreqsDao.save(connection, wordFreqs);
				
			} catch (Exception e) {
			}
		}
	}

}
