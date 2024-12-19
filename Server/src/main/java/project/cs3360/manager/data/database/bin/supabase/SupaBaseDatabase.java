package project.cs3360.manager.data.database.bin.supabase;

import project.cs3360.manager.data.database.DataSourceHandler;
import project.cs3360.response.*;

public class SupaBaseDatabase implements DataSourceHandler {
    @Override
    public AccountBalanceResponse getAccountBalance(String ID) {
        return null;
    }

    @Override
    public AccountInformationResponse getAccountInformation(String ID) {
        return null;
    }

    @Override
    public AuthenticationResponse getAuthenticationResponse(String ID, String password) {
        return null;
    }

    @Override
    public RegistrationResponse getRegistrationResponse(String firstName, String lastName, String phoneNumber, String email, String password) {
        return null;
    }

    @Override
    public AccountVerificationResponse getAccountVerificationResponse(String receiverID) {
        return null;
    }

    @Override
    public SendingMoneyResponse getSendingMoneyResponse(String from, String to, double amount) {
        return null;
    }

    @Override
    public TransactionIDResponse getTransactionDataResponse(String transactionID) {
        return null;
    }

    @Override
    public TransactionListResponse getTransactionInformationResponse(String ID, int startIndex, int endIndex, String startDate, String endDate) {
        return null;
    }
}
