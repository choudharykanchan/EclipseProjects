package test;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


public class Testing {
	
	static WebDriver driver;
	public static void main(String args[]) throws MalformedURLException {
		driver = new RemoteWebDriver(new URL("http://10.101.23.145:4444/wd/hub"), DesiredCapabilities.firefox());
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("http://www.google.com");
	}
}
