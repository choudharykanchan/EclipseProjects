package RTFrameWork;


import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeMethod;
import org.junit.AfterClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeTest;

import RTPages.CheckoutPage;
import RTPages.HomePage;
import RTPages.NeedsPage;
import RTPages.SchedulePage;
import RTPages.TiresPage;
import RTPages.VerifyPage;
import RTPages.InstallerPage;

public class BaseTest {
public	WebDriver driver;
	String uRL;
	String browser="FF";
	public HomePage homePage;
	public VerifyPage verifyPage;
	public NeedsPage needsPage;
	public TiresPage tiresPage;
	public SchedulePage schedulePage;
	public InstallerPage installerPage;
	public CheckoutPage checkoutPage;
	
	public BaseTest(String browser)
	{
		this.browser=browser;
		this.uRL=Utilities.uRL;
	}
	@BeforeTest
	public void setUp()
	{
		switch(browser)
		
		{
		case "FF":
			System.setProperty("webdriver.gecko.driver","C:/Users/thinksysuser/Desktop/Automation soft/geckodriver.exe");
			 driver = new FirefoxDriver();
			break;
		case "Chrome":
			System.setProperty("webdriver.chrome.driver","C:/Users/thinksysuser/Desktop/Automation soft/chromedriver.exe");
		 driver = new ChromeDriver();
			break;
		}
		driver.get(uRL);
	}
	
	@AfterTest
	public void tearDown()
	{
		driver.close();
	}

}
