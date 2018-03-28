package DealerTireLinks.DealerTireLinks;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpRequest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Utilities {
	WebDriver driver;
	String excelPath;
	String fileName;
	int start;
	int end;
	ReadDataFromExcel objExcelFile = new ReadDataFromExcel();
	
	//Utilities utl1=new Utilities( driver,excelPath, fileName, start,end);
	
	public Utilities(WebDriver driver,String excelPath,String fileName,int start,int end){
		
		this.driver=driver;
		this.excelPath=excelPath;
		this.fileName=fileName;
		this.start=start;
		this.end=end;
		
	}
	
	public Sample getCurrentURL() throws IOException
	{
		
	Sample ob=new Sample();
	List<String> l2 =objExcelFile.readExcelGivenURLs(excelPath,fileName,start,end);
	
	int tempStart= start;
	for (int i=0;i < l2.size();i++,tempStart++)
	{
		//driver.get("https://www.facebook.com/");
		//System.out.print("Hello Java ");
	  String URL= l2.get(i).toString().toString();
	  System.out.println(tempStart+" " +URL);
	driver.get(URL);
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);	
	String temp=driver.getCurrentUrl();
	
	System.out.println(temp);
	
	String Message= getStatus(tempStart, temp);
	ob.statusMessage.add(Message);
	ob.currentURLs.add(temp);
	
	//System.out.println("Hello");
	}
	return ob;
	
	}
	
	public List verifyURL(List<String> currentURLs)
	{
	List<String> l3 = null;
	ArrayList<String> URLsStatus= new ArrayList();
	try {
		l3 = objExcelFile.readExcelExpectedURLs(excelPath,fileName,start,end);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	String Message=null;
	for(int i=0;i<l3.size();i++)
	{
		String temp=l3.get(i);
		
		System.out.println("ExpectedURL"+l3.get(i));
		System.out.println("CurrentURL"+currentURLs.get(i));
		
		if(l3.get(i).equalsIgnoreCase(currentURLs.get(i)))
		{
			Message="URL matched";
			System.out.println(Message);
		}
		else
		{
			Message="URL MisMatched";
			System.out.println(Message);
		}
		URLsStatus.add(Message);
	}
	
	System.out.println("Finish");
return URLsStatus;
}
	public String getStatus(int start,String URL) throws IOException
	{
		URL url = new URL(URL);
		//HttpRequest request = new HttpRequest(url);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int code = connection.getResponseCode();
        //System.out.println(code);
		//Get the response code of the URL
		//int response_code = request.getResponseCode();
 String Message=null;
		//Check for 404:
		if(code ==200){
			Message="Correct page appears";
		    System.out.println(Message);
		}
		else
			{
			if(code==404){
				Message="404 - The content you are looking for is missing";
				System.out.println (Message);
			}
			if(code==400){
				Message="Blank Page appears";
				System.out.println (Message);
			}
				
			}
		return Message;
			}
	}

