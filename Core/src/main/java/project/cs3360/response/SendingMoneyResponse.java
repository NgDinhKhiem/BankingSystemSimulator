package project.cs3360.response;

public class SendingMoneyResponse extends AbstractResponse{
    public final boolean state;
    public SendingMoneyResponse(boolean state) {
        this.state = state;
    }

    protected SendingMoneyResponse() {
        this.state = false;
    }

    public boolean isSuccess() {
        return state;
    }
}
