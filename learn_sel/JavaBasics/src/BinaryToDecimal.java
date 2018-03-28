import java.util.Scanner;

import org.omg.CORBA.PUBLIC_MEMBER;

public class BinaryToDecimal {
	public static void main(String args[]){
		
			Scanner scn=new Scanner(System.in);
			 System.out.println("Please enter the binary number");
		int binaryNumber=scn.nextInt();
		
	
	int decimal = 0;
	int power = 0;
	if (binaryNumber==0)
	{
System.out.println("zero is a binary number");
	}
	else
	{
		while(binaryNumber>0)
		{
	int temp=binaryNumber%10;
	//decimal= (int) (decimal+temp*Math.pow(2, power));
	  decimal += temp*Math.pow(2, power);
binaryNumber=binaryNumber/10;
	power++;
	
	}}
	 System.out.println("Binary="+binaryNumber+" Decimal="+decimal); 
}
}

