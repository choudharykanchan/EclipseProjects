package learnSelenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Exercise1 {
	public static void main (String args[])
	{
		WebDriver driver = new FirefoxDriver();
		 
		String URL= "http://demoqa.com/";
		driver.get(URL);
		
		String PageTitle= driver.getTitle();
		System.out.println("Page title is "+PageTitle + "Title length is " +PageTitle.length());
		String AppearedURL=driver.getCurrentUrl();
		System.out.println( AppearedURL);
		if(AppearedURL.equals(URL))
		{
			System.out.println("Yes correct page is opened"+ AppearedURL );
			
		}
		else{
			System.out.println("Incorrect page" );
			
		}
		String PageSource= driver.getPageSource();
		System.out.println("Page source is" +PageSource+ "Page source length is"+PageSource.length());
		driver.close();
		
	}
	

}
