package mission;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BasePage {

    public static WebDriver driver;

    @FindBy(css = "[data-test='shopping-cart-badge']")
    @CacheLookup
    WebElement shoppingCartBadge;

    public BasePage() {
        PageFactory.initElements(driver, this);
    }

    public int getCartItemCount() {
        try {
            return Integer.parseInt(shoppingCartBadge.getText());
        } catch (Exception e) {
            return 0;
        }
    }

}
