package com.dealertire.RightTurnTesting;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.dealertire.RightTurnFramework.BaseTest;
import com.dealertire.RightTurnFramework.PageObject;
import com.dealertire.RightTurnFramework.RTPageFactory;
import com.dealertire.RightTurnFramework.SessionManager;
import com.dealertire.RightTurnFramework.Models.Price;
import com.dealertire.RightTurnFramework.Models.Product;
import com.dealertire.RightTurnFramework.Models.Vehicle;
import com.dealertire.RightTurnFramework.Models.Vehicle.Criteria;
import com.dealertire.RightTurnFramework.Pages.InstallPage;
import com.dealertire.RightTurnFramework.Pages.LocationPage;
import com.dealertire.RightTurnFramework.Pages.Modals.MapModal;
import com.dealertire.RightTurnFramework.Pages.ProductPage;
import com.dealertire.RightTurnFramework.Pages.SchedulePage;
import com.dealertire.RightTurnFramework.Pages.TireCoachPage;
import com.dealertire.RightTurnFramework.Pages.VehiclePage;
import com.dealertire.RightTurnFramework.Pages.VerifyPage;

/**
 * Feature: Installation
 * @author bgreen
 */
public class InstallerTests extends BaseTest {

	private Product product;

	/**
     * @see com.dealertire.RightTurnFramework.BaseTest#BaseTest(String, String, String)
     */
	public InstallerTests(String os, String browserName, String browserVersion) {
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
		
		if (SessionManager.restoreSession("installer", null, vehicle, null, 1, driver)) {
			logger.info("SETUP: Restored saved session");
			installPage = (InstallPage) RTPageFactory.getPageFromURL(driver.getCurrentUrl(), driver);
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
			
			logger.info("SETUP: Navigating to product  page");
			productPage = (ProductPage) tireCoachPage.clickNext();
			product = productPage.getRandomProduct();
			logger.info("SETUP: Using product " + product);
			productPage.selectQuantity(product, 1);
			
			logger.info("SETUP: Navigating to install page");
			installPage = (InstallPage) productPage.selectProduct(product);

			SessionManager.saveSession("installer", "44114", vehicle, product, 1, driver);
		}
	}
	
	/**
	 * Given I have selected a product
	 * When I navigate to the installer page
	 * Then I should see the “Yes” radio button checked
	 * When I click “Next”
	 * Then I should be on the scheduling page
	 */
	@Test
	public void preSelectedInstaller() {
		logger.info("Step 1: Verify default is set to 'yes'");
		assertTrue("Default was not set to 'yes'", installPage.isDefaultInstallerSelected());
		
		logger.info("Step 2: Click 'next'");
		PageObject nextPage = installPage.clickNext();
		assertTrue("Next page was not 'Schedule'", nextPage instanceof SchedulePage);
	}
	
	/**
	 * Given I have selected a product 
	 * When I navigate to the installer page
	 * And I click the “No” radio button
	 * Then I should see a modal appear for choosing a different installer
	 * When I click the first choice, “Select this installer”
	 * Then the modal should vanish
	 * And nothing on the page should change (same as where you started)
	 */
	@Test
	public void selectSameInstaller(){
		logger.info("Step 1: Choose not to use default dealer");
		installPage.selectInstaller(false);	
		
		logger.info("Step 2: Verify modal appears");
		mapModal = installPage.getModal();
		mapModal.waitForDisplay();
		assertTrue("Modal did not appear", installPage.isModalShowing());
	
		MapModal installer = installPage.getModal();
		
		logger.info("Step 3: Select first dealer shown");
		installer.selectDealer(1);
		
		assertFalse("Modal continued showing after selecting dealer", installPage.isModalShowing());
		assertTrue("Did not remain on Install page", installPage.isLoaded());
		assertFalse("Price changed!", installPage.isDifferentPrice());
	}
	
	/**
	 * Given I have selected a product 
	 * When I navigate to the installer page
	 * And I click the “No” radio button
	 * And I choose a different installer
	 * Then the modal should vanish
	 * And I should be on the installer page 
	 * And I should see the text “Some dealers charge different prices for services they provide”
	 * And I should see the text “New Total Price”
	 * And the price should reflect the new change
	 */
	@Test
	public void differentInstallerPriceChange(){
		logger.info("Step 1: Choose not to use default dealer");
		installPage.selectInstaller(false);
		
		logger.info("Step 2: Verify modal appears");
		mapModal = installPage.getModal();
		mapModal.waitForDisplay();
		assertTrue("Modal did not appear", installPage.isModalShowing());
	
		MapModal installer = installPage.getModal();

		logger.info("Step 3: Select second dealer shown");
		installer.selectDealer(2);
		assertFalse("Modal remained open after selecting dealer", installPage.isModalShowing());
		assertTrue("Did not remain on install page", installPage.isLoaded());	
		
		boolean priceChange = installPage.isDifferentPrice();
		
		if(priceChange) {
			logger.info("Step 4: Price changed; verifying the differene appears correctly");
			//Take old and new price and convert them integer for easier comparison
			Price oldPrice = product.getPrice();
			Price newPrice = installPage.getNewPrice();		
			int oldDollars = oldPrice.getDollars();
			int oldCents = oldPrice.getCents();
			int newDollars = newPrice.getDollars();
			int newCents = newPrice.getCents();
			//Multiply by 100 to get rid of decimal 
			//oldNumber must be multiplied by 2 since 2 tires were selected
			int oldNumber = 2*(100*oldDollars+oldCents);
			int newNumber = 100*newDollars+newCents;
			
			int differenceNumber1 = newNumber - oldNumber;
			if(installPage.increaseOrDecrease().equals("decreased")) {
				differenceNumber1 = oldNumber - newNumber;
			}

			Price difference = installPage.getDifference();
			int differenceDollars = difference.getDollars();
			int differenceCents = difference.getCents();
			int differenceNumber2 = differenceDollars*100+differenceCents;
			
			assertEquals("The difference was not calculated correctly", differenceNumber1, differenceNumber2);
		}
	}
	
	/**
	 * Given I am on the installer page
	 * And I have selected “No” on the radio button
	 * When I increase the distance dropdown
	 * Then the number of shown results on the Google Map should increase or stay the same 
	 */
	@Test
	public void increaseGoogleMapScope(){
		logger.info("Step 1: Choose not to use default dealer");
		installPage.selectInstaller(false);

		logger.info("Step 2: Wait for modal to appear");
		mapModal = installPage.getModal();
		mapModal.waitForDisplay();
		MapModal installer = installPage.getModal();	

		logger.info("Step 3: Increase scope in dropdown");
		int beforeResults = installer.getNumberOfResults();
		mapModal.increaseDropDownDistance();
		int postResults = installer.getNumberOfResults();
		
		assertTrue("Number of dealer results did not increase when the scope increased", postResults >= beforeResults);
	}
	
	/**
	 * Given I am on the installer page
	 * And I have selected “No” on the radio button
	 * When I increase the distance dropdown
	 * Then the number of shown results on the Google Map should increase or stay the same 
	 */
	@Test
	public void decreaseGoogleMapScope(){
		logger.info("Step 1: Choose not to use default dealer");
		installPage.selectInstaller(false);

		logger.info("Step 2: Wait for modal to appear");
		mapModal = installPage.getModal();
		mapModal.waitForDisplay();
		MapModal installer = installPage.getModal();	

		logger.info("Step 3: Decrease scope in dropdown");
		mapModal.increaseDropDownDistance();
		int beforeResults = installer.getNumberOfResults();
		mapModal.decreaseDropDownDistance();
		int postResults = installer.getNumberOfResults();
		
		assertTrue("Number of dealer results did not decrease when the scope decreased",beforeResults >= postResults);
	}
}
