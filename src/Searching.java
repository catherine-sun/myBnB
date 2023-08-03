import java.util.ArrayList;
import java.util.Scanner;

public class Searching {

    private static String type;
    private static String sAddr;
    private static String pCode;
    private static String city;
    private static String country;
    private static boolean ascendingPrice;

    private static final int edit = 0;
    private static final int apply = 1;
    private static final int clear = 2;
    private static final int nearby = 3;
    private static final int exitSearch = 4;
    private static final int minimize = 5;
    private static final int maximize = 6;
    private static final int togglePriceOrder = 7;

    public static void searchAndFilter(Scanner input) {
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
                            + "2 - Street Address\n"
                            + "3 - Postal Code\n"
                            + "4 - City\n"
                            + "5 - Country\n"
                            + "6 - Done\n"
                            + "Enter the field to update: ");

                        choice = input.nextInt();
                        input.nextLine();

                        if (choice < 0 || choice > 5) continue;

                        fields = new String[]{"Property Type", "Street Address", "Postal Code", "City", "Country", "Done"};
                        inp = SQLUtils.getInputArgs(new String[] {fields[choice - 1]});
                        updateFilter(fields[choice - 1], inp[0]);

                    } while (choice != 6);
                    choice = maximize;
                    break;

                case clear:
                    int i;
                    do {
                        i = 1;
                        String opt = "";
                        ArrayList<String> optArr = new ArrayList<>();

                        if (type != null) {
                            opt += i + " - Property Type\n";
                            optArr.add("Property Type");
                            i++;
                        }

                        if (sAddr != null) {
                            opt += i + " - Street Address\n";
                            optArr.add("Street Address");
                            i++;
                        }

                        if (pCode != null) {
                            opt += i + " - Postal Code\n";
                            optArr.add("Postal Code");
                            i++;
                        }

                        if (city != null) {
                            opt += i + " - City\n";
                            optArr.add("City");
                            i++;
                        }

                        if (country != null) {
                            opt += i + " - Country\n";
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
                            sAddr = null;
                            pCode = null;
                            city = null;
                            country = null;
                            break;
                        }

                        updateFilter(optArr.get(choice - 1), null);

                    } while (choice != i);
                    choice = maximize;
                    break;

                case nearby:
                    fields = new String[]{"Postal Code", "Distance"};
                    inp = SQLUtils.getInputArgs(fields);
                    Search.searchNearby(inp[0], Double.parseDouble(inp[1]));
                    break;

                case togglePriceOrder:
                    ascendingPrice = !ascendingPrice;
                    choice = minimize;

                case apply:
                    Search.searchByAddress(sAddr, pCode, city, country);
                    break;
            }
        }
    }

    public static String getSearchPrompt() {
        String filter = "******* Current Filter *******\n"
            + ((type == null && sAddr == null && pCode == null && city == null && country == null) ?
            "No filter applied\n" :
            (type == null ? "" : " - Type:\t" + type + "\n")
            + (sAddr == null ? "" : " - Address:\t" + sAddr + "\n")
            + (pCode == null ? "" : " - Postal Code:\t" + pCode + "\n")
            + (city == null ? "" : " - City:\t" + city + "\n")
            + (country == null ? "" : " - Country:\t" + country + "\n\n"));

        return String.format(filter
            + "******* Search Options *******\n"
            + "%2d - Edit filter\n"
            + "%2d - Apply filters\n"
            + "%2d - Clear filters\n"
            + "%2d - Search nearby\n"
            + "%2d - Exit search\n"
            + "%2d - Minimize search options",
            edit, apply, clear, nearby, exitSearch, minimize);
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
        if (val.trim().isEmpty()) {
            val = null;
        }
        switch (field) {
            case "Property Type":
                type = val;
                break;
            case "Street Address":
                sAddr = val;
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
