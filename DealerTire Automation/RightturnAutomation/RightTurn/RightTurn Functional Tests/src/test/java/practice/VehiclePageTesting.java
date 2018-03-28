package practice;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.dealertire.RightTurnFramework.BaseTest;
import com.dealertire.RightTurnFramework.PageObject;
import com.dealertire.RightTurnFramework.Models.Product;
import com.dealertire.RightTurnFramework.Models.Vehicle;
import com.dealertire.RightTurnFramework.Pages.CheckoutPage;
import com.dealertire.RightTurnFramework.Pages.HomePage;
import com.dealertire.RightTurnFramework.Pages.InstallPage;
import com.dealertire.RightTurnFramework.Pages.LocationPage;
import com.dealertire.RightTurnFramework.Pages.ProductPage;
import com.dealertire.RightTurnFramework.Pages.SchedulePage;
import com.dealertire.RightTurnFramework.Pages.TireCoachPage;
import com.dealertire.RightTurnFramework.Pages.TireCoachPage.MilesDriven;
import com.dealertire.RightTurnFramework.Pages.VehiclePage;
import com.dealertire.RightTurnFramework.Pages.VerifyPage;

import junit.framework.Assert;

public class VehiclePageTesting extends BaseTest {



	public VehiclePageTesting(String os, String browserName, String browserVersion) {
		super(os, browserName, browserVersion);
	}
	@Test
	public void test() throws Exception {
		locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();

		locationPage.waitForPageLoad();

		assertTrue(locationPage.isLoaded());
		locationPage.enterZipCode("44444");

		vehiclePage = (VehiclePage)locationPage.clickNext();
		Vehicle vehicle = Vehicle.getRandomVehicle();
		vehiclePage.selectVehicle(vehicle);

		verifyPage = (VerifyPage) vehiclePage.clickNext();
		tireCoachPage =(TireCoachPage) verifyPage.clickNext();
		tireCoachPage.waitForPageLoad();
		
		assertTrue("Tire coach page is not loaded", tireCoachPage.isLoaded());
		assertEquals(MilesDriven.TenToFifteenK, tireCoachPage.getSelectedMilesDriven());
		productPage=(ProductPage) tireCoachPage.clickNext();
		productPage.getRandomProduct();
		Product product=productPage.getRandomProduct();

		productPage.selectRandomQuantity(product);
		installPage=(InstallPage)productPage.selectProduct(product);
		
		schedulePage=(SchedulePage)installPage.clickNext();
		schedulePage.selectAvailableXtime();
	    //checkoutPage=	(CheckoutPage)schedulePage.clickNext();
	   // checkoutPage.changeAppointment();
	   // checkoutPage.fillForm();
	   // checkoutPage.placeOrder();
	    
	}

}