package stringPrograms;

public class ReverseStringUsingRecurssion {
String rev="";
	public static void main(String args[])
	{
		ReverseStringUsingRecurssion r =new ReverseStringUsingRecurssion();
		String rev=r.reverseString("Hello");
		System.out.println(rev);
		
	}
	
	public String reverseString(String str)
	
	{
	
	if(str.length()==0)
	{
		return rev;
	}
	else 
	{
		rev=rev+str.charAt(str.length()-1);
		
	}
	
	reverseString(str.substring(0, str.length()-1));
	return rev;
	}
	
}
