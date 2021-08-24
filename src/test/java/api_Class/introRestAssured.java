package api_Class;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

public class introRestAssured {
// Rest Assured is a Java library class that is used to perform automated API tests which
// uses gherkin BDD style statements such as given, when, then, and, etc to write such tests.
// Rest Assured has methods to fetch data from the request and response from JSON structures and in order
// to do that Rest-Assured works with HTTP methods such as GET, POST, PUT and DELETE, but as a tester you'll
// mainly use GET to request response from a server, and POST to create information to a server.
// Testers mostly use and GET and POST, PUT and DELETE will only be used when needed.

// Most often used methods from Rest-Asssured library when writing tests:
    // given() --> used to prepare the request
    // when() and get() or just when() --> used to send the request
    // then() --> used to verify the request
    // prettyPeek() --> used to print the response
    // log() --> logs(prints) request from response
    // asString() --> used to print in String format
    // contentType() --> used to verify body response as Json or XML format, but we'll only work with json.

    // In order to make a request we need to identify the baseURI
    // baseURI --> saves the base URL for all resources
    public static String baseURI = "https://api.octoperf.com/public/users";

    // When we make requests, we only provide the path(endpoint) to a specific base resource(url)
    private String path = "/login"; // login is our path (endpoint) that will be attached to the base url

    // Therefore, FULL URL will be --> https://api.octoperf.com/public/users/login for now

    /*
    We have two different parameter types in API:
    Path param --> will be part of url followed by the end of full resource url
    Ex: https://api.octoperf.com:443/public/users/login/, separated by single / slash
    Query/Request param  --> is not part of the url and it passed in Key and Value format.
    Ex: https://api.octoperf.com/public/users/login?password=test12&username=tla.jiraone@gmail.com
    ? --> means end of url, after that it's (key and value) query parameter.
    Ex: Query Parameter : password=test12 and username=tla.jiraone@gmail.com, where key is password and value is test12 and so on.
     */

    /* What is an endpoint?
    An endpoint is a unique URL that represents an object or collections of objects.
    For instance, http://www.google.com/search?source=book
                  Base URI  / resource ? parameter
    We can see that search is the endpoint
    */

    /*Params defined in the POST Request log in with response body token
    Base url : https://api.octoperf.com
    Full url : https://api.octoperf.com/public/users/login
    /login --> endpoint

    Full resource url with Query params : https://api.octoperf.com/public/users/login?password=test12&username=tla.jiraone@gmail.com
    */

    /*
    perform a POST request to full url with query params given "https://api.octoperf.com/public/users/login?password=test12&username=tla.jiraone@gmail.com"
    and print the response
     */
    @Test
    public void printTheResponse(){
        RestAssured.given()
                .when()
                .post("https://api.octoperf.com/public/users/login?password=test12&username=tla.jiraone@gmail.com")
                .prettyPeek(); // prints the response
    }

    /*
    When verifying the status codes you must pay close attention to the following error codes from the response
    1xx --> information
    2xx --> success (200 -> OK, 201 -> Created, 202 -> Accepted, 204 -> No content)
    3xx --> redirect
    4xx --> client error (400 -> Bad Request, 401 -> Unauthorized, 403 -> Forbidden, 404 -> Not Found, 405 -> Method not allowed)
    5xx --> server error (500 -> Internal server error, 502 -> Bad gateway, 501 -> Not implemented, 503 -> Service unavailable)
    */

    /*
    Perform a POST request to "https://api.octoperf.com/public/users/login?password=test12&username=tla.jiraone@gmail.com"
    and verify that status code is 200 OK
    Note: this website doesn't return code 201 as it should when making a POST request, instead returns 200. But it's OK for now.
     */
    @Test
    public void verifyStatusCode(){
        RestAssured.
                given().
                when().post("https://api.octoperf.com/public/users/login?password=test12&username=tla.jiraone@gmail.com").
                then().assertThat().statusCode(200); // should have returned 201, but 200 it's good for now
        System.out.println("Test verified, status code is 200 OK");
    }

    /*
    When verifying the response header, there are two ways to do it:
    accept --> used when making a GET request to verify the header that I'm receiving should be in json or xml format such as (application/json)
    ContentType() --> used when sending a POST request to check what I'm sending should be in json or xml format
    Note: As testers we mostly work with json format because it's used primarily to transmit data between a server and a web application.
    json stands for JavaScript Object notation, and is a format that is in key and value.
    xml stands for Extensible markup language
    */

    /*
    Perform a POST request to "https://api.octoperf.com/public/users/login?password=test12&username=tla.jiraone@gmail.com"
    and verify the response header content-type is application/json or likewise in json format.
     */
    @Test
    public void testHeaderContentType(){
        RestAssured.
                when().
                post("https://api.octoperf.com/public/users/login?password=test12&username=tla.jiraone@gmail.com").prettyPeek().
                then().assertThat().contentType(ContentType.JSON);
        System.out.println("Test verified, content-type is in application/json format");

    }
}
