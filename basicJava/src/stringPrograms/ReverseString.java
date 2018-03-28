package stringPrograms;

import java.util.Scanner;

public class ReverseString {
	public static void main(String args[])
	{
		System.out.println("Enter a String");
		Scanner sc=new Scanner(System.in);
		String s= sc.next();
		String n="";
		System.out.println(s.length());
		for(int i=s.length();i>0;i--)
		{
		 n= n+s.charAt(i-1);
		
		}
		System.out.println("Reverse string is " + n);
	}

}
