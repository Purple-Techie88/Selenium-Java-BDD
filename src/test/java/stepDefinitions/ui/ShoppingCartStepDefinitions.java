package stepDefinitions.ui;

import java.util.List;

import org.testng.Assert;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import mission.BasePage;
import mission.ShoppingCartPage;

public class ShoppingCartStepDefinitions {

    ShoppingCartPage shoppingCartPage;
    BasePage basePage;

    @And("^I  should see (\\d+) items added to the shopping cart$")
    public void i_should_see_items_added_to_the_shopping_cart(int itemsAdded) {
        basePage = new BasePage();
        Assert.assertEquals(basePage.getCartItemCount(), itemsAdded);
    }

    @And("^I click on the shopping cart$")
    public void i_click_on_the_shopping_cart() {
        shoppingCartPage = new ShoppingCartPage();
        shoppingCartPage.clickShoppingCartButton();
    }

    @And("^I verify that the QTY count for each item should be (\\d+)$")
    public void i_verify_that_the_QTY_count_for_each_item_should_be(int expectedQty) {
        List<String> qtyStrings = shoppingCartPage.getQtyForAllItems();

        System.out.println("Quantities in cart:");
        for (String q : qtyStrings) {
            int actualQty = Integer.parseInt(q);
            System.out.println(actualQty);  // print each quantity
            Assert.assertEquals(actualQty, expectedQty, "Item quantity does not match expected value");
        }

    }

    @And("^I remove the following item:$")
    public void i_remove_the_following_item(DataTable dataTable) {
        List<String> items = dataTable.asList(String.class);
        for (String item : items) {
            shoppingCartPage.removeItemFromCart(item);
        }
    }

    @And("^I click on the CHECKOUT button$")
    public void i_click_on_the_CHECKOUT_button() {
        shoppingCartPage.clickCheckoutbutton();
    }
}
