package impTopics;

public class MultiThreadingUsingThreadClass extends Thread {
	
	public  static void main(String args[])
	{
		MultiThreadingUsingThreadClass mt1=new MultiThreadingUsingThreadClass();
		MultiThreadingUsingThreadClass mt2=new MultiThreadingUsingThreadClass();
		mt1.start();
		mt2.start();
		
		
	}
public void run()
{
	for(int i=0;i<10;i++)
	{
		System.out.println(i);
	}
	try {
		Thread.sleep(400);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}

