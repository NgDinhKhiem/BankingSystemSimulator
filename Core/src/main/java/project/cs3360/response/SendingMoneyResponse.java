package project.cs3360.response;

public class SendingMoneyResponse extends AbstractResponse{
    private final boolean state;
    private final String transactionID;
    public SendingMoneyResponse(boolean state, String transactionID) {
        this.state = state;
        this.transactionID = transactionID;
    }

    protected SendingMoneyResponse() {
        this.state = false;
        this.transactionID = "";
    }

    public boolean isSuccess() {
        return state;
    }

    public String getTransactionID() {
        return transactionID;
    }
}
