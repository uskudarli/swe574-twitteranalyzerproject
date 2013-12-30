package swe574.g2.twitteranalysis.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import swe574.g2.twitteranalysis.Campaign;
import swe574.g2.twitteranalysis.controller.CampaignController;
import swe574.g2.twitteranalysis.exception.CampaignException;

public class CampaignControllerTest extends BaseTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testAddRemoveCampaign() {
		CampaignController controller = new CampaignController();

		String campaignName = "campaign name test";
		try {
			controller.addCampaign(campaignName);
		} catch (CampaignException ce) {
			fail("addCampaign threw a CampaignException where it was not expected..");
		}
		try {
			controller.removeCampaign(campaignName);
		} catch (CampaignException ce) {
			fail("removeCampaign threw a CampaignException where it was not expected..");
		}
	}

	@Test (expected = CampaignException.class)
	public void testDoubleAddSameCampaign() {
		CampaignController controller = new CampaignController();

		String campaignName = "campaign name test";
		
		// First call, should not throw exception
		try {
			controller.addCampaign(campaignName);
		} catch (CampaignException ce) {
			fail("addCampaign threw a CampaignException where it was not expected..");
		}
		
		// Second call with the same campaign name
		try {
			controller.addCampaign(campaignName);
			fail("addCampaign did not throw a CampaignException where it was expected..");
		} catch (CampaignException ce) {
			// Success case
		}
	}

	@Test (expected = CampaignException.class)
	public void testDoubleRemoveSameCampaign() {
		CampaignController controller = new CampaignController();

		String campaignName = "campaign name test";
		
		// First: Add an arbitrary campaign to remove
		try {
			controller.addCampaign(campaignName);
		} catch (CampaignException ce) {
			// Exception handled because there might be a campaign with the same name
			// by chance
		}

		// Second: Remove the campaign
		try {
			controller.removeCampaign(campaignName);
		} catch (CampaignException ce) {
			fail("removeCampaign threw a CampaignException where it was not expected..");
		}
		
		// Finally: Try to remove the same campaign which is already removed 
		try {
			controller.removeCampaign(campaignName);
			fail("removeCampaign did not throw a CampaignException where it was expected..");
		} catch (CampaignException ce) {
			// Success case
		}
	}

}