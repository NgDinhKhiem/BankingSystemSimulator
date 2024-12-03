package project.cs3360.response;

public class AuthenticationResponse extends AbstractResponse{
    private final boolean state;
    private final String token;

    public AuthenticationResponse(boolean state, String token) {
        this.state = state;
        this.token = token;
    }

    public AuthenticationResponse() {
        this.state = false;
        this.token = "";
    }

    public boolean isSuccess() {
        return state;
    }

    public String getToken() {
        return token;
    }
}
