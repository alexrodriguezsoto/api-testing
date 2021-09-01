package util;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import java.util.HashMap;
import java.util.Map;
import static org.apache.http.HttpStatus.SC_OK;


public class API_Utils {
    public String path;


    @BeforeClass
    public String setUpLogInAndToken() { // this method includes BaseURI, path, username, password, and token
       RestAssured.baseURI = ConfigurationReader.getProperty("url");
        path = ConfigurationReader.getProperty("loginPath");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("password", "test12");
        map.put("username", "tla.jiraone@gmail.com");

        return RestAssured.given()
                .queryParams(map)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .baseUri(RestAssured.baseURI)
                .post(path)
                .then()
                .statusCode(SC_OK)
                .extract()
                .body()
                .jsonPath()
                .get("token");
    }
}
