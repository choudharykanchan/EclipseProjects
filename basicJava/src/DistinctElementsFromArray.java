
public class DistinctElementsFromArray {
	public static void main(String[] args) {
		DistinctElementsFromArray d=new DistinctElementsFromArray();
		int[] arr={3,4,6,3,5,7,3,4,6};
		d.distinctElements(arr);
				
		
	}

	public void distinctElements(int[] arr)
	{
		int count=0;
	
		for(int i=0;i<arr.length;i++)
		{	boolean b=false;
			for(int j=0;j<i;j++)
			{
				if (arr[i]==arr[j])
						{
					b=true;
					break;
						}
			}
			if(b==false)
			{
				System.out.println( arr[i] );
			}
		}
	}
}
