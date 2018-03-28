package learnSelenium;

import org.openqa.selenium.ie.InternetExplorerDriver;

public class IeBrowser {
	public static void main(String args[])
	{
		System.setProperty("webdriver.ie.driver", "C:\\Users\\thinksysuser\\Desktop\\IEDriverServer.exe");
		
		InternetExplorerDriver  driver = new InternetExplorerDriver();
		 
		
		driver.get("http://facebook.com");
		driver.quit();
		
	}
	

}
