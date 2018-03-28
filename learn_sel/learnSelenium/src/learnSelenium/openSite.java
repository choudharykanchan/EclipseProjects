package learnSelenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class openSite {

	public static void main(String args[]) throws InterruptedException{
		WebDriver driver= new FirefoxDriver();
		
		driver.get("http://www.store.demoqa.com");
		System.out.println("Successfully opened the website www.Store.Demoqa.com");
		Thread.sleep(5);
		driver.quit();
	}
	
	
}

