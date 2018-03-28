package log4j;

import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Client {
	static Logger log=Logger.getLogger(Client.class.getName());
	
	public static void main(String args[])
	{
	
		PropertyConfigurator.configure("D:/Automation Workspace/Log4jProject/src/log4j/Log4j.properties");
		log.debug("This is debug message");
		log.error("This is error message");
		System.out.println("Done Succeesfully");
	}

}
