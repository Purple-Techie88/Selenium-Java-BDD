package stepDefinitions.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import mission.LoadProp;

public class LoginStepDefinitions {

    private final String headerName = LoadProp.getProperty("apiHeader");
    private final String headerValue = LoadProp.getProperty("apiKey");
    private Response loginAsUser;

    @Given("^I login (successfully|unsuccessfully) with the following data$")
    public void iLoginWithTheFollowingData(String loginType, io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        String email = data.get(0).get("Email");
        String password = data.get(0).get("Password");

        RequestSpecification request = RestAssured
                .given()
                .baseUri("https://reqres.in")
                .basePath("/api/login")
                .contentType(ContentType.JSON)
                .header(headerName, headerValue);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", email);
        if (password != null && !password.isEmpty()) {
            requestBody.put("password", password);
        }

        loginAsUser = request
                .body(requestBody)
                .when()
                .post();

        System.out.println("Login attempt (" + loginType + ") response: " + loginAsUser.getBody().asString());
    }

    @Then("^I should get a response code of (\\d+)$")
    public void iShouldGetAResponseCodeOf(int responseCode) {
        loginAsUser.then().statusCode(responseCode);

    }
    @And("^I should see the following response message:")
    public void iShouldSeeTheFollowingResponseMessage(DataTable dt) {
        Map<String, String> errorData = dt.asMaps(String.class, String.class).get(0);
        String expectedError = errorData.get("error");

        String actualError = loginAsUser.jsonPath().getString("error");
        Assert.assertEquals(actualError, expectedError, "Error message does not match!");
    }
}
