package api_Class.API_Day3;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import util.API_Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.is;

public class VerifyMemberResponsesWIthUtil {
    API_Utils uses = new API_Utils();

    @Test
    public void verifyDefaultMember() {
        RestAssured.baseURI = "https://api.octoperf.com";

        uses.response = RestAssured.given()
                .header("Authorization", uses.setUpLogInAndToken())
                .when()
                .get("/workspaces/member-of")
                .then().log().all()
                .extract().response();

        Assert.assertEquals(200, uses.response.statusCode());
        Assert.assertEquals("Default", uses.response.jsonPath().getString("name[0]"));
        Assert.assertEquals("Wkt3tHgB6T29TqnSuTha", uses.response.jsonPath().getString("id[0]"));
        Assert.assertEquals("1kt3tHgB6T29TqnSCje3", uses.response.jsonPath().getString("userId[0]"));
        Assert.assertEquals("", uses.response.jsonPath().getString("description[0]"));

        uses.jsonResponse = uses.response.jsonPath().getList("id");
    }

    @Test(dependsOnMethods = {"verifyDefaultMember"})
    public void verifyAdminMember() {
        String workspaceId = uses.jsonResponse.get(0); // id is reassigned into workspaceId
        RestAssured.baseURI = "https://api.octoperf.com";
        uses.response = RestAssured.given()
                .header("Authorization", uses.setUpLogInAndToken())
                .when()
                .get("workspaces/members/by-workspace/" + workspaceId)
                .then()
                .extract().response();

        Assert.assertEquals(200, uses.response.statusCode());

        uses.userId = uses.response.jsonPath().get("userId[0]");
        assertThat(uses.userId, is("1kt3tHgB6T29TqnSCje3"));

        uses.workSpaceId = uses.response.jsonPath().get("workspaceId[0]");
        assertThat(workspaceId, is("Wkt3tHgB6T29TqnSuTha"));

        uses.workspaceID = new HashMap<String, String>();
        uses.workspaceID.put("userId", uses.userId);
        uses.workspaceID.put("workspaceId", uses.workSpaceId);
    }

    @Test(dependsOnMethods = {"verifyAdminMember"})
    public void createProject() {
        String requestBody = "{\"id\":\"\",\"created\":\"2021-03-11T06:15:20.845Z\",\"lastModified\":\"2021-03-11T06:15:20.845Z\",\"userId\":\"" + uses.workspaceID.get("userId") + "\",\"workspaceId\":\"" + uses.workspaceID.get("workspaceId") + "\",\"name\":\"testing22\",\"description\":\"testing\",\"type\":\"DESIGN\",\"tags\":[]}";

        System.out.println(requestBody);
        String workspaceId = uses.jsonResponse.get(0);
        RestAssured.baseURI = "https://api.octoperf.com";
        uses.response = RestAssured.given()
                .headers("Content-type", "application/json")
                .header("Authorization", uses.setUpLogInAndToken())
                .and()
                .body(requestBody)
                .when()
                .post("/design/projects")
                .then()
                .extract().response();
        System.out.println(uses.response.prettyPrint());
        System.out.println(uses.response.getStatusLine());

        uses.id2 = uses.response.jsonPath().get("id");
        System.out.println("===> id2");

    }

    public void verifyCreatedBody() {
        String requestBody = "{\"id\":\"\",\"created\":\"2021-03-11T06:15:20.845Z\",\"lastModified\":\"2021-03-11T06:15:20.845Z\",\"userId\":\"" + uses.workspaceID.get("userId") + "\",\"workspaceId\":\"" + uses.workspaceID.get("workspaceId") + "\",\"name\":\"testing22\",\"description\":\"testing\",\"type\":\"DESIGN\",\"tags\":[]}";

        Map<String, String> hashmap = new HashMap<String, String>();
        hashmap.put("id", "");
        hashmap.put("created", "2021-03-11T06:15:20.845Z");
        hashmap.put("lastModified", "2021-03-11T06:15:20.845Z");
        hashmap.put("userId", uses.workspaceID.get("userId"));
        hashmap.put("workspaceId", uses.workspaceID.get("workspaceId"));
        hashmap.put("name", "createNew");
        hashmap.put("description", "new Dataset");
        hashmap.put("type", "DESIGN");
        hashmap.put("tags", "");

        VerifyMemberResponses a = new VerifyMemberResponses();
        a.setUpLogInAndToken();
        uses.response = RestAssured.
                given().
                body(hashmap).
                when().
                post("/design/projects").
                prettyPeek();
        Assert.assertEquals(201, uses.response.statusCode());
    }

    @Test(dependsOnMethods = {"createProject"})
    public void updateProjectsName() {
        String requestBody = "{\"created\":1615443320845,\"description\":\"testing\",\"id\":\"" + uses.id2 + "\",\"lastModified\":1629860121757,\"name\":\"testing Soto\",\"tags\":[],\"type\":\"DESIGN\",\"userId\":\"1kt3tHgB6T29TqnSCje3\",\"workspaceId\":\"Wkt3tHgB6T29TqnSuTha\"}";
        System.out.println(requestBody);
        String workspaceId = uses.jsonResponse.get(0);
        RestAssured.baseURI = "https://api.octoperf.com";
        uses.response = RestAssured.given()
                .headers("Content-type", "application/json")
                .header("Authorization", uses.setUpLogInAndToken())
                .and()
                .body(requestBody)
                .when()
                .put("/design/projects/" + uses.id2)
                .then()
                .extract().response();
        System.out.println(uses.response.prettyPrint());
        System.out.println(uses.response.getStatusLine());
    }

    @Test(dependsOnMethods = {"updateProjectsName"})
    public void deleteProject() {
        RestAssured.baseURI = "https://api.octoperf.com";
        uses.response = RestAssured.given()
                .header("Authorization", uses.setUpLogInAndToken())
                .when()
                .delete("/design/projects/" + uses.id2)
                .then()
                .extract().response();
        Assert.assertEquals(204, uses.response.statusCode());
        System.out.println(uses.response.prettyPrint());
        System.out.println(uses.response.getStatusLine());
    }
}