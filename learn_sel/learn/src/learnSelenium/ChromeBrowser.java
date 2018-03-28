package learnSelenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeBrowser {
	public static void main(String args[])
	
	{
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\thinksysuser\\Desktop\\chromedriver.exe");
		WebDriver driver= new ChromeDriver();
		driver.get("http://toolsqa.com/automation-practice-form/");
		driver.quit();
		
	}
}
