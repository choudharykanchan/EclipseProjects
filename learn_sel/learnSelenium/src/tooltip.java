package headless;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class tooltip {
	public static void main (String args[])

	{
		WebDriver driver= new FirefoxDriver();
		driver.get("http://toolsqa.com/automation-practice-switch-windows/");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		String handleName= driver.getWindowHandle();
		System.out.println(handleName);
		driver.findElement(By.id("button1")).click();
		Set <String>  HandlesNames=  driver.getWindowHandles();
		java.util.Iterator<String> itr = HandlesNames.iterator();
		while(itr.hasNext()) {
			Object element = itr.next();
			System.out.print(element + " ");
		}
		driver.quit();
	}}