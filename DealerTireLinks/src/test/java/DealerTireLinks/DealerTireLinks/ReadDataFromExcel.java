package DealerTireLinks.DealerTireLinks;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.collect.Table.Cell;

public class ReadDataFromExcel {
	 
	public List readExcelGivenURLs(String filePath,String fileName,int startingRow,int endingRow) throws IOException{
		 File file =    new File(filePath+"\\"+fileName);
		 FileInputStream inputStream = new FileInputStream(file);
		 ArrayList<String> GivenURLs = new ArrayList<String>();
		 String temp;
		  
		    
		 XSSFWorkbook LinksWorkbook = new XSSFWorkbook(inputStream);
		    	//LinksWorkbook = LinksWorkbook.getWorkbook(inputStream);
		    	
		 XSSFSheet LinksSheet = LinksWorkbook.getSheetAt(0);
		 
		    	 for (int i = startingRow-1; i <endingRow; i++) {

		    	        Row row = LinksSheet.getRow(i);
		    	      

		    	            temp=row.getCell(0).getStringCellValue();
		    	            GivenURLs.add(temp);
		    	 }
 	           
		    	        return GivenURLs;
		    	    }
	 
	 public List readExcelExpectedURLs(String filePath,String fileName,int startingRow,int endingRow) throws IOException{
		 File file =    new File(filePath+"\\"+fileName);
		 FileInputStream inputStream = new FileInputStream(file);
		 ArrayList<String> ExpectedURLs = new ArrayList<String>();
		 String temp;
		  
		    
		 XSSFWorkbook LinksWorkbook = new XSSFWorkbook(inputStream);
		    	//LinksWorkbook = LinksWorkbook.getWorkbook(inputStream);
		    	
		 XSSFSheet LinksSheet = LinksWorkbook.getSheetAt(0);
	
		    	 for (int i = startingRow-1; i < endingRow; i++) {

		    	        Row row = LinksSheet.getRow(i);
		    	      

		    	            temp=row.getCell(1).getStringCellValue();
		    	            ExpectedURLs.add(temp);
		    	 }
 	           
		    	        return ExpectedURLs;
		    	    }

		    
	
}
