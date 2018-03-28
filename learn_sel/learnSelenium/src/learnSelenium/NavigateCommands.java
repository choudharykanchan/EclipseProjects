package learnSelenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebElement;


public class NavigateCommands {
	public static void main (String args[])
	{
		WebDriver driver= new FirefoxDriver();
		driver.get("http://demoqa.com/");
		WebElement registration= driver.findElement(By.linkText("Registration"));
		driver.navigate().back();
		driver.navigate().forward();
		driver.navigate().to("http://demoqa.com");
		driver.navigate().refresh();
		driver.close();
		
		
		
	}
}
