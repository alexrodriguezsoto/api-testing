package util;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.http.HttpStatus.SC_OK;

public class API_Utils {
    private String path;
    public static Response response;
    public static Map<String, String> workspaceID;
    public List<String> jsonResponse;
    public String workSpaceId;
    public String userId;
    public String id2;

    @BeforeClass
    public String setUpLogInAndToken() { // this method includes BaseURI, path, username, password, and token
        RestAssured.baseURI = "https://api.octoperf.com";
        path = "/public/users/login";

        HashMap<String, Object> map = new HashMap<String, Object>();
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
