package mission;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebDriver;

public class ShoppingCartPage extends BasePage {

    @FindBy(id = "checkout")
    @CacheLookup
    WebElement checkoutButton;

    public ShoppingCartPage(WebDriver driver) {
    super(driver);
   }

    public void clickShoppingCartButton() {
        shoppingCartBadge.click();
    }

        public List<String> getQtyForAllItems() {
            return driver.findElements(By.cssSelector("[data-test='inventory-item']"))
                         .stream()
                         .map(item -> item.findElement(By.cssSelector("[data-test='item-quantity']")).getText())
                         .collect(Collectors.toList());
        }

    public void removeItemFromCart(String item) {
        String itemButtonId = "remove-" + item.toLowerCase().replace(" ", "-");
        driver.findElement(By.id(itemButtonId)).click();
    }

    public void clickCheckoutbutton() {
        checkoutButton.click();
    }
}
