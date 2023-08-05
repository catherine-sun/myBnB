import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

public class Reports extends DBTable {

	public static void prompt() {
		Scanner input = new Scanner(System.in);

		final int numBookingsDateRange = 1;
		final int numListingsLocation = 2;
		final int rankHostsByNumListings = 3;
		final int findCommercialHosts = 4;
		final int rankRentersByNumBookings = 5;
		final int rankUsersByCancellations = 6;
		final int popularNounPhrases = 7;

		final int exitReport = 8;

		String reportsPrompt = String.format(
			"******* Run a report *******\n"
			+ "%2d - Total number of bookings for a specified date range\n"
			+ "%2d - Total number of listings in a specified area\n"
			+ "%2d - Rank hosts by number of listings in a specified area\n"
			+ "%2d - Find possible commerical hosts in a specified area\n"
			+ "%2d - Rank renters by number of bookings within a specified time range\n"
			+ "%2d - Rank hosts and renters by number of booking cancellations\n"
			+ "%2d - Find the most popular noun phrase per listing\n"
			+ "%2d - Exit reports",
			numBookingsDateRange, numListingsLocation, rankHostsByNumListings,findCommercialHosts,
			rankRentersByNumBookings, rankUsersByCancellations, popularNounPhrases, exitReport);

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

		String findCommercialHostsPrompt =
			"******* Find Possible Commercial Hosts *******\n"
			+ "1. - Within a Country\n"
			+ "2. - Within a Country and City\n"
			+ "3. - Worldwide (No filter)";

		String popularNounPhrasesPrompt =
			"******* Find Popular Noun Phrase *******";

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

				case findCommercialHosts:
					System.out.println(findCommercialHostsPrompt);
					System.out.print(": ");
					reportChoice = input.nextInt();
					input.nextLine();
					if (reportChoice == 3) {
						findPossibleCommercialHosts(new String[]{}, 0);
					} else if (reportChoice <= 2 && reportChoice >= 1) {
						fields = new String[] {"Country", "City"};
						inp = new String[reportChoice];
						System.arraycopy(fields, 0, inp, 0, reportChoice);
						inp = SQLUtils.getInputArgs(inp);
						findPossibleCommercialHosts(inp, reportChoice);
					} else {
						System.out.println("Invalid choice");
					}
					break;

				case popularNounPhrases:
					System.out.println(popularNounPhrasesPrompt);
					getPopularNounPhrases();
					break;

				case exitReport:
					break;

				default:
					System.out.println("Invalid choice");

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
			int numBookings = 0;
			if (res.rs != null) {
				res.rs.next();
				numBookings = res.rs.getInt("count");
			}
			printReport("There are " + numBookings + " bookings from " + dates[0] + " to " + dates[1]);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void numBookingsDateRangeFilter (String fieldName, String field, String value) {
		String[] fields = new String[] {"Start date", "End date"};
		String[] dates = SQLUtils.getInputArgs(fields);

		String query = String.format("SELECT COUNT(*) as count FROM Booking INNER JOIN Posting ON " +
			"Booking.listingId = Posting.listingId INNER JOIN Listing ON Listing.listingId = Booking.listingId WHERE ((startDate <= '%s' AND endDate > '%s') OR (startDate <= '%s' AND endDate > '%s') " +
				"OR (startDate >= '%s' AND endDate < '%s') OR (endDate >= '%s' AND startDate < '%s')) " +
				" AND bookingStatus = '%s' AND %s = '%s'",
			dates[0], dates[1], dates[0], dates[0], dates[0], dates[1], dates[1], dates[1], Booking.STATUS_OK, field, value);

		QueryResult res = db.execute(query, null, null);

		try {
			int numBookings = 0;
			if (res.rs != null) {
				res.rs.next();
				numBookings = res.rs.getInt("count");
			}
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

		Calendar today = Calendar.getInstance();
		String startOfYear = String.format("%d-01-01", today.get(Calendar.YEAR));
		String todayYear = String.format("%d-%02d-%02d", today.get(Calendar.YEAR), today.get(Calendar.MONTH) + 1, today.get(Calendar.DATE));
		String query = String.format("SELECT fullName, COUNT(*) as count FROM Booking NATURAL JOIN Listing INNER JOIN User ON renterSin = sinNumber WHERE bookingStatus = '%s' AND "
			+ "renterSin IN (SELECT renterSin FROM Booking WHERE bookingStatus = '%s' AND endDate > '%s' AND startDate <= '%s' ", Booking.STATUS_OK, Booking.STATUS_OK,
			startOfYear, todayYear);

		if (city != null)
			query += String.format(" AND city = '%s' GROUP BY renterSin HAVING COUNT(*) >= 2)",
				startOfYear, todayYear, city);
		else
			query += " GROUP BY renterSin HAVING COUNT(*) >= 2)";

		if (startDate != null) {
			query += String.format(" AND ((startDate <= '%s' AND endDate > '%s') OR (startDate <= '%s' AND endDate > '%s') " +
					"OR (startDate >= '%s' AND endDate < '%s') OR (endDate >= '%s' AND startDate < '%s'))",
				startDate, endDate, startDate, startDate, startDate, endDate, endDate, endDate);
		}

		query += " GROUP BY renterSin ORDER BY COUNT(*) DESC";

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
			if (res.rs == null) {
				str += "Nothing to see\n";
			}
			for (int num = 1; num <= 10 && res.rs != null && res.rs.next(); num++) {
				str += num + ". " + res.rs.getString("fullName") + " with " + res.rs.getInt("count") + " cancellation(s)\n";
			}
			printReport(str.trim());

			query = String.format("SELECT fullName, COUNT(*) as count FROM Booking INNER JOIN User ON renterSin = sinNumber" +
				" WHERE bookingStatus = '%s' GROUP BY renterSin ORDER BY COUNT(*) DESC", Booking.STATUS_CANCELLED_RENTER);

			res = db.execute(query, null, null);

			str = "******* Rank Renters By Cancellations *******\n";
			if (res.rs == null) {
				str += "Nothing to see\n";
			}
			for (int num = 1; num <= 10 && res.rs != null && res.rs.next(); num++) {
				str += num + ". " + res.rs.getString("fullName") + " with " + res.rs.getInt("count") + " cancellation(s)\n";
			}
			printReport(str.trim());

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void findPossibleCommercialHosts (String [] filterInputs, int numFilters) {
		String[] filters = new String[] {"Country", "City"};
		String filter = numFilters > 0 ? "WHERE " : "";
		for (int i = 0; i < numFilters; i++) {
			filter += filters[i] + " = '" + filterInputs[i] +"'";
			if (i != numFilters - 1) filter += " AND ";
		}
		String query = String.format("SELECT hostSin, fullName, COUNT(*) as count, totalCount FROM Posting INNER JOIN Listing ON Posting.listingId = Listing.listingId"
			+" INNER JOIN User ON hostSin = sinNumber JOIN (SELECT COUNT(*) AS totalCount FROM Listing INNER JOIN Posting ON Posting.listingId = Listing.listingId %s) AS TotalNumListings"
			+ " %s GROUP BY hostSin, fullName, totalCount HAVING COUNT(*) > totalCount / 10 ORDER BY COUNT(*) DESC", filter, filter);

		QueryResult res = db.execute(query, null, null);

		try {
			if (res.rs != null) {
				String str = "";
				for (int num = 1; res.rs.next(); num++){
					str += num + ". " + res.rs.getString("fullName") + " with " + res.rs.getInt("count") + "/" + res.rs.getInt("totalCount") +" listing(s)\n";
				}
				printReport(str.trim());
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void getPopularNounPhrases() {

		String query = "SELECT commentBody, listingId FROM Rating WHERE commentBody IS NOT NULL";

		QueryResult res = db.execute(query, null, null);

		try {
			Hashtable<String, String> texts = new Hashtable<>();
			ArrayList<NPhrase> nPhrases = new ArrayList<>();

			String id;
			while (res.rs.next()) {
				id = res.rs.getString("listingId");
				if (texts.containsKey(id)) {
					texts.replace(id, res.rs.getString("commentBody") + ". ");
				} else {
					texts.put(id, res.rs.getString("commentBody") + ". ");
				}
			}


			Enumeration<String> k = texts.keys();
			while (k.hasMoreElements()) {
				String key = k.nextElement();

				nPhrases = NpParser.parseNounPhrase(texts.get(key));

				if (nPhrases.isEmpty()) {
					continue;
				}
				nPhrases.sort(null);
				System.out.printf("\n %-10s | %-40s\n", "Frequency", "Popular noun phrase from listing " + key);
				System.out.println("-".repeat(48));
				for (NPhrase np : nPhrases) {
					System.out.printf(" %-10d |  %-40s\n", np.getCount(), np.getNPhrase());
				}
				System.out.println();
			}


		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}
}
