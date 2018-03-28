package com.dealertire.RightTurnFramework.Pages.Controls;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.dealertire.RightTurnFramework.Utils;

/**
 * A datepicker element
 * @author bgreen
 */
public class DatePicker {
	private WebDriver driver;

	/**
	 * Create a representation of a date picker
	 * @param root The root element to attach to
	 * @param driver The webdriver to use to interact with it
	 */
	public DatePicker(WebElement root, WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	/**
	 * Select a date using the datepicker
	 * @param date The date to select
	 */
	public void selectDate(Calendar date) {
		
		DateTime timeSlot = new DateTime(date);
		SimpleDateFormat dateFormat = new SimpleDateFormat( "YYYY-M-d", Locale.getDefault() );
		String desiredDay = dateFormat.format( date.getTime() );
		
		SimpleDateFormat timeFormat = new SimpleDateFormat( "h:mm a", Locale.getDefault() );
		String desiredTime = timeFormat.format(date.getTime()).toLowerCase();
		
		SimpleDateFormat rangeFormat = new SimpleDateFormat("MMM d");
		
		String rangeStart = driver.findElement(By.className("comp--nav-range-start")).getText();
		String rangeEnd = driver.findElement(By.className("comp--nav-range-end")).getText();
		
		DateTime startDate;
		DateTime endDate;
		try {
			Date startDateJDK = rangeFormat.parse(rangeStart);
			startDate = new DateTime(startDateJDK).withYear(date.get(Calendar.YEAR)); //Fix the year
			
			Date endDateJDK = rangeFormat.parse(rangeEnd);
			endDate = new DateTime(endDateJDK).withYear(date.get(Calendar.YEAR)); //Fix the year
		} catch (ParseException e) {
			throw new IllegalStateException("Could not parse date range from calendar object: " + e.getMessage());
		}
		
		if (startDate.withTimeAtStartOfDay().isAfter(timeSlot.withTimeAtStartOfDay())) {
			throw new IllegalArgumentException("Desired date is too early, no appointment can be selected");
		}
		
		if (endDate.withTimeAtStartOfDay().isBefore(timeSlot.withTimeAtStartOfDay())) {
			System.err.println(endDate + " is before " + timeSlot);
			WebElement nextButton = driver.findElement(By.className("comp--nav-next"));
			nextButton.click();
			selectDate(date);
		}		
		
		WebElement day = null;
		List<WebElement> days = driver.findElements(By.cssSelector("div.comp--calendar-group-inner div.comp--calendar-col"));
		for (WebElement dayCandidate : days) {
			if (dayCandidate.getAttribute("data-day").equalsIgnoreCase(desiredDay)) {
				day = dayCandidate;
				break;
			}
		}
		
		if (day == null) throw new IllegalArgumentException("No such day found.");
		WebElement time = day.findElement(By.xpath("button[text()='" + desiredTime + "']"));
		Utils.clickUsingJavascript(time, driver);
		time.click();
		time.click();
		
	}
}
