package DealerTireLinks.DealerTireLinks;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class LinksVerification {
	
	String excelPath="\\C:\\Users\\thinksysuser\\Desktop";
	String fileName="Duplicate 11-05.xlsx";
	String createfileName="DuplicateStaus1.xlsx";
	int start=2;
	int end=20;
	WebDriver driver;
	Utilities utl;
	String valueToWrite = "Value to written";
	
	
	@Before
	public void setup()
	{
		System.setProperty("webdriver.gecko.driver","C:/Users/thinksysuser/Desktop/Automation/geckodriver.exe");
		 driver = new FirefoxDriver();
		
		utl= new Utilities(driver,excelPath,fileName,start,end);
		 
	}
	@Test
	public void test() throws IOException, InvalidFormatException
	{
		
	
		Sample ob=new Sample();
		ReadDataFromExcel readExcelFile =new ReadDataFromExcel();
		List GivenURLs= readExcelFile.readExcelGivenURLs(excelPath, fileName, start, end);
		List FutureURLs= readExcelFile.readExcelExpectedURLs(excelPath, fileName, start, end);
		ob=utl.getCurrentURL();
		//utl.verifyURL(ob.currentURLs);
		List statusMessage= ob.statusMessage;
	    System.out.println(statusMessage);
	    List URLStatus = utl.verifyURL(ob.currentURLs);
	   
        WriteDataToExcel objExcelFile = new WriteDataToExcel();

	        
		
	        objExcelFile.writeExcel(excelPath,createfileName,valueToWrite, start, end,GivenURLs,FutureURLs,URLStatus,statusMessage);
	     //  System.out.println("Hello2");
	}
@After
public void close()
{
	 driver.close();
	 System.exit(0);
}

}
