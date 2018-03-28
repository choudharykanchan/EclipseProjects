package com.dealertire.SMARTFramework.Pages;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dealertire.SMARTFramework.BaseTest;
import com.dealertire.SMARTFramework.SMARTPage;
import com.dealertire.SMARTFramework.Utils;
import com.dealertire.SMARTFramework.Pages.interfaces.HasDealerWidget;
import com.dealertire.SMARTFramework.widgets.DealerSelectWidget;

/**
 * The issue edit page
 * @author bgreen
 *
 */
public class IssuePage extends SMARTPage implements HasDealerWidget {
    @FindBy(id = "issuePageTitle")
	private WebElement issuePageTitle;
    
    @FindBy(id="category")
	private WebElement category;
    
    
	private WebElement description;
	
	@FindBy(id="issueDealer")
	private WebElement issueDealer;
	
	
	private WebElement issueDuration;
	
	//Dealer section
	private DealerSelectWidget dealerWidget;
	
	//Product section
	private WebElement sku;
	
	//pCategory
	@FindBy(id="productCategory")
	private WebElement productCategory;
	
	private WebElement manufacturingVendor;
	private WebElement brand;
	
	@FindBy(id="ui-id-6")
	WebElement distributorAutoComplete;
	
    //Name of distributor
	WebElement distributorName;
	
	//City of distributor
	WebElement distributorCity;
	
	// State of distributor
	WebElement distributorState;
	
	//Zip of Distributor
	WebElement distributorZip;
	
	//Distributor
	private WebElement distributor;
	
	//Save
	private WebElement btnSaveIssue;
	
	/**
	 * Default constructor
	 * @param driver The webdriver to use
	 */
	public IssuePage(WebDriver driver) {
		super(driver);
		dealerWidget = new DealerSelectWidget(driver);
	}

	public boolean navigateTo() {
		driver.navigate().to(BaseTest.testEnvironment.baseURL + "/SMART/?event=issue");
		waitForPageLoad();
		return this.isLoaded();
	}

	public boolean isLoaded() {
		return issuePageTitle.isDisplayed();
	}

	public void waitForPageLoad() {
		new WebDriverWait(driver,DEFAULT_TIMEOUT*4).until(
				ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.overlayLoader"))
							);
		
		Utils.WaitForAjax(driver);
	}
	
	/**
	 * Select the category for this issue
	 * @param cat The category to select
	 */
	public void selectCategory(String cat) {
		Select sCategory = new Select(category);
		sCategory.selectByVisibleText(cat);
	}
	
	/**
	 * Set the details for this issue.
	 * @param issueDetails The details. Can be quite long, as this is a text area.
	 */
	public void setDetails(String issueDetails) {
		description.clear();
		description.sendKeys(issueDetails);
	}
	
	/**
	 * Set the issue's estimated duration
	 * @param hours Hours half of the duration
	 * @param minutes Minutes half of the duration
	 */
	public void setDuration(String hours, String minutes) {
		issueDuration.click();
		
		new WebDriverWait(driver,DEFAULT_TIMEOUT*2).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.editable-input")));

		WebElement timeInput = driver.findElement(By.cssSelector("input.hasTimeEntry"));
		timeInput.click();
		timeInput.sendKeys(Keys.HOME);
		timeInput.sendKeys(hours);
		timeInput.sendKeys(Keys.END);
		timeInput.sendKeys(minutes);
		
		//blur timeInput by clicking elsewhere
		description.click();
	}
	
	/**
	 * Enter a sku for the issue
	 * @param newSku The sku to enter
	 */
	public void setSku(String newSku) {
		sku.clear();
		sku.sendKeys(newSku);
	}
	
	/** 
	 * Set the category in the product section
	 * @param cat The category to set. Valid values include "Tires", "Batteries", and "Oil"
	 */
	public void setProductCategory(String pCat) {
		productCategory.sendKeys(pCat);
	}
	
	/**
	 * Set the manufacturer for the product
	 * @param manufacturer The manufacturer. 
	 */
	public void setManufacturer(String manufacturer) {
		manufacturingVendor.clear();
		manufacturingVendor.sendKeys(manufacturer);
	}
	
	/**
	 * Set the vendor for the product, also known as "brand"
	 * @param vendor The vendor
	 */
	public void setVendor(String vendor) {
		brand.clear();
		brand.sendKeys(vendor);
	}
	
	/**
	 * Set the distributor. This is an autocomplete.
	 * @param dist The distributor
	 */
	public void setDistributor(String dist) {
		distributor.clear();
		distributor.sendKeys(dist);
	}
	
	/**
	 * Click a distributor in the autocomplete list. Make sure you've typed in the box first. 
	 * @param address The address to click on
	 */
	public void selectDistributorByAddress(String address) {
		new WebDriverWait(driver,DEFAULT_TIMEOUT*2).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ui-id-6 a")));
		WebElement autoCompleteSuggestion = distributorAutoComplete.findElement(By.linkText(address));
		autoCompleteSuggestion.click();
	}
	
	/**
	 * Save the issue
	 */
	public void saveIssue() {
		btnSaveIssue.click();
	}

/* Dealer Select options - see DealerSelectWidget */
	
	/* (non-Javadoc)
	 * @see com.dealertire.SMARTFramework.Pages.HasDealerWidget#openDealerPopup()
	 */
	public void openDealerPopup() {
		if (!isDealerPopupShowing()) {
			issueDealer.click();
			waitForDealerPopup();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.dealertire.SMARTFramework.Pages.HasDealerWidget#isDealerPopupShowing()
	 */
	public boolean isDealerPopupShowing() {
		return issueDealer.getAttribute("class").contains("editable-open");
	}
	
	/* (non-Javadoc)
	 * @see com.dealertire.SMARTFramework.Pages.HasDealerWidget#waitForDealerPopup()
	 */
	public void waitForDealerPopup() {
		new WebDriverWait(driver,DEFAULT_TIMEOUT*2).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.editable-input")));
	}
	
	/* (non-Javadoc)
	 * @see com.dealertire.SMARTFramework.Pages.HasDealerWidget#dealerListHasSuggestions()
	 */
	public boolean dealerListHasSuggestions() {
		return dealerWidget.hasSuggestions();
		
	}
	
	/* (non-Javadoc)
	 * @see com.dealertire.SMARTFramework.Pages.HasDealerWidget#getSuggestedDealers()
	 */
	public ArrayList<String> getSuggestedDealers() {
		return dealerWidget.getSuggestions();
	}
	
	/* (non-Javadoc)
	 * @see com.dealertire.SMARTFramework.Pages.HasDealerWidget#selectSuggestedDealerByName(java.lang.String)
	 */
	public void selectSuggestedDealerByName(String dealerName) {
		dealerWidget.selectSuggestedDealerByName(dealerName);
	}
	
	/* (non-Javadoc)
	 * @see com.dealertire.SMARTFramework.Pages.HasDealerWidget#isDealerInDealerListPopup(java.lang.String)
	 */
	public boolean isDealerInDealerListPopup(String dealerName) {
		return dealerWidget.isDealerInDealerListPopup(dealerName);
	}
	
	/* (non-Javadoc)
	 * @see com.dealertire.SMARTFramework.Pages.HasDealerWidget#setDealerSearchString(java.lang.String)
	 */
	public void setDealerSearchString(String partialDealer) {
		
		if (!isDealerPopupShowing()) {
			openDealerPopup();
		}
		
		dealerWidget.setDealerSearchString(partialDealer);
	}
	
	/* (non-Javadoc)
	 * @see com.dealertire.SMARTFramework.Pages.HasDealerWidget#addDealerToVisit(java.lang.String)
	 */
	public void addDealer(String fullDealerCode) {
		
		if (!isDealerPopupShowing()) {
			issueDealer.click();
			waitForDealerPopup();
		}
		dealerWidget.addDealer(fullDealerCode);
	}

	/* (non-Javadoc)
	 * @see com.dealertire.SMARTFramework.Pages.HasDealerWidget#closeDealerPopup()
	 */
	public void closeDealerPopup() {
		if (isDealerPopupShowing()) {
			dealerWidget.closeDealerPopup();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.dealertire.SMARTFramework.Pages.HasDealerWidget#acceptDealerPopup()
	 */
	public void acceptDealerPopup() {
		dealerWidget.acceptDealerPopup();
	}

	/* (non-Javadoc)
	 * @see com.dealertire.SMARTFramework.Pages.HasDealerWidget#clickSelectDealer()
	 */
	public void clickSelectDealer() {
		issueDealer.click();
	}
	
	/**
	 * Get the list of dealers
	 * @return a comma-separated list of dealership names for this issue
	 */
	public String getListOfDealers() {
		return issueDealer.getText();
	}
	
    /** 
     * Method for verification of Distributor information by Location
     * 
     * @return The name as a string
     */
	public String getDistributorName(){
		String name=distributorName.getAttribute("value");
	    return name;	
	}
	
	/**
	 * Method for verification of Distributor information by Name
	 * 
	 * @return The city as a string
	 */
	public String getTextCityOfDistributor(){
		String city=distributorCity.getAttribute("value");
	    return city;
	}
	
	/**
	 * Method for verification of Distributor information by State
	 * @return The state as a string
	 */
	public String getTextStateOfDistributor(){
		String state= distributorState.getAttribute("value");
		return state;
	}
	
	/**
	 * Method for verification of Distributor information by Zipcode
	 * 
	 * @return The zip code as a string
	 */
	public String getTextZipOfDistributor(){
		String zip =distributorZip.getAttribute("value");
	    return zip;
	}	
}
