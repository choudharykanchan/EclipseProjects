package learnSelenium;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SwitchCommand {
	public static void main (String args[])

	{
		WebDriver driver= new FirefoxDriver();
		driver.get("http://toolsqa.com/automation-practice-switch-windows/");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		String handleName=	driver.getWindowHandle();
		System.out.println(handleName);
		driver.findElement(By.id("button1")).click();
		Set HandlesNames=  driver.getWindowHandles();
		
			//System.out.println(HandlesNames);
			//for (String Handle: driver.getWindowHandles())
			//{
				
				//System.out.println(Handle);
				//driver.switchTo().window(Handle);
				
	//}Ok// Store the current window handle String winHandleBefore = driver.getWindowHandle(); // Perform the click operation that opens new window // Switch to new window opened for(String winHandle : driver.getWindowHandles()){    driver.switchTo().window(winHandle); } // Perform the actions on new window // Close the new window, if that window no more required driver.close(); // Switch back to original browser (first window) 
		
		java.util.Iterator itr = HandlesNames.iterator();
	
		while(itr.hasNext())
		{
		System.out.println(itr.next());
		}
		driver.quit();
}}
