package API_Testing.API_Day3;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.is;

// In addition to verifying response body from members, we'll also create, update and delete projects
public class VerifyMemberResponses {

    //TODO: Student Practice

    public String path;
    public static Response response;
    public static Map<String, String> workspaceID;
    public String defaultId;
    public String userId;
    public String workspaceId;
    public String id2;

    @BeforeClass
    public String setUpLogInAndToken() { // this method includes BaseURI, path, username, password, and token
        RestAssured.baseURI = "https://api.octoperf.com";
        path = "/public/users/login";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("password", "test12");
        map.put("username", "tla.jiraone@gmail.com");

        return RestAssured.given()
                .queryParams(map)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .post(path)
                .then()
                .statusCode(SC_OK)
                .extract()
                .body()
                .jsonPath()
                .get("token");
    }

    // Verify some values from default's member body "https://api.octoperf.com/workspaces/member-of
    // When finding path to 'default workspace' refer to the octoperf's swagger documentation
    @Test
    public void verifyDefaultMember() {

// Interface Response class represents the response to a request made by Rest-Assured.
        response = RestAssured.given()
                .header("Authorization",  setUpLogInAndToken())
                .when()
                .get("/workspaces/member-of")
                .then().log().all()
                .extract().response();

// verifying using TestNG's Assert class and jsonPath's getString() method to verify body
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("Default", response.jsonPath().getString("name[0]"));
        Assert.assertEquals("wAf7I3wB10t72x7FjLsY", response.jsonPath().getString("id[0]"));
        Assert.assertEquals("1kt3tHgB6T29TqnSCje3", response.jsonPath().getString("userId[0]"));
        Assert.assertEquals("", response.jsonPath().getString("description[0]"));

        // We need to get the id from default member in order to get access into AdminAccess
        defaultId = response.jsonPath().get("id[0]"); // wAf7I3wB10t72x7FjLsY
    }

    // Verify AdminAccess member's status code, userId, and workspaceId
    @Test(dependsOnMethods={"verifyDefaultMember"})
    public void verifyAdminMember() {

        response = RestAssured.given()
                .header("Authorization",  setUpLogInAndToken())
                .when()
                .get("workspaces/members/by-workspace/"+defaultId); // use the id from default to access into Admin

        Assert.assertEquals(200, response.statusCode());

        // Now, we'll be using assertThat() method from hamcrest Matchers to verify our response body
        userId = response.jsonPath().get("userId[0]");
        assertThat(userId, is("1kt3tHgB6T29TqnSCje3"));

        workspaceId = response.jsonPath().get("workspaceId[0]");
        assertThat(workspaceId, is("wAf7I3wB10t72x7FjLsY"));

        // Store Admin's userId and workspaceId into a hashmap which we'll use in order to crate a project
        workspaceID = new HashMap<String, String>();
        workspaceID.put("userId", userId);
        workspaceID.put("workspaceId", workspaceId);
    }

// Octoperf's Swagger documentation will show you how to use endpoints for creating, updating, delete

    // create project
    @Test(dependsOnMethods={"verifyAdminMember"})
    public void createProject() {
        String requestBody1 = "{\"id\":\"\",\"created\":\"2021-03-11T06:15:20.845Z\",\"lastModified\":\"2021-03-11T06:15:20.845Z\",\"userId\":\"" + workspaceID.get("userId") + "\",\"workspaceId\":\"" + workspaceID.get("workspaceId") + "\",\"name\":\"testing22\",\"description\":\"testing\",\"type\":\"DESIGN\",\"tags\":[]}";

        response = RestAssured.given()
                .headers("Content-type", "application/json")
                .header("Authorization", setUpLogInAndToken())
                .and()
                .body(requestBody1)
                .when()
                .post("/design/projects")
                .then()
                .extract().response();

        System.out.println(response.prettyPrint());
        id2 = response.jsonPath().get("id");
        System.out.println("This is the new id created when making a post request ===> " + id2); // id2 will be used for updating project

        Assert.assertEquals(201, response.statusCode());

        // Assert name, type using matchers from hamcrest along with JsonPath
        assertThat(response.jsonPath().getString("type"), is("DESIGN"));
        assertThat(response.jsonPath().getString("name"), is("testing22"));
    }

    // update created project
    @Test(dependsOnMethods ={"createProject"} )
    public void updateProjectsName() {
        String requestBody2 = "{\"created\":1615443320845,\"description\":\"testing\",\"id\":\""+id2+"\",\"lastModified\":1629860121757,\"name\":\"testing Soto\",\"tags\":[],\"type\":\"DESIGN\",\"userId\":\"1kt3tHgB6T29TqnSCje3\",\"workspaceId\":\"wAf7I3wB10t72x7FjLsY\"}";

        response = RestAssured.given()
                .headers("Content-type", "application/json")
                .header("Authorization",  setUpLogInAndToken())
                .and()
                .body(requestBody2)
                .when()
                .put("/design/projects/"+id2)
                .then()
                .extract().response();
        System.out.println(response.prettyPrint());
    }

    // delete created project
    @Test(dependsOnMethods ={"updateProjectsName"} )
    public void deleteProject() {
        response = RestAssured.given()
                .header("Authorization",  setUpLogInAndToken())
                .when()
                .delete("/design/projects/"+id2)
                .then()
                .extract().response();
        Assert.assertEquals(204, response.statusCode());
        System.out.println(response.getStatusLine());
    }
}
