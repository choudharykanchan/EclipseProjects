
public class BubbleSort {
	
	public static int[] bubblesort(int arr[])
	{
		int n=arr.length;
	
		for(int i=0;i<=n-1;i++)
		{
			for(int j=0;j<n-1;j++)
			{
				
				if(arr[j]>arr[j+1])
				{
				
					int temp=arr[j];
					arr[j]=arr[j+1];
					arr[j+1]=temp;
					
				}
			}
		}
		return arr;
	}
public static void main(String args[])
{
	int arr[]={4,6,2,1,7,8,10,12};
	System.out.println("Array before sorting");
	for(int i=0;i<arr.length;i++)
	{
		System.out.print(arr[i]);
	}
	
	int arr1[]=bubblesort(arr);
	System.out.println("Array after sorting");
	for(int i=0;i<arr1.length;i++)
	{
		System.out.print(arr1[i]);
	}
}
}
