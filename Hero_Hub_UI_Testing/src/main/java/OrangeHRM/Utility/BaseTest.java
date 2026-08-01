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
import com.aventstack.extentreports.ExtentTest;

import net.bytebuddy.utility.RandomString;

public class BaseTest extends ConfigeDataProvider {
    
    @BeforeSuite(alwaysRun = true)
    public void setupSuite() {
        // Fixed: Removed the unnecessary LOGGER argument
        OrangeHRM.Utility.Log.initialiseExtentReport();
    }
    
    @BeforeClass(alwaysRun = true)
    public void beforeClass() {     
        LOGGER.debug("***************         Next Class suite      ***********************");       
    }
    
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method, ITestResult result) throws Exception {      
        LOGGER.debug("**************      launching chrome browser     ********************* ");      
        launchBrowser();
        System.out.println("Session ID:" + ((RemoteWebDriver) driver).getSessionId());

        // Fix: We create a unique name using both the class name and method name
        String testName = result.getTestClass().getName() + " = " + method.getName();
        
        // Grabs the public static 'extent' object from Log and links it to this execution thread
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
        LOGGER.debug("*******************         Driver Quit       ***********************");       
        quitBrowser(); 
        
        // Clean up ThreadLocal reference after test finishes
        OrangeHRM.Utility.Log.removeTest();
    }

    @AfterClass(alwaysRun = true)
    public void afterClass()  {             
        LOGGER.debug("*******************        Next Class suite      ***********************");       
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownSuite() {           
        // Fixed: Removed the unnecessary LOGGER argument
        OrangeHRM.Utility.Log.flushExtent();
    }


    
    public static void launchBrowser() throws Exception {               
        ChromeOptions options = new ChromeOptions();        
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));                     
        options.addArguments("--force-device-scale-factor=0.9"); 
        options.addArguments("--headless=new"); // Runs Chrome invisibly in the background
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");        
        System.setProperty("webdriver.chrome.silentOutput", "true");        
        driver = new ChromeDriver(options);      
        driver.manage().window().maximize();
        driver.get(ConfigeDataProvider.getOrangeHrmUrl());            
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(25));      
    }   


    public static void quitBrowser() {
        if (driver != null) {
            driver.quit(); 
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
