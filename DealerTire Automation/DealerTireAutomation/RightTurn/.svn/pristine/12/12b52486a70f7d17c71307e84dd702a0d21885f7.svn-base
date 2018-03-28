package com.dealertire.RightTurnTesting.SmokeTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.dealertire.RightTurnFramework.BaseTest;
import com.dealertire.RightTurnFramework.PageObject;
import com.dealertire.RightTurnFramework.Models.Dealer;
import com.dealertire.RightTurnFramework.Models.Product;
import com.dealertire.RightTurnFramework.Models.Size;
import com.dealertire.RightTurnFramework.Models.Vehicle;
import com.dealertire.RightTurnFramework.Models.Dealer.Market;
import com.dealertire.RightTurnFramework.Models.Vehicle.Criteria;
import com.dealertire.RightTurnFramework.Pages.CheckoutPage;
import com.dealertire.RightTurnFramework.Pages.ConfirmationPage;
import com.dealertire.RightTurnFramework.Pages.InstallPage;
import com.dealertire.RightTurnFramework.Pages.LocationPage;
import com.dealertire.RightTurnFramework.Pages.ProductPage;
import com.dealertire.RightTurnFramework.Pages.SchedulePage;
import com.dealertire.RightTurnFramework.Pages.TireCoachPage;
import com.dealertire.RightTurnFramework.Pages.VehiclePage;
import com.dealertire.RightTurnFramework.Pages.VerifyPage;
import com.dealertire.RightTurnFramework.Pages.ServicesPage;
import com.dealertire.RightTurnFramework.Pages.TireCoachPage.DrivingStyle;
import com.dealertire.RightTurnFramework.Pages.TireCoachPage.MilesDriven;
import com.dealertire.RightTurnFramework.Pages.TireCoachPage.Weather;
import com.dealertire.RightTurnFramework.Pages.VehiclePage.Tab;

/**
 * End-to-end testing of the entire purchase funnel. DO NOT RUN IN PROD.
 * @author bgreen
 *
 */
public class EndtoEndTests extends BaseTest {

	/**
     * @see com.dealertire.RightTurnFramework.BaseTest#BaseTest(String, String, String)
     */
	public EndtoEndTests(String os, String browserName, String browserVersion) {
		super(os, browserName, browserVersion);
	}
	
	/**
	 * End to end: Search by size, find products, order them.
	 */
	@Test
	public void orderProductsBySize() {
		//------------ SETUP ------------------
		Map<Size.Criteria, Boolean> sCriteriaList = new HashMap<Size.Criteria, Boolean>();
		sCriteriaList.put(Size.Criteria.FITMENT, true);
		sCriteriaList.put(Size.Criteria.RUNFLAT, false);
		sCriteriaList.put(Size.Criteria.STAGGERED, false);
		Size size = Size.getRandomSizeByCriteria(sCriteriaList);
		logger.info("Using size " + size);
		locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
		
		//------------ Location Page ------------------
		logger.info("1. Location page");
		locationPage.enterZipCode("44114");
		vehiclePage = (VehiclePage) locationPage.clickNext();
		
		//------------ Vehicle Page ------------------
		logger.info("2. Vehicle page");
		vehiclePage.selectTab(Tab.SIZE_SELECT);
		assertFalse(vehiclePage.canGoOn());
		vehiclePage.selectSize(size);
		assertTrue(vehiclePage.canGoOn());
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		
		//------------ Verify page ------------------
		logger.info("3. Verify page");
	//	assertTrue(verifyPage.getDisplayedSizes().contains(size));
		
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		
		//------------ Tire Coach Page ------------------
		logger.info("4. Tire Coach page");
		assertEquals(MilesDriven.TenToFifteenK,tireCoachPage.getSelectedMilesDriven());
		assertEquals(Weather.SNOW, tireCoachPage.getSelectedWeather().get(0));
		assertEquals(DrivingStyle.TYPICAL, tireCoachPage.getSelectedDrivingStyle());
		
		productPage = (ProductPage) tireCoachPage.clickNext();
		
		//------------ Product page ------------------
		logger.info("5. Product page");
		Product product = productPage.getRandomProduct();
		logger.info("Using product " + product);
		productPage.selectQuantity(product, 1);	
		
		
		installPage = (InstallPage) productPage.selectProduct(product);
		
		//------------ Install page ------------------
		logger.info("6. Install page");
		schedulePage = (SchedulePage)installPage.clickNext();
		
		//------------ Schedule page ------------------
		logger.info("7. Schedule page");
		schedulePage.waitForPageLoad();
		assertTrue(schedulePage.isLoaded());
		assertFalse(schedulePage.canGoOn());
		
		Map<Dealer.Criteria, Boolean> criteria = new HashMap<Dealer.Criteria, Boolean>();
		criteria.put(Dealer.Criteria.LOUNGE, true);
		ArrayList<Dealer> dealers = Dealer.findDealers(criteria, Market.CLEVELAND, size.make);
		
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_MONTH, 7);
		
		//Assuming the first found dealer is the default dealer, 
		//finds the first available day to schedule after 1 week
		int i = 8;
		while(!dealers.get(0).isOpenOn(date)) {
			date.add(Calendar.DAY_OF_MONTH, i);
			i++;
		}
		date.set(Calendar.HOUR, 10);
		date.set(Calendar.MINUTE, 0);
		
		schedulePage.selectTimeSlot(date);
		assertTrue(schedulePage.canGoOn());
		
		
		PageObject nextPage = schedulePage.clickNext();
		
		if (nextPage instanceof ServicesPage) {
			checkoutPage = (CheckoutPage) nextPage.clickNext();
		} else {
			checkoutPage = (CheckoutPage) nextPage;
		}
		
		//------------ Checkout page ------------------
		logger.info("8. Checkout page");
		checkoutPage.waitForPageLoad();
		checkoutPage.fillForm();
		nextPage = checkoutPage.placeOrder();
		nextPage.waitForPageLoad();
		
		
		//------------ Confirmation page ------------------
		logger.info("9. Confirmation page");
		assertTrue(nextPage instanceof ConfirmationPage);
	}
	
	/**
	 * End to end: Search by vehicle, find products, order them.
	 */
	@Test
	public void orderProductsByVehicle() {
		
		//------------ SETUP ------------------
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		criteriaList.put(Criteria.STAGGERED, false);
		criteriaList.put(Criteria.OPTION, false);
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("Using vehicle " + vehicle);
		locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
		
		//------------ Location Page ------------------
		logger.info("1. Location page");
		locationPage.enterZipCode("44114");
		vehiclePage = (VehiclePage) locationPage.clickNext();
		
		//------------ Vehicle Page ------------------
		logger.info("2. Vehicle page");
		vehiclePage.selectYear(vehicle.year);
		assertFalse(vehiclePage.canGoOn());
		
		vehiclePage.selectMake(vehicle.make);
		assertFalse(vehiclePage.canGoOn());
		
		vehiclePage.selectModel(vehicle.model);		
		vehiclePage.selectTrim(vehicle.trim);
		assertTrue(vehiclePage.canGoOn());
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		
		//------------ Verify page ------------------
		logger.info("3. Verify page");
		assertEquals(vehicle.make + ":", verifyPage.getDisplayedMake());
		assertEquals(vehicle.year + " " + vehicle.model, verifyPage.getDisplayedModel());
		assertEquals("Trim: " + vehicle.trim, verifyPage.getDisplayedTrim());
		assertEquals(vehicle.option, verifyPage.getDisplayedOption());
		assertTrue(verifyPage.isVehicleImageVisible());
		
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		
		//------------ Tire Coach Page ------------------
		logger.info("4. Tire Coach page");
		assertEquals(MilesDriven.TenToFifteenK,tireCoachPage.getSelectedMilesDriven());
		assertEquals(Weather.SNOW, tireCoachPage.getSelectedWeather().get(0));
		assertEquals(DrivingStyle.TYPICAL, tireCoachPage.getSelectedDrivingStyle());
		
		productPage = (ProductPage) tireCoachPage.clickNext();
		
		//------------ Product page ------------------
		logger.info("5. Product page");
		Product product = productPage.getRandomProduct();
		logger.info("Using product " + product);
		productPage.selectQuantity(product, 1);	
		
		
		installPage = (InstallPage) productPage.selectProduct(product);
		
		//------------ Install page ------------------
		logger.info("6. Install page");
		schedulePage = (SchedulePage)installPage.clickNext();
		
		//------------ Schedule page ------------------
		logger.info("7. Schedule page");
		schedulePage.waitForPageLoad();
		assertTrue(schedulePage.isLoaded());
		assertFalse(schedulePage.canGoOn());
		
		Map<Dealer.Criteria, Boolean> criteria = new HashMap<Dealer.Criteria, Boolean>();
		criteria.put(Dealer.Criteria.LOUNGE, true);
		ArrayList<Dealer> dealers = Dealer.findDealers(criteria, Market.CLEVELAND, vehicle.make);
		
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_MONTH, 7);
		
		//Assuming the first found dealer is the default dealer, 
		//finds the first available day to schedule after 1 week
		int i = 8;
		while(!dealers.get(0).isOpenOn(date)) {
			date.add(Calendar.DAY_OF_MONTH, i);
			i++;
		}
		
		date.set(Calendar.HOUR, 10);
		date.set(Calendar.MINUTE, 0);
		
		schedulePage.selectTimeSlot(date);
		assertTrue(schedulePage.canGoOn());
		
		
		PageObject nextPage = schedulePage.clickNext();
		
		if (nextPage instanceof ServicesPage) {
			checkoutPage = (CheckoutPage) nextPage.clickNext();
		} else {
			checkoutPage = (CheckoutPage) nextPage;
		}
		
		//------------ Checkout page ------------------
		logger.info("8. Checkout page");
		checkoutPage.waitForPageLoad();
		checkoutPage.fillForm();
		nextPage = checkoutPage.placeOrder();
		nextPage.waitForPageLoad();
		
		
		//------------ Confirmation page ------------------
		logger.info("9. Confirmation page");
		assertTrue(nextPage instanceof ConfirmationPage);
	}

}
