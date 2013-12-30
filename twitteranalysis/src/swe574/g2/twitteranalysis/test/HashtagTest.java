package swe574.g2.twitteranalysis.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import swe574.g2.twitteranalysis.Campaign;
import swe574.g2.twitteranalysis.Hashtag;

public class HashtagTest extends BaseTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testEqualsObject() {
		boolean expectedValue;
		boolean returnValue;

		// Source Hashtag Properties
		int hashtagSourceStart = 1;
		int hashtagSourceEnd = 10;
		String hashtagSourceValue = "value";
		int hashtagSourceId = intIdCounter++;
		
		Hashtag hSource = new Hashtag();
		hSource.setEnd(hashtagSourceEnd);
		hSource.setId(hashtagSourceId);
		hSource.setStart(hashtagSourceStart);
		hSource.setValue(hashtagSourceValue);

		// Target Hashtag Properties
		int hashtagTargetStart = 1;
		int hashtagTargetEnd = 10;
		String hashtagTargetValue = "value";
		int hashtagTargetId = intIdCounter++;
		
		Hashtag hTarget = new Hashtag();
		hTarget.setEnd(hashtagTargetEnd);
		hTarget.setId(hashtagTargetId);
		hTarget.setStart(hashtagTargetStart);
		hTarget.setValue(hashtagTargetValue);
				
		// Target Object is null
		expectedValue = false;
		returnValue = hSource.equals(null);
		assertEquals(expectedValue, returnValue);		
		
		// Target Object is different type instance
		expectedValue = false;
		returnValue = hSource.equals(new Object());
		assertEquals(expectedValue, returnValue);		
		
		// Source Object's value is null;
		hSource.setValue(null);
		expectedValue = false;		
		returnValue = hSource.equals(hTarget);
		assertEquals(expectedValue, returnValue);		
		
		// Target Object's value is null;
		hSource.setValue(hashtagSourceValue);
		hTarget.setValue(null);
		expectedValue = false;		
		returnValue = hSource.equals(hTarget);
		assertEquals(expectedValue, returnValue);
		
		// Same Value, Different Start / End
		hSource.setValue(hashtagSourceValue);
		hTarget.setValue(hashtagTargetValue);
		expectedValue = false;
		hTarget.setStart(11);
		hTarget.setEnd(20);
		assertEquals(expectedValue, returnValue);
		
		// Same Value, Start, End pairs
		hTarget.setStart(hashtagTargetStart);
		hTarget.setEnd(hashtagTargetEnd);
		expectedValue = true;		
		returnValue = hSource.equals(hTarget);
		assertEquals(expectedValue, returnValue);
	}

}
