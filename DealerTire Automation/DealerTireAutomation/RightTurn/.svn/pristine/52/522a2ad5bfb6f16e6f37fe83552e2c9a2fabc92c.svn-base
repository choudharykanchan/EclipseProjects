package com.dealertire.RightTurnTesting;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.dealertire.RightTurnFramework.BaseTest;
import com.dealertire.RightTurnFramework.PageObject;
import com.dealertire.RightTurnFramework.Models.Product;
import com.dealertire.RightTurnFramework.Models.Vehicle;
import com.dealertire.RightTurnFramework.Models.Vehicle.Criteria;
import com.dealertire.RightTurnFramework.Pages.LocationPage;
import com.dealertire.RightTurnFramework.Pages.ProductPage;
import com.dealertire.RightTurnFramework.Pages.TireCoachPage;
import com.dealertire.RightTurnFramework.Pages.VehiclePage;
import com.dealertire.RightTurnFramework.Pages.VerifyPage;
import com.dealertire.RightTurnFramework.Pages.TireCoachPage.Weather;
import com.dealertire.RightTurnFramework.Pages.WinterPackageQuestionPage;
import com.dealertire.RightTurnFramework.Pages.WinterTireQuestionPage;

/**
 * Feature: Winter Tires
 * @author bgreen
 */
public class WinterTireTests extends BaseTest {

	/**
     * @see com.dealertire.RightTurnFramework.BaseTest#BaseTest(String, String, String)
     */
	public WinterTireTests(String os, String browserName, String browserVersion) {
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
	 * Test:
	 * Given that I am on the tire coach page
	 * When I select “Hot Sunny”
	 * And I click “Next”
	 * Then I should be on the Product page
	 * And I should not see any winter tires
	 */
	@Test
	public void noNeedForWinterTires_Sunny() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.TRUCK, true);
		criteriaList.put(Criteria.MULTISIZE, false);
		criteriaList.put(Criteria.WINTER, true);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		
		logger.info("Step 1: Select vehicle with winter tires");
		logger.info("Using vehicle " + vehicle);
		vehiclePage.selectVehicle(vehicle);
		
		logger.info("Step 2: Continue to tire coach page");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		
		logger.info("Step 3: Select sunny weather");
		tireCoachPage.selectOnlyWeather(Weather.SUNNY);
		
		logger.info("Step 4: Continue to product page");
		PageObject nextPage = tireCoachPage.clickNext();
		//On product page:
		assertTrue("Did not reach product page", nextPage instanceof ProductPage);
		//No winter tire filter:
		assertFalse("Should not see winter tire filter", ((ProductPage) nextPage).isWinterFilterShowing());
		//No winter tires found:
		assertTrue("Should not see winter tires", ((ProductPage) nextPage).getProductsByCriteria(Product.Criteria.WINTER, true).isEmpty());
	}
	
	/**
	 * Test:
	 * Given that I am on the tire coach page
	 * When I select "Occasional Rain”
	 * And I click “Next”
	 * Then I should be on the Product page
	 * And I should not see any winter tires
	 */
	@Test
	public void noNeedForWinterTires_someRain() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.TRUCK, true);
		criteriaList.put(Criteria.MULTISIZE, false);
		criteriaList.put(Criteria.WINTER, true);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		
		logger.info("Step 1: Select vehicle with winter tires");
		logger.info("Using vehicle " + vehicle);
		vehiclePage.selectVehicle(vehicle);
		
		logger.info("Step 2: Continue to tire coach page");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		
		logger.info("Step 3: Select rainy weather");
		tireCoachPage.selectOnlyWeather(Weather.RAIN);
		
		logger.info("Step 4: Continue to product page");
		PageObject nextPage = tireCoachPage.clickNext();
		//On product page:
		assertTrue("Did not reach product page", nextPage instanceof ProductPage);
		//No winter tire filter:
		assertFalse("Should not see winter tire filter", ((ProductPage) nextPage).isWinterFilterShowing());
		//No winter tires found:
		assertTrue("Should not see winter tires", ((ProductPage) nextPage).getProductsByCriteria(Product.Criteria.WINTER, true).isEmpty());
	}
	
	/**
	 * Test:
	 * Given that I am on the tire coach page
	 * When I select “Frequent Rain”
	 * And I click “Next”
	 * Then I should be on the Product page
	 * And I should not see any winter tires
	 */
	@Test
	public void noNeedForWinterTires_freqRain() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.TRUCK, true);
		criteriaList.put(Criteria.MULTISIZE, false);
		criteriaList.put(Criteria.WINTER, true);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		
		logger.info("Step 1: Select vehicle with winter tires");
		logger.info("Using vehicle " + vehicle);
		vehiclePage.selectVehicle(vehicle);
		
		logger.info("Step 2: Continue to tire coach page");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		
		logger.info("Step 3: Select very rainy weather");
		tireCoachPage.selectOnlyWeather(Weather.FREQUENTRAIN);
		
		logger.info("Step 4: Continue to product page");
		PageObject nextPage = tireCoachPage.clickNext();
		//On product page:
		assertTrue("Did not reach product page", nextPage instanceof ProductPage);
		//No winter tire filter:
		assertFalse("Should not see winter tire filter", ((ProductPage) nextPage).isWinterFilterShowing());
		//No winter tires found:
		assertTrue("Should not see winter tires", ((ProductPage) nextPage).getProductsByCriteria(Product.Criteria.WINTER, true).isEmpty());	}
	
	/**
	 * Given I have selected a vehicle without winter tires
	 * And I am on the Tire Coach page
	 * When I select “Snow, Rain, Ice”
	 * And I click “Next”
	 * Then I should be on the Product page
	 * And I should not see winter tires
	 * And I should not see the winter tires filter
	 */
	@Test
	public void noWinterTires() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		criteriaList.put(Criteria.WINTER, false);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("Using vehicle " + vehicle);
		
		vehiclePage.selectVehicle(vehicle);
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		
		logger.info("Step 1: Select vehicle with no winter tires");
		logger.info("Using vehicle " + vehicle);
		vehiclePage.selectVehicle(vehicle);
		
		logger.info("Step 2: Continue to tire coach page");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		
		logger.info("Step 3: Select snowy weather");
		tireCoachPage.selectOnlyWeather(Weather.SNOW);
		
		logger.info("Step 4: Continue to product page");
		PageObject nextPage = tireCoachPage.clickNext();
		//On product page:
		assertTrue("Did not reach product page", nextPage instanceof ProductPage);
		//No winter tire filter:
		assertFalse("Should not see winter tire filter", ((ProductPage) nextPage).isWinterFilterShowing());
		//No winter tires found:
		assertTrue("Should not see winter tires", ((ProductPage) nextPage).getProductsByCriteria(Product.Criteria.WINTER, true).isEmpty());	}
	
	/**
	 * Given I have selected a vehicle with winter tires
	 * And I am on the Tire Coach page
	 * When I select “Snow, Rain, Ice”
	 * And I click “Next”
	 * Then I should be on the Product page
	 * And none of the recommended tires should be winter tires
	 * And I should see winter tires
	 * And I should see the winter tires filter
	 */
	@Test
	public void winterTires() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		criteriaList.put(Criteria.WINTER, true);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		
		logger.info("Step 1: Select vehicle with winter tires");
		logger.info("Using vehicle " + vehicle);
		vehiclePage.selectVehicle(vehicle);
		
		logger.info("Step 2: Continue to tire coach page");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		
		logger.info("Step 3: Select snowy weather");
		tireCoachPage.selectOnlyWeather(Weather.SNOW);
		
		logger.info("Step 4: Continue to product page");
		PageObject nextPage = tireCoachPage.clickNext();
		//On product page:
		assertTrue("Should reach product page", nextPage instanceof ProductPage);
		productPage = (ProductPage) nextPage;
		
		//Recommended tires are not winter: 
		ArrayList<Product> recTires = productPage.getRecommendedTires();
		for (Product recProd : recTires) {
			assertFalse("Recommended tire was winter: " + recProd, recProd.matchesCriteria(Product.Criteria.WINTER));
		}
		
		//Winter tires found:
		assertTrue("Winter tires should be displayed", productPage.getProductsByCriteria(Product.Criteria.WINTER, true).size() > 0);
		//Winter tire filter:
		assertTrue("Winter tire filter should be displayed", productPage.isWinterFilterShowing());		
	}
	
	/**
	 * Given I have selected a vehicle with winter tires
	 * And I am on the Tire Coach page
	 * hen I select “Extreme Winter Weather”
	 * And I click “Next”
	 * Then I should be on the winter tire question screen 
	 */
	@Test
	public void extremeWeather() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		criteriaList.put(Criteria.WINTER, true);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("Step 1: Select vehicle with winter tires");
		logger.info("Using vehicle " + vehicle);
		vehiclePage.selectVehicle(vehicle);
		
		logger.info("Step 2: Continue to tire coach page");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		
		logger.info("Step 3: Select very snowy weather");
		tireCoachPage.selectOnlyWeather(Weather.EXTREMEWINTER);
		
		logger.info("Step 4: Continue to product page");
		PageObject nextPage = tireCoachPage.clickNext();
		
		assertTrue("Should be asked the Winter Tire Question", nextPage instanceof WinterTireQuestionPage);
		winterTireQuestionPage = (WinterTireQuestionPage) nextPage;
	}
	
	/**
	 * Given I am on the winter tire question screen
	 * When I select “Yes”
	 * And I click "Next"
	 * Then I should be on the product page
	 * And I should see winter tires displayed
	 * And I should see the “Winter Tire” filter displayed
	 * And the recommended tires should be winter tires
	 */
	@Test
	public void extremeWeatherYes() {
		//Given I am on the winter tire question screen
		extremeWeather();
		
		logger.info("Step 5: Click 'yes' on the winter tire question page");
		winterTireQuestionPage.clickYes();
		PageObject nextPage = winterTireQuestionPage.clickNext();
		
		assertTrue("Should be on the product page", nextPage instanceof ProductPage);
		productPage = (ProductPage) nextPage;
		
		//Winter tire filter:
		assertTrue("Winter tire filter should display", productPage.isWinterFilterShowing());
		
		//Winter tires found:
		assertTrue("Winter tires should be found", productPage.getProductsByCriteria(Product.Criteria.WINTER, true).size() > 0);
		
		//Recommended tires are winter: 
		ArrayList<Product> recTires = productPage.getRecommendedTires();
		for (Product recProd : recTires) {
			assertTrue("Recommended tire was not winter tire: " + recProd, recProd.matchesCriteria(Product.Criteria.WINTER));
		}
	}
	
	/**
	 * Given I am on the winter tire question screen
	 * When I select “No”
	 * And I click “Next”
	 * Then I should be on the product page
	 * And I should not see winter tires displayed
	 * And I should not see the “Winter Tire” filter displayed
	 */
	@Test
	public void extremeWeatherNo() {
		//Given I am on the winter tire question screen
		extremeWeather();
		
		logger.info("Step 5: Click 'no' on the winter tire question page");
		winterTireQuestionPage.clickNo();
		PageObject nextPage = winterTireQuestionPage.clickNext();
		
		assertTrue("Next page should be product page", nextPage instanceof ProductPage);
		productPage = (ProductPage) nextPage;
		
		//Winter tire filter:
		assertFalse("Should not see winter tire filters", productPage.isWinterFilterShowing());
		
		//Winter tires not found:
		assertTrue("Should not see winter tires", productPage.getProductsByCriteria(Product.Criteria.WINTER, true).isEmpty());
	}
	
	/**
	 * Given I have selected a vehicle with no winter ties
	 * But with an extreme winter wheel package
	 * And I am on the tire coach page
	 * When I select “Extreme Winter Weather”
	 * And I click “Next”
	 * Then I should be on the Extreme Winter Wheel package screen
	 * And I should be advised to call for details
	 * When I click the “Next” button
	 * Then I should be on the product page
	 * And I should not see winter tires displayed
	 * And I should not see the “Winter tire” filter displayed
	 */
	@Test
	public void extremeWinterWheelPackage() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		criteriaList.put(Criteria.WINTER, false);
		criteriaList.put(Criteria.WTPKG, true);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("Step 1: Select vehicle with no winter tires");
		logger.info("Using vehicle " + vehicle);
		vehiclePage.selectVehicle(vehicle);
		
		logger.info("Step 2: Continue to tire coach page");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		
		logger.info("Step 3: Select very snowy weather");
		tireCoachPage.selectOnlyWeather(Weather.EXTREMEWINTER);
		
		PageObject nextPage = tireCoachPage.clickNext();
		assertTrue("Should be shown the winter package page", nextPage instanceof WinterPackageQuestionPage);
		
		logger.info("Step 4: Click next on the winter package page");
		nextPage = nextPage.clickNext();
		assertTrue("Should be on the product page", nextPage instanceof ProductPage);
		productPage = (ProductPage) nextPage;
		
		//Winter tire filter:
		assertFalse("Should not see winter tire filter", productPage.isWinterFilterShowing());
		
		//Winter tires not found:
		assertTrue("Should not see winter tires", productPage.getProductsByCriteria(Product.Criteria.WINTER, true).isEmpty());
	}
}
