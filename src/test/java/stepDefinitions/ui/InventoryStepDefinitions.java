package stepDefinitions.ui;

import java.util.List;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import mission.InventoryPage;

public class InventoryStepDefinitions {

    final InventoryPage inventoryPage;

    public InventoryStepDefinitions(Hooks hooks) {
        this.inventoryPage = hooks.inventoryPage;
    }

    @And("^I add the following items to the basket$")
    public void i_add_the_following_items_to_the_basket(DataTable dataTable) {
        List<String> items = dataTable.asList(String.class);
        inventoryPage.addItemsToCart(items);
    }

}
