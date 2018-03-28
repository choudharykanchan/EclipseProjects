package com.dealertire.RightTurnTesting;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

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
import com.dealertire.RightTurnFramework.Pages.ProductDetailsPage;
import com.dealertire.RightTurnFramework.Pages.ProductPage;
import com.dealertire.RightTurnFramework.Pages.TireCoachPage;
import com.dealertire.RightTurnFramework.Pages.TireCoachPage.Weather;
import com.dealertire.RightTurnFramework.Pages.VehiclePage;
import com.dealertire.RightTurnFramework.Pages.VerifyPage;
import com.dealertire.RightTurnFramework.Pages.WinterTireQuestionPage;

/**
 * Tests for the Product Details feature.
 * @author bgreen
 */
public class ProductDetailsTests extends BaseTest {

	/**
     * @see com.dealertire.RightTurnFramework.BaseTest#BaseTest(String, String, String)
     */
	public ProductDetailsTests(String os, String browserName, String browserVersion) {
		super(os, browserName, browserVersion);
	}

	/**
	 * Runs at the beginning of each test if you don't care what vehicle you're choosing. Will not run automatically.
	 */
	public void SetUp() {		
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
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
			
			logger.info("STEUP: Navigating to Tire Coach page");
			tireCoachPage = (TireCoachPage) verifyPage.clickNext();
			
			logger.info("SETUP: Navigating to product page");
			productPage = (ProductPage) tireCoachPage.clickNext();
			SessionManager.saveSession("product", "44114", vehicle, driver);
		}
	}
	
	/**
	 * Run at the beginning of a test to choose a specific vehicle by criteria. Will not run automatically.
	 * @param criteriaList The list of criteria for the vehicle selection stage
	 */
	public void SetUp(Map<Criteria, Boolean> criteriaList) {		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("SETUP: Using vehicle " + vehicle);
		
		logger.info("SETUP: Navigating to location page");
		locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
		locationPage.enterZipCode("44114");
		
		logger.info("SETUP: Navigating to vehicle page");
		vehiclePage = (VehiclePage) locationPage.clickNext();
		vehiclePage.selectVehicle(vehicle);
		
		logger.info("SETUP: Navigating to verify page");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		logger.info("STEUP: Navigating to Tire Coach page");
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		
		logger.info("SETUP: Navigating to product page");
		productPage = (ProductPage) tireCoachPage.clickNext();
	}
	
	/**
	 * Given I am on the product page
	 * When I click the “details” link beside a tire
	 * Then I should be on the detail page
	 * And that tire should appear on the details page
	 */
	@Test
	public void clickDetailsButton() {
		SetUp();
		
		logger.info("Step 1: click 'Details' button");
		Product p = productPage.getRandomProduct();
		detailsPage = (ProductDetailsPage) productPage.clickDetailsButton(p);
		assertTrue("Details page did not load", detailsPage.isProductDisplayed(p));
	}
	
	/**
	 * Given I am on the product page
	 * When I click the picture of a tire
	 * Then I should be on the detail page
	 * And that tire should appear on the details page
	 */
	@Test
	public void clickPicture() {
		SetUp();
		logger.info("Step 1: click product picture");
		Product p = productPage.getRandomProduct();
		detailsPage = (ProductDetailsPage) productPage.clickPicture(p);
		assertTrue("Details page did not load", detailsPage.isProductDisplayed(p));
	}
	
	/**
	 * Given I am on the product page
	 * When I click the name of a tire
	 * Then I should be on the detail page
	 * And that tire should appear on the details page
	 */
	@Test
	public void clickTireName() {
		SetUp();
		
		logger.info("Step 1: click product name");
		Product p = productPage.getRandomProduct();
		detailsPage = (ProductDetailsPage) productPage.clickName(p);
		assertTrue("Details page did not load", detailsPage.isProductDisplayed(p));
	}
	
	/**
	 * Given I am on the product page
	 * And I am looking at a tire with reviews
	 * When I click the number beside “Reviews” for that tire
	 * Then I should be on the detail page
	 * And that tire should appear on the details page
	 * And I should be looking at the review section
	 */
	@Test
	public void clickExistingReviews() {
		SetUp();
		
		logger.info("Step 1: click product review");
		Product p = productPage.getRandomProductByCriteria(Product.Criteria.HASREVIEWS, true);
		detailsPage = (ProductDetailsPage) productPage.clickReviews(p);
		assertTrue("Details page did not load", detailsPage.isProductDisplayed(p));
		assertTrue("Review section did not load", detailsPage.isAtReviewSection()); 
	}
	
	/**
	 * Given I am on the product page
	 * And I am looking at a tire with reviews
	 * When I click the words “Be the first to write a review”
	 * Then I should be on the detail page
	 * And that tire should appear on the details page
	 * And I should be looking at the review section
	 */
	@Test
	public void clickNewReview() {
		SetUp();
		
		logger.info("Step 1: click 'add a new review'");
		Product p = productPage.getRandomProductByCriteria(Product.Criteria.HASREVIEWS, false);
		detailsPage = (ProductDetailsPage) productPage.clickReviews(p);
		assertTrue("Details page did not load", detailsPage.isProductDisplayed(p));
		assertTrue("Review section did not load", detailsPage.isAtReviewSection()); 
	}
	
	/**
	 * Given I am on the product page
	 * When I click the “details” link beside a recommended tire
	 * Then I should be on the detail page
	 * And that tire should appear on the details page
	 * And the tire should note that it is recommended
	 */
	@Test
	public void recomendedTire() {
		SetUp();
		
		logger.info("Step 1: click details link");
		Product p = productPage.getRandomRecommendedProduct();
		detailsPage = (ProductDetailsPage) productPage.clickDetailsButton(p);
		assertTrue("Details page did not load", detailsPage.isProductDisplayed(p));
		assertTrue("Recommended product banner did not appear", detailsPage.isProductRecommended());
	}
	
	/**
	 * Given I am on the product page
	 * When I click the “details” link beside a non-recommended tire
	 * Then I should be on the detail page
	 * And that tire should appear on the details page
	 * And the tire should not note that it is recommended
	 */
	@Test
	public void nonRecomendedTire() {
		SetUp();
		
		logger.info("Step 1: click details link");
		Product p = productPage.getRandomNonRecommendedProduct();
		detailsPage = (ProductDetailsPage) productPage.clickDetailsButton(p);
		assertTrue("Details page did not load", detailsPage.isProductDisplayed(p));
		assertFalse("Recommended product banner did appear", detailsPage.isProductRecommended());
	}
	
	/**
	 * Given I have chosen the options needed to see winter tires
	 * And I am on the product page 
	 * When I click the “details” link beside a winter tire
	 * Then I should be on the details page
	 * And I should see “Winter” displayed
	 */
	@Test
	public void winterTire() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		criteriaList.put(Criteria.WTPKG, false);
		criteriaList.put(Criteria.WINTER, true);
		SetUp(criteriaList);
		
		logger.info("Step 1: Choose extreme winter weather");
		tireCoachPage = (TireCoachPage) productPage.clickYourNeeds();
		tireCoachPage.selectOnlyWeather(Weather.EXTREMEWINTER);
		
		logger.info("Step 2: On the Question page, click 'yes'");
		winterTireQuestionPage = (WinterTireQuestionPage) tireCoachPage.clickNext();
		winterTireQuestionPage.clickYes();
		productPage = (ProductPage) winterTireQuestionPage.clickNext();
		
		logger.info("Step 3: click details link");
		Product p = productPage.getRandomProductByCriteria(Product.Criteria.WINTER, true);
		detailsPage = (ProductDetailsPage) productPage.clickDetailsButton(p);
		assertTrue("Details page did not load", detailsPage.isProductDisplayed(p));
		assertEquals("Wrong seasonality!", p.getSeasonality(), detailsPage.getSeasonalityIcon());
		
	}
	
	/**
	 * Given I am on the product page 
	 * When I click the “details” link beside a three-season tire
	 * Then I should be on the details page
	 * And I should see “3-season” displayed
	 */
	@Test
	public void threeSeasonTire() {
		SetUp();
		Product p = productPage.getRandomProductByCriteria(Product.Criteria.THREE_SEASON, true);
		
		logger.info("Step 1: click details link");
		detailsPage = (ProductDetailsPage) productPage.clickDetailsButton(p);
		assertTrue("Details page did not load", detailsPage.isProductDisplayed(p));
		assertEquals("Wrong seasonality!", p.getSeasonality(), detailsPage.getSeasonalityIcon());
	}
	
	/**
	 * Given I am on the product page 
	 * When I click the “details” link beside a all-season tire
	 * Then I should be on the details page
	 * And I should see “All-season” displayed
	 */
	@Test
	public void allSeasonTire() {
		SetUp();
		Product p = productPage.getRandomProductByCriteria(Product.Criteria.ALL_SEASON, true);
		
		logger.info("Step 1: click details link");
		detailsPage = (ProductDetailsPage) productPage.clickDetailsButton(p);
		assertTrue("Details page did not load", detailsPage.isProductDisplayed(p));
		assertEquals("Wrong seasonality!", p.getSeasonality(), detailsPage.getSeasonalityIcon());
	}
	
	/**
	 * Given I have selected a vehicle with runflat tires
	 * And I am on the product page
	 * When I click the “details” link beside a runflat tire
	 * Then I should be on the details page
	 * And I should see “Runflat” displayed
	 */
	@Test
	public void runflatTire() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		criteriaList.put(Criteria.RUNFLAT, true);
		SetUp(criteriaList);
		
		logger.info("Step 1: click details link");
		Product p = productPage.getRandomProductByCriteria(Product.Criteria.RUNFLAT, true);
		detailsPage = (ProductDetailsPage) productPage.clickDetailsButton(p);
		assertTrue("Details page did not load", detailsPage.isProductDisplayed(p));
		assertTrue("Runflat icon not shown!", detailsPage.isShowingRunflatIcon());
	}
	
	/**
	 * Given I am on the details page
	 * When I click “Back to results”
	 * Then I should be on the product page
	 */
	@Test 
	public void clickBack() {
		SetUp();
		
		logger.info("Step 1: click details link");
		Product p = productPage.getRandomProduct();
		detailsPage = (ProductDetailsPage) productPage.clickDetailsButton(p);
		
		logger.info("Step 2: click back");
		PageObject prevPage = detailsPage.clickBack();
		assertTrue("Product page did not load!", prevPage instanceof ProductPage);
	}
	
	/**
	 * Given I am on the details page
	 * And I have not selected a quantity
	 * When I click the “Select” button on a tire
	 * Then I should be on the details page
	 * And a pop-up should ask me how many tires I need
	 */
	@Test
	public void clickSelectNoQuantity() {
		SetUp();
		logger.info("Step 1: click details link");
		Product p = productPage.getRandomProduct();
		detailsPage = (ProductDetailsPage) productPage.clickDetailsButton(p);
		
		//Do not select quantity
		logger.info("Step 2: do not select quantity");
		
		logger.info("Step 3: click Select");
		PageObject currPage = detailsPage.clickSelect();
		assertTrue("Details page unloaded", currPage instanceof ProductDetailsPage);
		assertTrue("Details page no longer loaded", currPage.isLoaded());
		
		assertTrue("Popover did not appear", detailsPage.isPopoverShowing());
		assertEquals("Wrong messaging in popover", "How many tires do you need?", detailsPage.getPopoverText());		
	}
	
	/**
	 * Given I am on the details page
	 * When I select a quantity
	 * And I click the “Select” button on a tire
	 * Then I should be on the Install page
	 */
	@Test
	public void clickSelectWithQuantity() {
		SetUp();
		
		logger.info("Step 1: click details link");
		Product p = productPage.getRandomProduct();
		detailsPage = (ProductDetailsPage) productPage.clickDetailsButton(p);
		
		logger.info("Step 2: Select quantity");
		detailsPage.selectRandomQuantity();
		
		logger.info("Step 3: click Select");
		PageObject currPage = detailsPage.clickSelect();

		assertTrue("Install page did not appear", currPage instanceof InstallPage);
		assertTrue("Install page did not load", currPage.isLoaded());
	}
}
