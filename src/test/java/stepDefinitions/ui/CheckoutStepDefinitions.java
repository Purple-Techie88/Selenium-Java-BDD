package stepDefinitions.ui;

import org.testng.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import mission.CheckoutStepOnePage;
import mission.CheckoutStepTwoPage;

public class CheckoutStepDefinitions {

    CheckoutStepOnePage checkoutStepOnePage;
    CheckoutStepTwoPage checkoutStepTwoPage;

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

        System.out.println("Calculated Total: " + calculatedTotal);
        System.out.println("Cart Subtotal (" + type + "): " + cartSubTotal);

        Assert.assertEquals(calculatedTotal, cartSubTotal);

    }

    @And("a {string} rate of {int} % is applied to the total")
    public void a_rate_of_is_applied_to_the_total(String type, Integer taxRate) {
        double expectedTax = checkoutStepTwoPage.calculateTaxRate("tax", taxRate);
        double displayedTax = checkoutStepTwoPage.getCartSubtotal("tax");

        System.out.println("Expected Tax: " + expectedTax + "Displayed Tax: " + displayedTax);

        Assert.assertEquals(displayedTax, expectedTax);

    }
}
