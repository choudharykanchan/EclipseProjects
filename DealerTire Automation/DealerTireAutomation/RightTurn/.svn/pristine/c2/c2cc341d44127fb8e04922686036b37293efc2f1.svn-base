package com.dealertire.RightTurnFramework.testing;

import java.util.ArrayList;

import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.dealertire.RightTurnFramework.BaseTest;
import com.dealertire.RightTurnFramework.Models.Size;
import com.dealertire.RightTurnFramework.Models.Size.Criteria;
import com.dealertire.RightTurnFramework.Models.Vehicle;
import com.dealertire.RightTurnFramework.Pages.LocationPage;
import com.dealertire.RightTurnFramework.Pages.ProductPage;
import com.dealertire.RightTurnFramework.Pages.TireCoachPage;
import com.dealertire.RightTurnFramework.Pages.VehiclePage;
import com.dealertire.RightTurnFramework.Pages.VerifyPage;
import com.dealertire.RightTurnFramework.Pages.VehiclePage.Tab;
@SuppressWarnings("javadoc")
public class dbug extends BaseTest {

	public dbug(String os, String browserName, String browserVersion) {
		super(os, browserName, browserVersion);
	}
	
	@Test
	public void debugTest() {

ArrayList<Size> sizes = Size.getAllSizes();
		
		for (Size size : sizes) {
			logger.info("Using size " + size);
			
			//Navigate to the Product page
			locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
			locationPage.enterZipCode("44114");
			vehiclePage = (VehiclePage) locationPage.clickNext();
			vehiclePage.selectTab(Tab.SIZE_SELECT);
			vehiclePage.selectSize(size);
			
			verifyPage = (VerifyPage) vehiclePage.clickNext();
			if (verifyPage.isShowingSizeRadioButton() != size.matchesCriteria(Criteria.RUNFLAT)) {
				logger.warn("Mismatch! Runflat is " + verifyPage.isShowingSizeRadioButton());
			} else {
				logger.info("Match.");
			}
			
			logger.info("----------------------------------------------");
		}
		
		logger.info("END");
			
	}


}
