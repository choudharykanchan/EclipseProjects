package com.dealertire.RightTurnFramework.Pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dealertire.RightTurnFramework.PageObject;
import com.dealertire.RightTurnFramework.RTPage;
import com.dealertire.RightTurnFramework.Utils;
import com.dealertire.RightTurnFramework.Models.Dealer;
import com.dealertire.RightTurnFramework.Models.Dealer.Criteria;

/**
 * The Verify page, to ensure you have the right tire size.
 * To get here: Home -> Location -> Vehicle -> Verify
 * @author bgreen
 *
 */
public class VerifyPage extends RTPage {

	//output
	/** The make display on the right side of the page */
	@FindBy(css="#vehicleSpecs > .vehicle-label")
	private WebElement displayedMake;
	
	/** The year and model display on the right side of the page */
	@FindBy(css="#vehicleSpecs > .vehicle-model")
	private WebElement displayedModel;
	
	/** The trim display on the right side of the page */
	@FindBy(css="#vehicleSpecs > .vehicle-trim")
	private WebElement displayedTrim;
	
	/** The option display on the right side of the page */
	//@FindBy(css="#vehicleSpecs > .vehicle-option")
	//BUG: Accidentally styled vehicle-trim again
	@FindBy(xpath="//div[@id='vehicleSpecs']/h3[@class='vehicle-trim'][2]")
	private WebElement displayedOption;

	/** The XPath to discover if the RunFlat option is present. It is missing if runFlat is false, so we're storing the path instead to prevent errors. **/
	private String hasRunFlatXPath = "//div[@id='verifyText']/h4[normalize-space(text())='Run-flat tires:']/following-sibling::h3";

	/** The image display on the right side of the page */
	private WebElement vehicleImage;
	
	/** The size list on the left side of the page */
	@FindAll({@FindBy(css="#verifyText  .tire-size")})
	List<WebElement> sizes;
	
	/** The size radio buttons on the left*/
	@FindAll({@FindBy(css="div#tireSizesContainer input")})
	List<WebElement> sizesRadio;
	
	/**The runflat question radio parent div*/
	private WebElement runFlatsContainer;
	
	/**The staggered question radio parent div*/
	private WebElement staggeredOrLooseContainer;
	
	/** The dealer properties ID */
	WebElement dealerValueProp;
	
	/**
	 * Constructor. Calls super.
	 * @param driver
	 */
	public VerifyPage(WebDriver driver) {
		super(driver);
	}
	
	@Override
	public void waitForPageLoad() {
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.titleContains("Verify"));
	}

	@Override
	public boolean isLoaded() {
		return driver.getCurrentUrl().endsWith("/app/verify/") || driver.getCurrentUrl().endsWith("/app/verify");
	}
	
	
	@Override
	public PageObject clickNext() {
		// Sometimes we don't change URL, so call navigate with the extra parameter instead
		return super.navigate(nextButton, false);
	}

	/**
	 * Wait for the vehicleSpecs to display
	 */
	private void waitForDisplay() {
		Utils.WaitForAjax(driver);
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT*3).until(ExpectedConditions.presenceOfElementLocated(By.id("vehicleVerifyWrapper")));
	}
	
	private void checkModal() {
		if(driver.findElements(By.id("noTiresWrapper")).size() > 0) {
			//modal is showing. Error. 
			LogManager.getLogger(this.getClass().getSimpleName()).fatal("Modal is showing, page cannot be interacted with!");
			throw new RuntimeException("Cannot perform action, modal showing");
		}
	}
	
	/**
	 * Gets the make displayed on the verify page
	 * @return The make displayed on the page, or null if no make is displayed
	 */
	public String getDisplayedMake() {
		waitForDisplay();
		if (displayedMake.isDisplayed())	return displayedMake.getText();
		else return null;
	}
	
	/**
	 * Gets the model displayed on the verify page. This includes the year: "2012 328i" rather than "328i"
	 * @return The model displayed on the page, or null if no model is displayed
	 */
	public String getDisplayedModel() {
		waitForDisplay();
		if (displayedModel.isDisplayed())	return displayedModel.getText();
		else return null;
	}
	
	/**
	 * Gets the trim displayed on the verify page
	 * @return The trim displayed on the page, or null if no trim is displayed
	 */
	public String getDisplayedTrim() {
		waitForDisplay();
		if (displayedTrim.isDisplayed())	return displayedTrim.getText();
		else return null;
	}
	
	/**
	 * Gets the option displayed on the verify page
	 * @return The option displayed on the page, or null if no option is displayed
	 */
	public String getDisplayedOption() {
		waitForDisplay();
		if (driver.findElements(By.xpath("//div[@id='vehicleSpecs']/h3[@class='vehicle-trim'][2]")).size() > 0
			 && displayedOption.isDisplayed())	return displayedOption.getText();
		return null;
	}
		
	/**
	 * Gets a list of the sizes displayed on the verify page
	 * @return The list of sizes.
	 */
	public ArrayList<String> getDisplayedSizes() {
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.visibilityOfAllElements(sizes));
		ArrayList<String> retval = new ArrayList<String>();
		for(WebElement sizeDiv : sizes) {
			retval.add(sizeDiv.getText());
		}
		return retval;
	}
	
	/**
	 * Gets a list of the amenities on the left side of the dealer info box
	 * @return The list of amenities.
	 */
	public ArrayList<String> getLeftAmenitiesList() {
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.presenceOfElementLocated(By.id("dealerValueProp")));
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.visibilityOf(dealerValueProp));
		
		WebElement containingList = dealerValueProp.findElement(By.cssSelector("div.row-fluid > div.span4 > ul"));
		List<WebElement> items = containingList.findElements(By.cssSelector("li"));
		
		ArrayList<String> retval = new ArrayList<String>();
		for(WebElement item : items) {
			retval.add(item.getText());
		}
		return retval;
	}
	
	/**
	 * Get the list of transportation ameneties. Returned as Criteria for easier equality checks.
	 * @return The list of transportation ameneties. Can be empty, or contain any or all of the following: LOANER, SHUTTLE, or VALET.
	 */
	public ArrayList<Dealer.Criteria> getTransportationAmenetiesShown() {
		ArrayList<Criteria> retval = new ArrayList<Criteria>();
		
		ArrayList<String> allAmeneties = getLeftAmenitiesList();
		for (String amenety : allAmeneties) {
			if (amenety.equalsIgnoreCase(Criteria.LOANER.toString())) {
				retval.add(Criteria.LOANER);
			} else if (amenety.equalsIgnoreCase(Criteria.SHUTTLE.toString())) {
				retval.add(Criteria.SHUTTLE);
			} else if (amenety.equalsIgnoreCase(Criteria.VALET.toString())) {
				retval.add(Criteria.VALET);
			}
		}
		
		return retval;
	}
	
	/**
	 * Gets a list of the amenities on the right side of the dealer info box
	 * @return The list of amenities.
	 */
	public ArrayList<String> getRightAmenitiesList() {
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.visibilityOf(dealerValueProp));
		
		WebElement containingList = dealerValueProp.findElement(By.cssSelector("div.row-fluid > div.span8 > ul"));
		List<WebElement> items = containingList.findElements(By.cssSelector("li"));
		
		ArrayList<String> retval = new ArrayList<String>();
		for(WebElement item : items) {
			retval.add(item.getText().trim());
		}
		return retval;
	}
	
	/**
	 * Get the date this page is promising we can have the install by
	 * @return The date, or null if there's a parsing error
	 */
	public Calendar getInstallDate() {
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.visibilityOf(dealerValueProp));
		
		//This is silly. The only element nearby that has an ID is a specific link. So we have to walk the DOM
		WebElement howWeKnowThisLink = dealerValueProp.findElement(By.id("howDoWeKnowThis"));
		WebElement dateElement = howWeKnowThisLink.findElement(By.xpath("../strong"));
		String dateString = dateElement.getText();
		
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMM d"); //Monday, May 19
	    try {
			cal.setTime(sdf.parse(dateString));
			cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR)); //default to this year
		} catch (ParseException e) {
			LogManager.getLogger(this.getClass().getSimpleName()).error("Could not parse date " + dateString + "; error was " + e.getLocalizedMessage());
			return null;
		}
	    
	    return cal;
	}

	/**
	 * Checks if an image is being shown
	 * @return true if an image is displayed, false if it is not.
	 */
	public boolean isVehicleImageVisible() {
		waitForDisplay();
		return vehicleImage.isDisplayed();
	}
	
	/**
	 * Checks if "Runflat: Yes" is showing for the currrent vehicle
	 * @return True if runflat is showing yes, false if it is showing no or not showing
	 */
	public boolean isRunFlatAvailible() {
		//If we can find the element, it's available.
		//If we cannot, the list will be empty.
		
		if (isShowingRunflatRadioButton())
			throw new IllegalStateException("Runflat question is showing.");
		
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.visibilityOfAllElements(sizes));
		List<WebElement> potentialRunflatElements = driver.findElements(By.xpath(hasRunFlatXPath));
		
		if (potentialRunflatElements.size() <= 0) return false;
		
		WebElement runflatElement = potentialRunflatElements.get(0);
		return (runflatElement.getText().trim().equalsIgnoreCase("yes"));
		
	}
	
	/**
	 * Determines if we are being asked about which size we have
	 * @return True if the radio buttons are showing, false if not.
	 */
	public boolean isShowingSizeRadioButton() {
		checkModal();
		return sizesRadio.size() > 0;
	}
	
	/**
	 * Determines if we are being asked if we have runflats
	 * @return True if the radio buttons are showing, false if not.
	 */
	public boolean isShowingRunflatRadioButton() {
		checkModal();
		return runFlatsContainer.isDisplayed();
	}
	
	/**
	 * Determines if we are being asked if we have staggered tires
	 * @return True if the radio buttons are showing, false if not.
	 */
	public boolean isShowingStaggeredRadioButton() {
		checkModal();
		return staggeredOrLooseContainer.isDisplayed();
	}
	
	/**
	 * Choose a size from the radio buttons
	 * @param size The size to pick. Must be an exact match.
	 * @return The current page, for method chaining.
	 */
	public PageObject selectSize (String size) {
		checkModal();
	    for (WebElement radio : sizesRadio) {
	        if (radio.getAttribute("value").contains(size)) {
	        	//The actual radio button is invisible, so click the parent and hope it works
	        	//Firefox doesn't like this button
	            radio.findElement(By.xpath("./../span")).click(); 
	        }
	    }
	    return this;
	}
	
	/**
	 * Determines if the size is being shown as a radio button
	 * @param size The size to ask for
	 * @return True if it is showing, false if not or there are no radio buttons.
	 */
	public boolean isSizeDisplayed(String size) {
		  for (WebElement radio : sizesRadio) {
	        if (radio.getAttribute("value").contains(size)) {
	           return true;
	        }
	    }
	    return false;
	}
	
	/**
	 * Choose whether to use runflat or not
	 * @param choice True to select yes, false for no
	 * @return The current page, for method chaining.
	 */
	public PageObject selectRunflats(boolean choice) {
		checkModal();
		String id = (choice? "rf1" : "rf2");
		WebElement radioSelect = runFlatsContainer.findElement(By.id(id)).findElement(By.xpath("../span"));
		radioSelect.click();
	    return this;
	}
	
	/**
	 * Choose whether to use staggered or loose
	 * @param choice True to select staggered, false for loose
	 * @return The current page, for method chaining.
	 */
	public PageObject selectStaggered(boolean choice) {
		checkModal();
		String id = (choice? "stagg1" : "stagg2");
		WebElement radioSelect = staggeredOrLooseContainer.findElement(By.id(id)).findElement(By.xpath("../span"));
		radioSelect.click();
	    return this;
	}

}
