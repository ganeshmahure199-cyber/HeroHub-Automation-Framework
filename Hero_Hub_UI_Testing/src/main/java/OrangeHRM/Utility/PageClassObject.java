package OrangeHRM.Utility;

/**
 * @author Ganesh.Mahure
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;

import OrangeHRM.ExcelDataProvider.excelTestData;
import OrangeHRM.pages.loginPage;

public class PageClassObject {
    
    public static final Logger LOGGER = LogManager.getLogger("Log");        
    public excelTestData exceldata; 
    public loginPage login;    
    public static WebDriver driver; 

    @BeforeMethod(alwaysRun = true)
    public void getObject() {        
        exceldata = new excelTestData(0, 1);
        login = new loginPage(driver);
                
    }
}
