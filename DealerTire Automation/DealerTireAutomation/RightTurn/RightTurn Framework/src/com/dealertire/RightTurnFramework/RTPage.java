package com.dealertire.RightTurnFramework;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * A page on Right Turn, in the abstract. PageObjects should extend from here.
 * @author bgreen
 */
public abstract class RTPage implements PageObject {

	/**The driver, so the page can be interacted with  */
	protected WebDriver driver;
	
	//Site header
	/**The Your Vehicle site header option*/
	@FindBy(id="status-vehicle")
	protected WebElement statusVehicle;
	
	/***The Your Needs site header option*/
	@FindBy(id="status-needs")
	protected WebElement statusNeeds;
	
	//Navigation
	/** The "Next" button that appears on just about every page, with a few exceptions.	 */
	@FindBy(linkText="Next")
	protected WebElement nextButton;
	
	/** The "Back" button that appears on just about every page, with a few exceptions.	 */
	@FindBy(linkText="Back")
	protected WebElement backButton;
	
	
	/**Default timeout constant. Public so it can be set by tests on a case-by-case basis */
	public static int DEFAULT_TIMEOUT = 30; //seconds
	
	/**
	 * Constructor. Also does a PageFactory initElements as a convenience; doing it twice won't hurt anything.
	 * @param driver The driver to use on this page, so a reference can be stored.
	 */
	public RTPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
			
	@Override
	public PageObject clickBack() {
		return navigate(backButton);
	}
	
	@Override
	public PageObject clickNext() {
		return navigate(nextButton); 
	}
	
	@Override
	public boolean canGoOn() {
		Utils.WaitForAjax(driver);
		return nextButton.isDisplayed() && nextButton.isEnabled() && !(nextButton.getAttribute("class").contains("disabled"));
	}
	
	@Override
	public boolean canGoBack() {
		Utils.WaitForAjax(driver);
		return backButton.isDisplayed() && backButton.isEnabled() && !(backButton.getAttribute("class").contains("disabled"));
	}
	
	/**
	 * Handles page navigation. Call this when an action should navigate to a new page.
	 * This will not click before the button is ready, and will not return until the new page is loaded.
	 * It also will init elements on that new page for you. 
	 * @param button The webElement to click to kick off the page navigation.
	 * @return The next page, init'd and loaded and ready to use. 
	 */
	protected PageObject navigate(WebElement button) {
		return navigate(button, true);
	}
	
	/**
	 * Handles page navigation. Call this when an action should navigate to a new page.
	 * This will not click before the button is ready, and will not return until the new page is loaded.
	 * It also will init elements on that new page for you. 
	 * @param button The webElement to click to kick off the page navigation.
	 * @param waitForURLChange Pass false here if the URL won't change, so we can wait on AJAX instead.
	 * @return The next page, init'd and loaded and ready to use. 
	 */
	protected PageObject navigate(WebElement button, boolean waitForURLChange) {
		//Wait for button
		Utils.WaitForAjax(driver);
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(button));

		//Store old URL
		String oldURL = driver.getCurrentUrl();
		
		//Navigate
		Utils.ScrollElementIntoView(driver, button);
		button.click();
		
		//Wait -- sometimes our javascript navigation isn't being picked up by WebDriver properly
		if (waitForURLChange) Utils.WaitForUrlChange(driver, oldURL);
		else Utils.WaitForAjax(driver);
		
		//Get PageObject to return
		PageObject nextPage = RTPageFactory.getPageFromURL(driver.getCurrentUrl(), driver);
		PageFactory.initElements(driver, nextPage);
		
		//Make sure the page is usable
		nextPage.waitForPageLoad();
		Utils.WaitForAjax(driver);
		return nextPage; 
	}
	
	/**
	 * Gets all the links on the page. Links are defined as being elements with tagname = "a".
	 * This is still experimental, and may move in the future.
	 * @return A list of links on the page.
	 */
	public List<WebElement> getAllLinksOnPage() {
		 List<WebElement> allLinks = driver.findElements(By.tagName("a"));
		 return allLinks;
	}
	
	/**
	 * Click the "Your Vehicle" header at the top of the page.
	 * @return The page that click takes you to.
	 */
	public PageObject clickYourVehicle() {
		return navigate(statusVehicle);
	}
	
	/**
	 * Click the "Your Needs" header at the top of the page.
	 * @return The page that click takes you to.
	 */
	public PageObject clickYourNeeds() {
		return navigate(statusNeeds);
	}
	
	/**
	 * Force the dealer assigned by the system to be a specific dealer using a query string parameter on the current page. Does a page reload.
	 * @param dealerUUID The code to append to the parameter string. Can be found on the installer page by inspecting the "choose this dealer" button :) 
	 * @return The current page, reloaded from the appending.
	 */
	public PageObject changeAssignedDealer(String dealerUUID) {
		String currentURL = driver.getCurrentUrl();
		String desiredURL = currentURL + (currentURL.contains("?") ? "&" : "?") + "uid=" + dealerUUID;
		driver.navigate().to(desiredURL);
		
		//Get PageObject to return
		PageObject nextPage = RTPageFactory.getPageFromURL(driver.getCurrentUrl(), driver);
		PageFactory.initElements(driver, nextPage);
		
		//Make sure the page is usable
		nextPage.waitForPageLoad();
		Utils.WaitForAjax(driver);
		return nextPage; 
	}

}
