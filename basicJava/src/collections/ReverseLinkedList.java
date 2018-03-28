package collections;

import java.util.ArrayList;
import java.util.List;

public class ReverseLinkedList {
	public static void main (String args[])
	{
		List<String> originalList=new ArrayList();
		originalList.add("a");
		originalList.add("b");
		originalList.add("c");
		List<String> reverseFinal=reverseLinkedList(originalList);
		for(int i=0;i<reverseFinal.size();i++)
		{
			System.out.println(reverseFinal.get(i).toString());
		}
	}

	public static List<String> reverseLinkedList(List<String> values)
	{
		List<String> originalList=values;
		List<String> reverse=new ArrayList<>();
		for(int i=originalList.size()-1;i>=0;i--)
		{
			reverse.add(originalList.get(i));
		}
		return reverse;
	}
}
