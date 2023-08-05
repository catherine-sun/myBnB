import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Calendar;

public class Listing extends DBTable {

    public static void createListing (String sin, String listingType, String latitude, String longitude,
        String streetAddress, String postalCode, String city, String country) {

        double lat = Double.parseDouble(latitude);
        double lon = Double.parseDouble(longitude);

        String query = String.format("INSERT INTO %s (%s) VALUES ('%s', %f, %f, '%s', '%s', '%s', '%s')",
            ListingDB, "listingType, latitude, longitude, streetAddress, postalCode, city, country",
                listingType, lat, lon, streetAddress, postalCode, city, country);

        QueryResult res = db.executeUpdate(query, "Successfully created listing", "Error creating listing");
        Integer listingId = null;
        try {
            // Fetch the listing id that was generated
            ResultSet keys = res.stmt.getGeneratedKeys();
            keys.first();
            listingId = keys.getInt(1);
            System.out.println("The listing's id is " + listingId);
        } catch (SQLException e) {
            System.out.println("Error fetching the listing id: " + e.getMessage());
        }

        query = String.format("INSERT INTO %s (%s) VALUES (%s, '%s')",
                PostingDB, "listingId, hostSin", listingId, sin);

        db.executeUpdate(query, null, null);
    }

    public static void setAvailableDateSingle (String listingId)
    {
        String [] fields = new String[]{"Date to set as available", "Price"};
        String [] inputs = SQLUtils.getInputArgs(fields);
        Integer listingIdNum = Integer.parseInt(listingId);
        Double price = Double.parseDouble(inputs[1]);
        String query = String.format("INSERT INTO %s (%s) VALUES (%d, '%s', %f)",
            AvailableDateDB, "listingId, startDate, price", listingIdNum, inputs[0], price);

        db.executeUpdate(query, "Successfully made listing available", "Error making listing available");

    }

    public static void setAvailableDateRange (String listingId) {
        Integer listingIdNum = Integer.parseInt(listingId);
        String [] fields = new String[]{"Start of date range to set as available", "End of date range to set as available", "Price"};
        String[] inputs = SQLUtils.getInputArgs(fields);
        Date startDate = Date.valueOf(inputs[0]);
        Date endDate = Date.valueOf(inputs[1]);
        Double price = Double.parseDouble(inputs[2]);
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        if (startCalendar.compareTo(endCalendar) > 0) {
            System.out.println("End date cannot be earlier than the start date");
        } else {
            while (startCalendar.compareTo(endCalendar) < 1) {
                String dateString = String.format("%d-%d-%d", startCalendar.get(Calendar.YEAR),
                    startCalendar.get(Calendar.MONTH) + 1, startCalendar.get(Calendar.DAY_OF_MONTH));

                String query = String.format("INSERT INTO %s (%s) VALUES (%d, '%s', %f)",
                    AvailableDateDB, "listingId, startDate, price", listingIdNum, dateString, price);

                db.executeUpdate(query, "Successfully made listing available for " + dateString , "Error making listing available for " + dateString);

                startCalendar.roll(Calendar.DATE, true);
                if (startCalendar.get(Calendar.DATE) == 1)
                    startCalendar.roll(Calendar.MONTH, true);
            }
        }
    }

    public static boolean listingIsAvailable (int listingId, String date) {

        String query = String.format("SELECT * FROM %s WHERE listingId = %d AND startDate = '%s'",
        AvailableDateDB, listingId, date);

        QueryResult res = db.execute(query, null, null);

        return SQLUtils.resultSetIsEmpty(res.rs);
    }

    public static void searchAndFilter() {
        String searchPrompt = "TODO";

        System.out.println(searchPrompt);

    }
}
