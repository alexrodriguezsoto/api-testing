package api_Class;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

public class introRestAssured {
// Rest-Assured is a class that uses gherkin BDD style statements such as given, when, then.
// Rest-Assured works with GET, POST, PUT and DELETE operations, but as a tester you mainly perform
// GET to request response from a server, and POST to send or create information to/from a server.
// Testers mostly use and GET and POST, PUT and DElETE will only be used when needed.

// These are the most used restAssured methods:
    // given() --> used to prepare the request
    // when() and get() or just when() --> used to send the request
    // then() --> used to verify the request
    // prettyPeek() --> used to print the response
    // asString() --> used to print in String format
    // contentType() --> used to verify body response as Json or XML format, but we'll only work with json.

    // In order to make a request we need to identify the baseURI
    // baseURI --> saves the base url for all resources
    public static String baseURI = "https://api.octoperf.com/public/users";

    // When we make requests, we only provide the path(endpoint) to a specific base resource(url)
    private String path = "/login"; // login is our path (endpoint)

    // Therefore, FULL URL will be --> https://api.octoperf.com/public/users/login for now

    /*
    We have to types of parameters as follows:
    Path param --> is part of the url. Ex: https://api.octoperf.com:443/public/users/login/, separated by single / slash
    query/request param  --> is not part of the url. Ex: https://api.octoperf.com/public/users/login?password=test12&username=tla.jiraone@gmail.com
    where after ? is end of url, and everything else thereafter is in key and value parameter.
    Ex: Query Parameter : password=test12 and username=tla.jiraone@gmail.com, where key is password and its value test12 and so on.
     */

    //Params defined in the POST Request log in with response body token
    // Base url : https://api.octoperf.com
    //Full url : https://api.octoperf.com/public/users/login
    //Api End Point with Query: https://api.octoperf.com/public/users/login?password=test12&username=tla.jiraone@gmail.com


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
    2xx --> success
    3xx --> redirect
    4xx --> client error
    5xx --> server error

    Perform a POST request to "https://api.octoperf.com/public/users/login?password=test12&username=tla.jiraone@gmail.com"
    and verify that status code is 200 OK
    Note: his website doesn't return code 201 as it should when posting a request, instead returns 200. But it's OK for now.
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
    When verifying the response header, there are two ways to do it as follows:
    accept() --> used when making a GET request, verify that header that I'm getting from a server is in application/json or application/xml format.
    ContentType() --> used when sending a POST request, verify that I'm sending json or xml data format to the server and the server needs to accept, if not
    the server will response a default response back.
    Note: As testers we mostly work with json format because it's easy it works with our framework. XML is a more complex format to work with that is
    why companies used mostly json to work when working with API. For interview when they ask about headers, just tell them that I only work with accept
    and contentType to verify headers, that's all.
    */

    /*
    Perform a POST request to "https://api.octoperf.com/public/users/login?password=test12&username=tla.jiraone@gmail.com"
    and verify that in response header content-type is application/json
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
