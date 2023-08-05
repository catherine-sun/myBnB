import java.util.Calendar;
import java.util.Scanner;
import java.sql.Date;

public class Setup {

    /* To run on an empty database */
    public static void main (String[] args) {
        Scanner input = new Scanner(System.in);
        DBConnection db = new DBConnection();
        System.out.println("SETUP DATA");
        String dbName, usr, pwd;
        do {
            System.out.print("Enter database: ");
            dbName = input.nextLine().trim();

            System.out.print("Enter user [root]: ");
            usr = input.nextLine().trim();
            if (usr.length() == 0) { usr = "root"; }

            do {
                System.out.print("Enter password: ");
                pwd = input.nextLine().trim();

                if (pwd.length() == 0) {
                    System.out.println("Password cannot be empty.");
                }
            } while (pwd.length() == 0);

        } while (!db.connect(dbName, usr, pwd));

        DBTable.db = db;

        createData();
        updateData();
    }

    public static void createData (){
        DBTable.dev = true;

        User.createUser("101010101", "John Smith", "Professor", "44 Mooncrest Ave", "1983-09-23");
        User.createUser("122222221", "Catherine Sun", "Software Engineer", "25 Arbutus Crescent", "2003-07-09");
        User.createUser("133333331", "Christine Zhao", "Junior Full Stack Developer", "21 Waymount Avenue", "2003-05-15");
        User.createUser("144444441", "Patricia Smith", "Unemployed", "56 Second St", "1993-04-25");
        User.createUser("155555551", "Uncle Tetsu", "Chef", "213 Banging Danging House", "2000-01-01");
        User.createUser("166666661", "Eric Corlett", "Professor", "1185 Military Trail", "1979-11-14");
        User.createUser("177777771", "Nick Koudas", "Professor", "1185 Military Trail", "1983-09-23");
        User.createUser("188888881", "Bianca Schroeder", "Professor", "1185 Military Trail", "1983-09-23");

        User.createUser("266666662", "Tony Stark", "Super Hero", "1 Main Street", "1980-03-16");
        User.createUser("277777772", "Clark Kent", "Super Hero", "2 Main Street", "1985-10-03");
        User.createUser("288888882", "Bruce Wayne", "Super Hero", "3 Main Street2", "1978-02-22");

        User.createRenter("122222221");
        User.createRenter("133333331");
        User.createRenter("155555551");
        User.createRenter("166666661");
        User.createRenter("177777771");
        User.createRenter("188888881");

        User.createHost("101010101");
        User.createHost("122222221");
        User.createHost("133333331");
        User.createHost("144444441");

        Listing.createListing("101010101", "House", "34.3456", "45.2", "44 Mooncrest Drive", "M5D2N7", "Toronto", "Canada"); // 1
        Listing.createListing("101010101", "House", "35.3456", "46.2", "67 Godstone Crescent", "M5D2N7", "Toronto", "Canada"); // 2
        Listing.createListing("101010101", "Townhouse", "67.3456", "-25.2", "1085 Heaven Road", "M5S9N7", "Toronto", "Canada"); // 3
        Listing.createListing("122222221", "House", "24.3456", "45.2", "25 Arbutus Crescent", "M2K5M3", "Toronto", "Canada"); // 4
        Listing.createListing("122222221", "Basement", "24.3456", "45.2", "25 Arbutus Crescent", "M2K5M3", "Toronto", "Canada"); // 5
        Listing.createListing("133333331", "House", "22.3456", "40.2", "21 Waymount Avenue", "L4S2G5", "Richmond Hill", "Canada"); // 6
        Listing.createListing("144444441", "Apartment", "14.3456", "80.342", "6 Main Street", "M0D8R1", "Hamilton", "Canada"); // 7
        Listing.createListing("144444441", "Basement", "64.3456", "65.2", "11 Bob Corner", "M9K5F3", "Berlin", "Germany"); // 8
        Listing.createListing("144444441", "Bedroom (Double bed)", "64.3456", "65.2", "11 Bob Corner", "M9K5F3", "Berlin", "Germany"); // 9
        Listing.createListing("144444441", "Bedroom (Hammock)", "64.3456", "65.2", "11 Bob Corner", "M9K5F3", "Berlin", "Germany"); // 10
        Listing.createListing("144444441", "Bedroom", "64.3456", "65.2", "11 Bob Corner", "M9K5F3", "Berlin", "Germany"); // 11
        Listing.createListing("144444441", "Bedroom", "64.3456", "65.2", "11 Bob Corner", "M9K5F3", "Berlin", "Germany"); // 12

        String start = "2022-01-01";
        String end = "2026-09-01";

        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        startCalendar.setTime(Date.valueOf(start));
        endCalendar.setTime(Date.valueOf(end));

        while (startCalendar.compareTo(endCalendar) < 0) {
            String dateString = String.format("%d-%02d-%02d", startCalendar.get(Calendar.YEAR),
            startCalendar.get(Calendar.MONTH) + 1, startCalendar.get(Calendar.DAY_OF_MONTH));

            for (int i = 1; i <= 12; i++) {
                String query = String.format("INSERT INTO %s (%s) VALUES (%d, '%s', %f)",
                    DBTable.AvailableDateDB, "listingId, startDate, price",  i, dateString, (double) Math.round(Math.random() * 17000) / 100 + 40);

                    DBTable.db.executeUpdate(query, null, null);
            }

            startCalendar.roll(Calendar.DATE, true);
            if (startCalendar.get(Calendar.DATE) == 1) {
                startCalendar.roll(Calendar.MONTH, true);
                if (startCalendar.get(Calendar.MONTH) == 0)
                    startCalendar.roll(Calendar.YEAR, true);
            }
        }
        Booking.bookListing("155555551", "1", "2023-09-07", "2023-9-12");
        Booking.bookListing("166666661", "1", "2023-09-12", "2023-9-14");
        Booking.bookListing("155555551", "1", "2023-09-23", "2023-10-05");
        Booking.bookListing("266666662", "1", "2022-10-23", "2022-11-03");
        Booking.bookListing("277777772", "1", "2023-08-23", "2023-08-31");

        Booking.bookListing("133333331", "2", "2023-08-03", "2023-08-09");
        Booking.bookListing("155555551", "2", "2024-12-04", "2024-12-05");
        Booking.bookListing("166666661", "2", "2023-08-23", "2023-09-01");
        Booking.bookListing("266666662", "2", "2023-10-23", "2023-11-03");
        Booking.bookListing("277777772", "2", "2023-12-23", "2023-12-31");

        Booking.bookListing("177777771", "3", "2023-10-13", "2023-10-18");
        Booking.bookListing("188888881", "3", "2023-08-25", "2023-08-26");
        Booking.bookListing("166666661", "3", "2023-08-23", "2023-08-25");
        Booking.bookListing("166666661", "3", "2023-09-04", "2023-09-09");
        Booking.bookListing("166666661", "3", "2023-10-23", "2023-11-03");
        Booking.bookListing("266666662", "3", "2023-11-04", "2023-11-06");
        Booking.bookListing("277777772", "3", "2023-12-12", "2023-12-15");

        Booking.bookListing("188888881", "4", "2025-08-09", "2026-08-25");
        Booking.bookListing("188888881", "4", "2023-11-09", "2023-11-15");
        Booking.bookListing("188888881", "4", "2023-07-22", "2023-07-24");

        Booking.bookListing("133333331", "5", "2023-08-09", "2023-08-25");
        Booking.bookListing("166666661", "5", "2023-05-23", "2023-06-03");
        Booking.bookListing("133333331", "5", "2023-07-11", "2023-07-14");

        Booking.bookListing("166666661", "6", "2023-06-17", "2023-06-19");
        Booking.bookListing("122222221", "6", "2023-07-22", "2023-07-24");
        Booking.bookListing("188888881", "6", "2023-07-27", "2023-07-31");

        Booking.bookListing("177777771", "7", "2023-04-17", "2023-05-19");


        Booking.bookListing("166666661", "8", "2023-06-07", "2023-06-09");
        Booking.bookListing("122222221", "8", "2023-07-12", "2023-07-14");
        Booking.bookListing("188888881", "8", "2023-07-22", "2023-07-24");
        Booking.bookListing("177777771", "8", "2023-09-17", "2023-09-19");
        Booking.bookListing("266666662", "8", "2022-11-04", "2022-11-06");
        Booking.bookListing("277777772", "8", "2022-12-12", "2022-12-15");

        Booking.bookListing("166666661", "9", "2023-11-17", "2023-11-25");
        Booking.bookListing("133333331", "9", "2023-10-09", "2023-10-13");

        Booking.bookListing("122222221", "10", "2023-05-22", "2023-05-24");

        Booking.bookListing("177777771", "11", "2023-02-01", "2023-02-09");
        Booking.bookListing("188888881", "11", "2023-08-01", "2023-08-03");
        Booking.bookListing("188888881", "11", "2024-01-01", "2024-12-31");

        Booking.bookListing("155555551", "12", "2023-12-01", "2023-12-04");
        Booking.bookListing("266666662", "12", "2023-09-14", "2023-09-17");
        Booking.bookListing("277777772", "12", "2023-11-27", "2023-11-29");
        Booking.bookListing("277777772", "12", "2023-11-29", "2023-11-30");
        Booking.bookListing("277777772", "12", "2023-09-29", "2023-09-30");
        Booking.bookListing("277777772", "12", "2023-10-01", "2023-10-02");
        Booking.bookListing("277777772", "12", "2023-10-02", "2023-10-04");
        Booking.bookListing("277777772", "12", "2023-10-04", "2023-10-07");

    }

    public static void updateData() {
        DBTable.dev = true;
        Calendar todayCalendar = Calendar.getInstance();
        String dateString = String.format("%d-%02d-%02d", todayCalendar.get(Calendar.YEAR),
            todayCalendar.get(Calendar.MONTH) + 1, todayCalendar.get(Calendar.DAY_OF_MONTH));
        String[] queries = new String[] {
            "DELETE FROM AvailableDate WHERE startDate <= '" + dateString + "'",
            "UPDATE Booking SET bookingStatus = 'CANCELLED BY RENTER' WHERE renterSin = '255555552' AND startDate <= '2023-11-01'",
            "UPDATE Booking SET bookingStatus = 'CANCELLED BY HOST' WHERE listingId = 12 AND startDate = '2023-11-30'",
            "UPDATE Booking SET bookingStatus = 'CANCELLED BY HOST' WHERE listingId = 2 AND startDate = '2023-10-23'",
            "UPDATE Booking SET bookingStatus = 'CANCELLED BY HOST' WHERE listingId = 3 AND startDate = '2023-12-12'",
            "UPDATE Booking SET bookingStatus = 'CANCELLED BY HOST' WHERE listingId = 8 AND startDate = '2022-11-04'",
            "UPDATE Booking SET bookingStatus = 'CANCELLED BY RENTER' WHERE listingId = 8 AND startDate = '2022-12-12'",
            "UPDATE Booking SET bookingStatus = 'CANCELLED BY RENTER' WHERE listingId = 8 AND startDate = '2023-09-17'",
            "UPDATE Booking SET bookingStatus = 'CANCELLED BY RENTER' WHERE listingId = 12 AND startDate <= '2023-10-02'",
            "UPDATE Booking SET bookingStatus = 'CANCELLED BY HOST' WHERE listingId = 12 AND startDate > '2023-10-02'",
            "INSERT INTO Rating (authorSin, renterSin, listingId, startDate, score, object) VALUES ('277777772', '277777772', 12, '2023-09-29', 3, 'Host')",
            "INSERT INTO Rating (authorSin, renterSin, listingId, startDate, score, object) VALUES ('277777772', '277777772', 12, '2023-09-29', 3, 'Listing')",
            "INSERT INTO Rating (authorSin, renterSin, listingId, startDate, score, object, commentBody) VALUES ('266666662', '266666662', 1, '2022-10-23', 5, 'Host', 'Lovely home! Host was very accomadating of my disability')",
            "INSERT INTO Rating (authorSin, renterSin, listingId, startDate, score, object, commentBody) VALUES ('266666662', '266666662', 1, '2022-10-23', 5, 'Listing', 'Lovely home! Comfortable bed and clean carpet')",
            "INSERT INTO Rating (authorSin, renterSin, listingId, startDate, score, object, commentBody) VALUES ('144444441', '266666662', 1, '2022-10-23', 5, 'Renter', 'Very considerate renter! Definitely recommend')",
            "INSERT INTO Rating (authorSin, renterSin, listingId, startDate, score, object, commentBody) VALUES ('177777771', '177777771', 11, '2023-02-01', 3, 'Host', 'Host was quite unresponsive during the entire stay')",

            "INSERT INTO Rating (authorSin, renterSin, listingId, startDate, score, object, commentBody) VALUES ('166666661', '166666661', 6, '2023-06-17', 4, 'Host', 'While their broken english was hard to understand, they still tried to communicate as best as possible.')",
            "INSERT INTO Rating (authorSin, renterSin, listingId, startDate, score, object, commentBody) VALUES ('166666661', '166666661', 6, '2023-06-17', 3, 'Listing', 'The water was too cold')",

            "INSERT INTO Rating (authorSin, renterSin, listingId, startDate, score, object, commentBody) VALUES ('188888881', '188888881', 8, '2023-07-22', 1, 'Listing', 'Ugliest room I have ever seen. Bed was itchy and uncomfortable.')",
            "INSERT INTO Rating (authorSin, renterSin, listingId, startDate, score, object, commentBody) VALUES ('144444441', '188888881', 8, '2023-07-22', 5, 'Renter', 'Clean and considerate! Recommended booking with him!')",

        };

        for (String query: queries) {
            DBTable.db.executeUpdate(query, null, null);
        }

        for (String amenity: Listing.amenities) {
            String query = String.format("INSERT INTO Amenity (amenityName) VALUES ('%s')", amenity);
            DBTable.db.executeUpdate(query, null, null);
        }

        for (int i = 0; i < 50; i ++) {
            String query = String.format("INSERT INTO ProvidedAmenity (listingId, itemId, price) VALUES (%d, %d, %f)", ((int)(Math.random() * 12)) + 1, ((int)(Math.random() * Listing.amenities.size())) + 1, ((Math.random() * 22)) + 2);
            DBTable.db.executeUpdate(query, null, null);
        }
    }
    /*CREATE TABLE Rating (
	authorSin CHAR(9),
	renterSin CHAR(9),
	listingId INTEGER,
	startDate DATE,
	commentBody TEXT,
	score INTEGER CHECK (score >= 1 AND score <= 5) NOT NULL,
	object VARCHAR(30),
	PRIMARY KEY (authorSin, renterSin, listingId, startDate, object),
	FOREIGN KEY (renterSin) REFERENCES User(sinNumber) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (listingId) REFERENCES Listing(listingId) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (renterSin, listingId, startDate) REFERENCES Booking(renterSin, listingId, startDate) ON DELETE CASCADE ON UPDATE CASCADE
); */
}
