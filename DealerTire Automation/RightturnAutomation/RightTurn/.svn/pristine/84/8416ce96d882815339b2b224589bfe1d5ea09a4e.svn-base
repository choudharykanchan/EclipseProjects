package com.dealertire.RightTurnFramework.Pages;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dealertire.RightTurnFramework.PageObject;
import com.dealertire.RightTurnFramework.RTPage;
import com.dealertire.RightTurnFramework.Utils;
import com.dealertire.RightTurnFramework.Models.Dealer;
import com.dealertire.RightTurnFramework.Models.Dealer.Criteria;
import com.dealertire.RightTurnFramework.Pages.Controls.DatePicker;

/**
 * The page to schedule an appointment
 * SHOULD BE SUBMITTIED IN A PROD TEST
 * @author bgreen
 */
public class SchedulePage extends RTPage {

	private WebElement scheduleDateInput;
	
	private DatePicker datePicker;
	
	private WebElement amenitiesList;
	@FindBy(id="a2")
	private WebElement loanerRadio;
	@FindBy(id="a5")
	private WebElement shuttleRadio;
	@FindBy(id="a8")
	private WebElement valetRadio;
	
	@FindBy(css="#page_controls > a.next-btn")
	private WebElement nextButton;
	
	
	/**
     * @see com.dealertire.RightTurnFramework.RTPage#RTPage(WebDriver)
     */
	public SchedulePage(WebDriver driver) {
		super(driver);
		datePicker = new DatePicker(scheduleDateInput, driver);
	}

	@Override
	public boolean isLoaded() {
		return driver.getCurrentUrl().contains("/app/schedule");
	}
	
	@Override
	public void waitForPageLoad() {
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.presenceOfElementLocated(By.id("informationBucket")));
	}
	
	/**
	 * Select a date using the datepicker
	 * @param date The date to select
	 * @return The current page, for chaining.
	 */
	public PageObject selectTimeSlot(Calendar date) {
		datePicker.selectDate(date);
		return this;
	}
	
	/**
	 * Gets a list of the amenities under date and time slots
	 * @return The list of amenities.
	 */
	public ArrayList<String> getAmenitiesList() {	
		//data-xt-type="xt--amenities"
		List<WebElement> items = driver.findElements(By.cssSelector("button[data-xt-type='xt--amenities']"));
		
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
		
		ArrayList<String> allAmeneties = getAmenitiesList();
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
	 * Determines if the radio button for a given amenity is showing on the page or not
	 * @param amenity The amenity to search for
	 * @return True if so, false if not.
	 */
	public boolean isAmenityRadioShowing(Amenity amenity) {
		String value = getAmenityValue(amenity);
		
		List<WebElement> matches = amenitiesList.findElements(By.xpath(".//input[@value='" + value + "']"));
		return !matches.isEmpty();
	}
	
	/**
	 * Click on a given amenity's radio button
	 * @param amenity The amenity to click
	 * @return The current page, for method chaining.
	 */
	public PageObject clickAmenityRadio(Amenity amenity) {
		String value = getAmenityValue(amenity);
		WebElement radio = amenitiesList.findElement(By.xpath(".//input[@value='" + value + "']/../span"));
		
		radio.click();
		return this;
	}
	
	@Override
	public boolean canGoOn() {
		Utils.WaitForAjax(driver);
		return nextButton.isDisplayed() && nextButton.isEnabled() && !(nextButton.getAttribute("class").contains("disabled"));
	}
	
	@Override
	public PageObject clickNext() {
		return navigate(nextButton); 
	}
	
	private String getAmenityValue(Amenity amenity) {
		String value;
		switch(amenity) {
		case LOANER:
			value = "Loaner Car";
			break;
		case SHUTTLE:
			value = "Shuttle Service";
			break;
		case VALET:
			value = "Valet";
			break;
		default:
			throw new IllegalArgumentException("Cannot handle that amenity");		
		}
		
		return value;
	}
	/**
	 * The amenities you can choose on this page
	 * @author bgreen
	 */
	public enum Amenity {
		/**Loaner car*/
		LOANER,
		
		/**Shuttle service*/
		SHUTTLE,
		
		/**Valet service*/
		VALET
	}

}
