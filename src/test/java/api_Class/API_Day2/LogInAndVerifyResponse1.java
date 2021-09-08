package api_Class.API_Day2;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;

/* Today we'll just talk about the different ways that we can log in into our API server with Rest Assured using Map, passing
parameters using query/request parameters, and using some methods from RestAssured library. However, in the process, we'll
also verify the response we get after we log in
 */


public class LogInAndVerifyResponse1 {

    // After making a POST request to log in, the website asks for a token that we'll get from Postman to gain access.
    // FULL URL : https://api.octoperf.com/public/users/login
    // base url : https://api.octoperf.com
    // FULL URL with Query params: https://a10pi.octoperf.com/public/users/login?password=test12&username=tla.jiraone@gmail.com

    /* log in with Full url with query params,
    and verify status code is 200, content_Type is Json format
     */
    @Test
    public void fullUrlWithQueryParams(){
        RestAssured.given()
                .when()
                .post("https://api.octoperf.com/public/users/login?password=test12&username=tla.jiraone@gmail.com")
                .then().assertThat().statusCode(200).and().assertThat().contentType(ContentType.JSON);
    }

    // log in using Map and verify content-Type is application/json
    // Map store items in "key and value" pair. Hashmap implements Map, and can store different type of objects
    // for instance, we can have String keys and Integer values, or same type like String keys and String values, etc
    @Test
    public void LogInWithMap(){
        RestAssured.baseURI = "https://api.octoperf.com";
        String path = "/public/users/login";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", "tla.jiraone@gmail.com"); // where username is our 'key', and tla,jiraone@gmail.com is our 'value'
        map.put("password", "test12"); // where password is 'key'and test12 is 'value'

        RestAssured.given()
                .queryParams(map) //here using queryParams() method because it uses key and value
                .when()
                .post(path)
                .then().log().headers(). // log().headers() --> used to print the response's header
                assertThat().contentType("application/json");
    }


    // Log in with Query parameters and verify response status code 200 OK and
    // print response body
    @Test
    public void LogInWithQueryParams() {
        RestAssured.baseURI = "https://api.octoperf.com";
        String path = "/public/users/login";

        // without class reference
        given().contentType(ContentType.JSON)
                .queryParam("username","tla.jiraone@gmail.com")
                .queryParam("password", "test12")
                .when()
                .post(path)
                .then().assertThat()
                .statusCode(200)
                .and().log().body();
    }

    // 1) calling static method called 'param()' from RestAssured Class and log(print) the status
    @Test
    public void logInParam(){
        RestAssured.baseURI = "https://api.octoperf.com";
        String path = "/public/users/login";

        RestAssured.given()
                .param("username", "tla.jiraone@gmail.com")
                .param("password", "test12")
                .when()
                .post(path)
                .then().log().status();
    }

    /* 2) calling static method from RestAssured Class
    We can pass multiple parameters in same method using queryParams() method, and
    verify status code is 200 and header content-Type is in json format
     */
    @Test
    public void multiplelineQueryParams() {
        RestAssured.baseURI = "https://api.octoperf.coms";
        String path = "/public/user/login";

        RestAssured.given()
                .queryParams("password", "test12", "username", "tla.jiraone@gmail.com") // multiple params
                .when()
                .post(path)
                .then().assertThat().statusCode(200).contentType(ContentType.JSON);
    }
}
