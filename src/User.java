import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;


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


    public static String getNameBySin(String sin) {

        String query = String.format("SELECT %s FROM %s WHERE sinNumber = '%s'",
            "fullName", UserDB, sin);

        ResultSet rs = db.execute(query, null, null).rs;

        try {
            if (rs.next()) {
                return rs.getString("fullName");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
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

// CREATE TABLE Rating (
// 	authorSin CHAR(9),
// 	renterSin CHAR(9),
// 	listingId INTEGER,
// 	startDate DATE,
// 	commentBody TEXT,
// 	score INTEGER CHECK (score >= 1 AND score <= 5) NOT NULL,
// 	object VARCHAR(30),
// 	PRIMARY KEY (authorSin, renterSin, listingId, startDate, object),
// 	FOREIGN KEY (renterSin) REFERENCES User(sinNumber) ON DELETE CASCADE ON UPDATE CASCADE,
// 	FOREIGN KEY (listingId) REFERENCES Listing(listingId) ON DELETE CASCADE ON UPDATE CASCADE,
// 	FOREIGN KEY (renterSin, listingId, startDate) REFERENCES Booking(renterSin, listingId, startDate) ON DELETE CASCADE ON UPDATE CASCADE
// );

// SELECT bookingStatus, Booking.startDate, endDate, score, commentBody FROM
// Booking INNER JOIN (SELECT renterSin, listingId, startDate, score, commentBody
// FROM Rating ORDER BY startDate DESC) AS tmp ON tmp.renterSin = Booking.renterSin
// AND tmp.listingId = Booking.listingId AND tmp.startDate = Booking.startDate WHERE Booking.renterSin = '266666662';


public static void getRenterHistory(String sin) {
        String displayedFields = "Booking.listingId, "
            + "latitude, listingType, longitude, bookingStatus, streetAddress, Booking.startDate, postalCode, "
            + "endDate, city, score, country";

        String table = String.format("%s INNER JOIN %s ON %s LEFT JOIN %s", BookingDB, ListingDB,
            "Listing.listingId = Booking.listingId",
            "(SELECT renterSin, listingId, startDate, score FROM Rating ORDER BY startDate DESC)"
            + " AS tmp ON tmp.renterSin = Booking.renterSin AND tmp.listingId = Booking.listingId AND"
            + " tmp.startDate = Booking.startDate");

        String query = String.format("SELECT %s FROM %s WHERE Booking.renterSin = '%s' GROUP BY %s",
            displayedFields, table, sin, displayedFields);

        displayRenterHistory(db.execute(query, null, null).rs);
    }

    public static void displayRenterHistory(ResultSet rs) {

        String[] fields = new String[]{"ID:", "Latitude:", "Type:", "Longitude:", "Booking Status:", "Address:", "Start Date:", "Postal code:",
            "End Date:", "City:", "Latest Rating:", "Country:"};
        String hor = " " + "-".repeat(98);
        try {
        if (rs == null || !rs.next()) {
            System.out.println("Nothing to see");
            return;
        }

        do {
            System.out.println(hor);
            for (int i = 0; i < fields.length; i++) {
                System.out.printf(i%2 == 0 ? "|" : "");

                if (i == 10) {
                    Object obj = rs.getObject(i + 1);
                    System.out.printf(" %-16s %-30s ",
                        fields[i], (obj == null ? "" : obj.toString() + "/5"));
                } else {
                    System.out.printf(" %-16s %-30s ",
                        fields[i], rs.getObject(i + 1).toString());
                }

                System.out.printf(i%2 == 1 ? "|\n" : "");
            }
        } while (rs.next());

        System.out.println(hor);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void getHostHistory(String sin) {
        String displayedFields = PostingDB + ".hostSin, latitude, " + PostingDB + ".listingId, "
            + "longitude, listingType, streetAddress, minPrice, postalCode, averagePrice, city, maxPrice, country";

        String table = String.format("%s INNER JOIN %s ON %s LEFT JOIN %s", PostingDB, ListingDB,
            "Listing.listingId = Posting.listingId",
            "(SELECT listingId, MIN(price) AS minPrice, AVG(price) AS averagePrice, MAX(price) AS maxPrice FROM AvailableDate GROUP BY listingId)"
            + " AS tmp ON tmp.listingId = Posting.listingId");

        String query = String.format("SELECT %s FROM %s WHERE Posting.hostSin = '%s'",
            displayedFields, table, sin);

        String[] fields = new String[]{"Host:", "Latitude:", "ID:", "Longitude:", "Type:", "Address:", "Min Price:", "Postal code:",
            "Average Price:", "City:", "Max Price:", "Country:"};
        Searching.displayListings(db.execute(query, null, null).rs, fields);
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
