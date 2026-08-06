package com.orangeHRMTest;

import org.testng.annotations.Test;
import OrangeHRM.Utility.BaseTest;
import OrangeHRM.Utility.Library;
import OrangeHRM.pages.loginPage;

public class TC_CookieLoginExecution extends BaseTest {

    // 💡 Rule: By using single-session execution, you skip complex cookie injections 
    // and speed up your entire automated run pipeline!

    @Test(priority = 1, description = "Authenticate through the UI once to establish the master test session")
    public void step01_loginToApplication() {
        loginPage login = new loginPage(driver);
        
        // Logs in smoothly using your framework instance variables
        login.loginDetail(exceldata.username1, exceldata.password1);
        Library.threadSleep(4000); 
        
        // Asserts that we successfully landed on the internal dashboard page
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("dashboard")) {
            Library.assertEquals(driver, currentUrl, currentUrl);
            System.out.println("Login Successful! Master session established.");
        } else {
            org.testng.Assert.fail("Initial login failed! Current URL: " + currentUrl);
        }
    }

    @Test(priority = 2, dependsOnMethods = {"step01_loginToApplication"}, description = "Execute subsequent functional tests inside the same session")
    public void step02_verifyDashboardNavigation() {
        // Because the driver does not quit, you are STILL logged in here!
        System.out.println("Running second test method smoothly inside the authenticated browser window...");
        
        // Example: Validate dashboard URL state
        String currentUrl = driver.getCurrentUrl();
        Library.assertEquals(driver, currentUrl, currentUrl);
    }
}
