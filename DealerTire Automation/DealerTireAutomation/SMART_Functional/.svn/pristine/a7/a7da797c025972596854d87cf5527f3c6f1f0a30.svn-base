package com.dealertire.SMART_Functional.VisitPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.dealertire.SMARTFramework.BaseTest;
import com.dealertire.SMARTFramework.Utils;
import com.dealertire.SMARTFramework.Pages.VisitPage;

/**
 * Adding activities from within a visit
 * @author bgreen
 *
 */
@RunWith(Parameterized.class)
public class InlineActivityTests extends BaseTest {
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
	public InlineActivityTests(String remoteHost, String browser, String version, String dealerName, String dealerCode, String dealerID) {
		super(remoteHost, browser, version);
		this.dealerName = dealerName;
		this.dealerCode = dealerCode;
	}
	
	@Before
	public void setup() {
		//Create a visit
		logger.info("Setup: Opening Visit page and dealer popup");
		vp = new VisitPage(driver);
		vp.navigateTo();
		vp.openDealerPopup();
		
		logger.info("Setup: Select dealer");		
		vp.setDealerSearchString(dealerCode);		
		vp.selectSuggestedDealerByName(dealerName);		
		assertTrue("Dealer not selected!",
				vp.isDealerInDealerListPopup(dealerName));
		vp.acceptDealerPopup();
		vp.selectCommunicationMethod("In Person");
	}
	
	/**
	 * Scenario: Activity without Topics
	 * Given I am on the New Visit page
	 * When I click on the dropdown next to “Add New”
	 * And I select a type of activity
	 * And I do not enter any topics
	 * When I save the visit
	 * Then I will not see any errors
	 */
	@Test
	public void ActivityWithoutTopics() {
		vp.addActivityToVisit("Business Planning");
		boolean saveSuccess = vp.saveVisit();
		assertTrue("Could not save visit. Message was: " + vp.getErrorMessage(),
				saveSuccess);
	}
	
	/**
	 * Scenario: Activity with Topic
	 * Given I am on the New Visit page
	 * When I click on the dropdown next to “Add New”
	 * And I select a type of activity
	 * And I add a topic
	 * When I save the visit
	 * Then I will not see any errors
	 */
	@Test
	public void ActivityWithTopic() {
		vp.addActivityToVisitWithTopic("Business Planning", "Goal Setting");
		boolean saveSuccess = vp.saveVisit();
		assertTrue("Could not save visit. Message was: " + vp.getErrorMessage(),
				saveSuccess);
	}
	
	/**
	 * Scenario: Activity with Product
	 * Given I am on the New Visit page
	 * When I click on the dropdown next to “Add New”
	 * And I select a type of activity
	 * And I add a product
	 * When I save the visit
	 * Then I will not see any errors
	 */
	@Test
	public void ActivityWithProduct() {
		vp.addActivityToVisitWithProduct("Business Planning", "Tires");
		boolean saveSuccess = vp.saveVisit();
		assertTrue("Could not save visit. Message was: " + vp.getErrorMessage(),
				saveSuccess);
	}
	
	/**
	 * Scenario: Pending Activity
	 * Given I am on the New Visit page
	 * When I add a new pending activity
	 * And I save
	 * Then it should show up in the Pending Activities list
	 */
	@Test
	public void PendingActivity() {
		int prevPendingActivities = vp.getNumPendingActivities();
		vp.addPendingActivity("Business Planning");
		vp.saveVisit();
		assertEquals("Wrong number of pending activities!",
				prevPendingActivities+1, vp.getNumPendingActivities());
		
	}
	
	
}
