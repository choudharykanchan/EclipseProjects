package log4j;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class MultipleLoggers {
	static Logger log1=Logger.getLogger("log1");
	static Logger log2=Logger.getLogger("log2");
	public static void main(String args[])
	{
		PropertyConfigurator.configure("D:/Automation Workspace/Log4jProject/src/log4j/PropertiesFiles/MultipleLoggers.properties");
		
		log1.info("This is info message");
	//	log1.warn("This is warn message for log1");
		log2.warn("This is warn message");
		log2.info("This is warn message");
	}

}
