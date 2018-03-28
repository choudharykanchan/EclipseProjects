package com.dealertire.RightTurnFramework;

/**
 * A page object, in the abstract. These methods are guaranteed to exist in all pages.
 * @author bgreen
 *
 */
public interface PageObject {
	/**
	 * Checks if the page the driver is pointed at is the page represented by this PageObject. If not, you'll want to navigate to it before calling any other functions.
	 * @return True if the page is loaded, false if not.
	 */
	public boolean isLoaded();
	
	/**
	 * Waits until the page is completed loading. This can help with timing issues. 
	 */
	public void waitForPageLoad();
	
	/**
	 * Checks if navigation is allowed in the backward direction
	 * @return True if the back button is clickable; false otherwise
	 */
	public boolean canGoBack();
	
	/**
	 * Clicks the back button. This will wait for page load.
	 * @return A PageObject representing the new page this navigation leads to.
	 */
	public PageObject clickBack();
	
	/**
	 * Checks if navigation is allowed in the forward direction.
	 * @return True if the next button is clickable; false if not.
	 */
	public boolean canGoOn();
	
	/**
	 * Clicks the next button. This will wait for page load.
	 * @return A PageObject representing the new page this navigation leads to.
	 */
	public PageObject clickNext();
	
}
