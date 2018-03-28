package com.dealertire.SMARTFramework.Pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dealertire.SMARTFramework.BaseTest;
import com.dealertire.SMARTFramework.SMARTPage;
import com.dealertire.SMARTFramework.Utils;

/**
 * My Page: the user's homepage.
 * @author bgreen
 */
public class MyPage extends SMARTPage {


	//Dealer Simple Search
	private WebElement dealerSearchInput;
	private WebElement dealerNameSearchOnlyButton;
	private WebElement dealerList;
	
	//Dealer advanced search
	private WebElement advancedSearchLink;
	private WebElement advancedSearch; //Modal
	private WebElement dealerName; //input
	private WebElement loopInput;
	private WebElement segmentSelect;
	private WebElement targetSelect;
	private WebElement myDealersCheckbox;
	private WebElement notContactedCheckbox;
	private WebElement advancedSearchButton;
	private WebElement advancedSearchClearButton;
	//private WebElement closeAdvancedSearch;
	
	//Calendar
	private WebElement calendar;
	private WebElement calendarClickActions; //modal
  
	//Grids
	private WebElement userPageTabs;
	
	
	@FindBy(id="activities-grid")
	private WebElement activitiesGrid;
	
	@FindBy(id="issue-grid")
	private WebElement issuesGrid;
	
	@FindBy(css="#advancedSearch div div div:nth-of-type(3)>button")
	private WebElement closeSearchButton;
	
	@FindBy(css="#advancedSearch div div div:nth-of-type(1)>button")
	private WebElement closeAdvanceSearch;
	
	/**
	 * Default constructor
	 * @param driver The driver to use
	 */
	public MyPage(WebDriver driver) {
		super(driver);
	}

	public boolean navigateTo() {
		driver.navigate().to(BaseTest.testEnvironment.baseURL + "/SMART/?event=user");
		waitForPageLoad();
		return this.isLoaded();
	}

	public boolean isLoaded() {
		return userPageTabs.isDisplayed();
	}

	public void waitForPageLoad() {
		new WebDriverWait(driver,DEFAULT_TIMEOUT*2).until(ExpectedConditions.visibilityOf(userPageTabs));
		Utils.WaitForAjax(driver);
	}

	
	/**
	 * Perform a simple dealer search.
	 * @param searchterm The term to search for.
	 */
	public void performDealerSimpleSearch(String searchterm) {
		dealerSearchInput.clear();
		dealerSearchInput.sendKeys(searchterm);
		dealerNameSearchOnlyButton.click();
		Utils.WaitForAjax(driver);
	}
	
	/**
	 * Determine if any results are showing in the dealer search results pane.
	 * @return True if there are, false if there are no results.
	 */
	public boolean hasDealerSearchResults() {
		List<WebElement> noResultcandidates = dealerList.findElements(By.cssSelector("div.noDealers"));
		return (noResultcandidates.size() == 0);
	}
	
	/**
	 * Get all displayed search results
	 * @return An array-list of dealership numeric IDs (not codes!)
	 */
	public ArrayList<String> getDealerSearchResults() {
		ArrayList<String> results = new ArrayList<String>();
		List<WebElement> resultsElements = dealerList.findElements(By.cssSelector("div.row[data-dealershipid]"));
		for (WebElement item : resultsElements) {
			results.add(item.getAttribute("data-dealershipid"));
		}

		return results;
	}
	
	/**
	 * Get all selected search results
	 * @return An array-list of dealership numeric IDs (not codes!)
	 */
	public ArrayList<String> getSelectedDealerSearchResults() {
		ArrayList<String> results = new ArrayList<String>();
		List<WebElement> resultsElements = dealerList.findElements(By.cssSelector("a.list-group-item-info div.row[data-dealershipid]"));
		for (WebElement item : resultsElements) {
			results.add(item.getAttribute("data-dealershipid"));
		}

		return results;
	}
	
	/**
	 * Click a specific dealership in the results list
	 * @param dealerID The dealership ID
	 */
	public void clickDealershipInSearchResults(String dealerID) {
		WebElement resultsElement = dealerList.findElement(By.cssSelector("div.row[data-dealershipid='" + dealerID + "']"));
		resultsElement.click();
	}
	
	/**
	 * Click a specific dealership's name in the results list
	 * @param dealerID The dealership ID
	 * @return The page that loads next
	 */
	public DealerPage clickDealerNameInSearchResults(String dealerCode) {
		WebElement nameElement = dealerList.findElement(By.cssSelector("div.row[data-dealershipid='" + dealerCode + "'] address a"));
		nameElement.click();
		DealerPage page = new DealerPage(driver);
		page.waitForPageLoad();
		return page;
	}

	/**
	 * Get the segment for each of the search results
	 * @return A map of pairs in which the key is the dealerID and the value is the segment
	 */
	public HashMap<String,String> getDealerSearchResultSegments() {
		HashMap<String,String> results = new HashMap<String,String>();
		List<WebElement> resultsElements = dealerList.findElements(By.cssSelector("div.row[data-dealershipid]"));
		for (WebElement item : resultsElements) {
			String dealerID = item.getAttribute("data-dealershipid");
			WebElement label = item.findElement(By.cssSelector("div.logoContainer div.label"));
			String segment = label.getText().trim();
			
			results.put(dealerID, segment);
		}

		return results;
	}
	
	/**
	 * Determines if a given dealer is in the search results
	 * @param dealerID Dealership numeric ID, NOT code. Example: 67919
	 * @return True if the dealer is found. False if there is no results or if the dealer is not in the results.
	 */
	public boolean dealershipIsInResults(String dealerID) {
		if (!hasDealerSearchResults()) return false;
		return getDealerSearchResults().contains(dealerID);
	}

	/**
	 * Open the Advanced Search modal
	 */
	public void openAdvancedSearch() {
		advancedSearchLink.click();
		new WebDriverWait(driver,DEFAULT_TIMEOUT*2).until(ExpectedConditions.visibilityOf(advancedSearch));
	}
	
	/**
	 * Is the Advanced Search modal showing?
	 * @return True if so, false if not
	 */
	public boolean isAdvancedSearchShowing() {
		return advancedSearch.isDisplayed();
	}
	
	/**
	 * Click the search button on the Advanced Search modal, initiating the search
	 */
	public void performAdvancedSearch() {
		advancedSearchButton.click();
	}
	
	/**
	 * Click the "reset" button on the Advanced Search modal
	 */
	public void clearAdvancedSearch() {
		advancedSearchClearButton.click();
	}
	
	/**
	 * In the Advanced Search, choose a Segment to search for
	 * @param segment Valid values are "Inform", "Conquest", "Grow", and "Protect"
	 */
	public void selectSegment(String segment) {
		segment = segment.toLowerCase(); //Just in case
		Select slSegment = new Select(segmentSelect);
		slSegment.selectByValue(segment);		
	}
	
	/**
	 * In the Advanced Search, choose a Segment to search for
	 * @param regionTarget True for "Yes", false for "No"
	 */
	public void selectRegionTarget(Boolean regionTarget) {
		Select slRegionTarget = new Select(targetSelect);
		if (regionTarget) {
			slRegionTarget.selectByValue("Yes");
		} else {
			slRegionTarget.selectByValue("No");
		}
	}
	
	/**
	 * In the Advanced Search, Set the value of the "My Dealers" checkbox
	 * @param value True for checked, false for not checked
	 */
	public void setMyDealers(boolean value) {
		if (value != myDealersCheckbox.isSelected()) myDealersCheckbox.click();
	}
	
	/**
	 * In the Advanced Search, Set the value of the "Not Contacted This Month" checkbox
	 * @param value True for checked, false for not checked
	 */
	public void setNotContacted(boolean value) {
		if (value != notContactedCheckbox.isSelected()) notContactedCheckbox.click();
	}
	
	/**
	 * In the Advanced Search, set the Dealer Name search term
	 * @param name The name to search for
	 */
	public void setAdvancedSearchDealerName(String name) {
		dealerName.clear();
		dealerName.sendKeys(name);
	}
	
	/**
	 * In the Advanced Search, set the Loop Input search term
	 * @param input The input to search for
	 */
	public void setLoopInput(String input) {
		loopInput.clear();
		loopInput.sendKeys(input);
	}
	
	/**Calendars**/

	public enum DayOfWeek {
		/** Monday */
		MONDAY("Mon"),
		/** Tuesday */
		TUESDAY("Tues"),
		/** Wednesday */
		WEDNESDAY("Wed"),
		/** Thursday */
		THURSDAY("Thu"),
		/** Friday */
		FRIDAY("Fri");
		
		/**Short name for the day of the week**/
		public final String shortName;
		private DayOfWeek(String shortname) {
			this.shortName = shortname;
		}
	}
	
	/**
	 * Click on a given time slot in the calendar
	 * @param day The day of the week to click
	 * @param time The time. Must be formatted like "1am" (or like the header on the left, if it changes)
	 */
	public void clickCalendarTimeSlot(DayOfWeek day, String time) {
		Point intersection = getPointForTimeslot(day,time);
		Utils.clickAtPoint(intersection, driver);
	}
	
	private Point getPointForTimeslot(DayOfWeek day, String time) {
		/*First, we're going to find the x-coordinate of the day of the week*/
		String dowClass = "fc-" + day.shortName.toLowerCase();	
		WebElement dowHeader = calendar.findElement(By.cssSelector("th." + dowClass));
		Point topHeaderLocation = dowHeader.getLocation();
		//Adjust offset to include half the size to get the middle of the header
		topHeaderLocation.x += dowHeader.getSize().width/2;
		
		/*Then, the y-coordinate for the time slot*/
		WebElement timeHeader = calendar.findElement(By.xpath("//th[text()='" + time + "']"));
		//Utils.ScrollElementIntoView(driver, timeHeader);
		Point leftHeaderLocation = timeHeader.getLocation();
		//Adjust offset to get center
		leftHeaderLocation.y += timeHeader.getSize().height/2;
		
		
		//Now, click at the intersection between them.
		Point intersection = new Point(topHeaderLocation.x, leftHeaderLocation.y);
		return intersection;
	}
	
	/**
	 * Is the calendar modal showing?
	 * @return True for yes, false for no
	 */
	public boolean isCalendarModalShowing() {
		List<WebElement> candidates = driver.findElements(By.id("calendarClickActions"));
		if (candidates.size() > 0)	return calendarClickActions.isDisplayed();
		return false;
	}
	
	/**
	 * In the calendar modal, click "Create Visit"
	 */
	public void calendarModalClickVisit() {
		WebElement visitButton = calendarClickActions.findElement(By.linkText("Create Visit"));
		visitButton.click();
	}
	
	/**
	 * In the calendar modal, click "Create Non-Dealer Activity"
	 */
	public void calendarModalClickActivity() {
		WebElement activityButton = calendarClickActions.findElement(By.linkText("Create Non-Dealer Activity"));
		activityButton.click();
	}

	/**
	 * Drag a dealership from the search results to the timeslot. MUST have already searched for the dealer!
	 * @param dealerID The dealership ID for the already-found dealer
	 * @param day The day for the timeslot
	 * @param time The time for the timeslot
	 */
	public void dragDealerToTimeSlot(String dealerID, DayOfWeek day, String time) {
		WebElement dealershipElement = dealerList.findElement(By.cssSelector("div.row[data-dealershipid='" + dealerID + "']"));
		
		Point intersection = getPointForTimeslot(day,time);
		
		Utils.dragAndDrop(dealershipElement, intersection, driver);
		
	}
	
	
	/*Activity Grid*/
	/**
	 * Switch the grid section to the Activity grid
	 */
	public void switchToActivityGrid() {
		WebElement activityTab = userPageTabs.findElement(By.linkText("Activities"));
		activityTab.click();
	}
	
	/**
	 * Switch the grid section to the Issues grid
	 */
	public void switchToIssueGrid() {
		WebElement issueTab = userPageTabs.findElement(By.linkText("Issues"));
		issueTab.click();
	}
	
	/**
	 * Get the number of activities in the grid.
	 * @return The number
	 */
	public int getNumberActivitiesInGrid() {
		List<WebElement> rows = activitiesGrid.findElements(By.cssSelector("tr[role='row']"));
		return rows.size();
	}
	
	/**
	 * Returns the "Dealer" column for a given row in the activity table
	 * @param rownum The row number
	 * @return The dealer string, trimmed but otherwise unedited.
	 */
	public String getDealerFromActivityRow(int rownum) {
		List<WebElement> rows = activitiesGrid.findElements(By.cssSelector("tr[role='row']"));
		WebElement row = rows.get(rownum);
		
		WebElement dealer = row.findElement(By.cssSelector("td:nth-of-type(3)"));
		return dealer.getText().trim();
	}
	
	/**
	 * Get the number of issues in the grid.
	 * @return The number
	 */
	public int getNumberIssuesInGrid() {
		List<WebElement> rows = issuesGrid.findElements(By.cssSelector("tr[role='row']"));
		return rows.size();
	}
	
	/**
	 * Returns the "Dealer" column for a given row in the issues table
	 * @param rownum The row number
	 * @return The dealer string, trimmed but otherwise unedited.
	 */
	public String getDealerFromIssueRow(int rownum) {
		List<WebElement> rows = issuesGrid.findElements(By.cssSelector("tr[role='row']"));
		WebElement row = rows.get(rownum);
		
		WebElement dealer = row.findElement(By.cssSelector("td:nth-of-type(3)"));
		return dealer.getText().trim();
		
	}
	 /*In the advanced search modal click "close search"*/
	
	public void closeAdvancedSearchModal(){
		closeAdvanceSearch.click();
	}
	//close advanced search by clicking down close button
	public void closeSearchButton(){
		closeSearchButton.click(); 
	}
}

