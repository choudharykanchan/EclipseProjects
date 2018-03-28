package learnSelenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class IFrameElements {
	public static void main (String args[])
	{
		WebDriver driver= new FirefoxDriver();
		driver.get("http://toolsqa.wpengine.com/iframe-practice-page/");
		driver.switchTo().frame("IF1");
		driver.findElement(By.name("firstname")).sendKeys("ABC");
		driver.switchTo().defaultContent();
		driver.switchTo().frame("IF2");
		
		//driver.findElement(By.xpath("//*[@id='post-9']/div/div[1]/div/p[1]/a/img']")).click();
		driver.close();
		driver.quit();
	
}
}