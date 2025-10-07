package stepDefinitions;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import mission.LoadProp;

public class StepDefinition {

    // Base URL can be moved to properties later
    private final String baseUrl = LoadProp.getProperty("API_BASE_URL");
    private final String headerName = LoadProp.getProperty("apiHeader");
    private final String headerValue = LoadProp.getProperty("apiKey");
    private Response response, pageResponse;
    private final List<Integer> allUserIds = new ArrayList<>();
    private int totalUsers;

    @Given("^I get the default list of users for on 1st page$")
    public void iGetTheDefaultListofusers() {
        response = RestAssured
                .given()
                .baseUri(baseUrl)
                .basePath("/api/users?page=1")
                // .param("page", 1)
                .header(headerName, headerValue)
                .when()
                .get();

        Assert.assertEquals(response.getStatusCode(), 200);

        totalUsers = response.jsonPath().getInt("total");
    }

    @When("I get the list of all users within every page")
    public void iGetTheListOfAllUsers() {
        int totalPages = response.jsonPath().getInt("total_pages");

        for (int page = 1; page <= totalPages; page++) {
            pageResponse = RestAssured
                    .given()
                    .baseUri(baseUrl)
                    .basePath("/api/users")
                    .param("page", page)
                    .header(headerName, headerValue)
                    .when()
                    .get();
            Assert.assertEquals(pageResponse.getStatusCode(), 200);

            List<Integer> userIds = pageResponse.jsonPath().getList("data.id");
            allUserIds.addAll(userIds);
        }
    }

    @Then("I should see total users count equals the number of user ids")
    public void iShouldMatchTotalCount() {
        Assert.assertEquals(allUserIds.size(), totalUsers);

    }

    // @Given("I make a search for user (.*)")
    // public void iMakeASearchForUser(String sUserID) {
    //     singleUser = RestAssured
    //     .given()
    //     .baseUri(baseUrl)
    //     .param("page", 1)
    //     .header("x-api-key", "reqres-free-v1")
    //     .get();
    //     totalUsers = firstPageResponse.jsonPath().getInt("total");
    // }
    @Then("I should see the following user data")
    public void iMakeASearchForUser(String sUserID) {

    }
    // @Then("I receive error code (.*) in response")
    // public void iReceiveErrorCodeInResponse(int responseCode) {
    // }
    //     @Given("I create a user with following (.*) (.*)")
    //     public void iCreateUserWithFollowing(String sUsername, String sJob) {
    //     }
    //     @Then("response should contain the following data")
    //     public void iReceiveErrorCodeInResponse(DataTable dt) {
    //     }
    //     @Given("I login unsuccessfully with the following data")
    //     public void iLoginSuccesfullyWithFollowingData(DataTable dt) {
    //     @Given("^I wait for the user list to load$")
    //     @Then("I should see that every user has a unique id")
    //     }
    //     public void iShouldGetAResponseCodeOf(int responseCode) {
    //     @And("^I should see the following response message:$")
    //     }

}
