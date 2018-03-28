package com.dealertire.RightTurnTesting;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.dealertire.RightTurnFramework.BaseTest;
import com.dealertire.RightTurnFramework.PageObject;
import com.dealertire.RightTurnFramework.Models.Size;
import com.dealertire.RightTurnFramework.Models.StaggeredProduct;
import com.dealertire.RightTurnFramework.Models.Vehicle;
import com.dealertire.RightTurnFramework.Models.Product;
import com.dealertire.RightTurnFramework.Models.Vehicle.Criteria;
import com.dealertire.RightTurnFramework.Pages.HomePage;
import com.dealertire.RightTurnFramework.Pages.LocationPage;
import com.dealertire.RightTurnFramework.Pages.ProductPage;
import com.dealertire.RightTurnFramework.Pages.TireCoachPage;
import com.dealertire.RightTurnFramework.Pages.VehiclePage;
import com.dealertire.RightTurnFramework.Pages.VehiclePage.Tab;
import com.dealertire.RightTurnFramework.Pages.VerifyPage;

/**
 * Feature: Search by Size
 * @author bgreen
 */
public class SearchBySizeTests extends BaseTest {

	/**
     * @see com.dealertire.RightTurnFramework.BaseTest#BaseTest(String, String, String)
     */
	public SearchBySizeTests(String os, String browserName,String browserVersion) {
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
	 * Given I have entered a valid location
	 * When I reach the vehicle page
	 * Then I should see a tab for “search by size”
	 * When I click that tab
	 * Then I should be able to enter a size
	 * When I enter a size
	 * Then I should be able to continue
	 */
	@Test
	public void SearchBySizeTab() {
		Map<Size.Criteria, Boolean> sCriteriaList = new HashMap<Size.Criteria, Boolean>();
		sCriteriaList.put(Size.Criteria.FITMENT, true);
		Size size = Size.getRandomSizeByCriteria(sCriteriaList);
		logger.info("SETUP: Using size" + size);
		
		logger.info("Step 1: Select size tab");
		vehiclePage.selectTab(Tab.SIZE_SELECT);
		assertFalse("Shuold not be able to continue on", vehiclePage.canGoOn());
		
		logger.info("Step 2: Select size");
		vehiclePage.selectSize(size);
		assertTrue("Should be able to continue on", vehiclePage.canGoOn());
	}
	
	/***
	 * Given this is my first time to the site
	 * When I reach the vehicle page
	 * Then I should see the “search by vehicle” tab selected
	 */
	@Test
	public void defaultTabFirstTime() {
		assertEquals("Default tab should be Vehicle", Tab.VEHICLE_SELECT,vehiclePage.getSelectedTab());
	}
	
	/**
	 * Given I have selected a vehicle
	 * And I am on the verify page
	 * When click “Your vehicle”
	 * Then I should see the “search by vehicle” tab selected
	 */
	@Test
	public void defaultTabVehicleSearch() {
		logger.info("Step 1: selecting vehicle");
		Vehicle vehicle = Vehicle.getRandomVehicle();
		vehiclePage.selectVehicle(vehicle);
		logger.info("Using vehicle: " + vehicle);
		
		logger.info("Step 2: navigating to Verify page");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		logger.info("Step 3: click back");
		vehiclePage = (VehiclePage) verifyPage.clickBack();
		
		assertEquals("Default tab should be vehicle", Tab.VEHICLE_SELECT,vehiclePage.getSelectedTab());
	}
	
	/**
	 * Given I have selected a vehicle
	 * And I have triggered a PUWYLO modal
	 * When click continue
	 * And I navigate back to the vehicle page
	 * Then I should see the “search by vehicle” tab selected
	 */
	@Test
	public void defaultTabPUWYLOVehicleSearch() {
		logger.info("Step 1: Select vehicle");
		Vehicle vehicle = Vehicle.getRandomVehicle();
		vehiclePage.selectVehicle(vehicle);
		logger.info("Using vehicle: " + vehicle);
		
		logger.info("Step 2: navigate to verify page");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		//Navigate away
		logger.info("Step 3: Navigate away");
		driver.navigate().to("http://www.google.com");
		
		//Come back
		logger.info("Step 4: Navigate back to home page");
		driver.navigate().to(rootURL);
		
		homePage = new HomePage(driver);
		homePage.waitForPageLoad();
		puwyloModal = homePage.getPUWLOModal();
		puwyloModal.waitForModal();
		
		logger.info("Step 5: Click yes on PUWYLO modal");
		PageObject currPage = puwyloModal.clickYes();
		assertTrue("Verify page did not appear when modal accepted", currPage instanceof VerifyPage);
		
		logger.info("Step 6: Click back");
		verifyPage.clickBack();
		
		assertEquals("Default tab should be vehicle", Tab.VEHICLE_SELECT,vehiclePage.getSelectedTab());
	}
	
	/**
	 * Given I have selected a vehicle
	 * And I have triggered a PUWYLO modal
	 * When click “I would like to start over”
	 * And I enter a valid zip code
	 * And I click “next”
	 * Then I should see the “search by vehicle” tab selected
	 */
	@Test
	public void defaultTabPUWYLOVehicleClickNo() {
		logger.info("Step 1: Select vehicle");
		Vehicle vehicle = Vehicle.getRandomVehicle();
		vehiclePage.selectVehicle(vehicle);
		logger.info("Using vehicle: " + vehicle);
		
		logger.info("Step 2: navigate to verify page");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		//Navigate away
		logger.info("Step 3: Navigate away");
		driver.navigate().to("http://www.google.com");
		
		//Come back
		logger.info("Step 4: Navigate back to home page");
		driver.navigate().to(rootURL);
		
		homePage = new HomePage(driver);
		homePage.waitForPageLoad();
		puwyloModal = homePage.getPUWLOModal();
		puwyloModal.waitForModal();
		
		logger.info("Step 5: Click no on PUWYLO modal");
		puwyloModal.clickNo();
		
		logger.info("Step 6: Navigate back to Vehicle page");
		locationPage.navigateTo();
		locationPage.enterZipCode("44114");
		locationPage.clickNext();
		
		assertEquals("Default tab should be vehicle", Tab.VEHICLE_SELECT,vehiclePage.getSelectedTab());
	}
	
	/**
	 * Given I have selected a size
	 * And I have triggered a PUWYLO modal
	 * When click continue
	 * And I navigate back to the vehicle page
	 * Then I should see the “search by vehicle” tab selected
	 */
	@Test
	public void defaultTabPUWYLOSize() {
		logger.info("Step 1: Select size");
		Map<Size.Criteria, Boolean> sCriteriaList = new HashMap<Size.Criteria, Boolean>();
		sCriteriaList.put(Size.Criteria.STAGGERED, false);
		Size size = Size.getRandomSizeByCriteria(sCriteriaList);
		logger.info("Using size: " + size);
		
		vehiclePage.selectTab(Tab.SIZE_SELECT);
		vehiclePage.selectSize(size.make, size.toString());
		
		logger.info("Step 2: navigate to verify page");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		//Navigate away
		logger.info("Step 3: Navigate away");
		driver.navigate().to("http://www.google.com");
		
		//Come back
		logger.info("Step 4: Navigate back to home page");
		driver.navigate().to(rootURL);
		
		homePage = new HomePage(driver);
		homePage.waitForPageLoad();
		puwyloModal = homePage.getPUWLOModal();
		puwyloModal.waitForModal();
		
		logger.info("Step 5: Click yes");
		verifyPage = (VerifyPage) puwyloModal.clickYes();
		
		logger.info("Step 6: Navigate back to vehicle page");
		verifyPage.clickBack();
		
		assertEquals("Default tab should be size", Tab.SIZE_SELECT,vehiclePage.getSelectedTab());
	}
	
	/**
	 * Given I have selected a size
	 * And I have triggered a PUWYLO modal
	 * When click continue
	 * And I navigate back to the vehicle page
	 * Then I should see the “search by vehicle” tab selected
	 */
	@Test
	public void defaultTabPUWYLOSizeClickNo() {
		logger.info("Step 1: Select size");
		Map<Size.Criteria, Boolean> sCriteriaList = new HashMap<Size.Criteria, Boolean>();
		sCriteriaList.put(Size.Criteria.STAGGERED, false);
		Size size = Size.getRandomSizeByCriteria(sCriteriaList);
		logger.info("Using size: " + size);
		
		vehiclePage.selectTab(Tab.SIZE_SELECT);
		vehiclePage.selectSize(size.make, size.toString());
		
		logger.info("Step 2: navigate to verify page");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		//Navigate away
		logger.info("Step 3: Navigate away");
		driver.navigate().to("http://www.google.com");
		
		//Come back
		logger.info("Step 4: Navigate back to home page");
		driver.navigate().to(rootURL);
		
		homePage = new HomePage(driver);
		homePage.waitForPageLoad();
		puwyloModal = homePage.getPUWLOModal();
		puwyloModal.waitForModal();
		
		logger.info("Step 5: Click no on PUWYLO modal");
		puwyloModal.clickNo();
		
		logger.info("Step 6: Navigate back to Vehicle page");
		locationPage.navigateTo();
		locationPage.enterZipCode("44114");
		locationPage.clickNext();
		
		assertEquals("Default tab should be vehicle", Tab.VEHICLE_SELECT,vehiclePage.getSelectedTab());
	}
	
	/**
	 * Given I have selected a size
	 * And I am on the verify page
	 * When click “Your vehicle”
	 * Then I should see the “search by size” tab selected
	 */
	@Test
	public void defaultTabSizeSearch() {
		logger.info("Step 1: Select size");
		Map<Size.Criteria, Boolean> criteriaList = new HashMap<Size.Criteria, Boolean>();
		criteriaList.put(Size.Criteria.STAGGERED, false);
		criteriaList.put(Size.Criteria.RUNFLAT, false);
		Size size = Size.getRandomSizeByCriteria(criteriaList);
		
		logger.info("Using size: " + size);
		
		vehiclePage.selectTab(Tab.SIZE_SELECT);
		vehiclePage.selectSize(size);
		
		logger.info("Step 2: Click next");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		logger.info("Step 3: Click back");
		verifyPage.clickBack();
		
		assertEquals("Default tab should be size", Tab.SIZE_SELECT,vehiclePage.getSelectedTab());
	}


	/**
	 * Given I am on the vehicle page
	 * And I have entered a vehicle
	 * And I have entered a size on the size tab
	 * When I click the “Search by Vehicle” tab
	 * Then I should see the vehicle I previously entered
	 * When I click the “search by size” tab
	 * Then I should see the size I previously entered
	 */
	@Test
	public void tabsRememberInformation() {
		Map<Criteria, Boolean> vCriteriaList = new HashMap<Criteria, Boolean>();
		vCriteriaList.put(Criteria.OPTION, true);		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(vCriteriaList);
		logger.info("Using vehicle: " + vehicle);
		
		Map<Size.Criteria, Boolean> sCriteriaList = new HashMap<Size.Criteria, Boolean>();
		sCriteriaList.put(Size.Criteria.STAGGERED, false);
		sCriteriaList.put(Size.Criteria.RUNFLAT, false);
		Size size = Size.getRandomSizeByCriteria(sCriteriaList);
		logger.info("Using size: " + size);
		
		logger.info("Step 1: Enter vehicle information");
		vehiclePage.selectVehicle(vehicle);
		
		logger.info("Step 2: Switch to Size tab");
		vehiclePage.selectTab(Tab.SIZE_SELECT);
		
		logger.info("Step 3: Enter size");
		vehiclePage.selectSize(size);
		
		logger.info("Step 4: Switch back to Vehicle tab");
		vehiclePage.selectTab(Tab.VEHICLE_SELECT);

		assertEquals("Vehicle year should be as entered", vehicle.year, vehiclePage.getSelectedYear());
		assertEquals("Vehicle make should be as entered",vehicle.make, vehiclePage.getSelectedMake());
		assertEquals("Vehicle model should be as entered",vehicle.model, vehiclePage.getSelectedModel());
		assertEquals("Vehicle trim should be as entered",vehicle.trim, vehiclePage.getSelectedTrim());
		assertEquals("Vehicle option should be as entered",vehicle.option, vehiclePage.getSelectedOption());
		
		logger.info("Step 5: Switch back to Size tab");
		vehiclePage.selectTab(Tab.SIZE_SELECT);
		
		assertEquals("Size width should be as entered", size.width, vehiclePage.getSelectedWidth());
		assertEquals("Size diameter should be as entered", size.diameter, vehiclePage.getSelectedDiameter());
		assertEquals("Size ratio should be as entered", size.ratio, vehiclePage.getSelectedRatio());
	}
	
	/**
	 * Given I am on the vehicle page
	 * And I have entered a vehicle
	 * And I have entered a size on the size tab
	 * When I click “Your vehicle”
	 * And I enter a location
	 * And I click “Next”
	 * Then I should not see my previous vehicle selected
	 * And I should not see my previous size selected
	 */
	@Test
	public void pageChangesDoNotRememberInformation() {
		Map<Criteria, Boolean> vCriteriaList = new HashMap<Criteria, Boolean>();
		vCriteriaList.put(Criteria.OPTION, true);		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(vCriteriaList);
		logger.info("Using vehicle: " + vehicle);
		
		Map<Size.Criteria, Boolean> sCriteriaList = new HashMap<Size.Criteria, Boolean>();
		sCriteriaList.put(Size.Criteria.STAGGERED, false);
		sCriteriaList.put(Size.Criteria.RUNFLAT, false);
		Size size = Size.getRandomSizeByCriteria(sCriteriaList);
		logger.info("Using size: " + size);
		
		logger.info("Step 1: Enter vehicle information");
		vehiclePage.selectVehicle(vehicle);
		
		logger.info("Step 2: Switch to Size tab");
		vehiclePage.selectTab(Tab.SIZE_SELECT);
		
		logger.info("Step 3: Enter size");
		vehiclePage.selectSize(size);
		
		logger.info("Step 4: Go back to the Location page using the top header");
		locationPage = (LocationPage) vehiclePage.clickYourVehicle();
		
		logger.info("Step 5: Navigate back to Vehicle page");
		locationPage.enterZipCode("44114");
		vehiclePage = (VehiclePage) locationPage.clickNext();

		vehiclePage.selectTab(Tab.VEHICLE_SELECT);
		assertNotEquals("Vehicle year should not be as entered", vehicle.year, vehiclePage.getSelectedYear());
		assertNotEquals("Vehicle make should not be as entered", vehicle.make, vehiclePage.getSelectedMake());
		assertNotEquals("Vehicle model should not be as entered", vehicle.model, vehiclePage.getSelectedModel());
		assertNotEquals("Vehicle trim should not be as entered", vehicle.trim, vehiclePage.getSelectedTrim());
		assertNotEquals("Vehicle option should not be as entered", vehicle.option, vehiclePage.getSelectedOption());
		
		vehiclePage.selectTab(Tab.SIZE_SELECT);
		assertNotEquals("Size width should not be as entered", size.width, vehiclePage.getSelectedWidth());
		assertNotEquals("Size diameter should not be as entered", size.diameter, vehiclePage.getSelectedDiameter());
		assertNotEquals("Size ratio should not be as entered", size.ratio, vehiclePage.getSelectedRatio());
	}
	
	
	/**
	 * Given that I have entered a valid location 
	 * And I am entering a size without a service description
	 * When I click the “search by size” tab
	 * Then I should be able to select a make
	 * When I select a make
	 * Then I should not be able to continue
	 * And I should be able to select a width
	 * When I select a width
	 * Then I should not be able to continue
	 * And I should be able to select an aspect ratio
	 * When I select an aspect ratio
	 * Then I should not be able to continue
	 * And I should be able to select a diameter
	 * When I select a diameter
	 * Then I should be able to continue
	 */
	@Test
	public void nextLink() {
		Map<Size.Criteria, Boolean> sCriteriaList = new HashMap<Size.Criteria, Boolean>();
		sCriteriaList.put(Size.Criteria.FITMENT, true);
		Size size = Size.getRandomSizeByCriteria(sCriteriaList);
		logger.info("Using size: " + size);
		
		logger.info("Step 1: Change to Size tab");
		vehiclePage.selectTab(Tab.SIZE_SELECT);
		assertFalse("Should not be able to continue", vehiclePage.canGoOn());
		
		logger.info("Step 2: Enter make");
		vehiclePage.selectMake(size.make);
		assertFalse("Should not be able to continue", vehiclePage.canGoOn());
		
		logger.info("Step 3: Enter width");
		vehiclePage.selectWidth(size.width);
		assertFalse("Should not be able to continue", vehiclePage.canGoOn());
		
		logger.info("Step 4: Enter ratio");
		vehiclePage.selectRatio(size.ratio);
		assertFalse("Should not be able to continue", vehiclePage.canGoOn());
		
		logger.info("Step 5: Enter diameter");
		vehiclePage.selectDiameter(size.diameter);
		assertTrue("Should be able to continue", vehiclePage.canGoOn());
		
		logger.info("Step 6: Click next");
		PageObject nextPage = vehiclePage.clickNext();
		assertTrue("Did not arrive at correct page", nextPage instanceof VerifyPage);
	}
	
	/**
	 * Given I have selected a size with both runflats and non-runflats
	 * When I reach the verify page
	 * Then I should see the question “Do you have runflats?"
	 * And I should not be able to continue
	 * When I select yes
	 * And I click next
	 * Then I should be on the verify page
	 * And it should say “Runflats: yes”
	 * When I continue on to the product page
	 * Then I should see runflat tires
	 */
	//@Test //Removed from suite due to code changes
	public void verifyRunflatYes() {
		Map<Size.Criteria, Boolean> sCriteriaList = new HashMap<Size.Criteria, Boolean>();
		sCriteriaList.put(Size.Criteria.RUNFLAT, true);
		sCriteriaList.put(Size.Criteria.STAGGERED, false);
		Size size = Size.getRandomSizeByCriteria(sCriteriaList);
		
		vehiclePage.selectTab(Tab.SIZE_SELECT);
		vehiclePage.selectSize(size);
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		assertTrue(verifyPage.isShowingRunflatRadioButton());
		
		verifyPage.selectRunflats(true);
		PageObject nextPage = verifyPage.clickNext();
		assertTrue(nextPage instanceof VerifyPage);
		verifyPage = (VerifyPage) nextPage;
		
		assertTrue(verifyPage.isRunFlatAvailible());
		
		//Navigate to product page
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		productPage = (ProductPage) verifyPage.clickNext();
		
		ArrayList<Product> runflatTires = productPage.getProductsByCriteria(Product.Criteria.RUNFLAT, true);
		assertFalse(runflatTires.isEmpty());

	}
	
	/**
	 * Given I have selected a size with both runflats and non-runflats
	 * When I reach the verify page
	 * Then I should see the question “Do you have runflats?”
	 * And I should not be able to continue
	 * When I select no
	 * And I click next
	 * Then I should be on the verify page
	 * And it should say “Runflats: no”
	 * When I continue on to the product page
	 * Then I should not see runflat tires
	 */
	//@Test //Removed from suite due to code changes
	public void verifyRunflatNo() {
		Map<Size.Criteria, Boolean> sCriteriaList = new HashMap<Size.Criteria, Boolean>();
		sCriteriaList.put(Size.Criteria.RUNFLAT, true);
		sCriteriaList.put(Size.Criteria.STAGGERED, false);
		Size size = Size.getRandomSizeByCriteria(sCriteriaList);
		
		vehiclePage.selectTab(Tab.SIZE_SELECT);
		vehiclePage.selectSize(size);
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		assertTrue(verifyPage.isShowingRunflatRadioButton());
		
		verifyPage.selectRunflats(false);
		PageObject nextPage = verifyPage.clickNext();
		assertTrue(nextPage instanceof VerifyPage);
		verifyPage = (VerifyPage) nextPage;
		
		assertFalse(verifyPage.isRunFlatAvailible());
		
		//Navigate to product page
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		productPage = (ProductPage) verifyPage.clickNext();
		
		ArrayList<Product> runflatTires = productPage.getProductsByCriteria(Product.Criteria.RUNFLAT, true);
		assertTrue(runflatTires.isEmpty());
	}
	
	/**
	 * Given I have selected a size that can be part of a staggered set
	 * When I reach the verify page
	 * Then I should see the question “Is your front tire a different size than your back tire?”
	 * And I should not be able to continue
	 * When I select yes
	 * And I click next
	 * Then I should be on the verify page
	 * And it should show me a staggered size
	 * When I continue on to the product page
	 * Then I should see staggered tires
	 */
	@Test
	public void verifyStaggeredYes() {
		Map<Size.Criteria, Boolean> sCriteriaList = new HashMap<Size.Criteria, Boolean>();
		sCriteriaList.put(Size.Criteria.RUNFLAT, false);
		sCriteriaList.put(Size.Criteria.STAGGERED, true);
		Size size = Size.getRandomSizeByCriteria(sCriteriaList);
		logger.info("Using size: " + size);
		
		logger.info("Step 1: select Size tab");
		vehiclePage.selectTab(Tab.SIZE_SELECT);
		
		logger.info("Step 2: enter size");
		vehiclePage.selectSize(size);
		
		logger.info("Step 3: Click next");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		assertTrue("Staggered size radio button should appear", verifyPage.isShowingStaggeredRadioButton());
		
		logger.info("Step 4: Indicate that staggered size is true");
		verifyPage.selectStaggered(true);
		
		logger.info("Step 5: Click next");
		PageObject nextPage = verifyPage.clickNext();
		assertTrue("Did not arrive on correct page", nextPage instanceof VerifyPage);
		verifyPage = (VerifyPage) nextPage;
		
		//TODO: verify staggered size is showing
		//assertTrue(verifyPage.isSizeDisplayed(size.toString()));
		
		//Navigate to product page
		logger.info("Step 6: Continue to product page");
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		productPage = (ProductPage) verifyPage.clickNext();
		
		logger.info("Step 7: Verify all products are staggered");
		List<Product> allProducts = productPage.getAllTires();
		for(Product prod : allProducts) {
			assertTrue(prod + " was not staggered!", prod instanceof StaggeredProduct);
		}
	}
	
	/**
	 * Given I have selected a size that can be part of a staggered set
	 * When I reach the verify page
	 * Then I should see the question “Is your front tire a different size than your back tire?”
	 * And I should not be able to continue
	 * When I select yes
	 * And I click next
	 * Then I should be on the verify page
	 * And it should show me a staggered size
	 * When I continue on to the product page
	 * Then I should see staggered tires
	 */
	@Test
	public void verifyStaggeredNo() {
		Map<Size.Criteria, Boolean> sCriteriaList = new HashMap<Size.Criteria, Boolean>();
		sCriteriaList.put(Size.Criteria.RUNFLAT, false);
		sCriteriaList.put(Size.Criteria.STAGGERED, true);
		Size size = Size.getRandomSizeByCriteria(sCriteriaList);
		logger.info("Using size: " + size);
		
		logger.info("Step 1: select Size tab");
		vehiclePage.selectTab(Tab.SIZE_SELECT);
		
		logger.info("Step 2: enter size");
		vehiclePage.selectSize(size);
		
		logger.info("Step 3: Click next");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		assertTrue("Staggered size radio button should appear", verifyPage.isShowingStaggeredRadioButton());
		
		logger.info("Step 4: Indicate that staggered size is not true");
		verifyPage.selectStaggered(false);
		logger.info("Step 5: Click next");
		PageObject nextPage = verifyPage.clickNext();
		assertTrue("Did not arrive on correct page", nextPage instanceof VerifyPage);
		verifyPage = (VerifyPage) nextPage;
		
		//TODO: verify staggered size is showing
		//assertTrue(verifyPage.isSizeDisplayed(size.toString()));
		
		//Navigate to product page
		logger.info("Step 6: Continue to product page");
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		productPage = (ProductPage) verifyPage.clickNext();
		
		logger.info("Step 7: Verify no products are staggered");
		List<Product> allProducts = productPage.getAllTires();
		for(Product prod : allProducts) {
			assertFalse(prod + " was staggered!", prod instanceof StaggeredProduct);
		}
	}
}
