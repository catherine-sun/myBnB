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
}
