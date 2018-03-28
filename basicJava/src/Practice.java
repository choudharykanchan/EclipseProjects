
public class Practice {

	public int function()
	{
		try
		{
			return 0;
		
		}
		catch (Exception e)
		{
			return 1;
		}
		finally
		{
			return 2;
		}
	}
	public static void main(String[] args) {
		Practice p= new Practice();
		System.out.println(p.function());
	}
}
