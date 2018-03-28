import java.util.Scanner;

public class CheckPrimeNumber {
public static void main(String args[]){
	Scanner scn=new Scanner(System.in);
	 System.out.println("Please enter the number");
	int number=scn.nextInt();
	int count=0;
	for (int i=2;i<=number/2;i++)
	{
		if (number%i==0)
		{
		count++;
		break;
		}
	}
	if(count==0)
	{
	System.out.println("This number is a Prime number");
	}
	else{
		System.out.println("This number is not a Prime number");
	}

}
}
