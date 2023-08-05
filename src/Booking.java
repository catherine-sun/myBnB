import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

public class Booking extends DBTable {
    

    public static void bookListing (String sin, String listingId, String start, 
        String end) {
        
        Integer listingIdNum = Integer.parseInt(listingId);
        
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
                String dateString = String.format("%d-%d-%d", startCalendar.get(Calendar.YEAR),
                    startCalendar.get(Calendar.MONTH) + 1, startCalendar.get(Calendar.DAY_OF_MONTH));
 
                     
                String query = String.format("SELECT price FROM %s WHERE listingId = %d AND startDate = '%s'",
                    AvailableDateDB, listingIdNum, dateString);  
                
                QueryResult res = db.execute(query, null, null);

                try {
                    res.rs.next();
                    price += res.rs.getDouble("price");
                } catch (SQLException e) {
                    System.out.println("Error fetching the price for " + dateString);
                }

                query = String.format("DELETE FROM %s WHERE listingId = %d AND startDate = '%s'",
                    BookingDB, listingIdNum, dateString);   

                db.executeUpdate(query, null, null);

                startCalendar.roll(Calendar.DATE, true);
                if (startCalendar.get(Calendar.DATE) == 1)
                    startCalendar.roll(Calendar.MONTH, true);
            }

            String query = String.format("INSERT INTO %s (%s) VALUES (%d, '%s', '%s', '%s', %f)",
                BookingDB, "listingId, renterSin, startDate, endDate, price", listingIdNum, sin, start, end, price);  
            
            db.executeUpdate(query, "Successfully booked listing from " + start + " to " + end, "There was a problem booking the listing"); 
            System.out.println("The total cost of stay is " + price);
        }
  
    }
}
