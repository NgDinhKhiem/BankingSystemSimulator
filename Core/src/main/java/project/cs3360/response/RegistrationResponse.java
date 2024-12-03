package project.cs3360.response;

public class RegistrationResponse extends AbstractResponse{
    private final boolean state;
    private final String ID;

    public RegistrationResponse(boolean state, String ID) {
        this.state = state;
        this.ID = ID;
    }

    public RegistrationResponse() {
        this.state = false;
        this.ID = "";
    }

    public boolean isSuccess() {
        return state;
    }

    public String getID() {
        return ID;
    }
}
