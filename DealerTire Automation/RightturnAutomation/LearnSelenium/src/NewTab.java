import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import org.junit.After;import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
public class NewTab {
	WebDriver  driver;
	String base_URL="http://www.americangreetings.com/#";
	
	@Before
	public void setUp()
	{
		driver=new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
	
		
		
		
	}
	@Test
	public void newTab() throws InterruptedException
	{
	driver.get(base_URL);
	driver.findElement(By.tagName("body")).sendKeys(Keys.CONTROL +"t");
	


	Thread.sleep(2000);
	driver.get("https://www.facebook.com");
	
	//String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL,"t");
	}
	@Test
	public void dragAnddrop()
	{
		
	}
	@After
	public void tearDown() {
		driver.quit();


	}
}
