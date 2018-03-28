package stringPrograms;

import java.util.Scanner;

public class ReplaceString {

	public static void main(String args[])
	{
		char from='a',to='b';
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter string value");
		String s =sc.nextLine();
		String rs=replace(s,from,to);
		System.out.println(s);
		System.out.println(rs);
		
	}
	public static String replace(String s,char from,char to)
	{
		StringBuffer newString=new StringBuffer();
		if(s.length()>0)
		{
			for(int i=0;i<s.length();i++)
			{
				if(s.charAt(i)==from)
				{
					newString.append(to);
				}
				else
				{
					newString.append(s.charAt(i));
				}
			}
			
		}
		else
		{
			System.out.println("Please enter the valid string");
		}
		return newString.toString();
	}
}
