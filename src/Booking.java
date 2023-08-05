import java.sql.Date;
import java.util.Calendar;

public class Booking extends DBTable {
    

    public void bookListing (String sin, String listingId, String start, 
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
        while (startCalendar.compareTo(endCalendar) < 1) {
            String dateString = String.format("%d-%d-%d", startCalendar.get(Calendar.YEAR),
                startCalendar.get(Calendar.MONTH) + 1, startCalendar.get(Calendar.DAY_OF_MONTH));

            if ()

            startCalendar.roll(Calendar.DATE, true);
            if (startCalendar.get(Calendar.DATE) == 1)
                startCalendar.roll(Calendar.MONTH, true);
        }

            
    }
}
