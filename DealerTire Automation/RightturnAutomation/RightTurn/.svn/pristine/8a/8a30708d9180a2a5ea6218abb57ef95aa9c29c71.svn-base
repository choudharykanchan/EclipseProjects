package com.dealertire.RightTurnFramework;

import org.openqa.selenium.WebDriver;

import com.dealertire.RightTurnFramework.Pages.CheckoutPage;
import com.dealertire.RightTurnFramework.Pages.ComparePage;
import com.dealertire.RightTurnFramework.Pages.ConfirmationPage;
import com.dealertire.RightTurnFramework.Pages.HomePage;
import com.dealertire.RightTurnFramework.Pages.InstallPage;
import com.dealertire.RightTurnFramework.Pages.LocationPage;
import com.dealertire.RightTurnFramework.Pages.ProductDetailsPage;
import com.dealertire.RightTurnFramework.Pages.ProductPage;
import com.dealertire.RightTurnFramework.Pages.SchedulePage;
import com.dealertire.RightTurnFramework.Pages.ServicesPage;
import com.dealertire.RightTurnFramework.Pages.TireCoachPage;
import com.dealertire.RightTurnFramework.Pages.VehiclePage;
import com.dealertire.RightTurnFramework.Pages.VerifyPage;
import com.dealertire.RightTurnFramework.Pages.WinterPackageQuestionPage;
import com.dealertire.RightTurnFramework.Pages.WinterTireQuestionPage;

/**
 * Factory that creates RightTurn Page Objects
 * @author bgreen
 *
 */
public class RTPageFactory {
	
	/**
	 * Create a page object based on the current URL.
	 * @param url The URL you're on or plan to be on
	 * @param driver The driver to be passed to the new page
	 * @return A PageObject matching the page you requested.
	 */
	static public PageObject getPageFromURL(String url, WebDriver driver) {
		PageObject po;
		
		if (url.contains("app/location")) po = new LocationPage(driver);
		else if (url.contains("app/vehicle")) po = new VehiclePage(driver);
		else if (url.contains("app/verify")) po = new VerifyPage(driver);
		else if (url.contains("app/tire-coach/winter")) po = new WinterTireQuestionPage(driver);
		else if (url.contains("app/tire-coach/package")) po = new WinterPackageQuestionPage(driver);
		else if (url.contains("app/tire-coach")) po = new TireCoachPage(driver);
		else if (url.contains("app/product/detail")) po = new ProductDetailsPage(driver);
		else if (url.contains("app/product/comparison/")) po = new ComparePage(driver);
		else if (url.contains("app/product")) po = new ProductPage(driver);
		else if (url.contains("app/installer")) po = new InstallPage(driver);
		else if (url.contains("app/schedule")) po = new SchedulePage(driver);
		else if (url.contains("app/services")) po = new ServicesPage(driver);
		else if (url.contains("app/checkout")) po = new CheckoutPage(driver);
		else if (url.contains("app/confirmation")) po = new ConfirmationPage(driver);
		else po = new HomePage(driver);
		
		return po;
	}
}
