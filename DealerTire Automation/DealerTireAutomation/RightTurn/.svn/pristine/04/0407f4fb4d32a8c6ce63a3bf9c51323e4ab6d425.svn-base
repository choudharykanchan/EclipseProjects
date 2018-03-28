package com.dealertire.RightTurnFramework.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dealertire.RightTurnFramework.PageObject;
import com.dealertire.RightTurnFramework.RTPage;
import com.dealertire.RightTurnFramework.RTPageFactory;
import com.dealertire.RightTurnFramework.Utils;
import com.dealertire.RightTurnFramework.Models.Price;
import com.dealertire.RightTurnFramework.Pages.Modals.DateTimeModal;

/**
 * The checkout page. DO NOT SUBMIT THIS IN A PROD TEST!
 * @author bgreen
 */
public class CheckoutPage extends RTPage {
	
	@FindBy(css="#cart_summary select.small-select")
	WebElement qtySelect;
	
	@FindBy(css="#checkoutPageContainer > div.checkoutContainer > div:nth-child(1) > div:nth-child(1) > h1")
	WebElement filledFormHeader;
	
	@FindBy(css="#styled > a.next-link")
	WebElement back;	
	@FindBy(css="#appointment_summary > div > div > p > span.installation-h2")
	WebElement confirmationDate;	
	@FindBy(css="#appointment_summary > div.row-fluid.dealer-details > div.span8 > p > span")
	WebElement checkoutDate;
	@FindBy(css="p.installation-h1.pull-right")
	WebElement totalCost;
	@FindBy(css="p.installation-h1.pull-right")
	WebElement confirmationPrice;
	
	WebElement change_appointment;
	
	/*Modals*/
	WebElement RT_Modal; //Contains the "Are you sure?" popup

	@FindBy(css="#RT_Modal button.close")
	WebElement closeRTModal; //The x button	

	@FindBy(id="goBackLink")
	WebElement modalConfirm;
	
	
	DateTimeModal dateTimeModal; //The reschedule modal
	
	/*Form controls. All text inputs except if commented otherwise*/
	WebElement form_error_banner; //Div with text that shows up in red if you have an error
	WebElement ship_to_forename;
	WebElement ship_to_surname;
	WebElement bill_to_phone;
	WebElement copy_name; //This is a fake checkbox
	WebElement bill_to_forename;
	WebElement bill_to_surname;
	WebElement bill_to_address_line1;
	WebElement bill_to_address_line2;
	WebElement bill_to_address_city;
	WebElement bill_to_address_state; //Select box
	WebElement bill_to_address_postal_code;
	WebElement card_type; //Select box
	WebElement card_number;
	WebElement card_cvn;
	WebElement card_expirationmonth; //Select box
	WebElement card_expirationyear; //Select box
	WebElement bill_to_email;
	WebElement confirm_email;
	WebElement submit_order;
	WebElement testOrderingAmount; 
	WebElement fillFormTest;
	WebElement processing;
	
	@FindBy(css="#checkoutPageContainer > div:nth-child(1) > div.span4 > div > a > span")
	WebElement printButton;


	
	/**
	 * Constructor. Calls super.
     * @see com.dealertire.RightTurnFramework.RTPage#RTPage(WebDriver)
     */
	public CheckoutPage(WebDriver driver) {
		super(driver);
		dateTimeModal = new DateTimeModal(driver);
	}

	@Override
	public boolean isLoaded() {
		return driver.getCurrentUrl().contains("/app/checkout");
	}

	@Override
	public void waitForPageLoad() {
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.titleContains("Checkout"));
	}

	/**
	 * Change the quantity of tires selected
	 * @param numTires The number of tires to select
	 * @return The current page, for method chaining
	 */
	public PageObject changeQuantity(int numTires) {
		Utils.WaitForAjax(driver);
		Select qtySelectBox = new Select(qtySelect);
		qtySelectBox.selectByIndex(numTires);
		
		return this;
	}
	
	/**
	 * Get the current selected quantity of tires
	 * @return The quantity
	 */
	public Integer getCurrentQty() {
		Select qtySelectBox = new Select(qtySelect);
		String strValue = qtySelectBox.getFirstSelectedOption().getText();
		return Integer.parseInt(strValue);
	}
	
	/**
	 * Go back to previous page after clicking the "back" button
	 */
	public void selectBack() {
		back.click();
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.visibilityOf(RT_Modal));
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT*6).until(ExpectedConditions.elementToBeClickable(modalConfirm));
	}
	
	/**
	 * Click "continue" on the modal
	 */
	public void clickModalContinue() {
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT*6).until(ExpectedConditions.elementToBeClickable(modalConfirm));
		modalConfirm.click();
	}
	
	/**
	 * Click "cancel" on the "are you sure" modal 
	 */
	public void clickModalCancel() {
		WebElement cancelbutton = RT_Modal.findElement(By.partialLinkText("Cancel"));
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT*6).until(ExpectedConditions.elementToBeClickable(cancelbutton));
		cancelbutton.click();
	}
	
	/**
	 * Clicks submit oder on the checkout page. This assumes navigation will happen!
	 * @return The current page, for chaining.
	 */
	public PageObject placeOrder() {
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT*6).until(ExpectedConditions.elementToBeClickable(submit_order));
		Utils.ScrollElementIntoView(driver, submit_order);
		String oldURL = driver.getCurrentUrl();
		
		//This is one of the buttons Firefox hates
		//Fix attempt #1: Send a keypress to the element to ensure it has focus before clicking. Verdict: flat-out did not work.
		//Fix attempt #2: Click twice. Verdict: breaks Chrome because the element stop sbeing clickable.
		//Fix attempt #2.5: Click, then if still clickable, click again. 
		//It is difficult to figure out if the element is clickable. 
		//Therefore, let's swap-in the presence of that AJAXy "loading" spinner (attempt 3.6)
		//Unfortunately, IE moves so slowly that the spinner is there and gone before the thing looks for it!
		//Fix attempt #3: If we don't navigate, click again. 
		
		submit_order.click();
		
		try {
			Utils.WaitForUrlChange(driver, oldURL);
		} catch (TimeoutException e) {
			//Retry.
			submit_order.click();
			Utils.WaitForUrlChange(driver, oldURL);
		}
		
		
		PageObject nextPage = RTPageFactory.getPageFromURL(driver.getCurrentUrl(), driver);
		return nextPage;
	}
	
	/**
	 * Click the submit button, with no expectations about what will happen. 
	 */
	public void clickSubmitButton() {
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT*6).until(ExpectedConditions.elementToBeClickable(submit_order));
		Utils.ScrollElementIntoView(driver, submit_order);
		submit_order.click();
	}
	


	/**
	 * Fills in the checkout page form with valid test information
	 */
	public void fillForm() {
		testOrderingAmount.clear();
		testOrderingAmount.sendKeys("2000"); //Assuming $2000 is a sufficient amount
		Utils.WaitForAllAnimationComplete(driver);
		fillFormTest.click();
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.visibilityOf(filledFormHeader));
		card_number.clear();
		card_number.sendKeys("378282246310005"); //Valid American Express credit card test number
	}
	
	/**
	 * Checks to see if the information has been correctly entered into the form
	 * @return True if input is valid, false otherwise
	 */
	public boolean isValidInput() {
		return !form_error_banner.isDisplayed();
	}
	
	/**
	 * Obtain the displayed total price on the checkout page
	 * @return The total price as a string
	 */
	public String getPrice() {
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.visibilityOf(totalCost));
		return totalCost.getText();
	}
	
	/**
	 * Click the change appointment link on the checkout page
	 * @return The modal that pops up, so you can use it.
	 */
	public DateTimeModal changeAppointment() {
		change_appointment.click();
		return dateTimeModal;
	}
	
	
	/**
	 * Obtain the checkout date on the checkout page
	 * @return The checkout date as a string
	 */
	public String getCheckoutDate() {
		return checkoutDate.getText();
	}
	
	/**
	 * Obtain the confirmation date on the confirmation page
	 * @return The confirmation date as a string
	 */
	public String getConfirmationDate() {
		return confirmationDate.getText();
	}
	
	/**
	 * Obtain confirmation total on the confirmation page
	 * @return The confirmation total converted to an integer for easier comparison
	 */
	public int getConfirmationTotal() {
		Price price = new Price(confirmationPrice.getText());
		int confirmationTotal = 100*price.getDollars() + price.getCents();
		return confirmationTotal;
	}
}
