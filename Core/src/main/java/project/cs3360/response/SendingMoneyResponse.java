package project.cs3360.response;

public class SendingMoneyResponse extends AbstractResponse{
    private final boolean state;
    public SendingMoneyResponse(boolean state) {
        this.state = state;
    }

    public SendingMoneyResponse() {
        this.state = false;
    }

    public boolean isSuccess() {
        return state;
    }
}
