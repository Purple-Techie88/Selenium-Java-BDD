package mission;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InventoryPage extends BasePage {

    public InventoryPage(WebDriver driver) {
    super(driver);
    }

    public void addItemsToCart(List<String> items) {
        for (String item : items) {
            String itemButtonId = "add-to-cart-" + item.toLowerCase().replace(" ", "-");
            driver.findElement(By.id(itemButtonId)).click();
        }
    }
}
