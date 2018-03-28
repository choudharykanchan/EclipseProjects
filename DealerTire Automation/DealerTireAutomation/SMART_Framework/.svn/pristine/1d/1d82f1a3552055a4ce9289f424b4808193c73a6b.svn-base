package com.dealertire.SMARTFramework.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dealertire.SMARTFramework.BaseTest;
import com.dealertire.SMARTFramework.SMARTPage;
import com.dealertire.SMARTFramework.Utils;

/**
 * The Dealer page
 * @author bgreen
 *
 */
public class DealerPage extends SMARTPage {

	private WebElement dealerInfoPlaceholder;
	
	/**
	 * Default constructor
	 * @param driver
	 */
	public DealerPage(WebDriver driver) {
		super(driver);
	}

	public boolean navigateTo() {
		driver.navigate().to(BaseTest.testEnvironment.baseURL + "/SMART/?event=dealers");
		waitForPageLoad();
		return this.isLoaded();
	}

	public boolean isLoaded() {
		return dealerInfoPlaceholder.isDisplayed();
	}

	public void waitForPageLoad() {
		new WebDriverWait(driver,DEFAULT_TIMEOUT*4).until(
				ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.overlayLoader"))
							);
		
		//Utils.WaitForAjax(driver);

	}
	
	/**
	 * Get the ID of the dealer being displayed
	 * @return The ID
	 */
	public String getDealerIDDisplayed() {
		WebElement rowElem = dealerInfoPlaceholder.findElement(By.cssSelector("div.dealerTile div.row"));
		return rowElem.getAttribute("data-dealershipid");
	}
	
	/**
	 * Get the region target text displayed for the dealer
	 * @return The text, trimmed and ready for comparison
	 */
	public String getRegionTarget() {
		/* Warning:
		 * XPath ahead!
		 */
		
		//table -> thead -> tr -> th - > "Profile Information", but we want to save the Table element
		WebElement profileTable = driver.findElement(By.xpath("//table[thead/tr/th = 'Profile Information']"));
		
		//table -> tbody -> tr -> td "Regional Target" -> following sibling
		WebElement regionTarget = profileTable.findElement(By.xpath("tbody/tr/td[text()='Regional Target']/following-sibling::td"));
		
		return regionTarget.getText().trim();
	}

}
