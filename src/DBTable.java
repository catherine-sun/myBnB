public abstract class DBTable {

    public final String UserDB = "User";
    public final String HostDB = "Host";
    public final String RenterDB = "Renter";
    public final String BookingDB = "Booking";
    public final String ListingDB = "Listing";
    public final String PostingDB = "Posting";
    public final String AvailableDateDB = "AvailableDate";

    public DBConnection db;

    public void setConnection (DBConnection db) {
        this.db = db;
    }
}
