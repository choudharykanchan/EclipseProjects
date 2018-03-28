package TestNG;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MultipleTimesParameters {
WebDriver driver;
@DataProvider(name="Authentication")
public Object[][] credentials()
{
	return new Object[][]{{"Testuser1","Test@123"},{"Testuder2","Test@123"}};
	
}
@Test(dataProvider="Authentication")
public void test(String sUserName,String sPassword)
{
	driver = new FirefoxDriver();
	 
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    driver.get("http://www.store.demoqa.com");

    driver.findElement(By.xpath(".//*[@id='account']/a")).click();

    driver.findElement(By.id("log")).sendKeys(sUserName);

    driver.findElement(By.id("pwd")).sendKeys(sPassword);

    driver.findElement(By.id("login")).click();

    driver.findElement(By.xpath(".//*[@id='account_logout']/a")).click();

    driver.quit();
    driver.close();
}

}
