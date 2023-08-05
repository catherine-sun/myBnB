public abstract class DBTable {

    public static final String UserDB = "User";
    public static final String HostDB = "Host";
    public static final String RenterDB = "Renter";
    public static final String BookingDB = "Booking";
    public static final String ListingDB = "Listing";
    public static final String PostingDB = "Posting";
    public static final String AvailableDateDB = "AvailableDate";
    public static final String RatingDB = "Rating";

    public static DBConnection db;

    public void setConnection (DBConnection db) {
        this.db = db;
    }
}
