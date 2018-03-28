package com.dealertire.RightTurnFramework.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dealertire.RightTurnFramework.PageObject;
import com.dealertire.RightTurnFramework.RTPage;

/**
 * The Winter Tire Question Page
 * To get here: Home -> Location -> Vehicle [choose one with winter tires] -> Verify -> Tire Coach [choose Extreme Winter]-> Winter Tire Question
 * @author bgreen
 */
public class WinterTireQuestionPage extends RTPage {

	WebElement wtTrue;
	WebElement wtFalse;
	
	/**
	 * Constructor. Calls super.
     * @see com.dealertire.RightTurnFramework.RTPage#RTPage(WebDriver)
     */
	public WinterTireQuestionPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isLoaded() {
		return driver.getCurrentUrl().endsWith("app/tire-coach/winter/") || driver.getCurrentUrl().endsWith("app/tire-coach/winter");
	}

	@Override
	public void waitForPageLoad() {
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.titleContains("Tire Coach"));
	}
	
	/**
	 * Click yes, you do want to see winter tire recommendations. Does not navigate.
	 * @return The current page, for chaining purposes.
	 */
	public PageObject clickYes() {
		wtTrue.findElement(By.xpath("../span")).click();
		return this;
	}
	
	/**
	 * Click no, you do not want to see winter tire recommendations. Does not navigate.
	 * @return The current page, for chaining purposes.
	 */
	public PageObject clickNo() {
		wtFalse.findElement(By.xpath("../span")).click();
		return this;
	}

}
