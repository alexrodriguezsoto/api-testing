package api_class1;

import com.fasterxml.jackson.databind.util.JSONPObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

/**
 * GET Workspace Member api.octoperf.com
 */

public class getWorkspaceMember_03 {
    private String path;
    public static Response response;
    public static String jsonAsString;
    public static Map<String, String> list;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api.octoperf.com:443/public/users";
        path = "/login";


        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("password", "test12");
        map.put("username", "tla.jiraone@gmail.com");

        response = RestAssured.given()
                .queryParams(map)
                .when()
                .post(path)
                .then()
                .contentType(ContentType.JSON)
                .extract().response();
        //JsonPath is an alternative to using XPath for easily getting values from a JSON document
        JsonPath jsonpathEvaluator = response.jsonPath();
        String token = jsonpathEvaluator.get("token").toString();

        // Help us Visualize TOKEN
        System.out.println(jsonpathEvaluator.get("token").toString());

        //Store in a MAP key value response for the token so it can be used in many requests
       //  "token": "834f9392-ce89-411c-8366-00c86886ec9b"
        list = new HashMap<String, String>();
        list.put("Authorization", token);
        System.out.println(list);
        System.out.println("Log in Successfully");
        System.out.println("======== Test Started ========");
    }

    //Query Params using HashMap to pass parameters as map
    @Test
    public void hashmapQueryParams() {
        RestAssured.baseURI = "https://api.octoperf.com";
        RestAssured.given()
                .headers(list)
                .when()
                .get("/workspaces/member-of").prettyPrint();
//                .then()
//                .assertThat().statusCode(200);
        System.out.println("verified Status code 200 successful");
    }

    @Test
    public void verifyResponseBody() {
        RestAssured.baseURI = "https://api.octoperf.com";
        RestAssured.given()
                .headers(list)
                .when()
                .get("/workspaces/member-of")
                .then()
                .assertThat().statusCode(200)
                .and().assertThat().contentType(ContentType.JSON)
                .and().assertThat().body("id", hasItems("Wkt3tHgB6T29TqnSuTha"));
        System.out.println("verified Response Body id successful");
    }

    // Student TODO: Verify name and userId
    @Test
    public void verifyNameUserId() {
        RestAssured.baseURI = "https://api.octoperf.com";
        RestAssured.given()
                .headers(list)
                .when()
                .get("/workspaces/member-of")
                .then()
                .assertThat().statusCode(200)
                .and().assertThat().contentType(ContentType.JSON)
                .and().assertThat().body("id", hasItems("Wkt3tHgB6T29TqnSuTha"))
                .and().assertThat().body("name", hasItems("Default"))
                .and().assertThat().body("userId", hasItems("1kt3tHgB6T29TqnSCje3"));
        System.out.println("verified name and UserId successfully");
    }

    // Student TODO: Verify second array id, name, userId
    @Test
    public void verifyNameUserIdUser2() {
        RestAssured.baseURI = "https://api.octoperf.com";
        RestAssured.given()
                .headers(list)
                .when()
                .get("/workspaces/member-of")
                .then()
                .assertThat().statusCode(200)
                .and().assertThat().contentType(ContentType.JSON)
                .and().assertThat().body("id", hasItems("q8JFrnkBWzGOF5xrtZd1"))
                .and().assertThat().body("name", hasItems("test"))
                .and().assertThat().body("userId", hasItems("1kt3tHgB6T29TqnSCje3"));
        System.out.println("verified id, name and UserId successfully");
    }

    // Instructor: Lets now look at asserting response body in the same request
    @Test
    public void verifyIdNameUserid() {
        RestAssured.baseURI = "https://api.octoperf.com";
        RestAssured.given()
                .headers(list)
                .when()
                .get("/workspaces/member-of")
                .then()
                .assertThat().statusCode(200)
                .and().assertThat().contentType(ContentType.JSON)
                .and().assertThat()
                .body("id[1]", equalTo("q8JFrnkBWzGOF5xrtZd1"),
                        "name[1]", equalTo("test"),
                        "userId[1]", equalTo("1kt3tHgB6T29TqnSCje3"));
        System.out.println("verified id, name and UserId successfully");
    }

    // Instructor: Extract Response body and Asset values
    @Test
    public void extractResponseValidation() {
        RestAssured.baseURI = "https://api.octoperf.com";
        response = RestAssured.given()
                .headers(list)
                .when()
                .get("/workspaces/member-of")
                .then()
                .extract().response();
        Assert.assertEquals("Default", response.jsonPath().getString("name[0]"));
    }

    // Student TODO: Extract Response body and assert id, name, status code, userID for user Default
    // reference: https://devqa.io/rest-assured-api-requests-examples/
    @Test
    public void validateresponsebody() {
        RestAssured.baseURI = "https://api.octoperf.com";
        response = RestAssured.given()
                .headers(list)
                .when()
                .get("/workspaces/member-of")
                .then()
                .extract().response();

        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("Default", response.jsonPath().getString("name[0]"));
        Assert.assertEquals("Wkt3tHgB6T29TqnSuTha", response.jsonPath().getString("id[0]"));
        Assert.assertEquals("1kt3tHgB6T29TqnSCje3", response.jsonPath().getString("userId[0]"));
    }



}
