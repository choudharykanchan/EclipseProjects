package com.dealertire.SMARTFramework.Pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dealertire.SMARTFramework.BaseTest;
import com.dealertire.SMARTFramework.SMARTPage;
import com.dealertire.SMARTFramework.Utils;
import com.dealertire.SMARTFramework.Pages.interfaces.HasDealerWidget;
import com.dealertire.SMARTFramework.widgets.DealerSelectWidget;


public class ActivityPage extends SMARTPage implements HasDealerWidget {

	@FindBy(css="div[class='modal fade']")
	private WebElement suggestedDealer;
    
	//Title of the activity page
	@FindBy(id="smartActivityPage")
	private WebElement activityPageTitle;
	
	//Dealer section
	private DealerSelectWidget dealerWidget;
	
	//Select Communication Method 
	private WebElement communicationMethod;
	
	/**Add new: menu method **/
	@FindBy(id="s2id_ADParamsActivity")
	private WebElement searchActivity;
	
	/**Suggestion list**/
	//@FindBy(id="s2id_ADParamsActivity")
	@FindBy(id="select2-drop")
	private WebElement selectActivityMenu;
	
	/**List of dealers **/ 
	private WebElement ADDealerSelectAutoComplete;
	
	//Save
	private WebElement btnSaveActivity;
		
	//validation ErrorMessage
	@FindBy(id="validationErrors")
	private WebElement ErrorMessage;
	
	//Save success message
	@FindBy(css="div.successMessagesHeader")
	private WebElement savedSuccessfully; 

	
	/**
	 * Default constructor
	 * @param driver The webdriver to use
	 */
	
	public ActivityPage(WebDriver driver) {
		super(driver);
		dealerWidget = new DealerSelectWidget(driver);
		
	}
	
	public boolean navigateTo(){
		driver.navigate().to(BaseTest.testEnvironment.baseURL + "/SMART/?event=activity");
		waitForPageLoad();
		return this.isLoaded();
	}
	
	public boolean isLoaded() {
		return activityPageTitle.isDisplayed();
	}
	
	public void waitForPageLoad() {
		new WebDriverWait(driver,DEFAULT_TIMEOUT*4).until(
				ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.overlayLoader"))
							);
		
		Utils.WaitForAjax(driver);
		
	}

	
	public void openDealerPopup() {
		if (!isDealerPopupShowing()) {
			Utils.clickUsingJavascript(ADDealerSelectAutoComplete, driver);
			waitForDealerPopup();
		}
	}
	
	public boolean isDealerPopupShowing() {
		return ADDealerSelectAutoComplete.getAttribute("class").contains("editable-open");
	}
	
	public void waitForDealerPopup() {
		new WebDriverWait(driver,DEFAULT_TIMEOUT*2).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.editable-input")));
	}
	
	public void selectSuggestedDealerByName1(String dealerName) {
		dealerWidget.selectSuggestedDealerByName(dealerName);
	}
	
	public void selectCommunicationMethod(String method) {
		Select commMethod = new Select(communicationMethod);
		
		commMethod.selectByVisibleText(method);
		
		activityPageTitle.click(); //blur select box.
	}
	
	public boolean isActivityDropdownShowing() {
		
		return searchActivity.getAttribute("class").contains("select2-dropdown-open");
	}
	
	public void selectActivityDropdown(String activityType){
		if (!isActivityDropdownShowing())  {
			searchActivity.click();
			new WebDriverWait(driver,DEFAULT_TIMEOUT).until(ExpectedConditions.visibilityOfElementLocated(By.id("select2-drop")));
		}
		
		List<WebElement> dropdownOptions = driver.findElements(By.cssSelector("div.select2-result-label"));
		for (WebElement option : dropdownOptions) {
			if (option.getText().toLowerCase().contains(activityType.toLowerCase())) {
				option.click();
				Utils.WaitForAjax(driver);
				return;
			}
		}
		
		throw new IllegalStateException("Could not find option " + activityType);
	}

	public void saveActivity() {
		btnSaveActivity.click();
	}


	public boolean dealerListHasSuggestions() {
		return dealerWidget.hasSuggestions();
		
	}

	public ArrayList<String> getSuggestedDealers() {
		return dealerWidget.getSuggestions();
	}

	public void selectSuggestedDealerByName(String dealerName) {
		dealerWidget.selectSuggestedDealerByName(dealerName);
	}
	
	public void setDealerSearchString(String partialDealer) {
		
		if (!isDealerPopupShowing()) {
			openDealerPopup();
		}
		
		dealerWidget.setDealerSearchString(partialDealer);
	}

	public void addDealer(String fullDealerCode) {
		
		if (!isDealerPopupShowing()) {
			ADDealerSelectAutoComplete.click();
			waitForDealerPopup();
		}
		dealerWidget.addDealer(fullDealerCode);
	}


	public void closeDealerPopup() {
	if (isDealerPopupShowing()) {
		dealerWidget.closeDealerPopup();
	}
	}

	public void acceptDealerPopup() {
		dealerWidget.acceptDealerPopup();
	}


	public void clickSelectDealer() {
		ADDealerSelectAutoComplete.click();
	}

	public boolean isDealerInDealerListPopup(String dealerName) {
		return dealerWidget.isDealerInDealerListPopup(dealerName);
	}
	
	public boolean VerifyErrorMessage(){
		return ErrorMessage.isDisplayed(); 
	}
	
	public boolean SavedSuccessfully(){
		return savedSuccessfully.isDisplayed();
	}
}


	
	
	
	
	