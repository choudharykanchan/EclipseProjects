package learnSelenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Timings {
	public static void main (String arg[])
	{
		WebDriver driver= new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		
		driver.get("http://toolsqa.com/automation-practice-switch-windows/");
			driver.findElement(By.id("timingAlert")).click();

	        System.out.println("Timer JavaScript Alert is triggered but it is not yet opened");
			
			WebDriverWait wait= new WebDriverWait(driver,10);
			Alert myAlert = wait.until(ExpectedConditions.alertIsPresent());
			myAlert.accept();
			System.out.println("Alert Accepted");
			driver.close();
			
			
			
		
	}

}
