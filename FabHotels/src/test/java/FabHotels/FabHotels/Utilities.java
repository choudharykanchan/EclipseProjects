package FabHotels.FabHotels;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Alert;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

public class Utilities {
	static AppiumDriver driver;
	static WebDriverWait wait;
	
	public static AppiumDriver LaunchAndroidapp() throws MalformedURLException  {

	DesiredCapabilities capabilities = new DesiredCapabilities();
	capabilities.setCapability("deviceName", "ZY3222MGMB");
	capabilities.setCapability("platformName", "Android");
	capabilities.setCapability(CapabilityType.VERSION, "7.0");
	capabilities.setCapability("appPackage", "com.fabhotels.guests");
	capabilities.setCapability("appActivity", "app.fabhotels.MainActivity");
	capabilities.setCapability("noReset", true);
	capabilities.setCapability("fullReset", false);
	//capabilities.setCapability("autoGrantPermissions", "true");
	capabilities.setCapability("locationServicesAuthorized", true);
	capabilities.setCapability("autoAcceptAlerts", true);
	driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
	wait = new WebDriverWait(driver, 10);
	return driver;

}
	public static void teardown()
	{
		//((AppiumDriver)driver).closeApp();
		driver.quit();
	}
	
	public static void acceptAlertIfPresent() throws InterruptedException
	{
		try 
	    { 

          Thread.sleep(2000);
			Alert alert=driver.switchTo().alert(); 
			alert.accept();
			//Thread.sleep(2000);
			//driver.findElementById("android:id/button1");
			
	        
	        
	    }    
	    catch (NoAlertPresentException Ex) 
	    { 
	       System.out.println("No alert present");
	    }  
		
		
	}
	public static void acceptLoacationPopupIfPressent()
	{
		if(driver.findElementsById("android:id/button1").size()>0){
			driver.findElementById("android:id/button1").click();	
		}
	}
	public static void waitUntilElementfound(WebElement element)
	{
		WebDriverWait wait = new WebDriverWait(driver, 1000);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public static void verticalScroll() throws InterruptedException {
		Thread.sleep(2000);
        Dimension size=  driver.manage().window().getSize();
          int start_x=size.getWidth()/8;
          int start_y=(int)(size.getHeight() * 0.80);
          int end_x=size.getWidth()/8;
          int end_y=(int)(size.getHeight() * 0.30);
         
        //  driver.swipe(start_x, start_y, end_x, end_y, 3000);
         Thread.sleep(3000);
       
          TouchAction action=new TouchAction(driver);
          action.press(start_x, start_y).waitAction().moveTo(end_x, end_y).release().perform();
         // action.longPress(start_x,start_y).waitAction().moveTo(end_x,end_y).release().perform();
          Thread.sleep(2000);
      }
	
	public static void verticalScrollwithkeypad() throws InterruptedException {
		Thread.sleep(2000);
        Dimension size=  driver.manage().window().getSize();
          int start_x=size.getWidth()/8;
          int start_y=(int)(size.getHeight() * 0.40);
          int end_x=size.getWidth()/8;
          int end_y=(int)(size.getHeight() * 0.20);
         //driver.swipe(start_x, start_y, end_x, end_y, 3000);
         Thread.sleep(3000);
         //TouchAction action=new TouchAction(driver);
          //action.longPress(start_x,start_y).waitAction().moveTo(end_x,end_y).release().perform();
         TouchAction action=new TouchAction(driver);
         action.press(start_x, start_y).waitAction().moveTo(end_x, end_y).release().perform();
          Thread.sleep(2000);

      }
	public static void horizontalScroll() throws InterruptedException {
		Thread.sleep(2000);
        Dimension size=  driver.manage().window().getSize();
          
          int start_x=857;
          int start_y=460;
          int end_x=108;
          int end_y=460;
         
        //  driver.swipe(start_x, start_y, end_x, end_y, 3000);
         Thread.sleep(3000);
       
          TouchAction action=new TouchAction(driver);
          action.press(start_x, start_y).waitAction().moveTo(end_x, end_y).release().perform();
         // action.longPress(start_x,start_y).waitAction().moveTo(end_x,end_y).release().perform();
          Thread.sleep(2000);
      }
}
