package api_Class.API_Day2;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

//Logs display information from the response when requesting info from an API server
/*
Request logging
- given().log().all() --> log all request specification details including parameters, headers and body
- given().log().params() --> log only the parameters of the request
- given().log().body() --> log only the request body
- given().log().headers() --> log only the request headers
- given().log().cookies() --> log only the request cookies
- given().log().method() --> log only the request method
- given().log().path() --> log only the request path

Response Logging
- get("/x".then().log().body
- get("/x".then().log().ifError
- get("/x".then().log().all()
- get("/x".then().log().statusLine() --> only log the status line
- get("/x".then().log().header() --> only log the response headers
- get("/x".then().log().cookies() --> only log the response cookies
- get("/x".then().log().ifStatusCodeisEqualTo(200) --> only log if the status code is equal to 200
 */

public class getRequestsApiWithLogs {

    private String path;

    //@BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api.octoperf.com:443/public/users";
        path = "/login";
    }


    @Test
    public void testingLogs1() {

        // Logging the url only
        RestAssured.
                given().log().uri().
                when().get("https://api.octoperf.com/app/#/access/signin").then().statusCode(200);

        // Log the body only
        RestAssured.
                given().log().body().
                when().get("https://api.octoperf.com/app/#/access/signin").then().statusCode(200);
    }

    // logging all()
    @Test
    public void LogAll(){
        RestAssured.
                given().log().all(). // or everything() works same way as all()
                when().get("https://api.octoperf.com/app/#/access/signin").then().statusCode(200);
    }

    // Log fail
    @Test
    public void logFail(){
        RestAssured.
                when().get("https://api.octoperf.com/app/#/access/signin").
                then().log().ifValidationFails().statusCode(204); // will log only if validation fails
            // then().log().ifValidationFails().statusCode(200); // test will passed because validation log is correct 200
    }

    // log if status code is 200
    @Test
    public void logStatusCode(){
        RestAssured.
                when().get("https://api.octoperf.com/app/#/access/signin").
                then().log().ifStatusCodeIsEqualTo(200);
    }
}
