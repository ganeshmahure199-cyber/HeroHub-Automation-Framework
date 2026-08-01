package com.orangeHRMTest;

import org.testng.annotations.Test;
import OrangeHRM.Utility.BaseTest;
import OrangeHRM.ExcelDataProvider.excelTestData;
import OrangeHRM.pages.loginPage;

public class TC_LoginPageExcelData extends BaseTest {
    
    @Test
    public void testLoginExcelTestData_check() {        
        loginPage login = new loginPage(driver);                      
        login.loginDetail(excelTestData.username, excelTestData.password);
    }
    
}
