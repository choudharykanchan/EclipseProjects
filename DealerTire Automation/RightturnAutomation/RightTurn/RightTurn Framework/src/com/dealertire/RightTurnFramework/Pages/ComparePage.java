package com.dealertire.RightTurnFramework.Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dealertire.RightTurnFramework.PageObject;
import com.dealertire.RightTurnFramework.RTPage;
import com.dealertire.RightTurnFramework.Utils;
import com.dealertire.RightTurnFramework.Models.Price;
import com.dealertire.RightTurnFramework.Models.Product;
import com.dealertire.RightTurnFramework.Models.Product.Seasonality;

/**
 * The product comparison page
 * @author bgreen
 */
public class ComparePage extends RTPage {
	
	/** The slot number of the leftmost slot.*/
	public final static int LEFT_SLOT = 1;
	/** The slot number of the center slot.*/
	public final static int CENTER_SLOT = 2;
	/** The slot number of the rightmost slot.*/
	public final static int RIGHT_SLOT = 3;

	@FindBy(xpath="(//div[@id='tireTileWrapper']/div[@class='span4'])[0]")
	WebElement slot1;
	
	@FindBy(xpath="(//div[@id='tireTileWrapper']/div[@class='span4'])[1]")
	WebElement slot2;
	
	@FindBy(xpath="(//div[@id='tireTileWrapper']/div[@class='span4'])[2]")
	WebElement slot3;
	
	@FindAll(@FindBy(css="div#tireTileWrapper div.span4"))
	List<WebElement> slots;
	
	@FindBy(css="#backLink a")
	WebElement backLink;
	
	@FindBy(css="div.popover-content")
	WebElement popover;
	
	/**
     * @see com.dealertire.RightTurnFramework.RTPage#RTPage(WebDriver)
     */
	public ComparePage(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isLoaded() {
		return driver.getCurrentUrl().contains("/app/product/comparison");
	}
	
	@Override
	public void waitForPageLoad() {
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.titleContains("Compare Your Tires"));
	}

	
	@Override
	public boolean canGoBack() {
		Utils.WaitForAjax(driver);
		return backLink.isDisplayed() && backLink.isEnabled() && !(backLink.getAttribute("class").contains("disabled"));
	}

	@Override
	public PageObject clickBack() {
		return navigate(backLink);
	}

	/**
	 * Get the product being displayed in a given slot. 
	 * @param slotNum The slot number. Must be 1, 2, or 3
	 * @return The product in that slot. Will be null if the slot is empty.
	 */
	public Product getProductInSlot(int slotNum) {
		checkSlotNum(slotNum);		
		return new ProductCompareView(slots.get(slotNum-1)).getProduct();
	}
	
	/**
	 * Adds a product to the slot.
	 * @param slotNum The slot to add the product to
	 * @param product The product to add
	 * @return The current page, for method chaining.
	 */
	public PageObject addProductToSlot(int slotNum, Product product) {
		if (getProductInSlot(slotNum) != null)
			throw new IllegalArgumentException("There is already a product in slot " + slotNum + "!");
		
		WebElement root = slots.get(slotNum-1);
		WebElement openSelectClickTarget = root.findElement(By.cssSelector("p.add-tire-select"));
		openSelectClickTarget.click();
		
		//Wait for the list to load
		WebElement candidateForVisibility = root.findElement(By.cssSelector("div.add-tire-text-top")); //must be child of current select box or it will never come visible
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT*3).until(ExpectedConditions.visibilityOf(candidateForVisibility));
		
		List<WebElement> productList = root.findElements(By.cssSelector("span.add-product-name"));
		boolean found = false;
		for (WebElement candidate : productList) {
			if (candidate.getText().trim().equalsIgnoreCase(product.getName())) {
				candidate.click();
				found = true;
				break;
			}
		}
		
		if (!found)
			throw new IllegalArgumentException("Product not found in list.");
		
		return this;
	}
	
	/**
	 * Adds a product to the slot. This just clicks the first one, if you don't care which to click.
	 * @param slotNum The slot to add the product to
	 * @return The current page, for method chaining.
	 */
	public PageObject addFirstProductToSlot(int slotNum) {
		if (getProductInSlot(slotNum) != null)
			throw new IllegalArgumentException("There is already a product in slot " + slotNum + "!");
		
		WebElement root = slots.get(slotNum-1);
		WebElement openSelectClickTarget = root.findElement(By.cssSelector("p.add-tire-select"));
		openSelectClickTarget.click();
		
		//Wait for the list to load
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.add-tire-text-top")));
		
		WebElement firstProduct = root.findElement(By.cssSelector("span.add-product-name"));
		firstProduct.click();
		
		return this;
	}
	
	/**
	 * Remove the product in a given slot  by clicking the remove button.
	 * @param slotNum The slot to remove the product from
	 * @return This page, for method chaining.
	 */
	public PageObject removeProductFromSlot(int slotNum) {
		checkSlotNum(slotNum);
		
		ProductCompareView slot = new ProductCompareView(slots.get(slotNum-1));
		if (!slot.isDisplayingProduct())
			throw new IllegalArgumentException("No product to remove!");
		
		slot.removeProduct();
		return this;
	}
	

	/**
	 * Change the quantity select for a given slot.
	 * @param slotNum The slot number to change the quantity on
	 * @param qty The new quantity to set
	 * @return The current page, for method chaining;
	 */
	public PageObject changeQuantity(int slotNum, int qty) {
		checkSlotNum(slotNum);
		
		ProductCompareView slot = new ProductCompareView(slots.get(slotNum-1));
		if (!slot.isDisplayingProduct())
			throw new IllegalArgumentException("No product to set quantity for!");
		
		slot.setQuantity(qty);
		return this;
	}
	
	/**
	 * Attempts to select a product. This should only navigate if the quantity was set beforehand. 
	 * @param slotNum The slot to choose
	 * @return The page you are directed to, or the current page if no quantity was selected
	 */
	public PageObject selectProduct(int slotNum) {
		checkSlotNum(slotNum);
		
		ProductCompareView slot = new ProductCompareView(slots.get(slotNum-1));
		if (!slot.isDisplayingProduct())
			throw new IllegalArgumentException("No product in slot to select!");
		
		if (slot.getQuantity() > 0) {
			return navigate(slot.getSelectButton());
		} else {
			slot.getSelectButton().click();
			Utils.WaitForAllAnimationComplete(driver);
			return this;
		}
	}
	
	/**
	 * Returns the quantity selected for a given slot.
	 * @param slotNum The slot number to inspect
	 * @return The currently selected quantity;
	 */
	public int getCurrentQuantity(int slotNum) {
		checkSlotNum(slotNum);
		
		ProductCompareView slot = new ProductCompareView(slots.get(slotNum-1));
		if (!slot.isDisplayingProduct())
			throw new IllegalArgumentException("No product to check quantity for!");
		
		return slot.getQuantity();
	}
	
	/**
	 * Returns the price shown for a given slot.
	 * @param slotNum The slot number to inspect
	 * @return The currently displayed price;
	 */
	public Price getCurrentDisplayPrice(int slotNum) {
		checkSlotNum(slotNum);
		
		ProductCompareView slot = new ProductCompareView(slots.get(slotNum-1));
		if (!slot.isDisplayingProduct())
			throw new IllegalArgumentException("No product to check quantity for!");
		
		return slot.getDisplayedPrice();
	}
	
	private void checkSlotNum(int slotNum) {
		if (slotNum > 3)
			throw new IllegalArgumentException("There are only 3 slots, so I cannot access slot " + slotNum);
		if (slotNum < 1)
			throw new IllegalArgumentException("Slot number must be non-zero and positive!");
	}
	
	/**
	 * Checks if the popover is displayed
	 * @return True if it is; false if not.
	 */
	public boolean isPopoverShowing() {
		return popover.isDisplayed();
	}
	
	/**
	 * The view for a single product being compared
	 * @author bgreen
	 *
	 */
	public class ProductCompareView {
		private WebElement selectButton;
		private WebElement nameElem;
		private Select numTiresList;
		private WebElement reviewElem;
		private WebElement removeButton;
		private WebElement displayPrice;
		
		private Product prod;
		
		/**
		 * The basic constructor. Pass in the root element for the display and it will unpack itself by scraping the DOM.
		 * @param rootElement Should be the top div with an ID like "recommendedTire_0"
		 */
		@SuppressWarnings("deprecation")
		public ProductCompareView(WebElement rootElement) {
		
			if (!rootElement.getAttribute("class").contains("additionalTire")) {
				prod = null;
			} else {
				initDOMElements(rootElement);
				
				//Construct a Product
				String id = rootElement.getAttribute("id");
				String name = nameElem.getText();
				Price price = new Price(displayPrice.getText());
				
				//brand sucks to grab from DOM. Just fair warning.
				//Example: <i class="rt-icon pirelli_small"></i>
				WebElement brandTag = rootElement.findElement(By.cssSelector("i.rt-icon"));
				String brandclass = brandTag.getAttribute("class").split("\\s")[1];
				String brand = brandclass.substring(0, brandclass.lastIndexOf('_'));
						
				
				Seasonality seasonality = Seasonality.UNKNOWN;
				//Determine seasonality by icon scraping
				boolean isWinter = !rootElement.findElements(By.cssSelector("div.product_details_characteristics i.tire-winter")).isEmpty();
				boolean isAllSeason = !rootElement.findElements(By.cssSelector("div.product_details_characteristics i.tire-allseason")).isEmpty();
				boolean isThreeSeason = !rootElement.findElements(By.cssSelector("div.product_details_characteristics i.tire-3season")).isEmpty();

				if (isWinter) seasonality = Seasonality.WINTER;
				if (isThreeSeason) seasonality = Seasonality.THREE_SEASON;
				if (isAllSeason) seasonality = Seasonality.ALL_SEASON;
				
				
				//if we show the OEM tag, we're OEM
				boolean isOEM = !rootElement.findElements(By.cssSelector("div.oem-tag")).isEmpty();
				
				//if we show the OEM tag, we're OEM
				boolean isPromo = !rootElement.findElements(By.cssSelector("div.promo_display")).isEmpty();
				
				prod = new Product(id, name, price, brand, seasonality, isOEM, isPromo);
				prod.setCompareView(this);
				
				WebElement reviewContainer = rootElement.findElement(By.className("additional_reviews"));
				reviewElem = reviewContainer.findElement(By.tagName("a"));
				
				if (reviewContainer.getText().contains("Reviews:")) {
					String reviewNumText = reviewElem.getText();
					reviewNumText = reviewNumText.replace("(", "");
					reviewNumText = reviewNumText.replace(")", "");
					prod.setNumReviews(Integer.parseInt(reviewNumText));
				}
			}
		}
		
		/**
		 * Create a view from an existing product. Useful when changing pages.
		 * @param prod The product to create a view for
		 */
		public ProductCompareView(Product prod) {
			initDOMElements(driver.findElement(By.id(prod.getListID())));
			this.prod = prod;
			prod.setCompareView(this);
		}

		private void initDOMElements(WebElement rootElement) {
			selectButton = rootElement.findElement(By.cssSelector("div.putOnVehicle a"));
			numTiresList = new Select(rootElement.findElement(By.cssSelector(".product-qty-select")));
			nameElem = rootElement.findElement(By.cssSelector("div.name a"));
			removeButton = rootElement.findElement(By.cssSelector("div.remove-flag"));
			displayPrice = rootElement.findElement(By.cssSelector("h4.price-large"));
		}
		
		/**
		 * Whether this slot is empty or not.
		 * @return True if it has a product, false if not.
		 */
		public boolean isDisplayingProduct() {
			return prod != null;
		}
		
		/**
		 * Get the product displayed by this view
		 * @return The product
		 */
		public Product getProduct() {
			return prod;
		}
		
		/**
		 * Remove the product being displayed
		 */
		public void removeProduct() {
			removeButton.click();
			prod = null;
		}
		
		/**
		 * Set the currently selected quantity
		 * @param qty The quantity to set. 0 for "-"
		 */
		public void setQuantity(int qty) {
			numTiresList.selectByValue(Integer.toString(qty));
		}
		
		/**
		 * Get the currently displayed quantity
		 * @return The quantity
		 */
		public int getQuantity() {
			String option = numTiresList.getFirstSelectedOption().getText().trim();
			if (option.equals("-")) return 0;
			return Integer.parseInt(option);
		}
		
		/**
		 * Get the price currently being displayed
		 * @return The price displayed
		 */
		public Price getDisplayedPrice() {
			return new Price(displayPrice.getText());
		}
		
		/**
		 * Get the select button, to pass to navigate();
		 * @return the select button
		 */
		public WebElement getSelectButton() {
			return selectButton;
		}
	}

}
