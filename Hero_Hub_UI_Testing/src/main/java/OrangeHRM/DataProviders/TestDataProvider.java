package OrangeHRM.DataProviders;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class TestDataProvider {
  //=====================================================================================
    @DataProvider(name = "loginTest")
    public static Object[][] getLoginData() throws IOException {
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator 
                    + "test" + File.separator + "resources" + File.separator + "TestData" + File.separator + "ExcelTestData.xlsx";
        return fetchExcelDataMatrix(path);
    }
    
  //=====================================================================================
    
    @DataProvider(name = "gaurantedFuturePlus")
    public static Object[][] gfpF1Data() throws IOException {
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator 
                    + "test" + File.separator + "resources" + File.separator + "TestData" + File.separator + "Data.xlsx";
        return fetchExcelDataMatrix(path);
    }

  //=====================================================================================
    
    @DataProvider(name = "orangeHrmTestData")
    public static Object[][] OrangeHrmLoginData() throws IOException {
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator 
                    + "test" + File.separator + "resources" + File.separator + "TestData" + File.separator + "OrangeHrmTestData.xlsx";
        return fetchExcelDataMatrix(path);
    }

//========================================================================================
    private static Object[][] fetchExcelDataMatrix(String filePath) throws IOException {
        DataFormatter formatter = new DataFormatter();        
        try (FileInputStream fis = new FileInputStream(filePath);
             XSSFWorkbook wb = new XSSFWorkbook(fis)) {            
            XSSFSheet sheet = wb.getSheetAt(0); 
            int rowCount = sheet.getPhysicalNumberOfRows();            
            if (rowCount <= 1) {
                return new Object[0][0]; 
            }            
            XSSFRow headerRow = sheet.getRow(0);
            int colCount = headerRow.getLastCellNum();           
            Object[][] data = new Object[rowCount - 1][colCount];             
            for (int i = 0; i < rowCount - 1; i++) {
                XSSFRow row = sheet.getRow(i + 1);                              
                if (row == null) {
                    continue; 
                }                
                String firstCellText = formatter.formatCellValue(row.getCell(0)).trim();
                if (firstCellText.isEmpty()) {
                    continue; 
                }                
                for (int j = 0; j < colCount; j++) {
                    XSSFCell cell = row.getCell(j);                    
                    data[i][j] = formatter.formatCellValue(cell);
                }
            }
            return data; 
        }
    }
}
