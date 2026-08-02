package com.orangeHRMTest;

import org.testng.annotations.Test;

import OrangeHRM.DataProviders.TestDataProvider;
import OrangeHRM.Utility.BaseTest;
import OrangeHRM.pages.loginPage;

public class TC_loginTestUsingDataProvider extends  BaseTest {
	
	@Test(dataProvider = "orangeHrmTestData", dataProviderClass = TestDataProvider.class)
	public void loginVerificationRun(String usernameValue, String passwordValue, String TS) throws Exception {
		Thread.sleep(3000);
	    loginPage login = new loginPage(driver);
	    login.loginDetail(usernameValue, passwordValue);
	    System.out.println("Test Case: " + TS + " executed with Username: " + usernameValue + " and Password: " + passwordValue);
		Thread.sleep(3000);
	}


}
