import java.util.ArrayList;
import java.util.Scanner;

public class Searching {

    private static String type;
    private static String pCode;
    private static String city;
    private static String country;
    private static boolean ascendingPrice;

    private static final int edit = 0;
    private static final int apply = 1;
    private static final int clear = 2;
    private static final int exitSearch = 3;
    private static final int minimize = 4;
    private static final int maximize = 5;
    private static final int togglePriceOrder = 6;

    public static void searchAndFilter() {
        Scanner input = new Scanner(System.in);

        ascendingPrice = true;

        int choice = maximize;
        while (choice != exitSearch) {

            switch (choice) {
                case minimize:
                    System.out.println(getMinimizedPrompt());
                    break;
                default:
                    System.out.println(getSearchPrompt());
            }

            System.out.print(": ");
            choice = input.nextInt();
            input.nextLine();

            String[] inp, fields;

            switch (choice) {
                case edit:
                    do {
                        System.out.print("1 - Property Type\n"
                            + "2 - Postal Code\n"
                            + "3 - City\n"
                            + "4 - Country\n"
                            + "5 - Done\n"
                            + "Enter the field to update: ");

                        choice = input.nextInt();
                        input.nextLine();

                        if (choice < 0 || choice > 4) continue;

                        fields = new String[]{"Property Type", "Postal Code", "City", "Country", "Done"};
                        inp = SQLUtils.getInputArgs(new String[] {fields[choice - 1]});
                        updateFilter(fields[choice - 1], inp[0]);

                    } while (choice != 5);
                    choice = maximize;
                    break;

                case clear:
                    int i;
                    do {
                        i = 1;
                        String opt = "";
                        ArrayList<String> optArr = new ArrayList<>();

                        if (type != null) {
                            opt += String.format ("%d - Property Type\n", i);
                            optArr.add("Property Type");
                            i++;
                        }

                        if (pCode != null) {
                            opt += String.format ("%d - Postal Code\n", i);
                            optArr.add("Postal Code");
                            i++;
                        }

                        if (city != null) {
                            opt += String.format ("%d - City\n", i);
                            optArr.add("City");
                            i++;
                        }

                        if (country != null) {
                            opt += String.format ("%d - Country\n", i);
                            optArr.add("Country");
                            i++;
                        }

                        opt += i + " - Clear all\n";
                        i++;
                        opt += i + " - Done\nEnter the field to clear: ";

                        System.out.println(opt);

                        choice = input.nextInt();
                        input.nextLine();

                        if (choice < 0 || choice >= i) continue;

                        if (choice == i - 1) {
                            type = null;
                            pCode = null;
                            city = null;
                            country = null;
                            break;
                        }

                        updateFilter(optArr.get(choice - 1), null);

                    } while (choice != i);
                    choice = maximize;
                    break;

                case togglePriceOrder:
                    ascendingPrice = !ascendingPrice;
                    choice = minimize;

                case apply:
                    display();
                    break;
            }
        }

        // input.close();
    }

    public static String getSearchPrompt() {
        String filter = (type == null && pCode == null && city == null && country == null) ?
            "No filter applied\n" :
            "******* Current Filter *******\n"
            + (type == null ? "" : " - Type:\t" + type + "\n")
            + (pCode == null ? "" : " - Postal Code:\t" + pCode + "\n")
            + (city == null ? "" : " - City:\t" + city + "\n")
            + (country == null ? "" : " - Country:\t" + country + "\n\n");

        return String.format(filter
            + "******* Search Options *******\n"
            + "%2d - Edit filter\n"
            + "%2d - Apply filters\n"
            + "%2d - Clear filters\n"
            + "%2d - Exit search\n"
            + "%2d - Minimize search options",
            edit, apply, clear, exitSearch, minimize);
    }

    public static String getMinimizedPrompt() {
        return String.format(
            " %2d - Open search options\t"
            + "%2d - Sort by "
            + (ascendingPrice ? "descending" : "ascending")
            + " price",
            maximize, togglePriceOrder);
    }

    public static void updateFilter(String field, String val) {
        if (val == "") {
            val = null;
        }
        switch (field) {
            case "Property Type":
                type = val;
                break;
            case "Postal Code":
                pCode = val;
                break;
            case "City":
                city = val;
                break;
            case "Country":
                country = val;
                break;
            default:
                break;
        }
    }

    public static void display() {
        /* TODO */

        System.out.println(getMinimizedPrompt());
    }
}
