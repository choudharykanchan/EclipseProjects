package TestCases;

import static org.testng.Assert.assertEquals;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import FabHotels.FabHotels.Utilities;

import PageObjects.HomePage;
import PageObjects.HotelDetails;
import PageObjects.Payment;
import PageObjects.SearchResults;
import PageObjects.UserVerificationPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.StartsActivity;

public class T_C1 {
	AppiumDriver driver;
	UserVerificationPage userverificationpage;
	HomePage homepage;
	SearchResults searchresults;
	HotelDetails hoteldetails;
	Payment payment;
	@BeforeMethod
	public void first() throws MalformedURLException
	{
		driver=Utilities.LaunchAndroidapp();
	    userverificationpage=new UserVerificationPage(driver);
	    homepage=new HomePage(driver);
	    searchresults=new SearchResults(driver);
	    hoteldetails=new HotelDetails(driver);
	    payment=new Payment(driver);
	
	}
@Test
	public void all_Localities() throws InterruptedException
	{

		//Utilities.acceptAlertIfPresent();
		Utilities.waitUntilElementfound(userverificationpage.skipBtn);
		userverificationpage.skipBtn.click();
		Thread.sleep(3000);
		Utilities.acceptLoacationPopupIfPressent();
		homepage.newDelhiIcon.click();
		
		List<WebElement> localitiesNames= searchresults.localities;
		Thread.sleep(3000);
		List<String> uniqueNames=new ArrayList<String>();
		for(int i=0;i<2;i++)
		{
		for(int j=0;j<localitiesNames.size();j++)
		{
			String name=localitiesNames.get(j).getText();
			if(!uniqueNames.contains(name))
			uniqueNames.add(name);
		}
		Utilities.horizontalScroll();
		}
		
		for(int i=0;i<uniqueNames.size();i++)
		{
			
			System.out.println(uniqueNames.get(i));
		
		}
	}
		
	
	@Test
	public void verifyPhoneNumber()
	{
		Utilities.waitUntilElementfound(userverificationpage.skipBtn);
		userverificationpage.skipBtn.click();
		Utilities.acceptLoacationPopupIfPressent();
		homepage.phoneIcon.click();
		homepage.justOnceBtn.click();
		String phoneNumber=homepage.dialerPhoneNumber.getText();
		assertEquals("+91 70424 24242", phoneNumber);
	}
	@Test
	public void verifyCouponAndPaymentDetails() throws InterruptedException
	{
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		Utilities.waitUntilElementfound(userverificationpage.skipBtn);
		userverificationpage.skipBtn.click();
		Utilities.acceptLoacationPopupIfPressent();
		homepage.newDelhiIcon.click();
		searchresults.hotelImage.get(0).click();
		hoteldetails.selectDetails.click();
		hoteldetails.calenderDonebtn.click();
		
		for(int i=1;i<=searchresults.hotelImage.size()+1;i++)
		{
	
		if(hoteldetails.bookNow.isEnabled())
		{
			//System.out.println("Book Now is Enabled");
			hoteldetails.bookNow.click();
			break;
		}
		else
		{
			hoteldetails.backbtn.click();
			Thread.sleep(2000);
			Utilities.verticalScroll();
			//Utilities.waitUntilElementfound(searchresults.hotelImage.get(0));
			searchresults.hotelImage.get(0).click();
		}
		}
		
		Utilities.verticalScroll();
		payment.enterCouponfield.sendKeys("FABAPP25");
		payment.applybtn.click();
		
		int totalvalue=Integer.parseInt(payment.totalvalue.getText().replaceAll("\\D+",""));
	
		int discountedvalue=Integer.parseInt(payment.discountvalue.getText().replaceAll("\\D+",""));
		System.out.println("Totalvalue: Rs"+totalvalue+" Discountedvalue: Rs"+ discountedvalue);
		payment.continuebtn.click();
		payment.fullname.sendKeys("test test");
		Utilities.verticalScrollwithkeypad();
		payment.email.sendKeys("test@t.com");
		payment.mobilenumber.sendKeys("9968045655");
		Utilities.verticalScrollwithkeypad();
		payment.proceedToPaybtn.click();
		Utilities.verticalScroll();
		Utilities.verticalScroll();
		Thread.sleep(5000);
		
		
		int finalPay=Integer.parseInt(payment.finalPaymnet.getText().replaceAll("\\D+",""));
		
		int payAtHotel=Integer.parseInt(payment.payAthotel.getText().replaceAll("\\D+",""));
		System.out.println("Totalvalue After discount Rs: "+totalvalue+" Discounted value : Rs"+discountedvalue+" finalPay: Rs"+finalPay+" payAtHotel: Rs"+payAtHotel);
		payment.payAthotel.click();
		
		Thread.sleep(5000);
		payment.otpCloseButton.click();
		System.out.println(payment.otpCloseButton.getText());
		if(totalvalue-discountedvalue==finalPay)
		{
			System.out.println("Discount removed from payment");
		}
		
	}
	
	@Test
	public void totalCitiesAndHotels() throws InterruptedException
	{
	driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	Utilities.waitUntilElementfound(userverificationpage.skipBtn);
	userverificationpage.skipBtn.click();
	Utilities.acceptLoacationPopupIfPressent();
	homepage.searchBtn.click();
	
	WebElement All=	searchresults.ALL;
	List<WebElement> tempList=All.findElements(By.className("android.widget.LinearLayout"));
	//System.out.println("Size: "+tempList.size());
	List<String> cities=new ArrayList<String>();
	List<String> Details=new ArrayList<String>();

	List<String> temp_cities=new ArrayList<String>();
	List<String> temp_Details=new ArrayList<String>();


	for(int i=0;i<tempList.size()-1;i++){
	//System.out.println("Iter: "+ i);
	Thread.sleep(2000);
	Utilities.waitUntilElementfound(tempList.get(i).findElement(By.id("com.fabhotels.guests:id/tvSearchItemCity")));
	cities.add(tempList.get(i).findElement(By.id("com.fabhotels.guests:id/tvSearchItemCity")).getText());
	Utilities.waitUntilElementfound(tempList.get(i).findElement(By.id("com.fabhotels.guests:id/tvSearchItemDescription")));
	Details.add(tempList.get(i).findElement(By.id("com.fabhotels.guests:id/tvSearchItemDescription")).getText());	
	}

	int end=1;

	while(end<7){
		//System.out.println("While Loop"+end);
		
		try {
			Utilities.verticalScroll();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		All=	searchresults.ALL;
		tempList=All.findElements(By.className("android.widget.LinearLayout"));
		
		//System.out.println("Size: "+tempList.size());
		
		for(int i=1;i<tempList.size()-1;i++){
			//System.out.println("Inside Temp loop Iter number"+i);
			
		Utilities.waitUntilElementfound(tempList.get(i).findElement(By.id("com.fabhotels.guests:id/tvSearchItemCity")));
		temp_cities.add(tempList.get(i).findElement(By.id("com.fabhotels.guests:id/tvSearchItemCity")).getText());
		Utilities.waitUntilElementfound(tempList.get(i).findElement(By.id("com.fabhotels.guests:id/tvSearchItemDescription")));
		temp_Details.add(tempList.get(i).findElement(By.id("com.fabhotels.guests:id/tvSearchItemDescription")).getText());
		
		if(end==6){
			Utilities.waitUntilElementfound(tempList.get(i+1).findElement(By.id("com.fabhotels.guests:id/tvSearchItemCity")));
			temp_cities.add(tempList.get(i+1).findElement(By.id("com.fabhotels.guests:id/tvSearchItemCity")).getText());
			Utilities.waitUntilElementfound(tempList.get(i+1).findElement(By.id("com.fabhotels.guests:id/tvSearchItemDescription")));
			temp_Details.add(tempList.get(i+1).findElement(By.id("com.fabhotels.guests:id/tvSearchItemDescription")).getText());
			
			
		}
		
		}
		
		
		for(int i=0;i<temp_cities.size();i++){
			
			
			if(!cities.contains(temp_cities.get(i))){	
				cities.add(temp_cities.get(i));
				Details.add(temp_Details.get(i));
			}
			
		}
		end++;
	}
	
	for(int y=0;y<cities.size();y++){
		System.out.println(cities.get(y));
		System.out.println(Details.get(y));
	}
	}
	
	@AfterMethod
	public void teardown() throws InterruptedException
	{
		//Thread.sleep(5000);
		Utilities.teardown();
	}

}
