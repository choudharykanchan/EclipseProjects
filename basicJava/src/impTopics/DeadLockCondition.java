package impTopics;

public class DeadLockCondition  {
	
	public static void main(String args[])
	{
 String h1="Hello1";String h2="hello2";
Thread t1=new Thread(){
	

	public   void  run()
	{
		synchronized (h1)
		{
			System.out.println("Thread 1: locked resource 1");
			
		
		try { Thread.sleep(1000);} catch (Exception e) {}
		synchronized (h2)
		{
			System.out.println("Thread 1: locked resource 2");
		
		}}
	}
};
Thread t2=new Thread(){
	public  void  run()
	{
	synchronized (h2) {
		System.out.println("Thread 2: locked resource 2");
		
	
	try { Thread.sleep(400);} catch (Exception e) {}
	synchronized (h1) {
		System.out.println("Thread 2: locked resource 1");
		
	}
	}}
};
	
	
		t1.start();
		t2.start();
	}
	
}
