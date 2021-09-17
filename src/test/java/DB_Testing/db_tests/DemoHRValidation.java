package DB_Testing.db_tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import util.DBUtility;
import util.Database;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DemoHRValidation {

    @BeforeClass
    public void setUp(){
        DBUtility.establishConnection(Database.POSTGRESQL, "demoHR");
    }

    @AfterClass
    public void closeDB(){
        DBUtility.closeConnections();
    }

    @Test
    public void assertDB() throws SQLException {
        DBUtility.getRowsCount("select * from employees");
    }

    @Test
    public void assertDB2(){
        List<Map<String, Object>> empData  =DBUtility.getQueryResults("select * from titles limit 10");

        // Print rows -> array starts with 0 as the 1st from from SQL
        System.out.println(empData.get(0));
        System.out.println(empData.get(4));

        // Assert title = Staff, Senior Engineer
        // Table = titles

        Assert.assertEquals(empData.get(1).get("title"),"Staff");
        Assert.assertEquals(empData.get(2).get("title"),"Senior Engineer");

    }

    @Test
    public void test1(){
        //TODO
        // query all the records from departments table and assert "Sales" from dept_name column
        // and their respective dept_no "d007"
        List<Map<String, Object>> empData  =DBUtility.getQueryResults("select * from departments");

        Assert.assertEquals(empData.get(6).get("dept_no"),"d007");
        Assert.assertEquals(empData.get(6).get("dept_name"),"Sales");
    }

    @Test
    public void test2(){
        //TODO
        // query first_name from employees and assert "Anneke" and "Berni" from their column

        List<Map<String, Object>> empData  =DBUtility.getQueryResults("select first_name from employees");

        Assert.assertEquals(empData.get(4).get("first_name"),"Anneke");
        Assert.assertEquals(empData.get(12).get("first_name"),"Berni");
    }

    @Test
    public void test3(){
        //TODO
        // query all results from employees table and assert emp_no 10005 birth_date 1955-01-21
        // and first name "Kyoichi"

        List<Map<String, Object>> empData  =DBUtility.getQueryResults("select emp_no, birth_date, first_name from employees");

        Date date = Date.valueOf("1955-01-21");

        Assert.assertEquals(empData.get(3).get("emp_no"), 10005);
        Assert.assertEquals(empData.get(3).get("first_name"), "Kyoichi");

        Assert.assertEquals(empData.get(3).get("birth_date"), date);

    }

    @Test
    public void test4(){
        //TODO
        // query the total count from employees table and assert results = 300024

        List<Map<String, Object>> empData  =DBUtility.getQueryResults("select count(*) from employees");

        //Implicit type casting
        int p = 300024;
        long l = p;
        Assert.assertEquals(empData.get(0).get("count"), l);
    }


    @Test
    public void test5(){
        //TODO
        // query a list of emp_no whose minimun salary is 40000 or less limit 1

        List<Map<String, Object>> empData  =DBUtility.getQueryResults("select emp_no, min(salary) from salaries group by emp_no having min(salary)<=40000 limit 1");

        Assert.assertEquals(empData.get(0).get("min"), 40000);

    }

    @Test
    public void test6(){
        //TODO
        //  query emp_no and salary form the second highets salary table alias salary => as secondHighestSalary
        List<Map<String, Object>> empData  =DBUtility.getQueryResults("select salary from salaries order by salary desc limit 1 offset 1");
        Assert.assertEquals(empData.get(0).get("salary"), 157821);

    }

}
