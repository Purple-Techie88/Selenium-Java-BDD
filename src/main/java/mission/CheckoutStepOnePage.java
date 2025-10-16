package mission;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutStepOnePage extends BasePage {

    @FindBy(id = "continue")
    @CacheLookup
    WebElement continueButton;

    public CheckoutStepOnePage(WebDriver driver) {
        super(driver);

    }

    public void completeCheckoutField(String fieldId, String Value) {
        WebElement field = driver.findElement(By.id(fieldId));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(field));
        field.clear();
        field.sendKeys(Value);
    }

    public void clickContinueButton() {
        continueButton.click();
    }
}
