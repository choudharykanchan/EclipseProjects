
public class SingletonClass {

	String s="Hello World";
	private static SingletonClass obj=null;
	private SingletonClass()
	{
		
	}
	public static SingletonClass getInstance()
	{
		if(obj==null)
		{
			SingletonClass obj=new SingletonClass();
		}
		return obj;
	}
public static void main(String args[])
{
	
	
	
}
}
