package project.cs3360.response;

public class AccountInformationResponse extends AbstractResponse{
    private final boolean state;
    private final String ID;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;

    public AccountInformationResponse(boolean state, String id, String firstName, String lastName, String email, String phoneNumber) {
        this.state = state;
        this.ID = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public AccountInformationResponse(){
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
