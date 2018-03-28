
public class SumOfDigitUsingRecurssion {
	int sum=0;
	public static void main(String[] args) {
		SumOfDigitUsingRecurssion r=new SumOfDigitUsingRecurssion();
		System.out.println(r.sumOfDigits(345));
		
	}
	public int sumOfDigits(int n)
	{
		
		if(n==0)
		{
			return sum;
		}
		else
		{
			int r=n%10;
			sum=sum+r;
			n=n/10;
			
		}
		 sumOfDigits(n);
		 return sum;
	}

}
