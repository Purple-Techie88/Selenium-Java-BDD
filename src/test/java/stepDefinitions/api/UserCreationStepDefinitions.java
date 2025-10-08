package stepDefinitions.api;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import mission.LoadProp;

public class UserCreationStepDefinitions {

    private final String baseUrl = LoadProp.getProperty("API_BASE_URL");
    private final String headerName = LoadProp.getProperty("apiHeader");
    private final String headerValue = LoadProp.getProperty("apiKey");
    private Response createUser;

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
}
