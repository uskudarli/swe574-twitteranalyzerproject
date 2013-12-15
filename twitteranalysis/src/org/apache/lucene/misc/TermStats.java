package org.apache.lucene.misc;

import org.apache.lucene.index.Term;

public final class TermStats {
	public Term term;
	public int docFreq;
	public long totalTermFreq;

	public TermStats(Term t, int df) {
		this.term = t;
		this.docFreq = df;
	}

	public TermStats(Term t, int df, long tf) {
		this.term = t;
		this.docFreq = df;
		this.totalTermFreq = tf;
	}
}
