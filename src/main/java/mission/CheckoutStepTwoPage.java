package mission;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;

public class CheckoutStepTwoPage extends BasePage {

    public CheckoutStepTwoPage(WebDriver driver) {
        super(driver);
    }

    public double getCartSubtotal(String type) {
        WebElement labelType = driver.findElement(By.cssSelector("[data-test='" + type + "-label']"));
        String labelTypeText = labelType.getText();
        String subtotalAmount = labelTypeText.replaceAll("[^\\d.]", "");
        return Double.parseDouble(subtotalAmount);

    }

    public double addCartTotal() {
        double total = 0.0;

        List<WebElement> itemPrice = driver.findElements(By.cssSelector("[data-test='inventory-item-price']"));
        for (WebElement itemTotalPrice : itemPrice) {

            String itemPriceText = itemTotalPrice.getText().replace("$", "").trim();
            total += Double.parseDouble(itemPriceText);
        }
        return total;

    }

    public double calculateTaxRate(String type, int taxRate) {
        double subtotalAmount = getCartSubtotal("subtotal");
        double expectedTax = subtotalAmount * (taxRate / 100.0);
        expectedTax = Math.round(expectedTax * 100) / 100.0;

        return expectedTax;

    }

}
