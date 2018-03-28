package SeleniumGrid.SeleniumGrid;



import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class GridUsingDataProvider {
	//if data provider is in different class
	@Test(dataProvider="username",dataProviderClass=DataProviderClass.class)
	//if data provider is in same class
	//@Test(dataProvider="username")

     public void mailTest( String username) throws MalformedURLException{
		    DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		    //DesiredCapabilities cap=new DesiredCapabilities();
		// cap.setCapability("browserName", "chrome");
		 //cap.setCapability("version", "10009");
		   // System.setProperty("webdriver.chrome.driver", "C:\\Users\\thinksysuser\\Desktop\\Automation soft\\chromedriver.exe");
		   // capabilities.setBrowserName("chrome");
		//    capabilities.setVersion("58.0");
		   // capabilities.setPlatform(Platform.WINDOWS);
	        RemoteWebDriver driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
		    driver.navigate().to("http://gmail.com");
		    driver.findElement(By.xpath("//input[@id='identifierId']")) .sendKeys(username);
		   
		    driver.close();
		
		}
	/*	//If Data provider is written in same class
	@DataProvider(name="username")
  
    public Object[][] getDataFromDataprovider(){
		
		Object arr[][]=new String[4][1];
		
		arr[0][0]="Ram";
		arr[1][0]="sita";
		arr[2][0]="Mr Kennedy";
		arr[3][0]="Obama";
		
    return arr; 
    	
      
	    }
*/
}
