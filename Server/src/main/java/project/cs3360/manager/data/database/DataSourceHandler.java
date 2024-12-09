package project.cs3360.manager.data.database;

import project.cs3360.response.*;

public interface DataSourceHandler {
    AccountBalanceResponse getAccountBalance(final String ID);
    AccountInformationResponse getAccountInformation(final String ID);
    AuthenticationResponse getAuthenticationResponse(final String ID, final String password);
    RegistrationResponse getRegistrationResponse(final String firstName, final String lastName,final String phoneNumber,final String email, final String password);
    AccountVerificationResponse getAccountVerificationResponse(final String receiverID);
    SendingMoneyResponse getSendingMoneyResponse(final String from, final String to, final double amount);
    TransactionIDResponse getTransactionDataResponse(final String transactionID);
    TransactionListResponse getTransactionInformationResponse(String ID, int startIndex, int endIndex, String startDate, String endDate);
}
