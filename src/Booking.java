import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Scanner;

public class Booking extends DBTable {
    

    public static void bookListing (String sin, String listingId, String start, 
        String end) {
        
        Integer listingIdNum = Integer.parseInt(listingId);
        
        /* If the renterSin is the host, prevent them from booking */
        String query = String.format("SELECT * FROM %s WHERE hostSin = '%s' AND listingId = %d", 
            PostingDB, sin, listingIdNum);
        
        QueryResult result = db.execute(query, null, null);
        
        boolean isHost = false;
        try {
            if (result.rs.next()) {
                System.out.println("You cannot book a listing of which you are the host");
                isHost = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        if (isHost) return;

        Date startDate = Date.valueOf(start);
        Date endDate = Date.valueOf(end);
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        
        if (startDate.compareTo(endDate) >= 0) {
            System.out.println("The end date must be after the start date");
        }

        Listing listing = new Listing();
        listing.setConnection(db);

        boolean datesAreAvailable = true;
        while (startCalendar.compareTo(endCalendar) < 1) {
            String dateString = String.format("%d-%d-%d", startCalendar.get(Calendar.YEAR),
                startCalendar.get(Calendar.MONTH) + 1, startCalendar.get(Calendar.DAY_OF_MONTH));

            if (!Listing.listingIsAvailable(listingIdNum, dateString)) {
                datesAreAvailable = false;
                System.out.println("The selected listing is not available for the date " + dateString);
            }

            startCalendar.roll(Calendar.DATE, true);
            if (startCalendar.get(Calendar.DATE) == 1)
                startCalendar.roll(Calendar.MONTH, true);
        }

        if (datesAreAvailable) {
            startCalendar.setTime(startDate);
            endCalendar.setTime(endDate);

            double price = 0;
            while (startCalendar.compareTo(endCalendar) < 0) {
                String dateString = String.format("%d-%02d-%02d", startCalendar.get(Calendar.YEAR),
                    startCalendar.get(Calendar.MONTH) + 1, startCalendar.get(Calendar.DAY_OF_MONTH));
 
                     
                query = String.format("SELECT price FROM %s WHERE listingId = %d AND startDate = '%s'",
                    AvailableDateDB, listingIdNum, dateString);  
                
                QueryResult res = db.execute(query, null, null);

                try {
                    res.rs.next();
                    price += res.rs.getDouble("price");
                } catch (SQLException e) {
                    System.out.println("Error fetching the price for " + dateString);
                }

                query = String.format("DELETE FROM %s WHERE listingId = %d AND startDate = '%s'",
                    AvailableDateDB, listingIdNum, dateString);   
                
                db.executeUpdate(query, null, null);

                startCalendar.roll(Calendar.DATE, true);
                if (startCalendar.get(Calendar.DATE) == 1)
                    startCalendar.roll(Calendar.MONTH, true);
            }

            query = String.format("INSERT INTO %s (%s) VALUES (%d, '%s', '%s', '%s', %f, '%s')",
                BookingDB, "listingId, renterSin, startDate, endDate, price, bookingStatus", listingIdNum, sin, start, end, price, "OK");  
            
            db.executeUpdate(query, "Successfully booked listing from " + start + " to " + end, "There was a problem booking the listing"); 
            System.out.println("The total cost of stay is " + price);
        }
  
    }

    public static void rateBooking (String sin, String listingId, String startDate, boolean renter) {
        Integer listingIdNum = Integer.parseInt(listingId);
        String query = String.format ("SELECT * FROM %s WHERE renterSin = '%s' AND listingId=%d AND startDate = '%s'",
            BookingDB, sin, listingIdNum, startDate);
        
        QueryResult res = db.execute(query, null, null);
        try {
            if (res.rs.next()) {
            
                String[] fields, inputs;
                if (renter) {
                    fields  = new String[] {"Subject of rating (Host / Listing)", "Rating"};
                    do {
                        inputs = SQLUtils.getInputArgs(fields);
                    } while (!inputs[0].equals("Host") && inputs[0].equals("Listing"));
                } else {
                    fields  = new String[] {"Rating"};
                    inputs = SQLUtils.getInputArgs(fields);
                    inputs = new String[] { "Renter", inputs[0]};
                }
                
                System.out.println("Enter any comments you have (optional):");
                Scanner input = new Scanner(System.in);
                String comment = input.nextLine().trim();
                comment = comment.replaceAll("'", "''");
                query = String.format("INSERT INTO %s (%s) VALUES (%d, '%s', '%s', %d, '%s', '%s')", RatingDB,
                    "listingId, authorSin, startDate, score, commentBody, object", listingIdNum, sin, startDate, Integer.parseInt(inputs[1]),
                    comment, inputs[0]);
                
                db.executeUpdate(query, "Rating created", "There was a problem rating this booking");
            } else {
                System.out.println("You have had no bookings for this listing for " + startDate);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
