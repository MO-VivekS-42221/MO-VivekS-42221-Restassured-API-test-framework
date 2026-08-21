package com.api.automation.tests;

import com.api.automation.api.BaseAPIClient;
import com.api.automation.pojo.Post;
import com.api.automation.utils.Logger;
import io.restassured.response.Response;
import org.testng.annotations.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * PostAPITest - Comprehensive tests for Post API endpoints
 * Tests CRUD operations on JSONPlaceholder API
 */
public class PostAPITest {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final String POSTS_ENDPOINT = "/posts";
    private BaseAPIClient apiClient;
    private Logger logger = new Logger();

    @BeforeClass
    public void setup() {
        apiClient = new BaseAPIClient(BASE_URL);
        logger.info("PostAPITest - Setup completed");
    }

    @AfterClass
    public void teardown() {
        apiClient.reset();
        logger.info("PostAPITest - Teardown completed");
    }

    /**
     * Test GET all posts
     */
    @Test(description = "Verify GET all posts returns 200 and contains post list")
    public void testGetAllPosts() {
        logger.info("Testing GET all posts");
        
        Response response = apiClient.get(POSTS_ENDPOINT);
        
        response.then()
                .statusCode(200)
                .contentType("application/json")
                .body("", hasSize(greaterThan(0)))
                .body("[0].id", notNullValue())
                .body("[0].userId", notNullValue())
                .body("[0].title", notNullValue())
                .body("[0].body", notNullValue());
        
        logger.info("GET all posts test passed");
    }

    /**
     * Test GET post by ID
     */
    @Test(description = "Verify GET post by ID returns correct post details")
    public void testGetPostById() {
        logger.info("Testing GET post by ID");
        int postId = 1;
        
        Response response = apiClient.get(POSTS_ENDPOINT + "/{id}", postId);
        
        response.then()
                .statusCode(200)
                .contentType("application/json")
                .body("id", equalTo(postId))
                .body("userId", notNullValue())
                .body("title", notNullValue())
                .body("body", notNullValue());
        
        Post post = response.as(Post.class);
        assert post.getId() == postId;
        
        logger.info("GET post by ID test passed");
    }

    /**
     * Test GET posts by userId
     */
    @Test(description = "Verify GET posts filtered by userId")
    public void testGetPostsByUserId() {
        logger.info("Testing GET posts by userId");
        int userId = 1;
        
        Response response = apiClient
                .addQueryParam("userId", String.valueOf(userId))
                .get(POSTS_ENDPOINT);
        
        response.then()
                .statusCode(200)
                .body("", hasSize(greaterThan(0)))
                .body("[0].userId", equalTo(userId));
        
        apiClient.reset();
        logger.info("GET posts by userId test passed");
    }

    /**
     * Test POST create post
     */
    @Test(description = "Verify POST create new post returns 201")
    public void testCreatePost() {
        logger.info("Testing POST create post");
        
        Post newPost = new Post();
        newPost.setUserId(1);
        newPost.setTitle("Test Post");
        newPost.setBody("This is a test post body");
        
        Response response = apiClient.post(POSTS_ENDPOINT, newPost);
        
        response.then()
                .statusCode(201)
                .body("userId", equalTo(1))
                .body("title", equalTo("Test Post"))
                .body("body", equalTo("This is a test post body"));
        
        logger.info("POST create post test passed");
    }

    /**
     * Test PUT update post
     */
    @Test(description = "Verify PUT update post returns 200")
    public void testUpdatePost() {
        logger.info("Testing PUT update post");
        int postId = 1;
        
        Post updatedPost = new Post();
        updatedPost.setUserId(1);
        updatedPost.setId(postId);
        updatedPost.setTitle("Updated Title");
        updatedPost.setBody("Updated body content");
        
        Response response = apiClient.put(POSTS_ENDPOINT + "/{id}", updatedPost, postId);
        
        response.then()
                .statusCode(200)
                .body("title", equalTo("Updated Title"))
                .body("body", equalTo("Updated body content"));
        
        logger.info("PUT update post test passed");
    }

    /**
     * Test PATCH partially update post
     */
    @Test(description = "Verify PATCH partially update post")
    public void testPartialUpdatePost() {
        logger.info("Testing PATCH partial update post");
        int postId = 1;
        
        String patchBody = "{ \"title\": \"Patched Title\" }";
        
        Response response = given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(patchBody)
                .when()
                .patch(POSTS_ENDPOINT + "/{id}", postId);
        
        response.then()
                .statusCode(200)
                .body("title", equalTo("Patched Title"));
        
        logger.info("PATCH partial update post test passed");
    }

    /**
     * Test DELETE post
     */
    @Test(description = "Verify DELETE post returns 200")
    public void testDeletePost() {
        logger.info("Testing DELETE post");
        int postId = 1;
        
        Response response = apiClient.delete(POSTS_ENDPOINT + "/{id}", postId);
        
        response.then()
                .statusCode(200);
        
        logger.info("DELETE post test passed");
    }

    /**
     * Test GET non-existent post
     */
    @Test(description = "Verify GET non-existent post returns 404")
    public void testGetNonExistentPost() {
        logger.info("Testing GET non-existent post");
        int postId = 99999;
        
        Response response = apiClient.get(POSTS_ENDPOINT + "/{id}", postId);
        
        response.then()
                .statusCode(404);
        
        logger.info("GET non-existent post test passed");
    }

    /**
     * Test post response structure
     */
    @Test(description = "Verify post response has all required fields")
    public void testPostResponseStructure() {
        logger.info("Testing post response structure");
        
        Response response = apiClient.get(POSTS_ENDPOINT + "/1");
        
        response.then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("userId", notNullValue())
                .body("title", notNullValue())
                .body("body", notNullValue());
        
        logger.info("Post response structure test passed");
    }

    /**
     * Test post title length validation
     */
    @Test(description = "Verify post title has minimum length")
    public void testPostTitleLength() {
        logger.info("Testing post title length");
        
        Response response = apiClient.get(POSTS_ENDPOINT + "/1");
        
        response.then()
                .statusCode(200)
                .body("title", notNullValue());
        
        String title = response.path("title");
        assert title.length() > 0 : "Post title should not be empty";
        
        logger.info("Post title length test passed");
    }

    /**
     * Test pagination with query parameters
     */
    @Test(description = "Verify pagination with _start and _limit")
    public void testPostPagination() {
        logger.info("Testing post pagination");
        
        Response response = apiClient
                .addQueryParam("_start", "0")
                .addQueryParam("_limit", "10")
                .get(POSTS_ENDPOINT);
        
        response.then()
                .statusCode(200)
                .body("", hasSize(lessThanOrEqualTo(10)));
        
        apiClient.reset();
        logger.info("Post pagination test passed");
    }
}
