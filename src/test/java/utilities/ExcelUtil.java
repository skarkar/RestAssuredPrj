	package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

	public FileInputStream fip;
	public FileOutputStream fop;
	public XSSFWorkbook book;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;
	public XSSFCellStyle cell_style;
	public String data;
	String filePath;
	
	public ExcelUtil(String path) {
		this.filePath = path;
	}
	
	//Method to get total number of rows
	public int getRowCount(String sheetName) throws IOException {
		fip = new FileInputStream(filePath);
		book = new XSSFWorkbook(fip);
		sheet = book.getSheet(sheetName);
		int row_count = sheet.getLastRowNum();
		book.close();
		fip.close();
		
		return row_count;
	}
	
	//Method to get total number of cells
	public int getCellCount(String sheetName) throws IOException {
		fip = new FileInputStream(filePath);
		book = new XSSFWorkbook(fip);
		sheet = book.getSheet(sheetName);
		int cell_count = sheet.getRow(0).getLastCellNum();
		book.close();
		fip.close();
		
		return cell_count;
	}
	
	//Method to get specific cell data
	public String getCellData(String sheetName, int row_num, int cell_num) throws IOException {
		fip = new FileInputStream(filePath);
		book = new XSSFWorkbook(fip);
		sheet = book.getSheet(sheetName);
		row = sheet.getRow(row_num);
		cell = row.getCell(cell_num);
		try {
			//data = cell.toString();
			data = new DataFormatter().formatCellValue(cell); //This is same as toString() in addition it will keep the format of data. Ex. Cell value in excel is 12.3463 which formated to display as 12.35 it will return the value 12.35. same for Dates or numbers 
		}
		catch(Exception e) {
			data = "";
		}
		
		book.close();
		fip.close();
		
		return data;
	}
/*	
	//Method to write data in to specific cell - Creating new File/Overwriting existing file
	public static void setCellData(String filePath, String sheetName, int row_num, int cell_num, String data) throws IOException {
		fop = new FileOutputStream(filePath);
		book = new XSSFWorkbook();
		sheet = book.createSheet(sheetName);
		row = sheet.createRow(row_num);
		cell = row.createCell(cell_num);
		cell.setCellValue(data);
		book.write(fop);
		book.close();
		fop.close();
	}
*/	
	//Method to write data in to specific cell - Update Existing file
	public void setCellData(String sheetName, int row_num, int cell_num, String data) throws IOException {
		File xlfile = new File(filePath);
		if(!xlfile.exists()) {                          //Create Workbook if dont exist
			book = new XSSFWorkbook(fip);
			fop = new FileOutputStream(filePath);
			book.write(fop);
		}
		
		fip = new FileInputStream(filePath);
		book = new XSSFWorkbook(fip);
		
		if(book.getSheetIndex(sheetName) == -1)         //Create Sheet if dont exist
			book.createSheet(sheetName);
		
		sheet = book.getSheet(sheetName);
		
		if(sheet.getRow(row_num) == null)         //Create Row if dont exist
			sheet.createRow(row_num);
		
		row = sheet.getRow(row_num);
		cell = row.getCell(cell_num);
		cell.setCellValue(data);
		fop = new FileOutputStream(filePath);
		book.write(fop);
		book.close();
		fop.close();
		fip.close();
	}
	
	//Method set Cell Formating - Fill Cell color Green
	public void fillColorGreen(String sheetName, int row_num, int cell_num) throws IOException {
		fip = new FileInputStream(filePath);
		book = new XSSFWorkbook(fip);
		sheet = book.getSheet(sheetName);
		row = sheet.getRow(row_num);
		cell = row.getCell(cell_num);
		
		cell_style = book.createCellStyle();
		cell_style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		cell_style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cell.setCellStyle(cell_style);
		
		fop = new FileOutputStream(filePath);
		book.write(fop);
		
		book.close();
		fop.close();
		fip.close();
	}
	
	//Method set Cell Formating - Fill Cell color Red
	public void fillColorRed(String sheetName, int row_num, int cell_num) throws IOException {
		fip = new FileInputStream(filePath);
		book = new XSSFWorkbook(fip);
		sheet = book.getSheet(sheetName);
		row = sheet.getRow(row_num);
		cell = row.getCell(cell_num);
		
		cell_style = book.createCellStyle();
		cell_style.setFillForegroundColor(IndexedColors.RED.getIndex());
		cell_style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cell.setCellStyle(cell_style);
		
		fop = new FileOutputStream(filePath);
		book.write(fop);
		
		book.close();
		fop.close();
		fip.close();
	}
}
