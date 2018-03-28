package com.dealertire.RightTurnFramework.Pages;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dealertire.RightTurnFramework.PageObject;
import com.dealertire.RightTurnFramework.RTPage;
import com.dealertire.RightTurnFramework.Pages.Modals.PUWYLOModal;

/**
 * The home page. To get here, go to the root url.
 * @author bgreen
 */
public class HomePage extends RTPage {
	
	//Navigation
	@FindBy(css="a.next-btn")   //Button is in content system, CSS may be unstable! >.>
	private WebElement getStarted;
	
	private PUWYLOModal puwylo;
	
	@FindBy(id="IPEinvL")
	private WebElement surveyModal;
	
	
	/**
	 * Constructor. Calls super.
	 * @param driver
	 */
	public HomePage(WebDriver driver) {
        super(driver);
        puwylo = new PUWYLOModal(driver);
    }

	@Override
	public boolean isLoaded() {
		return driver.getCurrentUrl().endsWith(".com") || driver.getCurrentUrl().endsWith(".com/");
	}
	
	@Override
	public void waitForPageLoad() {
		new WebDriverWait(driver,  RTPage.DEFAULT_TIMEOUT*3).until(ExpectedConditions.titleContains("RightTurn"));
	}

	/**
	 * Click the "Get Started" button
	 * @return the next page that loads after clicking that button.
	 */
	public PageObject getStarted() {
		return navigate(getStarted);
	}
	
	
	/**
	 * Checks if the PUWYLO modal is showing.
	 * @return true if it is displayed, false if it is not showing or not on the DOM
	 */
	public boolean isShowingPuwyloModal() {
		return puwylo.isLoaded();
	}
	
	/**
	 * Checks if the survey modal is showing.
	 * @return true if it is displayed, false if it is not showing
	 */
	public boolean isShowingSurveyModal() {
		try {
			return surveyModal.isDisplayed();
		} catch (NoSuchElementException e) {
			return false; //sometimes the script isn't even on the DOM yet
		}
	}
	
	/**
	 * Dismiss the stupid survey modal that pops up
	 */
	public void dismissSurveyModal() {
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("clWin();");
	}
	
	/**
	 * Get the modal that may or may not be showing on this page;
	 * @return The PUWYLO pageObject to manipulate
	 */
	public PUWYLOModal getPUWLOModal() {
		return puwylo;
	}

}
