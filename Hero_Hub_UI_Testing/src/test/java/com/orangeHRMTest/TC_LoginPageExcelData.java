package com.orangeHRMTest;

import org.testng.annotations.Test;
import OrangeHRM.Utility.BaseTest;
import OrangeHRM.ExcelDataProvider.excelTestData;
import OrangeHRM.pages.loginPage;

public class TC_LoginPageExcelData extends BaseTest {
    
    @Test
    public void testLoginExcelTestData_check() {        
        loginPage login = new loginPage(driver);
              
        excelTestData testData = new excelTestData(0, 1);              
        login.loginDetail(testData.username, testData.password);
    }

    @Test
    public void testLoginWithDifferentUser_check() {        
        loginPage login = new loginPage(driver);
        
        excelTestData testData = new excelTestData(0, 2);        
        login.loginDetail(testData.username, testData.password);
        login.loginDetail(excelTestData.username, testData.password);
        
    }
}
