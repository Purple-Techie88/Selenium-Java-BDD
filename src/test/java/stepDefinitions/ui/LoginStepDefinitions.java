package stepDefinitions.ui;

import java.util.Map;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import mission.LoginPage;

public class LoginStepDefinitions {


    LoginPage loginPage;

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

}