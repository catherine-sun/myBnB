import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.ResultSet;

public class DBConnection {

    private Connection conn;
    private static final String connectionString = "jdbc:mysql://localhost:3306/";

    public boolean connect (String dbName, String user, String password){
        boolean success = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(connectionString + dbName, user, password);
            System.out.println("Successfully connected to database");
            success = true;
        } catch (SQLException ex) {
            System.out.println("Failed to connect to database");
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } catch (ClassNotFoundException e) {
            System.out.println("Class Not Found Error: " + e.getMessage());
        }

        return success;
    }

    public QueryResult execute (String query, String success, String error) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            if (stmt.execute(query)) {
                rs = stmt.getResultSet();
            }
            if (success != null) System.out.println(success);

        } catch (SQLException ex){
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            if (error != null) System.out.println(error);
        }
        return new QueryResult(stmt, rs);
    }

    public QueryResult executeUpdate (String query, String success, String error) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            if (stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS) > 0) {
                rs = stmt.getResultSet();
                if (success != null) System.out.println(success);
            } else {
                if (error != null) System.out.println(error);
            }

        } catch (SQLException ex){
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            if (error != null) System.out.println(error);
        }
        return new QueryResult(stmt, rs);
    }

    public static void cleanResultSet (ResultSet rs){
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException sqlEx) { } // ignore

            rs = null;
        }
    }

    public static void cleanStatement (Statement stmt){
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException sqlEx) { } // ignore

            stmt = null;
        }
    }

}
