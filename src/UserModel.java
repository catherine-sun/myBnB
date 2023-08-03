import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class UserModel {
    private String sinNumber;
    private String fullName;
    private String occupation;
    private String address;
    private Date dateOfBirth;
    private Role role;

    enum Role {
        RENTER,
        HOST,
        BOTH
    }

    public UserModel(String sinNumber, String fullName, String occupation, String address, Date dateOfBirth) {
        this.sinNumber = sinNumber;
        this.fullName = fullName;
        this.occupation = occupation;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.role = null;
    }

    public UserModel (ResultSet rs) throws SQLException {
        this.sinNumber = rs.getString("sinNumber");
        this.fullName = rs.getString("fullName");
        this.occupation = rs.getString("occupation");
        this.address = rs.getString("address");
        this.dateOfBirth = rs.getDate("dateOfBirth");
        this.role = null;
    }

    public String getSinNumber() { return sinNumber; }
    public String getFullName() { return fullName; }
    public String getOccupation() { return occupation; }
    public String getAddress() { return address; }
    public Date getDateOfBirth() { return dateOfBirth; }

    public void setRole(String str) {
        switch(str) {
            case "Host":
                role = isRenter() ? Role.BOTH : Role.HOST;
                break;
            case "Renter":
                role = isHost() ? Role.BOTH : Role.RENTER;
                break;
            case "Reset" :
                role = null;
                break;
        }
    }

    public boolean isHost() { return role == Role.HOST || role == Role.BOTH; }
    public boolean isRenter() { return role == Role.RENTER || role == Role.BOTH; }
}
