package api_Class.API_Day2;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

// JsonPath simple example
public class usingJsonPathWithJsonObject {

    @Test
    public void testJsonBody(){
        String jsonString = "{\r\n" +
                "  \"firstName\": \"James\",\r\n" +
                "  \"lastName\": \"Bond\"\r\n" +
                "}";

         // Get JsonPath instance from above JSON string
        JsonPath jsonPath = JsonPath.from(jsonString);

         // json's firstName, lastName holds a string value
         // therefore we'll use getString() method from JsonPath.
        String firstName = jsonPath.getString("firstName");
        Assert.assertEquals("James", firstName); // asserting if firstName is same
        String lastName = jsonPath.getString("lastName");
        Assert.assertEquals("Bond", lastName);
         }
    }