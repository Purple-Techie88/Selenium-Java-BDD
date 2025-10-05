package mission;

import org.openqa.selenium.support.PageFactory;

public class ShoppingCartPage extends BasePage{

     public ShoppingCartPage(){
        PageFactory.initElements(driver, this);
    }

    
     public void clickShoppingCartButton(){
        shoppingCartBadge.click();
     }

}
