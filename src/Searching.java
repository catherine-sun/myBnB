import java.util.ArrayList;
import java.util.Scanner;

public class Searching {

    // private static String type;
    // private static String sAddr;
    // private static String pCode;
    // private static String city;
    // private static String country;
    private static String startDate;
    private static String endDate;
    private static String minPrice;
    private static String maxPrice;
    private static boolean ascendingPrice;

// - return all listings in the vicinty of a location ordered by closest (distance, location specified by user)
// - option to rank listing by ascending or descending price
// - search by postal code: return all listings in the same and adjacent postal codes
// - search exact queries (address input, return listing if it exists)
// - all searches support filters (time, price)

    private static final int edit = 0;
    private static final int clear = 1;
    private static final int exact = 2;
    private static final int nearby = 3;
    private static final int apply = 4;
    private static final int exitSearch = 5;
    private static final int minimize = 6;
    private static final int maximize = 7;
    private static final int togglePriceOrder = 8;

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
                        System.out.print("1 - Start Date\n"
                            + "2 - End Date\n"
                            + "3 - Min Price\n"
                            + "4 - Max Price\n"
                            + "5 - Done\n"
                            + "Enter the field to update: ");

                        choice = input.nextInt();
                        input.nextLine();

                        if (choice < 0 || choice > 4) continue;

                        fields = new String[]{"Start Date", "End Date", "Min Price", "Max Price", "Done"};
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

                        if (startDate != null) {
                            opt += i + " - Start Date\n";
                            optArr.add("Start Date");
                            i++;
                        }

                        if (endDate != null) {
                            opt += i + " - End Date\n";
                            optArr.add("End Date");
                            i++;
                        }

                        if (minPrice != null) {
                            opt += i + " - Min Price\n";
                            optArr.add("Min Price");
                            i++;
                        }

                        if (maxPrice != null) {
                            opt += i + " - Max Price\n";
                            optArr.add("Max Price");
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
                            startDate = null;
                            endDate = null;
                            minPrice = null;
                            maxPrice = null;
                            break;
                        }

                        updateFilter(optArr.get(choice - 1), null);

                    } while (choice != i);
                    choice = maximize;
                    break;

                case exact:
                    fields = new String[]{"Street Address", "Postal Code", "City", "Country"};
                    inp = SQLUtils.getInputArgs(fields);
                    Search.searchByAddress(inp[0], inp[1], inp[2], inp[3]);
                    break;

                case nearby:
                    fields = new String[]{"Postal Code", "Distance"};
                    inp = SQLUtils.getInputArgs(fields);
                    Search.searchNearby(inp[0], Double.parseDouble(inp[1]));
                    break;

                case apply:
                    break;

                case togglePriceOrder:
                    ascendingPrice = !ascendingPrice;
                    choice = minimize;
                    break;

            }
        }
    }

    public static String getSearchPrompt() {
        String filter = "******* Current Filter *******\n"
            + (startDate == null ? "" : " - Start Date:\t" + startDate + "\n")
            + (endDate == null ? "" : " - End Date:\t" + endDate + "\n")
            + (minPrice == null ? "" : " - Min Price:\t" + minPrice + "\n")
            + (maxPrice == null ? "" : " - Max Price:\t" + maxPrice + "\n")
            + (ascendingPrice ? "- Price:\tLow to High\n" : "- Price:\tHigh to Low\n\n");

        return String.format(filter
            + "******* Search Options *******\n"
            + "%2d - Edit filter\n"
            + "%2d - Clear filters\n"
            + "%2d - Search exact destination\n"
            + "%2d - Search nearby destination\n"
            + "%2d - Search all\n"
            + "%2d - Exit search\n"
            + "%2d - Minimize search options",
            edit, clear, exact, nearby, apply, exitSearch, minimize);
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
            case "Start Date":
                startDate = val;
                break;
            case "End Date":
                endDate = val;
                break;
            case "Min Price":
                minPrice = val;
                break;
            case "Max Price":
                maxPrice = val;
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
