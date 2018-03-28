package com.dealertire.RightTurnFramework.Pages.Modals;

import java.util.Calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dealertire.RightTurnFramework.PageObject;
import com.dealertire.RightTurnFramework.RTPage;
import com.dealertire.RightTurnFramework.Pages.Controls.DatePicker;

/**
 * The modal to change the date and time on the Checkout page
 * @author bgreen
 *
 */
public class DateTimeModal extends RTPage {

	private WebElement dateTimeModal;
	
	private DatePicker datePicker;
	private WebElement scheduleDateInput; 
	WebElement update_appointment;
	
	/**
	 * Constructor. Calls super.
	 * @param driver
	 */
	public DateTimeModal(WebDriver driver) {
		super(driver);
		datePicker = new DatePicker(scheduleDateInput, driver);
	}
	
	
	/**
	 * Select a date using the datepicker
	 * @param date The date and time to select
	 * @return The current page, for chaining.
	 */
	public PageObject selectTimeSlot(Calendar date) {
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.visibilityOf(update_appointment));
		datePicker.selectDate(date);
		return this;
	}
	
	/**
	 * Click update appointment on the checkout page
	 * @return The current page, for chaining.
	 */
	public PageObject updateAppointment() {
		update_appointment.click();
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='appcheckout']/div[7]")));
		return this;
	}


	@Override
	public boolean isLoaded() {
		return dateTimeModal.isDisplayed();
	}


	@Override
	public void waitForPageLoad() {
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.visibilityOf(dateTimeModal));
	}
}
