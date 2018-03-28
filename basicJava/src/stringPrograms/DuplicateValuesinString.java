package stringPrograms;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class DuplicateValuesinString {

	public static void main(String[] args) {
		DuplicateValuesinString ds=new DuplicateValuesinString();
		ds.duplicateValuesString("HelloKanchan");
	

	}
	public void duplicateValuesString(String str)
	{  String str1=str.toLowerCase();
		char[] arr=str1.toCharArray();
		/*for(int i=0;i<arr.length;i++)
		{
			System.out.println(arr[i]);
		}*/
		Map<Character,Integer> map=new HashMap<Character,Integer>();
		for(char ch:arr)
		{
			if(map.containsKey(ch))
			{
				map.put(ch, map.get(ch)+1);
				//System.out.println(map.arr);
				}
	
		else
		{
			map.put(ch, 1);
		}
	} 
		for(Map.Entry<Character, Integer> m: map.entrySet())
		{
			System.out.println(m.getKey()+ "=="+ m.getValue());
		}
	}

}

