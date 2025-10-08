package stepDefinitions.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import mission.LoadProp;

public class UserListStepDefinitions {

    private final String baseUrl = LoadProp.getProperty("API_BASE_URL");
    private final String headerName = LoadProp.getProperty("apiHeader");
    private final String headerValue = LoadProp.getProperty("apiKey");
    private Response firstPage, pageResponse, secondPage, singleUser;
    private List<Integer> allUserIds = new ArrayList<>();
    private int totalUsers;

    @Given("^I get the default list of users for on 1st page$")
    public void iGetTheDefaultListofusers() {
        firstPage = RestAssured
                .given()
                .baseUri(baseUrl)
                .basePath("/api/users")
                .param("page", 1)
                .header(headerName, headerValue)
                .when()
                .get();

        Assert.assertEquals(firstPage.getStatusCode(), 200);

        totalUsers = firstPage.jsonPath().getInt("total");
    }

    @When("I get the list of all users within every page")
    public void iGetTheListOfAllUsers() {
        int totalPages = firstPage.jsonPath().getInt("total_pages");

        for (int page = 1; page <= totalPages; page++) {
            pageResponse = RestAssured
                    .given()
                    .baseUri(baseUrl)
                    .basePath("/api/users/")
                    .param("page", 1)
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

    @Then("I should see that every user has a unique id")
    public void iShouldSeeThatEveryUserHasAUniqueID() {

        Set<Integer> uniqueIds = new HashSet<>(allUserIds);
        System.out.println("All user IDs are unique: " + allUserIds);

        Assert.assertEquals(uniqueIds.size(), allUserIds.size(), "User IDs are not unique");

    }

    @Given("^I wait for the user list to load$")
    public void iWaitForUserListToLoad() throws InterruptedException {
        Thread.sleep(2000);

        allUserIds = new ArrayList<>();

        firstPage = RestAssured
                .given()
                .baseUri(baseUrl)
                .basePath("/api/users")
                .param("page", 1)
                .header(headerName, headerValue)
                .when()
                .get();

        int totalPages = firstPage.jsonPath().getInt("total_pages");

        allUserIds.addAll(firstPage.jsonPath().getList("data.id"));

        for (int page = 2; page <= totalPages; page++) {
            secondPage = RestAssured
                    .given()
                    .baseUri(baseUrl)
                    .basePath("/api/users")
                    .param("page", page)
                    .header(headerName, headerValue)
                    .when()
                    .get();
            allUserIds.addAll(secondPage.jsonPath().getList("data.id"));

            Set<Integer> uniqueIds = new HashSet<>(allUserIds);
            Assert.assertEquals(uniqueIds.size(), allUserIds.size(), "Duplicate user IDs found!");

        }
    }

    @Given("I make a search for user {int}")
    public void iMakeASearchForUser(int userId) {

        singleUser = RestAssured
                .given()
                .baseUri(baseUrl)
                .basePath("api/users/{id}")
                .pathParam("id", userId)
                .header(headerName, headerValue)
                .when()
                .get();
    }

    @Then("I should see the following user data")
    public void IShouldSeeFollowingUserData(DataTable dt) {
        Map<String, String> expectedUser = dt.asMaps(String.class, String.class).get(0);
        String expectedFirstName = expectedUser.get("first_name");
        String expectedEmail = expectedUser.get("email");

        Map<String, Object> actualUser = singleUser.jsonPath().getMap("data");

        Assert.assertEquals(actualUser.get("first_name"), expectedFirstName, "First name does not match");
        Assert.assertEquals(actualUser.get("email"), expectedEmail, "Email does not match");

        System.out.println("Verified user: " + actualUser.get("first_name") + " - " + actualUser.get("email"));

    }

    @Then("I receive error code {int} in response")
    public void iReceiveErrorCodeInResponse(int responseCode) {
        Assert.assertEquals(singleUser.getStatusCode(), responseCode,
                "Expected status code does not match actual status code");
    }
}
