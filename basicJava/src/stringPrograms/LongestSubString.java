package stringPrograms;

import java.util.ArrayList;
import java.util.List;

public class LongestSubString {
	public static void main(String[] args) {
		LongestSubString l=new LongestSubString();
		List<String> str2=l.longestSubArray("abcabcbb");
		for(int i=0;i<str2.size();i++)
		{
			System.out.println(str2.get(i));	
		}
		//System.out.println(str);
		//System.out.println(l.longestSubArray("java_language_is_sweet"));
		//System.out.println(l.longestSubArray("java_java_java_java"));
		//System.out.println(l.longestSubArray("abcabcbb"));
		//System.out.println("java_language_is_sweet");
		
		
		
	}
	public int isDistinct(String str,int j)
	{ String str1=str.toLowerCase();
	System.out.println(str1);
		char[] arr=str1.toCharArray();
		int distinct =0;
		for(int i=0;i<j;i++)
		{
			System.out.println(arr[i]+" " +arr[j]);
			if(arr[i]==arr[j])
			{
				distinct=i;
				break;
			}
		}
		return distinct;
	}
	
	public List<String> longestSubArray(String str)
	{
		List<String> subArray=new ArrayList<String>();
		int maxcount=0;
		int count;
		String temp="";
		String subString;
		int k=0;
		int j=0;
		//String str1=str.substring(k);		
				for(int i=0;i<str.length();i++)
		{
					String str1=str.substring(i);
			for (j=0;j<str1.length();j++,i++)	
			{
				int distinct=isDistinct(str1, j);
			if(distinct==0)
			{
				temp=temp+str1.charAt(j);
				System.out.println("Value of j"+j);
				System.out.println("value of temp "+temp);
				
			
			}
			else
			{ k=distinct;
			count=temp.length();
				break;
			}
			}
			
				count=temp.length();
				System.out.println("lenght of count "+count);
				if (count>maxcount)
				{
					maxcount=count;
					subArray.clear();
					//System.out.println("Cleared array");
					subArray.add(temp);
					//System.out.println(temp);
					System.out.println(i);
					k=i;
					//i--;
					temp="";
					
					//str=str1;
				}
				else if(count==maxcount)
				{
					subArray.add(temp);
					temp="";
					
					k=i;
					//str=str1;
				}
				
			}
		
		
		return subArray;
	}
}