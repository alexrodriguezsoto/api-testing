package api_Class.API_Day3;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class VerifyPostRequests {
    private String path;
    public static Response response;
    public static String jsonAsString;
    public static Map<String, String> listToken;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api.octoperf.com:443/public/users";
        path = "/login";
        // We have the base URI with endpoint /login, but in order to log in we'll use hashmap for password and ussername
        // You can log in in any way you want using query params, etc, but for now we'll just use hashmap
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("password", "test12");
        map.put("username", "tla.jiraone@gmail.com");

// Response class will allow us to get the response from the request we send to an API server, and Rest-Assured will
// help us fetch data needed to automate tests.
        response = RestAssured.given()
                .queryParams(map)
                .when()
                .post(path)
                .then()
                .contentType(ContentType.JSON)
                .extract().response();
        //JsonPath is a Rest-Assured library which is used to manipulate json data. JsonPath is mostly used for validating response body.
        JsonPath jsonpathEvaluator = response.jsonPath();
        String token = jsonpathEvaluator.get("token").toString();

        // print to help us Visualize TOKEN
        System.out.println(token); // we can see that this token it's stored as key and value

        // We'll use HashMap because the token can be used in many requests
        listToken = new HashMap<String, String>();
        listToken.put("Authorization", token);
        System.out.println(listToken);
        System.out.println("Log in Successfully");
        System.out.println("======== Start Testing ========");
    }

    // verify status code 200 and response body is application/json
    @Test
    public void verifyCodeAndJsonBody() {
        HashMap<String, Object> map = new HashMap<String,Object>();
        map.put("password", "test12");
        map.put("username", "tla.jiraone@gmail.com");

        RestAssured.given()
                .queryParams(map)
                .when()
                .post(path)
                .then()
                .assertThat().statusCode(200).and().contentType("application/json");
    }

    //verify response headers
    @Test
    public void verifyHeaders() {
        HashMap<String, Object> map = new HashMap<String,Object>();
        map.put("password", "test12");
        map.put("username", "tla.jiraone@gmail.com");

        RestAssured.given()
                .queryParams(map)
                .when()
                .post(path)
                .then()
                .assertThat().extract().headers();
    }
}
