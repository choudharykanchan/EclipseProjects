
public class DuplicateNumbers {
	public static void main(String args[])
	{
	int arr[]={4,6,2,4,6};
	System.out.println("Array before sorting");
	for(int i=0;i<arr.length;i++)
	{
		System.out.print(arr[i]);
	}
	System.out.println("Duplicate values are");
	duplicateNumbers(arr);
	
		
	}
	public static void duplicateNumbers(int arr[])
	{
		for(int i=0;i<arr.length;i++)
		{
			for(int j=i+1;j<arr.length;j++)
			{
				if(arr[i]==arr[j])
				{
					System.out.println(arr[i]);
					//System.out.println("index "+i+ "value"+arr[i]+"and"+ "index "+j+ "value"+arr[j]+"are same");
				}
			}
		}
	}

}
