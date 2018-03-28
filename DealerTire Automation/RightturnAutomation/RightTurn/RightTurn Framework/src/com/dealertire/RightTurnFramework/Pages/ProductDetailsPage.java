package com.dealertire.RightTurnFramework.Pages;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dealertire.RightTurnFramework.PageObject;
import com.dealertire.RightTurnFramework.RTPage;
import com.dealertire.RightTurnFramework.Models.Price;
import com.dealertire.RightTurnFramework.Models.Product;
import com.dealertire.RightTurnFramework.Models.Size;
import com.dealertire.RightTurnFramework.Models.StaggeredProduct;
import com.dealertire.RightTurnFramework.Models.Product.Seasonality;

/**
 * The Product page, with the list of tires that fit a given vehicle.
 * To get here: Home -> Location -> Vehicle -> Verify -> Tire Coach -> Product -> click on any tire
 * @author bgreen
 *
 */
public class ProductDetailsPage extends RTPage {
	
	@FindBy(css="h3.model")
	WebElement modelName;
	
	//WebElement detailsContainer;  // now it is changed
	@FindBy(css="div.comp--info-wrapper")
	WebElement detailsContainer;
	
	//@FindBy(css="div#backLink a")//now it is changed
	@FindBy(css="div.col-md-12 > a")
	WebElement backLink;
	
	//@FindBy(css="select.product-qty-select")//now it is changed
	@FindBy(css="span.comp--qty-wrapper")
	WebElement qtySelect;
	
	@FindBy(css="div.popover-content")
	WebElement popover;
	
	//@FindBy(css="div.putOnVehicle a")// now it is changed
	@FindBy(css="div.col-sm-12>button")
	WebElement selectButton;
	
	WebElement tireCoachRecommended;
	
	/**
	 * Constructor. Calls super.
	 * @param driver
	 */
	public ProductDetailsPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isLoaded() {
		return driver.getCurrentUrl().contains("/app/product/detail");
	}
	
	@Override
	public void waitForPageLoad() {
		new WebDriverWait(driver, 10).until(ExpectedConditions.titleContains("Pick Your Tires"));
	}
	
	@Override
	public PageObject clickBack() {
		return navigate(backLink); 
		
		
	}

	/**
	 * Get the name of the model of tire being displayed.
	 * @return The name, as displayed on the screen.
	 */
	public String getModelName() {
		return modelName.getText();
	}

	/**
	 * Checks if a given Product is displayed on the screen.
	 * To use this, capture the Product while you're on the ProductPage, then pass it in once 
	 * this current page is loaded.
	 * Currently just checks names.
	 * @param p The product to verify
	 * @return True if the product matches, false if not.
	 */
	public boolean isProductDisplayed(Product p) {
		return getProductDisplayed().equals(p);
	}
	
	/**
	 * Get the product displayed by the details page
	 * @return The product
	 */
	public Product getProductDisplayed() {
		
		//Construct a Product
		Product prod;
		
		String id = detailsContainer.getAttribute("id");
		//String name = detailsContainer.findElement(By.cssSelector("h3.model")).getText(); // now it is changed
		String name = detailsContainer.findElement(By.cssSelector("p.comp--info-details-model")).getText();
		
		

		
		//brand sucks to grab from DOM. Just fair warning.
		//Example: <i class="rt-icon pirelli_small"></i>
		//WebElement brandTag = detailsContainer.findElement(By.cssSelector("i.rt-icon"));//now it is changed
		WebElement brandTag = detailsContainer.findElement(By.cssSelector("	h4.comp--info-details-brand"));
	
		String brandclass = brandTag.getAttribute("class").split("\\s")[1];
		String brand = brandclass.substring(0, brandclass.lastIndexOf('_'));
				
		
		Seasonality seasonality = getSeasonalityIcon();

		
		//if we show the OEM tag, we're OEM
		boolean isOEM = !detailsContainer.findElements(By.cssSelector("div.oem-tag")).isEmpty();
		
		//if we show the OEM tag, we're OEM
		boolean isPromo = !detailsContainer.findElements(By.cssSelector("div.promo_display")).isEmpty();
		
		//if we show the Runflat tag, we're runflat
		boolean isRunflat = !detailsContainer.findElements(By.cssSelector("i.tire-runflat")).isEmpty();
		
		if (detailsContainer.findElements(By.className("rearQtySelect")).size() > 0) {
			Price frontPrice = new Price(detailsContainer.findElement(By.cssSelector("div.frontQtySelect h4.price-large")).getText());
			Price rearPrice = new Price(detailsContainer.findElement(By.cssSelector("div.rearQtySelect h4.price-large")).getText());
			prod = new StaggeredProduct(id, name, frontPrice, rearPrice, brand, seasonality, isOEM, isPromo, isRunflat);
			
			Size frontSize = new Size(detailsContainer.findElement(By.cssSelector("div.frontQtySelect div span")).getText());
			Size rearSize = new Size(detailsContainer.findElement(By.cssSelector("div.rearQtySelect div span")).getText());
			((StaggeredProduct) prod).setFrontSize(frontSize);
			((StaggeredProduct) prod).setRearSize(rearSize);
			
		} else {
			//Price price = new Price(detailsContainer.findElement(By.cssSelector("h4.price-large")).getText()); //now it is changed
			Price price = new Price(detailsContainer.findElement(By.cssSelector("dt.comp_ui_installPriceFront")).getText());
			
			prod = new Product(id, name, price, brand, seasonality, isOEM, isPromo, isRunflat);
		}
		
		return prod;
	}

	/**
	 * Verify that we were taken to the review section of the page.
	 * @return True if the url contains the anchor for the review section
	 */
	public boolean isAtReviewSection() {
		return driver.getCurrentUrl().contains("#reviews");
	}
	
	/**
	 * Verify if the product is recommended or not.
	 * @return True if the "Tire Coach Recommended" banner is displayed
	 */
	public boolean isProductRecommended() {
		/*//The div is always there but it only sometimes shows a background image.
		WebElement recIcon = driver.findElement(By.cssSelector("div.rec-icon"));
		String backgroundURL = recIcon.getCssValue("background-image");
		return !backgroundURL.isEmpty() && !backgroundURL.equalsIgnoreCase("none");*/
		return driver.getCurrentUrl().contains("tire=recommended");
	}
	
	/**
	 * Get the currently displayed seasonality icon
	 * @return Winter, Three-season, All-season, or unknown.
	 */
	public Product.Seasonality getSeasonalityIcon() {
		Seasonality seasonality = Seasonality.UNKNOWN;
		
		//Determine seasonality by icon scraping
		boolean isWinter = !detailsContainer.findElements(By.cssSelector("div.product_details_characteristics i.tire-winter")).isEmpty();
		//boolean isAllSeason = !detailsContainer.findElements(By.cssSelector("div.product_details_characteristics i.tire-allseason")).isEmpty();
		boolean isAllSeason = !detailsContainer.findElements(By.cssSelector("span.facet-icon--season")).isEmpty();
		
		boolean isThreeSeason = !detailsContainer.findElements(By.cssSelector("div.product_details_characteristics i.tire-3season")).isEmpty();

		if (isWinter) seasonality = Seasonality.WINTER;
		if (isThreeSeason) seasonality = Seasonality.THREE_SEASON;
		if (isAllSeason) seasonality = Seasonality.ALL_SEASON;
		
		return seasonality;
	}
	
	/**
	 * Checks if the page is advertising a runflat tire
	 * @return True if it is showing, false otherwise
	 */
	public boolean isShowingRunflatIcon() {
		return !detailsContainer.findElements(By.cssSelector("i.tire-runflat")).isEmpty();
	}

	
	/**
	 * Select a quantity of tires to buy. Required to continue on to the next page.
	 * @param qty The quantity to select. Should be at least 0 and at most 6.
	 * @return The current page, for chaining purposes.
	 */
	public PageObject selectQuantity(Integer qty) {
		Select quantitySelect = new Select(qtySelect);
		quantitySelect.selectByValue(qty.toString());
		return this;
	}
	
	/**
	 * Select a random quantity. For when you don't care about which quantity to select.
	 * @return The current page, for chaining purposes;
	 */
	public PageObject selectRandomQuantity() {
		Random rand = new Random();
		Product p = getProductDisplayed();
	
		Select quantitySelect = new Select(qtySelect);
		int max = quantitySelect.getOptions().size() < 4 ? 2 : 4; 
		Integer qty = rand.nextInt(max)+1;
		LogManager.getLogger(this.getClass().getSimpleName()).info("Selecting " + qty + " of product " + p);

		quantitySelect.selectByValue(qty.toString());
		return this;
	}
	
	/**
	 * Is a popover showing?
	 * @return True if it is; false if not.
	 */
	public boolean isPopoverShowing() {
		return popover.isDisplayed();
	}
	
	/**
	 * Get the text displayed in the popover.
	 * @return The text
	 */
	public String getPopoverText() {
		return popover.getText();
	}
	
	/**
	 * Click the "Select" button
	 * @return The page that you end up on. This uses a little bit of predictive logic so please verify to make sure that page is loaded properly.
	 */
	public PageObject clickSelect() {
		//This only changes page if there's a quantity selected.
		//Navigate waits for a page change, so we check first
		Select quantitySelect = new Select(qtySelect);
		if (quantitySelect.getFirstSelectedOption().getText().contains("-")) {
			selectButton.click();
			return this;
		} else {
			return navigate(selectButton);
		}
	}



	

}
