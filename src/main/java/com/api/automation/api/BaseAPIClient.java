package com.api.automation.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * BaseAPIClient - Core API client for handling HTTP requests
 * Provides methods for GET, POST, PUT, PATCH, DELETE operations
 * Supports authentication, headers, and request/response logging
 */
public class BaseAPIClient {

    private static final Logger logger = LogManager.getLogger(BaseAPIClient.class);
    
    private String baseURL;
    private RequestSpecification requestSpecification;
    private Map<String, String> headers;
    private Map<String, String> queryParams;
    private String authToken;

    public BaseAPIClient(String baseURL) {
        this.baseURL = baseURL;
        this.headers = new HashMap<>();
        this.queryParams = new HashMap<>();
        initializeRequestSpecification();
    }

    /**
     * Initialize RequestSpecification with base URL
     */
    private void initializeRequestSpecification() {
        this.requestSpecification = RestAssured.given()
                .baseUri(baseURL)
                .contentType(ContentType.JSON);
        logger.info("Base URL set to: " + baseURL);
    }

    /**
     * Add header to the request
     */
    public BaseAPIClient addHeader(String key, String value) {
        headers.put(key, value);
        logger.debug("Header added: " + key + " = " + value);
        return this;
    }

    /**
     * Add multiple headers
     */
    public BaseAPIClient addHeaders(Map<String, String> headers) {
        this.headers.putAll(headers);
        logger.debug("Multiple headers added");
        return this;
    }

    /**
     * Add Bearer Token for authentication
     */
    public BaseAPIClient addBearerToken(String token) {
        this.authToken = token;
        addHeader("Authorization", "Bearer " + token);
        logger.debug("Bearer token added");
        return this;
    }

    /**
     * Add Basic Authentication
     */
    public BaseAPIClient addBasicAuth(String username, String password) {
        requestSpecification.auth().basic(username, password);
        logger.debug("Basic authentication added for user: " + username);
        return this;
    }

    /**
     * Add API Key authentication
     */
    public BaseAPIClient addApiKey(String apiKey) {
        addHeader("X-API-Key", apiKey);
        logger.debug("API Key authentication added");
        return this;
    }

    /**
     * Add query parameter
     */
    public BaseAPIClient addQueryParam(String key, String value) {
        queryParams.put(key, value);
        logger.debug("Query param added: " + key + " = " + value);
        return this;
    }

    /**
     * Add multiple query parameters
     */
    public BaseAPIClient addQueryParams(Map<String, String> params) {
        this.queryParams.putAll(params);
        logger.debug("Multiple query params added");
        return this;
    }

    /**
     * Build request specification with headers and query params
     */
    private RequestSpecification buildRequest() {
        RequestSpecification spec = RestAssured.given()
                .baseUri(baseURL)
                .contentType(ContentType.JSON);

        // Add headers
        for (Map.Entry<String, String> header : headers.entrySet()) {
            spec.header(header.getKey(), header.getValue());
        }

        // Add query parameters
        for (Map.Entry<String, String> param : queryParams.entrySet()) {
            spec.queryParam(param.getKey(), param.getValue());
        }

        return spec;
    }

    /**
     * GET request
     */
    public Response get(String endpoint) {
        logger.info("Executing GET request to: " + endpoint);
        Response response = buildRequest().get(endpoint);
        logResponse(response);
        return response;
    }

    /**
     * GET request with path parameter
     */
    public Response get(String endpoint, Object... pathParams) {
        logger.info("Executing GET request to: " + endpoint + " with path params");
        Response response = buildRequest().get(endpoint, pathParams);
        logResponse(response);
        return response;
    }

    /**
     * POST request with body
     */
    public Response post(String endpoint, Object body) {
        logger.info("Executing POST request to: " + endpoint);
        logger.debug("Request body: " + body);
        Response response = buildRequest().body(body).post(endpoint);
        logResponse(response);
        return response;
    }

    /**
     * POST request without body
     */
    public Response post(String endpoint) {
        logger.info("Executing POST request to: " + endpoint);
        Response response = buildRequest().post(endpoint);
        logResponse(response);
        return response;
    }

    /**
     * PUT request with body
     */
    public Response put(String endpoint, Object body) {
        logger.info("Executing PUT request to: " + endpoint);
        logger.debug("Request body: " + body);
        Response response = buildRequest().body(body).put(endpoint);
        logResponse(response);
        return response;
    }

    /**
     * PATCH request with body
     */
    public Response patch(String endpoint, Object body) {
        logger.info("Executing PATCH request to: " + endpoint);
        logger.debug("Request body: " + body);
        Response response = buildRequest().body(body).patch(endpoint);
        logResponse(response);
        return response;
    }

    /**
     * DELETE request
     */
    public Response delete(String endpoint) {
        logger.info("Executing DELETE request to: " + endpoint);
        Response response = buildRequest().delete(endpoint);
        logResponse(response);
        return response;
    }

    /**
     * DELETE request with path parameter
     */
    public Response delete(String endpoint, Object... pathParams) {
        logger.info("Executing DELETE request to: " + endpoint + " with path params");
        Response response = buildRequest().delete(endpoint, pathParams);
        logResponse(response);
        return response;
    }

    /**
     * Log response details
     */
    private void logResponse(Response response) {
        logger.info("Response Status Code: " + response.getStatusCode());
        logger.debug("Response Body: " + response.getBody().asString());
        logger.debug("Response Headers: " + response.getHeaders());
    }

    /**
     * Reset headers and query params
     */
    public void reset() {
        headers.clear();
        queryParams.clear();
        authToken = null;
        logger.info("Request configuration reset");
    }

    /**
     * Get current base URL
     */
    public String getBaseURL() {
        return baseURL;
    }

    /**
     * Set new base URL
     */
    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
        initializeRequestSpecification();
    }
}
