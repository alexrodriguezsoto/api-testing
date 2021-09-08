package api_Class.API_Day3;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import util.API_Utils;
import util.ConfigurationReader;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class VerifyMemberResponsesWithUtil {

    API_Utils uses = new API_Utils();
    public static Response response;
    public String defaultId;
    public String userId;
    public String workspaceId;
    public static Map<String, String> workspaceID;
    public String id2;
    

    @Test
    public void verifyDefaultMember() {
        uses.setUpLogIn(); // contains the baseURI

        response = RestAssured.given()
                .header("Authorization", uses.octoperfToken )
                .when()
                .get(ConfigurationReader.getProperty("defaultPath"))
                .then().log().all()
                .extract().response();
// verifying using TestNG Assert class and jsonPath's getString() method to verify body
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("Default", response.jsonPath().getString("name[0]"));
        Assert.assertEquals("Wkt3tHgB6T29TqnSuTha", response.jsonPath().getString("id[0]"));
        Assert.assertEquals("1kt3tHgB6T29TqnSCje3", response.jsonPath().getString("userId[0]"));
        Assert.assertEquals("", response.jsonPath().getString("description[0]"));

        // We need to get the id from default member in order to get access into AdminAccess
        defaultId = response.jsonPath().get("id[0]"); // Wkt3tHgB6T29TqnSuTha
    }

    @Test(dependsOnMethods = {"verifyDefaultMember"}) // since this is dependent, it'll fetch the baseURI from above, just add path
    public void verifyAdminMember() {
        response = RestAssured.given()
                .header("Authorization", uses.octoperfToken)
                .when()
                .get(ConfigurationReader.getProperty("adminPath") + defaultId)
                .then()
                .extract().response();

        Assert.assertEquals(200, response.statusCode());

        userId = response.jsonPath().get("userId[0]");
        assertThat(userId, is("1kt3tHgB6T29TqnSCje3"));

        workspaceId = response.jsonPath().get("workspaceId[0]");
        assertThat(workspaceId, is("Wkt3tHgB6T29TqnSuTha"));

        workspaceID = new HashMap<String, String>();
        workspaceID.put("userId", userId);
        workspaceID.put("workspaceId", workspaceId);
    }

    @Test(dependsOnMethods = {"verifyAdminMember"})
    public void createProject() {
        String createBody = "{\"id\":\"\",\"created\":\"2021-03-11T06:15:20.845Z\",\"lastModified\":\"2021-03-11T06:15:20.845Z\",\"userId\":\"" + workspaceID.get("userId") + "\",\"workspaceId\":\"" + workspaceID.get("workspaceId") + "\",\"name\":\"testing22\",\"description\":\"testing\",\"type\":\"DESIGN\",\"tags\":[]}";

        response = RestAssured.given()
                .headers("Content-type", "application/json")
                .header("Authorization", uses.octoperfToken)
                .and()
                .body(createBody)
                .when()
                .post("/design/projects")
                .then()
                .extract().response();
        System.out.println(response.prettyPrint());
        System.out.println(response.getStatusLine());

        id2 = response.jsonPath().get("id");

        Assert.assertEquals(201, response.statusCode());
    }

    @Test(dependsOnMethods = {"createProject"})
    public void updateProjectsName() {
        String updateBody = "{\"created\":1615443320845,\"description\":\"testing\",\"id\":\"" + id2 + "\",\"lastModified\":1629860121757,\"name\":\"testing Soto\",\"tags\":[],\"type\":\"DESIGN\",\"userId\":\"1kt3tHgB6T29TqnSCje3\",\"workspaceId\":\"Wkt3tHgB6T29TqnSuTha\"}";

        response = RestAssured.given()
                .headers("Content-type", "application/json")
                .header("Authorization", uses.octoperfToken)
                .and()
                .body(updateBody)
                .when()
                .put("/design/projects/" + id2)
                .then()
                .extract().response();
        System.out.println(response.prettyPrint());
        System.out.println(response.getStatusLine());
    }

    @Test(dependsOnMethods = {"updateProjectsName"})
    public void deleteProject() {
        response = RestAssured.given()
                .header("Authorization", uses.octoperfToken)
                .when()
                .delete("/design/projects/" + id2)
                .then()
                .extract().response();
        Assert.assertEquals(204, response.statusCode());
        System.out.println(response.prettyPrint());
        System.out.println(response.getStatusLine());
    }
}
