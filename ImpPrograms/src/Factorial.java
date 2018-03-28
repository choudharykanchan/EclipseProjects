import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Factorial {
	public static void main (String args[]) throws IOException
	{
		System.out.println("Enter a number");
		Scanner sc=new Scanner(System.in);
		int n= sc.nextInt();
		System.out.println("Entered number is"+n);
		int fact=1;
		if (n<=0)
		{
			System.out.println("Entered number is 0 or less then 0");
		}
		else
		{
		for(int i=1;i<=n&&i>0;i++)
		{
			fact=fact*i;
		}
		System.out.println("Factorial is"+ fact);
	}

}
}