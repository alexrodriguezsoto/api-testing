package api_Class;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

//Logs display information from the response when requesting information from an API server
public class getRequestsApiWithLogs {

    private String path;

    //@BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api.octoperf.com:443/public/users";
        path = "/login";
    }


    @Test
    public void testingLogs1() {
        // logging everything
        RestAssured.
                given().log().everything(). // or all() get the same output
                when().get("https://api.octoperf.com/app/#/access/signin").then().statusCode(200);

        // Logging only the url
        RestAssured.
                given().log().uri().
                when().get("https://api.octoperf.com/app/#/access/signin").then().statusCode(200);

        // Log the body only
        RestAssured.
                given().log().body().
                when().get("https://api.octoperf.com/app/#/access/signin").then().statusCode(200);
    }

    // Log fail response
    @Test
    public void logFail(){
        RestAssured.
                when().get("https://api.octoperf.com/app/#/access/signin").
                then().log().ifValidationFails().statusCode(200);
    }

    // Log request and response
    @Test
    public void logRequestAndResponse(){
        RestAssured.given().log().ifValidationFails().
                when().get("https://api.octoperf.com/app/#/access/signin").
                then().log().ifValidationFails().statusCode(200);
    }
}
