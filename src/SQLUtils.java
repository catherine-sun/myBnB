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

    public static String[] getInputArgs (String[] names, int numArgs) {
        Scanner input = new Scanner(System.in);
        String[] args = new String[numArgs];
        for (int i = 0; i < numArgs; i++){
            do {
                System.out.println("Enter field " + names[i] + ":");
                args[i] = input.nextLine().trim();
            } while (args[i].length() == 0);
        }
        //input.close();
        return args;
    }
}
