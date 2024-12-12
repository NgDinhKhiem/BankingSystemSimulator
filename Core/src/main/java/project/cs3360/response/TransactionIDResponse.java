package project.cs3360.response;

import project.cs3360.object.TransactionData;

public class TransactionIDResponse extends AbstractResponse{
    private final TransactionData transactionData;

    public TransactionIDResponse(String transactionID, String fromID, String toID, double amount, String time, String state1) {
        this.transactionData = new TransactionData(transactionID, fromID, toID, amount, time, state1);
    }
    public TransactionIDResponse(TransactionData data) {
        this.transactionData = data;
    }

    protected TransactionIDResponse() {
        this.transactionData = new TransactionData();
    }

    public TransactionData getTransactionData() {
        return transactionData;
    }
}
