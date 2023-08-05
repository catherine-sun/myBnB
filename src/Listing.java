import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Calendar;

public class Listing extends DBTable {

    public static boolean validateListingId (int listingIdNum) {
        String query = String.format("SELECT * FROM %s WHERE listingId = %d", 
            PostingDB, listingIdNum);
        
        QueryResult res = db.execute(query, null, null);
        try {
            if (res.rs.next()) {
                return true;
            } else {
                System.out.println(listingIdNum + " is not a valid listing ID");
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public static boolean validateListingId (int listingIdNum, String hostSin) {
        String query = String.format("SELECT * FROM %s WHERE listingId = %d AND hostSin = '%s'", 
            PostingDB, listingIdNum, hostSin);
        
        QueryResult res = db.execute(query, null, null);
        try {
            if (res.rs.next()) {
                return true;
            } else {
                System.out.println(listingIdNum + " is not a valid listing ID for SIN " + hostSin);
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
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

        query = String.format("SELECT * FROM %s WHERE sinNumber = '%s'",
            HostDB, sin);

        res = db.execute(query, null, null);

        try {
            if (!res.rs.next()) {
                query = String.format("INSERT INTO %s (%s) VALUES ('%s')",
                    HostDB, "sinNumber", sin);
                db.executeUpdate(query, "Congrats on your first listing!", null);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void setAvailableDateSingle (String sin, String listingId, boolean available)
    {
        Integer listingIdNum = Integer.parseInt(listingId);
        if (!validateListingId(listingIdNum)) return;
        if (!validateListingId(listingIdNum, sin)) return;

        String [] fields = available ? new String[]{"Date to set as available", "Price"} : new String[]{"Date to set as unavailable"};
        String [] inputs = SQLUtils.getInputArgs(fields);
        if (available) {
            String bookingQuery = String.format("SELECT * FROM %s WHERE listingId = %d AND startDate <= '%s' AND endDate > '%s' AND bookingStatus = '%s'",
                BookingDB, listingIdNum, inputs[0], inputs[0], Booking.STATUS_OK);
                
            QueryResult res = db.execute(bookingQuery, null, null);

            try {
                if (res.rs.next()) {
                    System.out.println("The selected listing is already booked for the date " + inputs[0]);
                    return;
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            Double price = Double.parseDouble(inputs[1]);
            String query = String.format("INSERT INTO %s (%s) VALUES (%d, '%s', %f)",
                AvailableDateDB, "listingId, startDate, price", listingIdNum, inputs[0], price);

            db.executeUpdate(query, "Successfully made listing available", "Error making listing available");
        } else {
            String query1 = String.format("DELETE FROM %s WHERE listingId = '%s' AND startDate = '%s'",
                AvailableDateDB, listingIdNum, inputs[0]);
            db.executeUpdate(query1, "Successfully made listing unavailable", "Error making listing unavailable");

        }

    }

    public static void setAvailableDateRange (String sin, String listingId, boolean available) {
        Integer listingIdNum = Integer.parseInt(listingId);
        if (!validateListingId(listingIdNum)) return;
        if (!validateListingId(listingIdNum, sin)) return;
        String [] fields = available ? 
            new String[]{"Start of date range to set as available", "End of date range to set as available", "Price"}
            : new String[]{"Start of date range to set as unavailable", "End of date range to set as unavailable"};
        String[] inputs = SQLUtils.getInputArgs(fields);
        Date startDate = Date.valueOf(inputs[0]);
        Date endDate = Date.valueOf(inputs[1]);
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        if (startCalendar.compareTo(endCalendar) > 0) {
            System.out.println("End date cannot be earlier than the start date");
        } else {
            boolean datesAreAvailable = true;
            while (startCalendar.compareTo(endCalendar) < 1) {
                String dateString = String.format("%4d-%02d-%02d", startCalendar.get(Calendar.YEAR),
                    startCalendar.get(Calendar.MONTH) + 1, startCalendar.get(Calendar.DAY_OF_MONTH));
    
                if (!available) {
                    if (!Listing.listingIsAvailable(listingIdNum, dateString)) {
                        datesAreAvailable = false;
                        System.out.println("The selected listing is not available for the date " + dateString);
                    }
                } else {
                    if (!Listing.listingIsAvailable(listingIdNum, dateString)) {
                        String bookingQuery = String.format("SELECT * FROM %s WHERE listingId = %d AND startDate <= '%s' AND endDate > '%s' AND bookingStatus = '%s'",
                            BookingDB, listingIdNum, dateString, dateString, Booking.STATUS_OK);
                            
                        QueryResult res = db.execute(bookingQuery, null, null);

                        try {
                            if (res.rs.next()) {
                                datesAreAvailable = true;
                                System.out.println("The selected listing is already booked for the date " + dateString);
                            }
                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
    
                startCalendar.roll(Calendar.DATE, true);
                if (startCalendar.get(Calendar.DATE) == 1) {
                    startCalendar.roll(Calendar.MONTH, true);
                    if (startCalendar.get(Calendar.MONTH) == 0)
                        startCalendar.roll(Calendar.YEAR, true);
                }
            }
            if (!datesAreAvailable) return;
            
            startCalendar.setTime(startDate);
            endCalendar.setTime(endDate);

            while (startCalendar.compareTo(endCalendar) < 1) {
                String dateString = String.format("%4d-%02d-%02d", startCalendar.get(Calendar.YEAR),
                    startCalendar.get(Calendar.MONTH) + 1, startCalendar.get(Calendar.DAY_OF_MONTH));

                if (available) {
                    Double price = Double.parseDouble(inputs[2]);
                    String query = String.format("INSERT INTO %s (%s) VALUES (%d, '%s', %f)",
                        AvailableDateDB, "listingId, startDate, price", listingIdNum, dateString, price);

                    db.executeUpdate(query, "Successfully made listing available for " + dateString , "Error making listing available for " + dateString);
                } else {
                    String query1 = String.format("DELETE FROM %s WHERE listingId = '%s' AND startDate = '%s'",
                    AvailableDateDB, listingIdNum, dateString);
                    db.executeUpdate(query1, "Successfully made listing unavailable for " + dateString , "Error making listing available for " + dateString);
                }
                startCalendar.roll(Calendar.DATE, true);
                if (startCalendar.get(Calendar.DATE) == 1) {
                    startCalendar.roll(Calendar.MONTH, true);
                    if (startCalendar.get(Calendar.MONTH) == 0)
                        startCalendar.roll(Calendar.YEAR, true);
                }
            }
        }
    }

    public static void deleteListing(String sin, String listingId) {
        Integer listingIdNum = Integer.parseInt(listingId);
        String query = String.format("DELETE FROM %s WHERE hostSin = '%s' AND listingId = %d",
            PostingDB, sin, listingIdNum);
        
        QueryResult res = db.executeUpdate(query, null, null);
        if (res.rowsChanged >= 1) {
            System.out.println("Successfully deleted listing " + listingIdNum);
        } else {
            System.out.println("No listing was deleted");
        }
    }

    public static void updatePriceRange (String sin, String listingId, String dateStart, String dateEnd, String p) {
        Integer listingIdNum = Integer.parseInt(listingId);
        if (!validateListingId(listingIdNum)) return;
        if (!validateListingId(listingIdNum, sin)) return;
        Date startDate = Date.valueOf(dateStart);
        Date endDate = Date.valueOf(dateEnd);
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        Double price = Double.parseDouble(p);
         if (startCalendar.compareTo(endCalendar) > 0) {
            System.out.println("End date cannot be earlier than the start date");
        } else {
            boolean datesAreAvailable = true;
            while (startCalendar.compareTo(endCalendar) < 1) {
                String dateString = String.format("%4d-%02d-%02d", startCalendar.get(Calendar.YEAR),
                    startCalendar.get(Calendar.MONTH) + 1, startCalendar.get(Calendar.DAY_OF_MONTH));
                    if (!Listing.listingIsAvailable(listingIdNum, dateString)) {
                        datesAreAvailable = false;
                        System.out.println("The selected listing is not available for the date " + dateString);
                    }
                
    
                startCalendar.roll(Calendar.DATE, true);
                if (startCalendar.get(Calendar.DATE) == 1) {
                    startCalendar.roll(Calendar.MONTH, true);
                    if (startCalendar.get(Calendar.MONTH) == 0)
                        startCalendar.roll(Calendar.YEAR, true);
                }
            }
            if (!datesAreAvailable) return;
            
            startCalendar.setTime(startDate);
            endCalendar.setTime(endDate);

            while (startCalendar.compareTo(endCalendar) < 1) {
                String dateString = String.format("%4d-%02d-%02d", startCalendar.get(Calendar.YEAR),
                    startCalendar.get(Calendar.MONTH) + 1, startCalendar.get(Calendar.DAY_OF_MONTH));

 
                String query = String.format("UPDATE %s SET price = %f WHERE listingId = '%s' AND startDate = '%s'",
                    AvailableDateDB, price, listingIdNum, dateString);
                db.executeUpdate(query, "Successfully updated price for " + dateString , "Error updating for " + dateString);
            
                startCalendar.roll(Calendar.DATE, true);
                if (startCalendar.get(Calendar.DATE) == 1) {
                    startCalendar.roll(Calendar.MONTH, true);
                    if (startCalendar.get(Calendar.MONTH) == 0)
                        startCalendar.roll(Calendar.YEAR, true);
                }
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
