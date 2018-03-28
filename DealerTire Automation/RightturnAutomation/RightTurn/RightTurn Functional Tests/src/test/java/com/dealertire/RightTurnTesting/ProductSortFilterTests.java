package com.dealertire.RightTurnTesting;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.dealertire.RightTurnFramework.BaseTest;
import com.dealertire.RightTurnFramework.Models.Price;
import com.dealertire.RightTurnFramework.Models.Product;
import com.dealertire.RightTurnFramework.Models.Vehicle;
import com.dealertire.RightTurnFramework.Models.Vehicle.Criteria;
import com.dealertire.RightTurnFramework.Pages.LocationPage;
import com.dealertire.RightTurnFramework.Pages.ProductPage;
import com.dealertire.RightTurnFramework.Pages.TireCoachPage;
import com.dealertire.RightTurnFramework.Pages.TireCoachPage.Weather;
import com.dealertire.RightTurnFramework.Pages.VehiclePage;
import com.dealertire.RightTurnFramework.Pages.VerifyPage;
import com.dealertire.RightTurnFramework.Pages.ProductPage.Filters;
import com.dealertire.RightTurnFramework.Pages.ProductPage.Sort;

/**
 * Feature: Sort and filter products on the ProductPage
 * @author bgreen
 *
 */
public class ProductSortFilterTests extends BaseTest {
	
	/**
     * @see com.dealertire.RightTurnFramework.BaseTest#BaseTest(String, String, String)
     */
	public ProductSortFilterTests(String os, String browserName,String browserVersion) {
		super(os, browserName, browserVersion);
	}

	/**
	 * Runs before every test to ensure we're on the right page.
	 */
	@Before
	public void SetUp() {		
		//Navigate to the Vehicle page
		logger.info("SETUP: Navigating to location page");
		locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
		locationPage.enterZipCode("44114");
		
		logger.info("SETUP: Navigating to vehicle page");
		vehiclePage = (VehiclePage) locationPage.clickNext();
	}
	
	/**
	 * Test:
	 * Given that I have selected a vehicle 
	 * When I reach the product page
	 * Then the first three tires should be recommended tires
	 * When I click the “Recommended” button
	 * Then only recommended tires should appear
	 */
	@Test
	public void recommendedTires() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("SETUP: Using vehicle " + vehicle);
		
		vehiclePage.selectVehicle(vehicle);
		logger.info("SETUP: Navigating to verify page");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		logger.info("SETUP: Navigating to tire coach page");
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		
		logger.info("SETUP: Navigating to product page");
		productPage = (ProductPage) tireCoachPage.clickNext();
		
		ArrayList<Product> recTires = productPage.getRecommendedTires();
		
		logger.info("Step 1: Filter by recommended tires.");
		productPage.filterBy(ProductPage.Filters.RECOMMENDED);
		ArrayList<Product> visibleTires = productPage.getAllVisibleTires();
		
		assertEquals("Number of visible tires should equal number of recommended tires", recTires.size(), visibleTires.size());
		assertTrue("All recommended tires should be visible", visibleTires.containsAll(recTires));
		assertTrue("All visible tires should be recommended", recTires.containsAll(visibleTires));
	}
	
	/**
	 * Test:
	 * Given that I have selected a vehicle with tires on promotion
	 * And I am on the product page
	 * When I click the “special offer” filter
	 * Then only promotion tires should appear
	 */
	@Test
	public void promoTires() {
		selectVehicleWithPromotions();		
		
		logger.info("Step 1: Filter by promotion");
		productPage.filterBy(ProductPage.Filters.PROMO);
		ArrayList<Product> visibleTires = productPage.getAllVisibleTires();
		
		//Ensure they right number shows up
		assertEquals(productPage.getNumProductsInFilter(Filters.PROMO), visibleTires.size());
		
		//Be sure they're all on offer
		for (Product p : visibleTires) {
			assertTrue(p + " did not have a promotion, yet is visible.", p.matchesCriteria(Product.Criteria.PROMO));
		}
	}
	
	/**
	 * Test:
	 * Given that I have selected a vehicle with Original Equipment tires
	 * When I click the “original equipment” filter
	 * Then only factory tires should appear
	 */
	@Test
	public void oemTires() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		criteriaList.put(Criteria.OEM, true);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("SETUP: Using vehicle " + vehicle);
		
		vehiclePage.selectVehicle(vehicle);
		logger.info("SETUP: Navigating to verify page");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		logger.info("SETUP: Navigating to tire coach page");
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		
		logger.info("SETUP: Navigating to product page");
		productPage = (ProductPage) tireCoachPage.clickNext();
		
		logger.info("Step 2: Filter by Original Equipment");
		productPage.filterBy(ProductPage.Filters.FACTORY);
		ArrayList<Product> visibleTires = productPage.getAllVisibleTires();
		
		//Ensure they right number shows up
		assertEquals("Number of visible products should equal number of factory fitment products", productPage.getNumProductsInFilter(Filters.FACTORY), visibleTires.size());
		
		//Be sure they're all OEM
		for (Product p : visibleTires) {
			assertTrue(p + " was not factory fitment!", p.matchesCriteria(Product.Criteria.OEM));
		}
	}
	
	/**
	 * Test:
	 * Given that I have selected “Snow, Rain, Ice” on the Tire Coach page
	 * And I am on the product page
	 * When I click the “winter tires” filter
	 * Then only winter tires should appear
	 */
	@Test
	public void winterTires() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		criteriaList.put(Criteria.WINTER, true);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("SETUP: Using vehicle " + vehicle);
		
		vehiclePage.selectVehicle(vehicle);
		logger.info("SETUP: Navigating to verify page");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		logger.info("SETUP: Navigating to tire coach page");
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		
		logger.info("SETUP: Navigating to product page");
		productPage = (ProductPage) tireCoachPage.clickNext();
		
		logger.info("Step 1: Filter by winter tires");
		productPage.filterBy(ProductPage.Filters.WINTER);
		ArrayList<Product> visibleTires = productPage.getAllVisibleTires();
		
		//Ensure they right number shows up
		assertEquals("Visible products should equal number of winter tires on filter", productPage.getNumProductsInFilter(Filters.WINTER), visibleTires.size());
		
		//Be sure they're all Winter
		for (Product p : visibleTires) {
			assertTrue(p + " was not a winter tire, but was visible.", p.matchesCriteria(Product.Criteria.WINTER));
		}
	}

	/**
	 * Test:
	 * Given that I am on the product page
	 * When I click the “A-Z” button
	 * Then the tires should be sorted alphabetically in ascending order
	 * When I click the “Z-A” button
	 * Then the tires should be sorted alphabetically in descending order
	 */
	@Test
	public void sortAlpha() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("SETUP: Using vehicle " + vehicle);
		
		vehiclePage.selectVehicle(vehicle);
		logger.info("SETUP: Navigating to verify page");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		logger.info("SETUP: Navigating to tire coach page");
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		
		logger.info("SETUP: Navigating to product page");
		productPage = (ProductPage) tireCoachPage.clickNext();
		
		logger.info("Step 1: Sort A-Z");
		productPage.sortBy(Sort.AtoZ);
		verifyTiresSortedAlphabetically(true);
		
		logger.info("Step 2: Sort Z-A");
		productPage.sortBy(Sort.ZtoA);
		verifyTiresSortedAlphabetically(false);
		
	}
	
	/**
	 * Test:
	 * Given that I am on the product page
	 * When I click the "Price low to high” button
	 * Then the tires should be sorted by price in ascending order
	 * When I click the “Price high to low” button
	 * Then the tires should be sorted by price in descending order
	 */
	@Test
	public void sortPrice() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("SETUP: Using vehicle " + vehicle);
		
		vehiclePage.selectVehicle(vehicle);
		logger.info("SETUP: Navigating to verify page");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		logger.info("SETUP: Navigating to tire coach page");
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		
		logger.info("SETUP: Navigating to product page");
		productPage = (ProductPage) tireCoachPage.clickNext();
		
		logger.info("Step 1: Sort by price, low-high");
		productPage.sortBy(Sort.PriceLow);
		verifyTiresSortedByPrice(true);
		
		logger.info("Step 2: Sort by price, high-low");
		productPage.sortBy(Sort.PriceHigh);
		verifyTiresSortedByPrice(false);
		
	}
	
	/**
	 * Test:
	 * Given that I am on the product page
	 * When I click “Treadlife Warranty
	 * Then the tires should be sorted by warranty, largest first
	 */
	@Test
	public void sortWarranty() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("SETUP: Using vehicle " + vehicle);
		
		vehiclePage.selectVehicle(vehicle);
		logger.info("SETUP: Navigating to verify page");
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		logger.info("SETUP: Navigating to tire coach page");
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		
		logger.info("SETUP: Navigating to product page");
		productPage = (ProductPage) tireCoachPage.clickNext();
		
		logger.info("Step 1: Sort by treadlife warrenty");
		productPage.sortBy(Sort.TreadWarranty);
		ArrayList<Product> allTiresInOrder = productPage.getAllTires();

		//Sort one copy of the product list
		Product.ComparatorTreadLifeWarranty comparator = new Product.ComparatorTreadLifeWarranty();
		ArrayList<Product> allTiresTreadlifeOrder = productPage.getAllTires();
		Collections.sort(allTiresTreadlifeOrder, comparator);
		
		//Then verify they match
		for (int i = 0; i < allTiresInOrder.size(); i++) {
			Product tireOnPage = allTiresInOrder.get(i);
			Product expectedTire = allTiresTreadlifeOrder.get(i);
			assertEquals(tireOnPage + " was out of order!", expectedTire,tireOnPage);
		}
	}
	
	/* Utility methods */
	private Vehicle selectVehicleWithPromotions() {
		//This is a bit tricky since we can't tell in advance
		logger.info("SETUP: Attempting to find vehicle that has a promotion");
		ArrayList<Vehicle> candidates = Vehicle.findVehicles(Criteria.MULTISIZE, false);
		Collections.shuffle(candidates); //To ensure we get some measure of randomness in our data
		
		for (Vehicle candidate : candidates) {
			locationPage = (LocationPage) locationPage.navigateTo();
			locationPage.enterZipCode("44114");
			vehiclePage = (VehiclePage) locationPage.clickNext();
			vehiclePage.selectVehicle(candidate);
			verifyPage = (VerifyPage) vehiclePage.clickNext();
			tireCoachPage = (TireCoachPage) verifyPage.clickNext();
			productPage = (ProductPage) tireCoachPage.clickNext();
			if (productPage.getNumProductsInFilter(Filters.PROMO) > 0) {
				logger.info("SETUP: Using vehicle " + candidate);
				return candidate;
			}
				
		}
		return null;
	}
	
	private ArrayList<String> getBrands(ArrayList<Product> productList) {
		ArrayList<String> brands = new ArrayList<String>();
		for (Product p : productList) {
			brands.add(p.getBrand());
		}
		
		return brands;
	}
	
	private ArrayList<Price> getPrices(ArrayList<Product> productList) {
		ArrayList<Price> prices = new ArrayList<Price>();
		for (Product p : productList) {
			prices.add(p.getPrice());
		}
		
		return prices;
	}
	
	private void verifyTiresSortedAlphabetically(boolean forwardDirection) {
		ArrayList<Product> recTires = productPage.getVisibleRecommendedTires();
		Collections.sort(recTires); //Sort products by product number
		
		ArrayList<Product> addTires = productPage.getVisibleRecommendedTires();
		Collections.sort(addTires);
		
		ArrayList<String> recNames = getBrands(recTires);
		Collections.sort(recNames); //Sort names in alphabetical order
		ArrayList<String> addNames = getBrands(addTires);
		Collections.sort(addNames); 
		
		if (!forwardDirection) {
			Collections.reverse(recNames);
			Collections.reverse(addNames);
		}

		//Verify that the products in order match the names in alphabetical order
		for (int i = 0; i < recTires.size(); i++) {
			assertEquals(recNames.get(i) + " was out of order!", recTires.get(i).getBrand(), recNames.get(i));
		}
		
		for (int i = 0; i < addTires.size(); i++) {
			assertEquals(addNames.get(i) + " was out of order!", addTires.get(i).getBrand(), addNames.get(i));
		}
	}
	
	private void verifyTiresSortedByPrice(boolean forwardDirection) {
		ArrayList<Product> recTires = productPage.getVisibleRecommendedTires();
		Collections.sort(recTires); //Sort products by product number
		
		ArrayList<Product> addTires = productPage.getVisibleRecommendedTires();
		Collections.sort(addTires);
		
		ArrayList<Price> recPrices = getPrices(recTires);
		Collections.sort(recPrices); //Sort names in alphabetical order
		ArrayList<Price> addPrices = getPrices(addTires);
		Collections.sort(addPrices); 
		
		if (!forwardDirection) {
			Collections.reverse(recPrices);
			Collections.reverse(addPrices);
		}

		//Verify that the products in order match the names in alphabetical order
		for (int i = 0; i < recTires.size(); i++) {
			assertEquals(recPrices.get(i) + " was out of order!", recTires.get(i).getPrice(), recPrices.get(i));
		}
		
		for (int i = 0; i < addTires.size(); i++) {
			assertEquals(addPrices.get(i) + " was out of order!", addTires.get(i).getPrice(), addPrices.get(i));
		}
	}
	
	
}
