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
import com.dealertire.RightTurnFramework.Pages.ComparePage;
import com.dealertire.RightTurnFramework.Pages.InstallPage;
import com.dealertire.RightTurnFramework.Pages.LocationPage;
import com.dealertire.RightTurnFramework.Pages.ProductPage;
import com.dealertire.RightTurnFramework.Pages.TireCoachPage;
import com.dealertire.RightTurnFramework.Pages.VehiclePage;
import com.dealertire.RightTurnFramework.Pages.VerifyPage;

/**
 * Feature: Compare products
 * @author bgreen
 */
public class ProductCompareTests extends BaseTest {

	private Vehicle vehicle;
	/**
     * @see com.dealertire.RightTurnFramework.BaseTest#BaseTest(String, String, String)
     */
	public ProductCompareTests(String os, String browserName, String browserVersion) {
		super(os, browserName, browserVersion);
	}
	
	/**
	 * Runs before every test to ensure we're on the right page.
	 */
	@Before
	public void SetUp() {
		logger.info("SETUP: Navigating to location page");
		locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
		locationPage.enterZipCode("44114");
		
		logger.info("SETUP: Navigating to vehicle page");
		vehiclePage = (VehiclePage) locationPage.clickNext();
	}
	
	private void standardSetUp() {
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("SETUP: Using vehicle " + vehicle);

		navigateToProductPage();
	}
	
	private void navigateToProductPage() {
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
			
			logger.info("SETUP: Navigating to Tire Coach page");
			tireCoachPage = (TireCoachPage) verifyPage.clickNext();
			
			logger.info("SETUP: Navigating to product page");
			productPage = (ProductPage) tireCoachPage.clickNext();
			SessionManager.saveSession("product", "44114", vehicle, driver);
		}
	}
	
	/**
	 * Given that I have selected a vehicle
	 * And I am on the product page
	 * When I select the compare checkbox on one tire
	 * Then I should see a floating thumbnail in the lower right corner
	 * And it should only contain one image
	 * When I click the “Compare” button on the floating thumbnail
	 * Then I should be on the comparison page
	 * And I should see one tire’s details
	 */
	@Test
	public void singleTire() {
		standardSetUp();
		
		//When I select the compare checkbox
		Product prod = productPage.getRandomProduct();

		logger.info("Step 1: Adding " + prod + " to compare box.");
		productPage.addProductToCompare(prod);
		
		assertTrue("Compare container did not appear", productPage.isCompareContainerShowing());
		assertEquals("Wrong number of items in compare container", 1, productPage.getNumImagesInCompareContainer());
		
		logger.info("Step 2: Click 'Compare'");
		PageObject nextPage = productPage.clickCompare();
		assertTrue("Compare page did not appear", nextPage instanceof ComparePage);
		comparePage = (ComparePage) nextPage; 
		
		Product prodInSlot1 = comparePage.getProductInSlot(1);
		assertEquals("Wrong product in slot 1", prod, prodInSlot1); //product is showing in slot 1		
	}
	
	/**
	 * Given that I have selected a vehicle
	 * And I am on the product page
	 * When I select the compare checkbox on one tire
	 * And I select the compare checkbox on a second tire
	 * Then I should see a floating thumbnail in the lower right corner
	 * And it should contain two images
	 * When I click the “Compare” button on the floating thumbnail
	 * Then I should be on the comparison page
	 * And I should see two tire’s details
	 */
	@Test
	public void twoTires() {
		standardSetUp();
		
		//When I select the compare checkbox
		Product product1 = productPage.getRandomProduct();
		Product product2 = productPage.getRandomProduct();

		logger.info("Step 1a: Adding " + product1 + " to compare box.");
		productPage.addProductToCompare(product1);
		logger.info("Step 1b: Adding " + product2 + " to compare box.");
		productPage.addProductToCompare(product2);
		
		assertTrue("Compare container did not appear", productPage.isCompareContainerShowing());
		assertEquals("Wrong number of items in compare container", 2, productPage.getNumImagesInCompareContainer());
		
		logger.info("Step 2: Click 'Compare'");
		PageObject nextPage = productPage.clickCompare();
		assertTrue("Compare page did not appear", nextPage instanceof ComparePage);
		comparePage = (ComparePage) nextPage; 
		
		Product prodInSlot1 = comparePage.getProductInSlot(1);
		assertEquals("Wrong product in slot 1", product1, prodInSlot1);
		
		Product prodInSlot2 = comparePage.getProductInSlot(2);
		assertEquals("Wrong product in slot 2", product2, prodInSlot2);
	}
	
	/**
	 * Given that I have selected a vehicle
	 * And I am on the product page
	 * When I select the compare checkbox on one tire
	 * And I select the compare checkbox on a second tire
	 * And I select the compare checkbox on a third tire
	 * Then I should see a floating thumbnail in the lower right corner
	 * And it should contain three images
	 * When I click the “Compare” button on the floating thumbnail
	 * Then I should be on the comparison page
	 * And I should see three tire’s details
	 */
	@Test
	public void threeTires() {
		standardSetUp();
		
		Product product1 = productPage.getRandomProduct();
		Product product2 = productPage.getRandomProduct();
		Product product3 = productPage.getRandomProduct();
		
		logger.info("Step 1a: Adding " + product1 + " to compare box.");
		productPage.addProductToCompare(product1);
		logger.info("Step 1b: Adding " + product2 + " to compare box.");
		productPage.addProductToCompare(product2);
		logger.info("Step 1c: Adding " + product3 + " to compare box.");
		productPage.addProductToCompare(product3);
		
		assertTrue("Compare container did not appear", productPage.isCompareContainerShowing());
		assertEquals("Wrong number of items in compare container", 3, productPage.getNumImagesInCompareContainer());
		
		logger.info("Step 2: Click 'Compare'");
		PageObject nextPage = productPage.clickCompare();
		assertTrue("Compare page did not appear", nextPage instanceof ComparePage);
		comparePage = (ComparePage) nextPage; 
		
		Product prodInSlot1 = comparePage.getProductInSlot(1);
		assertEquals("Wrong product in slot 1", product1, prodInSlot1);
		
		Product prodInSlot2 = comparePage.getProductInSlot(2);
		assertEquals("Wrong product in slot 2", product2, prodInSlot2);
		
		Product prodInSlot3 = comparePage.getProductInSlot(3);
		assertEquals("Wrong product in slot 3", product3, prodInSlot3);
	}
	
	/**
	 * Given that I have selected a vehicle
	 * And I am on the product page
	 * When I select the compare checkbox on one tire
	 * And I select the compare checkbox on a second tire
	 * And I select the compare checkbox on a third tire
	 * And I click the compare checkbox on a fourth tire
	 * Then I should see a modal telling me I have reached the maximum number of items to compare
	 * When I click “Compare Now”
	 * Then I should be on the comparison page
	 * and I should see three tires’ details
	 */
	@Test
	public void fourTires() {
		standardSetUp();
		
		Product product1 = productPage.getRandomProduct();
		Product product2 = productPage.getRandomProduct();
		Product product3 = productPage.getRandomProduct();
		Product product4 = productPage.getRandomProduct();
		
		logger.info("Step 1a: Adding " + product1 + " to compare box.");
		productPage.addProductToCompare(product1);
		logger.info("Step 1b: Adding " + product2 + " to compare box.");
		productPage.addProductToCompare(product2);
		logger.info("Step 1c: Adding " + product3 + " to compare box.");
		productPage.addProductToCompare(product3);
				
		assertTrue("Compare container did not appear", productPage.isCompareContainerShowing());
		assertEquals("Wrong number of items in compare container", 3, productPage.getNumImagesInCompareContainer());
		assertFalse("Modal appeared too early", productPage.isModalShowing());
		
		logger.info("Step 1d: Adding " + product4 + " to compare box.");
		productPage.addProductToCompare(product4);
		assertTrue("Modal did not appear", productPage.isModalShowing());
		
		logger.info("Step 2: Click 'Next' in the 'too many items' modal");
		PageObject nextPage = productPage.modalClickNext();
		assertTrue("Compare page did not appear", nextPage instanceof ComparePage);
		comparePage = (ComparePage) nextPage;
		
		Product prodInSlot1 = comparePage.getProductInSlot(1);
		assertEquals("Wrong product in slot 1", product1, prodInSlot1);
		
		Product prodInSlot2 = comparePage.getProductInSlot(2);
		assertEquals("Wrong product in slot 2", product2, prodInSlot2);
		
		Product prodInSlot3 = comparePage.getProductInSlot(3);
		assertEquals("Wrong product in slot 3", product3, prodInSlot3);
	}
	
	/**
	 * Given that I have selected a vehicle
	 * And I am on the product page
	 * When I select the compare checkbox on one tire
	 * And I select the compare checkbox on a second tire
	 * And I select the compare checkbox on a third tire
	 * And I click the compare checkbox on a fourth tire
	 * Then I should see a modal telling me I have reached the maximum number of items to compare
	 * 
	 * When I click “Continue shopping”
	 * Then the modal should vanish
	 * And I should be on the product page
	 */	
	@Test
	public void fourTiresBackOut() {
		standardSetUp();
		
		Product product1 = productPage.getRandomProduct();
		Product product2 = productPage.getRandomProduct();
		Product product3 = productPage.getRandomProduct();
		Product product4 = productPage.getRandomProduct();
		
		logger.info("Step 1a: Adding " + product1 + " to compare box.");
		productPage.addProductToCompare(product1);
		logger.info("Step 1b: Adding " + product2 + " to compare box.");
		productPage.addProductToCompare(product2);
		logger.info("Step 1c: Adding " + product3 + " to compare box.");
		productPage.addProductToCompare(product3);
				
		assertTrue("Compare container did not appear", productPage.isCompareContainerShowing());
		assertEquals("Wrong number of items in compare container", 3, productPage.getNumImagesInCompareContainer());
		assertFalse("Modal appeared too early", productPage.isModalShowing());
		
		logger.info("Step 1d: Adding " + product4 + " to compare box.");
		productPage.addProductToCompare(product4);
		assertTrue("Modal did not appear", productPage.isModalShowing());
		
		logger.info("Step 2: Click 'Continue Shopping' in the 'too many items' modal");
		productPage.modalClickContinueShopping();
		assertFalse("Modal remained open", productPage.isModalShowing());
		assertTrue("Product page was no longer shown", productPage.isLoaded());
	}
	
	/**
	 * Given that I have selected a vehicle
	 * And I am on the product page
	 * When I select the compare checkbox on one tire
	 * And I select the compare checkbox on a second tire
	 * And I select the compare checkbox on a third tire
	 * And I click the compare checkbox on a fourth tire
	 * Then I should see a modal telling me I have reached the maximum number of items to compare
	 * 
	 * When I click the “[X]” button
	 * Then the modal should vanish
	 * And I should be on the product page
	 */
	@Test
	public void fourTiresCloseModal() {
		standardSetUp();
		
		Product product1 = productPage.getRandomProduct();
		Product product2 = productPage.getRandomProduct();
		Product product3 = productPage.getRandomProduct();
		Product product4 = productPage.getRandomProduct();
		
		logger.info("Step 1a: Adding " + product1 + " to compare box.");
		productPage.addProductToCompare(product1);
		logger.info("Step 1b: Adding " + product2 + " to compare box.");
		productPage.addProductToCompare(product2);
		logger.info("Step 1c: Adding " + product3 + " to compare box.");
		productPage.addProductToCompare(product3);
				
		assertTrue("Compare container did not appear", productPage.isCompareContainerShowing());
		assertEquals("Wrong number of items in compare container", 3, productPage.getNumImagesInCompareContainer());
		assertFalse("Modal appeared too early", productPage.isModalShowing());
		
		logger.info("Step 1d: Adding " + product4 + " to compare box.");
		productPage.addProductToCompare(product4);
		assertTrue("Modal did not appear", productPage.isModalShowing());
		
		logger.info("Step 2: Close the modal");		
		productPage.modalClose();
		assertFalse("Modal remained open", productPage.isModalShowing());
		assertTrue("Product page was no longer shown", productPage.isLoaded());
	}
	
	
	/**
	 * Given that I have selected a vehicle
	 * And I am on the product page
	 * When I select the compare checkbox on one tire
	 * And I click the same checkbox again
	 * Then I should not see a floating thumbnail
	 */
	@Test
	public void removeFromCompareOneItem() {
		standardSetUp();
		
		Product prod = productPage.getRandomProduct();
		logger.info("Step 1: Adding " + prod + " to compare box.");
		productPage.addProductToCompare(prod);
		assertTrue("Compare container did not appear", productPage.isCompareContainerShowing());
		
		logger.info("Step 2: Remove product from compare box");
		productPage.removeProductFromCompare(prod);
		assertFalse("Compare container did not vanish", productPage.isCompareContainerShowing());
	}
	
	/**
	 * When I select the compare checkbox on one tire
	 * And I select the compare checkbox on a second tire
	 * And I click the same compare checkbox again
	 * Then I should see a floating thumbnail
	 * And it should only have one image
	 */
	@Test
	public void removeFromCompareOneOfTwoItems() {
		standardSetUp();		
		Product prod1 = productPage.getRandomProduct();
		Product prod2 = productPage.getRandomProduct();
		
		logger.info("Step 1a: Adding " + prod1 + " to compare box.");
		productPage.addProductToCompare(prod1);
		assertTrue("Compare container did not appear", productPage.isCompareContainerShowing());
		
		logger.info("Step 1b: Adding " + prod2 + " to compare box.");
		productPage.addProductToCompare(prod2);
		assertTrue("Compare container vanished", productPage.isCompareContainerShowing());
		
		logger.info("Step 2: Remove " + prod2 + " from compare box");
		productPage.removeProductFromCompare(prod2);
		assertTrue("Compare container vanished with a product still in it", productPage.isCompareContainerShowing());
		assertEquals("Wrong number of items in compare container", 1, productPage.getNumImagesInCompareContainer());
	}
	
	/**
	 * Given I have selected one tire to compare
	 * And I am on the compare page
	 * When I select another tire from the center dropdown
	 * Then I should see that tire’s details appear in the center pane
	 * When I click “remove” on the center pane’s tire
	 * Then I should not see any tire in the center pane
	 */
	@Test
	public void addFromCompareCenter() {
		standardSetUp();
		
		Product product = productPage.getRandomProduct();
		Product productToAdd = productPage.getRandomProduct();
		
		logger.info("Step 1: Adding " + product + " to compare box.");
		productPage.addProductToCompare(product);
		assertTrue("Compare container did not appear", productPage.isCompareContainerShowing());
		
		logger.info("Step 2: Click compare");
		comparePage = (ComparePage) productPage.clickCompare();
		
		logger.info("Step 3: On the compare page, adding " + productToAdd + " to the center slot");
		comparePage.addProductToSlot(ComparePage.CENTER_SLOT, productToAdd);
		assertEquals("Wrong product displayed in center slot", productToAdd, comparePage.getProductInSlot(ComparePage.CENTER_SLOT));
		
		logger.info("Step 4: Remove the product in the center slot");
		comparePage.removeProductFromSlot(ComparePage.CENTER_SLOT);
		assertTrue("Product not removed!", comparePage.getProductInSlot(ComparePage.CENTER_SLOT) == null);
		
	}
	
	/**
	 * Given I have selected one tire to compare
	 * And I am on the compare page
	 * When I select another tire from the right dropdown
	 * Then I should see that tire’s details appear in the center pane
	 * When I select another tire from the right dropdown
	 * Then I should see that tire’s details appear in the right pane
	 * When I click “remove” on the right pane’s tire
	 * Then I should not see any tire in the right pane
	 */
	@Test
	public void addFromCompareRight() {
		standardSetUp();
		
		Product product = productPage.getRandomProduct();
		Product productToAdd1 = productPage.getRandomProduct();
		Product productToAdd2 = productPage.getRandomProduct();
		
		logger.info("Step 1: Adding " + product + " to compare box.");
		productPage.addProductToCompare(product);
		assertTrue("Compare container did not appear", productPage.isCompareContainerShowing());
		
		logger.info("Step 2: Click compare");
		comparePage = (ComparePage) productPage.clickCompare();
		
		logger.info("Step 3a: Add " + productToAdd1 + " to the RIGHT slot");
		comparePage.addProductToSlot(ComparePage.RIGHT_SLOT, productToAdd1);
		assertEquals("Product should have slid over to the CENTER slot", productToAdd1, comparePage.getProductInSlot(ComparePage.CENTER_SLOT));
		
		logger.info("Step 3b: Add " + productToAdd2 + " to the RIGHT slot");
		comparePage.addProductToSlot(ComparePage.RIGHT_SLOT, productToAdd2);
		assertEquals("Second product should remian in the RIGHT slot", productToAdd2, comparePage.getProductInSlot(ComparePage.RIGHT_SLOT));
		
		logger.info("Step 4: Remove from the right slot");
		comparePage.removeProductFromSlot(ComparePage.RIGHT_SLOT);
		assertTrue("Product not removed from right slot", comparePage.getProductInSlot(ComparePage.RIGHT_SLOT) == null);	
	}
	
	/**
	 * Given I have selected three tires to compare
	 * And I am on the compare page
	 * When I click “remove” on the left pane’s tire
	 * Then I should see tire 2 in the left pane
	 * And I should see tire 3 in the center pane
	 * And I should not see any tire in the right pane
	 */
	@Test
	public void removeFromCompareLeft() {
		standardSetUp();
		Product product1 = productPage.getRandomProduct();
		Product product2 = productPage.getRandomProduct();
		Product product3 = productPage.getRandomProduct();
		
		logger.info("Step 1a: Adding " + product1 + " to compare box.");
		productPage.addProductToCompare(product1);
		
		logger.info("Step 1b: Adding " + product2 + " to compare box.");
		productPage.addProductToCompare(product2);
		
		logger.info("Step 1c: Adding " + product3 + " to compare box.");
		productPage.addProductToCompare(product3);
		
		logger.info("Step 2: Click compare");
		comparePage = (ComparePage) productPage.clickCompare();
		
		logger.info("Step 3: On compare page, removed the leftmost product");
		comparePage.removeProductFromSlot(ComparePage.LEFT_SLOT);
		assertEquals("Center product should have slid over to the left slot", product2, comparePage.getProductInSlot(ComparePage.LEFT_SLOT));
		assertEquals("Right product should have slid over to the center slot", product3, comparePage.getProductInSlot(ComparePage.CENTER_SLOT));
		assertEquals("Right slot should be empty", null, comparePage.getProductInSlot(ComparePage.RIGHT_SLOT));
		
	}
	
	
	/**
	 * Given I have selected three tires to compare
	 * And I am on the compare page
	 * When I click “remove” on the center pane’s tire
	 * Then I should see tire 3 in the center pane
	 * And I should not see any tire in the right pane
	 */
	@Test
	public void removeFromCompareCenter() {
		standardSetUp();
		Product product1 = productPage.getRandomProduct();
		Product product2 = productPage.getRandomProduct();
		Product product3 = productPage.getRandomProduct();
		
		logger.info("Step 1a: Adding " + product1 + " to compare box.");
		productPage.addProductToCompare(product1);
		
		logger.info("Step 1b: Adding " + product2 + " to compare box.");
		productPage.addProductToCompare(product2);
		
		logger.info("Step 1c: Adding " + product3 + " to compare box.");
		productPage.addProductToCompare(product3);
		
		logger.info("Step 2: Click compare");
		comparePage = (ComparePage) productPage.clickCompare();
		
		logger.info("Step 3: On compare page, removed the central product");
		comparePage.removeProductFromSlot(ComparePage.CENTER_SLOT);
		assertEquals("Left product should remain in place", product1, comparePage.getProductInSlot(ComparePage.LEFT_SLOT));
		assertEquals("Right product should slide over to center slot", product3, comparePage.getProductInSlot(ComparePage.CENTER_SLOT));
		assertEquals("Right slot should now be empty", null, comparePage.getProductInSlot(ComparePage.RIGHT_SLOT));
		
	}
	
	/**
	 * Given I have selected three tires to compare
	 * And I am on the compare page
	 * When I change the quantity of the leftmost tire
	 * Then the price should change to reflect the new quantity
	 * And the center quantity should change to match the left quantity
	 * And the center price should change to reflect the new quantity
	 * And the right quantity should change to match the left quantity
	 * And the right price should change to reflect the new quantity
	 * 
	 * When I change the quantity of the center tire
	 * Then the price should change to reflect the new quantity
	 * And the left quantity should change to match the center quantity
	 * And the left price should change to reflect the new quantity
	 * And the right quantity should change to match the center quantity
	 * And the right price should change to reflect the new quantity
	 * 
	 * When I change the quantity of the right tire
	 * Then the price should change to reflect the new quantity
	 * And the left quantity should change to match the right quantity
	 * And the left price should change to reflect the new quantity
	 * And the center quantity should change to match the right quantity
	 * And the center price should change to reflect the new quantity
	 */
	@Test
	public void priceChangesComparePage() {
		//Special set up: This won't work right with staggered vehicle
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		criteriaList.put(Criteria.STAGGERED, false);
		vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("Using vehicle " + vehicle);

		navigateToProductPage();
		
		Product product1 = productPage.getRandomProduct();
		Product product2 = productPage.getRandomProduct();
		Product product3 = productPage.getRandomProduct();
		
		logger.info("Step 1a: Adding " + product1 + " to compare box.");
		productPage.addProductToCompare(product1);
		logger.info("Step 1b: Adding " + product2 + " to compare box.");
		productPage.addProductToCompare(product2);
		logger.info("Step 1c: Adding " + product3 + " to compare box.");
		productPage.addProductToCompare(product3);
		
		logger.info("Step 2: Click compare");
		comparePage = (ComparePage) productPage.clickCompare();
		
		//Capture base price
		Price basePriceOne = product1.getPrice();
		Price basePriceTwo = product2.getPrice();
		Price basePriceThree = product3.getPrice();
		
		logger.info("Step 3: Change left quantity to 2");
		comparePage.changeQuantity(ComparePage.LEFT_SLOT, 2);
		
		assertEquals("Left quantity did not update", 2, comparePage.getCurrentQuantity(ComparePage.LEFT_SLOT));
		assertEquals("Left price did not update", basePriceOne.times(2), comparePage.getCurrentDisplayPrice(ComparePage.LEFT_SLOT));
		
		assertEquals("Center quantity did not update",2, comparePage.getCurrentQuantity(ComparePage.CENTER_SLOT));
		assertEquals("Center price did not update",basePriceTwo.times(2), comparePage.getCurrentDisplayPrice(ComparePage.CENTER_SLOT));
		
		assertEquals("Right quantity did not update",2, comparePage.getCurrentQuantity(ComparePage.RIGHT_SLOT));
		assertEquals("Right price did not update",basePriceThree.times(2), comparePage.getCurrentDisplayPrice(ComparePage.RIGHT_SLOT));
		
		logger.info("Step 4: Change center quantity to 3");
		comparePage.changeQuantity(ComparePage.CENTER_SLOT, 3);
		
		assertEquals("Left quantity did not update",3, comparePage.getCurrentQuantity(ComparePage.LEFT_SLOT));
		assertEquals("Left price did not update",basePriceOne.times(3), comparePage.getCurrentDisplayPrice(ComparePage.LEFT_SLOT));
		
		assertEquals("Center quantity did not update",3, comparePage.getCurrentQuantity(ComparePage.CENTER_SLOT));
		assertEquals("Center price did not update",basePriceTwo.times(3), comparePage.getCurrentDisplayPrice(ComparePage.CENTER_SLOT));
		
		assertEquals("Right quantity did not update",3, comparePage.getCurrentQuantity(ComparePage.RIGHT_SLOT));
		assertEquals("Right price did not update",basePriceThree.times(3), comparePage.getCurrentDisplayPrice(ComparePage.RIGHT_SLOT));
		
		logger.info("Step 5: Change right quantity to 4");
		comparePage.changeQuantity(ComparePage.RIGHT_SLOT, 4);
		
		assertEquals("Left quantity did not update",4, comparePage.getCurrentQuantity(ComparePage.LEFT_SLOT));
		assertEquals("Left price did not update",basePriceOne.times(4), comparePage.getCurrentDisplayPrice(ComparePage.LEFT_SLOT));
		
		assertEquals("Center quantity did not update",4, comparePage.getCurrentQuantity(ComparePage.CENTER_SLOT));
		assertEquals("Center price did not update",basePriceTwo.times(4), comparePage.getCurrentDisplayPrice(ComparePage.CENTER_SLOT));
		
		assertEquals("Right quantity did not update",4, comparePage.getCurrentQuantity(ComparePage.RIGHT_SLOT));
		assertEquals("Right price did not update",basePriceThree.times(4), comparePage.getCurrentDisplayPrice(ComparePage.RIGHT_SLOT));
	
	}
	
	/**
	 * Given I am on the compare page
	 * When I click “Back to results”
	 * Then I should be on the product page
	 */
	@Test
	public void backLink() {
		standardSetUp();
		Product prod = productPage.getRandomProduct();

		logger.info("Step 1: Adding " + prod + " to compare box.");
		productPage.addProductToCompare(prod);
		
		assertTrue("Compare container did not appear", productPage.isCompareContainerShowing());
		assertEquals("Wrong number of items in compare container", 1, productPage.getNumImagesInCompareContainer());
		
		logger.info("Step 2: Click 'Compare'");
		PageObject nextPage = productPage.clickCompare();
		assertTrue("Compare page did not appear", nextPage instanceof ComparePage);
		comparePage = (ComparePage) nextPage; 
		
		logger.info("Step 3: Click 'Back'");
		PageObject prevPage = comparePage.clickBack();
		assertTrue("Product page did not return", prevPage instanceof ProductPage);
		assertTrue("Product page did not return", prevPage.isLoaded());
	}
	
	/**
	 * Given I am on the compare page
	 * And I have not selected a quantity
	 * When I click the “Select” button on a tire
	 * Then I should be on the Compare page
	 * And a pop-up should ask me how many tires I need
	 * When I select one or more tires
	 * And I click the “Select” button on a tire
	 * Then I should be on the Install page
	 */
	@Test
	public void nextLink() {
		standardSetUp();
		
		Product prod = productPage.getRandomProduct();

		logger.info("Step 1: Adding " + prod + " to compare box.");
		productPage.addProductToCompare(prod);
		
		assertTrue("Compare container did not appear", productPage.isCompareContainerShowing());
		assertEquals("Wrong number of items in compare container", 1, productPage.getNumImagesInCompareContainer());
		
		logger.info("Step 2: Click 'Compare'");
		PageObject nextPage = productPage.clickCompare();
		assertTrue("Compare page did not appear", nextPage instanceof ComparePage);
		comparePage = (ComparePage) nextPage; 
		
		logger.info("Step 3: Click 'Select'");
		PageObject currPage = comparePage.selectProduct(ComparePage.LEFT_SLOT);
		assertTrue("Compare page no longer displayed",currPage instanceof ComparePage);
		assertTrue("Compare page no longer loaded", currPage.isLoaded());
		assertTrue("Popover did not appear", comparePage.isPopoverShowing());
		
		logger.info("Step 4: Select a quantity");
		comparePage.changeQuantity(ComparePage.LEFT_SLOT, 1);
		
		logger.info("Step 5: Click 'Select' again");
		currPage = comparePage.selectProduct(ComparePage.LEFT_SLOT);
		assertTrue("Install page did not appear", currPage instanceof InstallPage);
		assertTrue("Install page did not load", currPage.isLoaded());
	}
}
