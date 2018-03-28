import java.io.IOException;
import java.util.Scanner;

public class Palindrome {
	public static void main (String args[]) throws IOException
	{
	System.out.println("Enter a number");
	Scanner sc=new Scanner(System.in);
	int n= sc.nextInt();
	System.out.println("Entered number is"+n);
	int temp=n,r,newNumber=0;
	while(n>0)
	{
		r=n%10;
		newNumber=(newNumber*10)+r;
		n=n/10;
		
	}
	if(temp==newNumber)
	{
		System.out.println("Palindrome Number");
	}
	else
	{
		System.out.println("Not a Palindrome Number");
	}
}
}