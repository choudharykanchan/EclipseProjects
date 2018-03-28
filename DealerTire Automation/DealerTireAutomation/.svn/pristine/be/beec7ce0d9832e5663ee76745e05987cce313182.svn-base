package com.dealertire.SMARTFramework.Pages.interfaces;

/**
 * A page object, in the abstract. These methods are guaranteed to exist in all pages.
 * @author bgreen
 *
 */
public interface PageObject {
	
	/**
	 * Navigate directly to a page. 
	 * @return True if the page was navigated to
	 */
	public boolean navigateTo();
	
	/**
	 * Checks if the page the driver is pointed at is the page represented by this PageObject. If not, you'll want to navigate to it before calling any other functions.
	 * This will check for the presence of something that loads early or is always present; the page may not be FULLY loaded yet. 
	 * @return True if the page is loaded, false if not.
	 */
	public boolean isLoaded();
	
	/**
	 * Waits until the page is completed loading. This can help with timing issues. 
	 */
	public void waitForPageLoad();
	
}

