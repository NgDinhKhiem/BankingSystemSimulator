package project.cs3360.manager.data.notification.noti;

public class TransactionNotification extends AbstractNotification {
    private final String state;
    private final String transactionID;

    public TransactionNotification(String state, String transactionID) {
        this.state = state;
        this.transactionID = transactionID;
    }
}
