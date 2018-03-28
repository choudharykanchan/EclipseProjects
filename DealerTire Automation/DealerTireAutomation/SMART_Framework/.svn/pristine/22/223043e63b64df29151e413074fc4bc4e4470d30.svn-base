package com.dealertire.SMARTFramework.Pages.interfaces;

import java.util.ArrayList;

/**
 * The page implementing this interface has a Dealer popup. This standardizes the methods for interacting with that popup.
 * @author bgreen
 *
 */
public interface HasDealerWidget {

	/**
	 * Open the dealer popup. Does nothing if it is already open.
	 */
	public abstract void openDealerPopup();

	/**
	 * Checks if the popup for entering a dealer is showing. 
	 * @return True if it is, false if it is not. 
	 */
	public abstract boolean isDealerPopupShowing();

	/**
	 * Wait until the dealer popup appears. 
	 */
	public abstract void waitForDealerPopup();

	/**
	 * Check if the search we're performing for a dealer has returned any suggestions. Will wait until searching is completed.
	 * @return True if there are suggestions. False if not. 
	 */
	public abstract boolean dealerListHasSuggestions();

	/**
	 * Get the list of dealers suggested by the autocomplete. 
	 * @return A list of strings representing the display name and code of the dealer. Sample: "TOYOTA MASTR TST ACC - DO NOT ALTER/SHIP (TOYTSTACCTM1)"
	 */
	public abstract ArrayList<String> getSuggestedDealers();

	/**
	 * Click on a dealer suggestion, by name or partial name.
	 * @param dealerName The name or partial name to look for.
	 */
	public abstract void selectSuggestedDealerByName(String dealerName);

	/**
	 * Verify that the dealer with this name is in the list in the dealer popup
	 * @param dealerName The name
	 * @return True if it is, false if it is not.
	 */
	public abstract boolean isDealerInDealerListPopup(String dealerName);

	/**
	 * Type into the dealer search box. For testing suggestions. Will not press enter.
	 * @param partialDealer The partial dealer code to enter.
	 */
	public abstract void setDealerSearchString(String partialDealer);

	/**
	 * Add a dealer to the visit, issue, whatever. Currently done by typing the full code and pressing enter, to avoid fragile clicks.
	 * @param fullDealerCode The dealer code. 
	 */
	public abstract void addDealer(String fullDealerCode);

	/**
	 * Close the dealer popup. Does nothing if it is not open.
	 */
	public abstract void closeDealerPopup();

	/**
	 * Click the checkmark on the dealer popup. Errors if it is not open.
	 */
	public abstract void acceptDealerPopup();

	/**
	 * Click the "Add a Dealer" link, regardless of the state of the popup. 
	 */
	public abstract void clickSelectDealer();

}