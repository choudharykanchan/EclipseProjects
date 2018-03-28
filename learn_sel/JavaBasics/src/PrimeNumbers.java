import java.util.Scanner;

public class PrimeNumbers {
	public static void main (String args[])
	{
		Scanner scn= new Scanner(System.in);
		System.out.println("Please enter the number");
		int number= scn.nextInt();
		int count=0;
		for(int i=2;i<=number;i++)
		{
			count=0;
			for(int j=2;j<=i/2;j++)
			{
				if(i%j==0)
					count++;
				break;				
			}
			if(count==0)
				System.out.println(i);
		}
	
		
		
	}

}
