package project.cs3360.manager.data.database;

import project.cs3360.response.*;

public interface DataSourceHandler {
    AccountBalanceResponse getAccountBalance();
    AccountInformationResponse getAccountInformation();
    AuthenticationResponse getAuthenticationResponse();
    RegistrationResponse getRegistrationResponse();
    AccountVerificationResponse getAccountVerificationResponse();
}
