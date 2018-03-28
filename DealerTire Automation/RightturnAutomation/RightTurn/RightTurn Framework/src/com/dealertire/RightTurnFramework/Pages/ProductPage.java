package com.dealertire.RightTurnFramework.Pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dealertire.RightTurnFramework.PageObject;
import com.dealertire.RightTurnFramework.RTPage;
import com.dealertire.RightTurnFramework.RTPageFactory;
import com.dealertire.RightTurnFramework.Utils;
import com.dealertire.RightTurnFramework.Models.Price;
import com.dealertire.RightTurnFramework.Models.Product;
import com.dealertire.RightTurnFramework.Models.Product.Criteria;
import com.dealertire.RightTurnFramework.Models.Product.Seasonality;
import com.dealertire.RightTurnFramework.Models.Size;
import com.dealertire.RightTurnFramework.Models.StaggeredProduct;

/**
 * The Product page, with the list of tires that fit a given vehicle.
 * To get here: Home -> Location -> Vehicle -> Verify -> Tire Coach -> Product
 * @author bgreen
 *
 */
public class ProductPage extends RTPage {

	//Lists
	static final String recTiresCss = "#tireTileWrapper  div.recommendedTire";
	static final String addTiresCss = "#tireTileWrapper div.additionalTire";
	static final String allTiresCss = "#tireTileWrapper > div";
	
	private ArrayList<Product> productsAlreadyChosen;
	private ArrayList<Product> cachedVisibleProducts;
	
	@FindAll({@FindBy(css=recTiresCss)})
	List<WebElement> recommendedTires;
	
	@FindAll({@FindBy(css=addTiresCss)})
	List<WebElement> additionalTires;
	
	@FindAll({@FindBy(css=allTiresCss)})
	List<WebElement> allTires;
	
	@FindBy(xpath="//ul[@id='showMe']//li[@data-filter='rec-all-products']/div[@class='showMeText']")
	WebElement filterRec;
	
	@FindBy(xpath="//ul[@id='showMe']//li[@data-filter='all-products']/div[@class='showMeText']")
	WebElement filterAll;
	
	@FindBy(xpath="//ul[@id='showMe']//li[@data-filter='promo-products']/div[@class='showMeText']")
	WebElement filterPromo;
	
	@FindBy(xpath="//ul[@id='showMe']//li[@data-filter='factory-products']/div[@class='showMeText']")
	WebElement filterFactory;
	
	@FindBy(xpath="//ul[@id='showMe']//li[@data-filter='winter-products']/div[@class='showMeText']")
	WebElement filterWinter;
	
	WebElement sortby_select;
	
	WebElement rtToaster;
	WebElement toasterClose;
	
	@FindBy(css="div.popover-content")
	WebElement popover;

	final String compareContainerLocator = "div.compareContainer";
	
	@FindBy(css=compareContainerLocator)
	WebElement compareContainer;
	
	
	@FindBy(css="div.compareContainer a.next-btn")
	WebElement compareButton;
	
	WebElement RT_Modal;
	
	@FindBy(css="div.continueShoppingLink a")
	WebElement modalContinueShopping;
	
	@FindBy(css="div.modal-body a.next-btn")
	WebElement modalNext;
	
	@FindBy(css="div.modal-header button.close")
	WebElement modalCloseButton;
	
	/**
	 * Constructor. Calls super.
	 * @param driver
	 */
	public ProductPage(WebDriver driver) {
		super(driver);
		productsAlreadyChosen = new ArrayList<Product>();
		cachedVisibleProducts = new ArrayList<Product>();
		
		if (isLoaded()) {
			int numTires = getNumVisibleTires();
			if (numTires < 5) {
				LogManager.getLogger(this.getClass().getSimpleName()).warn("The current vehicle has only " + numTires + " tires, it may not be a good fit for the datapool!");
			} else {
				LogManager.getLogger(this.getClass().getSimpleName()).info("The current vehicle has " + numTires + " tires in total.");
			}
		}
	}
	
	private void toasterCheck() {
		if (driver.findElements(By.cssSelector("#rtToaster")).size() > 0 && rtToaster.isDisplayed()
				&& toasterClose.getLocation().x <= 800) {
			//close toaster
			try {
				toasterClose.click();
			} catch (MoveTargetOutOfBoundsException e) {
				//ignore
			}
		}
	}
	
	
	@Override
	protected PageObject navigate(WebElement button) {
		Utils.ScrollElementIntoView(driver, button);
		toasterCheck();
		return super.navigate(button);
	}

	private void initLists() {
		Utils.WaitForJQueryAnimationCompletion(driver, "#tireTileWrapper");
		allTires = driver.findElements(By.cssSelector(allTiresCss));
		recommendedTires = driver.findElements(By.cssSelector(recTiresCss));
		additionalTires = driver.findElements(By.cssSelector(addTiresCss));
	}

	@Override
	public boolean isLoaded() {
		return driver.getCurrentUrl().contains("/app/product");
	}
	
	@Override
	public void waitForPageLoad() {
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.titleContains("Select Your Tires"));
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.presenceOfElementLocated(By.id("processingModal")));
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(By.id("processingModal")));
	}
	
	private ProductView getViewForProduct(Product prod) {
		if (prod.getListView() == null) return new ProductView(prod);
		return prod.getListView();
	}
	
	/* Getters */
	/**
	 * Gets the list of current recommended tires. This is anything it can find on the DOM.
	 * @return The list of tires, converted to Products.
	 */
	public ArrayList<Product> getRecommendedTires() {
		initLists(); //Reinitialize lists
		
		Utils.WaitForAjax(driver);
		ArrayList<Product> retval = new ArrayList<Product>();
		for (WebElement rTire : recommendedTires) {
			retval.add(new ProductView(rTire).getProduct());
		}
		
		return retval;
	}
	
	/**
	 * Gets the list of currently visible recommended tires. This should respect filtering.
	 * @return The list of tires, converted to Products.
	 */
	public ArrayList<Product> getVisibleRecommendedTires() {
		Utils.WaitForAjax(driver);
		initLists(); //Reinitialize lists
		
		
		ArrayList<Product> retval = new ArrayList<Product>();
		for (WebElement rTire : recommendedTires) {
			try {
				if (rTire.isDisplayed()) retval.add(new ProductView(rTire).getProduct());
			} catch (StaleElementReferenceException e) {
				continue; //We just ignore that element
			}
		}
		
		return retval;
	}
	
	/**
	 * Gets the list of current "additional" tires. This is anything it can find on the DOM.
	 * @return The list of tires, converted to Products.
	 */
	public ArrayList<Product> getAdditionalTires() {
		initLists(); //Reinitialize lists
		
		Utils.WaitForAjax(driver);
		ArrayList<Product> retval = new ArrayList<Product>();
		for (WebElement rTire : additionalTires) {
			retval.add(new ProductView(rTire).getProduct());
		}
		
		return retval;
	}
	
	/**
	 * Gets the list of current visible "Additional" tires. This should respect filtering.
	 * @return The list of tires, converted to Products.
	 */
	public ArrayList<Product> getVisibleAdditionalTires() {
		initLists(); //Reinitialize lists
		
		Utils.WaitForAjax(driver);
		ArrayList<Product> retval = new ArrayList<Product>();
		for (WebElement rTire : additionalTires) {
			try {
				if (rTire.isDisplayed()) retval.add(new ProductView(rTire).getProduct());
			} catch (StaleElementReferenceException e) {
				continue; //We just ignore that element
			}
		}
		
		return retval;
	}
	
	/**
	 * Get all tires on the DOM
	 * @return The list of tires, converted to Products
	 */
	public ArrayList<Product> getAllTires() {
		initLists(); //Reinitialize lists
		
		Utils.WaitForAjax(driver);
		ArrayList<Product> retval = new ArrayList<Product>();
		for (WebElement rTire : allTires) {
			retval.add(new ProductView(rTire).getProduct());
		}
		
		return retval;
	}
	
	/**
	 * Get all visible tires. This should respect filtering.
	 * @return The list of tires, converted to Products.
	 */
	public ArrayList<Product> getAllVisibleTires() {
		initLists(); //Reinitialize lists
		
		Utils.WaitForAjax(driver);
		ArrayList<Product> retval = new ArrayList<Product>();
		for (WebElement rTire : allTires) {
			try {
				if (rTire.isDisplayed()) {
					retval.add(new ProductView(rTire).getProduct());
				}
			} catch (StaleElementReferenceException e) {
				continue; //We just ignore that element
			}
		}

		cachedVisibleProducts = retval;
		return retval;
	}
	
	/**
	 * Get the number of visible tires. This should respect filtering.
	 * @return The number of tires
	 */
	public Integer getNumVisibleTires() {
		Utils.WaitForAjax(driver);
		initLists(); //Reinitialize lists
		int count = 0;
		
		
		for (WebElement rTire : allTires) {
			try {
				if (rTire.isDisplayed()) {
					count++;
				}
			} catch (StaleElementReferenceException e) {
				continue; //We just ignore that element
			}
		}
		return count;
	}
	
	/**
	 * Get all products that match a given criteria. Just in case, use this before filtering. 
	 * @param c The criteria all products should match.
	 * @param isTrue Whether the products should match the criteria or not match it
	 * @return The list of tires, converted to Products.
	 */
	public ArrayList<Product> getProductsByCriteria(Criteria c, boolean isTrue) {
		
		ArrayList<Product> retval = new ArrayList<Product>();
		for (WebElement rTire : recommendedTires) {
			Product p = new ProductView(rTire).getProduct();
			if (p.matchesCriteria(c) == isTrue) retval.add(p);
		}
		
		for (WebElement rTire : additionalTires) {
			Product p = new ProductView(rTire).getProduct();
			if (p.matchesCriteria(c) == isTrue) retval.add(p);
		}
		
		return retval;
	}
	
	/**
	 * Get a product on the page that matches the criteria. Handy for selecting a product. 
	 * This will not return the same tire twice. 
	 * @param c The criteria the product should match.
	 * @param isTrue Whether the criteria should be true or false
	 * @return The tire as a Product
	 * @throws IllegalArgumentException if there are no products that can be chosen, which can happen if you have exhausted the possible products on previous calls or if there are no products on the page matching your criteria.
	 */
	public Product getRandomProductByCriteria(Criteria c, boolean isTrue) {
		//Try to read this from cache.
		ArrayList<Product> domain;
		if (this.cachedVisibleProducts.size() == this.getNumVisibleTires()) {
			domain = cachedVisibleProducts;
		} else {
			domain = getAllVisibleTires();
		}
		domain.removeAll(productsAlreadyChosen);
		
		if (domain.size() <= 0) {
			throw new IllegalArgumentException("No product matched criteria");
		}
		Random rand = new Random();
		Product chosenOne = domain.get(rand.nextInt(domain.size()));
		productsAlreadyChosen.add(chosenOne);
		return chosenOne;
	}
	
	/**
	 * Get a random tire. Please use this for selecting a product rather than getting them all and picking the first one.
	 * This will not return the same tire twice. 
	 * @return The tire, ready to be selected.
	 * @throws IllegalArgumentException if there are no products that can be chosen, which can happen if you have exhausted the possible products on previous calls.
	 */
	public Product getRandomProduct() {
		//Try to read this from cache.
		ArrayList<Product> domain;
		if (this.cachedVisibleProducts.size() == this.getNumVisibleTires()) {
			domain = cachedVisibleProducts;
		} else {
			domain = getAllVisibleTires();
		}
		domain.removeAll(productsAlreadyChosen);
		
		if (domain.size() <= 0) {
			throw new IllegalArgumentException("Ran out of products!");
		}

		Random rand = new Random();
		Product chosenOne = domain.get(rand.nextInt(domain.size()));
		productsAlreadyChosen.add(chosenOne);
		return chosenOne;
	}
	
	/**
	 * Get a random tire from the Recommended list. Please use this for selecting a product rather than getting them all and picking the first one.
	 * This will not return the same tire twice. 
	 * @return The tire, ready to be selected.
	 * @throws IllegalArgumentException if there are no products that can be chosen, which can happen if you have exhausted the possible products on previous calls.
	 */
	public Product getRandomRecommendedProduct() {
		ArrayList<Product> domain = getVisibleRecommendedTires();
		domain.removeAll(productsAlreadyChosen);
		
		if (domain.size() <= 0) {
			throw new IllegalArgumentException("Ran out of products!");
		}
		Random rand = new Random();
		Product chosenOne = domain.get(rand.nextInt(domain.size()));
		productsAlreadyChosen.add(chosenOne);
		return chosenOne;
	}
	
	/**
	 * Get a random tire not from the Recommended list. Please use this for selecting a product rather than getting them all and picking the first one.
	 * This will not return the same tire twice. 
	 * @return The tire, ready to be selected.
	 * @throws IllegalArgumentException if there are no products that can be chosen, which can happen if you have exhausted the possible products on previous calls.
	 */
	public Product getRandomNonRecommendedProduct() {
		ArrayList<Product> domain = getVisibleAdditionalTires();
		domain.removeAll(productsAlreadyChosen);
		
		if (domain.size() <= 0) {
			throw new IllegalArgumentException("Ran out of products!");
		}
		Random rand = new Random();
		Product chosenOne = domain.get(rand.nextInt(domain.size()));
		productsAlreadyChosen.add(chosenOne);
		return chosenOne;
	}
	
	/**
	 * Get the text displayed in the popover.
	 * @return The text
	 */
	public String getPopoverText() {
		return popover.getText();
	}
	
	/**
	 * Get the number of products in the little circle beside each filter.
	 * @param f The filter you want the number from.
	 * @return The number of products in that filter's category.
	 */
	public int getNumProductsInFilter(Filters f) {
		WebElement numElement = getElementFromFilter(f).findElement(By.xpath("../span"));
		String number = numElement.getText();
		if (number.isEmpty()) return 0;
		return Integer.parseInt(number);
	}
	
	/**
	 * Get the number of images displaying in that little comparison box at the bottom of the page
	 * @return The number of images displayed. Should be the number of products to compare.
	 */
	public int getNumImagesInCompareContainer() {
		Utils.WaitForAllAnimationComplete(driver);
		return compareContainer.findElements(By.tagName("img")).size();
	}
	
	/**
	 * Checks if the popover is displayed
	 * @return True if it is; false if not.
	 */
	public boolean isPopoverShowing() {
		return popover.isDisplayed();
	}
	
	/**
	 * Checks if the winter tire filter is displayed
	 * @return True if it is, false if it is not.
	 */
	public boolean isWinterFilterShowing() {
		return filterWinter.isDisplayed();
	}
	/**
	 * Checks if the compare container is displayed
	 * @return True if it is, false if it is not.
	 */
	public boolean isCompareContainerShowing() {
		Utils.WaitForJQueryAnimationCompletion(driver, compareContainerLocator);
		return compareContainer.isDisplayed() 
				&& Integer.parseInt(compareContainer.getCssValue("bottom").replace("px","")) > -100; //Because when it's offscreen, it's still showing
	}
	
	/**
	 * Checks if there is a modal showing on this page.
	 * @return True if there is, false if there is not.
	 */
	public boolean isModalShowing() {
		//Right after you click the button, there's a brief pause before the animation starts.
		//If you try to call this right away, the animationComplete wait will return too soon because the animation isn't starting
		//According to wMontgomery, this is not a processing delay we can wait for, but a hard-coded sleep
		//within the bootstrap framework to prevent the modal from showing up too fast and scaring the user.
		//So we have a small hard-coded sleep here to cover that edge case. 
		//Sorry. --Bgreen
		
		try { Thread.sleep(1000);	} catch (InterruptedException e) {}
		
		Utils.WaitForAllAnimationComplete(driver);
		return RT_Modal.isDisplayed();
	}
	
	
	/*Page actions*/
	
	/**
	 * Select a quantity of tires
	 * @param p The product to set the quantity for
	 * @param numTires The number of tires to select
	 * @return The current page, for method chaining
	 */
	public PageObject selectQuantity(Product p, int numTires) {
		LogManager.getLogger(this.getClass().getSimpleName()).info("Selecting quantity.");
		Utils.WaitForAjax(driver);
		getViewForProduct(p).selectNumTires(numTires);
		return this;
	}
	
	/**
	 * Select a random quantity of tires, for when you don't care how many.
	 * @param p The product to set the quantity for
	 * @param isStaggered Whether this is a staggered set or not.
	 * @return The current page, for method chaining
	 * @deprecated in favor of {@link #selectRandomQuantity(Product)}
	 */
	public PageObject selectRandomQuantity(Product p, boolean isStaggered) {
		Random rand = new Random();
		int max = isStaggered ? 2 : 4;
		int qty = rand.nextInt(max)+1;
		LogManager.getLogger(this.getClass().getSimpleName()).info("Selecting " + qty + " of product " + p);
		return selectQuantity(p, qty);
	}
	
	
	/**
	 * Select a random quantity of tires, for when you don't care how many.
	 * @param p The product to set the quantity for
	 * @return The current page, for method chaining
	 */
	public PageObject selectRandomQuantity(Product p) {
		Random rand = new Random();
		int max = p instanceof StaggeredProduct ? 2 : 4;
		int qty = rand.nextInt(max)+1;
		LogManager.getLogger(this.getClass().getSimpleName()).info("Selecting " + qty + " of product " + p);
		return selectQuantity(p, qty);
	}
	
	/**
	 * Selects a product and adds it to the cart. This does not select a number of tires, so please do that first.
	 * @param p The product to select
	 * @return The page you are taken to when that product is selected. This uses some prediction, so please verify that the page isLoaded
	 */
	public PageObject selectProduct(Product p) {
		//The URL might not change, so we can't use navigate here.
		WebElement select = getViewForProduct(p).getSelectButton();
		select.click();
		WaitForProductSelectToResolve();
		
		//Get PageObject to return
		PageObject nextPage = RTPageFactory.getPageFromURL(driver.getCurrentUrl(), driver);
		PageFactory.initElements(driver, nextPage);
		
		//Make sure the page is usable
		nextPage.waitForPageLoad();
		Utils.WaitForAjax(driver);
		return nextPage; 
			
	}
	
	private void WaitForProductSelectToResolve()
	{
		 Wait<WebDriver> wait = new FluentWait<WebDriver>( driver )
				    .withTimeout( RTPage.DEFAULT_TIMEOUT*6, TimeUnit.SECONDS);
				 
				 
		  wait.until( new ExpectedCondition<Boolean>() { 
		    @Override 
		    public Boolean apply( WebDriver webDriver ) {
		    	//We're done when either we move to the next page OR we show the popover saying we're not moving on
		    	try {
		    		return !isLoaded() || popover.isDisplayed();
		    	} catch (NoSuchElementException e) {
		    		return !isLoaded();
		    	}
		    }
		  } );
	
	}
	
	/**
	 * Add a product to the compare list. Will do nothing if it's already being compared.
	 * @param p The product to add to the compare list.
	 * @return The current page, for chaining purposes.
	 */
	public PageObject addProductToCompare(Product p) {
		WebElement compareCheckbox = getViewForProduct(p).getCompareCheckbox();
		if (!compareCheckbox.isSelected()) {
			WebElement clickTarget = compareCheckbox.findElement(By.xpath("../span"));
			clickTarget.click();
		}
		Utils.WaitForAllAnimationComplete(driver); //There's about four animations, so let's listen for all of them to be done
		return this;
	}
	
	/**
	 * Remove a product from the compare list. Will do nothing if it's not already being compared.
	 * @param p The product to remove from the compare list.
	 * @return The current page, for chaining purposes.
	 */
	public PageObject removeProductFromCompare(Product p) {
		WebElement compareCheckbox = getViewForProduct(p).getCompareCheckbox();
		if (compareCheckbox.isSelected()){
			WebElement clickTarget = compareCheckbox.findElement(By.xpath("../span"));
			clickTarget.click();
		}
		Utils.WaitForJQueryAnimationCompletion(driver, compareContainerLocator);
		return this;
	}
	
	/**
	 * Clicks the "Compare" button. Please check if the compare box is showing first!
	 * @return The page to which you are taken when this action occurs, probably the Compare page.
	 */
	public PageObject clickCompare() {
		Utils.WaitForJQueryAnimationCompletion(driver, compareContainerLocator);
		return navigate(compareButton);
	}
	
	/**
	 * Clicks the "Continue shopping" button on the "too many items" modal. Please check if the modal is showing first!
	 * @return The current page, as this should dismiss the modal. 
	 */
	public PageObject modalClickContinueShopping() {
		modalContinueShopping.click();
		return this;
	}
	
	/**
	 * Clicks the "Compare Now" button on the "too many items" modal. Please check if the modal is showing first!
	 * @return The page to which you are taken when this action occurs, probably the Compare page.
	 */
	public PageObject modalClickNext() {
		return navigate(modalNext);
	}
	
	/**
	 * Clicks the "[X]" button on the "too many items" modal. Please check if the modal is showing first!
	 * @return The current page, as this should dismiss the modal. 
	 */
	public PageObject modalClose() {
		modalCloseButton.click();
		return this;
	}
	
	/**
	 * Selects a product and adds it to the cart. 
	 * @param p The product to select
	 * @param numTires The number of tires to add to the cart.
	 * @return The page you are taken to when that product is selected.
	 */
	public PageObject selectProduct(Product p, int numTires) {
		getViewForProduct(p).selectNumTires(numTires);
		WebElement select = getViewForProduct(p).getSelectButton();
		return navigate(select);
	}
	
	/**
	 * Clicks the "show details" link on a given product. 
	 * @param p The product to show details for.
	 * @return The page that is navigated to when this action occurs. Probably the details page.
	 */
	public PageObject clickDetailsButton(Product p) {
		return navigate(getViewForProduct(p).getDetailsButton());
	}
	
	/**
	 * Clicks the name of a given product. 
	 * @param p The product to show details for.
	 * @return The page that is navigated to when this action occurs. Probably the details page.
	 */
	public PageObject clickName(Product p) {
		return navigate(getViewForProduct(p).getNameElem());
	}
	

	/**
	 * Clicks the picture for a given product. 
	 * @param p The product to show details for.
	 * @return The page that is navigated to when this action occurs. Probably the details page.
	 */
	public PageObject clickPicture(Product p) {
		return navigate(getViewForProduct(p).getPictureElem());
	}

	/**
	 * Click the reviews link for a given product
	 * @param p The product to click
	 * @return The page that is navigated to when this action occurs. Probably the details page.
	 */
	public PageObject clickReviews(Product p) {
		return navigate(getViewForProduct(p).getReviewElem());
	}

	
	private WebElement getElementFromFilter(Filters f) {
		switch(f) {
		case ALL:
			return filterAll;
		case FACTORY:
			return filterFactory;
		case PROMO:
			return filterPromo;
		case RECOMMENDED:
			return filterRec;
		case WINTER:
			return filterWinter;
		default:
			return null;
		}
	}

	/**
	 * Filters the results list by a given filter.
	 * @param f The filter to apply
	 * @return The current page, for chaining purposes.
	 */
	public ProductPage filterBy(Filters f) {
		getElementFromFilter(f).click();
		return this;
	}
	
	/**
	 * Sorts the results list by a given criteria
	 * @param s The criteria for sorting
	 * @return The current page, for chaining purposes.
	 */
	public ProductPage sortBy(Sort s) {
		Select sortSelect = new Select(sortby_select);
		
		switch(s) {
		case AtoZ:
			sortSelect.selectByVisibleText("Brand: (A to Z)");
			break;
		case PriceHigh:
			sortSelect.selectByVisibleText("Price: High to Low");
			break;
		case PriceLow:
			sortSelect.selectByVisibleText("Price: Low to High");
			break;
		case TreadWarranty:
			sortSelect.selectByVisibleText("Treadlife Warranty");
			break;
		case ZtoA:
			sortSelect.selectByVisibleText("Brand: (Z to A)");
			break;
		default:
			break;
		};
		
		Utils.WaitForJQueryAnimationCompletion(driver, "#tireTileWrapper");
		return this;
	}
	
	

	/*Lists of choices */

	
	/** Filters for the Product page */
	public enum Filters {
		/** Filter by Recommended products */
		RECOMMENDED,
		
		/**Filter by All products */
		ALL,
		
		/**Filter by products with a promotion */
		PROMO,
		
		/**Filter by OEM fitment */
		FACTORY,
		
		/**Filter by Winter tire */
		WINTER
	}
	
	/**Sort criteria for the Product page*/
	public enum Sort {
		/** Sort alphabetically, A-Z */
		AtoZ,
		
		/**Sort alphabetically, Z-A*/
		ZtoA,
		
		/**Sort by price, low to high */
		PriceLow,
		
		/**Sort by price, high to low */
		PriceHigh,
		
		/**Sort by treadlife warranty */
		TreadWarranty
	}

	/**
	 * The list view for a given product. This represents the DOM elements that display a given product, and as such, is rather ephemeral.
	 * @author bgreen
	 */
	public class ProductView {
		private Product prod;
		private WebElement selectButton;
		private WebElement detailsButton;
		private WebElement nameElem;
		private WebElement picture;
		private Select numTiresList;
		private WebElement reviewElem;
		private WebElement compareBox;
		
		
		/**
		 * The basic constructor. Pass in the root element for the display and it will unpack itself by scraping the DOM.
		 * @param rootElement Should be the top div with an ID like "recommendedTire_0"
		 */
		public ProductView(WebElement rootElement) {
			initDOMElements(rootElement);
			
			//Construct a Product
			String html = rootElement.getAttribute("innerHTML");
			Document doc = Jsoup.parse(html);
			
		    
			String id = rootElement.getAttribute("id");
			int displayOrder = Integer.parseInt(id.substring(id.indexOf("_")+1));
			String name = nameElem.getText();

			
			//brand sucks to grab from DOM. Just fair warning.
			//Example: <i class="rt-icon pirelli_small"></i>
			Element brandTag = doc.getElementsByClass("rt-icon").get(0);
			String brandclass = brandTag.className().split("\\s")[1];
			String brand = brandclass.substring(0, brandclass.lastIndexOf('_'));
					
			Seasonality seasonality = Seasonality.UNKNOWN;
			//Determine seasonality by icon scraping
			
			Element product_details_characteristics = doc.getElementsByClass("product_details_characteristics").get(0);
			boolean isWinter = !product_details_characteristics.getElementsByClass("tire-winter").isEmpty();
			boolean isAllSeason = !product_details_characteristics.getElementsByClass("tire-allseason").isEmpty();
			boolean isThreeSeason = !product_details_characteristics.getElementsByClass("tire-3season").isEmpty();


			if (isWinter) seasonality = Seasonality.WINTER;
			if (isThreeSeason) seasonality = Seasonality.THREE_SEASON;
			if (isAllSeason) seasonality = Seasonality.ALL_SEASON;
			
			//if we show the OEM tag, we're OEM
			boolean isOEM = !doc.getElementsByClass("oem-tag").isEmpty();
			
			//if we show the Promo tag, we're on offer
			boolean isPromo = !doc.getElementsByClass("promo_display").isEmpty();
			
			//if we show the Runflat tag, we're runflat
			boolean isRunflat = !doc.getElementsByClass("tire-runflat").isEmpty();
			
			if (doc.getElementsByClass("rearQtySelect").size() > 0
					&& doc.getElementsByClass("frontQtySelect").size() > 0) {
				Element frontQtySelect = doc.getElementsByClass("frontQtySelect").get(0);
				Element rearQtySelect = doc.getElementsByClass("rearQtySelect").get(0);
				
				Price frontPrice = new Price(frontQtySelect.getElementsByClass("price-large").get(0).text());
				Price rearPrice = new Price(rearQtySelect.getElementsByClass("price-large").get(0).text());
				prod = new StaggeredProduct(id, name, frontPrice, rearPrice, brand, seasonality, isOEM, isPromo, isRunflat);
				
				Size frontSize = new Size(frontQtySelect.getElementsByClass("stagTireSize").get(0).text());
				Size rearSize = new Size(rearQtySelect.getElementsByClass("stagTireSize").get(0).text());
				((StaggeredProduct) prod).setFrontSize(frontSize);
				((StaggeredProduct) prod).setRearSize(rearSize);
				
			} else {
				Price price = new Price(doc.getElementsByClass("price-large").get(0).text());
				prod = new Product(id, name, price, brand, seasonality, isOEM, isPromo, isRunflat);
			}
			
			prod.setDisplayOrder(displayOrder);
			prod.setListView(this);
			
			//reviews
			Element reviewContainer = doc.getElementsByClass("additional_reviews").get(0);
			
			if (reviewContainer.text().contains("Reviews:")) {
				String reviewNumText = reviewElem.getText();
				reviewNumText = reviewNumText.replace("(", "");
				reviewNumText = reviewNumText.replace(")", "");
				prod.setNumReviews(Integer.parseInt(reviewNumText));
			}
			
			//warranty
			Element warrantyContainerDiv = doc.getElementsByClass("warranty").get(0);
			Element warrantyContainer = warrantyContainerDiv.getElementsByTag("a").get(0);
			String warrantyString = warrantyContainer.text();
			Integer warranty; 
			
			//Format: "35K Miles" or "See brochure"
			if (warrantyString.contains("K")) {
				warranty = Integer.parseInt(warrantyString.substring(0, warrantyString.indexOf('K')));
			} else {
				warranty = 0;
			}
			
			prod.setWarranty(warranty);
		}
		
		/**
		 * Creates a ProductView from a given product. If it's not accessible from the DOM, you'll get errors! 
		 * @param prod The product
		 */
		public ProductView(Product prod) {
			initDOMElements(driver.findElement(By.id(prod.getListID())));
			this.prod = prod;
			prod.setListView(this);
		}
		
		private void initDOMElements(WebElement rootElement) {
			selectButton = rootElement.findElement(By.cssSelector("div.putOnVehicle a"));
			detailsButton = rootElement.findElement(By.cssSelector("div.see-details"));
			numTiresList = new Select(rootElement.findElement(By.cssSelector(".product-qty-select")));
			nameElem = rootElement.findElement(By.cssSelector("div.name a"));
			picture = rootElement.findElement(By.cssSelector("div.addImageWrapper img"));
			compareBox = rootElement.findElement(By.cssSelector("div.add-compare input"));
			WebElement reviewContainer = rootElement.findElement(By.className("additional_reviews"));
			reviewElem = reviewContainer.findElement(By.tagName("a"));
		}
		
		/**
		 * Get the product displayed by this view
		 * @return The product
		 */
		public Product getProduct() {
			return prod;
		}
		
		/**
		 * Get the portion of the display that contains the review link
		 * @return The element
		 */
		public WebElement getReviewElem() {
			return reviewElem;
		}

		/**
		 * Get the portion of the display that contains the checkbox for the comparison
		 * @return The element
		 */
		public WebElement getCompareCheckbox() {
			return compareBox;
		}
		
		/**
		 * Get the select button so you can click it.
		 * @return the selectButton
		 */
		public WebElement getSelectButton() {
			return selectButton;
		}
		
		/**
		 * Get the "show details" link so you can click it.
		 * @return The details button
		 */
		public WebElement getDetailsButton() {
			return detailsButton;
		}
		
		/**
		 * Gets the element containing the product name, so you can click it.
		 * @return The name element.
		 */
		public WebElement getNameElem() {
			return nameElem;
		}
		
		/**
		 * Get the element containing the image of the tire, so you can click it.
		 * @return The element with the picture
		 */
		public WebElement getPictureElem() {
			return picture;
		}
		
		/**
		 * Select the number of tires to order.
		 * @param num The number of tires you want to order. Should be at least 1 and at most 6 or you may get errors.
		 */
		public void selectNumTires(int num) {
			numTiresList.selectByValue(new Integer(num).toString());
			prod.setQuantity(num);
		}
	}	
	
}
