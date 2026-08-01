package com.orangeHRMTest;

import org.testng.annotations.Test;

import OrangeHRM.Utility.BaseTest;
import OrangeHRM.pages.loginPage;

public class TC_loginTestUsingDataProvider extends  BaseTest {
	
	@Test(dataProvider = "orangeHrmTestData", dataProviderClass = OrangeHRM.DataProviders.TestDataProvider.class)
	public void loginVerificationRun(String usernameValue, String passwordValue) {
	    loginPage login = new loginPage(driver);
	    login.loginDetail(usernameValue, passwordValue);
	}


}
