
public class DecimalToBinary {

	public static void main(String[] args) {
		DecimalToBinary db=new DecimalToBinary();
		db.convertDecimalToBinary(25);
		

	}
	public void convertDecimalToBinary(int n)
	{
		int[] binary=new int[25];
		int i=0;
		while(n>0)
		{
			 binary[i]=n%2;
			 n=n/2;
			 i++;
			
		}
		for(int j=i;j>=0;j--)
		{
			System.out.print(binary[j]);
		}
	}

}
