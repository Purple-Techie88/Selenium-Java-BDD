// package stepDefinitions.ui;

// import java.util.Map;

// import io.cucumber.datatable.DataTable;
// import io.cucumber.java.en.And;
// import io.cucumber.java.en.Given;
// import mission.LoginPage;

// public class LoginStepDefinitions {
        
//     private final Hooks hooks;
//     private LoginPage loginPage;

//     public LoginStepDefinitions(Hooks hooks) {
//         this.hooks = hooks;
//         this.loginPage = hooks.loginPage;
//     }


//     @Given("^I am on the home page$")
//     public void iAmOnTheHomePage() {
//         loginPage.visitLoginPage();
//     }

//     @And("I log in with the following details")
//     public void iLoginWithUsernameAndPassword(DataTable dataTable) {
//         Map<String, String> credentials = dataTable.asMaps(String.class, String.class).get(0);

//         String username = credentials.get("userName");
//         String password = credentials.get("password");

//         loginPage.userLogin(username, password);
//     }
    
// }

package stepDefinitions.ui;

import java.util.Map;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import mission.LoginPage;

public class LoginStepDefinitions {

    private  Hooks hooks; // Inject hooks
    private final LoginPage loginPage;

    // Constructor injection
    public LoginStepDefinitions(Hooks hooks) {
        this.hooks = hooks;
        this.loginPage = hooks.loginPage; // Use the page object initialized in Hooks
    }

    @Given("^I am on the home page$")
    public void iAmOnTheHomePage() {
        loginPage.visitLoginPage();
    }

    @And("I log in with the following details")
    public void iLoginWithUsernameAndPassword(DataTable dataTable) {
        Map<String, String> credentials = dataTable.asMaps(String.class, String.class).get(0);
        loginPage.userLogin(credentials.get("userName"), credentials.get("password"));
    }
}
