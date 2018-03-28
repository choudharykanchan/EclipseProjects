
public class primeNumbersInBetween {
	public static void main(String args[])
	{
		numbers(2,20);
	}
public static void numbers(int x,int y)
{
	int count;
	for(int i=x;i<=y;i++)
	{
		count=0;
		for(int j=2;j<i;j++)
		{
			//System.out.println(i+" "+ j);
			if(i%j==0)
			{
				count++;
				
				break;
			}
			
		
		}
		if(count<1)
		{
			System.out.println(i);
		}
	}
}
}
