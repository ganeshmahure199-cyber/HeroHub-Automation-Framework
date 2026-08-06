package OrangeHRM.Utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import OrangeHRM.ExcelDataProvider.excelTestData;
import OrangeHRM.pages.loginPage;

public class PageClassObject {

    public static final Logger LOGGER = LogManager.getLogger("Log");      
    public static excelTestData exceldata; 
    public static loginPage login;
    public static WebDriver driver; 

    @BeforeMethod(alwaysRun = true)
    public void getObject() {
        LOGGER.debug("Readying framework method environment track...");
    }
}
