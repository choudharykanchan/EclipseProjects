
public class BinaryToDecimal {
	public static void main(String[] args) {
		BinaryToDecimal bd=new BinaryToDecimal();
		
		System.out.println(bd.binaryToDecimal(120101));
		
	}
public int binaryToDecimal(int n)
{
	int i=0,sum=0;
	while(n>0)
	{
		int r=n%10;
		if(r>1)
		{
			System.out.println("Not a binary number");
			break;
		}
		sum=(int) (sum+r*Math.pow(2, i));
		n=n/10;
		i++;
	}
	return sum;
}

}
