package com.orangeHRMTest;

import org.testng.annotations.Test;
import OrangeHRM.Utility.BaseTest;
import OrangeHRM.ExcelDataProvider.excelTestData;
import OrangeHRM.pages.loginPage;

public class TC_LoginPageExcelData extends BaseTest {
    
    @Test
    public void testLoginExcelData_Valid() throws Exception {    
    	Thread.sleep(3000);
        loginPage login = new loginPage(driver);                      
        login.loginDetail(excelTestData.username1, excelTestData.password1);
    }
    
  
}
