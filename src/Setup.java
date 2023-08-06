import java.util.Scanner;

public class Setup {

    /* To run on an empty database */
    public static void main (String[] args) {
        Scanner input = new Scanner(System.in);
        DBConnection db = new DBConnection();
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

        Booking.bookListing("155555551", "1", "2023-09-07", "2023-9-12");
        Booking.bookListing("166666661", "1", "2023-09-12", "2023-9-14");
        Booking.bookListing("155555551", "1", "2023-09-23", "2023-10-05");

        Booking.bookListing("133333331", "2", "2023-08-03", "2023-08-09");
        Booking.bookListing("155555551", "2", "2024-12-04", "2024-12-05");
        Booking.bookListing("166666661", "2", "2023-08-23", "2023-09-01");

        Booking.bookListing("177777771", "3", "2023-10-13", "2023-10-18");
        Booking.bookListing("188888881", "3", "2023-08-25", "2023-08-26");
        Booking.bookListing("166666661", "3", "2023-08-23", "2023-08-25");
        Booking.bookListing("166666661", "3", "2023-09-04", "2023-09-09");
        Booking.bookListing("166666661", "3", "2023-10-23", "2023-11-03");

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

        Booking.bookListing("166666661", "9", "2023-11-17", "2023-11-25");
        Booking.bookListing("133333331", "9", "2023-10-09", "2023-10-13");

        Booking.bookListing("122222221", "10", "2023-05-22", "2023-05-24");

        Booking.bookListing("177777771", "11", "2023-02-01", "2023-02-09");
        Booking.bookListing("188888881", "11", "2023-08-01", "2023-08-03");
        Booking.bookListing("188888881", "11", "2024-01-01", "2024-12-31");

        Booking.bookListing("155555551", "12", "2023-12-01", "2023-12-04");
        
    }
    
}
