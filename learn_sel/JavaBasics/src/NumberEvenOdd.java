import java.util.Scanner;

public class NumberEvenOdd {
	public static void main(String args[])
	{
		Scanner scn= new Scanner(System.in);
		
		System.out.println("Please enter the number");
		int number= scn.nextInt();
		if(number%2==0)
		{
			System.out.println("Number is even number");
			
		}
		else
		{
			System.out.println("Number is odd number");
		}
		
		
	}
}
