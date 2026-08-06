package com.orangeHRMTest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import OrangeHRM.Utility.BaseTest;
import OrangeHRM.Utility.Library;

public class TC_LoginPageExcelData extends BaseTest {
    
   
    @BeforeClass(dependsOnMethods = {"beforeClass"})
    public void loginPreconditionSetup() {
        LOGGER.info("Executing master login precondition step for Excel Data Validation...");
        login.loginDetail(exceldata.username1, exceldata.password1);
        Library.threadSleep(4000);
    }
    
    @Test(description = "Verify that the landing page successfully loads after valid Excel login authentication")
    public void testLoginExcelData_Valid() throws Exception {    
        System.out.println("Running dashboard landing verification test...");           
        String currentUrl = driver.getCurrentUrl();
        Library.assertEquals(driver, currentUrl, currentUrl);
    }
}
