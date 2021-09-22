package DB_Testing.db_tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import util.DBType;
import util.DBUtility;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DemoHRValidation {

    @BeforeClass
    public void setUp(){
        DBUtility.establishConnection(DBType.POSTGRESQL);
    }

    @AfterClass
    public void closeDB(){
        DBUtility.closeConnections();
    }


    @Test(description = "verify total number of employees is 300024")
    public void assertDB() throws SQLException {
       int result = DBUtility.getRowsCount("select * from employees");
       Assert.assertEquals(result, 300024  );
    }

    @Test(description = "verify title's index position for Staff and Senior Engineer from titles table")
    public void assertDB2(){
        List<Map<String, Object>> titlesData  =DBUtility.getQueryResults("select * from titles limit 10");

        // Print rows -> array starts with 0 which is the 1st element from SQL
        // therefore staff is at index 1 and Senior Engineer at index 4
        System.out.println(titlesData.get(1)); // {from_date=1996-08-03, to_date=9999-01-01, emp_no=10002, title=Staff}
        System.out.println(titlesData.get(4)); // {from_date=1995-12-01, to_date=9999-01-01, emp_no=10004, title=Senior Engineer}

        // Assert title = Staff, Senior Engineer from Table = titles
        Assert.assertEquals(titlesData.get(1).get("title"),"Staff");
        Assert.assertEquals(titlesData.get(4).get("title"),"Senior Engineer");
    }

    @Test
    public void test1(){
        //TODO
        // query all the records from departments table and assert "Sales" from dept_name column
        // with its respective dept_no "d007"

        List<Map<String, Object>> departmentsData  =DBUtility.getQueryResults("select * from departments");

        Assert.assertEquals(departmentsData.get(6).get("dept_no"),"d007");
        Assert.assertEquals(departmentsData.get(6).get("dept_name"),"Sales");
    }

    @Test
    public void test2(){
        //TODO
        // query first_name from employees and assert "Anneke" and "Saniya" are found within the first 10 records

        List<Map<String, Object>> empData  =DBUtility.getQueryResults("select first_name from employees limit 10");

        Assert.assertEquals(empData.get(4).get("first_name"),"Anneke");
        Assert.assertEquals(empData.get(6).get("first_name"),"Saniya");
    }

    @Test
    public void test3(){
        //TODO
        // query all results from employees table and assert emp_no 10005 birth_date 1955-01-21
        // and first name "Kyoichi"

        List<Map<String, Object>> empData  =DBUtility.getQueryResults("select emp_no, birth_date, first_name from employees");
        Date date = Date.valueOf("1955-01-21"); // must use Date class to format into a String value for assertion

        Assert.assertEquals(empData.get(3).get("emp_no"), 10005);
        Assert.assertEquals(empData.get(3).get("first_name"), "Kyoichi");
        Assert.assertEquals(empData.get(3).get("birth_date"), date);
    }

    @Test
    public void test4(){ // Different version found on method assertDB test
        //TODO
        // query the total count from employees table and assert result = 300024

        List<Map<String, Object>> empData = DBUtility.getQueryResults("select count(*) from employees");

        //Implicit type casting
        int p = 300024;
        long l = p;
        Assert.assertEquals(empData.get(0).get("count"), l);
    }


    @Test
    public void test5(){
        //TODO
        // query a list of emp_no whose minimun salary is 40000 or less, and limit the output to 1

        List<Map<String, Object>> empData  =DBUtility.getQueryResults("select emp_no, min(salary) from salaries group by emp_no having min(salary)<=40000 limit 1");
        Assert.assertEquals(empData.get(0).get("min"), 40000);
    }


    @Test
    public void test6(){
        //TODO
        //  Fetch the second highest salary from salaries table and assert that it is equal to 157821
        List<Map<String, Object>> empData  =DBUtility.getQueryResults("select salary from salaries order by salary desc limit 1 offset 1");
        Assert.assertEquals(empData.get(0).get("salary"), 157821);
    }
}
