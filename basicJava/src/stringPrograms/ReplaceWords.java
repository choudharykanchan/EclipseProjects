package stringPrograms;

import java.util.Scanner;

public class ReplaceWords {
	public static void main(String args[])
	{
		String from,to,rs;
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter string value");
		String s =sc.nextLine();
		System.out.println("Enter value to be replaced");
		from=sc.nextLine();
		System.out.println("Enter value replaced to");
		to=sc.nextLine();
		rs=replace(s,from,to);
		System.out.println(s);
		System.out.println(rs);
		
	}
	public static String replace(String s,String from,String to)
	{
		StringBuffer replacedString=new StringBuffer();
		
		 String value[]=s.split("\\s");
		 for(int i=0;i<value.length;i++)
		 {
			 
			if(value[i].equalsIgnoreCase(from))
			{
				value[i]=to;
				
			}
		 }
		 for (int i=0;i<value.length;i++)
		 {
			 replacedString.append(value[i]+" ");
		 }
		 return replacedString.toString();
	}
}
