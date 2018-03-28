package log4j;

import org.apache.log4j.Logger;

public class Client {
	static Logger log=Logger.getLogger(Client.class.getName());
	
	public static void main(String args[])
	{
		log.debug("This is debug message");
		log.error("This is eror message");
	}

}
