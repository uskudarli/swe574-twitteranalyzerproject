package swe574.g2.twitteranalysis.exec;

import swe574.g2.twitteranalysis.Query;

public class DefaultQueryProcessor implements Processor {

	@Override
	public Query[] process(Query query) {
		return new Query[] { query };
	}

}
