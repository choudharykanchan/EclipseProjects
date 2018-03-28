package learnSelenium;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.SpringLayout.Constraints;

import org.apache.poi.xssf.usermodel.XSSFCell;

import org.apache.poi.xssf.usermodel.XSSFRow;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelUtils {
	private static XSSFSheet ExcelWSheet;
	 
	private static XSSFWorkbook ExcelBook;

	private static XSSFCell Cell;

	private static XSSFRow Row;
	
	public static void setExcelFile(String Path,String SheetName) throws IOException
	{
		FileInputStream ExcelFile= new FileInputStream(Path);
	    ExcelBook =new XSSFWorkbook(ExcelFile);
		ExcelWSheet = ExcelBook.getSheet(SheetName);
		
		
		
	}
	public static String getCelldata(int RowNum,int colNum)
	{
		Cell= ExcelWSheet.getRow(RowNum).getCell(colNum);
		String cellData= Cell.getStringCellValue();
		return cellData;
	
	}

	
		
		
	}
	
	
	
	




	
	

