package com.dealertire.SMART_Functional.MyPage;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.dealertire.SMARTFramework.BaseTest;
import com.dealertire.SMARTFramework.Utils;
import com.dealertire.SMARTFramework.Pages.MyPage;
import com.dealertire.SMARTFramework.Pages.MyPage.DayOfWeek;
import com.dealertire.SMARTFramework.Pages.VisitPage;

/**
 * Visit Calendar testing on My Page
 * @author bgreen
 *
 */
@RunWith(Parameterized.class)
public class VisitCalendarTests extends BaseTest {

	private String dealerName;
	private String dealerCode;
	private MyPage myPage;
	private String dealerID;

	
	/**
	 * Parameter generation
	 * @return The parameters from the datasheetk
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
	public VisitCalendarTests(String remoteHost, String browser, String version, String dealerName, String dealerCode, String dealerID) {
		super(remoteHost, browser, version);
		this.dealerName = dealerName;
		this.dealerCode = dealerCode;
		this.dealerID = dealerID;
	}
	
	/**
	 * Scenario: New Visit From Calendar
	 * Given I am on My Page
	 * When I click on a time slot on the calendar
	 * Then I should be on the Visit page
	 * And I should see a blank visit
	 * And the time should be the time I clicked on
	 * And the date should be the date I clicked on
	 */
	@Test
	public void NewVisitFromCalendar() {
		logger.info("1. Navigating to My Page");
		myPage = new MyPage(driver);
		myPage.navigateTo();
		
		logger.info("2. Clicking on time slot.");
		myPage.clickCalendarTimeSlot(DayOfWeek.WEDNESDAY, "8am");
		
		//Get the date of wednesday this week
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		SimpleDateFormat visitPageDateFormatter = new SimpleDateFormat(VisitPage.dateFormatPattern);
		String currentWednesday = visitPageDateFormatter.format(c.getTime());
		
		logger.info("3.Verifications");
		assertTrue("Modal did not appear",
				myPage.isCalendarModalShowing());
		
		String currentURL = driver.getCurrentUrl();
		myPage.calendarModalClickVisit();
		Utils.WaitForUrlChange(driver, currentURL);
		
		VisitPage visitPage = new VisitPage(driver);
		assertTrue("Did not arrive on visit page",
				visitPage.isLoaded());
		visitPage.waitForPageLoad();
		
		assertEquals("Visit did not have the right date",
				currentWednesday,
				visitPage.getVisitDate());
				
		assertEquals("Visit did not have the right time",
				"08:00AM",
				visitPage.getVisitStartTime());
	}
	
	/**
	 * Given I am on My Page
	 * And I have searched for dealers from the quick search
	 * When I click on a dealer on the left
	 * And I click on a time slot on the calendar
	 * Then I should be on the Visit page
	 * And I should see a blank visit
	 * And the time should be the time I clicked on
	 * And the date should be the date I clicked on
	 * And the dealer should be the dealer that was selected
	 */
	@Test
	public void NewVisitFromCalendarWithDealer() {
		logger.info("1. Navigating to My Page");
		myPage = new MyPage(driver);
		myPage.navigateTo();
		
		logger.info("2. Searching by dealership code: '" + dealerCode + "'");
		myPage.performDealerSimpleSearch(dealerCode);
		
		logger.info("3. Clicking dealership.");
		myPage.clickDealershipInSearchResults(dealerID);
		
		logger.info("4. Clicking on time slot.");
		myPage.clickCalendarTimeSlot(DayOfWeek.WEDNESDAY, "8am");
		
		//Get the date of wednesday this week
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		SimpleDateFormat visitPageDateFormatter = new SimpleDateFormat(VisitPage.dateFormatPattern);
		String currentWednesday = visitPageDateFormatter.format(c.getTime());
		
		logger.info("5.Verifications");
		assertTrue("Modal did not appear",
				myPage.isCalendarModalShowing());
		
		String currentURL = driver.getCurrentUrl();
		myPage.calendarModalClickVisit();
		Utils.WaitForUrlChange(driver, currentURL);
		
		VisitPage visitPage = new VisitPage(driver);
		assertTrue("Did not arrive on visit page",
				visitPage.isLoaded());
		visitPage.waitForPageLoad();
		
		assertEquals("Visit did not have the right date",
				currentWednesday,
				visitPage.getVisitDate());
				
		assertEquals("Visit did not have the right time",
				"08:00AM",
				visitPage.getVisitStartTime());
		
		String listOfDealers = visitPage.getListOfDealers();
		assertTrue("Visit did not contain the right dealer; expected '" + dealerName + ";, but got '" + listOfDealers + "'",
				listOfDealers.toLowerCase().contains(dealerName.toLowerCase()));
	}
	
	/**
	 * Scenario: Drag and Drop
	 * Given I am on My Page
	 * And I have searched for dealers from the quick search
	 * When I drag a dealer from the left to a time slot on the calendar
	 * Then I should be on the Visit page
	 * And I should see a blank visit
	 * And the time should be the time I clicked on
	 * And the date should be the date I clicked on
	 * And the dealer should be the dealer that was dragged
	 */
	@Test
	public void DragAndDrop() {
		logger.info("1. Navigating to My Page");
		myPage = new MyPage(driver);
		myPage.navigateTo();
		
		logger.info("2. Searching by dealership code: '" + dealerCode + "'");
		myPage.performDealerSimpleSearch(dealerCode);
		
		String currentURL = driver.getCurrentUrl();
		logger.info("3. Dragging dealership to timeslot");
		myPage.dragDealerToTimeSlot(dealerID,DayOfWeek.WEDNESDAY, "9am");
		
		
		//Get the date of wednesday this week
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		SimpleDateFormat visitPageDateFormatter = new SimpleDateFormat(VisitPage.dateFormatPattern);
		String currentWednesday = visitPageDateFormatter.format(c.getTime());
		
		logger.info("4.Verifications");	
		Utils.WaitForUrlChange(driver, currentURL);
		VisitPage visitPage = new VisitPage(driver);
		
		assertTrue("Did not arrive on visit page",
				visitPage.isLoaded());
		visitPage.waitForPageLoad();
		
		assertEquals("Visit did not have the right date",
				currentWednesday,
				visitPage.getVisitDate());
				
		assertEquals("Visit did not have the right time",
				"09:00AM",
				visitPage.getVisitStartTime());
		
		String listOfDealers = visitPage.getListOfDealers();
		assertTrue("Visit did not contain the right dealer; expected '" + dealerName + ";, but got '" + listOfDealers + "'",
				listOfDealers.toLowerCase().contains(dealerName.toLowerCase()));
	}
}
