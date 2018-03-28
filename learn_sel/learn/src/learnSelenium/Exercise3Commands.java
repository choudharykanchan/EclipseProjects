package learnSelenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebElement;




public class Exercise3Commands {
	public static void main (String args[])
	{
		WebDriver driver= new FirefoxDriver();
		driver.get("http://toolsqa.com/automation-practice-form/");
		driver.findElement(By.name("firstname")).sendKeys("Kanchan");
		driver.findElement(By.name("lastname")).sendKeys("Choudhary");
		driver.findElement(By.id("submit")).click();
		
		driver.close();
	}
}
