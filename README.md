# API Automation Framework - REST Assured

## Overview
This is a comprehensive REST API automation framework built using **REST Assured**, **TestNG**, and **Java**. The framework is designed to test APIs efficiently with reusable components, detailed reporting, and best practices for API testing.

## Features
- ✅ **BDD Style Testing** - Write tests in Gherkin syntax
- ✅ **Comprehensive Logging** - Detailed logs for debugging
- ✅ **Parallel Execution** - Run multiple tests in parallel
- ✅ **Detailed Reporting** - ExtentReports integration
- ✅ **Data-Driven Testing** - Test with multiple datasets
- ✅ **Response Validation** - Schema and content validation
- ✅ **Reusable Components** - Utility classes and helpers
- ✅ **CI/CD Ready** - Easy integration with Jenkins/GitHub Actions

## Project Structure

```
MO-VivekS-42221-Restassured-API-test-framework/
├── src/
│   ├── main/
│   │   ├── java/com/api/automation/
│   │   │   ├── api/                    # API client classes
│   │   │   ├── utils/                  # Utility classes
│   │   │   ├── listeners/              # TestNG listeners
│   │   │   └── config/                 # Configuration classes
│   │   └── resources/
│   │       ├── config.properties       # Configuration file
│   │       └── log4j2.xml             # Logging configuration
│   │
│   └── test/
│       ├── java/com/api/automation/tests/  # Test classes
│       └── resources/                      # Test data
│
├── pom.xml                              # Maven configuration
├── testng.xml                           # TestNG configuration
├── README.md                            # Project documentation
└── .gitignore                           # Git ignore file
```

## Technology Stack

| Technology | Version | Purpose |
|-----------|---------|---------|
| Java | 11+ | Programming language |
| REST Assured | 5.3.0 | API testing library |
| TestNG | 7.7.0 | Test framework |
| Maven | 3.8+ | Build tool |
| Log4j2 | 2.20.0 | Logging framework |
| ExtentReports | 5.0.9 | Reporting |
| Jackson | 2.15.0 | JSON processing |
| Gson | 2.10.1 | JSON serialization |

## Getting Started

### Prerequisites
- Java 11 or higher
- Maven 3.8+
- Git

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/MO-VivekS-42221/MO-VivekS-42221-Restassured-API-test-framework.git
   cd MO-VivekS-42221-Restassured-API-test-framework
   ```

2. **Install dependencies**
   ```bash
   mvn clean install
   ```

3. **Update Configuration**
   - Edit `src/main/resources/config.properties`
   - Update base URL and other settings as needed

### Running Tests

**Run all tests:**
```bash
mvn test
```

**Run specific test suite:**
```bash
mvn test -Dsuite=testng.xml
```

**Run tests with specific tag:**
```bash
mvn test -Dgroups=smoke
```

**Run with parallel execution:**
```bash
mvn test -DthreadCount=5
```

## Configuration

### config.properties
The `config.properties` file contains all configuration settings:

```properties
base.url=https://jsonplaceholder.typicode.com
browser=chrome
timeout=10
report.path=test-output/reports
screenshot.path=test-output/screenshots
```

### Logging Configuration
Logging is configured via `log4j2.xml` and supports:
- Console logging
- File logging
- Different log levels per module

## API Testing Examples

### Example 1: Simple GET Request
```java
@Test
public void testGetAllPosts() {
    given()
        .when()
        .get("/posts")
        .then()
        .statusCode(200)
        .body("size()", greaterThan(0));
}
```

### Example 2: POST Request with Body
```java
@Test
public void testCreatePost() {
    JSONObject payload = new JSONObject();
    payload.put("userId", 1);
    payload.put("title", "Test Post");
    payload.put("body", "This is a test post");
    
    given()
        .contentType(ContentType.JSON)
        .body(payload.toString())
        .when()
        .post("/posts")
        .then()
        .statusCode(201)
        .body("id", notNullValue());
}
```

### Example 3: Request with Authentication
```java
@Test
public void testAuthenticatedRequest() {
    given()
        .header("Authorization", "Bearer " + token)
        .when()
        .get("/users")
        .then()
        .statusCode(200);
}
```

## Utility Classes

### JsonUtil
- `toJson()` - Convert object to JSON
- `fromJson()` - Convert JSON to object
- `prettyPrintJson()` - Pretty print JSON
- `isValidJson()` - Validate JSON format

### TimeUtil
- `sleep()` - Sleep for milliseconds
- `sleepInSeconds()` - Sleep for seconds
- `getCurrentTimestamp()` - Get current timestamp
- `waitUntil()` - Wait for condition

### FileUtil
- `createDirectory()` - Create directory
- `deleteFile()` - Delete file
- `fileExists()` - Check file existence
- `getFileNameWithTimestamp()` - Generate timestamped filename

### DataGenerator
- `generateRandomString()` - Generate random string
- `generateRandomEmail()` - Generate random email
- `generateRandomPhoneNumber()` - Generate phone number
- `generateRandomPassword()` - Generate password

### ConfigReader
- `getProperty()` - Read configuration property
- `getBaseURL()` - Get API base URL
- `getTimeout()` - Get timeout value

## Test Reports

After test execution, detailed reports are generated:
- **Location**: `test-output/reports/`
- **Format**: HTML with interactive dashboards
- **Screenshots**: Captured on failures

## CI/CD Integration

### GitHub Actions Example
```yaml
name: API Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - uses: actions/setup-java@v2
        with:
          java-version: '11'
      - run: mvn clean test
      - uses: actions/upload-artifact@v2
        if: always()
        with:
          name: test-reports
          path: test-output/
```

## Best Practices

1. **Use Page Object Model** - Organize API endpoints by resource
2. **Data-Driven Testing** - Use external data sources
3. **Assertion Libraries** - Use Hamcrest matchers
4. **Logging** - Log all requests and responses
5. **Error Handling** - Implement proper exception handling
6. **Code Reusability** - Create reusable utility methods
7. **Test Independence** - Tests should not depend on each other
8. **Descriptive Names** - Use clear, descriptive test names

## Troubleshooting

### Issue: Tests fail with connection timeout
**Solution**: Update timeout value in `config.properties`

### Issue: Reports not generating
**Solution**: Ensure `test-output/` directory exists and has write permissions

### Issue: Tests running slowly
**Solution**: Enable parallel execution in `testng.xml`

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support & Contact

For issues, questions, or suggestions:
- Create an issue on GitHub
- Contact: vivek.surwade@motilaloswal.com

## Changelog

### v1.0.0 (2026-08-21)
- Initial release
- API testing framework with REST Assured
- TestNG integration
- Comprehensive utilities
- Reporting capabilities

---

**Happy Testing! 🎯**
