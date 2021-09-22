package util;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import static org.apache.http.HttpStatus.SC_OK;


public class API_Utility {
    public String path;
    public String octoperfToken;
    public static Response response;


        @BeforeClass
        public void setUpLogIn() { // this method includes BaseURI, path, username, password, and token
            RestAssured.baseURI = ConfigurationReader.getProperty("url");
            path = ConfigurationReader.getProperty("loginPath");

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("password", "test12");
            map.put("username", "tla.jiraone@gmail.com");

            response = RestAssured.given()
                    .queryParams(map)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                    .post(path);

            octoperfToken = response.jsonPath().get("token");
            System.out.println("octoperf valid token ==> " + octoperfToken);
        }
}
