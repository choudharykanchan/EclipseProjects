package log4j;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class UserAndDevLoggers {
static Logger log= Logger.getLogger(RollOutLoggers.class.getName());

	
	public static void main (String args[])
	{
		PropertyConfigurator.configure("D:/Automation Workspace/Log4jProject/src/log4j/PropertiesFiles/UserAndDev.properties");
		 
		log.debug("Debug message ");
		log.info("Info messagae");
		log.warn("Warn message");
		log.error("Error meassage");
		log.fatal("Fatal meassage");
		
	}

}
