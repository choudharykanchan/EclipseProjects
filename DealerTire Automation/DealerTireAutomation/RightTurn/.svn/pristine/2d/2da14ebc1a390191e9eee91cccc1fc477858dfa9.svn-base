package com.dealertire.RightTurnFramework.Pages.Modals;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dealertire.RightTurnFramework.PageObject;
import com.dealertire.RightTurnFramework.RTPage;
import com.dealertire.RightTurnFramework.RTPageFactory;
import com.dealertire.RightTurnFramework.Utils;
import com.dealertire.RightTurnFramework.Models.Dealer;
import com.dealertire.RightTurnFramework.Models.Dealer.Market;

/**
 * The modal for selecting dealers on the Install page. Named MapModal in the page, probably because it has a Google Map embedded in it.
 * @author bgreen
 *
 */
public class MapModal extends RTPage {

	private WebElement mapModal;
	private WebElement dealerList;
	
	@FindBy(css="#mapModal .close")
	private WebElement modalCloseButton;

	@FindBy(css="#zoomDistance")
	private WebElement zoomDistance;
	
	@FindBy(css="#zoomDistance > option:nth-child(1)")
	private WebElement zoomDistance25;
	
	@FindBy(css="#zoomDistance > option:nth-child(3)")
	private WebElement zoomDistance100;
	
	@FindBy(css="#paginateNumbers")
	private WebElement numberOfResults;
	
	@FindBy(css="#RT_Modal > div.modal-body > div:nth-child(2) > div:nth-child(1) > a")
	private WebElement cancelButton;
	
	
	
	/**
	 * Constructor. Calls super.
	 * @param driver
	 */
	public MapModal(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isLoaded() {
		return mapModal.isDisplayed();
	}

	@Override
	public void waitForPageLoad() {
		waitForDisplay();
	}
	
	/**
	 * Wait for the modal to show up.
	 */
	public void waitForDisplay() {
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT*3).until(ExpectedConditions.visibilityOf(mapModal));
		Utils.WaitForAllAnimationComplete(driver);
	}
	
	/**
	 * Click "Close" on the dealer select modal
	 * @return The install page, since it should not navigate.
	 */
	public PageObject clickClose() {
		modalCloseButton.click();
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT*6).until(ExpectedConditions.not(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#RT_Modal"))));
		return RTPageFactory.getPageFromURL(driver.getCurrentUrl(), driver);
	}
	
	/**
	 * Get the Dealer in a given slot. 
	 * @param slot The slot to check. Slot is usually 1-5
	 * @return The Dealer.
	 */
	public Dealer getDealerInSlot(int slot) {
		WebElement dealerSlot = dealerList.findElement(By.id("dealer" + slot));
		
		String name = dealerSlot.findElement(By.className("newDealerName")).getText();
		String address1 = dealerSlot.findElement(By.xpath("div[2]")).getText();
		String address2 = dealerSlot.findElement(By.xpath("div[3]")).getText();
		
		Pattern addressPattern = Pattern.compile("(?<city>[\\w\\s]+), (?<state>[\\w]{2}) (?<zip>\\d{5})");
		Matcher addressMatcher = addressPattern.matcher(address2);
		addressMatcher.find();
		
		String state = addressMatcher.group("state");
		int zip = Integer.parseInt(addressMatcher.group("zip"));
		
		Dealer.Market market = (state.equalsIgnoreCase("OH") ? Market.CLEVELAND : Market.DALLAS);
		
		String header = mapModal.findElement(By.className("modal-header")).getText();
		
		Pattern headerPattern = Pattern.compile("Pick a different (?<make>[\\w]+) ");
		Matcher headerMatcher = headerPattern.matcher(header);
		headerMatcher.find();
		
		String make = headerMatcher.group("make");
		
		return new Dealer(name, market, zip, make, address1);
	}
	
	/**
	 * Select a dealer by slot number. This will error if the slot is hidden.
	 * @param slot The slot number. 
	 */
	public void selectDealer(int slot) {
		WebElement dealerSlot = dealerList.findElement(By.id("dealer" + slot));
		WebElement selectLink = dealerSlot.findElement(By.cssSelector("a.selectNewDealer"));
		selectLink.click();
	}
	
	/**
	 * Select a dealer by dealer object
	 * @param dealer The dealer to select
	 */
	public void selectDealer(Dealer dealer) {
		int slot = getSlot(dealer);
		selectDealer(slot);
	}
	
	/**
	 * Get the slot a given dealer is in. Will error if that dealer is not displayed.
	 * @param dealer The dealer to search for. 
	 * @return The slot number that dealer is in.
	 */
	public int getSlot(Dealer dealer) {
		WebElement dealerNameElement = dealerList.findElement(By.cssSelector("newDealerName:contains(" + dealer.name.toUpperCase() + ")"));
		WebElement dealerSlot = dealerNameElement.findElement(By.xpath("..")); //direct parent
		String dealerSlotID = dealerSlot.getAttribute("id");
		int ID = Integer.parseInt(dealerSlotID.substring(6)); //dealer1
		return ID;
	}
	
	/**
	 * Get the number of slots showing on the page
	 * @return The number of slots showing.
	 */
	public int getNumSlots() {
		List<WebElement> liList = dealerList.findElements(By.tagName("li"));
		return liList.size();
	}

	/**
	 * Increases the distance on the google maps dropdown
	 */
	public void increaseDropDownDistance() {
		zoomDistance.click();
		zoomDistance100.click();
	}
	
	/**
	 * Decreases the distance on the google maps dropdown
	 */
	public void decreaseDropDownDistance() {
		zoomDistance.click();
		zoomDistance25.click();
	}
	
	/**
	 * Find the number of dealers on the google map
	 * @return The number of dealers found within the current scope
	 */
	public int getNumberOfResults() {
		int results = 0;
		String temp = numberOfResults.getText();
		if(!temp.substring(temp.length()-2 , temp.length()-1).equals(" ")) {
			results = Integer.parseInt(temp.substring(temp.length()-2 , temp.length()));
		} else {
			results = Integer.parseInt(temp.substring(temp.length()-1 , temp.length()));
		}
		return results;
	}
}
