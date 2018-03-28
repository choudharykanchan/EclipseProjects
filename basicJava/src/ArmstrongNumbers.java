import java.util.Scanner;

public class ArmstrongNumbers {
	public static void main (String args[])
	{
		System.out.println("Enter a number");
		Scanner sc=new Scanner(System.in);
		int n= sc.nextInt();
		int temp=n;
		System.out.println("Entered number is "+n);
		
		int r=0,s=0;
		if(n<=0)
		{
			System.out.println("Enter a valid number");
		}
		else
		{
			while(n>0)
			{
			r=n%10;
			s=s+(r*r*r);
			n=n/10;
			}
		if(s==temp)
		{
			System.out.println("Armstrong Number");
		}
		else
		{
			System.out.println("Not a Armstrong Number");
		}
	}
	}
}
