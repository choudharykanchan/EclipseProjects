package learnSelenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebElement;




public class FindWebElement {
	public static void main (String args[])
	{
		WebDriver driver= new FirefoxDriver();
		driver.get("http://toolsqa.com/automation-practice-form/");
		driver.findElement(By.partialLinkText("Partial")).click();
		System.out.println("Partial Link Test Pass");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		String Name=driver.findElement(By.tagName("button")).toString();
		System.out.println(Name);
		driver.findElement(By.linkText("Link Test")).click();
		System.out.println("Link Test Pass");
		driver.close();
		
		
	}
}
