package impTopics;

import org.omg.CORBA.PUBLIC_MEMBER;

public class DeadLock extends Thread {
final String h1="Kanchan"; final String h2="Choudhary";static DeadLock d1;static DeadLock d2;
	public synchronized void method1()
	{
		
		{
			synchronized (h1) {
				System.out.println("Thread1: Hold h1");
				
				synchronized (h2) {
					System.out.println("Thread1: Hold h2");
				}
			}
		}
	}
		public synchronized void method2()
		{
			synchronized (h2) {
				System.out.println("Thread2:Hold h2");
				
				synchronized (h1) {
					System.out.println("Thread2:Hold h1");
				}
			}
		}
		
		 public static void main (String args[])
		 {
			  d1=new DeadLock();
			  d2=new DeadLock();
			 d1.start();
			 d2.start();
		 }
		 public void run()
		 {
			 d1.method1();
			 d2.method2();
		 }
}
		
	
	
	

