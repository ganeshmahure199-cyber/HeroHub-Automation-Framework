package OrangeHRM.ExcelDataProvider;

import OrangeHRM.Utility.Library;

public class excelTestData {
        
    public static String username1;
    public static String password1;
    

    public excelTestData(int sheetNumber, int rowNumber) {
        
        excelTestData.username1 = Library.getExcelData(sheetNumber, rowNumber, 1);
        excelTestData.password1= Library.getExcelData(sheetNumber, rowNumber, 2);
        
    }
}
