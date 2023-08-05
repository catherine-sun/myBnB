import java.sql.ResultSet;
import java.sql.SQLException;


public class User extends DBTable {

    public static boolean isUser (String sin){
        String query = String.format("SELECT * FROM %s WHERE sinNumber = '%s'",
            UserDB, sin);

        ResultSet rs = db.execute(query, null, null).rs;

        boolean result;
        try {
            result = rs.next();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            result = false;
        }
        return result;
    }

    public static boolean isHost (String sin){
        String query = String.format("SELECT * FROM %s WHERE sinNumber = '%s'",
            HostDB, sin);

        ResultSet rs = db.execute(query, null, null).rs;

        boolean result;
        try {
            result = rs.next();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            result = false;
        }
        return result;
    }

    public static boolean isRenter (String sin){
        String query = String.format("SELECT * FROM %s WHERE sinNumber = '%s'",
            RenterDB, sin);

        ResultSet rs = db.execute(query, null, null).rs;

        boolean result;
        try {
            result = rs.next();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            result = false;
        }
        return result;
    }
    /*
        CREATE TABLE User (
        sin CHAR(9) PRIMARY KEY,
        name VARCHAR(30) NOT NULL,
        occupation VARCHAR(15),
        address VARCHAR(50),
        dateOfBirth DATE
    );*/
    public static void createUser(String sin, String user, String occupation,
        String address, String dateOfBirth){

        String query = String.format("INSERT INTO %s %s VALUES ('%s', '%s', '%s', '%s', '%s')",
            UserDB, "(sinNumber, fullName, occupation, address, dateOfBirth)",
            sin, user, occupation, address, dateOfBirth );

        db.execute(query, "Successfully created user", null);
    }

    public static void deleteUser(String sin){

        String query = String.format("DELETE FROM %s WHERE sinNumber = '%s'",
            UserDB, sin);

        db.execute(query, "Successfully deleted user", null);
    }

    public static void updateProfile (String sin, String col, String value) {

        String query = String.format("UPDATE %s SET %s = '%s' WHERE sinNumber = '%s'",
            UserDB, col, value, sin);

        db.execute(query, "Successfully updated profile", null);
    }

    public static void createHost(String sin){

        String query = String.format("INSERT INTO %s (%s) VALUES ('%s')",
            HostDB, "sinNumber", sin);

        db.executeUpdate(query, null, null);
    }

    public static void createRenter(String sin){

        String query = String.format("INSERT INTO %s (%s) VALUES ('%s')",
            RenterDB, "sinNumber", sin);

        db.executeUpdate(query, null, null);
    }

    public static QueryResult findUser(String sin) {

        String query = String.format("SELECT * FROM %S WHERE sinNumber = '%s'",
            UserDB, sin);

        return db.execute(query, null, "User not found");
    }

    /*CREATE TABLE Renter (
        sin CHAR(9) PRIMARY KEY,
        paymentInfo VARCHAR(30),
        FOREIGN KEY (sin) REFERENCES User(sin)
            ON DELETE CASCADE
            ON UPDATE CASCADE
    );*/

    /*CREATE TABLE Host (
        sin CHAR(9) PRIMARY KEY,
        FOREIGN KEY (sin) REFERENCES User(sin)
            ON DELETE CASCADE
            ON UPDATE CASCADE
    );*/

}
