package com.dealertire.RightTurnTesting;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.dealertire.RightTurnFramework.BaseTest;
import com.dealertire.RightTurnFramework.Pages.LocationPage;
import com.dealertire.RightTurnFramework.Pages.VehiclePage;

/**
 * Feature: Zip code validation
 * The user shall be able to enter a zip code to identify their location
 * 
 * @author bgreen
 *
 */
public class ZipCodeValidationTests extends BaseTest {

	/**
     * @see com.dealertire.RightTurnFramework.BaseTest#BaseTest(String, String, String)
     */
	public ZipCodeValidationTests(String os, String browserName, String browserVersion) {
		super(os, browserName, browserVersion);
	}
	

	/**
	 * Runs before every test to ensure we're on the right page.
	 */
	@Before
	public void SetUp() {		
		//Navigate to the Location page
		logger.info("SETUP: Navigating to location page");
		locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
	}
	
	/**
	 * Test: Verify that when given a valid zip code, the location page accepts it as valid.
	 * 
	 * Given I am on the location page
	 * When I enter a valid zip code
	 * Then the page should recognize that the zip code is valid
	 * 
	 * @throws Exception if the location page does not respond to the zip code entered
	 */
	@Test
	public void testValidZipcodeIsValid() throws Exception {
		logger.info("Step 1: Enter valid zip code");
		assertTrue("Zip code should be valid", locationPage.zipcodeIsValid("94044"));
	}
	
	/**
	 * Test: Verify that when given an invalid zip code, the location page rejects it as invalid 
	 * 
	 * Given I am on the location page
	 * When I enter an invalid zip code
	 * Then the page should inform me that the zip code is invalid
	 * 
	 * @throws Exception if the page does not display any messages
	 */
	@Test
	public void testInvalidZipcodeIsInvalid() throws Exception {
		logger.info("Step 1: Enter invalid zip code");
		assertFalse("Zip code should not be valid", locationPage.zipcodeIsValid("00000"));
	}
	
	/**
	 * Test: Verify that when given a zip code representing an area in our market, the location page accepts this as in-market
	 * 
	 * Given that I am on the location page
	 * When I enter a valid zip code inside the service area
	 * Then the page should inform me that I am in the service area
	 * 
	 * @throws Exception if the page does not display any messages
	 */
	@Test
	public void testInMarketZipcode() throws Exception {	
		logger.info("Step 1: Enter in-market zip code");
		assertTrue("Zip code should be in market", locationPage.zipcodeInMarket("44114"));
	}
	
	/**
	 * Test: Verify that when given a zip code representing an area we do not service, the location page rejects this as out of our area
	 * 
	 * Given I am on the location page
	 * When I enter a valid zip code outside the service area
	 * Then the page should inform me that I am not in the service area
	 * 
	 * @throws Exception if the page does not display any messages
	 */
	@Test
	public void testOutMarketZipcode() throws Exception {
		logger.info("Step 1: Enter out market zip code");
		assertFalse("Zip code should not be in market", locationPage.zipcodeInMarket("94044"));
	}
	
	/**
	 * Test: Verify that when given a valid zip code in our service area, the location page allows the user to continue on to the next step
	 * 
	 * Given I am on the location page
	 * When I enter a valid zip code in the service area
	 * Then I should be able to continue on
	 * When I click next
	 * Then I should be on the Vehicle page
	 */
	@Test
	public void verifyValidZipCanGoOn() {
		logger.info("Step 1: Enter valid zip code");
		locationPage.enterZipCode("44114");
		assertTrue("Should be able to continue on", locationPage.canGoOn());
		
		logger.info("Step 2: Click 'next'");
		VehiclePage nextPage = (VehiclePage) locationPage.clickNext();
		assertTrue("Vehicle page should load", nextPage.isLoaded());
	}
	
	/**
	 * Test: Verify that when given an invalid zip code, the location page does not allow the user to continue on to the next step
	 * 
	 * Given I am on the location page
	 * When I enter an invalid zip code
	 * Then I should not be able to continue on
	 */
	@Test
	public void verifyInvalidZipCannotGoOn() {
		logger.info("Step 1: Enter invalid zip code");
		locationPage.enterZipCode("00000");
		assertFalse("Should not be able to continue", locationPage.canGoOn());
	}
	
	/**
	 * Test: Verify that when given a valid zip code outside our service area, the location page does not allow the user to continue on to the next step
	 * 
	 * Given that I am on the location page
	 * When I enter a valid zip code outside Dallas or Cleveland
	 * Then I should not be able to continue on
	 */
	@Test
	public void verifyOutMarketZipCannotGoOn() {
		logger.info("Step 1: Enter out market zip code");
		locationPage.enterZipCode("94044");
		assertFalse("Should not be able to continue", locationPage.canGoOn());
	}
	

}
