package api_Class.API_Day1;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.*; // this import will get all methods
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class StudentPracticeRequests {
    private final String API_KEY = "special-key";
    Response response;

    @BeforeClass
    public void setUp() {
        baseURI = "https://petstore.swagger.io/v2"; // This is base URI, just add the endpoint/resource

    }

    // response --> response that is sent by server as result of our request
    // given() --> prepares request
    // when() --> submit request
    // get --> send the request to given url
    // asString() --> returns the body as a single string


    /*
     * create a new pet object by passing valid id and name
     * verify response body and header is in json format
     */
    @Test
    public void createNewPet(){
        /// create the File that we want to send when making a post request
        File jsonFile = new File("src/test/resources/pet.json");

        given().log().all()
                .contentType(ContentType.JSON).
                // include the file in the response
                body(jsonFile).
                when().post("/pet").
                prettyPeek().
                then().
                statusCode(200).contentType(ContentType.JSON).
                body("id", equalTo(100111));
    }


    /*
    update the created pet object, verify response body
     */
    @Test
    public void updatePet(){
        File jsonUpdatedPet = new File("src/test/resources/updatePet.json");
        response = given().
                contentType(ContentType.JSON). // telling the api what kind of data format I am sending
                accept(ContentType.JSON).      // telling the api what kind of data format I want in return
                body(jsonUpdatedPet).
                when().
                put("/pet").prettyPeek();

        String bodyStr = response.asString();
        System.out.println("fetch data from response body ==> " + bodyStr);
    }

    //delete pet created, verify status code 404 not found
    @Test
    public void deletePet(){
        given().
                body(API_KEY).
                pathParam("id", 100111).
                when().delete("/pet/{id}").
                then().log().status(); // give me the status code after delete()
    }

    // get pet by id that was deleted, verify status is 404
    @Test
    public void getPetByIdDeleted(){
        given().
                pathParam("id", 100111).
                when().get("/pet/{id}").
                then().assertThat().statusCode(404);

    }
}
