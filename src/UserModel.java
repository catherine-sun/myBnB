

import java.sql.Date;

public class UserModel {
    private String sinNumber;
    private String fullName;
    private String occupation;
    private String address;
    private Date dateOfBirth;

    public UserModel(String sinNumber, String fullName, String occupation, String address, Date dateOfBirth) {
        this.sinNumber = sinNumber;
        this.fullName = fullName;
        this.occupation = occupation;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
   }

    // public void createUser() {
    //   String format = "INSERT INTO USER(sinNumber, fullName, occupation, address"
    //                   + ", dateOfBirth) VALUES ('%s', '%s','%s', '%s', '%s')";
    //   String query = String.format(format, sinNumber, fullName, occupation,
    //                                address, dateOfBirth );

    //   db.execute(query, "Successfully created user", null);
    // }

    public String getSinNumber() { return sinNumber; }
    public String getFullName() { return fullName; }
    public String getOccupation() { return occupation; }
    public String getAddress() { return address; }
    public Date getDateOfBirth() { return dateOfBirth; }

}
