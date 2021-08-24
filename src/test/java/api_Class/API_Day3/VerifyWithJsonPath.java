package api_Class.API_Day3;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class VerifyWithJsonPath {
    private String path;
    public static Response response;
    public static String jsonAsString;
    public static Map<String, String> listToken;

    @Test
    public void TestResponseBodyWithJsonPath() {
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

        JsonPath jsonpathEvaluator = response.jsonPath();
        String token = jsonpathEvaluator.get("token").toString();

        // print to help us Visualize TOKEN
        System.out.println(token);

        // We'll use HashMap because the token can be used in many requests
        listToken = new HashMap<String, String>();
        listToken.put("Authorization", token);
        System.out.println(listToken);
        System.out.println("Log in Successfully");
        System.out.println("======== Start Testing ========");

    }

    @Test
    public void hashmapQueryParams() {
        RestAssured.baseURI = "https://api.octoperf.com";
        RestAssured.given()
                .headers(listToken)
                .when()
                .get("/workspaces/member-of").prettyPrint();
    }
}
