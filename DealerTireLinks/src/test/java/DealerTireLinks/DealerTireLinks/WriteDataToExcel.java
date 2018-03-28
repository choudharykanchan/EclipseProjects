package DealerTireLinks.DealerTireLinks;

import java.awt.Color;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.sun.jna.platform.unix.X11.XClientMessageEvent.Data;

	
	public class WriteDataToExcel {
	    

	    public void writeExcel(String filePath,String fileName, String dataToWrite,int start,int end, List GivenURLs,List FutureURLs,List URLStatus,List statusMessage) throws IOException
	    {
	    	
	       File file = new File(filePath+"\\"+fileName);
	       XSSFWorkbook results;
	       XSSFSheet sheet;
	       XSSFCellStyle row1style0,row1style1,row1style2,row1style3;
	       XSSFColor lightgray;
	       FileInputStream inputStream= null;
	       if (!file.exists()) 
	       {
	    
	    	  // file.createNewFile();
	    	   results = new XSSFWorkbook();
	    	    sheet = results.createSheet();
	    	    System.out.println("New file is created");
	    	    Row Headerrow = sheet.createRow(0);
	            Cell cell0=Headerrow.createCell(0);
	            Cell cell1=Headerrow.createCell(1);
	            Cell cell2=Headerrow.createCell(2);
	            Cell cell3=Headerrow.createCell(3);
	            cell0.setCellValue("Current URL");
	            cell1.setCellValue("Future URL");
	            cell2.setCellValue("URL verification");
	            cell3.setCellValue("Status");
	           row1style0 = results.createCellStyle();
	         
	           
	            lightgray = new XSSFColor(Color.lightGray);
	            row1style0.setFillForegroundColor(  lightgray );
	            row1style0.setFillPattern( FillPatternType.SOLID_FOREGROUND );
	            row1style0.setWrapText(true);
	            //setDefaultColumnWidth
	            Font headerFont = results.createFont();
         	   headerFont.setBold(true);
         	  row1style0.setFont(headerFont);
         	   
	           
	           cell0.setCellStyle(row1style0);
	          cell1.setCellStyle(row1style0);
	          cell2.setCellStyle(row1style0);
	          cell3.setCellStyle(row1style0);
	       sheet.autoSizeColumn(0);
	       sheet.autoSizeColumn(1);
	       sheet.autoSizeColumn(2);
	       sheet.autoSizeColumn(3);
	    	   
	       }
	       
	       else
	    	   
	       {
	    	   inputStream = new FileInputStream(file);
	    	     results = new XSSFWorkbook(inputStream);
	    	  
	    	  //sheet= results.getSheetAt(0);
	    	     sheet = results.getSheetAt(0);
	    	  String sheetName= sheet.getSheetName();
	    	  System.out.println(sheetName);
	    	  System.out.println("File already exists");
	    	  
	       }
	     
	      
	     
	      
	       System.out.println("Editing File");
           for(int i=start-1,k=0;i<end;i++,k++)
           {
        	   Row row = sheet.createRow(i);
        	   Cell cell0=row.createCell(0);
	            Cell cell1=row.createCell(1);
	            Cell cell2=row.createCell(2);
	            Cell cell3=row.createCell(3);
	            cell0.setCellValue(GivenURLs.get(k).toString());
	            cell1.setCellValue(FutureURLs.get(k).toString());
	            cell2.setCellValue(URLStatus.get(k).toString());
	            cell3.setCellValue(statusMessage.get(k).toString());
	            sheet.autoSizeColumn(0);
	 	       sheet.autoSizeColumn(1);
	 	       sheet.autoSizeColumn(2);
	 	       sheet.autoSizeColumn(3);
	 	      row1style0 = results.createCellStyle();
	 	      row1style0.setWrapText(true);
	 	     cell0.setCellStyle(row1style0);
	          cell1.setCellStyle(row1style0);
	          cell2.setCellStyle(row1style0);
	          cell3.setCellStyle(row1style0);
               if (URLStatus.get(k).toString().equalsIgnoreCase("URL MisMatched"))
               {
            	   System.out.println("URL Mismatched appears and is in if loop");
            	   XSSFCellStyle headerStyle = results.createCellStyle();
            	   
            	   Font headerFont = results.createFont();
            	   headerFont.setColor(IndexedColors.RED.getIndex());
            	   headerStyle.setFont(headerFont);
            	   cell0.setCellStyle(headerStyle);
            	   cell1.setCellStyle(headerStyle);
            	   cell2.setCellStyle(headerStyle);
            	   cell3.setCellStyle(headerStyle);
               }
           }
           if (file.exists())
           {
        	   inputStream.close();
           }
           FileOutputStream outputStream = new FileOutputStream(file);
           BufferedOutputStream BOS=new BufferedOutputStream(outputStream);
           
           results.write(BOS);
           outputStream.close();
           BOS.close();
	    
           System.out.println("Excel File Written.");
           }
	}
	    
	
	

