package SeleniumGrid.SeleniumGrid;


import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CrossBrowserTesting {

	RemoteWebDriver driver;

	/**
	 * This function will execute before each Test tag in testng.xml
	 * @param browser
	 * @throws Exception
	 */
	@BeforeTest
	@Parameters({"browser","version"})
	
	public void setup(String browser, String version) throws Exception{
	
		if(browser.equalsIgnoreCase("firefox")){
		
			 DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			// capabilities.setPlatform(Platform.WIN8_1);
			  driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
		}
		//Check if parameter passed as 'chrome'
		else if(browser.equalsIgnoreCase("chrome")){
			//set path to chromedriver.exe
		/*	System.setProperty("webdriver.chrome.driver","C:/Users/thinksysuser/Desktop/Automation soft/chromedriver.exe");
			//create chrome instance
			driver = new ChromeDriver();*/
			 DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			 capabilities.setVersion(version);
			  driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
		}
		//Check if parameter passed as 'Edge'
				else if(browser.equalsIgnoreCase("Edge")){
					//set path to Edge.exe
					System.setProperty("webdriver.edge.driver","C:/Users/thinksysuser/Desktop/Automation soft/MicrosoftWebDriver.exe");
					//create Edge instance
					driver = new EdgeDriver();
				}
		else{
			//If no browser passed throw exception
			throw new Exception("Browser is not correct");
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@Test
	public void testParameterWithXML() throws InterruptedException{
		driver.get("http://demo.guru99.com/V4/");
		//Find user name
		WebElement userName = driver.findElement(By.name("uid"));
		//Fill user name
		userName.sendKeys("guru99");
		//Find password
		WebElement password = driver.findElement(By.name("password"));
		//Fill password
		password.sendKeys("guru99");
	}
	@AfterTest
	public void tearup()
	{
		driver.close();
	}
}
