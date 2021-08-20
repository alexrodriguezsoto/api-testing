package api_class1;

import com.sun.xml.bind.v2.model.core.TypeRef;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class getWorkspaceMemberByWorkspace_05 {

    //TODO: Student Practice

    private String path;
    public static Response response;
    public static String jsonAsString;
    public static Map<String, String> list;
    List<String> jsonResponse;

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
        list = new HashMap<String, String>();
        list.put("Authorization", token);
        System.out.println(list);
        System.out.println("Log in Successfully");
        System.out.println("======== Test Started ========");
    }

    @Test
    public void validateResponseBody() {
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

        String company = response.jsonPath().getString("id");
        System.out.println(company);

        //we have 1 array with 2 Json response body
        jsonResponse = response.jsonPath().getList("id");
        System.out.println(jsonResponse.get(1));
    }

    @Test
    public void validateResponseBody2() {
        String workspaceId = jsonResponse.get(1);
        RestAssured.baseURI = "https://api.octoperf.com";
        response = RestAssured.given()
                .headers(list)
                .when()
                .get("workspaces/members/by-workspace/"+workspaceId)
                .then()
                .extract().response();

        Assert.assertEquals(200, response.statusCode());
        System.out.println(response.prettyPrint());
    }

}
