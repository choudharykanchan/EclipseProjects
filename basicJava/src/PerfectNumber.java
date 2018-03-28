
public class PerfectNumber {
	public static void main(String args[])
	{
		PerfectNumber p=new PerfectNumber();
		p.isPerfectNumber(6);
	}
public void isPerfectNumber(int n)
{
	int sum=0;
	for(int i=1;i<n;i++)
	{
		if(n%i==0)
		{
			sum=sum+i;
			//System.out.println(sum);
		}
	}
	if(sum==n)
	{
		System.out.println("Perfect number");
	}
	else
	{
		System.out.println("Not a perfect Number");
	}
}
}
