package mission;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(id = "user-name")
    @CacheLookup
    private WebElement usernameField;

    @FindBy(id = "password")
    @CacheLookup
    private WebElement passwordField;

    @FindBy(id = "login-button")
    @CacheLookup
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void visitLoginPage() {
        driver.get(LoadProp.getProperty("url"));

    }

    public void userLogin(String username, String password) {
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();

    }
}
