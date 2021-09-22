package util;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtility {

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;


    //    PostgreSQL             demoHR
    public static void establishConnection(DBType dbType) {
        try {
            switch (dbType) {
                case POSTGRESQL:
                    connection = DriverManager.getConnection(ConfigurationReader.getProperty("demoHR"),
                                                             ConfigurationReader.getProperty("dbUserName"),
                                                             ConfigurationReader.getProperty("dbPassword"));
                    if (connection != null) {
                        System.out.println("Postgres DB connection Successfull");
                    } else {
                        System.out.println("Failed to connect to Postgres DBType");
                    }
                    break;
                case ORACLE:
                    break;
                case MONGO:
                    break;
                default:
                    connection = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getRowsCount(String query) throws SQLException {
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        resultSet = statement.executeQuery(query);

        resultSet.last();
        System.out.println("Total Count : " + resultSet.getRow());
        return resultSet.getRow();
    }


    public static List<Map<String, Object>> getQueryResults(String query) {
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(query);

            List<Map<String, Object>> list = new ArrayList<>();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();

            while (resultSet.next()) {
                Map<String, Object> rowMap = new HashMap<>();
                for (int col = 1; col <= columnCount; col++) {
                    rowMap.put(resultSetMetaData.getColumnName(col), resultSet.getObject(col));
                }
                list.add(rowMap);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void closeConnections() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
            System.out.println("DBType closed successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//
//    /**
//     *
//     * @param query
//     * @return returns a single cell value. If the results in multiple rows and/or
//     *         columns of data, only first column of the first row will be returned.
//     *         The rest of the data will be ignored
//     */
//    public static Object getCellValue(String query) {
//        return getQueryResultList(query).get(0).get(0);
//    }
//
//    /**
//     *
//     * @param query
//     * @return returns a list of Strings which represent a row of data. If the query
//     *      *         results in multiple rows and/or columns of data, only first row will
//     *      *         be returned. The rest of the data will be ignored
//     */
//    public static List<Object> getRowList(String query) {
//        return getQueryResultList(query).get(0);
//    }
//
//    /**
//     *
//     * @param query
//     * @return returns a map which represent a row of data where key is the column
//     *         name. If the query results in multiple rows and/or columns of data,
//     *         only first row will be returned. The rest of the data will be ignored
//     */
//    public static Map<String, Object> getRowMap(String query) {
//        return getQueryResultMap(query).get(0);
//    }
//
//    /**
//     *
//     * @param query
//     * @return returns query result in a list of lists where outer list represents
//     *         collection of rows and inner lists represent a single row
//     */
//    public static List<List<Object>> getQueryResultList(String query) {
//        executeQuery(query);
//        List<List<Object>> rowList = new ArrayList<>();
//        ResultSetMetaData rsmd;
//
//        try {
//            rsmd = resultSet.getMetaData();
//            while (resultSet.next()) {
//                List<Object> row = new ArrayList<>();
//                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
//                    row.add(resultSet.getObject(i));
//                }
//
//                rowList.add(row);
//
//            }
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        return rowList;
//
//    }
//
//    /**
//     *
//     * @param query
//     * @param column
//     * @return list of values of a single column from the result set
//     */
//    public static List<Object> getColumnData(String query, String column) {
//        executeQuery(query);
//        List<Object> rowList = new ArrayList<>();
//        ResultSetMetaData rsmd;
//
//        try {
//            rsmd = resultSet.getMetaData();
//            while (resultSet.next()) {
//                rowList.add(resultSet.getObject(column));
//            }
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        return rowList;
//
//    }
//
//    /**
//     *
//     * @param query
//     * @return returns query result in a list of maps where the list represents
//     *         collection of rows and a map represents represent a single row with
//     *         key being the column name
//     */
//    public static List<Map<String, Object>> getQueryResultMap(String query) {
//        executeQuery(query);
//        List<Map<String, Object>> rowList = new ArrayList<>();
//        ResultSetMetaData rsmd;
//
//        try {
//            rsmd = resultSet.getMetaData();
//
//            while (resultSet.next()) {
//
//                Map<String, Object> colNameValueMap = new HashMap<>();
//
//                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
//
//                    colNameValueMap.put(rsmd.getColumnName(i), resultSet.getObject(i));
//                }
//
//                rowList.add(colNameValueMap);
//
//            }
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        return rowList;
//
//    }
//
//    /**
//     *
//     * @param query
//     * @return List of columns returned in result set
//     */
//    public static List<String> getColumnNames(String query) {
//        executeQuery(query);
//        List<String> columns = new ArrayList<>();
//        ResultSetMetaData rsmd;
//
//        try {
//            rsmd = resultSet.getMetaData();
//            int columnCount = rsmd.getColumnCount();
//
//            for (int i = 1; i <= columnCount; i++) {
//                columns.add(rsmd.getColumnName(i));
//            }
//
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        return columns;
//
//    }
//
//    private static void executeQuery(String query) {
//        try {
//            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        try {
//            resultSet = statement.executeQuery(query);
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    public static int getRowCount() throws Exception {
//
//        resultSet.last();
//
//        int rowCount = resultSet.getRow();
//        return rowCount;
//
//    }


    public static ResultSet getResult(String sql) {
        // creates the result

        ResultSet result = null;

        try {
            result = statement.executeQuery(sql);

        }catch(Exception e) {}

        return result;
    }

    public static void UpdateQuery(String sql) {
        // INSERT, UPDATE, , DELETE, ALTER, TRUNCATE, DROP

        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {

        }
    }
}
