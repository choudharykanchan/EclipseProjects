package learnSelenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Exercise2 {
public static void main(String args[])
{
	WebDriver driver= new FirefoxDriver();
	
	String URL= "http://demoqa.com/frames-and-windows/";
	driver.get(URL);
	driver.findElement(By.xpath(".//*[@id='tabs-1']/div/p/a")).click();
			driver.close();
	
	
}
}
