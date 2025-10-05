package stepDefinitions;

import java.util.List;
import java.util.Map;

import org.testng.Assert;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import mission.BasePage;
import mission.InventoryPage;
import mission.LoginPage;
import mission.ShoppingCartPage;

public class CheckoutStepDefinition {
    LoginPage loginPage;
    InventoryPage inventoryPage;
    BasePage basePage;
    ShoppingCartPage shoppingCartPage;

    @Given("^I am on the home page$")
    public void iAmOnTheHomePage() {
        loginPage = new LoginPage();
        loginPage.visitLoginPage();
    }

    @And("I log in with the following details")
    public void iLoginWithUsernameAndPassword(DataTable dataTable){
        Map<String, String> credentials = dataTable.asMaps(String.class, String.class).get(0);

        String username = credentials.get("userName");
        String password = credentials.get("password");

        loginPage.userLogin(username, password);
    }

    @Given("^I add the following items to the basket$")
    public void i_add_the_following_items_to_the_basket(DataTable dataTable){
    inventoryPage = new InventoryPage();
    List<String> items = dataTable.asList(String.class);
    inventoryPage.addItemsToCart(items);
}

@Given("^I  should see (\\d+) items added to the shopping cart$")
public void i_should_see_items_added_to_the_shopping_cart(int arg1) {
    basePage = new BasePage();
    Assert.assertEquals(basePage.getCartItemCount(), 4);
}

@Given("^I click on the shopping cart$")
public void i_click_on_the_shopping_cart(){
    shoppingCartPage = new ShoppingCartPage();
    shoppingCartPage.clickShoppingCartButton();
}

@Given("^I verify that the QTY count for each item should be (\\d+)$")
public void i_verify_that_the_QTY_count_for_each_item_should_be(int arg1) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
}

@Given("^I remove the following item:$")
public void i_remove_the_following_item(DataTable arg1) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
    // E,K,V must be a scalar (String, Integer, Date, enum etc)
    throw new PendingException();
}

@Given("^I click on the CHECKOUT button$")
public void i_click_on_the_CHECKOUT_button() throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
}

@Given("^I type \"([^\"]*)\" for First Name$")
public void i_type_for_First_Name(String arg1) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
}

@Given("^I type \"([^\"]*)\" for Last Name$")
public void i_type_for_Last_Name(String arg1) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
}

@Given("^I type \"([^\"]*)\" for ZIP/Postal Code$")
public void i_type_for_ZIP_Postal_Code(String arg1) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
}

@When("^I click on the CONTINUE button$")
public void i_click_on_the_CONTINUE_button() throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
}

@Then("^Item total will be equal to the total of items on the list$")
public void item_total_will_be_equal_to_the_total_of_items_on_the_list() throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
}

@Then("^a Tax rate of (\\d+) % is applied to the total$")
public void a_Tax_rate_of_is_applied_to_the_total(int arg1) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
}


}
