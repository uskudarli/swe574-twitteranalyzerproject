package swe574.g2.twitteranalysis.exec;

import swe574.g2.twitteranalysis.Query;

public interface Processor {
	public Query[] process(Query query);
}
