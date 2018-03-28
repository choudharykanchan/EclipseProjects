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
public class ConfirmationPage extends RTPage {
	
	private WebElement cart_summary;
	
	/**
     * @see com.dealertire.RightTurnFramework.RTPage#RTPage(WebDriver)
     */
	public ConfirmationPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isLoaded() {
		return driver.getCurrentUrl().toLowerCase().contains("app/confirmation/");
	}
	
	@Override
	public void waitForPageLoad() {
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.visibilityOf(cart_summary));
	}

}
