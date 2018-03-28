import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class verifyLowerUpperorDigit {
	public static void main(String args[]) throws IOException
	{
		 
	BufferedReader bf=new BufferedReader(new InputStreamReader(System.in));
	char s=(char)bf.read();
		if(s>=97&&s<=123)
		{
			System.out.println("Lowercase");
		}
		else if(s>=65&&s<=96)
		{
			System.out.println("Uppercase");
		}
		else if(s>=48&&s<=57)
		{
			System.out.println("Digit");
		}
		
	}
}
