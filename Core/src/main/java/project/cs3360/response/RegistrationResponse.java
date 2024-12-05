package project.cs3360.response;

public class RegistrationResponse extends AbstractResponse{
    public final boolean state;
    public final String ID;

    public RegistrationResponse(boolean state, String ID) {
        this.state = state;
        this.ID = ID;
    }

    protected RegistrationResponse() {
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
