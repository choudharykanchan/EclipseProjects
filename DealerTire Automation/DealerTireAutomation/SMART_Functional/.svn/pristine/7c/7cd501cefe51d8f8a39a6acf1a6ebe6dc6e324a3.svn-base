package com.dealertire.SMART_Functional.VisitPage;

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
import com.dealertire.SMARTFramework.Pages.VisitPage;

/**
 * Feature: Dealer selection
 * In order to schedule a visit to a dealer
 * As a user
 * I want to select one or more dealers to visit when creating a new visit
 * @author bgreen
 *
 */
@RunWith(Parameterized.class)
public class DealerSelectionTest extends BaseTest {

	private static ArrayList<String[]> dataSheet;
	private String dealerName;
	private String dealerCode;
	
	private VisitPage vp;
	
	/**
	 * Parameter generation
	 * @return The parameters from the datasheet
	 * @throws IOException If the datasheet cannot be read
	 */
	@Parameters(name= "{index}: {3}")
    public static synchronized  Collection<String[]> parameters() throws IOException {
    	try {
			dataSheet = Utils.readFromCSV("dealers.csv");
		} catch (IOException e) {
			fail("Could not read dealer list to test.");
		}
    	
    	ArrayList<String[]> allParams = Utils.mergeDatasheets_cartesian(getEnvironmentDatasheet(), dataSheet);
    	return allParams;
    }
	
    
	
	/**
	 * Parameterized constructor for tesing
	 * @param browser The browser to use
	 * @param version The version to request
	 * @param dealerName The name of the dealer to use
	 * @param dealerCode The dealercode for that dealer
	 * @param dealerID the dealer ID for that dealer
	 */
	public DealerSelectionTest(String remoteHost, String browser, String version, String dealerName, String dealerCode, String dealerID) {
		super(remoteHost, browser, version);
		this.dealerName = dealerName;
		this.dealerCode = dealerCode;
	}


	/**
	 * Given I am on the New Visit page
	 * When I click on the “Select a Dealer” link
	 * Then I should see the dealer search popup appear
	 * When I click the “[X]” button on the dealer search popup
	 * Then I should not see the dealer search popup anymore
	 */
	@Test
	public void dealerSearchPopup() {
		//Given I am on the new visit page
		logger.info("Opening Visit page");
		vp = new VisitPage(driver);
		vp.navigateTo();
		
		//When I click select a dealer
		logger.info("Verifying dealer popup opens");
		vp.closeDealerPopup();
		vp.clickSelectDealer();
		
		//Then I should see the dealer search popup appear
		vp.waitForDealerPopup();
		assertTrue(vp.isDealerPopupShowing());
		
		//When I click the "[X]" button on the dealer search popup
		logger.info("Verifying dealer popup closes");
		vp.closeDealerPopup();
		
		//Then I should not see the dealer search popup anymore
		assertFalse(vp.isDealerPopupShowing());
		
	}
	
	/**
	 * Scenario: Search by name
	 * Given I am on the New Visit page
	 * And the dealer search popup is showing
	 * When I enter the full name of a dealership
	 * Then I should see that dealer in the suggestion list
	 */
	@Test
	public void SearchByName() {
		//Given I am on the new visit page
		logger.info("1. Opening Visit page and dealer popup");
		vp = new VisitPage(driver);
		vp.navigateTo();
		vp.openDealerPopup();
		
		logger.info("2. Enter full name");
		vp.setDealerSearchString(dealerName);
		
		logger.info("3. Verify suggestions");
		assertTrue("No suggestions",
				vp.dealerListHasSuggestions());
		
		String dealerString = dealerName + " (" + dealerCode + ")";
		ArrayList<String> suggestions = vp.getSuggestedDealers();
		assertTrue("Dealer was not suggested! Top suggested dealer: " + suggestions.get(0),
				suggestions.contains(dealerString));
	}
	
	/**
	 * Scenario: Search by partial name
	 * Given I am on the New Visit page
	 * And the dealer search popup is showing
	 * When I enter part of the name of a dealership
	 * Then I should see any dealer with those characters in their name in the suggestion list
	 */
	@Test
	public void SearchByPartialName() {
		String partialName = dealerName.substring(0, dealerName.length()/2);
		
		logger.info("1. Opening Visit page and dealer popup");
		vp = new VisitPage(driver);
		vp.navigateTo();
		vp.openDealerPopup();
		
		logger.info("2. Enter partial name");
		vp.setDealerSearchString(partialName);
		
		Utils.WaitForAjax(driver);
		
		logger.info("3. Verify suggestions");
		assertTrue("No suggestions",
				vp.dealerListHasSuggestions());
		
		String dealerString = dealerName + " (" + dealerCode + ")";
		ArrayList<String> suggestions = vp.getSuggestedDealers();
		assertTrue("Dealer was not suggested! Top suggested dealer: " + suggestions.get(0),
				suggestions.contains(dealerString));
	}
	
	/**
	 * Scenario: Search by dealercode
	 * Given I am on the New Visit page
	 * And the dealer search popup is showing
	 * When I enter a valid dealer code
	 * Then I should see that dealer in the suggestion list
	 */
	@Test
	public void SearchByDealerCode() {
		//Given I am on the new visit page
		logger.info("1. Opening Visit page and dealer popup");
		vp = new VisitPage(driver);
		vp.navigateTo();
		vp.openDealerPopup();
		
		logger.info("2. Enter full code");
		vp.setDealerSearchString(dealerCode);
		
		Utils.WaitForAjax(driver);
		
		logger.info("3. Verify suggestions");
		assertTrue("No suggestions",
				vp.dealerListHasSuggestions());
		
		String dealerString = dealerName + " (" + dealerCode + ")";
		ArrayList<String> suggestions = vp.getSuggestedDealers();
		assertTrue("Dealer was not suggested! Top suggested dealer: " + suggestions.get(0),
				suggestions.contains(dealerString));
	}
	
	/**
	 * Scenario: Search by partial dealercode
	 * Given I am on the New Visit page
	 * And the dealer search popup is showing
	 * When I enter the first few characters of a valid dealercode
	 * Then I should see any dealer with those characters in their dealercode in the suggestion list
	 */
	@Test
	public void SearchByPartialDealerCode() {
		String partialCode = dealerCode.substring(0, 4);
		
		logger.info("1. Opening Visit page and dealer popup");
		vp = new VisitPage(driver);
		vp.navigateTo();
		vp.openDealerPopup();
		
		logger.info("2. Enter partial code");
		vp.setDealerSearchString(partialCode);
		
		Utils.WaitForAjax(driver);
		
		logger.info("3. Verify suggestions");
		assertTrue("No suggestions",
				vp.dealerListHasSuggestions());
		
		String dealerString = dealerName + " (" + dealerCode + ")";
		ArrayList<String> suggestions = vp.getSuggestedDealers();
		assertTrue("Dealer was not suggested! Top suggested dealer: " + suggestions.get(0),
				suggestions.contains(dealerString));
	}
	
	/**
	 * Scenario: Search by dealercode
	 * Given I am on the New Visit page
	 * And the dealer search popup is showing
	 * When I enter a valid dealer code
	 * Then I should see that dealer in the suggestion list
	 */
	@Test
	public void SearchByInvalidDealerCode() {
		//Given I am on the new visit page
		logger.info("1. Opening Visit page and dealer popup");
		vp = new VisitPage(driver);
		vp.navigateTo();
		vp.openDealerPopup();
		
		logger.info("2. Enter invalid code");
		vp.setDealerSearchString("XYZ12304");
		
		Utils.WaitForAjax(driver);
		
		logger.info("3. Verify suggestions");
		assertFalse("Dealer was suggested!",
				vp.dealerListHasSuggestions());
	}
	
	/**
	 * Scenario: Select Dealer
	 * Given I am on the New Visit page
	 * And the dealer search popup is showing
	 * When I search for a dealer
	 * And I click on that dealer’s name in the suggestion list
	 * Then I should see the dealer added to the list of dealers
	 * And my cursor should be ready for new input
	 * When I click the checkmark
	 * Then I should see the dealer I selected in the list of dealers for the visit
	 */
	@Test
	public void SelectDealer() {
		logger.info("1. Opening Visit page and dealer popup");
		vp = new VisitPage(driver);
		vp.navigateTo();
		vp.openDealerPopup();
		
		logger.info("2. Enter full code");
		assertFalse("Dealer already selected! Test is invalid!",
				vp.isDealerInDealerListPopup(dealerName));
		
		vp.setDealerSearchString(dealerCode);
		
		Utils.WaitForAjax(driver);
		
		vp.selectSuggestedDealerByName(dealerName);
		
		assertTrue("Dealer not selected!",
				vp.isDealerInDealerListPopup(dealerName));
		
		vp.acceptDealerPopup();
				
		assertTrue("Dealer not added to list. List was: '" + vp.getListOfDealers() + "'",
				vp.getListOfDealers().toLowerCase().contains(dealerName.toLowerCase()));
	}
	
	/**
	 * Scenario: Select Dealer Cancel
	 * Given I am on the New Visit page
	 * And the dealer search popup is showing
	 * When I search for a dealer
	 * And I click on that dealer’s name in the suggestion list
	 * Then I should see the dealer added to the list of dealers
	 * And my cursor should be ready for new input
	 * When I click the [X] on the dealer popup
	 * Then I should not see the dealer I selected in the list of dealers for the visit
	 */
	@Test
	public void SelectDealerCancel() {
		logger.info("1. Opening Visit page and dealer popup");
		vp = new VisitPage(driver);
		vp.navigateTo();
		vp.openDealerPopup();
		
		logger.info("2. Enter full code");
		assertFalse("Dealer already selected! Test is invalid!",
				vp.isDealerInDealerListPopup(dealerName));
		
		vp.setDealerSearchString(dealerCode);
		
		Utils.WaitForAjax(driver);
		
		vp.selectSuggestedDealerByName(dealerName);
		
		assertTrue("Dealer not selected!",
				vp.isDealerInDealerListPopup(dealerName));
		
		vp.closeDealerPopup();
				
		assertFalse("Dealer added to list",
				vp.getListOfDealers().toLowerCase().contains(dealerName.toLowerCase()));
	}
}
