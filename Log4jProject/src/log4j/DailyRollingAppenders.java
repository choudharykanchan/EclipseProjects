package log4j;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class DailyRollingAppenders {
static Logger log= Logger.getLogger(RollOutLoggers.class.getName());
	
	public static void main (String args[])
	{
		PropertyConfigurator.configure("D:/Automation Workspace/Log4jProject/src/log4j/PropertiesFiles/DailyRolling.properties");
		 
		log.debug("Debug message1 ");
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 log.debug("Debug message2 ");
		
	 System.out.println("Chal gya ");
	}
}
