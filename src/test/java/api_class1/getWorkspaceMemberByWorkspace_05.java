package api_class1;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.apache.http.HttpStatus.SC_OK;

import java.util.*;

import org.json.JSONObject;
import io.restassured.response.ResponseOptions;
import util.utilities;

public class getWorkspaceMemberByWorkspace_05 {

    //TODO: Student Practice

    private String path;
    public static Response response;
    public static String jsonAsString;
    public static Map<String, String> lists;
    public static Map<String, String> workspaceID;
    List<String> jsonResponse;
    String workid;
    String id;
    String id2;

    String workSpaceId;
String  userId;
    String id4;
    String b;


    @BeforeClass
    public String setUpLogIn() {
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
    public void workscapeMember() {
        RestAssured.baseURI = "https://api.octoperf.com";
        response = RestAssured.given()
                .header("Authorization",  setUpLogIn())
                .when()
                .get("/workspaces/member-of")
                .then().log().all()
                .extract().response();

        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("Default", response.jsonPath().getString("name[0]"));
        Assert.assertEquals("Wkt3tHgB6T29TqnSuTha", response.jsonPath().getString("id[0]"));
        Assert.assertEquals("1kt3tHgB6T29TqnSCje3", response.jsonPath().getString("userId[0]"));
        Assert.assertEquals("", response.jsonPath().getString("description[0]"));

        String company = response.jsonPath().getString("id");
        System.out.println(company);

        b = response.jsonPath().get("id[0]");
        System.out.println(b);

    }

    @Test(dependsOnMethods={"workscapeMember"})
    public void memberByWorkspace() {
        String workspaceId = response.jsonPath().get("id").toString();
        System.out.println("----->"+workspaceId);
        RestAssured.baseURI = "https://api.octoperf.com";
        response = RestAssured.given()
                .header("Authorization",  setUpLogIn())
                .when()
                .get("workspaces/members/by-workspace/"+b)
                .then()
                .extract().response();

        Assert.assertEquals(200, response.statusCode());
        System.out.println(response.prettyPrint());

        id = response.body().jsonPath().get("userId").toString();
        workid = response.body().jsonPath().get("workspaceId").toString();


        System.out.println("---->"+id);
        System.out.println(workid);



        JsonPath jsonpathEvaluator = response.jsonPath();
        userId = jsonpathEvaluator.get("userId[0]").toString();
        System.out.println("_---->"+ userId);

        workSpaceId = jsonpathEvaluator.get("workspaceId[0]").toString();
        System.out.println("_---->"+ workSpaceId);

        workspaceID = new HashMap<String, String>();
        workspaceID.put("userId", userId);
        workspaceID.put("workspaceId", workSpaceId);

        System.out.println("_->"+ workspaceID.get("userId"));
        System.out.println("_->"+ workspaceID.get("workspaceId"));


    }


    @Test(dependsOnMethods ={"memberByWorkspace"} )
    public void createProject() {
        String requestBody = "{\"id\":\"\",\"created\":\"2021-03-11T06:15:20.845Z\",\"lastModified\":\"2021-03-11T06:15:20.845Z\",\"userId\":\""+workspaceID.get("userId")+"\",\"workspaceId\":\""+workspaceID.get("workspaceId")+"\",\"name\":\"testing22\",\"description\":\"testing\",\"type\":\"DESIGN\",\"tags\":[]}";
        System.out.println(requestBody);
        String workspaceId = jsonResponse.get(0);
        RestAssured.baseURI = "https://api.octoperf.com";
        response = RestAssured.given()
                .headers("Content-type", "application/json")
                .header("Authorization",  setUpLogIn())
                .and()
                .body(requestBody)
                .when()
                .post("/design/projects")
                .then()
                .extract().response();
        System.out.println(response.prettyPrint());
        System.out.println(response.getStatusLine());

                JsonPath jsonpathEvaluator = response.jsonPath();
        System.out.println(jsonpathEvaluator.prettyPrint());
        id2 = jsonpathEvaluator.get("id").toString();
        System.out.println("===>"+id2);

    }

 //   @Test(dependsOnMethods ={"validateResponseBody2"} )
    public void validatecreateProject2() {
        String requestBody = "{\"id\":\"\",\"created\":\"2021-03-11T06:15:20.845Z\",\"lastModified\":\"2021-03-11T06:15:20.845Z\",\"userId\":\""+workspaceID.get("userId")+"\",\"workspaceId\":\""+workspaceID.get("workspaceId")+"\",\"name\":\"testing22\",\"description\":\"testing\",\"type\":\"DESIGN\",\"tags\":[]}";

//        Map<String,String> hashmap=new HashMap<String,String>();
        JSONObject hashmap = new JSONObject();

        hashmap.put("id","");
        hashmap.put("created","2021-03-11T06:15:20.845Z");
        hashmap.put("lastModified","2021-03-11T06:15:20.845Z");
        hashmap.put("userId",workspaceID.get("userId"));
        hashmap.put("workspaceId",workspaceID.get("workspaceId"));
        hashmap.put("name","createNew");
        hashmap.put("description","new Dataset");
        hashmap.put("type","DESIGN");
        hashmap.put("tags","");

        System.out.println(requestBody);
        String workspaceId = jsonResponse.get(0);
        RestAssured.baseURI = "https://api.octoperf.com";
        response = RestAssured.given()
                .headers("Content-type", "application/json")
                .header("Authorization",  setUpLogIn())
                .and()
                .body(hashmap)
                .when()
                .post("/design/projects")
                .then()
                .extract().response();
        System.out.println(response.prettyPrint());
        System.out.println(response.prettyPeek());

//        JsonPath jsonpathEvaluator = response.jsonPath();
//        id2 = jsonpathEvaluator.get("id").toString();
//        list.put("id", id2);
//
//        System.out.println(list);

    }

    @Test(dependsOnMethods ={"createProject"} )
    public void updateProjectsName() {
        String requestBody = "{\"created\":1615443320845,\"description\":\"testing\",\"id\":\""+id2+"\",\"lastModified\":1629860121757,\"name\":\"testing Soto\",\"tags\":[],\"type\":\"DESIGN\",\"userId\":\"1kt3tHgB6T29TqnSCje3\",\"workspaceId\":\"Wkt3tHgB6T29TqnSuTha\"}";
        System.out.println(requestBody);
        String workspaceId = jsonResponse.get(0);
        RestAssured.baseURI = "https://api.octoperf.com";
        response = RestAssured.given()
                .headers("Content-type", "application/json")
                .header("Authorization",  setUpLogIn())
                .and()
                .body(requestBody)
                .when()
                .put("/design/projects/"+id2)
                .then()
                .extract().response();
        System.out.println(response.prettyPrint());
        System.out.println(response.getStatusLine());
    }

    @Test(dependsOnMethods ={"updateProjectsName"} )
    public void deleteProject() {
        RestAssured.baseURI = "https://api.octoperf.com";
        response = RestAssured.given()
                .header("Authorization",  setUpLogIn())
                .when()
                .delete("/design/projects/"+id2)
                .then()
                .extract().response();
        Assert.assertEquals(204, response.statusCode());
        System.out.println(response.prettyPrint());
        System.out.println(response.getStatusLine());
    }
}
