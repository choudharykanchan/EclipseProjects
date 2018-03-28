package impTopics;

public class MultiThreadingUsingRunnableInterface implements Runnable {
	public static void main(String args[])
	{
		MultiThreadingUsingRunnableInterface mr1=new MultiThreadingUsingRunnableInterface();
		MultiThreadingUsingRunnableInterface mr2=new MultiThreadingUsingRunnableInterface();
		Thread t1=new Thread(mr1);
		Thread t2=new Thread(mr2);
		t1.start();
		t2.start();
	}
public void run()
{
	for(int i=0;i<1000;i++)
	{
		System.out.println(i);
		
	}
}
}
