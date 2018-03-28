import java.util.Scanner;

public class ReverseNumber {
	public static void main (String args[])
	{
		Scanner sc=new Scanner(System.in);
		int a=sc.nextInt();
		int reverse=0;
		while(a!=0)
		{
	
			  reverse=reverse*10;
			reverse=reverse+ a%10;
			a=a/10;
			
		}
	}

}
