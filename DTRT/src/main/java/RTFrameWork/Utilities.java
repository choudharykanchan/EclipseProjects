package RTFrameWork;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Utilities {
	public  final static String uRL="https://demo.rightturn.com/";
	public  final static String vehicleDataFilePath="C:\\Users\\thinksysuser\\Desktop";
	public  final static String vehicleDataFileName="demoVehicles.xlsx";
	public static String readFromExcel(String filePath,String fileName,int rowno,String valueof) 
{
	
		File file=new File(vehicleDataFilePath+"\\"+vehicleDataFileName);
		FileInputStream is = null;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Workbook wb = null;
		try {
			wb = new XSSFWorkbook(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	Sheet sheet=wb.getSheetAt(0);
	Row row=sheet.getRow(rowno);
	Row row1=sheet.getRow(0);
	int totalcell=row.getLastCellNum();
	int matchingCellNo = 0;
	for(int i=0;i<totalcell;i++)
	{
		
	if(row1.getCell(i).getStringCellValue().equalsIgnoreCase(valueof)==true)
	{
		matchingCellNo=i;
		break;
	}
	
	}
	
	DataFormatter formatter = new DataFormatter();
	String value = formatter.formatCellValue(sheet.getRow(rowno).getCell(matchingCellNo));
	//String value=row.getCell(matchingCellNo).getStringCellValue();
	
	
	return value;
}

public static int getRandomNumber(int start,int end)
{
	
	int number=ThreadLocalRandom.current().nextInt(start, end);
	return number;
}

}
