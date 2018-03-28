
public class StringToInt {
	public static void main(String[] args) {
		StringToInt s=new StringToInt();
		s.asciiValue("678");
		
	}
public void asciiValue(String str)
{
	char zero='0';
	int sum=0;
	int zeroAsciiValue=zero;
	char[] arr=str.toCharArray();
	for(char ch:arr)
	{
		int asciiValue=ch;
		int value=asciiValue-zeroAsciiValue;
		sum=sum*10+value;
		
	}
	System.out.println(sum);
}
}
