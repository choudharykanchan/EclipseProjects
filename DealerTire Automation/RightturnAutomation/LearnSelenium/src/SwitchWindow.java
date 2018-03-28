import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;



import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;


public class SwitchWindow {
	public static void main(String args[])
	{
		WebDriver driver= new FirefoxDriver();
		String URL="http://www.toolsqa.com/automation-practice-switch-windows/";
		driver.get(URL);
	    WebElement NewWindow=driver.findElement(By.id("button1"));
	    NewWindow.click();
	    String handle=driver.getWindowHandle();
       //Set<String> handles=new Set<String>();
       Set<String> handles = driver.getWindowHandles();
       System.out.println("******************** Handles*********"+handles);
      // driver.switchTo().window((String)handles.toArray()[1]);
    		 //  driver.switchTo().window((String)handles.toArray()[0]);
       for (String handle1 : driver.getWindowHandles()) {
    	   
       	System.out.println(handle1);

       	driver.switchTo().window(handle1);

       	}
       
        
        
 
	    
	    
	
	}
	

}
