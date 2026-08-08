package OrangeHRM.Utility;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.google.common.io.Files;
import net.bytebuddy.utility.RandomString;

public class BaseTest extends ConfigeDataProvider {
    
    @BeforeSuite(alwaysRun = true)
    public void setupSuite() {        
        OrangeHRM.Utility.Log.initialiseExtentReport();
    }
    
    // 💡 PHASE 1 CONFIGURATION: Added dynamic object data model instantiation hooks
    @BeforeClass(alwaysRun = true)
    public void beforeClass() throws Exception {     
        LOGGER.debug("***************         Launching Browser Session for Class      ***********************");       
        launchBrowser();
        System.out.println("Session ID:" + ((RemoteWebDriver) driver).getSessionId());
        
        // 🛠️ ADDED LINES: Instantiates data properties and page objects right inside the Class lifecycle layer
        exceldata = new OrangeHRM.ExcelDataProvider.excelTestData(0, 1);
        login = new OrangeHRM.pages.loginPage(driver);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method, ITestResult result) throws Exception {      
        String testName = result.getTestClass().getName() + " = " + method.getName();              
        com.aventstack.extentreports.ExtentTest test = OrangeHRM.Utility.Log.extent.createTest(testName);
        OrangeHRM.Utility.Log.setTest(test);
        
        LOGGER.debug("====================================================================================");
        LOGGER.debug("                   Start -> Test -> " + method.getName() + "    ");
        LOGGER.debug("====================================================================================");
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(Method method, ITestResult result) throws Exception  {
        OrangeHRM.Utility.Log.afterMethodLogResult(method, result, driver);
        LOGGER.debug(" ");
        Library.threadSleep(1000);
        LOGGER.debug(" End -> Test -> " + method.getName() + "    ");                                
        OrangeHRM.Utility.Log.removeTest();
    }

    @AfterClass(alwaysRun = true)
    public void afterClass()  {             
        LOGGER.debug("*******************         Driver Quit (End of Class Suite Run)       ***********************");       
        quitBrowser(); 
        LOGGER.debug("*******************        Next Class suite      ***********************");       
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownSuite() {           
        OrangeHRM.Utility.Log.flushExtent();
    }
    
    public static void launchBrowser() throws Exception {               
        ChromeOptions options = new ChromeOptions();        
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));                     
        options.addArguments("--force-device-scale-factor=0.9"); 
        if (System.getenv("JENKINS_URL") != null) {
            LOGGER.info("Detected Jenkins CI/CD environment. Running browser in HEADLESS mode.");
            options.addArguments("--headless=new"); 
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        } else {
            LOGGER.info("Detected Local IDE environment. Launching VISIBLE browser window.");            
        }        
        System.setProperty("webdriver.chrome.silentOutput", "true");        
        driver = new ChromeDriver(options);      
        driver.manage().window().maximize();                
        driver.get(ConfigeDataProvider.getOrangeHrmUrl());            
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(25));      
    }   

    public static void quitBrowser() {
        if (driver != null) {
            driver.quit(); 
            driver = null; 
        }
    }   

    public static void reloadBrowser() {
        driver.get(ConfigeDataProvider.getOrangeHrmUrl());
    }   

    public static String getscreenshot() {
        TakesScreenshot ts = (TakesScreenshot) driver;
        return ts.getScreenshotAs(OutputType.BASE64);   
    }

    public static String takeScreenshot() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy-hhmmss");
        String strDate = formatter.format(date);
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);                
        String path = System.getProperty("user.dir") + File.separator + "Reports" + File.separator + "ChromeTestScreenShots" 
                      + File.separator + strDate + "_" + RandomString.make(5) + "_.jpg";
        try {
            Files.copy(srcFile, new File(path));
        } catch (IOException e) {          
            e.printStackTrace();
        }
        return path;
    }
}
