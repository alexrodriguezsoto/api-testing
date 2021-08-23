package api_Class;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

public class VerifyPostRequests {
    private String path;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api.octoperf.com:443/public/users";
        path = "/login";
    }

    // Query Params using HashMap to pass parameters as map
    @Test
    public void hashmapQueryParams() {
        HashMap<String, Object> map = new HashMap<String,Object>();
        map.put("password", "test12");
        map.put("username", "tla.jiraone@gmail.com");

        RestAssured.given()
                .queryParams(map)
                .when()
                .post(path)
                .then()
                .assertThat().statusCode(200);
        System.out.println("Verify Status code");
    }

    //validate json format
    @Test
    public void verifyJsonFormat() {
        HashMap<String, Object> map = new HashMap<String,Object>();
        map.put("password", "test12");
        map.put("username", "tla.jiraone@gmail.com");

        RestAssured.given()
                .queryParams(map)
                .when()
                .post(path)
                .then()
                .assertThat().statusCode(200).and().
                contentType(ContentType.JSON);
        System.out.println("Verify Content Type JSON successful");

    }
}
