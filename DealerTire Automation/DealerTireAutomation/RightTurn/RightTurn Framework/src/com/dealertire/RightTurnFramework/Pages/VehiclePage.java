package com.dealertire.RightTurnFramework.Pages;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dealertire.RightTurnFramework.PageObject;
import com.dealertire.RightTurnFramework.RTPage;
import com.dealertire.RightTurnFramework.Utils;
import com.dealertire.RightTurnFramework.Models.Size;
import com.dealertire.RightTurnFramework.Models.Vehicle;

/**
 * The Vehicle page, where a vehicle can be selected
 * To get here: Home -> Location -> Vehicle
 * @author bgreen
 *
 */
public class VehiclePage extends RTPage {
	
	//Tabs
	private WebElement shopByTireSizeTab;
	private WebElement shopByVehicleTab;
	
	//Vehicle tab dropdowns
	private WebElement inputYear;
	private WebElement inputMake;
	private WebElement inputModel;
	private WebElement inputTrim;
	private WebElement inputOption;
	
	//Size tab dropdowns
	private WebElement inputTS_make;
	private WebElement inputTS_width;
	private WebElement inputTS_ratio;
	private WebElement inputTS_diameter;
	private WebElement inputTS_svc;
	
	//output
	private WebElement VehicleOrTireSearchWrapper;
	
	@FindBy(css="#vehicleSpecs > .vehicle-label")
	private WebElement displayedMake;
	
	@FindBy(css="#vehicleSpecs > .vehicle-model")
	private WebElement displayedModel;
	
	@FindBy(css="#vehicleSpecs > .vehicle-trim")
	private WebElement displayedTrim;
	
	@FindBy(css="#vehicleSpecs > .vehicle-option")
	private WebElement displayedOption;
	
	@FindBy(css="#vehicleSpecs > h3.vehicle-label.mtn")
	private WebElement make;
	
	private WebElement vehicleImage;
	
	//Visible elements
	private WebElement contentTrivia;

	/**
	 * Constructor. Calls super.
	 * @param driver
	 */
	public VehiclePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	@Override
	public boolean isLoaded() {
		return driver.getCurrentUrl().contains("/app/vehicle");
	}
	
	@Override
	public void waitForPageLoad() {
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(By.id("processingModal")));
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.visibilityOf(VehicleOrTireSearchWrapper));
		Utils.WaitForAjax(driver);
		
		if (getSelectedTab() == Tab.VEHICLE_SELECT)
			new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.elementToBeClickable(inputYear));
		else if (getSelectedTab() == Tab.SIZE_SELECT)
			new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.elementToBeClickable(inputTS_make));
	}
	
	/**
	 * Get the selected tab
	 * @return The selected tab. Throws an exception if there's no selected tab.
	 */
	public Tab getSelectedTab() {
		if (Boolean.parseBoolean(shopByTireSizeTab.getAttribute("aria-selected"))) {
			return Tab.SIZE_SELECT;
		} if (Boolean.parseBoolean(shopByVehicleTab.getAttribute("aria-selected"))) {
			return Tab.VEHICLE_SELECT;
		}
		throw new IllegalArgumentException("No tab selected!");
	}
	
	/**
	 * Select a given tab
	 * @param tab The tab to select
	 * @return The current page, for chaining purposes.
	 */
	public PageObject selectTab(Tab tab) {
		switch(tab) {
			case SIZE_SELECT:
				shopByTireSizeTab.findElement(By.tagName("a")).click();
				new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.elementToBeClickable(inputTS_make));
				break;
			case VEHICLE_SELECT:
				shopByVehicleTab.findElement(By.tagName("a")).click();
				new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.elementToBeClickable(inputYear));
				break;
			default:
				break;
		}
		
		return this;
	}
	
	/**
	 * A utility method for selecting a vehicle in one step, to keep tests cleaner. 
	 * This is the same as calling SelectYear, SelectMake, SelectModel, SelectTrim, and SelectOption.
	 * This does not submit the form, allowing for verification steps afterward.
	 * @deprecated  Please use vehicle objects
	 * @param year The year to select. Ought to be numeric.
	 * @param make The make of the vehicle to select.
	 * @param model The model of the vehicle to select.
	 * @param trim The trim of the vehicle to select. Can be null; if it is null, trim is ignored.
	 * @param option The option of the vehicle to select. Can be null; if so, it is not selected.
	 * @return The current page, for method chaining.
	 */
	public PageObject selectVehicle(String year, String make, String model, String trim, String option) {
		selectYear(year);
		selectMake(make);
		selectModel(model);
		
		if (trim != null) selectTrim(trim);
		if (option != null) selectOption(option);
		
		return this;
	}
	
	/**
	 * A utility method to select a vehicle based on a Vehicle option.
	 * This does not submit the form, allowing for verification steps afterward.
	 * @param vehicle The vehicle to select
	 * @return The current page, for method chaining
	 */
	public PageObject selectVehicle(Vehicle vehicle) {
		selectYear(vehicle.year);
		selectMake(vehicle.make);
		selectModel(vehicle.model);
		
		if (vehicle.trim != null) selectTrim(vehicle.trim);
		if (vehicle.option != null) selectOption(vehicle.option);
		
		return this;
	}
	
	/**
	 * Select a size given a size string.
	 * This is the same as calling SelectMake, SelectWidth, SelectRatio, and SelectDiameter.
	 * This does not submit the form, allowing for verification steps afterward.
	 * @param make The make to select
	 * @param size The size to select
	 * @return The current page, for chaining purposes.
	 */
	public PageObject selectSize(String make, String size) {
		Size oSize = new Size(size);
		oSize.make = make;
		return selectSize(oSize);
	}
	
	/**
	 * Select a size given a Size object.
	 * This is the same as calling SelectMake, SelectWidth, SelectRatio, and SelectDiameter.
	 * This does not submit the form, allowing for verification steps afterward.
	 * @param size The size to select
	 * @return The current page, for chaining purposes.
	 */
	public PageObject selectSize(Size size) {
		if (getSelectedTab() == Tab.VEHICLE_SELECT) {
			LogManager.getLogger(this.getClass().getSimpleName()).warn("Attempting to use convenience method to select a size while the vehicles tab is displayed!");
		}
		
		selectMake(size.make);
		selectWidth(size.width);
		selectRatio(size.ratio);
		selectDiameter(size.diameter);
		return this;
	}
	
	/**
	 * Selects a year from the dropdown. 
	 * @param year The year to select. Should be numeric.
	 * @return The current page, for method chaining.
	 */
	public PageObject selectYear(String year) {
		//ensure we're ready
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.visibilityOf(inputYear));
		Select yearSelect = new Select(inputYear);
		yearSelect.selectByVisibleText(year);
		
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.visibilityOf(inputMake));
		return this;
	}
	
	/**
	 * Get the year selected in the currently visible dropdown.
	 * @return The text of the selected element
	 */
	public String getSelectedYear() {
		Select yearSelect = new Select(inputYear);
		
		return yearSelect.getFirstSelectedOption().getText();
	}
	
	/**
	 * Selects a make from the dropdown. Now handles either Size or Vehicle searches.
	 * @param make The make to select.
	 * @return The current page, for method chaining.
	 */
	public PageObject selectMake(String make) {
		
		if (inputMake.isDisplayed()) {
			Select makeSelect = new Select(inputMake);
			makeSelect.selectByVisibleText(make);
			new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.visibilityOf(inputModel));
		} else if(inputTS_make.isDisplayed()) {
			Select makeSelect = new Select(inputTS_make);
			makeSelect.selectByVisibleText(make);
			new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.elementToBeClickable(inputTS_width));
		}
		return this;
	}
	
	/**
	 * Get the make selected in the currently visible dropdown. Now handles either Size or Vehicle searches.
	 * @return The text of the selected element
	 */
	public String getSelectedMake() {
		Select makeSelect;
		
		if (inputMake.isDisplayed()) {
			makeSelect = new Select(inputMake);	
		} else {
			makeSelect = new Select(inputTS_make);
		}
		return makeSelect.getFirstSelectedOption().getText();
	}
	
	/**
	 * Selects a model from the dropdown. 
	 * @param model The model to select.
	 * @return The current page, for method chaining.
	 */
	public PageObject selectModel(String model) {
		Select modelSelect = new Select(inputModel);
		modelSelect.selectByVisibleText(model);

		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.visibilityOf(inputTrim));
		return this;
	}
	
	/**
	 * Get the model selected in the currently visible dropdown.
	 * @return The text of the selected element
	 */
	public String getSelectedModel() {
		Select modelSelect = new Select(inputModel);
		
		return modelSelect.getFirstSelectedOption().getText();
	}
	
	/**
	 * Selects a trim from the dropdown. 
	 * @param trim The trim to select.
	 * @return The current page, for method chaining.
	 */
	public PageObject selectTrim(String trim) {
		Select trimSelect = new Select(inputTrim);
		trimSelect.selectByVisibleText(trim);
		
		Utils.WaitForAjax(driver);
		return this;
	}
	
	/**
	 * Get the trim selected in the currently visible dropdown.
	 * @return The text of the selected element
	 */
	public String getSelectedTrim() {
		Select trimSelect = new Select(inputTrim);
		
		return trimSelect.getFirstSelectedOption().getText();
	}
	
	/**
	 * Selects a option from the dropdown. 
	 * @param option The option to select.
	 * @return The current page, for method chaining.
	 */
	public PageObject selectOption(String option) {
		Select optionSelect = new Select(inputOption);
		optionSelect.selectByVisibleText(option);
		
		Utils.WaitForAjax(driver);
		return this;
	}
	
	/**
	 * Get the option selected in the currently visible dropdown.
	 * @return The text of the selected element
	 */
	public String getSelectedOption() {
		Select optionSelect = new Select(inputOption);		
		return optionSelect.getFirstSelectedOption().getText();
	}
	
	/**
	 * Selects a width from the dropdown. 
	 * @param width The width to select.
	 * @return The current page, for method chaining.
	 */
	public PageObject selectWidth(String width) {
		Select widthSelect = new Select(inputTS_width);
		widthSelect.selectByVisibleText(width);
		
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.elementToBeClickable(inputTS_ratio));
		return this;
	}
	
	/**
	 * Get the width selected in the currently visible dropdown.
	 * @return The text of the selected element
	 */
	public String getSelectedWidth() {
		Select widthSelect = new Select(inputTS_width);
		return widthSelect.getFirstSelectedOption().getText();
	}
	
	/**
	 * Selects a ratio from the dropdown. 
	 * @param ratio The ratio to select.
	 * @return The current page, for method chaining.
	 */
	public PageObject selectRatio(String ratio) {
		Select ratioSelect = new Select(inputTS_ratio);
		ratioSelect.selectByVisibleText(ratio);
		
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.elementToBeClickable(inputTS_diameter));	
		return this;
	}
	
	/**
	 * Get the ratio selected in the currently visible dropdown.
	 * @return The text of the selected element
	 */
	public String getSelectedRatio() {
		Select ratioSelect = new Select(inputTS_ratio);
		return ratioSelect.getFirstSelectedOption().getText();
	}
	
	/**
	 * Selects a diameter from the dropdown. 
	 * @param diameter The diameter to select.
	 * @return The current page, for method chaining.
	 */
	public PageObject selectDiameter(String diameter) {
		Select diameterSelect = new Select(inputTS_diameter);
		diameterSelect.selectByVisibleText(diameter);
		
		Utils.WaitForAjax(driver);
		return this;
	}
	
	/**
	 * Get the diameter selected in the currently visible dropdown.
	 * @return The text of the selected element
	 */
	public String getSelectedDiameter() {
		Select diameterSelect = new Select(inputTS_diameter);
		return diameterSelect.getFirstSelectedOption().getText();
	}
	
	/**
	 * Selects a service description from the dropdown. 
	 * @param sd The description to select.
	 * @return The current page, for method chaining.
	 * @deprecated Do not use after Jan 30, as this field is going away.
	 */
	public PageObject selectServiceDescription(String sd) {
		Select sdSelect = new Select(this.inputTS_svc);
		sdSelect.selectByVisibleText(sd);
		
		Utils.WaitForAjax(driver);
		return this;
	}

	/**
	 * Get the diameter selected in the currently visible dropdown.
	 * @return The text of the selected element
	 * @deprecated Do not use after Jan 30, as this field is going away.
	 */
	public String getSelectedServiceDescription() {
		Select sdSelect = new Select(inputTS_svc);
		return sdSelect.getFirstSelectedOption().getText();
	}

	/**
	 * Checks if the trivia bucket is visible on the page
	 * @return True if the trivia bucket is being displayed; false otherwise
	 */
	public boolean isTriviaBucketVisible() {
		return contentTrivia.isDisplayed();
	}
	
	/**
	 * Checks if a vehicle image is showing on the page
	 * @return True if the vehicle image is being displayed; false otherwise
	 */
	public boolean isVehicleImageVisible() {
		return vehicleImage.isDisplayed();
	}
	
	/**
	 * Gets the make being displayed on the right side of the page.
	 * @return The make, as a string, or null if nothing is showing.
	 */
	public String getDisplayedMake() {
		return displayedMake.isDisplayed() ? displayedMake.getText() : null;
	}
	
	/**
	 * Gets the model being displayed on the right side of the page. This includes the year.
	 * @return The model, as a string, or null if nothing is showing.
	 */
	public String getDisplayedModel() {
		return displayedModel.isDisplayed()? displayedModel.getText() : null;
	}
	
	/**
	 * Gets the trim being displayed on the right side of the page.
	 * @return The trim, as a string, or null if no trim is showing.
	 */
	public String getDisplayedTrim() {
		return displayedTrim.isDisplayed() ? displayedTrim.getText() : null;
	}
	
	/**
	 * Gets the option being displayed on the right side of the page.
	 * @return The option, as a string, or null if no option is showing.
	 */
	public String getDisplayedOption() {
		return displayedOption.isDisplayed() ? displayedOption.getText() : null;
	}
	
	/**
	 * Gets make of the vehicle after it has been selected from the dropdown
	 * @return The vehicle make
	 */
	public String getMake() {
		String vehicleMake = make.getText();
		vehicleMake = vehicleMake.substring(0, vehicleMake.length()-1);
		return vehicleMake;
	}
	
	/**
	 * The tabs on the page
	 * @author bgreen
	 */
	public enum Tab {
		/**Vehicle select tab*/
		VEHICLE_SELECT,
		
		/**Size select tab*/
		SIZE_SELECT
	}
}
