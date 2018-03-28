import java.util.Scanner;

public class PrimeNumbers {
	public static void main (String args[])

	{
		System.out.println("Enter Number");
	Scanner sc =new Scanner(System.in);
	int n= sc.nextInt();
	int count=0;
	if(n<=1)
	{
		System.out.println("Enter valid number");
	}
	else
	{
	for(int i=2;i<n;i++)
	{
		if(n%i==0)
		{
			count++;
			break;
		}
	}
		if(count>0)
		{
			System.out.println("Not a prime number");
		}
		else
		{
			System.out.println("Prime number");
		}
	}
	}
}
