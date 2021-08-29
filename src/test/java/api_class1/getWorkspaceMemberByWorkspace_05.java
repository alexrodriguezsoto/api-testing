package api_class1;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.apache.http.HttpStatus.SC_OK;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class getWorkspaceMemberByWorkspace_05 {

    //TODO: Student Practice

    private String path;
    public static Response response;
    public static String jsonAsString;
    public static Map<String, String> list;
    public static Map<String, String> workspaceID;
    List<String> jsonResponse;
    String workSpaceId;
    String userId;

    @BeforeClass
    public String getAuthToken() {
        RestAssured.baseURI = "https://api.octoperf.com:443/public/users";
        path = "/login";
        
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

    @Test
    public void validateResponseBody() {
        RestAssured.baseURI = "https://api.octoperf.com";
        response = RestAssured.given()
                .header("Authorization",  getAuthToken())
                .when()
                .get("/workspaces/member-of")
                .then().log().all()
                .extract().response();

        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("Default", response.jsonPath().getString("name[0]"));
        Assert.assertEquals("Wkt3tHgB6T29TqnSuTha", response.jsonPath().getString("id[0]"));
        Assert.assertEquals("1kt3tHgB6T29TqnSCje3", response.jsonPath().getString("userId[0]"));

        String company = response.jsonPath().getString("id");
        System.out.println(company);

        //we have 1 array with 2 Json response body
        jsonResponse = response.jsonPath().getList("id");
        System.out.println(jsonResponse.get(0));
    }

    @Test(dependsOnMethods={"validateResponseBody"})
    public void validateResponseBody2() {
        String workspaceId = jsonResponse.get(0);
        RestAssured.baseURI = "https://api.octoperf.com";
        response = RestAssured.given()
                .header("Authorization",  getAuthToken())
                .when()
                .get("workspaces/members/by-workspace/"+workspaceId)
                .then()
                .extract().response();

        Assert.assertEquals(200, response.statusCode());
        System.out.println(response.prettyPrint());

        JsonPath jsonpathEvaluator = response.jsonPath();
        userId = jsonpathEvaluator.get("userId").toString();
        System.out.println("_---->"+ userId);

        workSpaceId = jsonpathEvaluator.get("workspaceId").toString();
        System.out.println("_---->"+ workSpaceId);

        workspaceID = new HashMap<String, String>();
        workspaceID.put("userId", userId);
        workspaceID.put("workspaceId", workSpaceId);

        System.out.println("_->"+ workspaceID.get("userId"));
        System.out.println("_->"+ workspaceID.get("workspaceId"));

    }


    @Test(dependsOnMethods ={"validateResponseBody2"} )
    public void validatecreateProject() {
        String requestBody = "{\"id\":\"\",\"created\":\"2021-03-11T06:15:20.845Z\",\"lastModified\":\"2021-03-11T06:15:20.845Z\",\"userId\":\""+workSpaceId+"\",\"workspaceId\":\""+userId+"\",\"name\":\"testing22\",\"description\":\"testing\",\"type\":\"DESIGN\",\"tags\":[]}";
        System.out.println(requestBody);
        String workspaceId = jsonResponse.get(0);
        RestAssured.baseURI = "https://api.octoperf.com";
        response = RestAssured.given()
                .headers("Content-type", "application/json")
                .header("Authorization",  getAuthToken())
                .and()
                .body(requestBody)
                .when()
                .post("/design/projects")
                .then()
                .extract().response();
        System.out.println(response.prettyPrint());
        System.out.println(response.getStatusLine());

    }

}
