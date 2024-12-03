package project.cs3360.manager.data;

import project.cs3360.Server;
import project.cs3360.manager.data.database.DataSourceHandler;
import project.cs3360.manager.data.database.bin.filesystem.SimpleFileDatabase;
import project.cs3360.response.*;
import project.cs3360.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class DataManager implements DataSourceHandler {
    //private static final String address = "example.com";
    //private static final int PORT = 80;
    private final Server server;
    private final Map<String, String> sectionTokenHolder = new HashMap<>();
    private DataSourceHandler dataSourceHandler;
    public DataManager(Server server){
        this.server = server;
        this.dataSourceHandler = new SimpleFileDatabase(this.server);
    }

    public String createToken(String ID){
        String key = Utils.generateRDKey(32);
        this.sectionTokenHolder.put(ID,key);
        return key;
    }

    public boolean isValid(String ID, String token){
        if(!this.sectionTokenHolder.containsKey(ID))
            return false;
        return this.sectionTokenHolder.get(ID).equals(token);
    }

    @Override
    public AccountBalanceResponse getAccountBalance(final String ID) {
        return dataSourceHandler.getAccountBalance(ID);
    }

    @Override
    public AccountInformationResponse getAccountInformation(final String ID) {
        return dataSourceHandler.getAccountInformation(ID);
    }

    @Override
    public AuthenticationResponse getAuthenticationResponse(final String ID, final String password) {
        return dataSourceHandler.getAuthenticationResponse(ID, password);
    }

    @Override
    public RegistrationResponse getRegistrationResponse(final String firstName, final String lastName, final String phoneNumber, final String email) {
        return dataSourceHandler.getRegistrationResponse(firstName, lastName, phoneNumber, email);
    }

    @Override
    public AccountVerificationResponse getAccountVerificationResponse(final String receiverID) {
        return dataSourceHandler.getAccountVerificationResponse(receiverID);
    }

    @Override
    public SendingMoneyResponse getSendingMoneyResponse(final String ID) {
        return dataSourceHandler.getSendingMoneyResponse(ID);
    }
}
