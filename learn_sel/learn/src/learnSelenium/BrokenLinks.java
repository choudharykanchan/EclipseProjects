package learnSelenium;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
public class BrokenLinks {
	public static List allLinks(WebDriver driver )
	
	{
		List<WebElement> elementlist= driver.findElements(By.tagName("a"));
		elementlist.addAll(driver.findElements(By.tagName("img")));
		List<WebElement> finalList= new ArrayList();
		for(WebElement element:elementlist)	
		{
			if (element.getAttribute("href")!=null)
				finalList.add(element);
		}
		return finalList;
	}
	public static String brokenList(URL url)
	{
		HttpURLConnection connection;
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			String response;
			response= connection.getResponseMessage();
			connection.disconnect();
			return response;
		} 
		catch(Exception e)
		{
			return e.getMessage();
		}
		
		
	}
	public static void main(String args[]) throws MalformedURLException
	{
		WebDriver driver= new FirefoxDriver();
		driver.get("http://toolsqa.wpengine.com/automation-practice-switch-windows/");
		List<WebElement> allelements = allLinks(driver);
		System.out.println("Total number of elements found " + allelements.size());
		for( WebElement element : allelements)
		 System.out.println("URL: " + element.getAttribute("href")+ " returned " + brokenList(new URL(element.getAttribute("href"))));
		driver.quit();
	}
	  
}
