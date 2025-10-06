package mission;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ShoppingCartPage extends BasePage{

@FindBy(id ="checkout")
@CacheLookup
WebElement checkoutButton;

     public ShoppingCartPage(){
        PageFactory.initElements(driver, this);
    }

     public void clickShoppingCartButton(){
        shoppingCartBadge.click();
     }

     public List <String> getQtyForAllItems(){
      List<WebElement> cartItems = driver.findElements(By.cssSelector("[data-test='inventory-item']"));
      List<String> qtyNum = new ArrayList<>();
      
      for (WebElement item: cartItems){
         WebElement itemQty = item.findElement(By.cssSelector("[data-test='item-quantity']"));

      }
      return qtyNum;
     }
   
     public void removeItemFromCart(String item){
      String itemButtonId = "remove-" + item.toLowerCase().replace(" ", "-");
      driver.findElement(By.id(itemButtonId)).click();
     }
    
     public void clickCheckoutbutton(){
      checkoutButton.click();
     }
}
