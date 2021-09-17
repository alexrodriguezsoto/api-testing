package DB_Testing.db_tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// what database you used in your project?
// in my project I used PostgreSQL database, but prior to that I have worked with Oracle database as well
// I have done manual testing as well as automated testing on database.

// how do connect to database using selenium/java?
// In Java there is JDBC api that handles connections to the database. So, this is what I used in my project
// to connect to db and automate.
// first find the type of the database, I need to add the postreSQL's ojdbc driver.
// next I need create CONNECTION for this I use the Connection class in Java/JDBC.
// Connection class requires the url, username and password
// after connection is created, use the STATEMENT class to execute queries and RESULTSET class to store
// the query result and interact with it.


public class PostgreS {
    // Path for postgresql database : url link to set up a connection
    String demoHR = "jdbc:postgresql://localhost:5432/demoHR";

    // Create a username
    String dbUserName = "postgres";

    // Enter a password for Postgres database
    String dbPassword = "";

    @Test
    public void countTest() throws SQLException {
        // Stablish a Database Connection
        // for JDBC connection we use interface Connection
        Connection connection = DriverManager.getConnection(demoHR, dbUserName, dbPassword);

        // Establish an interface called Statement to read from the database.
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        // ResultSet interface allow us to query and execute the command
        ResultSet resultSet = statement.executeQuery("select * from departments");

        // last row of the table
        resultSet.last();

        // Verify that the query contains more than 0 records (rows)
        int rowsCount = resultSet.getRow(); //9
        System.out.println(rowsCount);
        Assert.assertTrue(rowsCount > 0);

        resultSet.close();
        statement.close();
        connection.close();
    }

    @Test
    public void testDatabaseConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(demoHR, dbUserName, dbPassword);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("select * from departments");

        // Navigate inside the table rows and columns to retrieve result
        // resultSet.first();

        while (resultSet.next()) {
            // print the column with index 1
            System.out.println(resultSet.getString(1) + " " +
                    resultSet.getString("dept_no") + " " +
                    resultSet.getString("dept_name"));
        }
    }

    @Test
    public void metaData() throws SQLException {
        Connection connection = DriverManager.getConnection(demoHR, dbUserName, dbPassword);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        String sql = "select * from departments";
        ResultSet resultSet = statement.executeQuery(sql);

        // Metadata
        // Metadata is the data describing the data that is being stored in your Data source
        // Metadata generally includes the name, size and number of rows for each table present
        // in the database
        DatabaseMetaData dbMetadata = connection.getMetaData();

        // Return username of the database
        System.out.println("Database User: " + dbMetadata.getUserName());

        // Return database type
        System.out.println("Database type: " + dbMetadata.getDatabaseProductName());

        // Resultset Metadata will query the results
        ResultSetMetaData rsMetadata = resultSet.getMetaData();

        System.out.println("Column count: " + rsMetadata.getColumnCount());

        for (int i = 1; i <= rsMetadata.getColumnCount(); i++) {
            System.out.println(i + " --> " + rsMetadata.getColumnName(i));
        }

        // Throw resultset into a list of MAPS
        // Create a list of Maps

        List<Map<String, Object>> list = new ArrayList<>();
        ResultSetMetaData rsMetadata2 = resultSet.getMetaData();

        int colCount = rsMetadata2.getColumnCount();// numberical value from database

        while (resultSet.next()) {
            Map<String, Object> rowMap = new HashMap<>();
            for (int col = 1; col <= colCount; col++) {
                rowMap.put(rsMetadata2.getColumnName(col), resultSet.getObject(col));
            }
            list.add(rowMap);
        }

        for (Map<String, Object> emp : list) {
            System.out.println(emp.get("dept_no"));
        }
        resultSet.close();
        statement.close();
        connection.close();
    }

}
