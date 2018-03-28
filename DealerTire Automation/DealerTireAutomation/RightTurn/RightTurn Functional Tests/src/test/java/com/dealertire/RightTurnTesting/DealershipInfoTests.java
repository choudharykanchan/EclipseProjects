package com.dealertire.RightTurnTesting;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.dealertire.RightTurnFramework.BaseTest;
import com.dealertire.RightTurnFramework.Models.Dealer;
import com.dealertire.RightTurnFramework.Models.Vehicle;
import com.dealertire.RightTurnFramework.Models.Vehicle.Criteria;
import com.dealertire.RightTurnFramework.Pages.LocationPage;
import com.dealertire.RightTurnFramework.Pages.VehiclePage;
import com.dealertire.RightTurnFramework.Pages.VerifyPage;

/**
 * Tests for dealership info showing up on the verify page
 * @author bgreen
 *
 */
public class DealershipInfoTests extends BaseTest {

	/**
     * @see com.dealertire.RightTurnFramework.BaseTest#BaseTest(String, String, String)
     */
	public DealershipInfoTests(String os, String browserName, String browserVersion) {
		super(os, browserName, browserVersion);
	}
	
	Calendar dateAtBeginningOfTest;
	
	/**
	 * Set up before each test
	 */
	@Before
	public void SetUp() {
		dateAtBeginningOfTest = Calendar.getInstance();
	}
	
	/**Restore the date. No longer used	 */
	public void restore() {
		changeDate(dateAtBeginningOfTest);
	}
	
	/**
	 * Given that I have selected a vehicle
	 * And I am on the verify page
	 * Then I should see the text “You could have them installed as early as” followed by a date
	 * When the dealership is open 2 business days from now
	 * Then the date should be 2 business days from now
	 */
//	@Test
	public void correctDate() {
		//Calculate expected date
		Calendar expectedDate = Calendar.getInstance();
		expectedDate.add(Calendar.DAY_OF_MONTH, 2); //two days from now
		
		//Get a dealer
		Dealer dealer = Dealer.getRandomDealer();
		logger.info("Using dealer " + dealer.name);
		
		//Time travel, wheee!
		if (!dealer.isOpenOn(expectedDate)) {
			while (!dealer.isOpenOn(expectedDate)) {
				expectedDate.add(Calendar.DAY_OF_MONTH, 1);
			}
			
			Calendar systemDate  = (Calendar) expectedDate.clone();
			systemDate.add(Calendar.DAY_OF_MONTH, -2);
			changeDate(systemDate);
		}
				
		//Get a vehicle that dealer supports
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList, dealer.make);
		
		goToVerifyPage(dealer, vehicle);
		
		//Verify!
		Calendar actualDate = verifyPage.getInstallDate();
		if (actualDate == null) fail("Date parsing issue, please see log");
		assertEquals(expectedDate.get(Calendar.YEAR), actualDate.get(Calendar.YEAR));
		assertEquals(expectedDate.get(Calendar.MONTH), actualDate.get(Calendar.MONTH));
		assertEquals(expectedDate.get(Calendar.DAY_OF_MONTH), actualDate.get(Calendar.DAY_OF_MONTH));
		assertEquals(expectedDate.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US), actualDate.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US));
	}
	
	/**
	 * Given that I have selected a vehicle
	 * And I am on the verify page
	 * Then I should see the text “You could have them installed as early as” followed by a date
	 * When the dealership is not open 2 business days from now
	 * Then the date should be further than 2 business days from now
	 */
	//@Test
	public void correctDateDealershipClosed() {
		//Calculate expected datet
		Calendar expectedDate = Calendar.getInstance();
		expectedDate.add(Calendar.DAY_OF_MONTH, 2); //two days from now
		
		//Get a dealer
		Dealer dealer = Dealer.getRandomDealer();
		logger.info("Using dealer " + dealer.name);
		
		//Time travel, wheee!
		if (dealer.isOpenOn(expectedDate)) {
			while (dealer.isOpenOn(expectedDate)) {
				expectedDate.add(Calendar.DAY_OF_MONTH, 1);
			}
			
			Calendar systemDate  = (Calendar) expectedDate.clone();
			systemDate.add(Calendar.DAY_OF_MONTH, -2);
			changeDate(systemDate);
		}
		
		//Get a vehicle that dealer supports
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList, dealer.make);
		logger.info("Using vehicle " + vehicle);
		
		goToVerifyPage(dealer, vehicle);
		
		//Verify!
		Calendar actualDate = verifyPage.getInstallDate();
		if (actualDate == null) fail("Date parsing issue, please see log");
		assertNotEquals(expectedDate.get(Calendar.DAY_OF_MONTH), actualDate.get(Calendar.DAY_OF_MONTH));
		assertNotEquals(expectedDate.get(Calendar.DAY_OF_WEEK), actualDate.get(Calendar.DAY_OF_WEEK));
	}
	
	private void changeDate(Calendar date) {
		//"03-15-2008";
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-YYYY");
		String datestr = sdf.format(date.getTime());
		
		Runtime rt = Runtime.getRuntime();
		try {
			rt.exec("cmd /C date " + datestr);
		} catch (IOException e) {
			fail("Could not set system clock!");
		}
	}
	
	private void goToVerifyPage(Dealer dealer, Vehicle vehicle) {
		//Location page
		logger.info("SETUP: Navigating to location page");
		locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
		locationPage.enterZipCode(Integer.toString(dealer.zipcode));
		
		logger.info("SETUP: Navigating to vehicle page");
		vehiclePage = (VehiclePage) locationPage.clickNext();
		
		//Vehicle page
		vehiclePage.selectVehicle(vehicle);
		
		logger.info("SETUP: Navigating to verify page");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
	}
	
	/** Given that I have been assigned a dealership with a car wash 
	 * 	And I am on the verify page
	 * 	Then I should see a list of amenities
	 *  And it should contain “Carwash” on the left column
	 */
	@Test
	public void Carwash() {
		Map<Dealer.Criteria, Boolean> dealerCriteriaList = new HashMap<Dealer.Criteria, Boolean>();
		dealerCriteriaList.put(Dealer.Criteria.CARWASH, true);
		Dealer dealer = Dealer.getRandomDealerByCriteria(dealerCriteriaList);
		
		Map<Vehicle.Criteria, Boolean> vehicleCriteriaList = new HashMap<Vehicle.Criteria, Boolean>();
		vehicleCriteriaList.put(Vehicle.Criteria.MULTISIZE, false);
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(vehicleCriteriaList, dealer.make);
		
		goToVerifyPage(dealer, vehicle);
		
		//Verify
		ArrayList<String> itemsInLeftColumn = verifyPage.getLeftAmenitiesList();
		assertTrue("Dealer with carwash did not display carwash", itemsInLeftColumn.contains("Carwash"));
	}

	
	/** Given that I have been assigned a dealership without a car wash 
	 * 	And I am on the verify page
	 * 	Then I should see a list of amenities
	 *  And it should contain “Carwash” on the left column
	 */
	@Test
	public void NoCarwash() {
		Map<Dealer.Criteria, Boolean> dealerCriteriaList = new HashMap<Dealer.Criteria, Boolean>();
		dealerCriteriaList.put(Dealer.Criteria.CARWASH, false);
		Dealer dealer = Dealer.getRandomDealerByCriteria(dealerCriteriaList);
		
		Map<Vehicle.Criteria, Boolean> vehicleCriteriaList = new HashMap<Vehicle.Criteria, Boolean>();
		vehicleCriteriaList.put(Vehicle.Criteria.MULTISIZE, false);
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(vehicleCriteriaList, dealer.make);
		
		goToVerifyPage(dealer, vehicle);
		
		//Verify
		ArrayList<String> itemsInLeftColumn = verifyPage.getLeftAmenitiesList();
		assertFalse("Dealer with no carwash did display carwash",itemsInLeftColumn.contains("Carwash"));
	}

	/** Given that I have been assigned a dealership with a loaner car 
	 * 	And I am on the verify page
	 * 	Then I should see a list of amenities
	 *  And it should not contain “Loaner Car” on the left column
	 */
	@Test
	public void LoanerCar() {
		Map<Dealer.Criteria, Boolean> dealerCriteriaList = new HashMap<Dealer.Criteria, Boolean>();
		dealerCriteriaList.put(Dealer.Criteria.LOANER, true);
		Dealer dealer = Dealer.getRandomDealerByCriteria(dealerCriteriaList);
		
		Map<Vehicle.Criteria, Boolean> vehicleCriteriaList = new HashMap<Vehicle.Criteria, Boolean>();
		vehicleCriteriaList.put(Vehicle.Criteria.MULTISIZE, false);
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(vehicleCriteriaList, dealer.make);
		
		goToVerifyPage(dealer, vehicle);
		
		//Verify
		ArrayList<String> itemsInLeftColumn = verifyPage.getLeftAmenitiesList();
		assertTrue("Dealer with loaner car did not display loaner car",itemsInLeftColumn.contains("Loaner Car"));
	}

	
	/** Given that I have been assigned a dealership without a loaner car 
	 * 	And I am on the verify page
	 * 	Then I should see a list of amenities
	 *  And it should not contain “Loaner Car” on the left column
	 */
	@Test
	public void NoLoanerCar() {
		Map<Dealer.Criteria, Boolean> dealerCriteriaList = new HashMap<Dealer.Criteria, Boolean>();
		dealerCriteriaList.put(Dealer.Criteria.LOANER, false);
		Dealer dealer = Dealer.getRandomDealerByCriteria(dealerCriteriaList);
		
		Map<Vehicle.Criteria, Boolean> vehicleCriteriaList = new HashMap<Vehicle.Criteria, Boolean>();
		vehicleCriteriaList.put(Vehicle.Criteria.MULTISIZE, false);
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(vehicleCriteriaList, dealer.make);
		
		goToVerifyPage(dealer, vehicle);
		
		//Verify
		ArrayList<String> itemsInLeftColumn = verifyPage.getLeftAmenitiesList();
		assertFalse("Dealer with no loaner car did display loaner car",itemsInLeftColumn.contains("Loaner Car"));
	}
	
	/** Given that I have been assigned a dealership with a valet 
	 * 	And I am on the verify page
	 * 	Then I should see a list of amenities
	 *  And it should contain “Valet” on the left column
	 */
	@Test
	public void Valet() {
		Map<Dealer.Criteria, Boolean> dealerCriteriaList = new HashMap<Dealer.Criteria, Boolean>();
		dealerCriteriaList.put(Dealer.Criteria.VALET, true);
		Dealer dealer = Dealer.getRandomDealerByCriteria(dealerCriteriaList);
		
		Map<Vehicle.Criteria, Boolean> vehicleCriteriaList = new HashMap<Vehicle.Criteria, Boolean>();
		vehicleCriteriaList.put(Vehicle.Criteria.MULTISIZE, false);
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(vehicleCriteriaList, dealer.make);
		
		goToVerifyPage(dealer, vehicle);
		
		//Verify
		ArrayList<String> itemsInLeftColumn = verifyPage.getLeftAmenitiesList();
		assertTrue("Dealer with valet did not display valet",itemsInLeftColumn.contains("Valet"));
	}

	
	/** Given that I have been assigned a dealership without a valet 
	 * 	And I am on the verify page
	 * 	Then I should see a list of amenities
	 *  And it should not contain “Valet” on the left column
	 */
	@Test
	public void NoValet() {
		Map<Dealer.Criteria, Boolean> dealerCriteriaList = new HashMap<Dealer.Criteria, Boolean>();
		dealerCriteriaList.put(Dealer.Criteria.VALET, false);
		Dealer dealer = Dealer.getRandomDealerByCriteria(dealerCriteriaList);
		
		Map<Vehicle.Criteria, Boolean> vehicleCriteriaList = new HashMap<Vehicle.Criteria, Boolean>();
		vehicleCriteriaList.put(Vehicle.Criteria.MULTISIZE, false);
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(vehicleCriteriaList, dealer.make);
		
		goToVerifyPage(dealer, vehicle);
		
		//Verify
		ArrayList<String> itemsInLeftColumn = verifyPage.getLeftAmenitiesList();
		assertFalse("Dealer with no valet did display valet",itemsInLeftColumn.contains("Valet"));
	}

	/** Given that I have been assigned a dealership with a shuttle service
	 * 	And I am on the verify page
	 * 	Then I should see a list of amenities
	 *  And it should contain “Valet” on the left column
	 */
	@Test
	public void Shuttle() {
		Map<Dealer.Criteria, Boolean> dealerCriteriaList = new HashMap<Dealer.Criteria, Boolean>();
		dealerCriteriaList.put(Dealer.Criteria.SHUTTLE, true);
		Dealer dealer = Dealer.getRandomDealerByCriteria(dealerCriteriaList);
		
		Map<Vehicle.Criteria, Boolean> vehicleCriteriaList = new HashMap<Vehicle.Criteria, Boolean>();
		vehicleCriteriaList.put(Vehicle.Criteria.MULTISIZE, false);
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(vehicleCriteriaList, dealer.make);
		
		goToVerifyPage(dealer, vehicle);
		
		//Verify
		ArrayList<String> itemsInLeftColumn = verifyPage.getLeftAmenitiesList();
		assertTrue("Dealer with shuttle service did not display shuttle service",itemsInLeftColumn.contains("Shuttle Service"));
	}

	
	/** Given that I have been assigned a dealership without a shuttle service
	 * 	And I am on the verify page
	 * 	Then I should see a list of amenities
	 *  And it should not contain “Shuttle Service” on the left column
	 */
	@Test
	public void NoShuttle() {
		Map<Dealer.Criteria, Boolean> dealerCriteriaList = new HashMap<Dealer.Criteria, Boolean>();
		dealerCriteriaList.put(Dealer.Criteria.VALET, false);
		Dealer dealer = Dealer.getRandomDealerByCriteria(dealerCriteriaList);
		
		Map<Vehicle.Criteria, Boolean> vehicleCriteriaList = new HashMap<Vehicle.Criteria, Boolean>();
		vehicleCriteriaList.put(Vehicle.Criteria.MULTISIZE, false);
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(vehicleCriteriaList, dealer.make);
		
		goToVerifyPage(dealer, vehicle);
		
		//Verify
		ArrayList<String> itemsInLeftColumn = verifyPage.getLeftAmenitiesList();
		assertFalse("Dealer without shuttle service did display shuttle service",itemsInLeftColumn.contains("Shuttle Service"));
	}
	
	/** Given that I have been assigned a dealership with a coffee/snack bar
	 * 	And I am on the verify page
	 * 	Then I should see a list of amenities
	 *  And it should contain “Coffee/Snack Bar” on the right column
	 */
	@Test
	public void Coffee() {
		Map<Dealer.Criteria, Boolean> dealerCriteriaList = new HashMap<Dealer.Criteria, Boolean>();
		dealerCriteriaList.put(Dealer.Criteria.COFFEE, true);
		Dealer dealer = Dealer.getRandomDealerByCriteria(dealerCriteriaList);
		
		Map<Vehicle.Criteria, Boolean> vehicleCriteriaList = new HashMap<Vehicle.Criteria, Boolean>();
		vehicleCriteriaList.put(Vehicle.Criteria.MULTISIZE, false);
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(vehicleCriteriaList, dealer.make);
		
		goToVerifyPage(dealer, vehicle);
		
		//Verify
		ArrayList<String> itemsInRightColumn = verifyPage.getRightAmenitiesList();
		assertTrue("Dealer with coffee did not display coffee",itemsInRightColumn.contains("Coffee/Snack Bar"));
	}

	
	/** Given that I have been assigned a dealership without a Coffee/Snack bar
	 * 	And I am on the verify page
	 * 	Then I should see a list of amenities
	 *  And it should not contain “Coffee/Snack Bar” on the right column
	 */
	@Test
	public void NoCoffee() {
		Map<Dealer.Criteria, Boolean> dealerCriteriaList = new HashMap<Dealer.Criteria, Boolean>();
		dealerCriteriaList.put(Dealer.Criteria.COFFEE, false);
		Dealer dealer = Dealer.getRandomDealerByCriteria(dealerCriteriaList);
		
		Map<Vehicle.Criteria, Boolean> vehicleCriteriaList = new HashMap<Vehicle.Criteria, Boolean>();
		vehicleCriteriaList.put(Vehicle.Criteria.MULTISIZE, false);
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(vehicleCriteriaList, dealer.make);
		
		goToVerifyPage(dealer, vehicle);
		
		//Verify
		ArrayList<String> itemsInRightColumn = verifyPage.getRightAmenitiesList();
		assertFalse("Dealer without coffee did display coffee",itemsInRightColumn.contains("Coffee/Snack Bar"));
	}
	
	/** Given that I have been assigned a dealership with wifi
	 * 	And I am on the verify page
	 * 	Then I should see a list of amenities
	 *  And it should contain “Wi-Fi” on the right column
	 */
	@Test
	public void Wifi() {
		Map<Dealer.Criteria, Boolean> dealerCriteriaList = new HashMap<Dealer.Criteria, Boolean>();
		dealerCriteriaList.put(Dealer.Criteria.WIFI, true);
		Dealer dealer = Dealer.getRandomDealerByCriteria(dealerCriteriaList);
		
		Map<Vehicle.Criteria, Boolean> vehicleCriteriaList = new HashMap<Vehicle.Criteria, Boolean>();
		vehicleCriteriaList.put(Vehicle.Criteria.MULTISIZE, false);
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(vehicleCriteriaList, dealer.make);
		
		goToVerifyPage(dealer, vehicle);
		
		//Verify
		ArrayList<String> itemsInRightColumn = verifyPage.getRightAmenitiesList();
		assertTrue("Dealer with wifi did not display wifi",itemsInRightColumn.contains("Wi-Fi"));
	}

	
	/** Given that I have been assigned a dealership without wifi
	 * 	And I am on the verify page
	 * 	Then I should see a list of amenities
	 *  And it should not contain “Wi-Fi” on the right column
	 */
	@Test
	public void NoWifi() {
		Map<Dealer.Criteria, Boolean> dealerCriteriaList = new HashMap<Dealer.Criteria, Boolean>();
		dealerCriteriaList.put(Dealer.Criteria.WIFI, false);
		Dealer dealer = Dealer.getRandomDealerByCriteria(dealerCriteriaList);
		
		Map<Vehicle.Criteria, Boolean> vehicleCriteriaList = new HashMap<Vehicle.Criteria, Boolean>();
		vehicleCriteriaList.put(Vehicle.Criteria.MULTISIZE, false);
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(vehicleCriteriaList, dealer.make);
		
		goToVerifyPage(dealer, vehicle);
		
		//Verify
		ArrayList<String> itemsInRightColumn = verifyPage.getRightAmenitiesList();
		assertFalse("Dealer without wifi did display wifi",itemsInRightColumn.contains("Wi-Fi"));
	}
	
	/** Given that I have been assigned a dealership with a tv lounge
	 * 	And I am on the verify page
	 * 	Then I should see a list of amenities
	 *  And it should contain “TV Lounge” on the right column
	 */
	@Test
	public void TVLounge() {
		Map<Dealer.Criteria, Boolean> dealerCriteriaList = new HashMap<Dealer.Criteria, Boolean>();
		dealerCriteriaList.put(Dealer.Criteria.LOUNGE, true);
		Dealer dealer = Dealer.getRandomDealerByCriteria(dealerCriteriaList);
		
		Map<Vehicle.Criteria, Boolean> vehicleCriteriaList = new HashMap<Vehicle.Criteria, Boolean>();
		vehicleCriteriaList.put(Vehicle.Criteria.MULTISIZE, false);
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(vehicleCriteriaList, dealer.make);
		
		goToVerifyPage(dealer, vehicle);
		
		//Verify
		ArrayList<String> itemsInRightColumn = verifyPage.getRightAmenitiesList();
		assertTrue("Dealer with tv lounge did not display tv lounge",itemsInRightColumn.contains("TV Lounge"));
	}

	
	/** Given that I have been assigned a dealership without a tv lounge
	 * 	And I am on the verify page
	 * 	Then I should see a list of amenities
	 *  And it should not contain “TV Lounge” on the right column
	 */
	@Test
	public void NoTVLounge() {
		Map<Dealer.Criteria, Boolean> dealerCriteriaList = new HashMap<Dealer.Criteria, Boolean>();
		dealerCriteriaList.put(Dealer.Criteria.LOUNGE, false);
		Dealer dealer = Dealer.getRandomDealerByCriteria(dealerCriteriaList);
		
		Map<Vehicle.Criteria, Boolean> vehicleCriteriaList = new HashMap<Vehicle.Criteria, Boolean>();
		vehicleCriteriaList.put(Vehicle.Criteria.MULTISIZE, false);
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(vehicleCriteriaList, dealer.make);
		
		goToVerifyPage(dealer, vehicle);
		
		//Verify
		ArrayList<String> itemsInRightColumn = verifyPage.getRightAmenitiesList();
		assertFalse("Dealer without tv lounge did display tv lounge",itemsInRightColumn.contains("TV Lounge"));
	}
	
	/** Given that I have been assigned a dealership which offers multi-point inspection
	 * 	And I am on the verify page
	 * 	Then I should see a list of amenities
	 *  And it should contain “Multi Point Safety Inspection” on the right column
	 */
	@Test
	public void Inspection() {
		Map<Dealer.Criteria, Boolean> dealerCriteriaList = new HashMap<Dealer.Criteria, Boolean>();
		dealerCriteriaList.put(Dealer.Criteria.INSPECTION, true);
		Dealer dealer = Dealer.getRandomDealerByCriteria(dealerCriteriaList);
		
		Map<Vehicle.Criteria, Boolean> vehicleCriteriaList = new HashMap<Vehicle.Criteria, Boolean>();
		vehicleCriteriaList.put(Vehicle.Criteria.MULTISIZE, false);
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(vehicleCriteriaList, dealer.make);
		
		goToVerifyPage(dealer, vehicle);
		
		//Verify
		ArrayList<String> itemsInRightColumn = verifyPage.getRightAmenitiesList();
		assertTrue("Dealer with inspection did not display inspection",itemsInRightColumn.contains("Multi Point Safety Inspection"));
	}

	
	/** Given that I have been assigned a dealership without a multi-point safety inspection
	 * 	And I am on the verify page
	 * 	Then I should see a list of amenities
	 *  And it should not contain “Multi Point Safety Inspection” on the right column
	 */
	@Test
	public void NoInspection() {
		Map<Dealer.Criteria, Boolean> dealerCriteriaList = new HashMap<Dealer.Criteria, Boolean>();
		dealerCriteriaList.put(Dealer.Criteria.INSPECTION, false);
		Dealer dealer = Dealer.getRandomDealerByCriteria(dealerCriteriaList);
		
		Map<Vehicle.Criteria, Boolean> vehicleCriteriaList = new HashMap<Vehicle.Criteria, Boolean>();
		vehicleCriteriaList.put(Vehicle.Criteria.MULTISIZE, false);
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(vehicleCriteriaList, dealer.make);
		
		goToVerifyPage(dealer, vehicle);
		
		//Verify
		ArrayList<String> itemsInRightColumn = verifyPage.getRightAmenitiesList();
		assertFalse("Dealer without inspection did display inspection",itemsInRightColumn.contains("Multi Point Safety Inspection"));}
}
