package learnSelenium;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class PopupAndAlert {
	public static void main (String args[])
	{
		WebDriver driver= new FirefoxDriver();
		driver.get("http://toolsqa.wpengine.com/automation-practice-switch-windows/");
		
		String windowHandler = driver.getWindowHandle();
	    System.out.println(windowHandler);	
	    driver.findElement(By.id("alert")).click();
	    
	    Alert alert= driver.switchTo().alert();
	    alert.accept();
	    driver.close();
	    driver.quit();
	    
	    
	    
	}
}
