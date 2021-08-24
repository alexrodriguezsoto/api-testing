package api_Class.API_Day2;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import java.util.HashMap;
import static io.restassured.RestAssured.given;

/*Today we'll just talk about the different ways that we can log in into our API server with Rest Assured using Map, passing
parameters using query/request parameter, and some methods from RestAssured library.
 */


public class PostRequestToLogIn {

    public static String baseURI = "https://api.octoperf.com/public/users";
    private String path = "/login";

    //After requesting a POST to log in, the server asks for a token that we'll get from Postman to gain access.
    //FULL URL : https://api.octoperf.com/public/users/login
    //base url : https://api.octoperf.com/public/users/
    //endpoint : /login
    //FUll URL with Query params: https://api.octoperf.com/public/users/login?password=test12&username=tla.jiraone@gmail.com
    //Query Parameter : password=test12 and username=tla.jiraone@gmail.com, where password is key and test12 is value.

    // using the whole full resource url with query params to log in
    @Test
    public void urlWithQueryParams(){
        RestAssured.given()
                .when()
                .post("https://api.octoperf.com/public/users/login?password=test12&username=tla.jiraone@gmail.com")
                .prettyPrint();
    }

    // log in with HashMap
    //HashMap store items in "key and value" pair.
    //One object is used as a (key) to another object(value). Hashmap can store different type of objects
    //for instance, we can have String keys and Integer values, or same type like String keys and String values, etc
    @Test
    public void hashMapAsParams(){
        RestAssured.baseURI = "https://api.octoperf.com/public/users";
        String path = "/login";

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("password", "test12");
        map.put("username", "tla.jiraone@gmail.com");

        RestAssured.given()
                .queryParams(map)
                .when()
                .post(path)
                .prettyPrint();
    }


    // Log in with Query/Request parameter
    @Test
    public void LogInWithQueryParams() {
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

    // 1) calling static method called 'param()' from RestAssured Class
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

    // 2) calling static method from RestAssured Class
    //We can pass multiple parameters in same method;
    //instead of using param() we can use queryParams() method.
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
}
