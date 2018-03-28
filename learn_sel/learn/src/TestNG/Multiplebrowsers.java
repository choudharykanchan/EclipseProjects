package TestNG;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Multiplebrowsers {
	public WebDriver driver;
	
	@org.testng.annotations.BeforeClass
	@Parameters("Browser")
	public void beforeTest(String Browser)
	{
		if(Browser.equalsIgnoreCase("firefox"))
		{
			driver= new FirefoxDriver();
		}
		else if (Browser.equalsIgnoreCase("ie"))
{
	System.setProperty("webdriver.ie.driver", "C://Users//thinksysuser//Desktop//Automation//IEDriverServer.exe");
	

		  driver = new InternetExplorerDriver();
	}
		 driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
	  driver.get("http://store.demoqa.com/products-page/your-account/"); 
}

	  @Test
public void login() {
	
	    driver.findElement(By.id("log")).sendKeys("testuser_1");
	 
	    driver.findElement(By.id("pwd")).sendKeys("Test@123");
	 
	    driver.findElement(By.id("login")).click();
	 
		} 	
	  @org.testng.annotations.AfterClass 
	  public void afterTest() {
		  driver.quit();
	 
		}
}
