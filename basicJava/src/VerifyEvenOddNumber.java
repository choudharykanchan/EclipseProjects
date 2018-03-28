import java.util.Scanner;

public class VerifyEvenOddNumber {
	public static void main(String args[])
	{
		System.out.println("Enter number");
		Scanner scn=new Scanner(System.in);
		int a=scn.nextInt();
		
		if(a%2==0){
			System.out.println("Even number");
		}
		else
		{
			System.out.println("Odd number");
		}
	}

}
