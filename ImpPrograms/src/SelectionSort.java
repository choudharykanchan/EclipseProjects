import org.apache.commons.lang.ArrayUtils;

public class SelectionSort {
	public static int[] selectionSort(int arr[])
	{
		int min=0;
		int temp=0;
		int n=arr.length;
		int arr2[]=new int[n];
		for(int k=0;k<n;k++)
		{
		for(int i=0,j=1;j<arr.length-1;j++)
		{
			if(arr[i]<arr[j])
			{
				min=arr[i];
				temp=i;
			}
			else
			{
				min=arr[j];
				temp=j;
			}
			
		}
		arr2[k]=min;
		arr = ArrayUtils.remove(arr, temp);
		System.out.println(min);
		System.out.println(arr[k]);
		}	
	return arr2;
	}
	public static void main(String args[])
	{
		int arr[]={4,3,2,1};
		System.out.println("Array before sorting");
		for(int i=0;i<arr.length;i++)
		{
			System.out.print(arr[i]);
		}
		
		int arr1[]=selectionSort(arr);
		System.out.println("Array after sorting");
		for(int i=0;i<arr1.length;i++)
		{
			System.out.print(arr1[i]);
		}
	}
	}

