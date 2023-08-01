import java.sql.Date;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.println("Hello, World!");

        DBConnection db = new DBConnection();
        User user = new User();
        user.setConnection(db);
        int cmd;
        boolean running = true;

        while (running) {
            System.out.println("Enter command: ");
            cmd = input.nextInt();
            input.nextLine();
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
                    String sin, fullName, occupation, address, dateOfBirth;
                    do {
                        System.out.println("Enter the sin: ");
                        sin = input.nextLine().trim();
                    } while (sin.length() == 0);
                    do {
                        System.out.println("Enter the user: ");
                        fullName = input.nextLine().trim();
                    } while (fullName.length() == 0);
                    do {
                        System.out.println("Enter the occupation: ");
                        occupation = input.nextLine().trim();
                    } while (occupation.length() == 0);
                    do {
                        System.out.println("Enter the address: ");
                        address = input.nextLine().trim();
                    } while (address.length() == 0);
                    do {
                        System.out.println("Enter the date: ");
                        dateOfBirth = input.nextLine().trim();
                    } while (dateOfBirth == null);
                    user.createUser(sin, fullName, occupation, address, dateOfBirth);
                    break;
            }
        }
    }
}
