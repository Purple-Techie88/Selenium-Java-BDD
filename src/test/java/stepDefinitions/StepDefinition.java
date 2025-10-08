package stepDefinitions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import mission.LoadProp;

public class StepDefinition {

    // Base URL can be moved to properties later
    private final String baseUrl = LoadProp.getProperty("API_BASE_URL");
    private final String headerName = LoadProp.getProperty("apiHeader");
    private final String headerValue = LoadProp.getProperty("apiKey");
    private Response firstPage, pageResponse, singleUser, createUser, loginAsUser, secondPage;
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

    @Given("I create a user with following {string} {string}")
    public void iCreateUserWithFollowing(String userName, String job) {

        createUser = RestAssured
                .given()
                .baseUri(baseUrl)
                .basePath("api/users")
                .contentType("application/json")
                .header(headerName, headerValue)
                .body("{\"name\":\"" + userName + "\", \"job\":\"" + job + "\"}")
                .when()
                .post();

        Assert.assertEquals(createUser.getStatusCode(), 201, "User creation failed for: " + userName);

    }

    @Then("response should contain the following data {string} {string}")
    public void iReceiveErrorCodeInResponse(String expectedName, String expectedJob) {

        String actualName = createUser.jsonPath().getString("name");
        String actualJob = createUser.jsonPath().getString("job");
        String actualId = createUser.jsonPath().getString("id");
        String actualCreatedAt = createUser.jsonPath().getString("createdAt");

        Assert.assertEquals(actualName, expectedName, "Name does not match");
        Assert.assertEquals(actualJob, expectedJob, "Job does not match");
        Assert.assertNotNull(actualId, "ID should not be be empty");
        Assert.assertNotNull(actualCreatedAt, "createdAt should not be empty");

        System.out.println("Created user: " + actualName + " - " + actualJob + " - ID: " + actualId);

    }

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

    @Then("I should see that every user has a unique id")
    public void iShouldSeeThatEveryUserHasAUniqueID() {

        Set<Integer> uniqueIds = new HashSet<>(allUserIds);

        Assert.assertEquals(uniqueIds.size(), allUserIds.size(), "User IDs are not unique");

        System.out.println("All user IDs are unique: " + allUserIds);
    }
}
