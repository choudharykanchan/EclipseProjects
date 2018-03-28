
public class RemoveDuplicatesFromSortedArray {
	public static void main(String[] args) {
		RemoveDuplicatesFromSortedArray r=new RemoveDuplicatesFromSortedArray();
		int[] arr={1,2,11,4,7,2,0,3,5,3,5,6,7,9};
		int[] newArray=r.uniqueElements(arr);
		for(int i=0;i<newArray.length;i++)
		{
			System.out.print(newArray[i]+" ");
		}
			
		
	}

	public boolean isDistinct(int[] arr,int j)
	{
		boolean distinct=true;
		for (int i=0;i<j;i++)
		{
			if(arr[i]==arr[j])
			{
				distinct=false;
				break;
			}
		}
	return distinct;
	}
	public int numberOfdistinctElements(int[] arr)
	{
		int count=0,j=0;
		for(int i=0;i<arr.length;i++,j++)
		{
			if(isDistinct(arr,j)==true)
			{
				count++;
			}
		}
		return count;
	}
	public int[] uniqueElements(int arr[] )
	{ 
		int n=numberOfdistinctElements(arr);
		int j=0;int k=0;
		int[] newArray=new int[n];
		for(int i=0;i<arr.length;i++,j++)
		{
			if(isDistinct(arr,j)==true)
			{
				newArray[k]=arr[j];
				k++;
			}
		}
		return newArray;
	}
}
