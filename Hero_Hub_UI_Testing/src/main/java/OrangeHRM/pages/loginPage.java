package OrangeHRM.pages;

import OrangeHRM.Utility.Library;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class loginPage extends BasePage {

    // Constructor correctly initializes elements and passes driver to BasePage
    public loginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    // Removed the local duplicate 'WebDriver driver;' variable to prevent null pointers and variable shadowing.
    
    @FindBy(name = "username")
    private WebElement username;
    
    @FindBy(name = "password")
    private WebElement password;
    
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginBtn;
    
    /**
     * Performs application login using optimized framework wrappers
     * @param user - Application username
     * @param pass - Application password
     */
    public void loginDetail(String user, String pass) {
        // Leverages the parent 'driver' instance variable inherited from BasePage
        Library.Custom_SendKeys(driver, username, user, "Username Text Field");
        Library.Custom_SendKeys(driver, password, pass, "Password Text Field");
        Library.Custom_Click(driver, loginBtn, "Login Submit Button");
    }
}
