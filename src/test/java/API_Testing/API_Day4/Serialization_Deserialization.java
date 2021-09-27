package API_Testing.API_Day4;

import Pojos.Company;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class Serialization_Deserialization {

    @Test (description = "Deserialization --> Json to Object")
    public void readToMap() throws FileNotFoundException {
        // FileReader will read the json file
        FileReader fileReader = new FileReader("src/test/resources/company.json");

        // convert the json file into object so that we can use it more easily
        // let's create a Map from this json file.
        // We do the conversion using Gson which is an object-mapper library that serializes object to json and vice-versa.
        // json --> file type (like xml, pdf, doc)
        // gson --> library used for conversion(serialiation and deserialiation)
        Gson gson = new Gson();

        // we converted the file into a Map meaning we deserialized the company.json file to java object (Map)
        Map<String, ?> myCompany = gson.fromJson(fileReader, Map.class);
        System.out.println(myCompany); // {name=Peter, companyId=123.0, companyName=QLA, title=QA Engineer}
        Assert.assertEquals("Peter", myCompany.get("name"));
        Assert.assertEquals("QLA", myCompany.get("companyName"));
        Assert.assertEquals("QA Engineer", myCompany.get("title"));
    }

    @Test (description = "Deserialization --> Json to Object(Map)")
    public void readToObject() throws FileNotFoundException {
        // read the file
        FileReader fileReader = new FileReader("src/test/resources/company.json");
        // de serialize into pojo
        Gson gson = new Gson();
        Company myComp = gson.fromJson(fileReader, Company.class); // fromJson() converts from Json to Object
        System.out.println(myComp); // Company{name=Peter, companyId=123, companyName='QLA', title='QA Engineer'}

        System.out.println(myComp.getCompanyId());
        Assert.assertEquals(123, myComp.getCompanyId());
        System.out.println(myComp.getName());
        Assert.assertEquals("QLA", myComp.getCompanyName());
    }

    @Test (description = "Serialization --> Object to Json")
    public void writeToJsonFile() throws IOException {
        // create object
        Company myNewCompany = new Company("Kevin", 212, "AmazonX", "SDET");
        System.out.println(myNewCompany);

        // write to file aka serialize it
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter fileWriter =new FileWriter("src/test/resources/new_company.json");
        gson.toJson(myNewCompany, fileWriter); // toJson() will convert the Object to Json

        fileWriter.flush();
        fileWriter.close();
    }
}
