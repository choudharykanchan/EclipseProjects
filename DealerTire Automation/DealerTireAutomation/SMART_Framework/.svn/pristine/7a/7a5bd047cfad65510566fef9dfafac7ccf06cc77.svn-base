package com.dealertire.SMARTFramework.widgets;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dealertire.SMARTFramework.SMARTPage;
import com.dealertire.SMARTFramework.Utils;

/**
 * The widget for selecting a dealer. Contains auto-suggest features
 * @author bgreen
 *
 */
public class DealerSelectWidget {
	
	private WebDriver driver;
	
	@FindBy(id="select2-drop")
	private WebElement dealerResultsDiv;
	
	/**Textbox for inputing dealers**/
	@FindBy(css="ul.select2-choices")
	private WebElement txtDealer;
	
	/**
	 * Basic constructor
	 * @param driver The driver to use to interact with the page
	 */
	public DealerSelectWidget(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/**
	 * Check if the search we're performing for a dealer has returned any suggestions. Will wait until searching is completed.
	 * @return True if there are suggestions. False if not. 
	 */
	public boolean hasSuggestions() {
		List<WebElement> suggestionList = dealerResultsDiv.findElements(By.tagName("li"));
		if (suggestionList.size() > 1) return true;
		if (suggestionList.size() < 1) return false;
		
		//check if the one element is a "no suggestions" element or a "please enter more characters" element
		try {
			WebElement theElement = suggestionList.get(0);
			if (theElement.getAttribute("class").contains("select2-no-results")) return false;
			
			else if (theElement.getCssValue("class").contains("select2-no-searching")) {
				new WebDriverWait(driver,SMARTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(theElement, "Searching")));
				return hasSuggestions();
			}
			
			else {
				return true; //One suggestion means one suggestion
			}
		} catch (StaleElementReferenceException e) {
			/* This suggestions element is often in flux.
			 * and it can be hard to tell if it's done yet. 
			 * So, no big deal, we just start over. 
			 */
			return hasSuggestions();
		}
		
	}
	
	/**
	 * Get the list of dealers suggested by the autocomplete. 
	 * @return A list of strings representing the display name and code of the dealer. Sample: "TOYOTA MASTR TST ACC - DO NOT ALTER/SHIP (TOYTSTACCTM1)"
	 */
	public ArrayList<String> getSuggestions() {
		ArrayList<String> suggestedDealers = new ArrayList<String>();
		
		if (!hasSuggestions()) return suggestedDealers; //Empty array for no suggestions
		
		List<WebElement> suggestionList = dealerResultsDiv.findElements(By.cssSelector("li.select2-result"));
		
		//Check for no results and searching state
		try {
			WebElement firstElement = suggestionList.get(0);
			if (firstElement.getAttribute("class").contains("select2-no-results")) return suggestedDealers;
			if (firstElement.getAttribute("class").contains("select2-no-searching")) {
				new WebDriverWait(driver,SMARTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(firstElement, "Searching")));
				return getSuggestions();
			};
			
			for(WebElement suggestion : suggestionList) {
				WebElement nameDiv = suggestion.findElement(By.cssSelector("div.dealer-name"));
				suggestedDealers.add(nameDiv.getText());
			}
		} catch (StaleElementReferenceException e) {
			return getSuggestions(); //let's try again
		}
		
		return suggestedDealers;
	}
	
	/**
	 * Click on a dealer suggestion, by name or partial name.
	 * @param dealerName The name or partial name to look for.
	 */
	public void selectSuggestedDealerByName(String dealerName) {
		Utils.WaitForAjax(driver);
		if (!hasSuggestions()) throw new IllegalStateException("No dealers suggested!");
		
		List<WebElement> suggestionList = dealerResultsDiv.findElements(By.cssSelector("li.select2-result"));
		for(WebElement suggestion : suggestionList) {
			WebElement nameDiv = suggestion.findElement(By.cssSelector("div.dealer-name"));
			if (nameDiv.getText().toLowerCase().contains(dealerName.toLowerCase())) {
				nameDiv.click();

				return;
			}
		}
		
		throw new IllegalStateException("Dealer was not suggested!");
	}
	
	/**
	 * Verify that the dealer with this name is in the list in the dealer popup
	 * @param dealerName The name
	 * @return True if it is, false if it is not.
	 */
	public boolean isDealerInDealerListPopup(String dealerName) {
		//div#s2id_autogen2 li.select2-search-choice div
		List<WebElement> dealersInList = driver.findElements(By.cssSelector("div#s2id_autogen2 li.select2-search-choice div"));
		for (WebElement dealerInList : dealersInList) {
			if (dealerInList.getText().toLowerCase().contains(dealerName.toLowerCase())) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Type into the dealer search box. For testing suggestions. Will not press enter.
	 * @param partialDealer The partial dealer code to enter.
	 */
	public void setDealerSearchString(String partialDealer) {
		txtDealer.click();
		txtDealer.sendKeys(partialDealer);
	}
	
	/**
	 * Add a dealer to the visit. Currently done by typing the full code and pressing enter, to avoid fragile clicks.
	 * @param fullDealerCode The dealer code. 
	 */
	public void addDealer(String fullDealerCode) {
		Utils.clickUsingJavascript(txtDealer, driver);
		txtDealer.sendKeys(fullDealerCode);
		txtDealer.sendKeys(Keys.ENTER);
	}

	/**
	 * Close the dealer popup. Does nothing if it is not open.
	 */
	public void closeDealerPopup() {
		WebElement input = driver.findElement(By.cssSelector("div.editable-input"));
		List<WebElement> candidates = driver.findElements(By.cssSelector("div.popover-content button.editable-cancel"));
		candidates.get(0).click();
		
		new WebDriverWait(driver,SMARTPage.DEFAULT_TIMEOUT*2).until(ExpectedConditions.stalenessOf(input));
	}
	
	/**
	 * Click the checkmark on the dealer popup. Errors if it is not open.
	 */
	public void acceptDealerPopup() {
		WebElement input = driver.findElement(By.cssSelector("div.editable-input"));
		List<WebElement> candidates = driver.findElements(By.cssSelector("div.popover-content button.editable-submit"));
		Utils.clickUsingJavascript(candidates.get(0) ,driver);
		
		new WebDriverWait(driver,SMARTPage.DEFAULT_TIMEOUT*2).until(ExpectedConditions.stalenessOf(input));
	}

}
