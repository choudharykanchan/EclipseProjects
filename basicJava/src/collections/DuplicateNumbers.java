package collections;
import java.util.ArrayList;
import java.util.List;
public class DuplicateNumbers {

	public static void main(String[] args) {
		DuplicateNumbers d=new DuplicateNumbers();
		List<Integer> value=d.getList(8);
		value.add(29);
		int result=d.findDuplicateNumber(value);
		System.out.println(result);

	}
public int getSum(List<Integer> sum)
{
	int result=0;
	for(int i:sum)
	{
		result+=i;
	}
	return result;
}

public List<Integer> getList(int n)
{ 
	List<Integer> values=new ArrayList<>();
	for(int i=1;i<=n;i++)
	{
		values.add(i);
	}
	return values;
}

public int findDuplicateNumber(List<Integer> values)
{
	int total=getSum(values);
	int highestNumber=values.size()-1;
	int duplicateNumber=total-(highestNumber*(highestNumber+1)/2);
	return duplicateNumber;
}
}
