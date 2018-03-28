package com.dealertire.RightTurnTesting;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.dealertire.RightTurnFramework.BaseTest;
import com.dealertire.RightTurnFramework.Models.Vehicle;
import com.dealertire.RightTurnFramework.Models.Vehicle.Criteria;
import com.dealertire.RightTurnFramework.Pages.LocationPage;
import com.dealertire.RightTurnFramework.Pages.VehiclePage;
import com.dealertire.RightTurnFramework.Pages.VerifyPage;

/**
 * Feature: Vehicle entry
 * The user shall be able to select their vehicle 
 * 
 * @author bgreen
 *
 */
public class VehicleValidationTests extends BaseTest {

	/**
     * @see com.dealertire.RightTurnFramework.BaseTest#BaseTest(String, String, String)
     */
	public VehicleValidationTests(String os, String browserName, String browserVersion) {
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
		locationPage.enterZipCode("44114");
		
		logger.info("SETUP: Navigating to vehicle page");
		vehiclePage = (VehiclePage) locationPage.clickNext();
	}
	

	/**
	 * Test that the dropdowns allow entry of a vehicle with options.
	 * 
	 * Given that I have entered a valid location
	 * When I select a vehicle with more than one option package
	 * Then the interface should allow me to select an option
	 * And it should echo my choices on the right
	 */
	@Test
	public void vehicleWithOptions() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.OPTION, true);
		
		logger.info("Step 1: select vehicle with multiple options");
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("Vehicle in use: " + vehicle);
		vehiclePage.selectVehicle(vehicle);
		
		//Verify the display on the right
		assertEquals("Make not displayed correctly", vehicle.make + ":", vehiclePage.getDisplayedMake());
		assertEquals("Year/model not displayed correctly", vehicle.year + " " + vehicle.model, vehiclePage.getDisplayedModel());
		assertEquals("Trim not displayed correctly", "Trim: " + vehicle.trim, vehiclePage.getDisplayedTrim());
		assertEquals("Option not displayed correctly", "Option: " + vehicle.option, vehiclePage.getDisplayedOption());
		assertTrue("No vehicle image displayed", vehiclePage.isVehicleImageVisible());
		
	
	}
	
	/**
	 * Test that the dropdowns allow the entry of a vehicle without options.
	 * 
	 * Given that I have entered a valid location
	 * When I select a vehicle with no option choices
	 * Then the interface should not allow me to select an option
	 * And it should echo my choices on the right
	 * And it should not display an option on the right
	 */
	@Test
	public void vehicleWithoutOptions() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.OPTION, false);
		
		logger.info("Step 1: select vehicle without options");
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("Vehicle in use: " + vehicle);
		vehiclePage.selectVehicle(vehicle);
		
		//Verify the display on the right
		assertEquals("Make not displayed correctly", vehicle.make + ":", vehiclePage.getDisplayedMake());
		assertEquals("Year/model not displayed correctly", vehicle.year + " " + vehicle.model, vehiclePage.getDisplayedModel());
		assertEquals("Trim not displayed correctly", "Trim: " + vehicle.trim, vehiclePage.getDisplayedTrim());
		assertEquals("Option not displayed correctly", null, vehiclePage.getDisplayedOption());
		assertTrue("No vehicle image displayed", vehiclePage.isVehicleImageVisible());
	}
	
	/**
	 * Verifies that the trivia box is always displayed as the page is manipulated.
	 * 
	 * Given that I have entered a location
	 * And I am on the vehicle page
	 * When I make selections in the dropdown boxes
	 * Then the trivia box should not vanish
	 */
	@Test
	public void triviaAlwaysDisplayed() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.OPTION, true);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("Vehicle in use: " + vehicle);
		
		logger.info("Step 1: select year");
		vehiclePage.selectYear(vehicle.year);
		assertTrue("Trivia bucket vanished", vehiclePage.isTriviaBucketVisible());
		
		logger.info("Step 2: select make");
		vehiclePage.selectMake(vehicle.make);
		assertTrue("Trivia bucket vanished", vehiclePage.isTriviaBucketVisible());
		
		logger.info("Step 3: select model");
		vehiclePage.selectModel(vehicle.model);
		assertTrue("Trivia bucket vanished", vehiclePage.isTriviaBucketVisible());
		
		logger.info("Step 4: select trim");
		vehiclePage.selectTrim(vehicle.trim);
		assertTrue("Trivia bucket vanished", vehiclePage.isTriviaBucketVisible());
		
		logger.info("Step 5: select option");
		vehiclePage.selectOption(vehicle.option);
		assertTrue("Trivia bucket vanished", vehiclePage.isTriviaBucketVisible());
		
	}
	
	/**
	 * Verifies that the next button only appears when all necessary information is entered,
	 * and that it takes one to the Verify page.
	 * 
	 * Given that I have entered a valid location
	 * When I select a year from the dropdown
	 * Then I should not be able to continue
	 * And I should be able to select a make
	 * 
	 * When I select a make
	 * Then I should not be able to continue
	 * And I should be able to select a model
	 * 
	 * When I select a model
	 * Then I should not be able to continue
	 * And I should be able to select a trim
	 * 
	 * When I select a trim
	 * Then I should be able to continue
	 */
	@Test
	public void nextLinkNoOptions() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		criteriaList.put(Criteria.OPTION, false);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("Vehicle in use: " + vehicle);
		
		assertFalse("Should not be able to continue", vehiclePage.canGoOn());
		
		logger.info("Step 1: select year");
		vehiclePage.selectYear(vehicle.year);
		assertFalse("Should not be able to continue", vehiclePage.canGoOn());
		
		logger.info("Step 2: select make");
		vehiclePage.selectMake(vehicle.make);
		assertFalse("Should not be able to continue", vehiclePage.canGoOn());
		
		logger.info("Step 3: select model");
		vehiclePage.selectModel(vehicle.model);
		assertFalse("Should not be able to continue", vehiclePage.canGoOn());
		
		logger.info("Step 4: select trim");
		vehiclePage.selectTrim(vehicle.trim);
		assertTrue("Should be able to continue", vehiclePage.canGoOn());
	
		logger.info("Step 5: click next");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		//Verify the display on the verify page
		assertEquals("Incorrect make on verify page", vehicle.make + ":", verifyPage.getDisplayedMake());
		assertEquals("Incorrect year/model on verify page", vehicle.year + " " + vehicle.model, verifyPage.getDisplayedModel());
		assertEquals("Incorrect trim on verify page", "Trim: " + vehicle.trim, verifyPage.getDisplayedTrim());
		assertEquals("Incorrect option on verify page", vehicle.option, verifyPage.getDisplayedOption());
		assertTrue("Missing image on verify page", verifyPage.isVehicleImageVisible());
	}
	
	/**
	 * Verifies that the next button only appears when all necessary information is entered,
	 * and that it takes one to the Verify page.
	 * 
	 * Given that I have entered a valid location
	 * When I select a year from the dropdown
	 * Then I should not be able to continue
	 * And I should be able to select a make
	 * 
	 * When I select a make
	 * Then I should not be able to continue
	 * And I should be able to select a model
	 * 
	 * When I select a model
	 * Then I should not be able to continue
	 * And I should be able to select a trim
	 * 
	 * When I select a trim
	 * And there are options availible to select
	 * Then I should not be able to continue
	 * And I should be able to select an option
	 * 
	 * When I select an option
	 * Then I should be able to continue
	 */
	@Test
	public void nextLinkWithOptions() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		criteriaList.put(Criteria.OPTION, true);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("Vehicle in use: " + vehicle);
		
		assertFalse("Should not be able to continue", vehiclePage.canGoOn());
		
		logger.info("Step 1: select year");
		vehiclePage.selectYear(vehicle.year);
		assertFalse("Should not be able to continue", vehiclePage.canGoOn());
		
		logger.info("Step 2: select make");
		vehiclePage.selectMake(vehicle.make);
		assertFalse("Should not be able to continue", vehiclePage.canGoOn());
		
		logger.info("Step 3: select model");
		vehiclePage.selectModel(vehicle.model);
		assertFalse("Should not be able to continue", vehiclePage.canGoOn());
		
		logger.info("Step 4: select trim");
		vehiclePage.selectTrim(vehicle.trim);
		assertFalse("Should not be able to continue", vehiclePage.canGoOn());
		
		logger.info("Step 5: select option");
		vehiclePage.selectOption(vehicle.option);
		assertTrue("Should be able to continue", vehiclePage.canGoOn());
	
		logger.info("Step 6: click next");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		//Verify the display on the verify page
		assertEquals("Incorrect make on verify page", vehicle.make + ":", verifyPage.getDisplayedMake());
		assertEquals("Incorrect year/model on verify page", vehicle.year + " " + vehicle.model, verifyPage.getDisplayedModel());
		assertEquals("Incorrect trim on verify page", "Trim: " + vehicle.trim, verifyPage.getDisplayedTrim());
		assertEquals("Incorrect option on verify page", vehicle.option, verifyPage.getDisplayedOption());
		assertTrue("Missing image on verify page", verifyPage.isVehicleImageVisible());
	}
	
	/**
	 * Verifies that the back button takes the user to the Location page, and that it is always available
	 * 
	 * Given I have entered a valid location
	 * And I am on the vehicle page
	 * When I click the back button
	 * Then I should return to the location page
	 * 
	 * Given that I have entered a valid location
	 * And I am on the vehicle page
	 * When I select a year
	 * Then I should be able to click the back button
	 * 
	 * When I select a make
	 * Then I should still be able to click the back button
	 * 
	 * When I select a model
	 * Then I should still be able to click the back button
	 * 
	 * When I select a trim
	 * Then I should still be able to click the back button
	 * 
	 * When I select an option
	 * Then I should still be able to click the back button
	 */
	@Test
	public void backLink() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		criteriaList.put(Criteria.OPTION, true);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("Vehicle in use: " + vehicle);
	
		logger.info("Step 1: click back");
		assertTrue("Cannot go back!", vehiclePage.canGoBack());
		vehiclePage.clickBack();
		assertTrue("Wrong page loaded", locationPage.isLoaded());
		
		//Return to vehicle page
		logger.info("Step 2: Return to vehicle page");
		locationPage.enterZipCode("44114");
		locationPage.clickNext();
		
		assertTrue("Cannot go back!", vehiclePage.canGoBack());
		
		logger.info("Step 3: select year");
		vehiclePage.selectYear(vehicle.year);
		assertTrue("Cannot go back!", vehiclePage.canGoBack());
		
		logger.info("Step 4: select make");
		vehiclePage.selectMake(vehicle.make);
		assertTrue("Cannot go back!", vehiclePage.canGoBack());
		
		logger.info("Step 5: select model");
		vehiclePage.selectModel(vehicle.model);
		assertTrue("Cannot go back!", vehiclePage.canGoBack());
		
		logger.info("Step 6: select trim");
		vehiclePage.selectTrim(vehicle.trim);
		assertTrue("Cannot go back!", vehiclePage.canGoBack());
		
		logger.info("Step 7: select option");
		vehiclePage.selectOption(vehicle.option);
		assertTrue("Cannot go back!", vehiclePage.canGoBack());
	}
	
	/**
	 * Tests that the verify page displays a single size correctly
	 * 
	 * Given that I have entered a valid location
	 * And I am on the vehicle page
	 * When I select a vehicle with one tire size
	 * And I click next
	 * Then the verify page should contain the vehicle I selected
	 * And it should display the correct size
	 */
	@Test
	public void verifyOneSize() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		criteriaList.put(Criteria.STAGGERED, false);
		criteriaList.put(Criteria.OPTION, true);
		
		logger.info("Step 1: Select vehicle");
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("Vehicle in use: " + vehicle);
		String size = vehicle.sizes.get(0);
		
		vehiclePage.selectVehicle(vehicle);
		
		logger.info("Step 2: Click next");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
	
		//Verify the display on the verify page
		assertEquals("Incorrect make on verify page", vehicle.make + ":", verifyPage.getDisplayedMake());
		assertEquals("Incorrect year/model on verify page", vehicle.year + " " + vehicle.model, verifyPage.getDisplayedModel());
		assertEquals("Incorrect trim on verify page", "Trim: " + vehicle.trim, verifyPage.getDisplayedTrim());
		assertEquals("Incorrect option on verify page", vehicle.option, verifyPage.getDisplayedOption());
		assertTrue("Missing image on verify page", verifyPage.isVehicleImageVisible());
				
		ArrayList<String> displayedSizes = verifyPage.getDisplayedSizes();
		assertEquals("Wrong number of sizes displayed", 1,displayedSizes.size()); //only one size
		assertEquals("Wrong size displayed", size,displayedSizes.get(0));
	}
	
	/**
	 * Tests that the verify page displays a single size correctly
	 * 
	 * Given that I have entered a valid location
	 * And I am on the vehicle page
	 * When I select a vehicle with one tire size and no options
	 * And I click next
	 * Then the verify page should contain the vehicle I selected
	 * And it should display the correct size
	 */
	@Test
	public void verifyNoOption() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		criteriaList.put(Criteria.STAGGERED, false);
		criteriaList.put(Criteria.OPTION, false);
		
		logger.info("Step 1: Select vehicle");
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("Vehicle in use: " + vehicle);
		String size = vehicle.sizes.get(0);
		
		vehiclePage.selectVehicle(vehicle);
		
		logger.info("Step 2: Click next");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
	
		//Verify the display on the verify page
		assertEquals("Incorrect make on verify page", vehicle.make + ":", verifyPage.getDisplayedMake());
		assertEquals("Incorrect year/model on verify page", vehicle.year + " " + vehicle.model, verifyPage.getDisplayedModel());
		assertEquals("Incorrect trim on verify page", "Trim: " + vehicle.trim, verifyPage.getDisplayedTrim());
		assertEquals("Incorrect option on verify page", null, verifyPage.getDisplayedOption());
		assertTrue("Missing image on verify page", verifyPage.isVehicleImageVisible());
				
		ArrayList<String> displayedSizes = verifyPage.getDisplayedSizes();
		assertEquals("Wrong number of sizes displayed", 1,displayedSizes.size()); //only one size
		assertEquals("Wrong size displayed", size,displayedSizes.get(0));
	}
	
	/**
	 * Tests that the verify page displays a staggered set correctly
	 * 
	 * Given that I have entered a valid location
	 * And I am on the vehicle page
	 * When I select a vehicle with one staggered tire size
	 * And I click next
	 * Then the verify page should contain the vehicle I selected
	 * And it should display the correct front and rear sizes
	 */
	@Test
	public void verifyStaggered() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		criteriaList.put(Criteria.STAGGERED, true);
		criteriaList.put(Criteria.OPTION, true);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		String sizeFront = vehicle.sizes.get(0);
		String sizeRear = vehicle.sizes.get(1);
		
		logger.info("Step 1: Select vehicle");
		logger.info("Vehicle in use: " + vehicle);
		
		vehiclePage.selectVehicle(vehicle);
		
		logger.info("Step 2: Click next");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
	
		//Verify the display on the verify page
		assertEquals("Incorrect make on verify page", vehicle.make + ":", verifyPage.getDisplayedMake());
		assertEquals("Incorrect year/model on verify page", vehicle.year + " " + vehicle.model, verifyPage.getDisplayedModel());
		assertEquals("Incorrect trim on verify page", "Trim: " + vehicle.trim, verifyPage.getDisplayedTrim());
		assertEquals("Incorrect option on verify page", null, verifyPage.getDisplayedOption());
		assertTrue("Missing image on verify page", verifyPage.isVehicleImageVisible());
		
		ArrayList<String> displayedSizes = verifyPage.getDisplayedSizes();
		assertEquals("Incorrect number of sizes displayed", 2,displayedSizes.size());
		assertEquals("Incorrect front size displayed", sizeFront, displayedSizes.get(0));
		assertEquals("Incorrect rear size displayed", sizeRear, displayedSizes.get(1));
	}
	
	/**
	 * Test that runflat tires are displayed correctly on the verify page
	 * 
	 * Given that I have entered a valid location
	 * And I am on the vehicle page
	 * When I select a vehicle with one tire size and runflat tires
	 * And I click next
	 * Then the verify page should contain the vehicle I selected
	 * And it should inform me that runflats are available
	 */
	@Test
	public void verifyRunflat() {
		//Get vehicle
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.RUNFLAT, true);
		criteriaList.put(Criteria.MULTISIZE, false);
		criteriaList.put(Criteria.STAGGERED, false);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		
		logger.info("Step 1: Select vehicle");
		logger.info("Vehicle in use: " + vehicle);
		
		vehiclePage.selectVehicle(vehicle);
		
		logger.info("Step 2: Click next");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		assertTrue("Runflat should be displayed", verifyPage.isRunFlatAvailible());		
	}
	
	/**
	 * Test that runflat tires in a staggered set are displayed correctly on the verify page
	 * 
	 * Given that I have entered a valid location
	 * And I am on the vehicle page
	 * When I select a vehicle with one staggered tire size and runflat tires
	 * And I click next
	 * Then the verify page should contain the vehicle I selected
	 * And it should inform me that runflats are available
	 */
	@Test
	public void verifyRunflatStaggered() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.RUNFLAT, true);
		criteriaList.put(Criteria.MULTISIZE, false);
		criteriaList.put(Criteria.STAGGERED, true);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		
		logger.info("Step 1: Select vehicle");
		logger.info("Vehicle in use: " + vehicle);
		
		vehiclePage.selectVehicle(vehicle);
		
		logger.info("Step 2: Click next");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
			
		assertTrue("Runflat should be displayed", verifyPage.isRunFlatAvailible());
	}
	
	/**
	 * Test that runflat tires in a staggered set are displayed correctly on the verify page
	 * 
	 * Given that I have entered a valid location
	 * And I am on the vehicle page
	 * When I select a vehicle with one tire size and no runflat tires
	 * And I click next
	 * Then the verify page should contain the vehicle I selected
	 * And it should not inform me that runflats are available
	 */
	@Test
	public void verifyNoRunflat() {
		//Get vehicle
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.RUNFLAT, false);
		criteriaList.put(Criteria.MULTISIZE, false);
		criteriaList.put(Criteria.STAGGERED, false);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);		
		logger.info("Step 1: Select vehicle");
		logger.info("Vehicle in use: " + vehicle);
		
		vehiclePage.selectVehicle(vehicle);
		
		logger.info("Step 2: Click next");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		assertFalse("Runflat should not be displayed", verifyPage.isRunFlatAvailible());
	}
	
	/**
	 * Given that I have entered a valid location
	 * And I am on the vehicle page
	 * When I select a vehicle which has multiple sizes
	 * And I click next
	 * Then the verify page should show me multiple sizes
	 * And I should be able to select one
	 * And I should not be able to continue
	 * When I select a size
	 * Then I should be able to continue
	 */
	@Test
	public void verifyPageMultiSize() {
		//Get vehicle
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, true);
		criteriaList.put(Criteria.STAGGERED, false);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);		
		logger.info("Step 1: Select vehicle");
		logger.info("Vehicle in use: " + vehicle);
		
		vehiclePage.selectVehicle(vehicle);
		
		logger.info("Step 2: Click next");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		assertTrue("Size radio button should be displayed", verifyPage.isShowingSizeRadioButton());
		for (String size : vehicle.sizes) {
			assertTrue("Size " + size + " should be displayaed for this vehicle", verifyPage.isSizeDisplayed(size));
		}
		assertFalse("Should not be able to continue", verifyPage.canGoOn());
		
		logger.info("Step 3: Select size");
		verifyPage.selectSize(vehicle.sizes.get(0));
		assertTrue("Should be able to continue now", verifyPage.canGoOn());
	}
	
	/**
	 * Given that I have entered a valid location
	 * And I am on the vehicle page
	 * When I select a vehicle which has multiple staggered sizes
	 * And I click next
	 * Then the verify page should show me multiple staggered sizes
	 * And I should be able to select one
	 * And I should not be able to continue
	 * When I select a size
	 * Then I should be able to continue
	 */
	@Test
	public void verifyMultiSizeStaggered() {
		//Get vehicle
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, true);
		criteriaList.put(Criteria.STAGGERED, true);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);		
		logger.info("Step 1: Select vehicle");
		logger.info("Vehicle in use: " + vehicle);
		
		vehiclePage.selectVehicle(vehicle);
		
		logger.info("Step 2: Click next");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		assertTrue("Size radio button should be displayed", verifyPage.isShowingSizeRadioButton());
		for (String size : vehicle.sizes) {
			assertTrue("Size " + size + " should be displayaed for this vehicle", verifyPage.isSizeDisplayed(size));
		}
		assertFalse("Should not be able to continue", verifyPage.canGoOn());
		
		logger.info("Step 3: Select size");
		verifyPage.selectSize(vehicle.sizes.get(0));
		assertTrue("Should be able to continue now", verifyPage.canGoOn());
	}
}
