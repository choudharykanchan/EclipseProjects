package com.dealertire.SMARTFramework.Pages;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
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

/**
 * The visit page.
 * @author bgreen
 *
 */
public class VisitPage extends SMARTPage implements HasDealerWidget {
	 
	/**Title**/
	private WebElement visitPageTitle;
	
	/**Date and time**/
	private WebElement visitDate;
	
	//visit Duration
	private WebElement visitDuration;
	
	//visit start time
	private WebElement visitStartTime;
	
	/**Dealer widget**/
	private DealerSelectWidget dealerWidget;
	
	/**List of dealers **/
	private WebElement visitDealers;
	
	//popup of dealer search
	@FindBy(id="s2id_autogen326")
	WebElement dealerSearch;
	
	//errorMessage
	@FindBy(id="validationErrors")
	WebElement errorMessage;

	/**Textbox for inputing dealers**/
	@FindBy(css="ul.select2-choices")
	private WebElement txtDealer;
	
	/**Suggestion list **/
	@FindBy(id="select2-drop")
	private WebElement dealerResultsDiv;
	
	/**Communication method **/
	private WebElement visitCommunicationMethod;
	
	/**Add new: menu **/
	@FindBy(id="select2-results-1")
	private WebElement select2DropDown;
	
	//s2id_activitySelect
	private WebElement s2id_activitySelect;
	
	/**ACtivity table**/
	@FindBy(id="visitActivities")
	private WebElement visitActivitiesTable;

	private WebElement tabPendingActivitiesTable_info;
	/**Regex pattern: "Showing (?&lt;showingStart>\\d+) to (?&lt;showingEnd>\\d+) of (?&lt;total>\\d+) items" **/
	private final String infoRegex = "Showing (?<showingStart>\\d+) to (?<showingEnd>\\d+) of (?<total>\\d+) items";
	
	@SuppressWarnings("unused")
	private WebElement visitOutcome;
	@SuppressWarnings("unused")
	private WebElement visitOpportunities;
	
	@FindBy(id="btnSaveVisit")
	private WebElement btnSaveVisit;
	@SuppressWarnings("unused")
	private WebElement btnSaveVisitOptions;
	private WebElement visitCombineDialog;
	private WebElement btnCombineVisits;
	private VisitPage visitpage;

	/**The format for dates on this page*/
	public static final String dateFormatPattern = "yyyy-MM-dd";
	
	/**
	 * Default constructor
	 * @param driver
	 */
	public VisitPage(WebDriver driver) {
		super(driver);
		dealerWidget = new DealerSelectWidget(driver);
	}

	public boolean navigateTo() {
		driver.navigate().to(BaseTest.testEnvironment.baseURL + "/SMART/?event=visit");
		waitForPageLoad();
		return this.isLoaded();
	}

	public boolean isLoaded() {
		return visitPageTitle.isDisplayed();
	}

	public void waitForPageLoad() {
		new WebDriverWait(driver,DEFAULT_TIMEOUT*4).until(
				ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.overlayLoader"))
							);
		
		Utils.WaitForAjax(driver);
		new WebDriverWait(driver,DEFAULT_TIMEOUT*2).until(ExpectedConditions.visibilityOf(visitPageTitle));
		Utils.WaitForAjax(driver);
	}
	
	/**
	 * Determine if this is a new visit or editing an existing one
	 * @return True if it's a new visit, false if it's an edit.
	 */
	public boolean isNewVisit() {
		if (visitPageTitle.getText().contains("New")) return true;
		if (visitPageTitle.getText().contains("Edit")) return false;
		throw new IllegalStateException("Page is neither creating a new visit or editing an existing one. Please check for gremlins.");
	}
	
	/**
	 * Get the date of the visit, as a string. 
	 * @return The date, formatted like "2015-1-21"
	 */
	public String getVisitDate() {
		return visitDate.getText();
	}
	
	/**
	 * Get the time of the visit, as a string. 
	 * @return The time, formatted like "09:00AM"
	 */
	public String getVisitStartTime() {
		return visitStartTime.getText();
	}

	/**
	 * Select a communication method
	 * @param method Valid values are configurable but usually include "In Person"
	 */
	public void selectCommunicationMethod(String method) {
		Select commMethod = new Select(visitCommunicationMethod);
		
		commMethod.selectByVisibleText(method);
	}
	/**
	 * Add an activity to the visit
	 * @param activityType The type of activity to add
	 */
	public void addActivityToVisit(String activityType) {
		if (!select2DropDown.isDisplayed())	s2id_activitySelect.click();
		
		List<WebElement> dropdownOptions = select2DropDown.findElements(By.cssSelector("li.select2-result-selectable div.select2-result-label"));
		for (WebElement option : dropdownOptions) {
			if (option.getText().toLowerCase().contains(activityType.toLowerCase())) {
				option.click();
				Utils.WaitForAjax(driver);
				return;
			}
		}
		
		throw new IllegalStateException("Could not find option " + activityType);
	}
	
	/**
	 * Add an activity to the visit with a topic
	 * @param activityType The type of activity
	 * @param topic The topic
	 */
	public void addActivityToVisitWithTopic(String activityType, String topic) {
		int numExistingActivities = visitActivitiesTable.findElements(By.cssSelector("tbody tr")).size();
		addActivityToVisit(activityType);
		
		visitActivitiesTable = driver.findElement(By.id("visitActivitiesTable")); //refresh element

		WebElement row = visitActivitiesTable.findElement(By.cssSelector("tbody tr:nth-child(" + numExistingActivities+1 +")"));
		WebElement topicButton = row.findElement(By.id("dealerActivityTopics_")); //this ID is non-unique
		topicButton.click();
		
		List<WebElement> topics = driver.findElements(By.cssSelector("div.editable-checklist label"));
		for (WebElement topicElement : topics) {
			if(topicElement.getText().trim().equalsIgnoreCase(topic)) {
				topicElement.click();
			}
		}
	}
	
	/**
	 * Add an activity to the visit with a product
	 * @param activityType The type of activity
	 * @param product The product. Try "Tires"
	 */
	public void addActivityToVisitWithProduct(String activityType, String product) {
		int numExistingActivities = visitActivitiesTable.findElements(By.cssSelector("tbody tr")).size();
		addActivityToVisit(activityType);
		
		visitActivitiesTable = driver.findElement(By.id("visitActivitiesTable")); //refresh element
		
		WebElement row = visitActivitiesTable.findElement(By.cssSelector("tbody tr:nth-child(" + numExistingActivities+1 +")"));
		WebElement productButton = row.findElement(By.id("dealerActivityProducts_")); //this ID is non-unique
		productButton.click();
		
		List<WebElement> products = driver.findElements(By.cssSelector("div.editable-checklist label"));
		for (WebElement productElement : products) {
			if(productElement.getText().trim().equalsIgnoreCase(product)) {
				productElement.click();
			}
		}
	}
	
	/**
	 * Add an activity that is pending.
	 * @param activityType The type
	 */
	public void addPendingActivity(String activityType) {
		int numExistingActivities = visitActivitiesTable.findElements(By.cssSelector("tbody tr")).size();
		addActivityToVisit(activityType);
		
		visitActivitiesTable = driver.findElement(By.id("visitActivitiesTable")); //refresh element
		
		pendActivity(numExistingActivities+1);
	}
	
	/**
	 * Put an activity into the pending state
	 * @param activityNumber The activity number. Starts at 1 for the first activity.
	 */
	public void pendActivity(int activityNumber) {
		WebElement row = visitActivitiesTable.findElement(By.cssSelector("tbody tr:nth-child(" + activityNumber +")"));
		WebElement postponeButton = row.findElement(By.cssSelector("button.postpone")); 
		postponeButton.click();
	}
	
	/**
	 * Removes an activity. Similar to CancelActivity, but for items that have not been saved.
	 * @param activityNumber The activity number. Starts at 1 for the first activity.
	 */
	public void removeActivity(int activityNumber) {
		WebElement row = visitActivitiesTable.findElement(By.cssSelector("tbody tr:nth-child(" + activityNumber +")"));
		WebElement removeButton = row.findElement(By.cssSelector("button[name='remove']")); 
		removeButton.click();
	}
	
	/**
	 * Cancels an activity
	 * @param activityNumber The activity number. Starts at 1 for the first activity.
	 */
	public void cancelActivity(int activityNumber) {
		WebElement row = visitActivitiesTable.findElement(By.cssSelector("tbody tr:nth-child(" + activityNumber +")"));
		WebElement removeButton = row.findElement(By.cssSelector("button.cancel")); 
		removeButton.click();
	}
	
	/**
	 * Handle the "Are you sure you want to delete" popup
	 * @param accept Whether to click 'ok'. False clicks 'cancel'.
	 */
	public void handleActivityPopup(boolean accept) {
		Alert alert = driver.switchTo().alert();
		if (accept ) alert.accept();
		else alert.dismiss();
	}
	
	/**
	 * Completes an activity
	 * @param activityNumber The activity number. Starts at 1 for the first activity.
	 */
	public void completeActivity(int activityNumber) {
		WebElement row = visitActivitiesTable.findElement(By.cssSelector("tbody tr:nth-child(" + activityNumber +")"));
		WebElement removeButton = row.findElement(By.cssSelector("button.complete")); 
		removeButton.click();
	}
	
	/**
	 * Get the number of suggestions under pending activities
	 * @return The number of activities displayed in the visit
	 */
	public int getNumActivitiesInVisit() {
		List<WebElement> rows = visitActivitiesTable.findElements(By.cssSelector("tbody tr"));
		return rows.size();
	}
	
	/**
	 * Get the number of suggestions under pending activities
	 * @return The number of activities displayed in the pending activities table
	 */
	public int getNumPendingActivities() {
		Utils.WaitForAjax(driver);
		String infoText = tabPendingActivitiesTable_info.getText();
		Pattern p = Pattern.compile(infoRegex);
		Matcher m = p.matcher(infoText);
		m.find();
		return Integer.parseInt(m.group("total"));
	}
	
	/**
	 * Save the visit
	 * @return True if the save was successful, false if not
	 */
	public boolean saveVisit() {
		Utils.clickUsingJavascript(btnSaveVisit, driver);
		Utils.WaitForAjax(driver);
		
		if (visitCombineDialog.isDisplayed()) {
			btnCombineVisits.click();
			Utils.WaitForAjax(driver);
			return true;
		}
		
		WebElement successMessage = driver.findElement(By.cssSelector("div.successMessagesHeader"));
		return successMessage.isDisplayed();
				
	}
	
	/**
	 * Get the error message at the top of the page
	 * @return The message text
	 */
	public String getErrorMessage() {
		WebElement warningMessage = driver.findElement(By.cssSelector("div.warningsHeader"));
		WebElement errorMessage = driver.findElement(By.cssSelector("div.errorsHeader"));
		WebElement validationErrorMessage = driver.findElement(By.id("validationErrors"));
		String message;
		
		if (warningMessage.isDisplayed()) {
			message = "WARNING: " + warningMessage.getText();
		} else if (errorMessage.isDisplayed()) {
			message = "ERROR: " + errorMessage.getText();
		} else if (validationErrorMessage.isDisplayed()) {
			message = validationErrorMessage.getText();
		} else {
			message = "Unknown error.";
		}
		
		
		return message;
	}

/* Dealer Select options - see DealerSelectWidget */
	
	/* (non-Javadoc)
	 * @see com.dealertire.SMARTFramework.Pages.HasDealerWidget#openDealerPopup()
	 */
	public void openDealerPopup() {
		if (!isDealerPopupShowing()) {
			visitDealers.click();
			waitForDealerPopup();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.dealertire.SMARTFramework.Pages.HasDealerWidget#isDealerPopupShowing()
	 */
	public boolean isDealerPopupShowing() {
		return visitDealers.getAttribute("class").contains("editable-open");
	}
	
	/* (non-Javadoc)
	 * @see com.dealertire.SMARTFramework.Pages.HasDealerWidget#waitForDealerPopup()
	 */
	public void waitForDealerPopup() {
		new WebDriverWait(driver,DEFAULT_TIMEOUT*2).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.editable-input")));
	}
	
	/* (non-Javadoc)
	 * @see com.dealertire.SMARTFramework.Pages.HasDealerWidget#dealerListHasSuggestions()
	 */
	public boolean dealerListHasSuggestions() {
		return dealerWidget.hasSuggestions();
		
	}
	
	/* (non-Javadoc)
	 * @see com.dealertire.SMARTFramework.Pages.HasDealerWidget#getSuggestedDealers()
	 */
	public ArrayList<String> getSuggestedDealers() {
		return dealerWidget.getSuggestions();
	}
	
	/* (non-Javadoc)
	 * @see com.dealertire.SMARTFramework.Pages.HasDealerWidget#selectSuggestedDealerByName(java.lang.String)
	 */
	public void selectSuggestedDealerByName(String dealerName) {
		dealerWidget.selectSuggestedDealerByName(dealerName);
	}
	
	/* (non-Javadoc)
	 * @see com.dealertire.SMARTFramework.Pages.HasDealerWidget#isDealerInDealerListPopup(java.lang.String)
	 */
	public boolean isDealerInDealerListPopup(String dealerName) {
		return dealerWidget.isDealerInDealerListPopup(dealerName);
	}
	
	/* (non-Javadoc)
	 * @see com.dealertire.SMARTFramework.Pages.HasDealerWidget#setDealerSearchString(java.lang.String)
	 */
	public void setDealerSearchString(String partialDealer) {
		
		if (!isDealerPopupShowing()) {
			openDealerPopup();
		}
		
		dealerWidget.setDealerSearchString(partialDealer);
	}
	
	/* (non-Javadoc)
	 * @see com.dealertire.SMARTFramework.Pages.HasDealerWidget#addDealerToVisit(java.lang.String)
	 */
	public void addDealer(String fullDealerCode) {
		
		if (!isDealerPopupShowing()) {
			visitDealers.click();
			waitForDealerPopup();
		}
		dealerWidget.addDealer(fullDealerCode);
	}

	/* (non-Javadoc)
	 * @see com.dealertire.SMARTFramework.Pages.HasDealerWidget#closeDealerPopup()
	 */
	public void closeDealerPopup() {
		if (isDealerPopupShowing()) {
			dealerWidget.closeDealerPopup();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.dealertire.SMARTFramework.Pages.HasDealerWidget#acceptDealerPopup()
	 */
	public void acceptDealerPopup() {
		dealerWidget.acceptDealerPopup();
	}

	/* (non-Javadoc)
	 * @see com.dealertire.SMARTFramework.Pages.HasDealerWidget#clickSelectDealer()
	 */
	public void clickSelectDealer() {
		visitDealers.click();
	}
	
	/**
	 * Get the list of dealers
	 * @return a comma-separated list of dealership names for this visit
	 */
	public String getListOfDealers() {
		return visitDealers.getText();
	}
	

}
	
	
	
	
	

