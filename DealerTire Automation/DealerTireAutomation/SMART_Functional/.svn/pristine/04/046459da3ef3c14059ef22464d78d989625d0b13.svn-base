package com.dealertire.SMART_Functional.MyPage;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.dealertire.SMARTFramework.BaseTest;
import com.dealertire.SMARTFramework.Utils;
import com.dealertire.SMARTFramework.Pages.DealerPage;
import com.dealertire.SMARTFramework.Pages.MyPage;

/**
 * Feature: Advanced Search on My Page
 * @author bgreen
 */
@RunWith(Parameterized.class)
public class DealerAdvancedSearchTests extends BaseTest {

	private MyPage myPage;

	
	/**
	 * Parameter generation
	 * @return The parameters from the datasheet
	 * @throws IOException If the datasheet cannot be read
	 */
	@Parameters(name= "{index}")
    public static synchronized  Collection<String[]> parameters() throws IOException {
		return getEnvironmentDatasheet();
    }

	/**
	 * Parameterized constructor for testing
	 * @param remoteHost The remote host to use
	 * @param browser The browser to use
	 * @param version The version to request
	 * @param dealerName The name of the dealer to use
	 * @param dealerCode The dealercode for that dealer
	 * @param dealerID the dealer ID for that dealer
	 */
	public DealerAdvancedSearchTests(String remoteHost, String browser, String version) {
		super(remoteHost, browser, version);
	}
	
	/**
	 * Scenario: Advanced Search
	 * Given I am on My Page
	 * When I click the down arrow next to the search box
	 * And I select “Advanced Search”
	 * Then the advanced search modal should appear.
	 */
	@Test
	public void AdvancedSearch() {
		logger.info("1. Navigating to My Page");
		myPage = new MyPage(driver);
		myPage.navigateTo();
		
		logger.info("2. Opening Advanced Search");
		myPage.openAdvancedSearch();
		
		logger.info("3. Verifying modal");
		assertTrue("Modal did not open!",
				myPage.isAdvancedSearchShowing());
	}
	
	/**
	 * Scenario: Inform Segment
	 * Given I am in the advanced search modal
	 * And I have entered search criteria
	 * When I choose “Inform” from the Segment options
	 * And I conduct a search
	 * Then I should only see dealers in the “Inform” segment
	 */
	@Test
	public void InformSegment() {
		logger.info("1. Navigating to My Page");
		myPage = new MyPage(driver);
		myPage.navigateTo();
		
		logger.info("2. Opening Advanced Search");
		myPage.openAdvancedSearch();
		
		logger.info("3. Selecting segment");
		myPage.selectSegment("Inform");
		
		logger.info("4. Searching");
		myPage.performAdvancedSearch();
		Utils.WaitForAjax(driver);
		
		logger.info("5. Verifying results");
		HashMap<String,String> results = myPage.getDealerSearchResultSegments();
		
		for (String key : results.keySet()) {
			assertTrue("Dealer " + key + " was not in the Inform segment!",
					results.get(key).equalsIgnoreCase("Inform"));
		}
	}
	
	/**
	 * Scenario: DeepDive Segment
	 * Given I am in the advanced search modal
	 * And I have entered search criteria
	 * When I choose “Deep Dive” from the Segment options
	 * And I conduct a search
	 * Then I should only see dealers in the “Inform” segment
	 */
	@Test
	public void DeepDiveSegment() {
		logger.info("1. Navigating to My Page");
		myPage = new MyPage(driver);
		myPage.navigateTo();
		
		logger.info("2. Opening Advanced Search");
		myPage.openAdvancedSearch();
		
		logger.info("3. Selecting segment");
		myPage.selectSegment("Conquest");
		
		logger.info("4. Searching");
		myPage.performAdvancedSearch();
		Utils.WaitForAjax(driver);
		
		logger.info("5. Verifying results");
		HashMap<String,String> results = myPage.getDealerSearchResultSegments();
		
		for (String key : results.keySet()) {
			assertTrue("Dealer " + key + " was not in the Conquest segment!",
					results.get(key).equalsIgnoreCase("Conquest"));
		}
	}
	
	/**
	 * Scenario: Grow Segment
	 * Given I am in the advanced search modal
	 * And I have entered search criteria
	 * When I choose “Grow” from the Segment options
	 * And I conduct a search
	 * Then I should only see dealers in the “Grow” segment
	 */
	@Test
	public void GrowSegment() {
		logger.info("1. Navigating to My Page");
		myPage = new MyPage(driver);
		myPage.navigateTo();
		
		logger.info("2. Opening Advanced Search");
		myPage.openAdvancedSearch();
		
		logger.info("3. Selecting segment");
		myPage.selectSegment("Grow");
		
		logger.info("4. Searching");
		myPage.performAdvancedSearch();
		Utils.WaitForAjax(driver);
		
		logger.info("5. Verifying results");
		HashMap<String,String> results = myPage.getDealerSearchResultSegments();
		
		for (String key : results.keySet()) {
			assertTrue("Dealer " + key + " was not in the Grow segment!",
					results.get(key).equalsIgnoreCase("Grow"));
		}
	}
	
	/**
	 * Scenario: Protect Segment
	 * Given I am in the advanced search modal
	 * And I have entered search criteria
	 * When I choose “Protect” from the Segment options
	 * And I conduct a search
	 * Then I should only see dealers in the “Inform” segment
	 */
	@Test
	public void ProtectSegment() {
		logger.info("1. Navigating to My Page");
		myPage = new MyPage(driver);
		myPage.navigateTo();
		
		logger.info("2. Opening Advanced Search");
		myPage.openAdvancedSearch();
		
		logger.info("3. Selecting segment");
		myPage.selectSegment("Protect");
		
		logger.info("4. Searching");
		myPage.performAdvancedSearch();
		Utils.WaitForAjax(driver);
		
		logger.info("5. Verifying results");
		HashMap<String,String> results = myPage.getDealerSearchResultSegments();
		
		for (String key : results.keySet()) {
			assertTrue("Dealer " + key + " was not in the Protect segment!",
					results.get(key).equalsIgnoreCase("Protect"));
		}
	}
	
	/**
	 * Scenario: Region Target Yes
	 * Given I am in the advanced search modal
	 * And I have entered search criteria
	 * When I choose “Yes” from the Region Target options
	 * And I conduct a search
	 * Then I should only see dealers in the targeted region
	 * When I click on a dealer’s name
	 * Then I should be on their dealer page
	 * And the region target field should read “Yes”
	 */
	@Test
	public void RegionTargetYes() {
		int maxVerifications = 5;
		
		logger.info("1. Navigating to My Page");
		myPage = new MyPage(driver);
		myPage.navigateTo();
		
		logger.info("2. Opening Advanced Search");
		myPage.openAdvancedSearch();
		
		logger.info("3. Selecting Region Target");
		myPage.selectRegionTarget(true);
		
		logger.info("4. Searching");
		myPage.performAdvancedSearch();
		Utils.WaitForAjax(driver);
		
		logger.info("5. Verifying results");
		ArrayList<String> results = myPage.getDealerSearchResults();

		if (results.size() < maxVerifications) {
			maxVerifications = results.size();
		}
		
		for (String dealerID : results) {
			myPage.clickDealerNameInSearchResults(dealerID);
			DealerPage dp = new DealerPage(driver);
			String regionTarget = dp.getRegionTarget();
			
			assertTrue("Dealer " + dealerID + " did not have the stated region target! Regional target was: " + regionTarget,
					regionTarget.equalsIgnoreCase("Yes"));
		}
	}
	
	/**
	 * Scenario: Region Target No
	 * Given I am in the advanced search modal
	 * And I have entered search criteria
	 * When I choose “No” from the Region Target options
	 * And I conduct a search
	 * Then I should only see dealers in the targeted region
	 * When I click on a dealer’s name
	 * Then I should be on their dealer page
	 * And the region target field should read “No”
	 */
	@Test
	public void RegionTargetNo() {
		int maxVerifications = 5;
		
		logger.info("1. Navigating to My Page");
		myPage = new MyPage(driver);
		myPage.navigateTo();
		
		logger.info("2. Opening Advanced Search");
		myPage.openAdvancedSearch();
		
		logger.info("3. Selecting Region Target");
		myPage.selectRegionTarget(true);
		
		logger.info("4. Searching");
		myPage.performAdvancedSearch();
		Utils.WaitForAjax(driver);
		
		logger.info("5. Verifying results");
		ArrayList<String> results = myPage.getDealerSearchResults();

		if (results.size() < maxVerifications) {
			maxVerifications = results.size();
		}
		
		for (String dealerID : results) {
			myPage.clickDealerNameInSearchResults(dealerID);
			DealerPage dp = new DealerPage(driver);
			String regionTarget = dp.getRegionTarget();
			
			assertTrue("Dealer " + dealerID + " did not have the stated region target! Regional target was: " + regionTarget,
					regionTarget.equalsIgnoreCase("No"));
		}
	}
	
}
