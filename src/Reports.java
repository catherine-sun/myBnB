import java.sql.SQLException;
import java.util.Scanner;

public class Reports extends DBTable {

	public static void prompt() {
		Scanner input = new Scanner(System.in);

		final int numBookingsDateRange = 1;
		final int numListingsLocation = 2;
		final int exitReport = 3;

		String reportsPrompt = String.format(
			"******* Run a report *******\n"
			+ "%2d - Total number of bookings for a specified date range\n"
			+ "%2d - Total number of listings for a specified date range\n"
			+ "%2d - Exit reports",
			numBookingsDateRange, numListingsLocation, exitReport);

		String numBookingsDateRangePrompt = 
			"******* Find Bookings *******\n"
			+ "1. - By City\n"
			+ "2. - By Postal Code\n"
			+ "3. - Show All (No filter)";

		String numListingsLocationPrompt = 
			"******* Find Listings *******\n"
			+ "1. - By Country\n"
			+ "2. - By Country and City\n"
			+ "3. - By Country, City and Postal Code\n"
			+ "4. - Show All (No filter)";

		String[] fields, inp;
		int choice = numBookingsDateRange, reportChoice;
		while (choice != exitReport) {

			System.out.println(reportsPrompt);
            System.out.print(": ");
            choice = input.nextInt();
            input.nextLine();

			switch (choice) {
				case numBookingsDateRange:
					System.out.println(numBookingsDateRangePrompt);
					System.out.print(": ");
					reportChoice = input.nextInt();
					input.nextLine();
					if (reportChoice == 3) {
						numBookingsDateRangeAll();
					} else if (reportChoice == 1) {
						fields = new String[] {"City"};
						inp = SQLUtils.getInputArgs(fields);
						numBookingsDateRangeFilter("City", "City", inp[0]);
					} else if (reportChoice == 2) {
						fields = new String[] {"Postal Code"};
						inp = SQLUtils.getInputArgs(fields);
						numBookingsDateRangeFilter("Postal Code", "postalCode", inp[0]);
					} else {
						System.out.println("Invalid choice");
					}
					break;

				case numListingsLocation:
					System.out.println(numListingsLocationPrompt);
					System.out.print(": ");	
					reportChoice = input.nextInt();
					input.nextLine();
					if (reportChoice == 4) {
						numListingsLocation(new String[]{}, 0);
					} else if (reportChoice <= 3 && reportChoice >= 1) {
						fields = new String[] {"Country", "City", "Postal Code"};
						inp = new String[reportChoice];
						System.arraycopy(fields, 0, inp, 0, reportChoice);
						inp = SQLUtils.getInputArgs(inp);
						numListingsLocation(inp, reportChoice);
					} else {
						System.out.println("Invalid choice");
					}
			}
		}
	}

	public static void printReport(String report) {
		System.out.println(report);
	}

	public static void numBookingsDateRangeAll() {
		String[] fields = new String[] {"Start date", "End date"};
		String[] dates = SQLUtils.getInputArgs(fields);

		String query = String.format("SELECT COUNT(*) as count FROM Booking INNER JOIN Posting ON " +
			"Booking.listingId = Posting.listingId WHERE ((startDate <= '%s' AND endDate > '%s') OR (startDate <= '%s' AND endDate > '%s') " +
				"OR (startDate >= '%s' AND endDate < '%s') OR (endDate >= '%s' AND startDate < '%s')) " +
				" AND bookingStatus = '%s'",
			dates[0], dates[1], dates[0], dates[0], dates[0], dates[1], dates[1], dates[1], Booking.STATUS_OK);

		QueryResult res = db.execute(query, null, null);

		try {
			res.rs.next();
			int numBookings = res.rs.getInt("count");
			printReport("There are " + numBookings + " bookings from " + dates[0] + " to " + dates[1]);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void numBookingsDateRangeFilter (String fieldName, String field, String value) {
		String[] fields = new String[] {"Start date", "End date"};
		String[] dates = SQLUtils.getInputArgs(fields);

		String query = String.format("SELECT COUNT(*) as count FROM Booking INNER JOIN Posting ON " +
			"Booking.listingId = Posting.listingId WHERE ((startDate <= '%s' AND endDate > '%s') OR (startDate <= '%s' AND endDate > '%s') " +
				"OR (startDate >= '%s' AND endDate < '%s') OR (endDate >= '%s' AND startDate < '%s')) " +
				" AND bookingStatus = '%s' AND %s = '%s'",
			dates[0], dates[1], dates[0], dates[0], dates[0], dates[1], dates[1], dates[1], Booking.STATUS_OK, field, value);

		QueryResult res = db.execute(query, null, null);

		try {
			res.rs.next();
			int numBookings = res.rs.getInt("count");
			printReport("There are " + numBookings + " bookings from " + dates[0] + " to " + dates[1] + " with " + fieldName + " = " + value);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void numListingsLocation (String [] loc, int numFilters) {
		String [] possibleFilters = new String[] {"Country", "City", "PostalCode"};

		String query = "SELECT COUNT(*) as count FROM Listing INNER JOIN Posting ON " +
			"Listing.listingId = Posting.listingId WHERE ";

		for (int i = 0; i < numFilters; i++){
			query += possibleFilters[i] + " = '" + loc[i] + "'";
			if (i < numFilters - 1) query += " AND ";
		}

		QueryResult res = db.execute(query, null, null);

		try {
			res.rs.next();
			int numBookings = res.rs.getInt("count");
			printReport("There are " + numBookings + " listings in the selected area");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
