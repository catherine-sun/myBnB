import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Searching extends DBTable {

    private static String listingType;
    private static String startDate;
    private static String endDate;
    private static String minPrice;
    private static String maxPrice;
    private static boolean ascendingPrice;

    private static final int start = -1;
    private static final int edit = 0;
    private static final int clear = 1;
    private static final int addAmenity = 2;
    private static final int removeAmenity = 3;
    private static final int exact = 4;
    private static final int nearby = 5;
    private static final int worldwide = 6;
    private static final int exitSearch = 7;

    private static ArrayList<Integer> selectedAmenities = new ArrayList<>();

    private static String displayedFields = PostingDB + ".hostSin, latitude, " + PostingDB + ".listingId, "
        + "longitude, listingType, streetAddress, minPrice, postalCode, averagePrice, city, maxPrice, country";

    private static String postedListings = PostingDB + " INNER JOIN " + ListingDB
        + " ON " + ListingDB + ".listingId = " + PostingDB + ".listingId";

    public static void searchAndFilter(Scanner input) {
        ascendingPrice = true;

        int choice = start;
        while (choice != exitSearch) {

            System.out.println(getSearchPrompt());
            System.out.print(": ");
            choice = input.nextInt();
            input.nextLine();

            String[] inp, fields;
            int i;

            switch (choice) {
                case edit:
                    do {
                        System.out.println("******* Edit Filter *******");
                        System.out.print("1 - Listing Type\n"
                            + "2 - Start Date\n"
                            + "3 - End Date\n"
                            + "4 - Min Price\n"
                            + "5 - Max Price\n"
                            + "6 - Sort by " + (ascendingPrice ? "High to Low\n" : "Low to High\n")
                            + "7 - Done\n"
                            + "Enter the field to update: ");

                        choice = input.nextInt();
                        input.nextLine();

                        if (choice < 0 || choice > 6) continue;

                        if (choice == 6) {
                            ascendingPrice = !ascendingPrice;
                            continue;
                        }

                        fields = new String[]{"Listing Type", "Start Date", "End Date", "Min Price", "Max Price", "Done"};
                        inp = SQLUtils.getInputArgs(new String[] {fields[choice - 1]});
                        updateFilter(fields[choice - 1], inp[0]);

                    } while (choice != 7);
                    choice = start;
                    break;

                case clear:
                    do {
                        System.out.println("******* Clear Filter *******");
                        i = 1;
                        String opt = "";
                        ArrayList<String> optArr = new ArrayList<>();

                        if (listingType != null) {
                            opt += i + " - Listing Type\n";
                            optArr.add("Listing Type");
                            i++;
                        }

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
                            listingType = null;
                            startDate = null;
                            endDate = null;
                            minPrice = null;
                            maxPrice = null;
                            break;
                        }

                        updateFilter(optArr.get(choice - 1), null);

                    } while (choice != i);
                    choice = start;
                    break;

                case exact:
                    fields = new String[]{
                        "Street Address [ENTER to exclude field]",
                        "Postal Code [ENTER to exclude field]",
                        "City [ENTER to exclude field]",
                        "Country [ENTER to exclude field]"};
                    inp = new String[fields.length];
                    for (i = 0; i < fields.length; i++){
                        System.out.print("Enter " + fields[i] + ": ");
                        inp[i] = input.nextLine().trim().replaceAll("'", "''");
                    }
                    searchByAddress(inp[0], inp[1], inp[2], inp[3]);
                    break;

                case nearby:
                    inp = new String[2];
                    do {
                        System.out.print("Enter Postal Code: ");
                        inp[0] = input.nextLine().trim().replaceAll("'", "''");
                    } while (isNullOrEmpty(inp[0]));

                    do {
                        System.out.print("Enter Distance [ENTER to exclude field]: ");
                        inp[1] = input.nextLine().trim().replaceAll("'", "''");
                    } while (!isNullOrEmpty(inp[1]) && Double.parseDouble(inp[1]) < 0);

                    searchNearby(inp[0], isNullOrEmpty(inp[1]) ? -1 : Double.parseDouble(inp[1]));
                    break;

                case worldwide:
                    searchByAddress(null, null, null, null);
                    break;

                case addAmenity:
                    addDesiredAmenity(input);
                    break;

                case removeAmenity:
                    removeDesiredAmenity(input);
                    break;

                case exitSearch:
                    break;

                default:
                    System.out.println("Invalid Option");
            }
        }
    }

    public static String getSearchPrompt() {
        String filter = "******* Desired Amenities *******\n";

        if (selectedAmenities != null) {
            filter += selectedAmenities.size() > 0 ? "" : "Nothing yet\n";
            int i = 1;
            for (int choice : selectedAmenities) {
                filter += String.format("%2d. %s\n",
                    i, Listing.amenities.get(choice - 1));
                i++;
            }
        }

        filter += "******* Current Filter *******\n"
            + (listingType == null ? "" : " - Type:\t" + listingType + "\n")
            + (startDate == null ? "" : " - Start Date:\t" + startDate + "\n")
            + (endDate == null ? "" : " - End Date:\t" + endDate + "\n")
            + (minPrice == null ? "" : " - Min Price:\t" + minPrice + "\n")
            + (maxPrice == null ? "" : " - Max Price:\t" + maxPrice + "\n")
            + (ascendingPrice ? " - Sort Price:\tLow to High\n" : " - Sort Price:\tHigh to Low\n");


        return String.format(filter
            + "******* Search Options *******\n"
            + "%2d - Edit filter\n"
            + "%2d - Clear filters\n"
            + "%2d - Add desired amenities\n"
            + "%2d - Remove desired amenities\n"
            + "%2d - Search exact destination\n"
            + "%2d - Search nearby postal codes\n"
            + "%2d - Search world wide\n"
            + "%2d - Exit search",
            edit, clear, addAmenity, removeAmenity, exact, nearby, worldwide, exitSearch);
    }

    public static void updateFilter(String field, String val) {
        if (val != null && val.trim().isEmpty()) {
            val = null;
        }
        switch (field) {
            case "Listing Type":
                listingType = val;
                break;
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

    public static void searchByAddress(String streetAddress, String postalCode, String city, String country) {

        boolean hasAddr = isNullOrEmpty(streetAddress);
        boolean hasPCode = isNullOrEmpty(postalCode);
        boolean hasCity = isNullOrEmpty(city);
        boolean hasCountry = isNullOrEmpty(country);
        boolean hasType = isNullOrEmpty(listingType);
        boolean prev = !hasAddr;

        String filter = (hasAddr ? "" : "streetAddress = '" + streetAddress + "'")
            + (!hasPCode && !hasAddr ? " AND " : "")
            + (hasPCode ? "" : "postalCode = '" + postalCode + "'")
            + (!hasCity && (prev = prev || !hasPCode) ? " AND " : "")
            + (hasCity ? "" : "city = '" + city + "'")
            + (!hasCountry && (prev = prev || !hasCity) ? " AND " : "")
            + (hasCountry ? "" : "country = '" + country + "'")
            + (!hasType && (prev = prev || !hasCountry) ? " AND " : "")
            + (hasType ? "" : "listingType = '" + listingType + "'");

        String amenityFilter = getAmenityFilter();
        if (!isNullOrEmpty(amenityFilter)) {
            filter += isNullOrEmpty(filter) ? amenityFilter : " AND" + amenityFilter;
        }

        if (!isNullOrEmpty(filter)) {
            filter = "WHERE " + filter;
        }

        String query = String.format("SELECT %s FROM %s %s GROUP BY %s ORDER BY %s",
            displayedFields, postedListings + availableListings(), filter, displayedFields, "averagePrice " + (ascendingPrice ? "ASC" : "DESC"));

        ResultSet rs = db.execute(query, null, null).rs;

        String[] fields = new String[]{"Host:", "Latitude:", "ID:", "Longitude:", "Type:", "Address:", "Min Price:", "Postal code:",
            "Average Price:", "City:", "Max Price:", "Country:"};
        displayListings(rs, fields);
    }

    public static void searchNearby(String postalCode, double radius) {

        String d[] = getXYFromPostalCode(postalCode);

        if (d == null) {
            System.out.println("No listing satisfying filters and postal code " + postalCode);
            return;
        }
        String distance = "( 3959 * ACOS( COS( RADIANS(latitude) ) * COS( RADIANS( " + d[1] +" ) ) * COS( RADIANS( " + d[0] + " ) - RADIANS(longitude) ) + SIN( RADIANS(latitude) ) * SIN( RADIANS( " + d[1] + " ))))";
        String filter = (radius < 0 ? "" : distance + " <= " + radius);

        if (!isNullOrEmpty(listingType)) {
            filter += isNullOrEmpty(filter) ? "listingType = '" + listingType + "'" : " AND listingType = '" + listingType + "'";
        }

        String amenityFilter = getAmenityFilter();
        if (!isNullOrEmpty(amenityFilter)) {
            filter += isNullOrEmpty(filter) ? amenityFilter : " AND" + amenityFilter;
        }
        filter = isNullOrEmpty(filter) ? "" : "WHERE " + filter;
        System.out.println(filter);

        String query = String.format("SELECT %s FROM %s %s GROUP BY %s ORDER BY %s ASC, averagePrice %s",
            displayedFields + ", " + distance + " AS distance", postedListings + availableListings(), filter, displayedFields, "distance", (ascendingPrice ? "ASC" : "DESC"));

        ResultSet rs = db.execute(query, null, null).rs;

        String[] fields = new String[]{"Host:", "Latitude:", "ID:", "Longitude:", "Type:", "Address:", "Min Price:", "Postal code:",
            "Average Price:", "City:", "Max Price:", "Country:", "Relative Dist:", ""};
        displayListings(rs, fields);
    }

    public static String[] getXYFromPostalCode(String postalCode) {
        String query = String.format("SELECT %s FROM %s WHERE %s = '%s'",
        "longitude, latitude, postalCode", postedListings, "postalCode", postalCode);

        String filter = getAmenityFilter();
        if (!isNullOrEmpty(filter)) {
            query += isNullOrEmpty(query) ? " WHERE" + filter : " AND" + filter;
        }

        ResultSet rs = db.execute(query, null, null).rs;
        try {
            if (rs != null && rs.next()) {
                String x = rs.getObject(1).toString();
                String y = rs.getObject(2).toString();
                String id = rs.getString(3);
                return new String[]{x, y, id};
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static String availableListings() {
        boolean hasMin = isNullOrEmpty(minPrice);
        boolean hasMax = isNullOrEmpty(maxPrice);
        boolean hasStart = isNullOrEmpty(startDate);
        boolean hasEnd = isNullOrEmpty(endDate);

        String filter = (hasMin ? "" : "price >= " + minPrice)
            + (!hasMin && !hasMax ? " AND " : "")
            + (hasMax ? "" : "price <= " + maxPrice)
            + (!hasStart && (!hasMin || !hasMax) ? " AND " : "")
            + (hasStart ? "" : "startDate >= '" + startDate + "'")
            + (!hasEnd && (!hasStart || !hasMin || !hasMax) ? " AND " : "")
            + (hasEnd ? "" : "startDate < '" + endDate + "'");

        if (!isNullOrEmpty(filter)) {
            filter = "WHERE " + filter;
        }

        return String.format(" INNER JOIN (SELECT listingId, MIN(price) AS minPrice, AVG(price) AS averagePrice, MAX(price) AS maxPrice FROM AvailableDate %s GROUP BY listingId) AS tmp ON tmp.listingId = Posting.listingId", filter);
    }

    public static void displayListings(ResultSet rs, String[] fields) {
        String hor = " " + "-".repeat(98);
        try {
            if (rs == null || !rs.next()) {
                System.out.println("Nothing to see");
                return;
            }

            do {
                System.out.println(hor);
                for (int i = 0; i < fields.length; i++) {
                    System.out.printf(i%2 == 0 ? "|" : "");

                    if (fields[i].isEmpty()) {
                        System.out.printf(" %-16s %-30s ",
                            fields[i], "");
                    } else if (i == 0) {
                        System.out.printf(" %-16s %-30s ",
                            fields[i], User.getNameBySin(rs.getString(i + 1)));
                    } else if (i == 6 || i == 8 || i == 10) {
                        String amt = String.format("%.2f", rs.getDouble(i + 1));
                        System.out.printf(" %-16s %-30s ",
                            fields[i], "$" + amt);
                    } else if (fields[i].equals("Relative Dist:")) {
                        String dist = String.format("%.2f", rs.getDouble(i + 1));
                        System.out.printf(" %-16s %-30s ",
                            fields[i], dist + " km");
                    } else {
                        System.out.printf(" %-16s %-30s ",
                            fields[i], rs.getObject(i + 1).toString());
                    }

                    System.out.printf(i%2 == 1 ? "|\n" : "");
                }
            } while (rs.next());

            System.out.println(hor);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addDesiredAmenity(Scanner input) {
        int choice = 0;

        System.out.println("******* List of Amenities *******");
        for (int i = 0; i < Listing.amenities.size(); i++) {
            if (!selectedAmenities.contains(Integer.valueOf(i + 1))) {
                System.out.printf("%2d. %-30s", i + 1, Listing.amenities.get(i));
            }
            if ((i + 1)%2 == 0 || i + 1 == Listing.amenities.size()) {
                System.out.println();
            }
        }
        System.out.println("Enter the amenity you are looking for (type in comma-separated list of numbers):");
        System.out.print(": ");

        String cmd = input.nextLine().replaceAll(" ", "");
        if (isNullOrEmpty(cmd)) {
            System.out.println("No changes made");
            return;
        }
        String[] inp = cmd.split(",");
        for (String s : inp) {
            choice = Integer.parseInt(s);
            if (selectedAmenities.contains(choice)) {
                System.out.println(Listing.amenities.get(choice - 1) + " already added");
            } else if (choice >= 1 && choice <= Listing.amenities.size()) {
                selectedAmenities.add(choice);
            }
        }
    }

    public static void removeDesiredAmenity(Scanner input) {
        int choice = 0;
        if (selectedAmenities == null || selectedAmenities.isEmpty()) {
            System.out.println("No amenities currently selected");
            return;
        }

        System.out.println("******* Your Selected Amenities *******");
        for (int i = 0; i < selectedAmenities.size(); i++) {
            System.out.printf("%2d. %-30s", i + 1, Listing.amenities.get(selectedAmenities.get(i) - 1));
            if ((i + 1)%2 == 0 || i + 1 == selectedAmenities.size()) {
                System.out.println();
            }
        }
        System.out.println("Enter the amenity filter you would like to remove (type in comma-separated list of numbers):");
        System.out.print(": ");

        String cmd = input.nextLine().replaceAll(" ", "");
        if (isNullOrEmpty(cmd)) {
            System.out.println("No changes made");
            return;
        }
        String[] inp = cmd.split(",");
        for (String s : inp) {
            choice = Integer.parseInt(s);
            if (choice >= 1 && choice <= selectedAmenities.size()) {
                System.out.println(Listing.amenities.get(selectedAmenities.get(choice - 1) - 1) + " sucessfully removed");
                selectedAmenities.remove(choice - 1);
            }
        }
    }

    public static String getAmenityFilter() {
        if (selectedAmenities == null || selectedAmenities.isEmpty()) {
            return "";
        }
        String filter = "";
        for (int choice : selectedAmenities) {
            filter += filter.isEmpty() ? "" : " AND ";
            filter += "itemId = " + choice;
        }

        return String.format(" EXISTS (SELECT listingId, itemId FROM ProvidedAmenity WHERE %s AND ProvidedAmenity.listingId = Posting.listingId)", filter);
    }

}
