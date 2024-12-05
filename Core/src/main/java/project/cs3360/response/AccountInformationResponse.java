package project.cs3360.response;

public class AccountInformationResponse extends AbstractResponse{
    public final boolean state;
    public final String ID;
    public final String firstName;
    public final String lastName;
    public final String email;
    public final String phoneNumber;

    public AccountInformationResponse(boolean state, String id, String firstName, String lastName, String email, String phoneNumber) {
        this.state = state;
        this.ID = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    protected AccountInformationResponse(){
        this.state = false;
        this.ID = "";
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.phoneNumber = "";
    }

    public boolean isState() {
        return state;
    }

    public String getID() {
        return ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
