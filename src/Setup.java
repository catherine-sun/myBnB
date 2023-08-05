import java.util.Calendar;
import java.util.Scanner;
import java.sql.Date;

public class Setup {

    /* To run on an empty database */
    public static void main (String[] args) {
        Scanner input = new Scanner(System.in);
        DBConnection db = new DBConnection();
        System.out.println("SETUP DATA");
        String dbName, usr, pwd;
        do {
            System.out.print("Enter database: ");
            dbName = input.nextLine().trim();

            System.out.print("Enter user [root]: ");
            usr = input.nextLine().trim();
            if (usr.length() == 0) { usr = "root"; }

            do {
                System.out.print("Enter password: ");
                pwd = input.nextLine().trim();

                if (pwd.length() == 0) {
                    System.out.println("Password cannot be empty.");
                }
            } while (pwd.length() == 0);

        } while (!db.connect(dbName, usr, pwd));

        DBTable.db = db;

        createData();
        updateData();
        input.close();
    }

    public static void createData (){
        DBTable.dev = true;

        User.createUser("101010101", "John Smith", "Professor", "44 Mooncrest Ave", "1983-09-23");
        User.createUser("122222221", "Catherine Sun", "Software Engineer", "25 Arbutus Crescent", "2003-07-09");
        User.createUser("133333331", "Christine Zhao", "Junior Full Stack Developer", "21 Waymount Avenue", "2003-05-15");
        User.createUser("144444441", "Patricia Smith", "Unemployed", "56 Second St", "1993-04-25");
        User.createUser("155555551", "Uncle Tetsu", "Chef", "213 Banging Danging House", "2000-01-01");
        User.createUser("166666661", "Eric Corlett", "Professor", "1185 Military Trail", "1979-11-14");
        User.createUser("177777771", "Nick Koudas", "Professor", "1185 Military Trail", "1983-09-23");
        User.createUser("188888881", "Bianca Schroeder", "Professor", "1185 Military Trail", "1983-09-23");
        User.createUser("266666662", "Tony Stark", "Super Hero", "1 Main Street", "1980-03-16");
        User.createUser("277777772", "Clark Kent", "Super Hero", "2 Main Street", "1985-10-03");
        User.createUser("288888882", "Bruce Wayne", "Super Hero", "3 Main Street2", "1978-02-22");
        User.createUser("630775846", "Ty Meza", "Advertising", "251 Tall Street", "1999-09-30");
        User.createUser("522340330", "Kayson Morton", "Athlete", "97 Wide Hill", "2007-02-15");
        User.createUser("191702269", "Paula Stevens", "Astronaut", "4 South Drive", "2008-05-26");
        User.createUser("460173513", "Ty Horn", "Banker", "851 Tall Hill", "2004-10-21");
        User.createUser("790046557", "Zariah Camacho", "Astronaut", "1 North Road", "2000-10-31");
        User.createUser("983625551", "Zuri Dyer", "Athlete", "315 St Margarets Hill", "1971-04-31");
        User.createUser("625295664", "Yara Sutton", "Architect", "5 St Margarets Drive", "1948-01-30");
        User.createUser("881671015", "Yara Crane", "Astronaut", "21 Long Drive", "1982-06-27");
        User.createUser("889977038", "Paula Crosby", "Anthropologist", "5 Tall Street", "1994-02-30");
        User.createUser("272537565", "Zuri Dougherty", "Athlete", "81 North Road", "2005-09-03");
        User.createUser("974193888", "Paula Camacho", "Banker", "356 East Hill", "1952-03-29");
        User.createUser("576603711", "Ty Stevens", "Banker", "58 St Margarets Drive", "2001-10-30");
        User.createUser("821169232", "Hector Morton", "Artist", "1 East Street", "1979-02-31");
        User.createUser("444914259", "Ty Sutton", "Advertising", "81 Long Street", "2003-10-28");
        User.createUser("320949279", "Miracle Dyer", "Architect", "424 Tall Road", "1951-06-10");
        User.createUser("599394011", "Hector Dougherty", "Artist", "54 Ladson Road", "2005-10-31");
        User.createUser("513985129", "Hector Crane", "Architect", "964 West Hill", "1934-11-12");
        User.createUser("289224235", "Ty Dougherty", "Banker", "8 Ashland Road", "2006-05-12");
        User.createUser("472829926", "Hector Stevens", "Artist", "5 Short Hill", "2003-10-30");
        User.createUser("484055042", "Miracle Camacho", "Athlete", "4 St Margarets Drive", "2001-12-11");
        User.createUser("181544002", "Ty Morton", "Astronaut", "946 St Margarets Hill", "2000-8-20");
        User.createUser("877783005", "Paula Crosby", "Banker", "2 Tall Road", "1976-01-18");
        User.createUser("987692755", "Zuri Sutton", "Astronaut", "92 Ashland Road", "1945-03-02");
        User.createUser("323577222", "Zariah Crane", "Banker", "784 West Street", "1962-7-14");
        User.createUser("990443434", "Miracle Olsen", "Actor", "7 Narrow Drive", "2009-07-21");
        User.createUser("676312612", "Ty Meza", "Actor", "759 Penn Road", "2007-12-31");
        User.createUser("925758730", "Hector Crosby", "Acrobat", "289 Ladson Hill", "1965-12-16");
        User.createUser("887464519", "Ty Camacho", "Actor", "645 Short Street", "2005-05-30");
        User.createUser("878119760", "Yara Olsen", "Architect", "485 Short Hill", "2006-04-31");
        User.createUser("137056904", "Zariah Crosby", "Banker", "53 East Street", "1935-11-31");
        User.createUser("410418744", "Ty Dyer", "Architect", "5 Narrow Road", "1965-04-31");
        User.createUser("852795814", "Hector Crane", "Architect", "945 West Drive", "1941-12-08");
        User.createUser("511886807", "Ty Dougherty", "Acrobat", "75 Ladson Drive", "1977-02-26");
        User.createUser("738273869", "Zariah Sutton", "Advertising", "64 Cherry Hill", "2000-10-03");
        User.createUser("957719874", "Zuri Stevens", "Athlete", "44 St Margarets Road", "1954-10-01");
        User.createUser("258840678", "Ty Morton", "Anthropologist", "794 Vermont Street", "1942-07-10");
        User.createUser("831598436", "Kayson Sutton", "Artist", "4 Princess Drive", "1996-12-12");
        User.createUser("619150233", "Zuri Olsen", "Acrobat", "5 Ashland Hill", "2003-04-06");
        User.createUser("287997303", "Miracle Sweeney", "Advertising", "664 East Hill", "1978-10-15");
        User.createUser("029969970", "Miracle Stevens", "Athlete", "7 North Street", "2001-10-08");


        User.createRenter("122222221");
        User.createRenter("133333331");
        User.createRenter("155555551");
        User.createRenter("166666661");
        User.createRenter("177777771");
        User.createRenter("188888881");

        User.createHost("101010101");
        User.createHost("122222221");
        User.createHost("133333331");
        User.createHost("144444441");

        Listing.createListing("101010101", "House", "34.3456", "45.2", "44 Mooncrest Drive", "M5D2N7", "Toronto", "Canada"); // 1
        Listing.createListing("101010101", "House", "35.3456", "46.2", "67 Godstone Crescent", "M5D2N7", "Toronto", "Canada"); // 2
        Listing.createListing("101010101", "Townhouse", "67.3456", "-25.2", "1085 Heaven Road", "M5S9N7", "Toronto", "Canada"); // 3
        Listing.createListing("122222221", "House", "24.3456", "45.2", "25 Arbutus Crescent", "M2K5M3", "Toronto", "Canada"); // 4
        Listing.createListing("122222221", "Basement", "24.3456", "45.2", "25 Arbutus Crescent", "M2K5M3", "Toronto", "Canada"); // 5
        Listing.createListing("133333331", "House", "22.3456", "40.2", "21 Waymount Avenue", "L4S2G5", "Richmond Hill", "Canada"); // 6
        Listing.createListing("144444441", "Apartment", "14.3456", "80.342", "6 Main Street", "M0D8R1", "Hamilton", "Canada"); // 7
        Listing.createListing("144444441", "Basement", "64.3456", "65.2", "11 Bob Corner", "M9K5F3", "Berlin", "Germany"); // 8
        Listing.createListing("144444441", "Bedroom (Double bed)", "64.3456", "65.2", "11 Bob Corner", "M9K5F3", "Berlin", "Germany"); // 9
        Listing.createListing("144444441", "Bedroom (Hammock)", "64.3456", "65.2", "11 Bob Corner", "M9K5F3", "Berlin", "Germany"); // 10
        Listing.createListing("144444441", "Bedroom", "64.3456", "65.2", "11 Bob Corner", "M9K5F3", "Berlin", "Germany"); // 11
        Listing.createListing("144444441", "Bedroom", "64.3456", "65.2", "11 Bob Corner", "M9K5F3", "Berlin", "Germany"); // 12
        Listing.createListing("576603711", "Bedroom (Hammock)", "-8.49", "23.0257", "148 Short Street", "5F4NL3", "Berlin", "Canada");
        Listing.createListing("155555551", "Townhouse", "-72.327", "11.218", "2 South Road", "AGGH3Q", "Toronto", "Germany");
        Listing.createListing("599394011", "Bedroom (Hammock)", "-2.687", "6.38", "99 Long Drive", "68VNQS", "Berlin", "Germany");
        Listing.createListing("511886807", "Ranch", "-9.154", "-6.015", "85 St Margarets Hill", "8QCFHN", "Toronto", "Germany");
        Listing.createListing("460173513", "Cottage", "1.803", "82.72", "19 Princess Drive", "L53GFG", "Toronto", "Germany");
        Listing.createListing("144444441", "Apartment", "-54.08", "0.78", "389 Ladson Street", "V8XASC", "Mississauga", "Germany");
        Listing.createListing("122222221", "Ranch", "6.745", "-47.466", "25 Tall Hill", "N7UWV4", "Scarborough", "Germany");
        Listing.createListing("323577222", "Split-level", "-22.37", "-34.0419", "59 Long Drive", "24YXEA", "Mississauga", "Canada");
        Listing.createListing("460173513", "Hut", "7.31", "-73.3307", "7 St Margarets Hill", "JDD6WS", "Berlin", "Germany");
        Listing.createListing("410418744", "Bedroom (Double bed)", "33.58", "-79.25", "48 Wide Hill", "T25QDZ", "Mississauga", "Canada");
        Listing.createListing("630775846", "Bedroom", "2.695", "9.24", "95 Vermont Street", "T5B6PZ", "Toronto", "Germany");
        Listing.createListing("122222221", "Basement", "7.25", "7.28", "265 Tall Drive", "BPVNW5", "Toronto", "Germany");
        Listing.createListing("576603711", "House", "-9.21", "5.8423", "64 Ladson Street", "GPEB3T", "Mississauga", "Canada");
        Listing.createListing("266666662", "Split-level", "81.15", "92.391", "3 North Drive", "9GX16M", "Toronto", "Canada");
        Listing.createListing("191702269", "Hut", "-3.3858", "6.654", "18 North Drive", "TXKANP", "Berlin", "Germany");
        Listing.createListing("878119760", "Apartment", "5.63", "-60.002", "72 South Hill", "3FT1G2", "Berlin", "Germany");
        Listing.createListing("101010101", "Ranch", "25.78", "-30.818", "965 West Drive", "C7XPYE", "Scarborough", "Canada");
        Listing.createListing("410418744", "Ranch", "5.199", "95.66", "91 Wide Street", "HYBO5Y", "Scarborough", "Germany");
        Listing.createListing("166666661", "Split-level", "-24.606", "-3.896", "391 St Margarets Street", "TNYNCK", "Berlin", "Germany");
        Listing.createListing("522340330", "Bedroom", "-59.663", "5.15", "4 Tall Hill", "GE3AW4", "Mississauga", "Germany");
        Listing.createListing("181544002", "Apartment", "-1.981", "-91.01", "952 West Street", "ZP96X8", "Mississauga", "Canada");
        Listing.createListing("266666662", "Townhouse", "-20.096", "-7.6999", "57 North Street", "A77WB6", "Scarborough", "Germany");
        Listing.createListing("925758730", "Cottage", "-8.29", "41.39", "2 Cherry Drive", "4W8P84", "Scarborough", "Germany");
        Listing.createListing("852795814", "Apartment", "-32.5615", "6.867", "654 South Road", "BLRJVT", "Scarborough", "Germany");
        Listing.createListing("877783005", "Split-level", "84.866", "22.53", "2 Tall Drive", "SZC1DL", "Mississauga", "Germany");
        Listing.createListing("144444441", "House", "-1.73", "50.973", "52 St Margarets Hill", "4UMY43", "Berlin", "Germany");
        Listing.createListing("887464519", "Apartment", "0.5125", "-41.68", "746 West Street", "T3XKUC", "Mississauga", "Germany");
        Listing.createListing("188888881", "House", "4.3874", "-63.2525", "986 East Road", "7327WW", "Berlin", "Canada");
        Listing.createListing("029969970", "Bedroom (Double bed)", "61.6642", "-71.3318", "2 Ashland Hill", "JHETKH", "Mississauga", "Canada");
        Listing.createListing("444914259", "Bedroom (Double bed)", "4.98", "34.6958", "318 Wide Road", "3H6C58", "Berlin", "Canada");
        Listing.createListing("790046557", "Cottage", "66.646", "83.476", "67 North Drive", "SPGRA3", "Berlin", "Germany");
        Listing.createListing("831598436", "Bedroom", "84.17", "37.614", "131 St Margarets Drive", "FK9PPZ", "Toronto", "Canada");
        Listing.createListing("460173513", "Apartment", "17.55", "1.4088", "99 Narrow Hill", "TI2GCF", "Mississauga", "Canada");
        Listing.createListing("320949279", "Cottage", "84.23", "0.6134", "5 South Street", "1IZCOM", "Mississauga", "Germany");
        Listing.createListing("472829926", "Cottage", "-3.77", "4.25", "414 Tall Street", "MXI78F", "Mississauga", "Canada");
        Listing.createListing("983625551", "Bedroom", "34.6871", "9.19", "782 South Street", "9QHI8N", "Scarborough", "Germany");
        Listing.createListing("137056904", "House", "-0.24", "97.958", "69 Ladson Drive", "HO13PT", "Mississauga", "Germany");
        Listing.createListing("974193888", "Ranch", "-4.3821", "1.562", "42 Ladson Road", "F2M2NE", "Mississauga", "Germany");
        Listing.createListing("133333331", "Basement", "6.9298", "-7.922", "498 Cherry Road", "EID2H8", "Mississauga", "Germany");
        Listing.createListing("987692755", "Basement", "-1.299", "-92.29", "1 Short Hill", "H3PSVC", "Toronto", "Canada");
        Listing.createListing("831598436", "Ranch", "-73.2339", "8.36", "482 Wide Street", "AQ111T", "Toronto", "Canada");
        Listing.createListing("133333331", "Bedroom (Double bed)", "71.1004", "21.776", "38 Cherry Road", "Z5ISCP", "Scarborough", "Canada");
        Listing.createListing("957719874", "Ranch", "-9.7009", "4.36", "59 South Street", "HP66PS", "Berlin", "Germany");
        Listing.createListing("323577222", "Hut", "-2.651", "5.866", "97 East Street", "3LLCYR", "Toronto", "Canada");
        Listing.createListing("987692755", "Hut", "-4.165", "-7.323", "539 St Margarets Hill", "H5HS83", "Scarborough", "Germany");
        Listing.createListing("272537565", "Bedroom (Double bed)", "7.71", "30.28", "5 Tall Hill", "QHR9BD", "Berlin", "Germany");
        Listing.createListing("101010101", "Hut", "3.639", "-5.94", "198 East Street", "RYWYVX", "Mississauga", "Canada");
        Listing.createListing("676312612", "Bedroom (Double bed)", "32.3636", "4.11", "5 Ashland Drive", "EE3GGE", "Berlin", "Germany");
        Listing.createListing("410418744", "Cottage", "-87.267", "75.58", "12 Wide Hill", "JOM1JF", "Mississauga", "Canada");
        Listing.createListing("831598436", "Bedroom", "54.06", "6.56", "9 Narrow Road", "PNPJ24", "Scarborough", "Germany");
        Listing.createListing("990443434", "Hut", "38.053", "2.3364", "236 South Drive", "MPTBG9", "Berlin", "Germany");
        Listing.createListing("277777772", "Hut", "87.5585", "-89.886", "2 East Street", "B6V9WE", "Scarborough", "Germany");
        Listing.createListing("957719874", "Ranch", "-96.72", "72.54", "919 North Road", "SM7DOZ", "Berlin", "Germany");
        Listing.createListing("576603711", "House", "51.245", "-17.9258", "6 Cherry Road", "FYHCUL", "Scarborough", "Germany");
        Listing.createListing("821169232", "Bedroom (Hammock)", "-68.034", "17.213", "189 Penn Drive", "R9KT3R", "Toronto", "Canada");
        Listing.createListing("878119760", "Bedroom (Double bed)", "18.9262", "-3.974", "86 Ladson Street", "UMP7II", "Toronto", "Canada");
        Listing.createListing("990443434", "House", "48.2576", "-6.7029", "17 East Road", "4MZKKE", "Mississauga", "Canada");
        Listing.createListing("821169232", "Split-level", "43.2763", "-0.1536", "146 Tall Drive", "TWZ5IL", "Toronto", "Germany");
        Listing.createListing("181544002", "House", "5.619", "-7.426", "6 Penn Road", "B1HAO8", "Scarborough", "Germany");
        Listing.createListing("576603711", "Bedroom", "-1.355", "-5.776", "6 East Drive", "XGJRXF", "Toronto", "Canada");
        Listing.createListing("101010101", "Apartment", "-2.889", "41.92", "3 Narrow Road", "OUNT8P", "Mississauga", "Germany");
        Listing.createListing("137056904", "House", "2.59", "-91.5746", "538 Short Drive", "CTU93R", "Mississauga", "Germany");
        Listing.createListing("101010101", "Bedroom (Double bed)", "1.3984", "-39.99", "4 Long Road", "YS7Y67", "Mississauga", "Canada");
        Listing.createListing("277777772", "Bedroom", "5.263", "-27.1637", "328 St Margarets Drive", "I4SK5I", "Berlin", "Germany");
        Listing.createListing("188888881", "Bedroom (Double bed)", "49.422", "-49.003", "13 Tall Hill", "EDQKVJ", "Mississauga", "Germany");
        Listing.createListing("887464519", "Bedroom (Hammock)", "4.06", "4.654", "896 East Hill", "3KFMR7", "Toronto", "Germany");
        Listing.createListing("887464519", "House", "-15.74", "93.15", "21 Long Hill", "B5DFE3", "Scarborough", "Germany");
        Listing.createListing("619150233", "Ranch", "22.428", "3.1509", "42 Short Drive", "UNZ9EV", "Scarborough", "Canada");
        Listing.createListing("188888881", "Apartment", "8.46", "-4.6473", "48 Vermont Hill", "3TNC2F", "Berlin", "Canada");
        Listing.createListing("511886807", "Ranch", "-4.989", "-4.1069", "8 St Margarets Drive", "QST1JX", "Mississauga", "Canada");
        Listing.createListing("831598436", "Hut", "-3.753", "-1.2186", "3 Ashland Hill", "XUJELH", "Scarborough", "Germany");
        Listing.createListing("738273869", "Bedroom (Hammock)", "-9.8455", "0.975", "231 Long Hill", "26BUHE", "Berlin", "Germany");
        Listing.createListing("522340330", "Hut", "67.8115", "-76.5193", "6 West Hill", "4TONVB", "Toronto", "Canada");
        Listing.createListing("877783005", "House", "2.373", "9.078", "9 Short Road", "G735DQ", "Mississauga", "Canada");
        Listing.createListing("676312612", "Hut", "-0.298", "-85.76", "53 Ladson Street", "FOP2Z4", "Toronto", "Germany");
        Listing.createListing("576603711", "Hut", "-68.17", "-74.4264", "28 Wide Drive", "EUWSHS", "Scarborough", "Canada");
        Listing.createListing("191702269", "Bungalow", "5.82", "-8.673", "4 Wide Street", "7ZXSK8", "Scarborough", "Germany");
        Listing.createListing("133333331", "House", "18.6033", "89.99", "8 St Margarets Hill", "UWVYIW", "Scarborough", "Canada");
        Listing.createListing("887464519", "Split-level", "-25.391", "-43.99", "43 Penn Street", "K14IVM", "Berlin", "Germany");
        Listing.createListing("630775846", "Townhouse", "-8.6714", "-35.91", "6 Ladson Road", "R8PDYX", "Toronto", "Germany");
        Listing.createListing("877783005", "Bedroom", "-67.477", "9.01", "7 Princess Street", "Z2VRB5", "Berlin", "Canada");
        Listing.createListing("676312612", "Townhouse", "-65.94", "73.068", "2 Princess Street", "I35EVA", "Berlin", "Germany");
        Listing.createListing("974193888", "Apartment", "2.45", "-36.1696", "1 East Hill", "QYXNWS", "Scarborough", "Germany");
        Listing.createListing("877783005", "Cottage", "-72.3704", "-84.18", "3 East Drive", "HVS8AN", "Mississauga", "Germany");
        Listing.createListing("790046557", "Hut", "-4.806", "-86.57", "6 Vermont Road", "H5M883", "Scarborough", "Germany");
        Listing.createListing("277777772", "Ranch", "-48.7716", "9.52", "9 North Road", "SWHF5U", "Scarborough", "Germany");
        Listing.createListing("983625551", "Bedroom (Hammock)", "5.006", "88.037", "58 Ladson Drive", "CG1T67", "Berlin", "Germany");
        Listing.createListing("511886807", "Bedroom (Hammock)", "61.592", "16.66", "569 Long Drive", "DS5EHW", "Scarborough", "Germany");

        String start = "2022-01-01";
        String end = "2026-09-01";

        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        startCalendar.setTime(Date.valueOf(start));
        endCalendar.setTime(Date.valueOf(end));

         while (startCalendar.compareTo(endCalendar) < 0) {
             String dateString = String.format("%d-%02d-%02d", startCalendar.get(Calendar.YEAR),
             startCalendar.get(Calendar.MONTH) + 1, startCalendar.get(Calendar.DAY_OF_MONTH));

             for (int i = 1; i < ((int)(Math.random() * 97)) + 3; i++) {
                 String query = String.format("INSERT INTO %s (%s) VALUES (%d, '%s', %f)",
                     DBTable.AvailableDateDB, "listingId, startDate, price",  i, dateString, (double) Math.round(Math.random() * 17000) / 100 + 40);
                 System.out.println(query);
                 DBTable.db.executeUpdate(query, null, null);
             }

            startCalendar.roll(Calendar.DATE, true);
             if (startCalendar.get(Calendar.DATE) == 1) {
                startCalendar.roll(Calendar.MONTH, true);
                 if (startCalendar.get(Calendar.MONTH) == 0)
                    startCalendar.roll(Calendar.YEAR, true);
             }
        }

        Booking.bookListing("155555551", "1", "2023-09-07", "2023-9-12");
        Booking.bookListing("166666661", "1", "2023-09-12", "2023-9-14");
        Booking.bookListing("155555551", "1", "2023-09-23", "2023-10-05");
        Booking.bookListing("266666662", "1", "2022-10-23", "2022-11-03");
        Booking.bookListing("277777772", "1", "2023-08-23", "2023-08-31");

        Booking.bookListing("133333331", "2", "2023-08-03", "2023-08-09");
        Booking.bookListing("155555551", "2", "2024-12-04", "2024-12-05");
        Booking.bookListing("166666661", "2", "2023-08-23", "2023-09-01");
        Booking.bookListing("266666662", "2", "2023-10-23", "2023-11-03");
        Booking.bookListing("277777772", "2", "2023-12-23", "2023-12-31");

        Booking.bookListing("177777771", "3", "2023-10-13", "2023-10-18");
        Booking.bookListing("188888881", "3", "2023-08-25", "2023-08-26");
        Booking.bookListing("166666661", "3", "2023-08-23", "2023-08-25");
        Booking.bookListing("166666661", "3", "2023-09-04", "2023-09-09");
        Booking.bookListing("166666661", "3", "2023-10-23", "2023-11-03");
        Booking.bookListing("266666662", "3", "2023-11-04", "2023-11-06");
        Booking.bookListing("277777772", "3", "2023-12-12", "2023-12-15");

        Booking.bookListing("188888881", "4", "2025-08-09", "2026-08-25");
        Booking.bookListing("188888881", "4", "2023-11-09", "2023-11-15");
        Booking.bookListing("188888881", "4", "2023-07-22", "2023-07-24");

        Booking.bookListing("133333331", "5", "2023-08-09", "2023-08-25");
        Booking.bookListing("166666661", "5", "2023-05-23", "2023-06-03");
        Booking.bookListing("133333331", "5", "2023-07-11", "2023-07-14");

        Booking.bookListing("166666661", "6", "2023-06-17", "2023-06-19");
        Booking.bookListing("122222221", "6", "2023-07-22", "2023-07-24");
        Booking.bookListing("188888881", "6", "2023-07-27", "2023-07-31");

        Booking.bookListing("177777771", "7", "2023-04-17", "2023-05-19");


        Booking.bookListing("166666661", "8", "2023-06-07", "2023-06-09");
        Booking.bookListing("122222221", "8", "2023-07-12", "2023-07-14");
        Booking.bookListing("188888881", "8", "2023-07-22", "2023-07-24");
        Booking.bookListing("177777771", "8", "2023-09-17", "2023-09-19");
        Booking.bookListing("266666662", "8", "2022-11-04", "2022-11-06");
        Booking.bookListing("277777772", "8", "2022-12-12", "2022-12-15");

        Booking.bookListing("166666661", "9", "2023-11-17", "2023-11-25");
        Booking.bookListing("133333331", "9", "2023-10-09", "2023-10-13");

        Booking.bookListing("122222221", "10", "2023-05-22", "2023-05-24");

        Booking.bookListing("177777771", "11", "2023-02-01", "2023-02-09");
        Booking.bookListing("188888881", "11", "2023-08-01", "2023-08-03");
        Booking.bookListing("188888881", "11", "2024-01-01", "2024-12-31");

        Booking.bookListing("155555551", "12", "2023-12-01", "2023-12-04");
        Booking.bookListing("266666662", "12", "2023-09-14", "2023-09-17");
        Booking.bookListing("277777772", "12", "2023-11-27", "2023-11-29");
        Booking.bookListing("277777772", "12", "2023-11-29", "2023-11-30");
        Booking.bookListing("277777772", "12", "2023-09-29", "2023-09-30");
        Booking.bookListing("277777772", "12", "2023-10-01", "2023-10-02");
        Booking.bookListing("277777772", "12", "2023-10-02", "2023-10-04");
        Booking.bookListing("277777772", "12", "2023-10-04", "2023-10-07");

        Booking.bookListing("676312612", "74", "2024-11-05", "2024-12-05");
        Booking.bookListing("144444441", "62", "2022-04-31", "2022-05-31");
        Booking.bookListing("258840678", "81", "2024-03-06", "2024-04-06");
        Booking.bookListing("460173513", "13", "2025-11-30", "2025-12-30");
        Booking.bookListing("599394011", "98", "2024-07-01", "2024-07-05");
        Booking.bookListing("484055042", "36", "2023-01-21", "2023-01-25");
        Booking.bookListing("258840678", "36", "2024-12-08", "2024-12-15");
        Booking.bookListing("029969970", "70", "2024-09-30", "2024-10-02");
        Booking.bookListing("619150233", "90", "2022-05-30", "2022-06-30");
        Booking.bookListing("320949279", "24", "2024-08-30", "2024-09-30");
        Booking.bookListing("889977038", "28", "2023-09-08", "2023-10-08");
        Booking.bookListing("790046557", "41", "2024-11-01", "2024-11-08");
        Booking.bookListing("472829926", "74", "2024-04-30", "2024-05-05");
        Booking.bookListing("925758730", "85", "2023-11-08", "2023-12-08");
        Booking.bookListing("676312612", "50", "2024-11-17", "2024-12-17");
        Booking.bookListing("323577222", "96", "2022-12-13", "2022-12-23");
        Booking.bookListing("166666661", "49", "2024-10-30", "2024-10-31");
        Booking.bookListing("522340330", "68", "2026-01-30", "2026-02-02");
        Booking.bookListing("738273869", "58", "2023-08-21", "2023-08-28");
        Booking.bookListing("137056904", "49", "2024-03-14", "2024-03-19");
        Booking.bookListing("987692755", "36", "2023-11-31", "2023-12-02");
        Booking.bookListing("122222221", "82", "2022-05-08", "2022-06-08");
        Booking.bookListing("137056904", "98", "2025-09-09", "2025-09-12");
        Booking.bookListing("957719874", "44", "2025-03-31", "2025-04-01");
        Booking.bookListing("472829926", "51", "2026-03-31", "2026-04-02");
        Booking.bookListing("287997303", "30", "2022-06-19", "2022-06-22");
        Booking.bookListing("987692755", "15", "2026-09-30", "2026-10-01");
        Booking.bookListing("191702269", "43", "2022-12-25", "2022-12-31");
        Booking.bookListing("472829926", "63", "2026-12-23", "2026-12-29");
        Booking.bookListing("877783005", "61", "2023-05-31", "2023-06-02");
        Booking.bookListing("484055042", "84", "2023-12-30", "2024-01-01");
        Booking.bookListing("320949279", "82", "2026-12-19", "2026-12-25");
        Booking.bookListing("289224235", "42", "2026-06-05", "2026-06-06");
        Booking.bookListing("983625551", "55", "2024-09-18", "2024-09-23");
        Booking.bookListing("191702269", "13", "2022-10-02", "2022-10-22");
        Booking.bookListing("878119760", "81", "2022-06-29", "2022-06-31");
        Booking.bookListing("277777772", "97", "2025-09-20", "2025-09-30");
        Booking.bookListing("878119760", "60", "2023-09-30", "2023-10-06");
        Booking.bookListing("266666662", "10", "2022-01-04", "2022-02-04");
        Booking.bookListing("878119760", "92", "2024-08-03", "2024-08-13");
        Booking.bookListing("522340330", "76", "2026-02-06", "2026-02-16");
        Booking.bookListing("288888882", "50", "2026-06-30", "2026-06-31");
        Booking.bookListing("188888881", "50", "2026-10-07", "2026-10-17");
        Booking.bookListing("320949279", "46", "2025-10-20", "2025-10-21");
        Booking.bookListing("877783005", "48", "2026-12-02", "2026-12-12");
        Booking.bookListing("511886807", "61", "2022-11-09", "2022-11-19");
        Booking.bookListing("277777772", "96", "2025-05-07", "2025-05-17");
        Booking.bookListing("878119760", "42", "2023-03-16", "2023-03-18");
        Booking.bookListing("831598436", "33", "2023-12-20", "2023-12-21");
        Booking.bookListing("983625551", "85", "2024-12-08", "2024-12-18");
        Booking.bookListing("821169232", "38", "2023-05-09", "2023-05-19");
        Booking.bookListing("974193888", "75", "2025-12-31", "2026-01-06");
        Booking.bookListing("676312612", "98", "2024-09-07", "2024-09-17");
        Booking.bookListing("460173513", "81", "2024-07-30", "2024-07-31");
        Booking.bookListing("513985129", "67", "2023-10-03", "2023-10-13");
        Booking.bookListing("277777772", "23", "2025-11-31", "2026-01-01");
        Booking.bookListing("444914259", "99", "2022-12-31", "2023-02-01");
        Booking.bookListing("181544002", "57", "2022-10-28", "2022-10-20");
        Booking.bookListing("177777771", "47", "2022-06-23", "2022-06-26");

    }


    public static void updateData() {
        DBTable.dev = true;
        Calendar todayCalendar = Calendar.getInstance();
        String dateString = String.format("%d-%02d-%02d", todayCalendar.get(Calendar.YEAR),
            todayCalendar.get(Calendar.MONTH) + 1, todayCalendar.get(Calendar.DAY_OF_MONTH));
        String[] queries = new String[] {
            "DELETE FROM AvailableDate WHERE startDate <= '" + dateString + "'",
            "UPDATE Booking SET bookingStatus = 'CANCELLED BY RENTER' WHERE renterSin = '255555552' AND startDate <= '2023-11-01'",
            "UPDATE Booking SET bookingStatus = 'CANCELLED BY HOST' WHERE listingId = 12 AND startDate = '2023-11-30'",
            "UPDATE Booking SET bookingStatus = 'CANCELLED BY HOST' WHERE listingId = 2 AND startDate = '2023-10-23'",
            "UPDATE Booking SET bookingStatus = 'CANCELLED BY HOST' WHERE listingId = 3 AND startDate = '2023-12-12'",
            "UPDATE Booking SET bookingStatus = 'CANCELLED BY HOST' WHERE listingId = 8 AND startDate = '2022-11-04'",
            "UPDATE Booking SET bookingStatus = 'CANCELLED BY RENTER' WHERE listingId = 8 AND startDate = '2022-12-12'",
            "UPDATE Booking SET bookingStatus = 'CANCELLED BY RENTER' WHERE listingId = 8 AND startDate = '2023-09-17'",
            "UPDATE Booking SET bookingStatus = 'CANCELLED BY RENTER' WHERE listingId = 12 AND startDate <= '2023-10-02'",
            "UPDATE Booking SET bookingStatus = 'CANCELLED BY HOST' WHERE listingId = 12 AND startDate > '2023-10-02'",
            "INSERT INTO Rating (authorSin, renterSin, listingId, startDate, score, object) VALUES ('277777772', '277777772', 12, '2023-09-29', 3, 'Host')",
            "INSERT INTO Rating (authorSin, renterSin, listingId, startDate, score, object) VALUES ('277777772', '277777772', 12, '2023-09-29', 3, 'Listing')",
            "INSERT INTO Rating (authorSin, renterSin, listingId, startDate, score, object, commentBody) VALUES ('266666662', '266666662', 1, '2022-10-23', 5, 'Host', 'Lovely home! Host was very accomadating of my disability')",
            "INSERT INTO Rating (authorSin, renterSin, listingId, startDate, score, object, commentBody) VALUES ('266666662', '266666662', 1, '2022-10-23', 5, 'Listing', 'Lovely home! Comfortable bed and clean carpet')",
            "INSERT INTO Rating (authorSin, renterSin, listingId, startDate, score, object, commentBody) VALUES ('144444441', '266666662', 1, '2022-10-23', 5, 'Renter', 'Very considerate renter! Definitely recommend')",
            "INSERT INTO Rating (authorSin, renterSin, listingId, startDate, score, object, commentBody) VALUES ('177777771', '177777771', 11, '2023-02-01', 3, 'Host', 'Host was quite unresponsive during the entire stay')",

            "INSERT INTO Rating (authorSin, renterSin, listingId, startDate, score, object, commentBody) VALUES ('166666661', '166666661', 6, '2023-06-17', 4, 'Host', 'While their broken english was hard to understand, they still tried to communicate as best as possible.')",
            "INSERT INTO Rating (authorSin, renterSin, listingId, startDate, score, object, commentBody) VALUES ('166666661', '166666661', 6, '2023-06-17', 3, 'Listing', 'The water was too cold')",

            "INSERT INTO Rating (authorSin, renterSin, listingId, startDate, score, object, commentBody) VALUES ('188888881', '188888881', 8, '2023-07-22', 1, 'Listing', 'Ugliest room I have ever seen. Bed was itchy and uncomfortable.')",
            "INSERT INTO Rating (authorSin, renterSin, listingId, startDate, score, object, commentBody) VALUES ('144444441', '188888881', 8, '2023-07-22', 5, 'Renter', 'Clean and considerate! Recommended booking with him!')",

        };

        for (String query: queries) {
            DBTable.db.executeUpdate(query, null, null);
        }

        for (String amenity: Listing.amenities) {
            String query = String.format("INSERT INTO Amenity (amenityName) VALUES ('%s')", amenity);
            DBTable.db.executeUpdate(query, null, null);
        }

        for (int i = 0; i < 600; i ++) {
            String query = String.format("INSERT INTO ProvidedAmenity (listingId, itemId, price) VALUES (%d, %d, %f)", ((int)(Math.random() * 99)) + 1, ((int)(Math.random() * Listing.amenities.size())) + 1, ((Math.random() * 22)) + 2);
            DBTable.db.executeUpdate(query, null, null);
        }
    }
}
