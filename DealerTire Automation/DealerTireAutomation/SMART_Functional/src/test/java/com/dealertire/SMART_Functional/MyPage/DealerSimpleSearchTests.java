package com.dealertire.SMART_Functional.MyPage;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.dealertire.SMARTFramework.BaseTest;
import com.dealertire.SMARTFramework.Utils;
import com.dealertire.SMARTFramework.Pages.DealerPage;
import com.dealertire.SMARTFramework.Pages.MyPage;

/**
 * The simple search for dealers on the My Page
 * @author bgreen
 *
 */
@RunWith(Parameterized.class)
public class DealerSimpleSearchTests extends BaseTest {

	
	private String dealerName;
	private String dealerCode;
	private MyPage myPage;
	private String dealerID;

	
	/**
	 * Parameter generation
	 * @return The parameters from the datasheet
	 * @throws IOException If the datasheet cannot be read
	 */
	@Parameters(name= "{index}: {3}")
    public static synchronized  Collection<String[]> parameters() throws IOException {
    	
    	ArrayList<String[]> allParams = null;
		try {
			ArrayList<String[]> dataSheet = Utils.readFromCSV("dealers.csv");
			allParams = Utils.mergeDatasheets_cartesian(getEnvironmentDatasheet(), dataSheet);
	    	
		} catch (IOException e) {
			fail("Could not read dealer list to test.");
		}
	
		return allParams;
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
	public DealerSimpleSearchTests(String remoteHost, String browser, String version, String dealerName, String dealerCode, String dealerID) {
		super(remoteHost, browser, version);
		this.dealerName = dealerName;
		this.dealerCode = dealerCode;
		this.dealerID = dealerID;
	}
	
	/**
	 * Scenario: Full name of Dealership
	 * Given I am on My Page
	 * When I enter the full name of a dealership into the search box
	 * And I conduct a search
	 * Then I should see that dealership in the search results
	 */
	@Test
	public void fullNameOfDealership() {
		logger.info("1. Navigating to My Page");
		myPage = new MyPage(driver);
		myPage.navigateTo();
		
		logger.info("2. Searching by dealership name: '" + dealerName + "'");
		myPage.performDealerSimpleSearch(dealerName);
		
		logger.info("3. Verifying search results.");
		assertTrue("Dealership " + this.dealerID + " did not appear in search results",
				myPage.dealershipIsInResults(dealerID));
	}
	
	/** 
	 * Scenario: Partial Name
	 * Given I am on My Page
	 * When I enter the first few letters of a dealer’s name into the search box
	 * And I conduct a search
	 * Then I should see only dealerships containing those letters in the search results
	 */
	@Test
	public void partialNameOfDealership() {
		String partialName = dealerName.substring(0, dealerName.length()/2);
		logger.info("1. Navigating to My Page");
		myPage = new MyPage(driver);
		myPage.navigateTo();
		
		logger.info("2. Searching by partial dealership name: '" + partialName + "'");
		myPage.performDealerSimpleSearch(partialName);
		
		logger.info("3. Verifying search results.");
		assertTrue("Dealership " + this.dealerID + " did not appear in search results",
				myPage.dealershipIsInResults(dealerID));
	}
	
	/**
	 * Scenario: Full Dealercode
	 * Given I am on My Page
	 * When I enter the full dealercode for a dealer into the search box
	 * And I conduct a search
	 * Then I should see that dealer in the search results
	 */
	@Test
	public void fullDealercode() {
		logger.info("1. Navigating to My Page");
		myPage = new MyPage(driver);
		myPage.navigateTo();
		
		logger.info("2. Searching by dealership code: '" + dealerCode + "'");
		myPage.performDealerSimpleSearch(dealerCode);
		
		logger.info("3. Verifying search results.");
		assertTrue("Dealership " + this.dealerID + " did not appear in search results",
				myPage.dealershipIsInResults(dealerID));
	}
	
	
	/**
	 * Scenario: Partial Dealercode
	 * Given I am on My Page
	 * When I enter the first few characters of a dealercode into the search box
	 * And I conduct a search
	 * Then I should see only dealerships whose code contains those characters in the search results
	 */
	@Test
	public void partialDealercode() {
		String partialCode = dealerCode.substring(0, 4);
		logger.info("1. Navigating to My Page");
		myPage = new MyPage(driver);
		myPage.navigateTo();
		
		logger.info("2. Searching by partial dealership code: '" + partialCode + "'");
		myPage.performDealerSimpleSearch(partialCode);
		
		logger.info("3. Verifying search results.");
		assertTrue("Dealership " + this.dealerID + " did not appear in search results",
				myPage.dealershipIsInResults(dealerID));
	}
	
	/**
	 * Scenario: Full invalid dealercode
	 * Given I am on My Page
	 * When I enter a full dealercode that does not correspond to a valid dealer
	 * And I conduct a search
	 * Then I should see “No Dealers” in the search results
	 */
	@Test
	public void fullInvalidDealercode() {
		logger.info("1. Navigating to My Page");
		myPage = new MyPage(driver);
		myPage.navigateTo();
		
		logger.info("2. Searching by dealership code: 'XYZ0000'");
		myPage.performDealerSimpleSearch("XYZ0000");
		
		logger.info("3. Verifying search results.");
		assertFalse("Search results were found!",
				myPage.hasDealerSearchResults());
	}
	
	/**
	 * Scenario: Partial invalid dealercode
	 * Given I am on My Page
	 * When I enter the first few characters of a dealercode that does not correspond to a valid dealer
	 * And I conduct a search
	 * Then I should see “No Dealers” in the search results
	 */
	@Test
	public void partialInvalidDealercode() {
		logger.info("1. Navigating to My Page");
		myPage = new MyPage(driver);
		myPage.navigateTo();
		
		logger.info("2. Searching by dealership code: 'XYZ'");
		myPage.performDealerSimpleSearch("XYZ");
		
		logger.info("3. Verifying search results.");
		assertFalse("Search results were found!",
				myPage.hasDealerSearchResults());
	}
	
	/**
	 * Scenario: Select Dealer
	 * Given I have searched for dealers
	 * And I found results
	 * When I click any part of a result other than the dealer’s name
	 * Then I should see that result selected
	 */
	@Test
	public void SelectDealer() {
		logger.info("1. Navigating to My Page");
		myPage = new MyPage(driver);
		myPage.navigateTo();
		
		logger.info("2. Searching by dealership code: '" + dealerCode + "'");
		myPage.performDealerSimpleSearch(dealerCode);
		
		logger.info("3. Clicking dealership.");
		myPage.clickDealershipInSearchResults(dealerID);
		
		logger.info("4. Verifying selection.");
		assertTrue("Dealer ID was not selected!",
				myPage.getSelectedDealerSearchResults().contains(dealerID));
	}
	
	/**
	 * Scenario: Edit Dealer
	 * Given I have searched for dealers
	 * And I found results
	 * When I click the dealer’s name
	 * Then I should be on the Dealer page
	 * And that dealer’s information should be loaded
	 */
	@Test
	public void EditDealer() {
		logger.info("1. Navigating to My Page");
		myPage = new MyPage(driver);
		myPage.navigateTo();
		
		logger.info("2. Searching by dealership code: '" + dealerCode + "'");
		myPage.performDealerSimpleSearch(dealerCode);
		
		logger.info("3. Clicking dealership Name.");
		DealerPage dealerPage = myPage.clickDealerNameInSearchResults(dealerID);
		
		logger.info("4. Verifying navigation.");
		assertTrue("Dealer page did not load!",
				dealerPage.isLoaded());
		
		assertEquals(dealerID, dealerPage.getDealerIDDisplayed());
	}
	
	
}
