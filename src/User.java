public class User {

    public final String UserDB = "User";
    public DBConnection db;
    public void setConnection (DBConnection db){
        this.db = db;
    }
    /*
        CREATE TABLE User (
        sin CHAR(9) PRIMARY KEY,
        name VARCHAR(30) NOT NULL,
        occupation VARCHAR(15),
        address VARCHAR(50),
        dateOfBirth DATE
    );*/
    public void createUser(String sin, String user, String occupation,
        String address, String dateOfBirth){

        String query = String.format("INSERT INTO %s VALUES ('%s', '%s', '%s', '%s', '%s')", 
            "User (sinNumber, fullName, occupation, address, dateOfBirth)",
            sin, user, occupation, address, dateOfBirth );
        
        db.execute(query);
    }

    /*CREATE TABLE Renter (
        sin CHAR(9) PRIMARY KEY,
        paymentInfo VARCHAR(30),
        FOREIGN KEY (sin) REFERENCES User(sin)
            ON DELETE CASCADE
            ON UPDATE CASCADE
    );*/

    /*CREATE TABLE Host (
        sin CHAR(9) PRIMARY KEY,
        FOREIGN KEY (sin) REFERENCES User(sin)
            ON DELETE CASCADE
            ON UPDATE CASCADE
    );*/
}
