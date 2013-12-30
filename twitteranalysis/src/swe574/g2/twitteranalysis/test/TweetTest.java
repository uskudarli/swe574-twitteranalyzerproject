package swe574.g2.twitteranalysis.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ibm.icu.util.Calendar;

import swe574.g2.twitteranalysis.Campaign;
import swe574.g2.twitteranalysis.Hashtag;
import swe574.g2.twitteranalysis.MentionedUser;
import swe574.g2.twitteranalysis.Tweet;

public class TweetTest extends BaseTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUpBeforeMethod() {
		
	}
	
	@After
	public void tearDownAfterMethod() {
		
	}

	/*
	 * This test case tests the copy constuctor of swe574.g2.twitteranalysis.Tweet and compares
	 * two Tweet objects whether the properties are copied correctly or not.
	 * 
	 */
	@Test
	public void testTweetContructorWithStatus() {
		// Campaing Properties
		String campaignDescription = "campaign desciption";
		int campaignId = intIdCounter++;
		String campaignName = "campaign name";
		int campaignOwnerUserId = intIdCounter++;

		// Tweet Properties
		String content = "content";
		
		cal.set(113, 1, 1); // 01.01.2013
		Date createdDate = cal.getTime();
		
		long extTweetId = longIdCounter++;
		int favoriteCount = intIdCounter++;

		// Sample Hashtag One
		int hashtagOneBegin = 1;
		int hashtagOneId = intIdCounter++;
		int hashtagOneEnd = 10;
		String hashtagOneValue = "hashtagOne value";

		// Sample Hashtag Two
		int hashtagTwoBegin = 1;
		int hashtagTwoId = intIdCounter++;
		int hashtagTwoEnd = 10;
		String hashtagTwoValue = "hashtagTwo value";
		
		int tweetId = intIdCounter++;
		
		
		// Create Tweet
		Tweet tSource = new Tweet();
		
		// Create Campaign
		Campaign cSource = new Campaign();
		cSource.setDescription(campaignDescription);
		cSource.setId(campaignId);
		cSource.setName(campaignName);
		cSource.setOwnerUserId(campaignOwnerUserId);
		
		tSource.setCampaign(cSource);
		tSource.setContent(content);
		tSource.setCreatedAt(createdDate);
		tSource.setExtTweetId(extTweetId);
		tSource.setFavoriteCount(favoriteCount);
		
		
		List<Hashtag> hashtagList = new ArrayList<Hashtag>();
		
		Hashtag hashtagOne = new Hashtag();
		hashtagOne.setEnd(hashtagOneEnd);
		hashtagOne.setId(hashtagOneId);
		hashtagOne.setStart(hashtagOneBegin);
		hashtagOne.setValue(hashtagOneValue);
		
		Hashtag hashtagTwo = new Hashtag();
		hashtagTwo.setEnd(hashtagTwoEnd);
		hashtagTwo.setId(hashtagTwoId);
		hashtagTwo.setStart(hashtagTwoBegin);
		hashtagTwo.setValue(hashtagTwoValue);
		
		hashtagList.add(hashtagOne);
		hashtagList.add(hashtagTwo);
		
		tSource.setHashtags(hashtagList);
		
		tSource.setId(tweetId);

		
	}

}