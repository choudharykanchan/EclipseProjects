package com.dealertire.RightTurnTesting;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.dealertire.RightTurnFramework.BaseTest;
import com.dealertire.RightTurnFramework.PageObject;
import com.dealertire.RightTurnFramework.RTPageFactory;
import com.dealertire.RightTurnFramework.SessionManager;
import com.dealertire.RightTurnFramework.Utils;
import com.dealertire.RightTurnFramework.Models.Product;
import com.dealertire.RightTurnFramework.Models.Size;
import com.dealertire.RightTurnFramework.Models.Vehicle;
import com.dealertire.RightTurnFramework.Models.Vehicle.Criteria;
import com.dealertire.RightTurnFramework.Pages.HomePage;
import com.dealertire.RightTurnFramework.Pages.InstallPage;
import com.dealertire.RightTurnFramework.Pages.LocationPage;
import com.dealertire.RightTurnFramework.Pages.ProductPage;
import com.dealertire.RightTurnFramework.Pages.TireCoachPage;
import com.dealertire.RightTurnFramework.Pages.VehiclePage;
import com.dealertire.RightTurnFramework.Pages.VerifyPage;
import com.dealertire.RightTurnFramework.Pages.VehiclePage.Tab;

/**
 * Feature: Pick Up Where You Left Off
 * You can PUWYLO from the Verify page, Tire Coach page, Product page, and Installer page
 * This presents as a pop-up asking if you want to pick up where you left off
 * and presenting the vehicle you had selected
 * @author bgreen
 *
 */
public class PUWYLOTests extends BaseTest {

	/**
     * @see com.dealertire.RightTurnFramework.BaseTest#BaseTest(String, String, String)
     */
	public PUWYLOTests(String os, String browserName, String browserVersion) {
		super(os, browserName, browserVersion);
	}
	
	/**
	 * Test:
	 * Given I have reached the Verify page
	 * When I navigate away from the site
	 * And I return to the home page
	 * Then I should see a popup asking if I want to pick up where I left off
	 */
	@Test
	public void verifyPage() {

		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		
		if (SessionManager.restoreSession("verify", null, vehicle, driver)) {
			logger.info("SETUP: Restored saved session");
			verifyPage = (VerifyPage) RTPageFactory.getPageFromURL(driver.getCurrentUrl(), driver);
		} else {
			logger.warn("SETUP: Could not restore session; creating a fresh token.");
			//Navigate to location page
			logger.info("SETUP: Navigating to location page");
			locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
			locationPage.enterZipCode("44114");
			
			logger.info("SETUP: Navigating to vehicle page");
			vehiclePage = (VehiclePage) locationPage.clickNext();
			vehiclePage.selectVehicle(vehicle);
			
			logger.info("SETUP: Navigating to verify page");
			verifyPage = (VerifyPage) vehiclePage.clickNext();
			
			SessionManager.saveSession("verify", "44321", vehicle, driver);
		}

		//Navigate away
		logger.info("Step 1: Navigate away");
		driver.navigate().to("http://www.google.com");
		closeSurveyPopup();
		
		//Come back
		logger.info("Step 2: Navigate back to home page");
		driver.navigate().to(rootURL);
		
		homePage = new HomePage(driver);
		homePage.waitForPageLoad();
		
		assertTrue("Home page did not load", homePage.isLoaded());
		
		logger.info("Step 3: Verify PUWYLO modal appears");
		if (homePage.isShowingSurveyModal()) {
			logger.info("Dismissing survay popup");
			homePage.dismissSurveyModal();
		}
		puwyloModal = homePage.getPUWLOModal();
		puwyloModal.waitForModal();
		
		assertTrue("Modal did not appear", homePage.isShowingPuwyloModal());
		assertEquals("Modal pointed to the wrong section", "Vehicle Verification", puwyloModal.getSection());
		
		
		logger.info("Step 4: Click yes");
		if (homePage.isShowingSurveyModal()) {
			logger.info("Dismisisng survay popup");
			homePage.dismissSurveyModal();
		}
		PageObject currPage = puwyloModal.clickYes();
		assertTrue("Verify page did not appear when modal accepted", currPage instanceof VerifyPage);
	}
	
	/**
	 * Test:
	 * Given that I have entered a vehicle 
	 * And I have reached the Verify page
	 * When I navigate away from the site
	 * And I return to the home page
	 * Then I should see a popup asking if I want to pick up where I left off
	 * And it should display the vehicle I chose
	 */
	@Test
	public void correctVehicleDisplayed() {
		
		ArrayList<Vehicle> vehiclesToTest = Vehicle.findVehicles(Criteria.MULTISIZE, false);
		int numCarsToTest = vehiclesToTest.size();
		if (numCarsToTest > 5 ) numCarsToTest = 5;
		
		for (int i = 0; i < numCarsToTest; i++) {
			logger.info("Testing vehicle " + i + " of " + numCarsToTest);
			
			if (SessionManager.restoreSession("verify", null, vehiclesToTest.get(i), driver)) {
				logger.info("Restored saved session");
				verifyPage = (VerifyPage) RTPageFactory.getPageFromURL(driver.getCurrentUrl(), driver);
			} else {
				logger.warn("SETUP: Could not restore session; creating a fresh token.");
				//Navigate to location page
				logger.info("SETUP: Navigating to location page");
				locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
				locationPage.enterZipCode("44114");
				
				logger.info("SETUP: Navigating to vehicle page");
				vehiclePage = (VehiclePage) locationPage.clickNext();
				vehiclePage.selectVehicle(vehiclesToTest.get(i));
				
				logger.info("SETUP: Navigating to verify page");
				verifyPage = (VerifyPage) vehiclePage.clickNext();

				SessionManager.saveSession("verify", "44321", vehiclesToTest.get(i), driver);
			}
			//Navigate away
			logger.info("Step 1: Navigate away");
			driver.navigate().to("http://www.google.com");
			closeSurveyPopup();
			
			//Come back
			logger.info("Step 2: Navigate back to home page");
			driver.navigate().to(rootURL);
			
			homePage = new HomePage(driver);
			homePage.waitForPageLoad();
			
			assertTrue("Home page did not load", homePage.isLoaded());
			
			logger.info("Step 3: Verify PUWYLO modal appears");
			if (homePage.isShowingSurveyModal()) {
				logger.info("Dismissing survay popup");
				homePage.dismissSurveyModal();
			}
			puwyloModal = homePage.getPUWLOModal();
			puwyloModal.waitForModal();
			
			assertTrue("Modal did not appear", homePage.isShowingPuwyloModal());
			
			assertEquals("Wrong vehicle appeared", vehiclesToTest.get(i).year + " " + vehiclesToTest.get(i).make, puwyloModal.getMake());
			assertEquals("Wrong model appeared", vehiclesToTest.get(i).model, puwyloModal.getModel());
		}
		
	}
	
	/**
	 * Given I have selected a size
	 * And I am on the verify page
	 * When I navigate away from the site
	 * And I return to the site 
	 * Then I should see a modal asking if I want to pick up where I left off
	 * And I should see the correct size
	 */
	@Test
	public void correctSizeDisplayed() {
		Map<Size.Criteria, Boolean> sCriteriaList = new HashMap<Size.Criteria, Boolean>();
		sCriteriaList.put(Size.Criteria.STAGGERED, false);
		Size size = Size.getRandomSizeByCriteria(sCriteriaList);
		
		//Navigate to the Location page
		locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
		
		//Enter Zip code
		locationPage.enterZipCode("44321");
		vehiclePage = (VehiclePage) locationPage.clickNext();
		
		vehiclePage.selectTab(Tab.SIZE_SELECT);
		vehiclePage.selectSize(size);
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		//Navigate away
		logger.info("Step 1: Navigate away");
		driver.navigate().to("http://www.google.com");
		closeSurveyPopup();
		
		//Come back
		logger.info("Step 2: Navigate back to home page");
		driver.navigate().to(rootURL);
		
		homePage = new HomePage(driver);
		homePage.waitForPageLoad();
		
		assertTrue("Home page did not load", homePage.isLoaded());
		
		logger.info("Step 3: Verify PUWYLO modal appears");
		if (homePage.isShowingSurveyModal()) {
			logger.info("Dismissing survay popup");
			homePage.dismissSurveyModal();
		}
		puwyloModal = homePage.getPUWLOModal();
		puwyloModal.waitForModal();
		
		assertTrue("Modal did not appear", homePage.isShowingPuwyloModal());
		
		assertEquals("Wrong size appeared", size, puwyloModal.getSize());
		
	}
	
	/**
	 * Test:
	 * Given that I have reached the PUWYLO popup
	 * When I click "No" 
	 * Then the popup should go away
	 * And I should be on the home page
	 */
	@Test
	public void clickNo() {
		//Pick vehicle
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);

		if (SessionManager.restoreSession("verify", null, vehicle, driver)) {
			logger.info("Restored saved session");
			verifyPage = (VerifyPage) RTPageFactory.getPageFromURL(driver.getCurrentUrl(), driver);
		} else {
			logger.warn("SETUP: Could not restore session; creating a fresh token.");
			//Navigate to location page
			logger.info("SETUP: Navigating to location page");
			locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
			locationPage.enterZipCode("44114");
			
			logger.info("SETUP: Navigating to vehicle page");
			vehiclePage = (VehiclePage) locationPage.clickNext();
			vehiclePage.selectVehicle(vehicle);
			
			logger.info("SETUP: Navigating to verify page");
			verifyPage = (VerifyPage) vehiclePage.clickNext();
			
			SessionManager.saveSession("verify", "44321", vehicle, driver);
		}
		
		//Navigate away
		logger.info("Step 1: Navigate away");
		driver.navigate().to("http://www.google.com");
		closeSurveyPopup();
		
		//Come back
		logger.info("Step 2: Navigate back to home page");
		driver.navigate().to(rootURL);
		
		homePage = new HomePage(driver);
		homePage.waitForPageLoad();
		
		assertTrue("Home page did not load", homePage.isLoaded());
		
		logger.info("Step 3: Verify PUWYLO modal appears");
		if (homePage.isShowingSurveyModal()) {
			logger.info("Dismissing survay popup");
			homePage.dismissSurveyModal();
		}
		puwyloModal = homePage.getPUWLOModal();
		puwyloModal.waitForModal();
		
		assertTrue("Modal did not appear", homePage.isShowingPuwyloModal());
		assertEquals("Modal showed wrong section", "Vehicle Verification", puwyloModal.getSection());
		
		logger.info("Step 4: Click no");
		PageObject currPage = puwyloModal.clickNo();
		assertTrue("Home page did not remain in place when modal was rejected", currPage instanceof HomePage);
	}
	
	/**
	 * Test:
	 * Given that I have reached the PUWYLO popup
	 * When I click "close" 
	 * Then the popup should go away
	 * And I should be on the home page
	 */
	@Test
	public void clickClose() {
		//Pick vehicle
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		
		if (SessionManager.restoreSession("verify", null, vehicle, driver)) {
			logger.info("Restored saved session");
			verifyPage = (VerifyPage) RTPageFactory.getPageFromURL(driver.getCurrentUrl(), driver);
		} else {
			logger.warn("SETUP: Could not restore session; creating a fresh token.");
			//Navigate to location page
			logger.info("SETUP: Navigating to location page");
			locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
			locationPage.enterZipCode("44114");
			
			logger.info("SETUP: Navigating to vehicle page");
			vehiclePage = (VehiclePage) locationPage.clickNext();
			vehiclePage.selectVehicle(vehicle);
			
			logger.info("SETUP: Navigating to verify page");
			verifyPage = (VerifyPage) vehiclePage.clickNext();
			
			SessionManager.saveSession("verify", "44321", vehicle, driver);
		}
		
		//Navigate away
		logger.info("Step 1: Navigate away");
		driver.navigate().to("http://www.google.com");
		closeSurveyPopup();
		
		//Come back
		logger.info("Step 2: Navigate back to home page");
		driver.navigate().to(rootURL);
		
		homePage = new HomePage(driver);
		homePage.waitForPageLoad();
		
		assertTrue("Home page did not load", homePage.isLoaded());
		
		logger.info("Step 3: Verify PUWYLO modal appears");
		if (homePage.isShowingSurveyModal()) {
			logger.info("Dismissing survay popup");
			homePage.dismissSurveyModal();
		}
		puwyloModal = homePage.getPUWLOModal();
		puwyloModal.waitForModal();
		
		assertTrue("Modal did not appear", homePage.isShowingPuwyloModal());
		
		logger.info("Step 3: Close modal");
		PageObject currPage = puwyloModal.clickClose();
		assertFalse("Modal did not go away", homePage.isShowingPuwyloModal());
		assertTrue("Home page is not loaded", currPage instanceof HomePage);
	}
	
	/**
	 * Test:
	 * Given I have reached the Tire Coach page
	 * When I navigate away from the site
	 * And I return to the home page
	 * Then I should see a popup asking if I want to pick up where I left off
	 */
	@Test
	public void tireCoachPage() {
		//Navigate to the Location page
		locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();

		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		
		if (SessionManager.restoreSession("tirecoach", null, vehicle, driver)) {
			logger.info("Restored saved session");
			tireCoachPage = (TireCoachPage) RTPageFactory.getPageFromURL(driver.getCurrentUrl(), driver);
		} else {
			logger.warn("SETUP: Could not restore session; creating a fresh token.");
			//Navigate to location page
			logger.info("SETUP: Navigating to location page");
			locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
			locationPage.enterZipCode("44114");
			
			logger.info("SETUP: Navigating to vehicle page");
			vehiclePage = (VehiclePage) locationPage.clickNext();
			vehiclePage.selectVehicle(vehicle);
			
			logger.info("SETUP: Navigating to verify page");
			verifyPage = (VerifyPage) vehiclePage.clickNext();
		
			//Go on
			Utils.WaitForAjax(driver);
			logger.info("SETUP: Navigating to Tire Coach page");
			tireCoachPage = (TireCoachPage) verifyPage.clickNext();
			
			SessionManager.saveSession("tirecoach", "44321", vehicle, driver);
		}
		
		//Navigate away
		logger.info("Step 1: Navigate away");
		driver.navigate().to("http://www.google.com");
		closeSurveyPopup();
		
		//Come back
		logger.info("Step 2: Navigate back to home page");
		driver.navigate().to(rootURL);
		
		homePage = new HomePage(driver);
		homePage.waitForPageLoad();
		
		assertTrue("Home page did not load", homePage.isLoaded());
		
		logger.info("Step 3: Verify PUWYLO modal appears");
		if (homePage.isShowingSurveyModal()) {
			logger.info("Dismissing survay popup");
			homePage.dismissSurveyModal();
		}
		puwyloModal = homePage.getPUWLOModal();
		puwyloModal.waitForModal();
		
		assertTrue("Modal did not appear", homePage.isShowingPuwyloModal());
		assertEquals("Wrong section displayed", "TireCoach - Your Needs", puwyloModal.getSection());
		
		logger.info("Step 4: Click 'yes'");
		PageObject currPage = puwyloModal.clickYes();
		assertTrue("Wrong page loaded", currPage instanceof TireCoachPage);
	}
	
	/**
	 * Test:
	 * Given I have reached the Product page
	 * When I navigate away from the site
	 * And I return to the home page
	 * Then I should see a popup asking if I want to pick up where I left off
	 */
	@Test
	public void productPage() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		
		if (SessionManager.restoreSession("product", null, vehicle, driver)) {
			logger.info("Restored saved session");
			productPage = (ProductPage) RTPageFactory.getPageFromURL(driver.getCurrentUrl(), driver);
		} else {
			logger.warn("SETUP: Could not restore session; creating a fresh token.");
			//Navigate to location page
			logger.info("SETUP: Navigating to location page");
			locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
			locationPage.enterZipCode("44114");
			
			logger.info("SETUP: Navigating to vehicle page");
			vehiclePage = (VehiclePage) locationPage.clickNext();
			vehiclePage.selectVehicle(vehicle);
			
			logger.info("SETUP: Navigating to verify page");
			verifyPage = (VerifyPage) vehiclePage.clickNext();
		
			//Go on
			Utils.WaitForAjax(driver);
			logger.info("SETUP: Navigating to Tire Coach page");
			tireCoachPage = (TireCoachPage) verifyPage.clickNext();
			
			productPage = (ProductPage) tireCoachPage.clickNext();
			
			SessionManager.saveSession("product", "44321", vehicle, driver);
		}
		
		//Navigate away
		logger.info("Step 1: Navigate away");
		driver.navigate().to("http://www.google.com");
		closeSurveyPopup();
		
		//Come back
		logger.info("Step 2: Navigate back to home page");
		driver.navigate().to(rootURL);
		
		homePage = new HomePage(driver);
		homePage.waitForPageLoad();
		
		assertTrue("Home page did not load", homePage.isLoaded());
		
		logger.info("Step 3: Verify PUWYLO modal appears");
		if (homePage.isShowingSurveyModal()) {
			logger.info("Dismissing survay popup");
			homePage.dismissSurveyModal();
		}
		puwyloModal = homePage.getPUWLOModal();
		puwyloModal.waitForModal();
		
		assertTrue("Modal did not appear", homePage.isShowingPuwyloModal());
		assertEquals("Wrong section displayed", "Tire Recommendations", puwyloModal.getSection());
		
		logger.info("Step 4: Click yes");;
		PageObject currPage = puwyloModal.clickYes();
		assertTrue("Wrong section loaded", currPage instanceof ProductPage);
	}

	/**
	 * Test:
	 * Given I have reached the Install page
	 * When I navigate away from the site
	 * And I return to the home page
	 * Then I should see a popup asking if I want to pick up where I left off
	 */
	@Test
	public void installPage() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		
		if (SessionManager.restoreSession("install", null, vehicle, null, null, driver)) {
			logger.info("Restored saved session");
			installPage = (InstallPage) RTPageFactory.getPageFromURL(driver.getCurrentUrl(), driver);
		} else {
			logger.warn("SETUP: Could not restore session; creating a fresh token.");
			//Navigate to location page
			logger.info("SETUP: Navigating to location page");
			locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
			locationPage.enterZipCode("44114");
			
			logger.info("SETUP: Navigating to vehicle page");
			vehiclePage = (VehiclePage) locationPage.clickNext();
			vehiclePage.selectVehicle(vehicle);
			
			logger.info("SETUP: Navigating to verify page");
			verifyPage = (VerifyPage) vehiclePage.clickNext();
		
			//Go on
			Utils.WaitForAjax(driver);
			logger.info("SETUP: Navigating to Tire Coach page");
			tireCoachPage = (TireCoachPage) verifyPage.clickNext();
			
			productPage = (ProductPage) tireCoachPage.clickNext();
			
			//Select a product; don't care which
			ArrayList<Product> tires = productPage.getRecommendedTires();
			Product product = tires.get(0);
			installPage = (InstallPage) productPage.selectProduct(product, 2);
			
			SessionManager.saveSession("install", "44321", vehicle, product, 2, driver);
		}

		//Navigate away
		logger.info("Step 1: Navigate away");
		driver.navigate().to("http://www.google.com");
		closeSurveyPopup();
		
		//Come back
		logger.info("Step 2: Navigate back to home page");
		driver.navigate().to(rootURL);
		
		homePage = new HomePage(driver);
		homePage.waitForPageLoad();
		
		assertTrue("Home page did not load", homePage.isLoaded());
		
		logger.info("Step 3: Verify PUWYLO modal appears");
		if (homePage.isShowingSurveyModal()) {
			logger.info("Dismissing survay popup");
			homePage.dismissSurveyModal();
		}
		puwyloModal = homePage.getPUWLOModal();
		puwyloModal.waitForModal();
		
		assertTrue("Modal did not appear", homePage.isShowingPuwyloModal());
		assertEquals("Wrong section appeared in modal", "Installer - Schedule", puwyloModal.getSection());
		
		logger.info("Step 4: Accept modal");
		PageObject currPage = puwyloModal.clickYes();
		assertTrue("Wrong page loaded", currPage instanceof InstallPage);
	}
	
	private void closeSurveyPopup() {
		List<String> windows = new ArrayList<String> (driver.getWindowHandles());
		if (windows.size() > 1) {
			logger.info("Closing survey popup");
			driver.switchTo().window(windows.get(1));
			driver.close();
			driver.switchTo().window(windows.get(0));
		}
	}

}
