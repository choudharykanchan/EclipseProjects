package com.dealertire.RightTurnFramework.Pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dealertire.RightTurnFramework.RTPage;
import com.dealertire.RightTurnFramework.Utils;

/**
 * The Tire Coach page, where options about your driving style can be selected.
 * To get here: Home -> Location -> Vehicle -> Verify -> Tire Coach
 * @author bgreen
 *
 */
public class TireCoachPage extends RTPage {
			
	/* Options to choose */
	private WebElement drivingDistance;
	private WebElement weather;
	private WebElement style;
	private WebElement truck;
	
	/* Navigation */
	@FindBy(linkText="Next")
	private WebElement nextButton;
	
	@FindBy(linkText="Back")
	private WebElement backButton;

	/**
	 * Constructor. Calls super.
	 * @param driver
	 */
	public TireCoachPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	@Override
	public boolean isLoaded() {
		return driver.getCurrentUrl().contains("/app/tire-coach");
	}

	@Override
	public void waitForPageLoad() {
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.titleContains("Tire Coach"));
		Utils.WaitForAjax(driver); //Defaults won't be right unless you wait for the AJAX to finish
	}
	
	/**
	 * Select the average miles driven. This is a single select, so the old option will be unselected.
	 * @param md The parameter to choose.
	 * @return The current page, for chaining purposes.
	 */
	public TireCoachPage selectMilesDriven(MilesDriven md) {
		
		WebElement itemToSelect = drivingDistance.findElement(By.cssSelector("li[data-value='" + md.dataValue + "']"));
		itemToSelect.click();
		return this;
	}
	
	/**
	 * Get the current selection for Miles Driven. This will only be one, as it is a single-select.
	 * @return The selection.
	 */
	public MilesDriven getSelectedMilesDriven() {
		WebElement selectedItem = drivingDistance.findElement(By.cssSelector("li.selected"));
		int dv = Integer.parseInt(selectedItem.getAttribute("data-value"));
		return MilesDriven.getByDataValue(dv);
	}
	
	/**
	 * Deselect a specific Weather Conditions option. This will not do anything if there is only one Weather Condition selected, because it must have at least one selection.
	 * @param w The weather condition to unselect
	 * @return The current page, for chaining purposes.;
	 */
	public TireCoachPage deSelectWeather(Weather w) {
		WebElement itemToSelect = weather.findElement(By.cssSelector("li[data-value='" + w.dataValue + "']"));
		if (itemToSelect.getAttribute("class").contains("selected")) {
			itemToSelect.click();
		}
		return this;
	}
	
	/**
	 * Select a weather condition. As this is a multi-select, this may result in multiple items selected.
	 * @param w The weather condition to select.
	 * @return The current page, for chaining purposes.
	 */
	public TireCoachPage selectWeather(Weather w) {		
		WebElement itemToSelect = weather.findElement(By.cssSelector("li[data-value='" + w.dataValue + "']"));
		if (!itemToSelect.getAttribute("class").contains("selected")) {
			itemToSelect.click();
		}
		return this;
	}
	
	/**
	 * Select only the given weather option. This will attempt to deselect everything else.
	 * @param w The weather option you want
	 * @return The current page, for chaining purposes.
	 */
	public TireCoachPage selectOnlyWeather(Weather w) {
		WebElement itemToSelect = weather.findElement(By.cssSelector("li[data-value='" + w.dataValue + "']"));
		if (!itemToSelect.getAttribute("class").contains("selected")) {
			itemToSelect.click();
		}
		
		List<WebElement> selectedItems = weather.findElements(By.cssSelector("li.selected"));
		for (WebElement item : selectedItems) {
			int dv = Integer.parseInt(item.getAttribute("data-value"));
			if (dv != w.dataValue) {
				item.click();
			}
		}
		return this;
	}
	
	/**
	 * Gets all selected weather conditions. This will be at least one, and may be all of the weather options availible.
	 * @return The list of weather conditions that are selected.
	 */
	public ArrayList<Weather> getSelectedWeather() {
		ArrayList<Weather> selectedOptions = new ArrayList<Weather>();
		List<WebElement> selectedItems = weather.findElements(By.cssSelector("li.selected"));
		for (WebElement item : selectedItems) {
			int dv = Integer.parseInt(item.getAttribute("data-value"));
			selectedOptions.add(Weather.getByDataValue(dv));
		}
		
		return selectedOptions;
	}
	
	/**
	 * Select a driving style. This is a single select, so it will override any previous driving style.
	 * @param ds The driving style to select
	 * @return The current page, for chaining purposes.
	 */
	public TireCoachPage selectDrivingStyle(DrivingStyle ds) {
		
		WebElement itemToSelect = style.findElement(By.cssSelector("li[data-value='" + ds.dataValue + "']"));
		itemToSelect.click();
		return this;
	}
	
	/**
	 * Get the selected driving style. This will always be one.
	 * @return The currently selected driving style.
	 */
	public DrivingStyle getSelectedDrivingStyle() {
		WebElement selectedItem = style.findElement(By.cssSelector("li.selected"));
		int dv = Integer.parseInt(selectedItem.getAttribute("data-value"));
		return DrivingStyle.getByDataValue(dv);
	}
	
	/**
	 * Select a truck option. This is a multi-select, so this will add to the list of selected options rather than replace.
	 * @param opt The option to select
	 * @return The current page, for chaining purposes.
	 */
	public TireCoachPage selectTruckOption(TruckOptions opt) {
		
		WebElement itemToSelect = truck.findElement(By.cssSelector("li[data-value='" + opt.dataValue + "']"));
		itemToSelect.click();
		return this;
	}
	
	/**
	 * Get the list of selected truck options. This will be at least 0 and at most all of the available truck options.
	 * @return The list of selected truck options.
	 */
	public ArrayList<TruckOptions> getSelectedTruckOptions() {
		ArrayList<TruckOptions> selectedOptions = new ArrayList<TruckOptions>();
		List<WebElement> selectedItems = truck.findElements(By.cssSelector("li.selected"));
		for (WebElement item : selectedItems) {
			int dv = Integer.parseInt(item.getAttribute("data-value"));
			selectedOptions.add(TruckOptions.getByDataValue(dv));
		}
		
		return selectedOptions;
	}

	/**
	 * Checks if the truck options are showing on the page.
	 * @return True if they are, false if they are not.
	 */
	public boolean isTruckOptionsShowing() {
		WebElement truckElement = driver.findElement(By.cssSelector("#truck"));
		return truckElement.isDisplayed();
	}
	
	/* Enums */
	
	/** The options for the miles driven select */
	public enum MilesDriven {
	    /**Less than 5k miles */
		Under5K(1),
	    
		/**5-10 thousand miles */
	    FiveToTenK(2),
	    
	    /**10-15 thousand miles*/
	    TenToFifteenK(3),
	    
	    /**15-20 thousand miles */
	    FifteenToTwentyK(4),
	    
	    /**20+ thousand miles */
	    TwentyKPlus(5);
	    
	    /** The internal data value of the option    */
	    public int dataValue;
	    
	    private MilesDriven(int dv) {
	    	dataValue = dv;
	    }
	    
	    /**
	     * Convert from a data value scraped from the page into a MilesDriven option
	     * @param dv The datavalue on the option on the page
	     * @return The proper entry for that option
	     */
	    public static MilesDriven getByDataValue(int dv) {
	        for (MilesDriven md : values()) {
	            if (md.dataValue == dv) return md;
	        }
	        return null;
	    }
	  }
	
	/** The options for the weather conditions select */
	public enum Weather {
		/** Hot sunny days*/
		SUNNY(1),
		
		/** Occasional rain */
		RAIN(2),
		
		/** Frequent rain*/
		FREQUENTRAIN(3),
		
		/** Snow, rain, ice*/
		SNOW(4),
		
		/** Extreme weather */
		EXTREMEWINTER(5);
		
		 /** The internal data value of the option    */
		public int dataValue;
		
		private Weather(int dv) {
		    dataValue = dv;
		}
		    
		/**
	     * Convert from a data value scraped from the page into a MilesDriven option
	     * @param dv The datavalue on the option on the page
	     * @return The proper entry for that option
	     */
	    public static Weather getByDataValue(int dv) {
	        for (Weather w : values()) {
	            if (w.dataValue == dv) return w;
	        }
	        return null;
	    }
	}
	
	/** The options for the driving style select */
	public enum DrivingStyle {
		/** Slow and steady*/
		SLOW(1),
		
		/**Easygoing*/
		EASYGOING(2),
		
		/**Typical */
		TYPICAL(3),
		
		/**Spirited*/
		SPIRITED(4),
		
		/**Fast and lively*/
		FAST(5);
		
		 /** The internal data value of the option    */
		public int dataValue;
		
		private DrivingStyle(int dv) {
		    dataValue = dv;
		}
		    
		/**
	     * Convert from a data value scraped from the page into a MilesDriven option
	     * @param dv The datavalue on the option on the page
	     * @return The proper entry for that option
	     */
	    public static DrivingStyle getByDataValue(int dv) {
	        for (DrivingStyle ds : values()) {
	            if (ds.dataValue == dv) return ds;
	        }
	        return null;
	    }
	}

	/** The options for the truck options select */
	public enum TruckOptions {
		/** Off-road */
		OFFROAD(1),
		
		/**Towing things */
		TOWING(2),
		
		/**Heavy loads */
		LOADS(3);
		
		/** The internal data value of the option    */
		public int dataValue;
		
		private TruckOptions(int dv) {
		    dataValue = dv;
		}
		  
		/**
	     * Convert from a data value scraped from the page into a MilesDriven option
	     * @param dv The datavalue on the option on the page
	     * @return The proper entry for that option
	     */
	    public static TruckOptions getByDataValue(int dv) {
	        for (TruckOptions to : values()) {
	            if (to.dataValue == dv) return to;
	        }
	        return null;
	    }
	}
}
