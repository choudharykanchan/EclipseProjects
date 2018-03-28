package Guru99LiveSeleniumProject.Guru99LiveSeleniumProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

/*import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;*/

import com.gargoylesoftware.htmlunit.javascript.host.worker.Worker;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Utility {
	//static Workbook wb;
	
	/*public static int[] getRowAndCellNo(String filePath,String fileName,String value) throws InvalidFormatException
	{
		FileInputStream fs;
		int a[]=new int[2];
		 wb=null;
		 fs=null;
		 File file=new File(filePath+fileName);
		 
		try {
			fs = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			 wb=new XSSFWorkbook(fs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Sheet sheet=wb.getSheetAt(0);
	int firstrow=sheet.getFirstRowNum();
	int lastrow= sheet.getLastRowNum();
	int rowNumber;
	int cellNumber;
	Iterator rows=sheet.rowIterator();
	Row row;
	Cell cell;
	
		while(rows.hasNext())
		{
			row=(Row) rows.next();
			
			Iterator cells=row.cellIterator();
			while(cells.hasNext())
			{
				cell=(Cell) cells.next();
				String cellValue=cell.getStringCellValue();
				if(cellValue.equalsIgnoreCase(value))
				{
				cellNumber=cell.getColumnIndex();
				rowNumber=row.getRowNum();
				a[0]=rowNumber;
				a[1]=cellNumber;
				break;
				}
				
			}
					
		}
		return a;
		
	}*/
	public static String[][] readDataFromExcel(String filePath,String fileName,String tableName)
	{
		String tabArray[][]=null;
		Workbook workbook=null;
		try {
			 workbook = Workbook.getWorkbook(new File(filePath));
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Sheet sheet;
		 sheet = workbook.getSheet(fileName);
		 int startRow, startCol, endRow, endCol;
		 Cell tableStart=sheet.findCell(tableName);
		 startRow=tableStart.getColumn();
		 startCol=tableStart.getRow();
		 Cell tableEnd=sheet.findCell(tableName, startCol + 1, startRow + 1,
					100, 64000, false);
		 endRow=tableEnd.getRow();
		 endCol=tableEnd.getColumn();
		 System.out.println("start row"+ startRow );
		 System.out.println(sheet.getCell(startRow, startCol).getContents());
		 tabArray=new String[endRow - startRow - 1][endCol - startCol - 1];
		int ci=0;
		
		
		 for(int i=startRow+1;i<endRow;i++,ci++)
		 {
			 int cj=0;
			 for(int j=startCol+1;j<endCol;j++,cj++)
			 {
				 tabArray[ci][cj]=sheet.getCell(j, i).getContents();
			 }
		 }
		 return tabArray;
		 
	}


}
