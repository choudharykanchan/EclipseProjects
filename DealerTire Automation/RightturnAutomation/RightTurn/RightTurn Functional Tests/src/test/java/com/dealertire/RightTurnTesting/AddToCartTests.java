package com.dealertire.RightTurnTesting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.dealertire.RightTurnFramework.BaseTest;
import com.dealertire.RightTurnFramework.PageObject;
import com.dealertire.RightTurnFramework.RTPageFactory;
import com.dealertire.RightTurnFramework.SessionManager;
import com.dealertire.RightTurnFramework.Models.Product;
import com.dealertire.RightTurnFramework.Models.Vehicle;
import com.dealertire.RightTurnFramework.Models.Vehicle.Criteria;
import com.dealertire.RightTurnFramework.Pages.InstallPage;
import com.dealertire.RightTurnFramework.Pages.LocationPage;
import com.dealertire.RightTurnFramework.Pages.ProductPage;
import com.dealertire.RightTurnFramework.Pages.TireCoachPage;
import com.dealertire.RightTurnFramework.Pages.VehiclePage;
import com.dealertire.RightTurnFramework.Pages.VerifyPage;

/**
 * Tests around adding a product to the cart
 * @author bgreen
 */
public class AddToCartTests extends BaseTest {

	private Vehicle vehicle; 
	/**
     * @see com.dealertire.RightTurnFramework.BaseTest#BaseTest(String, String, String)
     */
	public AddToCartTests(String os, String browserName, String browserVersion) {
		super(os, browserName, browserVersion);
	}

	/**
	 * Runs before every test to ensure we're on the right page.
	 */
	@Before
	public void SetUp() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("SETUP: Using vehicle " + vehicle);
		
		if (SessionManager.restoreSession("product", null, vehicle, driver)) {
			logger.info("SETUP: Restored saved session");
			productPage = (ProductPage) RTPageFactory.getPageFromURL(driver.getCurrentUrl(), driver);
		} else {
			logger.warn("SETUP: Could not restore session; creating a fresh token.");
		
			//Navigate to the Product page
			logger.info("SETUP: Navigating to location page");
			locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
			locationPage.enterZipCode("44114");
			
			logger.info("SETUP: Navigating to vehicle page");
			vehiclePage = (VehiclePage) locationPage.clickNext();
			vehiclePage.selectVehicle(vehicle);
			
			logger.info("SETUP: Navigating to verify page");
			verifyPage = (VerifyPage) vehiclePage.clickNext();
			
			logger.info("SETUP: Navigating to tire coach page");
			tireCoachPage = (TireCoachPage) verifyPage.clickNext();
			
			logger.info("SETUP: Navigating to product page");
			productPage = (ProductPage) tireCoachPage.clickNext();
			productPage.waitForPageLoad();
			SessionManager.saveSession("product", "44114", vehicle, driver);
		}
	}
	
	/**
	 * Given I am on the product page
	 * And I have not selected a quantity
	 * When I click the “Select” button on a tire
	 * Then I should be on the details page
	 * And a pop-up should ask me how many tires I need
	 */
	@Test
	public void clickSelectNoQuantity() {
		Product p = productPage.getRandomProduct();
				
		//Do not select quantity
		logger.info("1. Clicking select without choosing a quantity");
		PageObject currPage = productPage.selectProduct(p);
		assertTrue(currPage instanceof ProductPage);
		assertTrue(currPage.isLoaded());
		
		assertTrue("Popover did not appear", productPage.isPopoverShowing());
		assertEquals("Popover text was not correct", "How many tires do you need?", productPage.getPopoverText());		
	}
	
	/**
	 * Given I am on the product page
	 * When I select a quantity
	 * And I click the “Select” button on a tire
	 * Then I should be on the Install page
	 */
	@Test
	public void clickSelectWithQuantity() {
		Product p = productPage.getRandomProduct();
		
		logger.info("1. Selecting a quantity");
		productPage.selectRandomQuantity(p);
		
		logger.info("2. Clicking select");
		PageObject currPage = productPage.selectProduct(p);
		assertTrue("Install page did not appear! ", currPage instanceof InstallPage);
		assertTrue("Install page did not load correctly", currPage.isLoaded());
	}
}
