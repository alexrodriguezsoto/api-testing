package DB_Testing.db_tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// What type of database did you work with in your project?
// in my current project I used PostgreSQL database, but prior to that I have worked with Oracle database as well.
// I have done manual testing as well as automation testing on databases.

// How do you connect to database using selenium/java?
// In Java there is JDBC api that handles connections to the database. So, this is what I used in my project
// to connect to database and automate.
// first I find the type of database, I need to add the postreSQL's jdbc driver.
// next I need create CONNECTION, for this I use the Connection class in Java/JDBC.
// Connection class requires url, username and password
// after connection is created, use the STATEMENT class to execute queries and RESULTSET class to store
// the query result and interact with it.


public class PostgreS {
    // Path for postgresql database : url link to set up a connection
    String demoHR = "jdbc:postgresql://localhost:5432/demoHR";

    // Create a username
    String dbUserName = "postgres";

    // Enter a password for Postgres database
    String dbPassword = "";


    @Test (description = "testing departments table with given above credentials")
    public void printAllFromDepartments() throws SQLException {
        // Establish a DBType Connection
        // To connect Database with JDBC we use interface Connection
        Connection connection = DriverManager.getConnection(demoHR, dbUserName, dbPassword);
        // The Statement Interface allows us to read data from the database.
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // ResultSet interface allow us to execute and save the query in ResulSet object to make manipulation within tests
        ResultSet resultSet = statement.executeQuery("select * from departments");

        // next() --> checks whether there is any data in the database
        while (resultSet.next()) {
            // print the column with index 1
            System.out.println(resultSet.getString(1) + " " +
                    resultSet.getString("dept_no") + " " +
                    resultSet.getString("dept_name"));
        }
        resultSet.close();
        statement.close();
        connection.close();
    }


    @Test (description = "Verify departments table contains records (rows) > 0 ")
    public void countDeptRowsTest() throws SQLException {
        Connection connection = DriverManager.getConnection(demoHR, dbUserName, dbPassword);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("select * from departments");

        // last() fetches the last row from the table
        resultSet.last();

        // Verify departments table contains more than 0 records (rows)
        int rowsCount = resultSet.getRow(); //9
        System.out.println(rowsCount);
        Assert.assertTrue(rowsCount > 0);

        resultSet.close();
        statement.close();
        connection.close();
    }


    // Metadata is the data describing the data that is being stored in your Data source.
    // Metadata generally includes the name, size and number of rows for each table present
    // in the database. There are 2 types of METADATA in databases; Database MetaData, y ResulSet MetaData

    @Test
    public void metaData() throws SQLException {
        Connection connection = DriverManager.getConnection(demoHR, dbUserName, dbPassword);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        // DatabaseMetaData fetches data from database itself
        DatabaseMetaData dbMetadata = connection.getMetaData();

        // Return username of the database
        System.out.println("DBType User: " + dbMetadata.getUserName());

        // Return database type
        System.out.println("DBType type: " + dbMetadata.getDatabaseProductName());

       // -------------------------------------------------------------------------
        // ResulSet MetaData from Departments table
        String sql = "select * from departments";
        ResultSet resultSet = statement.executeQuery(sql);

        // Resultset MetaData fetches all data from ResulSet when executing queries
        ResultSetMetaData rsMetadata = resultSet.getMetaData();
        System.out.println("Column count: " + rsMetadata.getColumnCount());

        for (int i = 1; i <= rsMetadata.getColumnCount(); i++) {
            System.out.println(i + " --> " + rsMetadata.getColumnName(i));
        }

        // Throw resultset into a list of MAPS
        // Create a list of Maps
        List<Map<String, Object>> list = new ArrayList<>();
        ResultSetMetaData rsMetadata2 = resultSet.getMetaData();

        int colCount = rsMetadata2.getColumnCount();// numerical value from database

        while (resultSet.next()) {
            Map<String, Object> rowMap = new HashMap<>();
            for (int col = 1; col <= colCount; col++) {
                rowMap.put(rsMetadata2.getColumnName(col), resultSet.getObject(col));
            }
            list.add(rowMap);
        }

        for (Map<String, Object> emp : list) {
            System.out.println(emp.get("dept_no")+" "+emp.get("dept_name"));
        }
        resultSet.close();
        statement.close();
        connection.close();
    }
}
