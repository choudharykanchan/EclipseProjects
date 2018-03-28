package com.dealertire.RightTurnTesting;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.dealertire.RightTurnFramework.BaseTest;
import com.dealertire.RightTurnFramework.PageObject;
import com.dealertire.RightTurnFramework.Models.Vehicle;
import com.dealertire.RightTurnFramework.Models.Vehicle.Criteria;
import com.dealertire.RightTurnFramework.Pages.LocationPage;
import com.dealertire.RightTurnFramework.Pages.ProductPage;
import com.dealertire.RightTurnFramework.Pages.TireCoachPage;
import com.dealertire.RightTurnFramework.Pages.TireCoachPage.DrivingStyle;
import com.dealertire.RightTurnFramework.Pages.TireCoachPage.MilesDriven;
import com.dealertire.RightTurnFramework.Pages.TireCoachPage.Weather;
import com.dealertire.RightTurnFramework.Pages.VehiclePage;
import com.dealertire.RightTurnFramework.Pages.VerifyPage;

/**
 * Feature: Tire Coach 
 * @author bgreen
 */
public class TireCoachTests extends BaseTest {

	/**
     * @see com.dealertire.RightTurnFramework.BaseTest#BaseTest(String, String, String)
     */
	public TireCoachTests(String os, String browserName, String browserVersion) {
		super(os, browserName, browserVersion);
	}
	
	
	/**
	 * Runs before every test to ensure we're on the right page.
	 */
	@Before
	public void SetUp() {		

	}
	
	
	/**
	 * Test:
	 * Given I have selected a truck
	 * When I reach the tire coach page
	 * Then I should see the truck options
	 */
	@Test
	public void truckCausesTruckOptions() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.TRUCK, true);
		criteriaList.put(Criteria.MULTISIZE, false);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("SETUP: Using vehicle " + vehicle);
		
		logger.info("SETUP: Navigate to location page");
		locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
		locationPage.enterZipCode("44114");
		
		logger.info("SETUP: Navigate to vehicle page");
		vehiclePage = (VehiclePage) locationPage.clickNext();
		vehiclePage.selectVehicle(vehicle);
		
		logger.info("SETUP: Navigate to verify page");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		logger.info("SETUP: Navigate to tire coach page");
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		
		assertTrue("Truck options should show", tireCoachPage.isTruckOptionsShowing());
	}
	
	/**
	 * Test:
	 * Given I have selected a non-truck vehicle
	 * When I reach the tire coach page
	 * Then I should not see the truck options
	 */
	@Test
	public void nonTruckNoTruckOptions() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.TRUCK, false);
		criteriaList.put(Criteria.MULTISIZE, false);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("SETUP: Using vehicle " + vehicle);
		
		logger.info("SETUP: Navigate to location page");
		locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
		locationPage.enterZipCode("44114");
		
		logger.info("SETUP: Navigate to vehicle page");
		vehiclePage = (VehiclePage) locationPage.clickNext();
		vehiclePage.selectVehicle(vehicle);
		
		logger.info("SETUP: Navigate to verify page");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		logger.info("SETUP: Navigate to tire coach page");
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		
		assertFalse("Truck options should not show", tireCoachPage.isTruckOptionsShowing());
	}
	
	/**Given I have entered a zip code in Dallas
	 * And I have selected a non-truck vehicle
	 * When I reach the tire coach page
	 * Then the miles select should default to 10k-15k
	 * And the weather select should default to “Occasional Rain”
	 * And the driving style select should default to “Typical”
	*/
	@Test
	public void defaultsDallas() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.TRUCK, false);
		criteriaList.put(Criteria.MULTISIZE, false);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("SETUP: Using vehicle " + vehicle);
		
		logger.info("SETUP: Navigate to location page");
		locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
		
		logger.info("Step 1: Enter Dallas zip code");
		locationPage.enterZipCode("76096");
		
		logger.info("Step 2: Navigate to Tire Coach");
		vehiclePage = (VehiclePage) locationPage.clickNext();
		
		vehiclePage.selectVehicle(vehicle);
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		
		assertEquals("Dallas default miles should be 10-15k", MilesDriven.TenToFifteenK,tireCoachPage.getSelectedMilesDriven());
		assertEquals("Dallas default weather should be some rain", Weather.RAIN, tireCoachPage.getSelectedWeather().get(0));
		assertEquals("Dallas default driving style should be typical", DrivingStyle.TYPICAL, tireCoachPage.getSelectedDrivingStyle());
	}
	
	/**Given I have entered a zip code in Dallas
	 * And I have selected a non-truck vehicle
	 * When I reach the tire coach page
	 * Then the miles select should default to 10k-15k
	 * And the weather select should default to “Occasional Rain”
	 * And the driving style select should default to “Typical”
	 * And the truck options should default to none selected
	*/
	
	@Test
	public void defaultsDallasTruck() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.TRUCK, true);
		criteriaList.put(Criteria.MULTISIZE, false);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("SETUP: Using vehicle " + vehicle);
		
		logger.info("SETUP: Navigate to location page");
		locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
		
		logger.info("Step 1: Enter Dallas zip code");
		locationPage.enterZipCode("76096");
		
		logger.info("Step 2: Navigate to Tire Coach");
		vehiclePage = (VehiclePage) locationPage.clickNext();
		
		vehiclePage.selectVehicle(vehicle);
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		
		assertEquals("Dallas default miles should be 10-15k", MilesDriven.TenToFifteenK,tireCoachPage.getSelectedMilesDriven());
		assertEquals("Dallas default weather should be some rain", Weather.RAIN, tireCoachPage.getSelectedWeather().get(0));
		assertEquals("Dallas default driving style should be typical", DrivingStyle.TYPICAL, tireCoachPage.getSelectedDrivingStyle());
		assertEquals("Dallas default truck options should be none", 0, tireCoachPage.getSelectedTruckOptions().size());
	}
	
	/**Given I have entered a zip code in Cleveland
	 * And I have selected a non-truck vehicle
	 * When I reach the tire coach page
	 * Then the miles select should default to 10k-15k
	 * And the weather select should default to “Snow, Rain, Ice”
	 * And the driving style select should default to “Typical”
	*/
	@Test
	public void defaultsCleveland() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.TRUCK, false);
		criteriaList.put(Criteria.MULTISIZE, false);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("SETUP: Using vehicle " + vehicle);
		
		logger.info("SETUP: Navigate to location page");
		locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
		
		logger.info("Step 1: Enter Cleveland zip code");
		locationPage.enterZipCode("44114");

		logger.info("Step 2: Navigate to Tire Coach");
		vehiclePage = (VehiclePage) locationPage.clickNext();
		
		vehiclePage.selectVehicle(vehicle);
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		
		assertEquals("Cleveland default miles should be 10-15k", MilesDriven.TenToFifteenK,tireCoachPage.getSelectedMilesDriven());
		assertEquals("Cleveland default weather should be snow", Weather.SNOW, tireCoachPage.getSelectedWeather().get(0));
		assertEquals("Cleveland default driving style should be typical", DrivingStyle.TYPICAL, tireCoachPage.getSelectedDrivingStyle());
	}
	
	/**Given I have entered a zip code in Cleveland
	 * And I have selected a non-truck vehicle
	 * When I reach the tire coach page
	 * Then the miles select should default to 10k-15k
	 * And the weather select should default to “Snow, Rain, Ice”
	 * And the driving style select should default to “Typical”
	 * And the truck options should default to none selected
	*/
	
	@Test
	public void defaultsClevelandTruck() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.TRUCK, true);
		criteriaList.put(Criteria.MULTISIZE, false);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("SETUP: Using vehicle " + vehicle);
		
		logger.info("SETUP: Navigate to location page");
		locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
		
		logger.info("Step 1: Enter Cleveland zip code");
		locationPage.enterZipCode("44114");

		logger.info("Step 2: Navigate to Tire Coach");
		vehiclePage = (VehiclePage) locationPage.clickNext();
		
		vehiclePage.selectVehicle(vehicle);
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		
		assertEquals("Cleveland default miles should be 10-15k", MilesDriven.TenToFifteenK,tireCoachPage.getSelectedMilesDriven());
		assertEquals("Cleveland default weather should be snow", Weather.SNOW, tireCoachPage.getSelectedWeather().get(0));
		assertEquals("Cleveland default driving style should be typical", DrivingStyle.TYPICAL, tireCoachPage.getSelectedDrivingStyle());
		assertEquals("Cleveland default truck options should be none", 0, tireCoachPage.getSelectedTruckOptions().size());
	}
	
	/**
	 * Given that I have selected a vehicle
	 * When I reach the tire coach page
	 * And I click back
	 * Then I should be on the verify page
	 */
	@Test
	public void backButtonVerification() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
logger.info("SETUP: Using vehicle " + vehicle);
		
		logger.info("SETUP: Navigate to location page");
		locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
		locationPage.enterZipCode("44114");
		
		logger.info("SETUP: Navigate to vehicle page");
		vehiclePage = (VehiclePage) locationPage.clickNext();
		vehiclePage.selectVehicle(vehicle);
		
		logger.info("SETUP: Navigate to verify page");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		logger.info("SETUP: Navigate to tire coach page");
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		
		logger.info("Step 1: Click back");
		assertTrue("Cannot go back", tireCoachPage.canGoBack());
		PageObject prevPage = tireCoachPage.clickBack();
		assertTrue("Did not reach correct page", prevPage instanceof VerifyPage);
	}
	
	/**
	 * Given that I have selected a vehicle
	 * When I reach the tire coach page
	 * And I click next
	 * Then I should be on the tire results page
	 */
	@Test
	public void nextButtonVerification() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("SETUP: Using vehicle " + vehicle);
		
		logger.info("SETUP: Navigate to location page");
		locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
		locationPage.enterZipCode("44114");
		
		logger.info("SETUP: Navigate to vehicle page");
		vehiclePage = (VehiclePage) locationPage.clickNext();
		vehiclePage.selectVehicle(vehicle);
		
		logger.info("SETUP: Navigate to verify page");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		logger.info("SETUP: Navigate to tire coach page");
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		
		assertTrue("Cannot go on", tireCoachPage.canGoOn());
		PageObject prevPage = tireCoachPage.clickNext();
		assertTrue("Did not reach correct page", prevPage instanceof ProductPage);
	}

}
