package project.cs3360.manager.data.database;

import project.cs3360.response.*;

public interface DataSourceHandler {
    AccountBalanceResponse getAccountBalance(final String ID);
    AccountInformationResponse getAccountInformation(final String ID);
    AuthenticationResponse getAuthenticationResponse(final String ID, final String password);
    RegistrationResponse getRegistrationResponse(final String firstName, final String lastName,final String phoneNumber,final String email);
    AccountVerificationResponse getAccountVerificationResponse(final String receiverID);
    SendingMoneyResponse getSendingMoneyResponse(final String ID);
}
