package DTRT.DTRT;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class Test1 {
	@Test
	public void start()
	{
		System.setProperty("webdriver.gecko.driver", "C:/Users/thinksysuser/Desktop/Automation soft/geckodriver.exe");
		WebDriver driver=new FirefoxDriver();
		driver.get("https://demo.rightturn.com/");
		driver.close();
		
		
	}

}
