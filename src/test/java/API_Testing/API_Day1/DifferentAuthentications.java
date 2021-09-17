package API_Testing.API_Day1;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class DifferentAuthentications {

    @Test
    public void usingApiKey(){ //I got this API key long time ago, you don't have to get it, this is just an example of api key.
        RestAssured.given().log().all().
                queryParam("t", "Kung Fury"). // t is used to search for a movie, go to the url for more info
                queryParam("apikey", "a9faab96"). // where key = apikey, value = a9faab96
                when().get("http://www.omdbapi.com/").
                then().log().all().assertThat().statusCode(200);
    }

    @Test
    public void basicAuthentication(){
        // auth --> provides different types of authentication such oauth2, digest, basic, preemptive, etc
        // today we'll use basic(username, password) which uses username and password as parameters
        RestAssured.given().
                auth().
                basic("admin", "admin").
                when().get("https://the-internet-herokuapp.com/basic_auth").
                then().assertThat().statusCode(200);
    }

    @Test
    public void basicPreemptiveExample(){
        RestAssured.given().
                auth().preemptive().basic("admin", "admin").
                when().get("https://the-internet-herokuapp.com/basic_auth").
                then().assertThat().statusCode(200);
    }

    // This is not an authentication example, it's just to show what an XML body looks like
    @Test
    public void xmlResponse(){
        RestAssured.get("http://parabank.parasoft.com/parabank/services/bank/customers/12212/").
                then().log().all().
                assertThat().statusCode(200);
    }
}
