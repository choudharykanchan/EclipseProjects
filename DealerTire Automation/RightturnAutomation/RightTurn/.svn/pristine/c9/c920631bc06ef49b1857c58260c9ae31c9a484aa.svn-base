package com.dealertire.RightTurnFramework.Pages;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dealertire.RightTurnFramework.PageObject;
import com.dealertire.RightTurnFramework.RTPage;
import com.dealertire.RightTurnFramework.Models.Dealer;
import com.dealertire.RightTurnFramework.Models.Dealer.Market;
import com.dealertire.RightTurnFramework.Models.Price;
import com.dealertire.RightTurnFramework.Pages.Modals.MapModal;

/**
 * The Install page.
 * To get here: Home -> Location -> Vehicle -> Verify -> Tire Coach -> Product -> Install
 * @author bgreen
 *
 */
public class InstallPage extends RTPage {
	
	WebElement sameDealerRadio;
	WebElement differentDealerRadio;
	WebElement mapModal;
	WebElement dealerInfoContainer;
	WebElement dealerUnChangedHeadline;
	WebElement informationBucket;
	
	@FindBy(css="#informationBucket .dealer-changed")
	private WebElement modalWarningMessage;
	
	@FindBy(css="tr.allInOneRow")
	private WebElement allInOneRow;
	
	@FindBy(css="#informationBucket tr.allInOneRow td:nth-child(3) span")
	private WebElement newPrice;
	
	@FindBy(css="#informationBucket tr.allInOneRow td.oldDealerInfo span")
	private WebElement oldPrice;
	
	/**
	 * Constructor. Calls super.
	 * @param driver
	 */
	public InstallPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isLoaded() {
		return driver.getCurrentUrl().contains("/app/installer");
	}
	
	@Override
	public void waitForPageLoad() {
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.presenceOfElementLocated(By.id("informationBucket")));
	}
	
	/**
	 * Determines if the "No, I want to use a different installer" button is checked for the question about using the pre-supplied dealer 
	 * @return True if "No" is selected, false if "yes" is selected
	 */
	public boolean isDifferentInstallerSelected() {
		return differentDealerRadio.isSelected();
	}
	
	/**
	 * Determines if the "Yes" button is checked for the question about using the pre-supplied dealer 
	 * @return True if "Yes" is selected, false if "No, I want to use a different dealer" is selected
	 */
	public boolean isDefaultInstallerSelected() {
		return sameDealerRadio.isSelected();
	}
	
	/**
	 * Obtain the difference from the increase or decrease in price
	 * @return the difference in price
	 */
	public Price getDifference() {
		Price oldP = new Price(oldPrice.getText());
		Price newP = new Price(newPrice.getText());

		Price difference = new Price (oldP.getDollars() - newP.getDollars(),
										oldP.getCents() - newP.getCents());
		
		return difference;
	}
	
	/**
	 * Obtain the updated price after choosing a different dealer
	 * @return The new price resulting from a change in dealer
	 */
	public Price getNewPrice() {
		String temp = newPrice.getText();
		Price price = new Price(temp);
		return price;
	}
	
	/**
	 * Determines whether the newly selected dealer charges a different price
	 * @return true if price changes, false otherwise
	 */
	public boolean isDifferentPrice() {
		//If the price changes, you get three cells in the final row.
		List<WebElement> cells = allInOneRow.findElements(By.tagName("td"));
		return cells.size() > 2;
	}
	
	/**
	 * Shows whether the new price increased or decreased
	 * @return 'increased' or 'decreased'
	 */
	public String increaseOrDecrease() {
		Price difference = getDifference();
		return (difference.getDollars() < 0 || (difference.getDollars() == 0 && difference.getCents() < 0)) ?
				"decreased" : "increased";
	}
	
	/**
	 * Determines if the modal with the installer map is showing or not
	 * @return True if it is, false if it is invisible or not on the DOM
	 */
	public boolean isModalShowing() {
		try {
			return mapModal.isDisplayed();
		} catch (NoSuchElementException e) {
			return false; //If the modal does not exist, it is not displayed
		}
	}
	
	/**
	 * Get a handle for the map modal so you can manipulate it.
	 * @return A MapModal object. Doesn't check if it's showing.
	 */
	public MapModal getModal() {
		return new MapModal(driver);
	}
	
	/**
	 * Choose whether to use runflat or not
	 * @param choice True for current installer, false for separate one
	 * @return The current page, for method chaining.
	 */
	public PageObject selectInstaller(boolean choice) {
		String id = (choice? "sameDealerRadio" : "differentDealerRadio");
		WebElement radioSelect = driver.findElement(By.id(id)).findElement(By.xpath("../span"));
		radioSelect.click();
	    return this;
	}
	
	/**
	 * Get the dealer currently being displayed on the page. 
	 * @return The dealer
	 */
	public Dealer getDisplayedDealer() {
		String name = dealerInfoContainer.findElement(By.className("dealer-name")).getText();
		String address1 = dealerInfoContainer.findElement(By.className("dealer-address")).getText();
		String address2 = dealerInfoContainer.findElement(By.className("dealer-city")).getText();
		
		Pattern addressPattern = Pattern.compile("(?<city>[\\w\\s]+), (?<state>[\\w]{2}) (?<zip>\\d{5})");
		Matcher addressMatcher = addressPattern.matcher(address2);
		addressMatcher.find();
		
		String state = addressMatcher.group("state");
		int zip = Integer.parseInt(addressMatcher.group("zip"));
		
		Dealer.Market market = (state.equalsIgnoreCase("OH") ? Market.CLEVELAND : Market.DALLAS);
		
		String headline = dealerUnChangedHeadline.getText();
		
		Pattern headlinePattern = Pattern.compile("Here's your nearest (?<make>[\\w]+) ");
		Matcher headlineMatcher = headlinePattern.matcher(headline);
		headlineMatcher.find();
		
		String make = headlineMatcher.group("make");
		
		return new Dealer(name, market, zip, make, address1);
	}

}
