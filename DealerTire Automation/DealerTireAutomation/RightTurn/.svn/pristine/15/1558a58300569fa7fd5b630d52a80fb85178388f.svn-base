package com.dealertire.RightTurnTesting;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.dealertire.RightTurnFramework.BaseTest;
import com.dealertire.RightTurnFramework.Models.Product;
import com.dealertire.RightTurnFramework.Models.Vehicle;
import com.dealertire.RightTurnFramework.Models.Vehicle.Criteria;
import com.dealertire.RightTurnFramework.Pages.LocationPage;
import com.dealertire.RightTurnFramework.Pages.ProductDetailsPage;
import com.dealertire.RightTurnFramework.Pages.ProductPage;
import com.dealertire.RightTurnFramework.Pages.TireCoachPage;
import com.dealertire.RightTurnFramework.Pages.VehiclePage;
import com.dealertire.RightTurnFramework.Pages.VerifyPage;

@SuppressWarnings("javadoc")
public class debugTests extends BaseTest {

	
	
	public debugTests(String os, String browserName, String browserVersion) {
		super(os, browserName, browserVersion);
	}

	@Test
	public void debug() {
		
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		criteriaList.put(Criteria.STAGGERED, true);
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("Using vehicle " + vehicle);
		
		//Navigate to the Product page
		locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
		locationPage.enterZipCode("44114");
		vehiclePage = (VehiclePage) locationPage.clickNext();
		vehiclePage.selectVehicle(vehicle);
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		
		//Navigate to Install page
		productPage = (ProductPage) tireCoachPage.clickNext();
		Product product = productPage.getRandomProduct();
		logger.info("Using product " + product);

		detailsPage = (ProductDetailsPage) productPage.clickDetailsButton(product);
		Product prod2 = detailsPage.getProductDisplayed();
		assertEquals(product, prod2);
	}
}
