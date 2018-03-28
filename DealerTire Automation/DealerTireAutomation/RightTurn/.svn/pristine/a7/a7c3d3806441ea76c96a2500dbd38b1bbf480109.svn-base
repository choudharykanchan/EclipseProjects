package com.dealertire.RightTurnFramework.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dealertire.RightTurnFramework.RTPage;

/**
 * The services page.
 * @author bgreen
 *
 */
public class ServicesPage extends RTPage {
	
	private WebElement informationBucket;
	
	/**
     * @see com.dealertire.RightTurnFramework.RTPage#RTPage(WebDriver)
     */
	public ServicesPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isLoaded() {
		return driver.getCurrentUrl().contains("/app/services");
	}
	
	@Override
	public void waitForPageLoad() {
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.visibilityOf(informationBucket));
	}

}
