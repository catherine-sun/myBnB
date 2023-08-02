import java.sql.ResultSet;
import java.sql.SQLException;

public class Listing extends DBTable {

    public void createListing (String sin, String listingType, String latitude, String longitude,
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

        // if the sin number isnt a host, create a new entry in the host table
        User user = new User();
        user.setConnection(db);
        if (!user.isHost(sin)) {
            user.createHost(sin);
            System.out.println("Congrats on your first listing!");
        }
    }
    
}