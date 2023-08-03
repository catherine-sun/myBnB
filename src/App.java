import java.sql.Date;
import java.util.Scanner;

public class App {

    private static UserModel sessionUser;

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
                            + "0 - Return to main menu",
                            displayUser, updateUser, addPay, rentingHistory,
                            deleteUser);

        String hostPrompt = String.format(
                            "******* Host Toolkit *******\n"
                            + "%2d - Add a new listing\n"
                            + "%2d - View my listings\n"
                            + "%2d - Change listing details\n"
                            + "%2d - Change listing availability\n"
                            + "%2d - Change listing price\n"
                            + "%2d - Remove listing\n"
                            + "0 - Return to main menu",
                            createListing, displayUserListings,
                            updateListing, updateAvailability, updatePrice,
                            deleteListing);

        String searchPrompt = "TODO";

        User user = new User();
        user.setConnection(db);
        Listing listing = new Listing();
        listing.setConnection(db);
        String[] inp, f;

        enum menu {
            MAIN,
            ACCOUNT,
            HOST_TOOLKIT
        }

        menu curMenu = menu.MAIN;

        int choice = 0;
        while (choice != exit) {

            switch(choice) {
                case gotoAccount:
                    curMenu = menu.ACCOUNT;
                    System.out.println(userPrompt);
                    break;
                case gotoHostToolkit:
                    curMenu = menu.HOST_TOOLKIT;
                    System.out.println(hostPrompt);
                    break;
                default:
                    System.out.println(prompt);
                    break;
            }
            System.out.println(curMenu);

            choice = input.nextInt();
            input.nextLine();

            if ((curMenu == menu.MAIN && (choice < 1 || choice > 10)) ||
                (curMenu == menu.ACCOUNT && choice != 0 && (choice < 11 || choice > 15)) ||
                (curMenu == menu.HOST_TOOLKIT && choice != 0 && (choice < 16 || choice > 21))) {

                System.out.println((choice != 0 || choice < 11 || choice > 15));
                System.out.println("Invalid option " + choice  + " on menu " + curMenu);
                continue;
            }

            switch(choice) {
                case newSession:
                    if (sessionUser != null) {
                        System.out.println("Logged out of " + sessionUser.getFullName());
                    }
                    /* TODO */
                case createUser:
                    f = new String[]{"Sin Number", "Full Name", "Occupation", "Address", "Date of Birth"};
                    inp = SQLUtils.getInputArgs(f);
                    user.createUser(inp[0], inp[1], inp[2], inp[3], inp[4]);
                    sessionUser = new UserModel(inp[0], inp[1], inp[2], inp[3], Date.valueOf(inp[4]));
                    break;
                case displayListings:
                    /* TODO */
                    break;
                case searchListings:
                    System.out.println(searchPrompt);
                    /* TODO */
                    break;
                case bookListing:
                    /* TODO */
                    break;
                case cancelBooking:
                    /* TODO */
                    break;
                case rate:
                    /* TODO */
                    break;
                case exit:
                    /* TODO */
                    break;
                case displayUser:
                    /* TODO */
                    break;
                case updateUser:
                    if (sessionUser == null) {
                        System.out.println("Please log in or create an account");
                        break;
                    }
                    do {
                        System.out.print("1 - Occupation\n"
                                        + "2 - Address\n"
                                        + "3 - Date of Birth\n"
                                        + "4 - Done\n"
                                        + "Enter the field to update: ");

                        choice = input.nextInt();
                        input.nextLine();

                        if (choice < 0 || choice >= 4) continue;
                        f = new String[]{"occupation", "address", "dateOfBirth"};
                        inp = SQLUtils.getInputArgs(new String[] {f[choice - 1]});
                        user.updateProfile(sessionUser.getSinNumber(), f[choice -1], inp[0]);

                    } while (choice != 4);
                break;
                case addPay:
                    /* TODO */
                    break;
                case rentingHistory:
                    /* TODO */
                    break;
                case deleteUser:
                    /* TODO */
                    break;
                case createListing:
                    f = new String[] {"Your Sin Number", "Listing Type", "Latitude", "Longitude", "Street Address", "Postal Code", "City", "Country"};
                    inp = SQLUtils.getInputArgs(f);
                    listing.createListing(inp[0], inp[1], inp[2], inp[3], inp[4], inp[5], inp[6], inp[7]);
                    break;
                case displayUserListings:
                    /* TODO */
                    break;
                case updateListing:
                    /* TODO */
                    break;
                case updateAvailability:
                    /* TODO */
                    break;
                case updatePrice:
                    /* TODO */
                    break;
                case deleteListing:
                    /* TODO */
                    break;
                default:
                    curMenu = menu.MAIN;
                    break;
            }
        }


        // // DBConnection db = new DBConnection();
        // User user = new User();
        // user.setConnection(db);
        // Listing listing = new Listing();
        // listing.setConnection(db);
        // int cmd;
        // boolean running = false;

        // while (running) {
        //     System.out.println("Enter command: ");
        //     cmd = input.nextInt();
        //     input.nextLine();
        //     String sin;
        //     String[] inputs, fields;
        //     switch(cmd){
        //         case 0:
        //             running = false;
        //             break;
        //         case 1:
        //             String connectString1, user1, pwd1;
        //             do {
        //                 System.out.println("Enter the connection string: ");
        //                 connectString1 = input.nextLine().trim();
        //             } while (connectString1.length() == 0);
        //             do {
        //                 System.out.println("Enter the user: ");
        //                 user1 = input.nextLine().trim();
        //             } while (user1.length() == 0);
        //             do {
        //                 System.out.println("Enter the password: ");
        //                 pwd1 = input.nextLine().trim();
        //             } while (pwd1.length() == 0);
        //             db.connect(connectString1, user1, pwd1);
        //             break;
        //         case 2:
        //             fields = new String[]{"Sin Number", "Full Name", "Occupation", "Address", "Date of Birth"};
        //             inputs = SQLUtils.getInputArgs(fields);
        //             user.createUser(inputs[0], inputs[1], inputs[2], inputs[3], inputs[4]);
        //             break;
        //         case 3:
        //             // update user profile
        //             fields = new String[] {"Sin Number"};
        //             inputs = SQLUtils.getInputArgs(fields);
        //             if (!user.isUser(inputs[0])) {
        //                 System.out.println("Error. Invalid sin number = " + inputs[0]);
        //             } else {
        //                 sin = inputs[0];
        //                 do {
        //                     System.out.println("1. Occupation\n2. Address\n3. Date of birth\n4. Done\nEnter the field to update:");
        //                     cmd = input.nextInt(); input.nextLine();
        //                     if (cmd < 0 || cmd >= 4) continue;
        //                     fields = new String[]{"occupation", "address", "dateOfBirth"};
        //                     inputs = SQLUtils.getInputArgs(new String[] {fields[cmd - 1]});
        //                     user.updateProfile(sin, fields[cmd -1], inputs[0]);
        //                 } while (cmd != 4);

        //             }
        //             break;
        //         case 4:
        //             // create a new listing
        //             fields = new String[] {"Your Sin Number", "Listing Type", "Latitude", "Longitude", "Street Address", "Postal Code", "City", "Country"};
        //             inputs = SQLUtils.getInputArgs(fields);
        //             listing.createListing(inputs[0], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5], inputs[6], inputs[7]);
        //             break;
        //         case 5:
        //     }
        // }
        input.close();
    }

    public void setSessionUser(UserModel usr) {
        sessionUser = usr;
    }

    public UserModel getSessionUser() {
        return sessionUser;
    }
}
