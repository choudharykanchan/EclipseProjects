package log4j;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class RollOutLoggers {
	static Logger log= Logger.getLogger(RollOutLoggers.class.getName());
	
	public static void main (String args[])
	{
		PropertyConfigurator.configure("D:/Automation Workspace/Log4jProject/src/log4j/PropertiesFiles/Rollout.properties");
	 for(int i=1;i<=120;i++)
	 {
		 log.debug("Debug message " +i);
	 }
	}

}
