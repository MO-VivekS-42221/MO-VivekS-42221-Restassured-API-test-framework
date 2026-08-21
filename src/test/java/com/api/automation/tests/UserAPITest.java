package com.api.automation.tests;

import com.api.automation.api.BaseAPIClient;
import com.api.automation.pojo.User;
import com.api.automation.utils.Logger;
import io.restassured.response.Response;
import org.testng.annotations.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * UserAPITest - Comprehensive tests for User API endpoints
 * Tests CRUD operations on JSONPlaceholder API
 */
public class UserAPITest {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final String USERS_ENDPOINT = "/users";
    private BaseAPIClient apiClient;
    private Logger logger = new Logger();

    @BeforeClass
    public void setup() {
        apiClient = new BaseAPIClient(BASE_URL);
        logger.info("UserAPITest - Setup completed");
    }

    @AfterClass
    public void teardown() {
        apiClient.reset();
        logger.info("UserAPITest - Teardown completed");
    }

    /**
     * Test GET all users
     */
    @Test(description = "Verify GET all users returns 200 and contains user list")
    public void testGetAllUsers() {
        logger.info("Testing GET all users");
        
        Response response = apiClient.get(USERS_ENDPOINT);
        
        response.then()
                .statusCode(200)
                .contentType("application/json")
                .body("", hasSize(greaterThan(0)))
                .body("[0].id", notNullValue())
                .body("[0].name", notNullValue())
                .body("[0].email", notNullValue());
        
        logger.info("GET all users test passed");
    }

    /**
     * Test GET user by ID
     */
    @Test(description = "Verify GET user by ID returns correct user details")
    public void testGetUserById() {
        logger.info("Testing GET user by ID");
        int userId = 1;
        
        Response response = apiClient.get(USERS_ENDPOINT + "/{id}", userId);
        
        response.then()
                .statusCode(200)
                .contentType("application/json")
                .body("id", equalTo(userId))
                .body("name", notNullValue())
                .body("username", notNullValue())
                .body("email", notNullValue());
        
        User user = response.as(User.class);
        assert user.getId() == userId;
        
        logger.info("GET user by ID test passed");
    }

    /**
     * Test GET user with query parameters
     */
    @Test(description = "Verify GET users with query parameters")
    public void testGetUsersWithQueryParams() {
        logger.info("Testing GET users with query parameters");
        
        Response response = apiClient
                .addQueryParam("id", "1")
                .get(USERS_ENDPOINT);
        
        response.then()
                .statusCode(200)
                .body("", hasSize(1))
                .body("[0].id", equalTo(1));
        
        apiClient.reset();
        logger.info("GET users with query parameters test passed");
    }

    /**
     * Test POST create user
     */
    @Test(description = "Verify POST create new user returns 201")
    public void testCreateUser() {
        logger.info("Testing POST create user");
        
        User newUser = new User();
        newUser.setId(999);
        newUser.setName("Test User");
        newUser.setUsername("testuser");
        newUser.setEmail("test@example.com");
        newUser.setPhone("1234567890");
        newUser.setWebsite("http://test.com");
        
        Response response = apiClient.post(USERS_ENDPOINT, newUser);
        
        response.then()
                .statusCode(201)
                .body("name", equalTo("Test User"))
                .body("email", equalTo("test@example.com"));
        
        logger.info("POST create user test passed");
    }

    /**
     * Test PUT update user
     */
    @Test(description = "Verify PUT update user returns 200")
    public void testUpdateUser() {
        logger.info("Testing PUT update user");
        int userId = 1;
        
        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setName("Updated Name");
        updatedUser.setUsername("updatedusername");
        updatedUser.setEmail("updated@example.com");
        
        Response response = apiClient.put(USERS_ENDPOINT + "/{id}", updatedUser, userId);
        
        response.then()
                .statusCode(200)
                .body("name", equalTo("Updated Name"))
                .body("email", equalTo("updated@example.com"));
        
        logger.info("PUT update user test passed");
    }

    /**
     * Test PATCH partially update user
     */
    @Test(description = "Verify PATCH partially update user")
    public void testPartialUpdateUser() {
        logger.info("Testing PATCH partial update user");
        int userId = 1;
        
        String patchBody = "{ \"name\": \"Patched Name\", \"email\": \"patched@example.com\" }";
        
        Response response = given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(patchBody)
                .when()
                .patch(USERS_ENDPOINT + "/{id}", userId);
        
        response.then()
                .statusCode(200)
                .body("name", equalTo("Patched Name"));
        
        logger.info("PATCH partial update user test passed");
    }

    /**
     * Test DELETE user
     */
    @Test(description = "Verify DELETE user returns 200")
    public void testDeleteUser() {
        logger.info("Testing DELETE user");
        int userId = 1;
        
        Response response = apiClient.delete(USERS_ENDPOINT + "/{id}", userId);
        
        response.then()
                .statusCode(200);
        
        logger.info("DELETE user test passed");
    }

    /**
     * Test GET non-existent user
     */
    @Test(description = "Verify GET non-existent user returns 404")
    public void testGetNonExistentUser() {
        logger.info("Testing GET non-existent user");
        int userId = 99999;
        
        Response response = apiClient.get(USERS_ENDPOINT + "/{id}", userId);
        
        response.then()
                .statusCode(404);
        
        logger.info("GET non-existent user test passed");
    }

    /**
     * Test user response structure
     */
    @Test(description = "Verify user response has all required fields")
    public void testUserResponseStructure() {
        logger.info("Testing user response structure");
        
        Response response = apiClient.get(USERS_ENDPOINT + "/1");
        
        response.then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("name", notNullValue())
                .body("username", notNullValue())
                .body("email", notNullValue())
                .body("address", notNullValue())
                .body("phone", notNullValue())
                .body("website", notNullValue())
                .body("company", notNullValue());
        
        logger.info("User response structure test passed");
    }

    /**
     * Test user email format validation
     */
    @Test(description = "Verify user email format is valid")
    public void testUserEmailFormat() {
        logger.info("Testing user email format");
        
        Response response = apiClient.get(USERS_ENDPOINT);
        
        response.then()
                .statusCode(200)
                .body("[0].email", matchesPattern("^[A-Za-z0-9+_.-]+@(.+)$"));
        
        logger.info("User email format test passed");
    }
}
