package project.cs3360.response;

import project.cs3360.response.object.TransactionData;

public class TransactionListResponse extends AbstractResponse{
    private final TransactionData[] transactionID;

    public TransactionListResponse(TransactionData... transactions ) {
        this.transactionID = (transactions);
    }

    protected TransactionListResponse() {
        this.transactionID = new TransactionData[]{};
    }

    public TransactionData[] getTransactionID() {
        return transactionID;
    }
}
