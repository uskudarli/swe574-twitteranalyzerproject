package swe574.g2.twitteranalysis.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import swe574.g2.twitteranalysis.Campaign;

public class CampaignTest extends BaseTest {

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
		
		// Source Campaing Properties
		String campaignSourceDescription = "campaign desciption";
		int campaignSourceId = intIdCounter++;
		String campaignSourceName = "campaign name";
		int campaignSourceOwnerUserId = intIdCounter++;
		
		Campaign cSource = new Campaign();
		cSource.setDescription(campaignSourceDescription);
		cSource.setId(campaignSourceId);
		cSource.setName(campaignSourceName);
		cSource.setOwnerUserId(campaignSourceOwnerUserId);		

		String campaignTargetDescription = "campaign desciption";
		int campaignTargetId = intIdCounter++;
		String campaignTargetName = "campaign name";
		int campaignTargetOwnerUserId = intIdCounter++;

		Campaign cTarget = new Campaign();
		cTarget.setDescription(campaignTargetDescription);
		cTarget.setId(campaignTargetId);
		cTarget.setName(campaignTargetName);
		cTarget.setOwnerUserId(campaignTargetOwnerUserId);
		
		// Target Object is null
		expectedValue = false;
		returnValue = cSource.equals(null);
		assertEquals(expectedValue, returnValue);		
		
		// Target Object is different type instance
		expectedValue = false;
		returnValue = cSource.equals(new Object());
		assertEquals(expectedValue, returnValue);		
		
		// Source Object's name is null;
		cSource.setName(null);
		expectedValue = false;		
		returnValue = cSource.equals(cTarget);
		assertEquals(expectedValue, returnValue);
		
		// Same Name
		cSource.setName(campaignSourceName);
		expectedValue = true;
		returnValue = cSource.equals(cTarget);
		assertEquals(expectedValue, returnValue);
		
		// Same Name Pointers
		cSource.setName(campaignSourceName);
		cTarget.setName(campaignSourceName);
		expectedValue = true;		
		returnValue = cSource.equals(cTarget);
		assertEquals(expectedValue, returnValue);
	}

	
	/*
	 * CompareToTests
	 */
	@Test
	public void testCompareTo() {
		int expectedValue;
		int nonExpectedValue;
		int returnValue;
		
		// Source Campaing Properties
		String campaignSourceDescription = "campaign desciption";
		int campaignSourceId = intIdCounter++;
		String campaignSourceName = "campaign name";
		int campaignSourceOwnerUserId = intIdCounter++;
		
		Campaign cSource = new Campaign();
		cSource.setDescription(campaignSourceDescription);
		cSource.setId(campaignSourceId);
		cSource.setName(campaignSourceName);
		cSource.setOwnerUserId(campaignSourceOwnerUserId);		

		String campaignTargetDescription = "campaign desciption";
		int campaignTargetId = intIdCounter++;
		String campaignTargetName = "campaign name";
		int campaignTargetOwnerUserId = intIdCounter++;

		Campaign cTarget = new Campaign();
		cTarget.setDescription(campaignTargetDescription);
		cTarget.setId(campaignTargetId);
		cTarget.setName(campaignTargetName);
		cTarget.setOwnerUserId(campaignTargetOwnerUserId);
		
		// Target Object is null
		expectedValue = -1;
		returnValue = cSource.compareTo(null);
		assertEquals(expectedValue, returnValue);	
		
		// Source Object's name is null;
		cSource.setName(null);
		expectedValue = -1;		
		returnValue = cSource.compareTo(cTarget);
		assertEquals(expectedValue, returnValue);
		
		// Same Name
		cSource.setName(campaignSourceName);
		expectedValue = 0;
		returnValue = cSource.compareTo(cTarget);
		assertEquals(expectedValue, returnValue);
		
		// Same Name Pointers
		cSource.setName(campaignSourceName);
		cTarget.setName(campaignSourceName);
		expectedValue = 0;		
		returnValue = cSource.compareTo(cTarget);
		assertEquals(expectedValue, returnValue);
		
		// Different Name
		cSource.setName(campaignSourceName + " Some Text");
		cTarget.setName(campaignTargetName);
		nonExpectedValue = 0;
		returnValue = cSource.compareTo(cTarget);
		assertNotEquals(nonExpectedValue, returnValue);
	}

}
