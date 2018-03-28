import java.util.Scanner;

public class FloydsTriangle {
	public static void main (String args[])
	{
	System.out.println("Enter number of rows");
	Scanner sc=new Scanner(System.in);
	int n= sc.nextInt();
	System.out.println("Entered number is"+n);
int k=1;
for (int i=1;i<=n;i++)
{
	
	for(int j=1;j<=i;j++)
	{
		System.out.print(k+" ");
		k++;
		
		
	}
	System.out.println(" ");
	
	
}
}
}