import java.sql.SQLException;
import java.util.Calendar;
import java.util.Scanner;

public class Reports extends DBTable {

	public static void prompt() {
		Scanner input = new Scanner(System.in);

		final int numBookingsDateRange = 1;
		final int numListingsLocation = 2;
		final int rankHostsByNumListings = 3;
		final int rankRentersByNumBookings = 4;
		final int rankUsersByCancellations = 5;

		final int exitReport = 6;

		String reportsPrompt = String.format(
			"******* Run a report *******\n"
			+ "%2d - Total number of bookings for a specified date range\n"
			+ "%2d - Total number of listings in a specified area\n"
			+ "%2d - Rank hosts by number of listings in a specified area\n"
			+ "%2d - Rank renters by number of bookings within a specified time range\n"
			+ "%2d - Rank hosts and renters by number of booking cancellations\n"
			+ "%2d - Exit reports",
			numBookingsDateRange, numListingsLocation, rankHostsByNumListings, rankRentersByNumBookings, rankUsersByCancellations,
			exitReport);

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

		String rankHostsPrompt = 
			"******* Rank Hosts *******\n"
			+ "1. - By Country\n"
			+ "2. - By Country and City\n"
			+ "3. - Show All (No filter)";

		String rankRentersPrompt = 
			"******* Rank Renters *******\n"
			+ "1. - In a time period\n"
			+ "2. - In a time period per city\n"
			+ "3. - Show All (No filter)";

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
					break;
				
				case rankHostsByNumListings:
					System.out.println(rankHostsPrompt);
					System.out.print(": ");	
					reportChoice = input.nextInt();
					input.nextLine();
					if (reportChoice == 3) {
						rankHostsByListing(new String[]{}, 0);
					} else if (reportChoice <= 2 && reportChoice >= 1) {
						fields = new String[] {"Country", "City"};
						inp = new String[reportChoice];
						System.arraycopy(fields, 0, inp, 0, reportChoice);
						inp = SQLUtils.getInputArgs(inp);
						rankHostsByListing(inp, reportChoice);
					} else {
						System.out.println("Invalid choice");
					}
					break;

				case rankRentersByNumBookings:
					System.out.println(rankRentersPrompt);
					System.out.print(": ");	
					reportChoice = input.nextInt();
					input.nextLine();
					if (reportChoice == 3) {
						rankRentersByBooking(null, null, null);
					} else if (reportChoice <= 2 && reportChoice >= 1) {
						fields = new String[] {"Start of date range", "End of date range", "City"};
						inp = new String[reportChoice + 1];
						System.arraycopy(fields, 0, inp, 0, reportChoice + 1);
						inp = SQLUtils.getInputArgs(inp);
						rankRentersByBooking(inp[0], inp[1], reportChoice == 1 ? null : inp[2]);
					} else {
						System.out.println("Invalid choice");
					}
					break;

				case rankUsersByCancellations:
					rankUsersByCancellations();
					break;
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
			"Listing.listingId = Posting.listingId";

		for (int i = 0; i < numFilters; i++){
			if (i == 0) query += " WHERE ";
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

	public static void rankHostsByListing (String [] loc, int numFilters) {
		String [] possibleFilters = new String[] {"Country", "City"};

		String query = "SELECT fullName, hostSin, COUNT(*) as count FROM Posting NATURAL JOIN Listing INNER JOIN User ON hostSin = sinNumber";

		for (int i = 0; i < numFilters; i++){
			if (i == 0) query += " WHERE ";
			query += possibleFilters[i] + " = '" + loc[i] + "'";
			if (i < numFilters - 1) query += " AND ";
		}

		query += " GROUP BY hostSin ORDER BY COUNT(*) DESC";

		QueryResult res = db.execute(query, null, null);

		try {
			int num = 1;
			String str = "";
			while (res.rs.next() && num <= 10) {
				str += num + ". " + res.rs.getString("fullName") + " with " + res.rs.getInt("count") + " listing(s)\n";
				num++;
			}
			printReport(str.trim());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void rankRentersByBooking (String startDate, String endDate, String city) {

		String query = String.format("SELECT fullName, COUNT(*) as count FROM Booking NATURAL JOIN Listing INNER JOIN User ON renterSin = sinNumber WHERE bookingStatus = '%s'", Booking.STATUS_OK);
		
		if (startDate != null) {
			query += String.format(" AND ((startDate <= '%s' AND endDate > '%s') OR (startDate <= '%s' AND endDate > '%s') " +
					"OR (startDate >= '%s' AND endDate < '%s') OR (endDate >= '%s' AND startDate < '%s'))",
				startDate, endDate, startDate, startDate, startDate, endDate, endDate, endDate);
		}

		if (city != null) {
			Calendar today = Calendar.getInstance();
			String startOfYear = String.format("%d-01-01", today.get(Calendar.YEAR));
			String todayYear = String.format("%d-%02d-%02d", today.get(Calendar.YEAR), today.get(Calendar.MONTH) + 1, today.get(Calendar.DATE));
			query += String.format("AND endDate >= '%s' AND startDate <= '%s' AND city = '%s' GROUP BY renterSin HAVING COUNT(*) >= 2 ORDER BY COUNT(*) DESC", 
				startOfYear, todayYear, city);
		} else {
			query += " GROUP BY renterSin ORDER BY COUNT(*) DESC";
		}

		QueryResult res = db.execute(query, null, null);

		try {
			int num = 1;
			String str = "";
			while (res.rs.next() && num <= 10) {
				str += num + ". " + res.rs.getString("fullName") + " with " + res.rs.getInt("count") + " booking(s)\n";
				num++;
			}
			printReport(str.trim());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void rankUsersByCancellations () {
		/* Rank hosts */
		
		try {
			String query = String.format("SELECT fullName, COUNT(*) as count FROM Booking NATURAL JOIN Posting INNER JOIN User on hostSin = sinNumber" +
				" WHERE bookingStatus = '%s' GROUP BY hostSin ORDER BY COUNT(*) DESC", Booking.STATUS_CANCELLED_HOST);

			QueryResult res = db.execute(query, null, null);

			String str = "******* Rank Hosts By Cancellations *******\n";
			for (int num = 1; num <= 10 && res.rs.next(); num++) {
				str += num + ". " + res.rs.getString("fullName") + " with " + res.rs.getInt("count") + " cancellation(s)\n";
			}
			printReport(str.trim());
			
			query = String.format("SELECT fullName, COUNT(*) as count FROM Booking INNER JOIN User ON renterSin = sinNumber" +
				" WHERE bookingStatus = '%s' GROUP BY renterSin ORDER BY COUNT(*) DESC", Booking.STATUS_CANCELLED_RENTER);

			res = db.execute(query, null, null);

			str = "******* Rank Renters By Cancellations *******\n";

			for (int num = 1; num <= 10 && res.rs.next(); num++) {
				str += num + ". " + res.rs.getString("fullName") + " with " + res.rs.getInt("count") + " cancellation(s)\n";
			}
			printReport(str.trim());
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
