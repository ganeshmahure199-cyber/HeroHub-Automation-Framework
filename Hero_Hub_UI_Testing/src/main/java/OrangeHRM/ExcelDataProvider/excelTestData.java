package OrangeHRM.ExcelDataProvider;

import OrangeHRM.Utility.Library;

public class excelTestData {
        
    public static String username;
    public static String password;

    public excelTestData(int sheetNumber, int rowNumber) {
        
        excelTestData.username = Library.getExcelData(sheetNumber, rowNumber, 4);
        excelTestData.password = Library.getExcelData(sheetNumber, rowNumber, 5);
    }
}
