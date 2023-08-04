import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;


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
    public static boolean createUser(String sin, String user, String occupation,
        String address, String dateOfBirth){

        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.roll(Calendar.YEAR, -18);
        Calendar birthCalendar = Calendar.getInstance();
        birthCalendar.setTime(Date.valueOf(dateOfBirth));
        if (todayCalendar.compareTo(birthCalendar) < 0) {
            System.out.println("You must be +18 years old in order to create an account");
            return false;
        }

        String query = String.format("INSERT INTO %s %s VALUES ('%s', '%s', '%s', '%s', '%s')",
            UserDB, "(sinNumber, fullName, occupation, address, dateOfBirth)",
            sin, user, occupation, address, dateOfBirth );

        db.execute(query, "Successfully created user", null);
        return true;
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

    public static void addPaymentInfo (String sin, String info) {

        String query = String.format("UPDATE %s SET paymentInfo = '%s' WHERE sinNumber = '%s'",
            RenterDB, info, sin);

        db.executeUpdate(query, "Successfully updated payment info", "Error updating payment info");

    }


// CREATE TABLE Booking (
// 	listingId INTEGER,
// 	renterSin CHAR(9),
// 	startDate DATE,
// 	endDate DATE NOT NULL,
// 	bookingStatus VARCHAR(20) NOT NULL,
// 	price REAL CHECK (price >= 0),
// 	PRIMARY KEY(listingId, renterSin, startDate),
// 	FOREIGN KEY (listingId) REFERENCES Listing(listingId) ON DELETE CASCADE ON UPDATE CASCADE,
// 	FOREIGN KEY (renterSin) REFERENCES User(sinNumber) ON DELETE CASCADE ON UPDATE CASCADE,
// 	CHECK (startDate <= endDate)
// );


    public static void getRentHistory(String sin) {

        String displayedFields = "bookingStatus, startDate, endDate, ";

        String query = String.format("SELECT %s FROM %s",
            displayedFields, RenterDB + " INNER JOIN " + BookingDB);

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
