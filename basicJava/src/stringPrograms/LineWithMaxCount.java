package stringPrograms;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LineWithMaxCount {
	FileReader fr;
	BufferedReader br;
	public static void main(String[] args) throws IOException {
		LineWithMaxCount l=new LineWithMaxCount();
		l.lineWithMaxCount("C:/Users/thinksysuser/Desktop/Example.txt");

	}
	public void lineWithMaxCount(String filePath) throws IOException
	{int count=0;
		String line = null;
		int maxcount=0;
		List<String> lines=new ArrayList<String>();
		try {
		 fr=new FileReader(filePath);
			 br=new BufferedReader(fr);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
			while((line=br.readLine())!=null)
			{
				String[] linearr=line.split(" ");
				count=linearr.length;
				if(count>maxcount)
				{
					lines.clear();
					lines.add(line);
				}
				if(count==maxcount)
				{
					lines.add(line);
				}
				
			}
	 
		for (int i=0;i<lines.size();i++)
		{
			System.out.println(lines.get(i));
			System.out.println(count);
		}
	}

}
