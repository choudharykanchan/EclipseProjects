package SeleniumGrid.SeleniumGrid;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Parameterisation {
	@Test
	//@Parameters({"username"})
			@Parameters({"username"})	
    public void mailTest( String username) throws MalformedURLException{
		    DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		   // System.setProperty("webdriver.chrome.driver", "C:\\Users\\thinksysuser\\Desktop\\Automation soft\\chromedriver.exe");
		   // capabilities.setBrowserName("chrome");
		//    capabilities.setVersion("58.0");
		   // capabilities.setPlatform(Platform.WINDOWS);
	        RemoteWebDriver driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
		    driver.navigate().to("http://gmail.com");
		    driver.findElement(By.xpath("//input[@id='identifierId']")) .sendKeys(username);
		   
		    driver.close();
		
		}

}
