package swe574.g2.twitteranalysis.test;

import org.junit.After;
import org.junit.Before;

import com.ibm.icu.util.Calendar;

public class BaseTest {
	protected int intIdCounter;
	protected long longIdCounter;
	protected Calendar cal;
	
	@Before
	public void setUpBeforeMethod() throws Exception {
		cal = Calendar.getInstance();
		intIdCounter = 1;
		longIdCounter = 101;
	}
	
	@After
	public void tearDownAfterMethod() throws Exception {
		
	}
}
