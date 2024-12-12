package project.cs3360.response;

public class AccountVerificationResponse extends AbstractResponse{
    private final boolean state;
    private final String receiverName;

    public AccountVerificationResponse(boolean state, String receiverName) {
        this.state = state;
        this.receiverName = receiverName;
    }

    protected AccountVerificationResponse() {
        this.state = false;
        this.receiverName = "";
    }

    public boolean isSuccess() {
        return state;
    }

    public String getReceiverName() {
        return receiverName;
    }
}
