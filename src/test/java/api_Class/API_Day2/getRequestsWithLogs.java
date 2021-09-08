package api_Class.API_Day2;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

//Logs display information from the response when requesting info from an API server
/*
Request logging
- given().log().all() --> logs all request specification details including parameters, headers and body
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
- get("/x".then().log().statusLine() --> log only the status line
- get("/x".then().log().header() --> log only the response headers
- get("/x".then().log().cookies() --> log only the response cookies
- get("/x".then().log().ifStatusCodeisEqualTo(200) --> log only if the status code is equal to 200
 */

public class getRequestsWithLogs {

// Request logging examples

    @Test
    public void testingLogs1() {

        // Logging the url only
        RestAssured.
                given().log().uri().
                when().get("https://api.octoperf.com").then().statusCode(200);

        // Log the body only
        RestAssured.
                given().log().body().
                when().get("https://api.octoperf.com").then().statusCode(200);
    }

    // logging all()
    @Test
    public void LogAll(){
        RestAssured.
                given().log().all(). // or everything() works same way as all()
                when().get("https://api.octoperf.com").then().statusCode(200);
    }


// Response Logging examples

    // Log fail, in this example we want to validate that the status is 200, only if this fails, the request will be logged
    @Test
    public void logFail(){
        RestAssured.
                when().get("https://api.octoperf.com").
                then().log().ifValidationFails().statusCode(204); // will log because validation status code is not 200
            // then().log().ifValidationFails().statusCode(200);
    }

    // log if status code is equal 200
    @Test
    public void logStatusCode(){
        RestAssured.
                when().get("https://api.octoperf.com").
                then().log().ifStatusCodeIsEqualTo(200);
    }

    // log response header
    @Test
    public void  logResponseHeader(){
        RestAssured.
                when().get("https://api.octoperf.com").
                then().log().headers();
    }
}
