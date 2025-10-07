package stepDefinitions;

import java.util.List;
import java.util.Map;

import org.testng.Assert;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import mission.BasePage;
import mission.CheckoutStepOnePage;
import mission.CheckoutStepTwoPage;
import mission.InventoryPage;
import mission.LoginPage;
import mission.ShoppingCartPage;

public class CheckoutStepDefinition {

    LoginPage loginPage;
    InventoryPage inventoryPage;
    BasePage basePage;
    ShoppingCartPage shoppingCartPage;
    CheckoutStepOnePage checkoutStepOnePage;
    CheckoutStepTwoPage checkoutStepTwoPage;

    public CheckoutStepDefinition() {
    }

    @Given("^I am on the home page$")
    public void iAmOnTheHomePage() {
        loginPage = new LoginPage();
        loginPage.visitLoginPage();
    }

    @And("I log in with the following details")
    public void iLoginWithUsernameAndPassword(DataTable dataTable) {
        Map<String, String> credentials = dataTable.asMaps(String.class, String.class).get(0);

        String username = credentials.get("userName");
        String password = credentials.get("password");

        loginPage.userLogin(username, password);
    }

    @And("^I add the following items to the basket$")
    public void i_add_the_following_items_to_the_basket(DataTable dataTable) {
        inventoryPage = new InventoryPage();
        List<String> items = dataTable.asList(String.class);
        inventoryPage.addItemsToCart(items);
    }

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
        List<String> qty = shoppingCartPage.getQtyForAllItems();

        for (String quantity : qty) {
            Assert.assertEquals(quantity, String.valueOf(expectedQty));
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

    @And("^I type \"([^\"]*)\" for \"([^\"]*)\"$")
    public void i_type_for_First_Name(String value, String fieldName) {
        checkoutStepOnePage = new CheckoutStepOnePage();

        String fieldId;
        switch (fieldName) {
            case "First Name":
                fieldId = "first-name";
                break;
            case "Last Name":
                fieldId = "last-name";
                break;
            case "ZIP/Postal Code":
                fieldId = "postal-code";
                break;
            default:
                throw new IllegalArgumentException("Unknown checkout field: " + fieldName);
        }

        checkoutStepOnePage.completeCheckoutField(fieldId, value);

    }

    @When("^I click on the CONTINUE button$")
    public void i_click_on_the_CONTINUE_button() {
        checkoutStepOnePage.clickContinueButton();
    }

    @Then("Item total will be equal to the {string} of items on the list")
    public void item_total_will_be_equal_to_the_of_items_on_the_list(String type) {
        checkoutStepTwoPage = new CheckoutStepTwoPage();
        double calculatedTotal = checkoutStepTwoPage.addCartTotal();
        double cartSubTotal = checkoutStepTwoPage.getCartSubtotal(type);

        Assert.assertEquals(calculatedTotal, cartSubTotal);

    }

    @And("a {string} rate of {int} % is applied to the total")
    public void a_rate_of_is_applied_to_the_total(String type, Integer taxRate) {
        double expectedTax = checkoutStepTwoPage.calculateTaxRate("tax", taxRate);
        double displayedTax = checkoutStepTwoPage.getCartSubtotal("tax");

        Assert.assertEquals(displayedTax, expectedTax);

    }

}
