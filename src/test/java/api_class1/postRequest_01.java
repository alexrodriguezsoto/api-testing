package api_class1;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.*;

/**
 * POST Log in api.octoperf.com
 */
public class postRequest_01 {

    // in order to make a request we need to identify the baseURI
    public static String baseURI = "https://api.octoperf.com/public/users";
    // define path for your API Request
    private String path = "/login";

    //Params defined in the POST Request log in with response body token
    //Actual End Point : https://api.octoperf.com/public/users/login
    //Api End Point with Query: https://api.octoperf.com/public/users/login?password=test12&username=tla.jiraone@gmail.com
    //Query Parameter : password=test12 and username=tla.jiraone@gmail.com
    @Test
    public void noQueryParams(){
        RestAssured.given()
                .when()
                .post("https://api.octoperf.com/public/users/login?password=test12&username=tla.jiraone@gmail.com")
                .prettyPrint();
    }

    @Test
    public void hashMapQueryParams(){
        RestAssured.baseURI = "https://api.octoperf.com/public/users";
        String path = "/login";

        //Hashmap store items in a "key/value" pairs. You can access them by an index of another type.
        //One object is used as a key (index) to another object (value). It can store different types:
        //String keys and Integer values, or the same type, like: String keys and String values:
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("password", "test12");
        map.put("username", "tla.jiraone@gmail.com");

        RestAssured.given()
                .queryParams(map)
                .when()
                .post(path)
                .prettyPrint();
    }

    @Test
    public void logInParam(){
        RestAssured.baseURI = "https://api.octoperf.com/public/users";
        String path = "/login";
        RestAssured.given()
                .param("password", "test12")
                .param("username", "tla.jiraone@gmail.com")
                .when()
                .post(path)
                .prettyPrint();

    }

    //We can pass multiple parameters in same method;
    //instead of using queryParam() we have to use queryParams() method.
    @Test
    public void multiplelineQueryParams() {
        RestAssured.baseURI = "https://api.octoperf.com/public/users";
        String path = "/login";
        RestAssured.given()
                .queryParams("password", "test12", "username", "tla.jiraone@gmail.com") // multiple params
                .when()
                .post(path)
                .prettyPrint();
    }

    // calling static method from RestAssure Class
    @Test
    public void staticQueryParams() {
        RestAssured.baseURI = "https://api.octoperf.com/public/users";
        String path = "/login";
        given().contentType(ContentType.JSON) // without class reference
                .queryParam("password", "test12")
                .queryParam("username","tla.jiraone@gmail.com")
                .when()
                .post(path)
                .then()
                .statusCode(200);
    }
}
