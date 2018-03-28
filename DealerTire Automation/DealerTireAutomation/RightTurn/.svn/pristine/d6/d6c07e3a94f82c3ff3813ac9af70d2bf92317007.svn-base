package com.dealertire.RightTurnTesting;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.dealertire.RightTurnFramework.BaseTest;
import com.dealertire.RightTurnFramework.Models.Dealer;
import com.dealertire.RightTurnFramework.Models.Product;
import com.dealertire.RightTurnFramework.Models.Vehicle;
import com.dealertire.RightTurnFramework.Models.Vehicle.Criteria;
import com.dealertire.RightTurnFramework.Pages.InstallPage;
import com.dealertire.RightTurnFramework.Pages.LocationPage;
import com.dealertire.RightTurnFramework.Pages.ProductPage;
import com.dealertire.RightTurnFramework.Pages.SchedulePage;
import com.dealertire.RightTurnFramework.Pages.TireCoachPage;
import com.dealertire.RightTurnFramework.Pages.VehiclePage;
import com.dealertire.RightTurnFramework.Pages.VerifyPage;

/**
 * Feature: Scheduling
 * @author  nreilly
 */
public class SchedulingTests extends BaseTest {

	private Product product;
	
	/**
     * @see com.dealertire.RightTurnFramework.BaseTest#BaseTest(String, String, String)
     */
	public SchedulingTests(String os, String browserName, String browserVersion) {
		super(os, browserName, browserVersion);
	}

	/**
	 * Runs before every test to ensure we're on the right page.
	 */
	@Before
	public void SetUp() {		
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("SETUP: Using vehicle " + vehicle);
		
		//Navigate to the Product page
		logger.info("SETUP: Navigating to location page");
		locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
		locationPage.enterZipCode("44114");
		
		logger.info("SETUP: Navigating to vehicle page");
		vehiclePage = (VehiclePage) locationPage.clickNext();
		vehiclePage.selectVehicle(vehicle);
	}
	
	/**
	 * Given I have selected a vehicle with its default dealer
	 * And I know the dealer transportation amenities from the verify page
	 * When I navigate to the scheduling page
	 * Then I should see the correct radio amenity buttons or none(if there are no amenities)
	 */
	@Test
	public void lonarCarValetAndShuttle(){
		logger.info("SETUP: Navigating to verify page");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		ArrayList<Dealer.Criteria> expectedAmenities = verifyPage.getTransportationAmenetiesShown();
		
		logger.info("SETUP: Navigating to tire coach page");
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();	
		
		logger.info("SETUP: Navigating to product page");
		productPage = (ProductPage) tireCoachPage.clickNext();
		product = productPage.getRandomProduct();
		logger.info("SETUP: Using product " + product);
		productPage.selectRandomQuantity(product);
		
		logger.info("SETUP: Navigating to install page");
		installPage = (InstallPage) productPage.selectProduct(product);
		
		logger.info("SETUP: Navigating to schedule page");
		schedulePage = (SchedulePage)installPage.clickNext();		
		schedulePage.waitForPageLoad();		
		ArrayList<Dealer.Criteria> shownAmenities = schedulePage.getTransportationAmenetiesShown();
		
		assertEquals("Amenities displayed were not correct", expectedAmenities , shownAmenities);
	}

	/**
	 * Given I am on the scheduling page
	 * When I click the date box
	 * And select a date
	 * Then the time box should pop up
	 * When I choose a valid time
	 * Then I should be able to proceed to the next page
	 */
	@Test
	public void scheduleDateAndTimeSlots(){
		logger.info("SETUP: Navigating to verify page");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		logger.info("SETUP: Navigating to tire coach page");
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();	
		
		logger.info("SETUP: Navigating to product page");
		productPage = (ProductPage) tireCoachPage.clickNext();
		product = productPage.getRandomProduct();
		logger.info("SETUP: Using product " + product);
		productPage.selectRandomQuantity(product);
		
		logger.info("SETUP: Navigating to install page");
		installPage = (InstallPage) productPage.selectProduct(product);
		
		logger.info("SETUP: Navigating to schedule page");
		schedulePage = (SchedulePage)installPage.clickNext();		
		schedulePage.waitForPageLoad();		
		
		assertTrue("Schedule page did not load", schedulePage.isLoaded());
		assertFalse("Should not be able to navigate onward", schedulePage.canGoOn());
		
		logger.info("Step 1: Select time slot");
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_MONTH, 7);
		date.set(Calendar.HOUR, 14);
		date.set(Calendar.MINUTE, 0);
		
		schedulePage.selectTimeSlot(date);
		assertTrue("Should now be able to navigate onward", schedulePage.canGoOn());
	}	
}
