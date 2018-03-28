import java.util.Scanner;

public class FindMiddleIndex {
	public static void main(String args[])
	{
		FindMiddleIndex m=new FindMiddleIndex();
		int[] arr1=m.takeArrayInput();
		m.findMiddleIndex(arr1);
		
		
	}
public int[] takeArrayInput()
{
	
	System.out.println("Enter size of array");
	Scanner sc=new Scanner(System.in);
	int n=sc.nextInt();
	int[] arr=new int[n];
	for(int i=0;i<n;i++)
	{
		System.out.println("Enter array value");
		arr[i]=sc.nextInt();
	}
	return arr;
}
public void findMiddleIndex(int[] arr)
{
	int startIndex=0;
	int EndIndex=arr.length-1;
	int sumLeft=0;
	int sumRight=0;
	while(startIndex<=EndIndex)
	{
	if(sumLeft>sumRight)
	{
		sumRight=sumRight+arr[EndIndex];
		EndIndex--;
	}
	else{
		sumLeft+=arr[startIndex];
		startIndex++;
	}
	}
	//System.out.println("StartIndex "+startIndex+" EndIndex "+ EndIndex+" LeftSum "+ sumLeft+ " RightSum "+ sumRight);
//	System.out.println("Hello");
	if(startIndex>EndIndex)
	{
	if(sumLeft==sumRight)
	{
		
		System.out.println("Middle Index is " + EndIndex); 
		//break;
	}
	else{
		System.out.println("Pass a valid array");
	}
	
	}
	}

}


