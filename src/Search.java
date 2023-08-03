import java.sql.ResultSet;
import java.sql.SQLException;

public class Search extends DBTable {

    private static String displayedFields = PostingDB + ".hostSin, " + PostingDB + ".listingId,"
        + "listingType, latitude, longitude, streetAddress, postalCode, city, country";

    private static String postedListings = PostingDB + " INNER JOIN " + ListingDB
        + " ON " + ListingDB + ".listingId = " + PostingDB + ".listingId";

    public static void searchByAddress(String streetAddress, String postalCode, String city, String country) {

        String filter = (isNullOrEmpty(streetAddress) ? "" : "streetAddress = '" + streetAddress + "'")
            + (isNullOrEmpty(postalCode) ? "" : "postalCode = '" + postalCode + "'")
            + (isNullOrEmpty(city) ? "" : "city = '" + city + "'")
            + (isNullOrEmpty(country) ? "" : "country = '" + country + "'");

        if (!isNullOrEmpty(filter)) {
            filter = "WHERE " + filter;
        }

        String query = String.format("SELECT %s FROM %s %s",
            displayedFields, postedListings, filter);

        ResultSet rs = db.execute(query, null, null).rs;
        displayListings(rs);
    }

    public static void searchNearby(String postalCode, double radius) {

        String d[] = getXYFromPostalCode(postalCode);

        if (d == null) {
            System.out.println("No listing with postal code " + postalCode);
            return;
        }

        String distance = "SQRT(POWER(longitude - " + d[0] + ", 2) +  POWER(latitude - " + d[1] + ", 2))";

        String query = String.format("SELECT %s FROM %s WHERE %s != '%s' AND %s <= %f ORDER BY %s ASC",
            displayedFields + ", " + distance, postedListings, ListingDB + ".listingId", d[2], distance, radius, "10");

        System.out.println(query);
        ResultSet rs = db.execute(query, null, null).rs;
        displayListings(rs);
    }

    public static String[] getXYFromPostalCode(String postalCode) {
        String query = String.format("SELECT %s FROM %s WHERE %s = '%s'",
        "longitude, latitude, Listing.listingId, postalCode", postedListings, "postalCode", postalCode);

        ResultSet rs = db.execute(query, null, null).rs;
        try {
            if (rs.next()) {
                String x = rs.getObject(1).toString();
                String y = rs.getObject(2).toString();
                String id = rs.getString(3);
                return new String[]{x, y, id};
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }


// CREATE TABLE Listing (
// 	listingId INTEGER AUTO_INCREMENT PRIMARY KEY,
// 	listingType VARCHAR(30),
// 	latitude REAL CHECK (latitude >= -90 AND latitude <= 90),
// 	longitude REAL CHECK (longitude >= -180 AND longitude <= 180),
// 	streetAddress VARCHAR(30) NOT NULL,
// 	postalCode CHAR(6) NOT NULL,
// 	city VARCHAR(30) NOT NULL,
// 	country VARCHAR(30) NOT NULL
// );

    public static void displayListings(ResultSet rs) {

        String[] fields = new String[]{"Host", "ID", "Type", "Latitude", "Longitude", "Address", "Postal code", "City", "Country"};
        String hor = " --------------------------------";
        try {
            if (rs == null || !rs.next()) {
                System.out.println("Nothing to see");
                return;
            }

            do {
                System.out.println(hor);
                for (int i = 0; i < 9; i++) {
                    System.out.printf("| %12s: %16s |\n",
                        fields[i], rs.getObject(i + 1).toString());
                }
            } while (rs.next());

            System.out.println(hor);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
