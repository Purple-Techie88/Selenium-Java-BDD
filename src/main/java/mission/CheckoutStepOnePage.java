package mission;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutStepOnePage extends BasePage {

    @FindBy(id ="first-name")
    @CacheLookup
    WebElement firstNameField;

    @FindBy(id ="last-name")
    @CacheLookup
    WebElement lastNameField;
    
    @FindBy(id ="postal-code")
    @CacheLookup
    WebElement postalCodeField;

    @FindBy(id ="continue")
    @CacheLookup
    WebElement continueButton;

    public CheckoutStepOnePage() {
        PageFactory.initElements(driver, this);
    }

    public void completeFirstNameField(String firstName){
        firstNameField.sendKeys(firstName);
    }

    public void completeLastNameField(String lastName){
        lastNameField.sendKeys(lastName);
    }

    public void completePostalCodeField(String postalCode ){
        postalCodeField.sendKeys(postalCode);
    }

    public void clickContinueButton(){
        continueButton.click();
    }
}
