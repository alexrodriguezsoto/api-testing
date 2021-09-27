package DB_Testing.db_tests;

import org.testng.annotations.Test;
import util.DBUtility;
import util.DBType;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class WorkingWithDML_DDL {
    public static void main(String... args) throws SQLException, ClassNotFoundException {
//        createTable();
//        InsertToDB();
//        update();
        update("Barak", "Rodriguez");

    }

    // Create Table
    private static void createTable() {
        Connection connection = null;
        Statement stmt = null;
        String CreateSql = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/demoHR",
                    "postgres", "");

            System.out.println("DBType Connected ..");

            stmt = connection.createStatement();
            CreateSql = "Create Table students(id int primary key, first_name varchar, last_name varchar, address text)";
            stmt.executeUpdate(CreateSql);
            stmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println(CreateSql + " Table Created successfully");
    }

    private static void InsertToDB() {
        try {
            // create a mysql DBType connection
            String myUrl = "jdbc:postgresql://localhost:5432/demoHR";
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(myUrl, "postgres", "");

            String query2 = "INSERT INTO Students (id, first_name, last_name, address ) VALUES (?, ?, ? , ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query2);
            preparedStmt.setInt(1, 3);
            preparedStmt.setString(2, "Janeth");
            preparedStmt.setString(3, "Smith");
            preparedStmt.setString(4, "12 S Street");

            // execute the preparedstatement
            preparedStmt.execute();
            conn.close();
        } catch (Exception e) {
            System.out.println("Got an exception!");
            System.out.println(e.getMessage());
        }
    }

    // UPDATE TABLE
    public static void update() throws SQLException, ClassNotFoundException {
        String myUrl = "jdbc:postgresql://localhost:5432/demoHR";
        Class.forName("org.postgresql.Driver");
        Connection conn = DriverManager.getConnection(myUrl, "postgres", "");

        //index start with 0 for column 1 in pgadmin
        String sql = "UPDATE students SET id=?, first_name = ?,  last_name=?, address=?";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, 3);
        statement.setString(2, "Rocky");
        statement.setString(3, "Rambo");
        statement.setString(4, "1549 s holliwood");

        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("An existing user was updated successfully!");
        }
    }

    public static void update(String first_name, String last_name) throws SQLException, ClassNotFoundException {
        String myUrl = "jdbc:postgresql://localhost:5432/demoHR";
        Class.forName("org.postgresql.Driver");
        Connection conn = DriverManager.getConnection(myUrl, "postgres", "");

        //index start with 0 for column 1 in pgadmin
        String sql = "UPDATE students SET  first_name = ?, last_name = ?";

        PreparedStatement statement = conn.prepareStatement(sql);

        //index start with 0 for column 1 in pgadmin
        statement.setString(1, first_name);
        statement.setString(2, last_name);

        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("An existing user was updated successfully!");
        }
    }

    // Delete Table from database -- Example
    @Test
    public void test7() throws SQLException {
        DBUtility.establishConnection(DBType.POSTGRESQL);

        List<Map<String, Object>> empData = DBUtility.getQueryResults("DROP TABLE students");
        System.out.println(empData);

    }
}
