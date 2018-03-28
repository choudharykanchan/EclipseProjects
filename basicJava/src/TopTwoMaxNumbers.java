
public class TopTwoMaxNumbers {
public static void main(String[] args) {
	TopTwoMaxNumbers mn=new TopTwoMaxNumbers();
	int[] arr={15,4,2,7,4,3,8,0};
	mn.topTwoMaxNumbers(arr);
	
}
public void topTwoMaxNumbers(int[] arr )
{
	int maxFh=arr[0];
	int maxSh=arr[1];
	int temp;
	if(maxSh>maxFh)
	{ temp=maxFh;
		maxFh=maxSh;
		maxSh=temp;
	}
	for(int i=2;i<arr.length;i++)
	{
		if(arr[i]>maxFh)
		{
			maxSh=maxFh;
			maxFh=arr[i];
		}
		else
		{
			if(arr[i]>maxSh)
			{
				maxSh=arr[i];
			}
		}
	
	}
	System.out.println("First Highest Number "+ maxFh +" Second Highest Number "+ maxSh);
}

}
