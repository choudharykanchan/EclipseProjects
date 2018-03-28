package com.dealertire.SMARTFramework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.dealertire.SMARTFramework.Pages.interfaces.PageObject;

/**
 * A page in SMART
 * @author bgreen
 *
 */
public abstract class SMARTPage implements PageObject {
	/**The driver, so the page can be interacted with  */
	protected WebDriver driver;
	
	/**Default timeout constant. Public so it can be set by tests on a case-by-case basis */
	public static int DEFAULT_TIMEOUT = 10; //seconds
	
	/**The navigation bar container */
	protected WebElement adminNavigationBarContainer;
	
	/**
	 * Constructor. Also does a PageFactory initElements as a convenience; doing it twice won't hurt anything.
	 * @param driver The driver to use on this page, so a reference can be stored.
	 */
	public SMARTPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
}
