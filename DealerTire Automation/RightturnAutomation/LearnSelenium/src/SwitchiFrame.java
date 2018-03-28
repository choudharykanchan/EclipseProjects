import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;

public class SwitchiFrame {
	WebDriver driver;
	String base_url = "http://www.americangreetings.com";

	@Before
	public void setUp() {
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
	}

	@Test
	public void testIframe() {
		driver.get(base_url+"/");
		try{ Thread.sleep(3000); }
		catch(Exception e){ e.printStackTrace();}		
		WebElement singInLink = driver.findElement(By.className("agi-mem-signin"));
		//WebElement singInLink = driver.findElement(By.linkText("Sign In"));
		singInLink.click();
		try{ Thread.sleep(3000); }
		catch(Exception e){ e.printStackTrace();}	
			//driver.switchTo().frame(1);(Use if having only 1 iframe in code exist)
		//driver.switchTo().frame(driver.findElements(By.xpath("//iframe[@scrolling='no']")).get(0));
		driver.switchTo().frame( driver.findElements(By.tagName("iFrame")).get(0));
	
		WebElement id = driver.findElement(By.id("email"));
		id.sendKeys("xyz.com");
		
		

	}

	@After
	public void tearDown() {
		driver.quit();

	}
}
