
public class FabonacciSeries {
	static int f0=0;
	static int f1=1;
	static int f2;

	
public static void fabonacci(int n)
{
if(n>1)
{
f2=f0+f1;
System.out.print(f2+" ");
f0=f1;
f1=f2;
fabonacci(n-1);
}

	
}
public static void main (String args[])
{
	System.out.print(f0+ " "+f1+ " ");
	fabonacci(15);
}
}


