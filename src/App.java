import java.sql.Date;
import java.util.Scanner;

public class App {

    private static UserModel sessionUser;

    enum Menu {
        MAIN,
        ACCOUNT,
        HOST_TOOLKIT
    };

    public static void main(String[] args) throws Exception {
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

        /*
        * add a new profile
        * edit profile
        * delete profile
        * add payment info
        * view renting history
        * view future bookings
        * view listings
        * view rental history of each listing
        *
        * add a new listing
        * edit a listing
        * update availability of listing (unless its booked)
        * update price of a listing (during a given time range, notify host if they cannot change)
        * remove a listing
        *
        * book a listing
        * cancel a booking (record if its been canceled)
        *
        * rate and comment (listing, renter, hoster)
        */

        /* Commands */
        final int start = 0;
        final int newSession = 1;
        final int createUser = 2;
        final int displayListings = 3;
        final int searchListings = 4;
        final int bookListing = 5;
        final int cancelBooking = 6;
        final int rate = 7;
        final int gotoAccount = 8;
        final int gotoHostToolkit = 9;
        final int exit = 10;
        final int displayUser = 11;
        final int updateUser = 12;
        final int addPay = 13;
        final int rentingHistory = 14;
        final int deleteUser = 15;
        final int createListing = 16;
        final int displayUserListings = 17;
        final int updateListing = 18;
        final int updateAvailability = 19;
        final int updatePrice = 20;
        final int deleteListing = 21;

        String loginPrompt = sessionUser == null ?
                            String.format("You are currently not signed in\n"
                            + "%2d - Log in\n"
                            + "%2d - Create a new account",
                            newSession, createUser)
                            : "You are currenlty signed in as "
                            + sessionUser.getFullName();

        String prompt = String.format(
                        "******* Welcome to myBnB! *******\n"
                        + "%2d - Log in\n"
                        + "%2d - Create a new account\n"
                        + "%2d - Browse listings\n"
                        + "%2d - Search and filter\n"
                        + "%2d - Book a listing\n"
                        + "%2d - Cancel a booking\n"
                        + "%2d - Rate and comment\n"
                        + "%2d - Account settings\n"
                        + "%2d - Host toolkit\n"
                        + "%2d - Exit session",
                        newSession, createUser, displayListings, searchListings,
                        bookListing, cancelBooking, rate, gotoAccount,
                        gotoHostToolkit, exit);

        String userPrompt = String.format(
                            "******* Account Settings *******\n"
                            + "%2d - View my profile\n"
                            + "%2d - Change profile details\n"
                            + "%2d - Add payment info\n"
                            + "%2d - View my renting history\n"
                            + "%2d - Delete my account\n"
                            + "%2d - Return to main menu",
                            displayUser, updateUser, addPay, rentingHistory,
                            deleteUser, start);

        String hostPrompt = String.format(
                            "******* Host Toolkit *******\n"
                            + "%2d - Add a new listing\n"
                            + "%2d - View my listings\n"
                            + "%2d - Change listing details\n"
                            + "%2d - Change listing availability\n"
                            + "%2d - Change listing price\n"
                            + "%2d - Remove listing\n"
                            + "%2d - Return to main menu",
                            createListing, displayUserListings,
                            updateListing, updateAvailability, updatePrice,
                            deleteListing, start);

        //User user = new User();
        //user.setConnection(db);
        //Listing listing = new Listing();
        //listing.setConnection(db);
        DBTable.db = db;
        String[] inp, fields;
        String sin;
        QueryResult res;

        Menu curMenu = Menu.MAIN;

        int choice = start;
        while (choice != exit) {

            switch(choice) {
                case gotoAccount:
                    curMenu = Menu.ACCOUNT;
                    System.out.println(userPrompt);
                    break;
                case gotoHostToolkit:
                    curMenu = Menu.HOST_TOOLKIT;
                    System.out.println(hostPrompt);
                    break;
                default:
                    curMenu = Menu.MAIN;
                    System.out.println(prompt);
                    break;
            }

            choice = input.nextInt();
            input.nextLine();

            if ((curMenu == Menu.MAIN && (choice < 1 || choice > 10)) ||
                ((curMenu == Menu.ACCOUNT && (choice < 11 || choice > 15)) ||
                (curMenu == Menu.HOST_TOOLKIT && (choice < 16 || choice > 21)))
                && choice != start) {
                System.out.println("Invalid option");
                choice = start;
            }

            switch(choice) {
                case newSession:
                    if (sessionUser != null) {
                        System.out.println("Logged out of " + sessionUser.getFullName());
                    }
                    do {
                        System.out.print("Enter SIN: ");
                        sin = input.nextLine().trim();

                        if (!sin.matches("[A-Za-z0-9]{9}")) {
                            System.out.println("SIN must be 9 characters long and consist of only letters and numbers");
                        }
                    } while (!sin.matches("[A-Za-z0-9]{9}"));

                    res = User.findUser(sin);
                    sessionUser = res.rs.next() ? new UserModel(res.rs) : null;

                    System.out.println(sessionUser == null ? "User does not exist" : "Successfully logged in!");
                    continue;

                case createUser:
                    if (sessionUser != null) {
                        System.out.println("Logged out of " + sessionUser.getFullName());
                    }
                    fields = new String[]{"Sin Number", "Full Name", "Occupation", "Address", "Date of Birth"};
                    inp = SQLUtils.getInputArgs(fields);
                    User.createUser(inp[0], inp[1], inp[2], inp[3], inp[4]);
                    sessionUser = new UserModel(inp[0], inp[1], inp[2], inp[3], Date.valueOf(inp[4]));
                    continue;

                case displayListings:
                    /* TODO */
                    continue;

                case searchListings:
                    /* TODO */
                    continue;

                case gotoAccount:
                    continue;

                case gotoHostToolkit:
                    continue;

                case start:
                    continue;

                case exit:
                    continue;

                default:
                    if (sessionUser == null) {
                        System.out.println("Please log in or create an account");
                        continue;
                    }
                    break;
                }

            /* Login required */
            switch (choice) {
                case bookListing:
                    sin = sessionUser.getSinNumber();
                    if (!user.isRenter(sin)) {
                        user.createRenter(sin);
                    }
                    /* TODO */
                    fields = new String[]{"Listing ID", "Start date", "End date"};
                    inp = SQLUtils.getInputArgs(fields);
                    Booking.bookListing(sin, inp[0], inp[1], inp[2]);
                    continue;

                case cancelBooking:
                    /* TODO */
                    continue;

                case rate:
                    /* TODO */
                    continue;

                case displayUser:
                    /* TODO */
                    continue;

                case updateUser:
                    do {
                        System.out.print("1 - Occupation\n"
                                        + "2 - Address\n"
                                        + "3 - Date of Birth\n"
                                        + "4 - Done\n"
                                        + "Enter the field to update: ");

                        choice = input.nextInt();
                        input.nextLine();

                        if (choice < 0 || choice >= 4) continue;
                        fields = new String[]{"occupation", "address", "dateOfBirth"};
                        inp = SQLUtils.getInputArgs(new String[] {fields[choice - 1]});
                        User.updateProfile(sessionUser.getSinNumber(), fields[choice -1], inp[0]);

                    } while (choice != 4);
                    continue;

                case addPay:
                    // If the current user isn't a renter, create a new entry in the renter table
                    sin = sessionUser.getSinNumber();
                    if (!User.isRenter(sin)) {
                        user.createRenter(sin);
                    }
                    /* TODO */
                    continue;

                case rentingHistory:
                    sin = sessionUser.getSinNumber();
                    if (!user.isRenter(sin)) {
                        System.out.println("Empty");
                    }
                    /* TODO */
                    continue;

                case deleteUser:
                    user.deleteUser(sessionUser.getSinNumber());
                    sessionUser = null;
                    continue;

                case createListing:
                    fields = new String[] {"Listing Type", "Latitude", "Longitude", "Street Address", "Postal Code", "City", "Country"};
                    inp = SQLUtils.getInputArgs(fields);
                    Listing.createListing(sessionUser.getSinNumber(), inp[0], inp[1], inp[2], inp[3], inp[4], inp[5], inp[6]);
                    continue;

                default:
                    break;
                }

            /* Hosts only */
            if (!user.isHost(sessionUser.getSinNumber())) {
                System.out.println("You currently aren't hosting any listings");
                continue;
            }

            switch (choice) {
                case displayUserListings:
                    /* TODO */
                    continue;

                case updateListing:
                    /* TODO */
                    continue;

                case updateAvailability:
                    fields = new String[] {"Listing Id"};
                    inp = SQLUtils.getInputArgs(fields);
                    Listing.setAvailableDateRange(inp[0]);
                    continue;

                case updatePrice:
                    /* TODO */
                    continue;

                case deleteListing:
                    /* TODO */
                    continue;
            }
        }

        input.close();
    }

    public void setSessionUser(UserModel usr) { sessionUser = usr; }
    public UserModel getSessionUser() { return sessionUser; }
}
