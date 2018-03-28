package stringPrograms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class DuplicateWords {
 public static void main(String args[])
 {
	 DuplicateWords w=new DuplicateWords();
	// File file =new File("C:/Users/thinksysuser/Desktop/Example.txt");
	 try {
		w.findDuplicates("C:/Users/thinksysuser/Desktop/Example.txt");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 }
	
	public Map<String, Integer> findDuplicates(String filePath) throws IOException
	{
		Map<String,Integer> map=new HashMap<String,Integer>();
		FileReader fs = null;
		BufferedReader br = null;
		try {
			fs=new FileReader(filePath);
			br=new BufferedReader(fs);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	System.out.println(br.readLine());
		
		String line=br.readLine();
		System.out.println(line);
		String[] tokens=line.split("[. ]");
		for(int i=0;i<tokens.length;i++)
		{
			String temp=tokens[i].toLowerCase();
			if(map.containsKey(temp))
			{
				map.put(temp, map.get(temp)+1);
			}
			else
			{
				map.put(temp,1);
			}
			
		}
		/*for(Map.Entry<String, Integer> m:map.entrySet())
		{
			System.out.println(m.getKey()+"=="+m.getValue());
		}*/
		/*while(br.readLine()!=null)
		{
			System.out.println(br.readLine());
		}*/
		return map;	
	}
	
	{
		//TreeMap<String,Integer> tmap=new TreeMap<String,Integer>(map);
		
		
	}
}
