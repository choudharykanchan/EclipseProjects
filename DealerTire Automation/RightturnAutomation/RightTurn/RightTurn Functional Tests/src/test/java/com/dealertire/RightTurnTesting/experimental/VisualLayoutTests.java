package com.dealertire.RightTurnTesting.experimental;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.dealertire.RightTurnFramework.BaseTest;
import com.dealertire.RightTurnFramework.Models.Vehicle;
import com.dealertire.RightTurnFramework.Models.Vehicle.Criteria;
import com.dealertire.RightTurnFramework.Pages.LocationPage;
import com.dealertire.RightTurnFramework.Pages.ProductPage;
import com.dealertire.RightTurnFramework.Pages.TireCoachPage;
import com.dealertire.RightTurnFramework.Pages.VehiclePage;
import com.dealertire.RightTurnFramework.Pages.VerifyPage;
import com.googlecode.fightinglayoutbugs.FightingLayoutBugs;
import com.googlecode.fightinglayoutbugs.LayoutBug;
import com.googlecode.fightinglayoutbugs.SimpleTextDetector;
import com.googlecode.fightinglayoutbugs.WebPage;

/**
 * Experimental test suite to do some simple visual checks programmatically.
 * WARNING: These tests are experimental (and very buggy). They have been commented out to remove them from the test suite.
 * If you want to work with them, uncomment the @Test annotations.
 * @author bgreen
 *
 */
@SuppressWarnings("unused")
public class VisualLayoutTests extends BaseTest {
	
	/** The library object that does the checks*/
	protected FightingLayoutBugs flb;
	
	/**The bugs we found, to be logged */
	protected Collection<LayoutBug> layoutBugs;
	
	/**The page we are going to test */
	WebPage pageUnderTest;

	/**
     * @see com.dealertire.RightTurnFramework.BaseTest#BaseTest(String, String, String)
     */
	public VisualLayoutTests(String os, String browserName,	String browserVersion) {
		super(os, browserName, browserVersion);
	}
	
	/**
	 * Runs before every test to set up the library
	 */
	@Before
	public void SetUp() {	
		flb = new FightingLayoutBugs();
		
		File screenshotLocation = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + File.separatorChar + "FLBScreenshotDir");
		
		if (!screenshotLocation.exists()) screenshotLocation.mkdir();
		logger.info("Screenshot location: " + screenshotLocation.getAbsolutePath());
		flb.setScreenshotDir(screenshotLocation);
		
		//Fix for chrome bug:
		flb.setTextDetector(new SimpleTextDetector());
	}

	/**
	 * Check the layout of the Location page for mistakes
	 */
	//@Test
	public void layoutLocationPage() {
		locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
		verifyLayout();	
	}

	
	/**
	 * Check the layout of the vehicle page for mistakes
	 */
	//@Test
	public void layoutVehiclePage() {
		locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
		locationPage.enterZipCode("44114");
		vehiclePage = (VehiclePage) locationPage.clickNext();
		verifyLayout();
	}

	
	/**
	 * Check the layout of the verify page for mistakes
	 */
	//@Test
	public void layoutVerifyPage() {
		locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
		locationPage.enterZipCode("44114");
		vehiclePage = (VehiclePage) locationPage.clickNext();
		
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("Using vehicle " + vehicle);
		
		vehiclePage.selectVehicle(vehicle);
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		verifyLayout();
	}

	
	/**
	 * Check the layout of the Tire Coach page for mistakes
	 */
	//@Test
	public void layoutTireCoachPage() {
		locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
		locationPage.enterZipCode("44114");
		vehiclePage = (VehiclePage) locationPage.clickNext();
		
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("Using vehicle " + vehicle);
		
		vehiclePage.selectVehicle(vehicle);
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		verifyLayout();
	}

	
	/**
	 * Check the layout of the product page for mistakes.
	 */
	//@Test
	public void layoutProductPage() {
		locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
		locationPage.enterZipCode("44114");
		vehiclePage = (VehiclePage) locationPage.clickNext();
		
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("Using vehicle " + vehicle);
		
		vehiclePage.selectVehicle(vehicle);
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		productPage = (ProductPage) tireCoachPage.clickNext();
		verifyLayout();
	}
	
	
	private void verifyLayout() {
		pageUnderTest = new WebPage(driver);
		layoutBugs = flb.findLayoutBugsIn(pageUnderTest);
		if (layoutBugs.size() > 0) {
	        logger.warn("Found " + layoutBugs.size() + " layout bug(s).");
	        for (LayoutBug bug : layoutBugs) {
	            logger.error(bug);
	        }
	        fail("Found " + layoutBugs.size() + " layout bug(s).");
		} else {
			logger.info("No layout bugs found!");
		}
	}
	
}
