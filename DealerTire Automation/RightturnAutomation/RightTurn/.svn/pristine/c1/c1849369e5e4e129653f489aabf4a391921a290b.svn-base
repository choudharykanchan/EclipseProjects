package com.dealertire.RightTurnFramework.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dealertire.RightTurnFramework.RTPage;

/**
 * The Winter Tire Package Question Page
 * To get here: Home -> Location -> Vehicle [choose one with a WTP] -> Verify -> Tire Coach [choose Extreme Winter]-> Winter Package Question
 * @author bgreen
 */
public class WinterPackageQuestionPage extends RTPage {

	
	/**
	 * Constructor. Calls super.
     * @see com.dealertire.RightTurnFramework.RTPage#RTPage(WebDriver)
     */
	public WinterPackageQuestionPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isLoaded() {
		return driver.getCurrentUrl().endsWith("app/tire-coach/package/") || driver.getCurrentUrl().endsWith("app/tire-coach/package");
	}

	@Override
	public void waitForPageLoad() {
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.titleContains("Tire Coach"));
	}


}
