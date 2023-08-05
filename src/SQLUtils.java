import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SQLUtils {

    public static String argList (String[] args) {
        String str = "";
        if (args.length > 0)
        {
            str = args[0];
            for (int i = 1; i < args.length; i++) {
                if (args[i] != null){
                    str += ", " + args[i];
                } else {
                    str += ", NULL";
                }
            }
        }
        return str;
    }

    public static String[] getInputArgs (String[] names) {
        Scanner input = new Scanner(System.in);
        String[] args = new String[names.length];
        for (int i = 0; i < names.length; i++){
            do {
                System.out.println("Enter " + names[i] + ":");
                args[i] = input.nextLine().trim();
            } while (args[i].length() == 0);
        }
        //input.close();
        return args;
    }

    public static boolean resultSetIsEmpty (ResultSet rs){
        boolean isEmpty = false;

        try {
            isEmpty = rs.first();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return isEmpty;
    }
}
