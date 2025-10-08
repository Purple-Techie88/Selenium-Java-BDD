package stepDefinitions.ui;

import java.util.List;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import mission.InventoryPage;

public class InventoryStepDefinitions {

    InventoryPage inventoryPage;

    @And("^I add the following items to the basket$")
    public void i_add_the_following_items_to_the_basket(DataTable dataTable) {
        inventoryPage = new InventoryPage();
        List<String> items = dataTable.asList(String.class);
        inventoryPage.addItemsToCart(items);
    }

}
