package mission;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutStepOnePage extends BasePage {
    
    @FindBy(id ="continue")
    @CacheLookup
    WebElement continueButton;

     public CheckoutStepOnePage() {
        PageFactory.initElements(driver, this);
    }

    public void completeCheckoutField(String fieldId, String Value){
        WebElement field = driver.findElement(By.id(fieldId));
        field.clear();
        field.sendKeys(Value);
    }

    public void clickContinueButton(){
        continueButton.click();
    }
}
