package OrangeHRM.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import OrangeHRM.Utility.PageClassObject;

/**
 * @author Ganesh.Mahure
 */
public abstract class BasePage extends PageClassObject {
 
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;                
        PageFactory.initElements(driver, this);
    }
}
