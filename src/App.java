import java.sql.Date;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.println("Hello, World!");

        DBConnection db = new DBConnection();
        User user = new User();
        Listing listing = new Listing();
        user.setConnection(db);
        listing.setConnection(db);
        int cmd;
        boolean running = true;

        while (running) {
            System.out.println("Enter command: ");
            cmd = input.nextInt();
            input.nextLine();
            String sin;
            String[] inputs, fields;
            switch(cmd){
                case 0:
                    running = false;
                    break;
                case 1:
                    String connectString1, user1, pwd1;
                    do {
                        System.out.println("Enter the connection string: ");
                        connectString1 = input.nextLine().trim();
                    } while (connectString1.length() == 0);
                    do {
                        System.out.println("Enter the user: ");
                        user1 = input.nextLine().trim();
                    } while (user1.length() == 0);
                    do {
                        System.out.println("Enter the password: ");
                        pwd1 = input.nextLine().trim();
                    } while (pwd1.length() == 0);
                    db.connect(connectString1, user1, pwd1);
                    break;
                case 2:
                    fields = new String[]{"Sin Number", "Full Name", "Occupation", "Address", "Date of Birth"};
                    inputs = SQLUtils.getInputArgs(fields);
                    user.createUser(inputs[0], inputs[1], inputs[2], inputs[3], inputs[4]);
                    break;
                case 3:
                    // update user profile
                    fields = new String[] {"Sin Number"};
                    inputs = SQLUtils.getInputArgs(fields);
                    if (!user.isUser(inputs[0])) {
                        System.out.println("Error. Invalid sin number = " + inputs[0]);
                    } else {
                        sin = inputs[0];
                        do {
                            System.out.println("1. Occupation\n2. Address\n3. Date of birth\n4. Done\nEnter the field to update:");
                            cmd = input.nextInt(); input.nextLine();
                            if (cmd < 0 || cmd >= 4) continue;            
                            fields = new String[]{"occupation", "address", "dateOfBirth"};
                            inputs = SQLUtils.getInputArgs(new String[] {fields[cmd - 1]});
                            user.updateProfile(sin, fields[cmd -1], inputs[0]);
                        } while (cmd != 4);

                    }   
                    break;
                case 4:
                    // create a new listing
                    fields = new String[] {"Your Sin Number", "Listing Type", "Latitude", "Longitude", "Street Address", "Postal Code", "City", "Country"};
                    inputs = SQLUtils.getInputArgs(fields);
                    listing.createListing(inputs[0], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5], inputs[6], inputs[7]);
                    break;
                case 5:
            }
        }
        input.close();
    }
}
