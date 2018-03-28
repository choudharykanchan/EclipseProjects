package com.dealertire.RightTurnFramework.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dealertire.RightTurnFramework.PageObject;
import com.dealertire.RightTurnFramework.RTPage;
import com.dealertire.RightTurnFramework.Utils;

/**
 * The Location page, where zip codes are entered.
 * To get here: Home -> Location
 * @author bgreen
 *
 */
public class LocationPage extends RTPage {
	private WebElement zipInput;
	
	//Success or error messages
	private WebElement zipSuccess;
	private WebElement zipInvalid;
	private WebElement notInMarket;
	
	
	/**
	 * Constructor. Calls super.
	 * @param driver
	 */
	public LocationPage(WebDriver driver) {
        super(driver);
    }
	
	@Override
	public boolean isLoaded() {
		return driver.getCurrentUrl().contains("/app/location");
	}
	
	@Override
	public void waitForPageLoad() {
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.titleContains("Location"));
	}
	
	/**
	 * Navigate to the Location page
	 * @return the current object, for chaining.
	 */
	public PageObject navigateTo() {
	 if (!driver.getCurrentUrl().endsWith("/app/location/")) {
		driver.navigate().to(driver.getCurrentUrl().split(".com")[0] + ".com/app/location");
	 }
	 
	 return this;
	}
	
	/**
	 * Enters a zip code into the box. 
	 * @param zipCode The zip code to enter.
	 */
	public void enterZipCode(String zipCode) {
		zipInput.clear();
		zipInput.sendKeys(zipCode);
	}
	
	/**
	 * Checks if the zip code is recognized as a valid US zip code by entering it into the textbox and checking the display.
	 * @param zipCode The zip code to check
	 * @return True if the zip code is recognized as a valid US zip code; false if not.
	 * @throws Exception if no recognized display appears
	 */
	public boolean zipcodeIsValid(String zipCode) throws Exception {
		zipInput.clear();
		zipInput.sendKeys(zipCode);
		
		Utils.WaitForAjax(driver);
		
		if (zipSuccess.isDisplayed()) return true;
		else if (notInMarket.isDisplayed()) return true;
		else if (zipInvalid.isDisplayed()) return false;
		
		throw new Exception("Could not determine if zipcode was validated.");
	}
	
	/**
	 * Test if currently entered zip code is valid. Does not enter a zip code
	 * @return True if the zip code is recognized as a valid US zip code; false if not.
	 * @throws Exception if no recognized display appears
	 */
	public boolean zipcodeIsValid() throws Exception{
		Utils.WaitForAjax(driver);
		
		if (zipSuccess.isDisplayed()) return true;
		else if (notInMarket.isDisplayed()) return true;
		else if (zipInvalid.isDisplayed()) return false;
		
		throw new Exception("Could not determine if zipcode was validated.");
	}
	
	/**
	 * Checks if the zip code is in the market by entering it into the textbox and checking the display
	 * @param zipCode The zip code to enter
	 * @return True if the zip code is considered in our area of service, false if not.
	 * @throws Exception if no recognized display appears
	 */
	public boolean zipcodeInMarket(String zipCode) throws Exception {
		zipInput.clear();
		zipInput.sendKeys(zipCode);

		Utils.WaitForAjax(driver);
		
		if (zipSuccess.isDisplayed()) return true;
		else if (notInMarket.isDisplayed()) return false;
		else if (zipInvalid.isDisplayed()) return false;
		
		throw new Exception("Could not determine if zipcode was in market.");
	}

}
