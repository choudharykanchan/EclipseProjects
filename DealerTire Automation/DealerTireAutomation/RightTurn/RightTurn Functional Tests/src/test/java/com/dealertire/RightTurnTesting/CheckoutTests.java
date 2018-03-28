package com.dealertire.RightTurnTesting;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.dealertire.RightTurnFramework.BaseTest;
import com.dealertire.RightTurnFramework.PageObject;
import com.dealertire.RightTurnFramework.RTPageFactory;
import com.dealertire.RightTurnFramework.SessionManager;
import com.dealertire.RightTurnFramework.Utils;
import com.dealertire.RightTurnFramework.Models.Price;
import com.dealertire.RightTurnFramework.Models.Product;
import com.dealertire.RightTurnFramework.Models.Vehicle;
import com.dealertire.RightTurnFramework.Models.Vehicle.Criteria;
import com.dealertire.RightTurnFramework.Pages.CheckoutPage;
import com.dealertire.RightTurnFramework.Pages.InstallPage;
import com.dealertire.RightTurnFramework.Pages.LocationPage;
import com.dealertire.RightTurnFramework.Pages.ProductPage;
import com.dealertire.RightTurnFramework.Pages.SchedulePage;
import com.dealertire.RightTurnFramework.Pages.ServicesPage;
import com.dealertire.RightTurnFramework.Pages.TireCoachPage;
import com.dealertire.RightTurnFramework.Pages.VehiclePage;
import com.dealertire.RightTurnFramework.Pages.VerifyPage;
import com.dealertire.RightTurnFramework.Pages.Modals.DateTimeModal;

/**
 * Tests around adding a product to the cart
 * @author bgreen
 */
public class CheckoutTests extends BaseTest {
	
	private Product product;
	/**
     * @see com.dealertire.RightTurnFramework.BaseTest#BaseTest(String, String, String)
     */
	public CheckoutTests(String os, String browserName, String browserVersion) {
		super(os, browserName, browserVersion);
	}

	/**
	 * Runs before every test to ensure we're on the right page.
	 * @param checkoutPage 
	 */
	@Before
	public void SetUp() {		
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		criteriaList.put(Criteria.RUNFLAT, false);
		criteriaList.put(Criteria.STAGGERED, false);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("Using vehicle " + vehicle);
		

		if (SessionManager.restoreSession("checkout", null, vehicle, null, 1, driver)) {
			logger.info("SETUP: Restored saved session");
			PageObject restoredPage = RTPageFactory.getPageFromURL(driver.getCurrentUrl(), driver);
			
			if (restoredPage instanceof SchedulePage) {
				logger.info("SETUP: Resumed from Schedule page, selecting date.");
				schedulePage = (SchedulePage)restoredPage;
				schedulePage.waitForPageLoad();
				Calendar date = Calendar.getInstance();
				date.add(Calendar.DAY_OF_MONTH, 7);
				
				schedulePage.selectTimeSlot(date);
				
				PageObject nextPage = schedulePage.clickNext();
				
				if (nextPage instanceof ServicesPage) {
					checkoutPage = (CheckoutPage) nextPage.clickNext();
				} else {
					checkoutPage = (CheckoutPage) nextPage;
				}
				
			} else {
				checkoutPage = (CheckoutPage) restoredPage;
			}
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
			Utils.WaitForAllAnimationComplete(driver);
			
			logger.info("SETUP: Navigating to tire coach page");
			tireCoachPage = (TireCoachPage) verifyPage.clickNext();
			
			logger.info("SETUP: Navigating to product page");
			productPage = (ProductPage) tireCoachPage.clickNext();
			product = productPage.getRandomProduct();
			logger.info("SETUP: Using product " + product);
			productPage.selectQuantity(product, 1);
			
			logger.info("SETUP: Navigating to install page");
			installPage = (InstallPage) productPage.selectProduct(product);
			
			logger.info("SETUP: Navigating to schedule page");
			schedulePage = (SchedulePage)installPage.clickNext();
			schedulePage.waitForPageLoad();
			Calendar date = Calendar.getInstance();
			date.add(Calendar.DAY_OF_MONTH, 7);
			date.set(Calendar.HOUR_OF_DAY, 12);
			date.set(Calendar.MINUTE, 0);
			
			schedulePage.selectTimeSlot(date);
			
			logger.info("SETUP: Navigating beyond schedule page");
			PageObject nextPage = schedulePage.clickNext();
			
			if (nextPage instanceof ServicesPage) {
				logger.info("SETUP: Navigating from Services page to Checkout page");
				checkoutPage = (CheckoutPage) nextPage.clickNext();
			} else {
				checkoutPage = (CheckoutPage) nextPage;
			}
			checkoutPage.waitForPageLoad();
			SessionManager.saveSession("checkout", "44114", vehicle, product, 1, driver);
		}

		checkoutPage.waitForPageLoad();
		Utils.WaitForAjax(driver);
		Utils.WaitForAllAnimationComplete(driver);
	}
	
	/**
	 * Given I am on the checkout page
	 * When I enter customer information
	 * And a valid billing address
	 * And a valid credit card
	 * And a valid email
	 * And I click Place Order
	 * Then I should be on the order confirmation page
	 * And the details I entered should appear
	 */
	@Test
	public void validDataInput() {
		logger.info("Step 1: Fill out form using test harness");
		checkoutPage.fillForm();
		
		logger.info("Step 2: place order");
		PageObject nextPage = checkoutPage.placeOrder();
		assertFalse("Remained on checkout page after placing order!", nextPage instanceof CheckoutPage);
	}
	
	/**
	 * Given I am on the checkout page
	 * When I click “Adjust/Change”
	 * And I select a new date and time
	 * And I click “update appointment”
	 * Then the checkout page should reflect the date and time chosen
	 * When I enter the rest of the page’s data
	 * And I click on “Place Order”
	 * Then I should see the new date and time on the confirmation page
	 */
	@Test
	public void reschedule() {
		logger.info("Step 1: Fill out form using test harness");
		checkoutPage.fillForm();
		
		logger.info("Step 2: Change appointment date/time");
		String originalDate = checkoutPage.getCheckoutDate();	
		DateTimeModal modal = checkoutPage.changeAppointment();
		
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_MONTH, 15);
		date.set(Calendar.HOUR, 12);
		modal.selectTimeSlot(date);
		modal.updateAppointment();
		
		String newDate = checkoutPage.getCheckoutDate();
		assertNotEquals("Date did not on checkout page after changing it using the modal", originalDate, newDate);
		
		logger.info("Step 3: Place order");
		checkoutPage.placeOrder();
		String confirmationDate = checkoutPage.getConfirmationDate();
		
		assertEquals("Confirmation page did not display new date", newDate, confirmationDate);
	}
	
	/**
	 * Given I am on the checkout page
	 * When I change the tire quantity
	 * Then the price should change to reflect the new quantity
	 * When I enter the rest of the page’s data
	 * And I click “Place Order”
	 * Then I should see the new quantity on the confirmation page
	 * And I should see the new price on the confirmation page
	 */
	@Test
	public void changeQuantity() {
		logger.info("Step 1: Fill out form using test harness");
		checkoutPage.fillForm();
		
		String s1 = checkoutPage.getPrice();
		Price oldPrice = new Price(s1);
		
		logger.info("Step 2: Change quantity to 2");
		checkoutPage.changeQuantity(2);		
		
		String s2 = checkoutPage.getPrice();
		Price newPrice = new Price(s2);
		
		int oldTotal = (100*oldPrice.getDollars() + oldPrice.getCents());
		int newTotal = (100*newPrice.getDollars() + newPrice.getCents());
		
		assertTrue("Total did not accurately update when changing quantitiy of tires.", 2*oldTotal-newTotal==0 || Math.abs(2*oldTotal-newTotal)==1);
	
		logger.info("Step 3: Place order");
		checkoutPage.placeOrder();
		int confirmationTotal = checkoutPage.getConfirmationTotal();
		
		assertEquals("Confirmation page did not reflect new total.", newTotal , confirmationTotal);
	}
	
	/**
	 * Given I am on the checkout page
	 * When I enter ANY invalid information and click “Place Order” 
	 * Then I should see an error
	 * And I should still be on the checkout page
	 */
	@Test
	public void invalidDataSubmission() {
		logger.info("Step 1: click submit without entering information");
		checkoutPage.clickSubmitButton();
		
		assertFalse("Invalid input was accepted",checkoutPage.isValidInput());
		PageObject invalidInput = RTPageFactory.getPageFromURL(driver.getCurrentUrl(), driver);
		assertTrue("Did not remain on checkout page",invalidInput instanceof CheckoutPage);
	}
	
	/**
	 * Given I am on the checkout page
	 * When I click the “Back” link
	 * Then I should see a modal appear
	 * When I click “Continue”
	 * Then I should be on the scheduling page
	 */
	@Test
	public void goBackToSchedulePage() {
		String checkoutURL = driver.getCurrentUrl();
		
		logger.info("Step 1: Click 'Back'");
		checkoutPage.selectBack();
		
		logger.info("Step 2: Click 'continue' on the modal");
		checkoutPage.clickModalContinue();
		Utils.WaitForUrlChange(driver, checkoutURL);
		PageObject previousPage = RTPageFactory.getPageFromURL(driver.getCurrentUrl(), driver);
		
		assertTrue("Schedule page did not appear when navigating backward", previousPage instanceof SchedulePage);
		
		logger.info("CLEANUP: Cleaning up session");
		//Clean up session so it's not invalid now
		previousPage.clickNext();
	}
	
	/**
	 * Given I am on the checkout page
	 * And I have entered data
	 * When I click the “Back” link
	 * Then I should see a modal appear
	 * When I click “Cancel”
	 * Then I should be on the checkout page
	 * And I should see my data still present
	 */
	@Test
	public void goBackAndCancel() {
		logger.info("Step 1: Click 'Back'");
		checkoutPage.selectBack();
		
		logger.info("Step 2: Click 'cancel' on the modal");
		checkoutPage.clickModalCancel();
		PageObject previousPage = RTPageFactory.getPageFromURL(driver.getCurrentUrl(), driver);
		
		assertTrue("Browser did not remain on Checkout page", previousPage instanceof CheckoutPage);
	}
}
