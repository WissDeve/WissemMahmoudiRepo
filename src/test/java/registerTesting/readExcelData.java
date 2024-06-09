package registerTesting;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class readExcelData {
	
	
	public String[][] readSheet() throws InvalidFormatException, IOException {
	    File myFile = new File("test-data\\dataDriven.xlsx");
	    XSSFWorkbook wb = new XSSFWorkbook(myFile);
	    XSSFSheet mySheet = wb.getSheet("Feuil1");
	    int numberOfRows = mySheet.getPhysicalNumberOfRows();
	    int numberOfColumns = mySheet.getRow(0).getLastCellNum();
	    String[][] myArray = new String[numberOfRows - 1][numberOfColumns]; // Adjusted array size

	    for (int i = 1; i < numberOfRows; i++) { // Start from index 1 to skip the header row
	        XSSFRow row = mySheet.getRow(i);
	        for (int j = 0; j < numberOfColumns; j++) {
	            XSSFCell cell = row.getCell(j);
	            if (cell != null) {
	                switch (cell.getCellType()) {
	                    case STRING:
	                        myArray[i - 1][j] = cell.getStringCellValue();
	                        break;
	                    case NUMERIC:
	                        // Handle numeric values (e.g., convert to string)
	                        myArray[i - 1][j] = String.valueOf(cell.getNumericCellValue());
	                        break;
	                    default:
	                        // Handle other cell types if needed
	                        break;
	                }
	            }
	        }
	    }
	    return myArray;
	}
}
